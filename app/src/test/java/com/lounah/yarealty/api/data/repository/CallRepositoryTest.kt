package com.lounah.yarealty.api.data.repository

import com.lounah.yarealty.api.util.StubObjectsFactory
import com.lounah.yarealty.data.database.dao.CallsDao
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.data.repository.CallHistoryRepositoryImpl
import com.lounah.yarealty.domain.repository.CallHistoryRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class CallRepositoryTest {

    @Mock
    lateinit var callsDao: CallsDao

    private lateinit var callHistoryRepository: CallHistoryRepository

    private val stubCall = StubObjectsFactory.provideStubCall()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        callHistoryRepository = CallHistoryRepositoryImpl(callsDao)
    }

    @Test
    fun emptyResult() {
        val expected = emptyList<Call>()
        `when`(callsDao.getCallHistory()).thenReturn(Flowable.just(expected))

        callHistoryRepository.getCallHistory()
                .test()
                .assertValue(expected)

        verify(callsDao).getCallHistory()
        verifyNoMoreInteractions(callsDao)
    }

    @Test
    fun resultWithData() {
        val expected = listOf(stubCall)
        `when`(callsDao.getCallHistory()).thenReturn(Flowable.just(expected))

        callHistoryRepository.getCallHistory()
                .test()
                .assertValue(expected)

        verify(callsDao).getCallHistory()
        verifyNoMoreInteractions(callsDao)
    }

    @Test
    fun addsNewCall() {
        callHistoryRepository.addNewCall(stubCall)
                .test()

        verify(callsDao).insert(stubCall)
        verifyNoMoreInteractions(callsDao)
    }
}