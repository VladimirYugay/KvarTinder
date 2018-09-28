package com.lounah.yarealty.api.domain

import com.lounah.yarealty.api.util.StubObjectsFactory
import com.lounah.yarealty.data.network.ComplainRequestBody
import com.lounah.yarealty.data.repository.OfferRepositoryImpl
import com.lounah.yarealty.data.repository.SettingsRepositoryImpl
import com.lounah.yarealty.domain.interactor.FlatInteractor
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FlatInteractorTest {

    @Mock
    lateinit var offerRepository: OfferRepositoryImpl

    @Mock
    lateinit var settingsRepository: SettingsRepositoryImpl

    private lateinit var interacor: FlatInteractor

    private val stubFlatViewObject = StubObjectsFactory.provideStubFlatViewObject()

    private val stubOffer = StubObjectsFactory.provideStubOffer()

    private val stubComplainVewObject = StubObjectsFactory.provideComplainViewObject()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interacor = FlatInteractor(offerRepository, settingsRepository)
    }

    @Test
    fun getsFlatById() {
        `when`(offerRepository.fetchOfferById("0")).thenReturn(Single.just(stubOffer))

        interacor.getFlatById("0")

        verify(offerRepository).fetchOfferById("0")
        verifyNoMoreInteractions(offerRepository)
    }

    @Test
    fun removesFromFavs() {
        interacor.removeFlatFromFavorite(stubOffer)


        verify(offerRepository).removeFavoriteOffer(stubOffer)
        verifyNoMoreInteractions(offerRepository)
    }

    @Test
    fun savesAsFav() {
        interacor.saveFlatAsFavorite(stubOffer)

        verify(offerRepository).saveOfferToFavorites(stubOffer)
        verifyNoMoreInteractions(offerRepository)
    }

    @Test
    fun savesFlatAsDisliked() {
        interacor.saveFlatAsDisliked(stubOffer.offerId)

        verify(offerRepository).saveOfferToDisliked(stubOffer.offerId)
        verifyNoMoreInteractions(offerRepository)
    }

    @Test
    fun savesFlatAsDisliked_byId() {
        interacor.saveFlatAsDisliked(stubOffer.offerId)

        verify(offerRepository).saveOfferToDisliked(stubOffer.offerId)
        verifyNoMoreInteractions(offerRepository)
    }

    @Test
    fun performsReverse() {
        interacor.performReverse(stubOffer)

        verify(offerRepository).performReverse(stubOffer)
        verifyNoMoreInteractions(offerRepository)
    }

    @Test
    fun complainsToOffer() {
        val complainRequestBody = ComplainRequestBody(
                stubComplainVewObject.offerId,
                stubComplainVewObject.partnerId,
                stubComplainVewObject.reason,
                stubComplainVewObject.text,
                stubComplainVewObject.uid)
        `when`(offerRepository.complainToOffer(complainRequestBody)).thenReturn(Single.just(true))

        interacor.complainToOffer(stubComplainVewObject)
                .test()
                .assertValue(true)

        verify(offerRepository).complainToOffer(complainRequestBody)
        verifyNoMoreInteractions(offerRepository)
    }
}