package com.lounah.yarealty.presentation.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View

interface LoadFragmentCallback {

    fun loadFragment(fragment: Fragment)

    fun loadFragmentWithoutBackStack(fragment: Fragment)

    fun startNewActivity(activity: AppCompatActivity, args: Bundle)
}