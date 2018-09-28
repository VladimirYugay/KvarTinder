package com.lounah.yarealty.domain.model

data class RealtySettings(

        val settingsCase: SettingsCase,

        val agents: String? = null,                  // NO YES
        val areaMax: Int? = null,                    // 200
        val areaMin: Int? = null,                    // 20
        val balcony: String? = null,                 // LOGGIA
        val bathroomUnit: String? = null,            // MATCHED SEPARATED
        val buildingType: List<String>? = null,      // (multiple !) BLOCK BRICK MONOLIT MONOLIT_BRICK PANEL
        val builtYearMax: Int? = null,               // 2018
        val builtYearMin: Int? = null,               // 1950
        val category: String? = null,                // APARTMENT HOUSE LOT ROOMS
        val currency: String? = null,                // RUR
        val expectDemolition: String? = null,        // NO YES
        val expectMetro: String? = null,             // NO YES
        val floorExceptFirst: String? = null,        // NO YES
        val floorMax: Int? = null,                   // 25
        val floorMin: Int? = null,                   // 2
        val hasAgentFee: String? = null,             // NO YES
        val hasAircondition: String? = null,         // NO YES
        val hasDishwasher: String? = null,           // NO YES
        val hasElectricitySupply: String? = null,    // NO YES
        val hasFurniture: String? = null,            // NO YES
        val hasGasSupply: String? = null,            // NO YES
        val hasHeatingSupply: String? = null,        // NO YES
        val hasPark: String? = null,                 // NO YES
        val hasPhoto: String? = null,                // NO YES
        val hasPond: String? = null,                 // NO YES
        val hasRefrigerator: String? = null,         // NO YES
        val hasSewerageSupply: String? = null,       // NO YES
        val hasTelevision: String? = null,           // NO YES
        val hasWashingMachine: String? = null,       // NO YES
        val hasWaterSupply: String? = null,          // NO YES
        val houseType: List<String>? = null,         // (multiple !) DUPLEX HOUSE PARTHOUSE TOWNHOUSE
        val kitchenSpaceMax: Int? = null,            // 25
        val kitchenSpaceMin: Int? = null,            // 5
        val lastFloor: String? = null,               // NO YES
        val livingSpaceMax: Int? = null,             // 20
        val livingSpaceMin: Int? = null,             // 10
        val lotAreaMax: Int? = null,                 // 50
        val lotAreaMin: Int? = null,                 // 20
        val lotType: String? = null,                 // GARDEN IGS
        val metroTransport: String? = null,          // ON_FOOT ON_TRANSPORT
        val minFloors: Int? = null,                  // 10
        val priceMax: Int? = null,                   // 20000000
        val priceMin: Int? = null,                   // 5000
        val priceType: String? = null,               // PER_OFFER
        val rentTime: String? = null,                // LARGE SHORT
        val rgid: Long? = null,                      // 587795
        val roomsTotal: List<Int>? = null,           // (multiple !) 1 2 3 4 5 6 OPEN_PLAN PLUS_4 PLUS_7 STUDIO
        val showOnMobile: String? = null,            // YES
        val showResale: String? = null,              // NO
        val showSimilar: String? = null,             // NO
        val sort: String? = null,                    // RELEVANCE
        val timeToMetro: Int? = null,                // 5 25
        val type: String? = null,                    // RENT SELL
        val withChildren: String? = null,            // NO YES
        val withPets: String? = null,                // NO YES
        val page: Int? = 0,
        val pageSize: Int? = 10
) {

    companion object {

        @JvmStatic
        fun fromMap(map: Map<String, Any>, settingsCase: SettingsCase): RealtySettings {
            val transformer = Transformer(map)

            val agents = transformer.getStringParam("agents")
            val areaMax = transformer.getIntParam("areaMax")
            val areaMin = transformer.getIntParam("areaMin")
            val balcony = transformer.getStringParam("balcony")
            val bathroomUnit = transformer.getStringParam("bathroomUnit")
            val buildingType = transformer.getStringListParam("buildingType")
            val builtYearMax = transformer.getIntParam("builtYearMax")
            val builtYearMin = transformer.getIntParam("builtYearMin")
            val category = transformer.getStringParam("category")
            val currency = transformer.getStringParam("currency")
            val expectDemolition = transformer.getStringParam("expectDemolition")
            val expectMetro = transformer.getStringParam("expectMetro")
            val floorExceptFirst = transformer.getStringParam("floorExceptFirst")
            val floorMax = transformer.getIntParam("floorMax")
            val floorMin = transformer.getIntParam("floorMin")
            val hasAgentFee = transformer.getStringParam("hasAgentFee")
            val hasAircondition = transformer.getStringParam("hasAircondition")
            val hasDishwasher = transformer.getStringParam("hasDishwasher")
            val hasElectricitySupply = transformer.getStringParam("hasElectricitySupply")
            val hasFurniture = transformer.getStringParam("hasFurniture")
            val hasGasSupply = transformer.getStringParam("hasGasSupply")
            val hasHeatingSupply = transformer.getStringParam("hasHeatingSupply")
            val hasPark = transformer.getStringParam("hasPark")
            val hasPhoto = transformer.getStringParam("hasPhoto")
            val hasPond = transformer.getStringParam("hasPond")
            val hasRefrigerator = transformer.getStringParam("hasRefrigerator")
            val hasSewerageSupply = transformer.getStringParam("hasSewerageSupply")
            val hasTelevision = transformer.getStringParam("hasTelevision")
            val hasWashingMachine = transformer.getStringParam("hasWashingMachine")
            val hasWaterSupply = transformer.getStringParam("hasWaterSupply")
            val houseType = transformer.getStringListParam("houseType")
            val kitchenSpaceMax = transformer.getIntParam("kitchenSpaceMax")
            val kitchenSpaceMin = transformer.getIntParam("kitchenSpaceMin")
            val lastFloor = transformer.getStringParam("lastFloor")
            val livingSpaceMax = transformer.getIntParam("livingSpaceMax")
            val livingSpaceMin = transformer.getIntParam("livingSpaceMin")
            val lotAreaMax = transformer.getIntParam("lotAreaMax")
            val lotAreaMin = transformer.getIntParam("lotAreaMin")
            val lotType = transformer.getStringParam("lotType")
            val metroTransport = transformer.getStringParam("metroTransport")
            val minFloors = transformer.getIntParam("minFloors")
            val priceMax = transformer.getIntParam("priceMax")
            val priceMin = transformer.getIntParam("priceMin")
            val priceType = transformer.getStringParam("priceType")
            val rentTime = transformer.getStringParam("rentTime")
            val rgid = transformer.getLongParam("rgid")
            val roomsTotal = transformer.getIntListParam("roomsTotal")
            val showOnMobile = transformer.getStringParam("showOnMobile")
            val showResale = transformer.getStringParam("showResale")
            val showSimilar = transformer.getStringParam("showSimilar")
            val sort = transformer.getStringParam("sort")
            val timeToMetro = transformer.getIntParam("timeToMetro")
            val type = transformer.getStringParam("type")
            val withChildren = transformer.getStringParam("withChildren")
            val withPets = transformer.getStringParam("withPets")
            val page = transformer.getIntParam("page")
            val pageSize = transformer.getIntParam("pageSize")

            return RealtySettings(
                    settingsCase,
                    agents,
                    areaMax,
                    areaMin,
                    balcony,
                    bathroomUnit,
                    buildingType,
                    builtYearMax,
                    builtYearMin,
                    category,
                    currency,
                    expectDemolition,
                    expectMetro,
                    floorExceptFirst,
                    floorMax,
                    floorMin,
                    hasAgentFee,
                    hasAircondition,
                    hasDishwasher,
                    hasElectricitySupply,
                    hasFurniture,
                    hasGasSupply,
                    hasHeatingSupply,
                    hasPark,
                    hasPhoto,
                    hasPond,
                    hasRefrigerator,
                    hasSewerageSupply,
                    hasTelevision,
                    hasWashingMachine,
                    hasWaterSupply,
                    houseType,
                    kitchenSpaceMax,
                    kitchenSpaceMin,
                    lastFloor,
                    livingSpaceMax,
                    livingSpaceMin,
                    lotAreaMax,
                    lotAreaMin,
                    lotType,
                    metroTransport,
                    minFloors,
                    priceMax,
                    priceMin,
                    priceType,
                    rentTime,
                    rgid,
                    roomsTotal,
                    showOnMobile,
                    showResale,
                    showSimilar,
                    sort,
                    timeToMetro,
                    type,
                    withChildren,
                    withPets,
                    page,
                    pageSize
            )
        }
    }

    internal class Transformer(private val params: Map<String, Any>) {

        fun getStringParam(name: String): String? {
            return params[name] as String?
        }

        fun getIntParam(name: String): Int? {
            return params[name] as Int?
        }

        fun getStringListParam(name: String): List<String>? {
            return (params[name] as? Collection<String>)?.toList()
        }

        fun getIntListParam(name: String): List<Int>? {
            if (params.containsKey(name)) {
                val stringSet = params[name] as Collection<*>
                val stringList = stringSet.toList()
                if (stringList.isNotEmpty()) {
                    if (stringList[0] is Int) {
                        return stringList as? List<Int>
                    }
                    if (stringList[0] is String) {
                        return (stringList as? List<String>)?.map { it.toInt() }
                    }
                }
            }
            return null
        }

        fun getLongParam(name: String): Long? {
            return params[name].toString().toLong()
        }
    }

    fun toNullableMap(): Map<String, Any?> = mapOf(
            "settingsCase" to settingsCase,
            "agents" to agents,
            "areaMax" to areaMax,
            "areaMin" to areaMin,
            "balcony" to balcony,
            "bathroomUnit" to bathroomUnit,
            "buildingType" to buildingType,
            "builtYearMax" to builtYearMax,
            "builtYearMin" to builtYearMin,
            "category" to category,
            "currency" to currency,
            "expectDemolition" to expectDemolition,
            "expectMetro" to expectMetro,
            "floorExceptFirst" to floorExceptFirst,
            "floorMax" to floorMax,
            "floorMin" to floorMin,
            "hasAgentFee" to hasAgentFee,
            "hasAircondition" to hasAircondition,
            "hasDishwasher" to hasDishwasher,
            "hasElectricitySupply" to hasElectricitySupply,
            "hasFurniture" to hasFurniture,
            "hasGasSupply" to hasGasSupply,
            "hasHeatingSupply" to hasHeatingSupply,
            "hasPark" to hasPark,
            "hasPhoto" to hasPhoto,
            "hasPond" to hasPond,
            "hasRefrigerator" to hasRefrigerator,
            "hasSewerageSupply" to hasSewerageSupply,
            "hasTelevision" to hasTelevision,
            "hasWashingMachine" to hasWashingMachine,
            "hasWaterSupply" to hasWaterSupply,
            "houseType" to houseType,
            "kitchenSpaceMax" to kitchenSpaceMax,
            "kitchenSpaceMin" to kitchenSpaceMin,
            "lastFloor" to lastFloor,
            "livingSpaceMax" to livingSpaceMax,
            "livingSpaceMin" to livingSpaceMin,
            "lotAreaMax" to lotAreaMax,
            "lotAreaMin" to lotAreaMin,
            "lotType" to lotType,
            "metroTransport" to metroTransport,
            "minFloors" to minFloors,
            "priceMax" to priceMax,
            "priceMin" to priceMin,
            "priceType" to priceType,
            "rentTime" to rentTime,
            "rgid" to rgid,
            "roomsTotal" to roomsTotal,
            "showOnMobile" to showOnMobile,
            "showResale" to showResale,
            "showSimilar" to showSimilar,
            "sort" to sort,
            "timeToMetro" to timeToMetro,
            "type" to type,
            "withChildren" to withChildren,
            "withPets" to withPets,
            "page" to page,
            "pageSize" to pageSize
    )

    fun toMap(): Map<String, Any> = toNullableMap().filterValues { it != null } as Map<String, Any>
}