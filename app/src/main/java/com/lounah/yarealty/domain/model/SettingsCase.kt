package com.lounah.yarealty.domain.model

/**
 * Здесь будут находиться типы настроек для разных случаев поиска и их дефолтные параметры
 */
enum class SettingsCase constructor(val settingCode: String, val defaultParams: Map<String, Any>) {

    BUY_APARTMENT("0_0", mapOf(
//            "showResale" to "NO",
//            "showSimilar" to "NO",
//            "currency" to "RUR",
            "type" to "SELL",
            "category" to "APARTMENT",
//            "sort" to "RELEVANCE",
//            "minFloors" to 0,
//            "areaMin" to 0,
//            "areaMax" to 100000,
//            "expectDemolition" to "NO",
//            "buildingType" to listOf("BLOCK", "BRICK", "PANEL", "MONOLIT", "MONOLIT_BRICK"),
//            "livingSpaceMax" to 100000,
//            "metroTransport" to "ON_FOOT",
//            "bathroomUnit" to "SEPARATED",
//            "roomsTotal" to listOf<Int>(2, 3, 4, 5, 6),
//            "kitchenSpaceMax" to 10000,
//            "floorExceptFirst" to "NO",
//            "agents" to "NO",
//            "floorMin" to 0,
//            "floorMax" to 10000,
//            "builtYearMin" to 1950,
//            "livingSpaceMin" to 0,
//            "builtYearMax" to 2018,
//            "hasPhoto" to "YES",
//            "kitchenSpaceMin" to 0,
//            "lastFloor" to "NO",
//            "priceType" to "PER_OFFER",
            "rgid" to 741964L,
//            "priceMin" to 1000,
//            "priceMax" to 100000000,
            "page" to 0,
            "pageSize" to 15
    )),

    BUY_ROOM("0_1", mapOf(
//            "showResale" to "NO",
//            "showSimilar" to "NO",
//            "currency" to "RUR",
            "type" to "SELL",
            "category" to "ROOMS",
//            "sort" to "RELEVANCE",
//            "timeToMetro" to 0,
//            "buildingType" to listOf("BRICK", "PANEL", "BLOCK", "MONOLIT", "MONOLIT_BRICK"),
//            "roomsTotal" to listOf(1, 2, 3), // todo STUDIO, OPEN_PLAN
//            "floorExceptFirst" to "NO",
//            "agents" to "NO",
//            "floorMin" to 0,
//            "builtYearMin" to 1950,
//            "builtYearMax" to 2018,
//            "kitchenSpaceMin" to 0,
//            "livingSpaceMin" to 0,
//            "livingSpaceMax" to 100000,
//            "minFloors" to 1,
//            "hasPhoto" to "YES",
//            "floorMax" to 1000,
//            "priceMin" to 1,
//            "priceType" to "PER_OFFER",
//            "kitchenSpaceMax" to 10000,
            "rgid" to 741964L,
//            "priceMin" to 0,
//            "priceMax" to 10000000,
            "page" to 0,
            "pageSize" to 15
    )),

    BUY_HOUSE("0_2", mapOf(
//            "showResale" to "NO",
//            "showSimilar" to "NO",
//            "currency" to "RUR",
            "type" to "SELL",
            "category" to "HOUSE",
//            "sort" to "RELEVANCE",
//            "lotAreaMin" to 1,
//            "areaMin" to 0,
//            "houseType" to listOf("DUPLEX", "HOUSE", "PARTHOUSE", "TOWNHOUSE"),
//            "areaMax" to 1500,
//            "lotAreaMax" to 1500,
//            "hasPhoto" to "YES",
//            "priceType" to "PER_OFFER",
//            "agents" to "NO",
            "rgid" to 741964L,
//            "priceMin" to 1000,
//            "priceMax" to 100000000,
            "page" to 0,
            "pageSize" to 15
    )),

