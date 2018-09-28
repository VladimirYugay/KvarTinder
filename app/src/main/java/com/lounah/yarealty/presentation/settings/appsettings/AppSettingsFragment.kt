package com.lounah.yarealty.presentation.settings.appsettings

import android.arch.lifecycle.Observer
import com.lounah.yarealty.BuildConfig
import com.lounah.yarealty.R
import com.lounah.yarealty.presentation.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_app_settings.*
import javax.inject.Inject
import de.psdev.licensesdialog.LicensesDialog
import android.content.Intent
import android.net.Uri
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.MapType


class AppSettingsFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: AppSettingsViewModel
    
    override val TAG = "APP_SETTINGS_FRAGMENT"
    override val layoutRes = R.layout.fragment_app_settings

    override fun initComponentsCallbacks() {

        textview_appsettings_clear_map_cache.setOnClickListener {
            showToast(R.string.successfully)
        }

        textview_appsettings_clearimagecache.setOnClickListener { clearGlideCache() }

        textview_appsettings_cleardata.setOnClickListener { clearData() }

        textview_appsettings_liceses.setOnClickListener { showLicenses() }

        textview_appsettings_license_agreement.setOnClickListener {
            onOpenWebPage(resources.getString(R.string.mobile_argeement_link))
        }

        textview_appsettings_privacy_policy.setOnClickListener {
            onOpenWebPage(resources.getString(R.string.privacy_policy_link))
        }

        textview_appsettings_mapkit_terms_of_use.setOnClickListener {
            onOpenWebPage(resources.getString(R.string.mapkit_terms_of_use_link))
        }

        textview_appsettings_support.setOnClickListener {
            val intent = composeEmail(resources.getString(R.string.support_email), resources.getString(R.string.app_name))
            startActivity(intent)
        }

//        rg_appsettings_maptypes.setOnCheckedChangeListener { radioGroup, checkedId ->
//            when(checkedId) {
//                R.id.btn_schema -> viewModel.changeMapType(MapType.VECTOR_MAP)
//                R.id.btn_atlas -> viewModel.changeMapType(MapType.SATELLITE)
//                R.id.btn_hybrid -> viewModel.changeMapType(MapType.HYBRID)
//            }
//        }

        initViewModelObservers()
    }

    override fun initComponents() {
        textview_appsettings_app_version.text = resources.getString(R.string.app_version, BuildConfig.VERSION_NAME)
    }

    private fun initViewModelObservers() {
        viewModel.isDataClearing.observe(this, Observer {
            it?.let {
                if (it) showToast(R.string.clearing)
                else showToast(R.string.cleared)
            }
        })

        viewModel.isImagesCacheClearing.observe(this, Observer {
            it?.let {
                if (it) showToast(R.string.clearing)
                else showToast(R.string.cleared)
            }
        })
    }

    private fun clearGlideCache() {
        viewModel.clearImagesCache(context)
    }

    private fun clearData() {
        viewModel.clearData()
    }

    private fun showLicenses() {
        LicensesDialog.Builder(context)
                .setNotices(R.raw.licenses)
                .build()
                .showAppCompat()
    }

    private fun onOpenWebPage(url: String) {
        val openBrowserIntent = Intent(Intent.ACTION_VIEW)
        openBrowserIntent.data = Uri.parse(url)
        startActivity(openBrowserIntent)
    }

    private fun composeEmail(address: String, subject: String): Intent {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, address)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        return intent
    }
}