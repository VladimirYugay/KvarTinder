package com.lounah.yarealty.api.util

import com.lounah.yarealty.data.entity.*
import com.lounah.yarealty.domain.model.ComplainViewObject
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.domain.model.RealtySettings
import com.lounah.yarealty.domain.model.SettingsCase
import java.util.*

object StubObjectsFactory {
    fun provideRealtySettings() = RealtySettings(SettingsCase.BUY_APARTMENT,
            "NO",
            1000,
            0,
            "NO",
            "SEPARATED",
            listOf("BLOCK"),
            2018,
            1950,
            "APARTMENT",
            "RUR",
            "NO",
            "NO",
            "NO",
            100,
            0,
            "NO",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            "YES",
            listOf("DUPLEX"),
            1000,
            0,
            "NO",
            1000,
            0)

    fun provideStubOffer() = Offer("0",
            Area(1000.0, "SQUARE_METER"),
            Price("RUR", 1000, "SHORT", "", "", 0, "", 0, ""),
            Author("0", "", emptyList(), ""),
            Location(0, Point(0.0, 0.0, ""),
                    Metro(0, "", "", 0, 0.0, 0.0, 0, ""),
                    Highway("", 0.0),
                    Station("", 0.0), 0, 0, 0, 0, "", "", ""),
            House(false),
            Building(Improvements(false),
                    BuildingImprovementsMap(false), 2018, 0, "", "", 0, "", "", ""),
            "", "", "", "", 10, 10, emptyList(), "", "", 2.0, 0, emptyList(),
            "", 0.0, 0, "", "", "", 0)

    fun provideStubCall() = Call("0", "", "", "", Date())

    fun provideStubFlatViewObject() = FlatViewObject("0", "0", "", "", emptyList(), "", "", "", "", "", "", "", Point(0.0, 0.0, ""), emptyMap(), provideStubOffer())

    fun provideComplainViewObject() = ComplainViewObject("0", "0", "", "")
}