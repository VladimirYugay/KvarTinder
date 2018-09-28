package com.lounah.yarealty.api.domain

import com.lounah.yarealty.domain.interactor.AppSettingsInteractor
import com.lounah.yarealty.domain.repository.OfferRepository
import com.lounah.yarealty.domain.repository.SettingsRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class AppSettingsInteractorTest {
    @Mock
    lateinit var offerRepository: OfferRepository

    @Mock
    lateinit var settingsRepository: SettingsRepository

    private lateinit var appSettingsInteractor: AppSettingsInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        appSettingsInteractor = AppSettingsInteractor(offerRepository, settingsRepository)
    }

    @Test
    fun clearData_withResult() {
        `when`(appSettingsInteractor.clearData()).thenReturn(Single.just(true))
        verify(settingsRepository).clearSettings()
        verifyNoMoreInteractions(settingsRepository)
    }
}