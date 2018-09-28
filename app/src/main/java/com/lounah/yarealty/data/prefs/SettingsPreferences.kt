package com.lounah.yarealty.data.prefs

import android.content.Context
import com.lounah.yarealty.domain.model.RealtySettings
import com.lounah.yarealty.domain.model.SettingsCase
import javax.inject.Inject

class SettingsPreferences @Inject constructor(context: Context)
    : AbstractSharedPreferencesService(context, "settings") {

    private companion object {
        const val CASE_PREF = "case"
        const val DEFAULT_CASE_PREF = "0_0"
        const val PAGE_KEY = "page"
    }

    fun getSettings(): RealtySettings {
        val case = SettingsCase.getCaseFor(sharedPreferences.getString(CASE_PREF, DEFAULT_CASE_PREF)!!)

        val map = mutableMapOf<String, Any>()

        val prefs: Map<String, *> = sharedPreferences.all

        for ((k, v) in prefs) v?.let { map[k] = it }
//
//        case.defaultParams.filterKeys { !map.containsKey(it) }.forEach { k, v -> map[k] = v }
//
        case.defaultParams.forEach {entry ->
            if (!map.containsKey(entry.key)) map[entry.key] = entry.value
        }

        val caseName = case.settingCode
        val casePage = casesPageSharedPrefs.getInt(caseName, 0)
        map[PAGE_KEY] = casePage

        return RealtySettings.fromMap(map, case)
    }

    fun updateCase(newCase: String) {
        sharedPreferences.edit().clear().putString("case", newCase).apply()
    }

    fun updateString(key: String, value: String) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply()
    }

    fun updateInteger(key: String, value: Int) {
        sharedPreferences.edit()
                .putInt(key, value)
                .apply()
    }

    fun updateLong(key: String, value: Long) {
        sharedPreferences.edit()
                .putLong(key, value)
                .apply()
    }

    fun removeKey(key: String) {
        sharedPreferences.edit()
                .remove(key)
                .apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
        casesPageSharedPrefs.edit().clear().apply()
    }

    fun saveCasePage(case: String, page: Int) {
        casesPageSharedPrefs.edit().putInt(case, page).apply()
    }
}