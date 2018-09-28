package com.lounah.yarealty.api.domain

import com.lounah.yarealty.api.util.StubObjectsFactory
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.domain.interactor.CallHistoryInteractor
import com.lounah.yarealty.domain.repository.CallHistoryRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class CallHistoryInteractorTest {

    @Mock
    lateinit var callHistoryRepository: CallHistoryRepository

    private lateinit var callInteractor: CallHistoryInteractor

    private val stubCall = StubObjectsFactory.provideStubCall()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        callInteractor = CallHistoryInteractor(callHistoryRepository)
    }


    @Test
    fun emptyResult() {
        val expected = emptyList<Call>()
        Mockito.`when`(callHistoryRepository.getCallHistory()).thenReturn(Flowable.just(expected))

        callInteractor.getCallHistory()
                .test()
                .assertValue(expected)

        Mockito.verify(callHistoryRepository).getCallHistory()
        Mockito.verifyNoMoreInteractions(callHistoryRepository)
    }

    @Test
    fun resultWithData() {
        val expected = listOf(stubCall)
        Mockito.`when`(callHistoryRepository.getCallHistory()).thenReturn(Flowable.just(expected))

        callInteractor.getCallHistory()
                .test()
                .assertValue(expected)

        Mockito.verify(callHistoryRepository).getCallHistory()
        Mockito.verifyNoMoreInteractions(callHistoryRepository)
    }

    @Test
    fun addsNewCall() {
        callInteractor.addNewCall(stubCall)

        Mockito.verify(callHistoryRepository).addNewCall(stubCall)
        Mockito.verifyNoMoreInteractions(callHistoryRepository)
    }
}