package com.morris.concepcionapp.ui.main.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.morris.concepcionapp.R
import com.morris.concepcionapp.ui.main.fragments.InicioFragment
import com.morris.concepcionapp.ui.main.fragments.NoticiasFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_2,
    R.string.tab_text_1
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when (position) {

            0 -> {
                NoticiasFragment()
            }
            else -> {
                InicioFragment.newInstance()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {

        // Show 2 total pages.
        return 2
    }
}