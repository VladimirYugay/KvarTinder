package com.lounah.yarealty.api.data.api

import com.lounah.yarealty.data.network.ComplainRequestBody
import com.lounah.yarealty.domain.model.RealtySettings
import com.lounah.yarealty.domain.model.SettingsCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class RealtyApiTest : ApiTest() {
    private lateinit var params: RealtySettings

    @Before
    fun init() {
        params = RealtySettings.fromMap(SettingsCase.BUY_APARTMENT.defaultParams, SettingsCase.BUY_APARTMENT)
    }

    @Test
    fun getOfferById() {
        api.getOfferById(offerId)
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertNoErrors()
                .assertValue { it.response.offerId == offerId }
    }

    @Test
    fun getAllOffer() {
        api.getAllOffers(params)
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertNoErrors()
                .assertValue { it.response.pager.totalItems > 0 }
    }

    @Test
    fun addFavoritesAndCheck() {
        api.addToFavorites(offerId)
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertNoErrors()
                .assertValue {
                    it.response.actual.contains(offerId)
                            || it.response.outdated.contains(offerId)
                            || it.response.relevant.contains(offerId)
                }
    }

    @Test
    fun complainToOffer() {
        val complainBody = ComplainRequestBody(
                "219353371468896694",
                "1035218734",
                "SPECIFIC_REASON",
                "some text",
                "4001517379")

        api.complainToOffer(complainBody)
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertNoErrors()
                .assertValue { it.response.status == "OK" }
    }
}