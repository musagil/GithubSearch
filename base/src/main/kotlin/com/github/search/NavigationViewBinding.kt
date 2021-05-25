package com.github.search

import android.view.View.OnClickListener
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.github.search.ktx.hideKeyboard
import com.google.android.material.navigation.NavigationView
import javax.inject.Inject

/**
 * This class takes care of the navigation going through the [Toolbar] of those fragments that
 * live within a [DrawerLayout] and are tied to a [NavigationView].
 *
 * The navigation is set via databinding.
 */
class NavigationViewBinding @Inject constructor(private val fragment: Fragment) {

    private fun findNavController() = fragment.findNavController()

    @get:MenuRes
    var toolbarMenuResId: Int = R.menu.empty_menu
    var menuItemClickListener: Toolbar.OnMenuItemClickListener? = null
    var toolbarTitle: String? = null

    var searchableCallback: (String) -> Unit = {}

    @get:DrawableRes
    val toolbarNavigationIcon: Int
        get() = if (findNavController().currentDestination?.isHomeDestination() == true) {
            R.drawable.ic_menu
        } else {
            R.drawable.ic_back
        }

    val toolbarNavigationIconClickListener = OnClickListener {
        it.hideKeyboard()
        findNavController().navigateUp()
    }

    private fun NavDestination.isHomeDestination() = id == R.id.repoListFragment
}
