package toy.narza.clonetracker.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import toy.narza.clonetracker.network.constants.LadderMode

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ProgressFragment.newInstance(LadderMode.values()[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return LadderMode.values()[position].name
    }

    override fun getCount(): Int {
        return LadderMode.values().size
    }
}