package com.lounah.yarealty.presentation.favourites

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.lounah.yarealty.R
import com.lounah.yarealty.presentation.common.BaseFragment
import com.lounah.yarealty.presentation.favourites.calls.CallHistoryFragment
import com.lounah.yarealty.presentation.favourites.myfavorites.MyFavouritesFragment
import kotlinx.android.synthetic.main.fragment_favs.*


class FavouritesFragment : BaseFragment() {

    override val TAG = "FAVS_FRAGMENT"

    override val layoutRes = R.layout.fragment_favs

    private companion object {
        private val CONTAINER_ID = R.id.container_favs
        private val MY_FAVS_FRAGMENT_POSITION = 0
        private val CALL_HISTORY_FRAGMENT_POSITION = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null)
            replaceFragment(MyFavouritesFragment())
    }

    override fun initComponents() {

    }

    override fun initComponentsCallbacks() {
        tablayout_favs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {
                    MY_FAVS_FRAGMENT_POSITION -> replaceFragment(MyFavouritesFragment())
                    CALL_HISTORY_FRAGMENT_POSITION -> replaceFragment(CallHistoryFragment())
                }
            }
        })
        tablayout_favs.getTabAt(MY_FAVS_FRAGMENT_POSITION)!!.select()
    }

    private fun replaceFragment(fragmentToReplace: Fragment) {
        fragmentManager!!.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(CONTAINER_ID, fragmentToReplace)
                .commit()
    }
}