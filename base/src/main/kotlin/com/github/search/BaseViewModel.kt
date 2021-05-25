package com.github.search

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import kotlin.reflect.KProperty1

abstract class BaseViewModel<S : MvRxState>(
    initialState: S
) : BaseMvRxViewModel<S>(initialState, BuildConfig.DEBUG) {

    protected fun <A> selectSubscribeNonNull(
        prop1: KProperty1<S, A>,
        subscriber: (A) -> Unit
    ) {
        selectSubscribe(prop1 = prop1) {
            if (it != null) {
                subscriber.invoke(it)
            }
        }
    }
}
