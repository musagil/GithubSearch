package com.github.search

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.MvRx
import com.github.search.FragmentNavigation.NavControllerIdentifier.ChildNavController
import com.github.search.FragmentNavigation.NavControllerIdentifier.ParentNavController
import com.github.search.FragmentNavigation.NavControllerIdentifier.RootNavController
import javax.inject.Inject

@OpenForTesting
class FragmentNavigation @Inject constructor(val fragment: Fragment) {

    fun navigate(
        @IdRes destination: Int,
        args: Parcelable? = null,
        navControllerIdentifier: NavControllerIdentifier = ParentNavController
    ) = navController(navControllerIdentifier).navigate(destination, bundleOf(MvRx.KEY_ARG to args))

    fun popBackStack(navControllerIdentifier: NavControllerIdentifier = ParentNavController) =
        navController(navControllerIdentifier).popBackStack()

    /**
     * If you want to navigate you should use [navigate] instead, so that the navigation logic will be testable.
     */
    fun navController(identifier: NavControllerIdentifier = ParentNavController) = when (identifier) {
        RootNavController -> fragment.requireActivity().findNavController(R.id.nav_host)
        ParentNavController -> fragment.findNavController()
        is ChildNavController -> {
            val navHostFragment =
                fragment.childFragmentManager.findFragmentById(identifier.navHostFragmentId) as NavHostFragment
            navHostFragment.navController
        }
    }

    sealed class NavControllerIdentifier {
        /**
         * Outermost navController of which id is R.id.navFragment.
         *
         * Use this to navigate to a root level destination (like Login screen) inside a nested navigation destination.
         */
        object RootNavController : NavControllerIdentifier()

        /**
         * Default navController which is the closest parent of the fragment.
         *
         * Use this to navigate to a sibling destination.
         */
        object ParentNavController : NavControllerIdentifier()

        /**
         * Child navController is used in nested screens where we have multiple Fragments.
         * The [navHostFragmentId] parameter is mandatory in this case.
         *
         * Use this to navigate within NestedFragments like SupplierDetail
         */
        data class ChildNavController(@IdRes val navHostFragmentId: Int) : NavControllerIdentifier()
    }
}
