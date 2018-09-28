package com.lounah.yarealty.api.data.repository

import com.lounah.yarealty.api.util.StubObjectsFactory
import com.lounah.yarealty.data.database.dao.DislikedOfferDao
import com.lounah.yarealty.data.database.dao.FavoriteOfferDao
import com.lounah.yarealty.data.entity.DislikedOffer
import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.data.network.NetworkSource
import com.lounah.yarealty.data.prefs.SettingsPreferences
import com.lounah.yarealty.data.repository.OfferRepositoryImpl
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class OfferRepositoryTest {

    @Mock
    private lateinit var offerDao: FavoriteOfferDao

    @Mock
    private lateinit var dislikedOfferDao: DislikedOfferDao

    @Mock
    private lateinit var api: NetworkSource

    @Mock
    private lateinit var settingsPreferences: SettingsPreferences

    private lateinit var offerRepository: OfferRepositoryImpl

    private val stubSettings = StubObjectsFactory.provideRealtySettings()
    private val stubOffer = StubObjectsFactory.provideStubOffer()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(settingsPreferences.getSettings()).thenReturn(stubSettings)
        offerRepository = OfferRepositoryImpl(offerDao, dislikedOfferDao, api, settingsPreferences)
    }

    @Test
    fun withError() {
        val expected = Throwable()
        `when`(api.getAllOffers(stubSettings)).thenReturn(Single.error(expected))

        offerRepository.fetchOffers(stubSettings)
                .test()
                .assertError(expected)

        verify(api).getAllOffers(stubSettings)
        verifyNoMoreInteractions(api)
        verifyZeroInteractions(offerDao)
        verifyZeroInteractions(dislikedOfferDao)
    }

    @Test
    fun savesOfferToFavourites() {
        offerRepository.saveOfferToFavorites(stubOffer)
                .test()
                .assertValue(true)

        verify(offerDao).insert(stubOffer)
        verifyNoMoreInteractions(offerDao)
        verifyZeroInteractions(dislikedOfferDao)
    }

    @Test
    fun savesOfferToDisliked() {
        offerRepository.saveOfferToDisliked(stubOffer.offerId)
                .test()
                .assertValue(true)

        verify(dislikedOfferDao).insert(DislikedOffer(stubOffer.offerId))
        verifyNoMoreInteractions(dislikedOfferDao)
        verifyZeroInteractions(offerDao)
    }

    @Test
    fun fetchesFavouriteOffers_emptyList() {
        val expected = emptyList<Offer>()
        `when`(offerDao.getOffers()).thenReturn(Flowable.just(expected))

        offerRepository.fetchFavoriteOffers()
                .test()
                .assertValue(expected)

        verify(offerDao).getOffers()
        verifyNoMoreInteractions(offerDao)
        verifyZeroInteractions(dislikedOfferDao)
    }

    @Test
    fun fetchesFavouriteOffers_withResult() {
        val expected = listOf(stubOffer)
        `when`(offerDao.getOffers()).thenReturn(Flowable.just(expected))

        offerRepository.fetchFavoriteOffers()
                .test()
                .assertValue { it.size == 1 }

        verify(offerDao).getOffers()
        verifyNoMoreInteractions(offerDao)
        verifyZeroInteractions(dislikedOfferDao)
    }

    @Test
    fun fetchesFavouriteOffers_withError() {
        val expected = Throwable()
        `when`(offerDao.getOffers()).thenReturn(Flowable.error(expected))

        offerRepository.fetchFavoriteOffers()
                .test()
                .assertError(expected)

        verify(offerDao).getOffers()
        verifyNoMoreInteractions(offerDao)
        verifyZeroInteractions(dislikedOfferDao)
    }

    @Test
    fun removesFavouriteOffer() {
        offerRepository.removeFavoriteOffer(stubOffer)
                .test()
                .assertValue(true)

        verify(offerDao).delete(stubOffer)
        verify(dislikedOfferDao).insert(DislikedOffer(stubOffer.offerId))
        verifyNoMoreInteractions(offerDao)
        verifyNoMoreInteractions(dislikedOfferDao)
    }

    @Test
    fun clearsData() {
        offerRepository.clearData()
                .test()
                .assertValue(true)

        verify(offerDao).clearData()
        verify(dislikedOfferDao).clearData()
        verifyNoMoreInteractions(offerDao)
        verifyNoMoreInteractions(dislikedOfferDao)
    }

    @Test
    fun performsReverse() {
        offerRepository.performReverse(stubOffer)
                .test()
                .assertComplete()

        verify(offerDao).delete(stubOffer)
        verify(dislikedOfferDao).delete(DislikedOffer(stubOffer.offerId))
        verifyNoMoreInteractions(offerDao)
        verifyNoMoreInteractions(dislikedOfferDao)
    }
}