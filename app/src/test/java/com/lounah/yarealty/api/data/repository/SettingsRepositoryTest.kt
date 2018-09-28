package com.lounah.yarealty.api.data.repository

import com.lounah.yarealty.data.network.NetworkSource
import com.lounah.yarealty.data.network.RealtyApi
import com.lounah.yarealty.data.prefs.SettingsPreferences
import com.lounah.yarealty.data.repository.SettingsRepositoryImpl
import com.lounah.yarealty.device.network.NetworkInfo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class SettingsRepositoryTest {

    @Mock
    lateinit var settingsPreferences: SettingsPreferences

    @Mock
    lateinit var realtyApi: RealtyApi

    @Mock
    lateinit var networkInfo: NetworkInfo

    private lateinit var api: NetworkSource
    private lateinit var settingsRepository: SettingsRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        api = NetworkSource(realtyApi)
        settingsRepository = SettingsRepositoryImpl(settingsPreferences, api, networkInfo)
    }

    @Test
    fun getsSettings() {
        settingsRepository.getSettings()

        verify(settingsPreferences).getSettings()
        verifyNoMoreInteractions(settingsPreferences)
    }

    @Test
    fun updatesSettingCase() {
        val newSettingsCase = "0_0"
        settingsRepository.updateSettingCase(newSettingsCase)

        verify(settingsPreferences).updateCase(newSettingsCase)
        verify(settingsPreferences).getSettings()
        verifyNoMoreInteractions(settingsPreferences)
    }

    @Test
    fun updatesStringSetting() {
        val newSetting = Pair("", "")
        settingsRepository.updateStringSetting(newSetting.first, newSetting.second)

        verify(settingsPreferences).updateString(newSetting.first, newSetting.second)
        verifyNoMoreInteractions(settingsPreferences)
    }

    @Test
    fun updatesIntSetting() {
        val newSetting = Pair("", 0)
        settingsRepository.updateIntSetting(newSetting.first, newSetting.second)

        verify(settingsPreferences).updateInteger(newSetting.first, newSetting.second)
        verifyNoMoreInteractions(settingsPreferences)
    }

    @Test
    fun updatesBooleanSetting() {
        val newSetting = Pair("", true)
        settingsRepository.updateStringSetting(newSetting.first, "YES")

        verify(settingsPreferences).updateString(newSetting.first, "YES")
        verifyNoMoreInteractions(settingsPreferences)
    }

    @Test
    fun clearsSettings() {
        settingsRepository.clearSettings()

        verify(settingsPreferences).clear()
        verify(settingsPreferences).updateCase("0_0")
        verify(settingsPreferences).getSettings()
        verifyNoMoreInteractions(settingsPreferences)
    }
}