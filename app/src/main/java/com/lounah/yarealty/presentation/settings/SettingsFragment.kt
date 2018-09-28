package com.lounah.yarealty.presentation.settings

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.lounah.yarealty.R
import com.lounah.yarealty.presentation.common.BaseFragment
import com.lounah.yarealty.presentation.settings.appsettings.AppSettingsFragment
import com.lounah.yarealty.presentation.settings.filters.SearchFiltersFragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {

    override val TAG = "SETTINGS_FRAGMENT"

    override val layoutRes: Int = R.layout.fragment_settings

    private companion object {
        const val CONTAINER_ID = R.id.container_settings
        const val SEARCH_FILTERS_FRAGMENT_POSITION = 0
        const val APP_SETTINGS_FRAGMENT_POSITION = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null)
            replaceFragment(SearchFiltersFragment())
    }

    override fun initComponents() {
    }

    override fun initComponentsCallbacks() {
        tablayout_settings.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {
                    SEARCH_FILTERS_FRAGMENT_POSITION -> replaceFragment(SearchFiltersFragment())
                    APP_SETTINGS_FRAGMENT_POSITION -> replaceFragment(AppSettingsFragment())
                }
            }
        })
        tablayout_settings.getTabAt(SEARCH_FILTERS_FRAGMENT_POSITION)!!.select()
    }

    private fun replaceFragment(fragmentToReplace: Fragment) {
        fragmentManager!!.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(CONTAINER_ID, fragmentToReplace)
                .commit()
    }
}