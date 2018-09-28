package com.lounah.yarealty.presentation.main

import android.content.Context
import android.graphics.PorterDuff
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import com.lounah.yarealty.R
import com.lounah.yarealty.presentation.common.BaseFragment
import com.lounah.yarealty.presentation.common.LoadFragmentCallback
import com.lounah.yarealty.presentation.favourites.FavouritesFragment
import com.lounah.yarealty.presentation.flat.FlatsFragment
import com.lounah.yarealty.presentation.settings.SettingsFragment
import kotlinx.android.synthetic.main.fragment_main.*


class FragmentMain : BaseFragment() {

    override val TAG = "MAIN_FRAGMENT"

    override val layoutRes: Int = R.layout.fragment_main

    private lateinit var adapter: MainViewPagerAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loadFragmentCallback = context as LoadFragmentCallback
    }

    override fun initComponents() {
        initializeViewPager()
        initializeTabLayout()
    }

    private fun initializeViewPager() {
        adapter = MainViewPagerAdapter(childFragmentManager)
        viewpager_main.adapter = adapter
        viewpager_main.offscreenPageLimit = 3
    }

    private fun initializeTabLayout() {
        tablayout_main.setupWithViewPager(viewpager_main)
        tablayout_main.getTabAt(0)?.setCustomView(R.layout.view_tab_main)?.setIcon(R.drawable.ic_settings)
        tablayout_main.getTabAt(1)?.setCustomView(R.layout.view_tab_main)?.setIcon(R.drawable.ic_flats)
        tablayout_main.getTabAt(2)?.setCustomView(R.layout.view_tab_main)?.setIcon(R.drawable.ic_star)
    }

    override fun initComponentsCallbacks() {
        tablayout_main.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_settings)
                    1 -> tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_flats)
                    2 -> tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_star)
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_settings_colored)
                    1 -> tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_flats_colored)
                    2 -> tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_star_colored)
                }
            }
        })
        tablayout_main.getTabAt(1)?.select()
    }

    inner class MainViewPagerAdapter(fragmentManager: FragmentManager)
        : FragmentPagerAdapter(fragmentManager) {

        private val NUM_ITEMS = 3

        override fun getCount(): Int {
            return NUM_ITEMS
        }

        override fun getItem(position: Int): Fragment? =
                when (position) {
                    0 -> SettingsFragment()
                    1 -> FlatsFragment()
                    2 -> FavouritesFragment()
                    else -> FlatsFragment()
                }

        override fun getPageTitle(position: Int): CharSequence? = null
    }
}