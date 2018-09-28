package com.lounah.yarealty.api.presentation.appsettings

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.lounah.yarealty.domain.interactor.AppSettingsInteractor
import com.lounah.yarealty.presentation.settings.appsettings.AppSettingsViewModel
import com.lounah.yarealty.utils.SchedulersProvider
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class AppSettingsViewModelTest {

//    @Rule
//    @JvmField
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    lateinit var interactor: AppSettingsInteractor
//
//    @Mock
//    lateinit var schedulersProvider: SchedulersProvider
//
//    private lateinit var viewModel: AppSettingsViewModel
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        viewModel = AppSettingsViewModel(interactor, schedulersProvider)
//    }
//
//    @Test
//    fun testNull() {
//        assertThat(viewModel.isDataClearing, notNullValue())
//        assertThat(viewModel.isImagesCacheClearing, notNullValue())
//        verify(interactor, never()).clearData()
//    }
//
//    @Test
//    fun dontPostResultWithoutObservers() {
//        viewModel.clearData()
//        verify(interactor, never()).clearData()
//    }
//
//    @Test
//    fun postResultWhenObserved() {
//        viewModel.clearData()
//        viewModel.isDataClearing.observeForever(mock(ArgumentMatchers.any()))
//        verify(interactor).clearData()
//    }
//
//    @Test
//    fun changeWhileObserved() {
//        repoViewModel.repo.observeForever(mock())
//
//        repoViewModel.setId("a", "b")
//        repoViewModel.setId("c", "d")
//
//        verify(repository).loadRepo("a", "b")
//        verify(repository).loadRepo("c", "d")
//    }
}