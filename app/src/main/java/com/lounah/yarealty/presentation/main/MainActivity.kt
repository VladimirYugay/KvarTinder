package com.lounah.yarealty.presentation.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.lounah.yarealty.R
import com.lounah.yarealty.presentation.common.BaseActivity
import com.lounah.yarealty.presentation.common.LoadFragmentCallback

class MainActivity : BaseActivity(), LoadFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            loadFragmentWithoutBackStack(FragmentMain())
        }
    }

    override fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(fragment.javaClass.name)
                .commit()
    }

    override fun loadFragmentWithoutBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

    override fun startNewActivity(activity: AppCompatActivity, args: Bundle) {
        val intent = Intent(this, activity::class.java)
        intent.putExtras(args)
        startActivity(intent)
    }
}
