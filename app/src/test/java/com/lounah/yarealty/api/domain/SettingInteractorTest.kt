package com.lounah.yarealty.api.domain

import com.lounah.yarealty.data.repository.SettingsRepositoryImpl
import com.lounah.yarealty.domain.interactor.SettingInteractor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class SettingInteractorTest {

    @Mock
    lateinit var settingRepository: SettingsRepositoryImpl

    private lateinit var settingsInteractor: SettingInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        settingsInteractor = SettingInteractor(settingRepository)
    }

    @Test
    fun getsCurrentSettings() {
        settingsInteractor.getCurrentSettings()

        verify(settingRepository).getSettings()
        verifyNoMoreInteractions(settingRepository)
    }

    @Test
    fun updatesSettingsCase() {
        val newSettingsCase = "0_0"
        settingsInteractor.updateSettingCase(newSettingsCase)

        verify(settingRepository).updateSettingCase(newSettingsCase)
        verifyNoMoreInteractions(settingRepository)
    }

    @Test
    fun updatesStringSetting() {
        val newSetting = Pair("", "")
        settingsInteractor.updateStringSetting(newSetting.first, newSetting.second)

        verify(settingRepository).updateStringSetting(newSetting.first, newSetting.second)
        verifyNoMoreInteractions(settingRepository)
    }

    @Test
    fun updatesIntSetting() {
        val newSetting = Pair("", 0)
        settingsInteractor.updateIntSetting(newSetting.first, newSetting.second)

        verify(settingRepository).updateIntSetting(newSetting.first, newSetting.second)
        verifyNoMoreInteractions(settingRepository)
    }

    @Test
    fun updatesBooleanSetting() {
        val newSetting = Pair("", true)
        settingsInteractor.updateStringSetting(newSetting.first, "YES")

        verify(settingRepository).updateStringSetting(newSetting.first, "YES")
        verifyNoMoreInteractions(settingRepository)
    }
}