package com.lounah.yarealty.utils

import java.text.DecimalFormat

object Utils {
    private val formatter = DecimalFormat().apply {
        val symbols = decimalFormatSymbols
        symbols.groupingSeparator = ' '
        decimalFormatSymbols = symbols
    }

    fun formatIntToCostString(value: Int?): String? {
        return formatter.format(value) ?: null
    }

    fun formatIntToCostString(value: Int, currency: String): String {
        return "${formatter.format(value)} $currency"
    }

    fun formatIntToCostString(value: Long, currency: String): String {
        return "${formatter.format(value)} $currency"
    }

    fun formatDoubleToString(value: Double): String {
        return formatter.format(value)
    }

    fun formatIntToIpAddress(ip: Int): String {
        return "%d.%d.%d.%d".format(
                ip and 0xFF,
                ip shr 8 and 0xFF,
                ip shr 16 and 0xFF,
                ip shr 24 and 0xFF)
    }

    fun truncatePriceValue(value: Int): String {
        if (value % 1_000_000 == 0) {
            return "${(value / 1_000_000)}млн"
        }
        else if (value % 1000 == 0) {
            return "${(value / 1000) }тыс"
        }
        return value.toString()
    }

    fun truncateFieldValue(value: Long): String {
        if (value % 1_000_000 == 0L) {
            return "${(value / 1_000_000)}млн"
        }
        else if (value % 1000 == 0L) {
            return "${(value / 1000) }тыс"
        }
        return value.toString()
    }

    fun getValuesFromSearchFilters(textFromField: String): MutableList<String> {
        val result = mutableListOf<String>()

        if (textFromField.contains("-")) {
            var minValueAsString = textFromField.split("-")[0]
            var maxValueAsString = textFromField.split("-")[1]

            if (minValueAsString.contains("тыс")) {
                minValueAsString = minValueAsString.split("тыс")[0].trim() + "000"
            } else if (minValueAsString.contains("млн")) {
                minValueAsString = minValueAsString.split("млн")[0].trim() + "000000"
            }

            if (maxValueAsString.contains("тыс")) {
                maxValueAsString = maxValueAsString.split("тыс")[0].trim() + "000"
            } else if (maxValueAsString.contains("млн")) {
                maxValueAsString = maxValueAsString.split("млн")[0].trim() + "000000"
            }

            result.add(minValueAsString)
            result.add(maxValueAsString)

            return result
        }

        if (textFromField.contains("Неважно")) {
            result.add("0")
            result.add("0")
            return result
        }

        if (textFromField.contains("от")) {
            var maxValueAsString = "1000000000"
            var minValueAsString = "0"
            val minValueFromFieldAsString = textFromField.split("от")[1].trim()
            when {
                minValueFromFieldAsString.contains("тыс") -> {
                    maxValueAsString = "1000000000"
                    minValueAsString = minValueFromFieldAsString.split("тыс")[0].trim() + "000"
                }
                minValueFromFieldAsString.contains("млн") -> {
                    maxValueAsString = "1000000000"
                    minValueAsString = minValueFromFieldAsString.split("млн")[0].trim() + "000000"
                }
                else -> {
                    maxValueAsString = "1000000000"
                    minValueAsString = minValueFromFieldAsString
                }
            }
            result.add(minValueAsString)
            result.add(maxValueAsString)
            return result
        }

        if (textFromField.contains("до")) {
            var maxValueAsString = "1_000_000_000"
            var minValueAsString = "0"
            val maxValueFromFieldAsString = textFromField.split("до")[1].trim()
            when {
                maxValueFromFieldAsString.contains("тыс") -> {
                    minValueAsString = "0"
                    maxValueAsString = maxValueFromFieldAsString.split("тыс")[0].trim() + "000"
                }
                maxValueFromFieldAsString.contains("млн") -> {
                    minValueAsString = "0"
                    maxValueAsString = maxValueFromFieldAsString.split("млн")[0].trim() + "000000"
                }
                else -> {
                    maxValueAsString = maxValueFromFieldAsString
                    minValueAsString = "0"
                }
            }
            result.add(minValueAsString)
            result.add(maxValueAsString)
            return result
        }

        return result
    }

    fun parseDeeplinkOffers(offerIdsFromDeeplink: String): List<String>
            = offerIdsFromDeeplink.substring(offerIdsFromDeeplink.indexOfLast { it == '/' }+1, offerIdsFromDeeplink.length)
            .split("&")
}