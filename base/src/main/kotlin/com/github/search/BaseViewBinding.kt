package com.github.search

import android.annotation.SuppressLint
import androidx.databinding.BaseObservable
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.DeliveryMode
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.RedeliverOnStart
import kotlin.reflect.KProperty1

@SuppressLint("RxJava2MethodMissingCheckReturnValue") // False positive, https://github.com/vanniktech/lint-rules/issues/286
abstract class BaseViewBinding<S : MvRxState>(
    open val fragment: BaseMvRxFragment,
    open val viewModel: BaseMvRxViewModel<S>
) : BaseObservable(), MvRxView by fragment {

    protected fun findNavController() = fragment.findNavController()

    protected val view get() = fragment.requireView()

    protected val context get() = fragment.requireContext()

    open fun onChangeNotified() = Unit

    override fun notifyChange() {
        super.notifyChange()
        onChangeNotified()
    }

    protected fun <S : MvRxState, A> BaseMvRxViewModel<S>.selectSubscribeNonNull(
        prop1: KProperty1<S, A?>,
        deliveryMode: DeliveryMode = RedeliverOnStart,
        subscriber: (A) -> Unit
    ) {
        selectSubscribe(prop1 = prop1, deliveryMode = deliveryMode) {
            if (it != null) {
                subscriber.invoke(it)
            }
        }
    }
}