    BUY_LOT("0_3", mapOf(
//            "showResale" to "NO",
//            "showSimilar" to "NO",
//            "currency" to "RUR",
            "type" to "SELL",
            "category" to "LOT",
//            "sort" to "RELEVANCE",
//            "lotAreaMin" to 0,
//            "lotType" to "IGS",
//            "priceType" to "PER_OFFER",
//            "lotAreaMax" to 500000,
            "rgid" to 741964L,
//            "priceMin" to 0,
//            "priceMax" to 100000000,
            "page" to 0,
            "pageSize" to 15
    )),

    RENT_APARTMENT("1_0", mapOf(
//            "showResale" to "NO",
//            "showSimilar" to "NO",
//            "currency" to "RUR",
            "type" to "RENT",
            "category" to "APARTMENT",
//            "sort" to "RELEVANCE",
//            "areaMin" to 0,
//            "areaMax" to 15000,
//            "kitchenSpaceMin" to 0,
//            "kitchenSpaceMax" to 3000,
//            "floorMin" to 0,
//            "builtYearMin" to 1959,
//            "builtYearMax" to 2018,
//            "floorMax" to 1000,
//            "withChildren" to "NO",
//            "hasDishwasher" to "NO",
//            "hasTelevision" to "NO",
//            "hasAircondition" to "NO",
//            "priceType" to "PER_OFFER",
            "rgid" to 741964L,
//            "agents" to "NO",
//            "withPets" to "NO",
//            "hasRefrigerator" to "NO",
//            "hasWashingMachine" to "NO",
//            "hasAgentFee" to "NO",
//            "hasFurniture" to "YES",
            "rgid" to 587795,
//            "priceMin" to 0,
//            "priceMax" to 100000000,
            "page" to 0,
            "pageSize" to 15
    )),

    RENT_ROOM("1_1", mapOf(
//            "showResale" to "NO",
//            "showSimilar" to "NO",
//            "currency" to "RUR",
            "type" to "RENT",
            "category" to "ROOMS",
//            "livingSpaceMin" to 0,
//            "livingSpaceMax" to 50000,
//            "sort" to "RELEVANCE",
//            "floorMin" to 1,
//            "floorMax" to 1000,
//            "kitchenSpaceMax" to 25000,
//            "priceMax" to 200000000,
//            "builtYearMin" to 1950,
//            "rentTime" to "SHORT",
//            "builtYearMax" to 2018,
//            "hasDishwasher" to "NO",
//            "hasTelevision" to "NO",
//            "hasAircondition" to "NO",
//            "withChildren" to "NO",
//            "hasRefrigerator" to "NO",
//            "hasWashingMachine" to "NO",
//            "hasAgentFee" to "NO",
//            "hasFurniture" to "YES",
//            "minFloors" to 0,
//            "lastFloor" to "NO",
//            "kitchenSpaceMin" to 0,
//            "priceMin" to 0,
//            "priceType" to "PER_OFFER",
            "rgid" to 741964L,
            "page" to 0,
            "pageSize" to 15
    )),

    RENT_HOUSE("1_2", mapOf(
//            "showResale" to "NO",
//            "showSimilar" to "NO",
//            "currency" to "RUR",
            "type" to "RENT",
            "category" to "HOUSE",
//            "sort" to "RELEVANCE",
//            "areaMin" to 0,
//            "houseType" to listOf("DUPLEX", "HOUSE", "PARTHOUSE", "TOWNHOUSE"),
//            "lotAreaMin" to 0,
//            "areaMax" to 15000,
//            "hasAgentFee" to "NO",
//            "priceType" to "PER_OFFER",
//            "rentTime" to "LARGE",
//            "lotAreaMax" to 50000,
            "rgid" to 741964L,
//            "priceMin" to 0,
//            "priceMax" to 100000,
            "page" to 0,
            "pageSize" to 15
    ));

    companion object {
        fun getCaseFor(settingsName: String): SettingsCase {
            return when (settingsName) {
                "0_0" -> BUY_APARTMENT
                "0_1" -> BUY_ROOM
                "0_2" -> BUY_HOUSE
                "0_3" -> BUY_LOT
                "1_0" -> RENT_APARTMENT
                "1_1" -> RENT_ROOM
                "1_2" -> RENT_HOUSE
                else -> BUY_APARTMENT
            }
        }
    }
}