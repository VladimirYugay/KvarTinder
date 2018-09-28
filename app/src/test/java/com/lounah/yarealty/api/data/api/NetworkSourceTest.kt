package com.lounah.yarealty.api.data.api

import com.lounah.yarealty.data.network.NetworkSource
import com.lounah.yarealty.data.network.RealtyApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NetworkSourceTest {

    @Mock
    lateinit var api: RealtyApi

    private lateinit var networkSource: NetworkSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        networkSource = NetworkSource(api)
    }

    @Test
    fun getsAllOffers_success() {

    }

    @Test
    fun getsAllOffers_error() {

    }

    @Test
    fun getsOfferById_success() {

    }


    @Test
    fun getsOfferById_error() {

    }
}