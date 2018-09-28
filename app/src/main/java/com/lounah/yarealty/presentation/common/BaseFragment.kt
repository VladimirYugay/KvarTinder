package com.lounah.yarealty.presentation.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    lateinit var loadFragmentCallback: LoadFragmentCallback

    protected abstract val layoutRes: Int
    protected abstract val TAG: String

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        loadFragmentCallback = context as LoadFragmentCallback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initComponentsCallbacks()
    }

    protected fun showToast(msgId: Int) {
        Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show()
    }

    protected abstract fun initComponents()
    protected abstract fun initComponentsCallbacks()
}
