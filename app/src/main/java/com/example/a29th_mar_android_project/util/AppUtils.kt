package com.example.a29th_mar_android_project.util

object AppUtils {
    const val ASIA_BUTTON_IDENTIFIER = 1001
    const val EUROPE_BUTTON_IDENTIFIER = ASIA_BUTTON_IDENTIFIER + 1
    const val NORTH_AMERICA_BUTTON_IDENTIFIER = EUROPE_BUTTON_IDENTIFIER + 1
    const val SOUTH_AMERICA_BUTTON_IDENTIFIER = NORTH_AMERICA_BUTTON_IDENTIFIER + 1
    const val AUSTRALIA_BUTTON_IDENTIFIER = SOUTH_AMERICA_BUTTON_IDENTIFIER + 1
    const val AFRICA_BUTTON_IDENTIFIER = AUSTRALIA_BUTTON_IDENTIFIER + 1

    const val REQUEST_ID_CONTINENT_DETAIL = 5001
    const val REQUEST_ID_COUNTRY_DETAIL = REQUEST_ID_CONTINENT_DETAIL + 1
    const val REQUEST_ID_COUNTRY_LIST = REQUEST_ID_COUNTRY_DETAIL + 1

    const val REQUEST_NAME_CONTINENT = "continent"
    const val REQUEST_NAME_COUNTRY = "country"
    const val REQUEST_NAME_COUNTRY_LIST = "countryList"


    fun getContinentStringCode(continentId: Int): String = when (continentId) {
        ASIA_BUTTON_IDENTIFIER -> "AS"
        EUROPE_BUTTON_IDENTIFIER -> "EU"
        NORTH_AMERICA_BUTTON_IDENTIFIER -> "NA"
        SOUTH_AMERICA_BUTTON_IDENTIFIER -> "SA"
        AUSTRALIA_BUTTON_IDENTIFIER -> "AU"
        AFRICA_BUTTON_IDENTIFIER -> "AF"
        else -> "UNKNOWN"
    }

    fun getUniqueCacheKey(requestName: String, uniqueIdentifier: String? = null): Int {
        val requestId = when (requestName) {
            REQUEST_NAME_CONTINENT -> REQUEST_ID_CONTINENT_DETAIL
            REQUEST_NAME_COUNTRY -> REQUEST_ID_COUNTRY_DETAIL
            REQUEST_NAME_COUNTRY_LIST -> REQUEST_ID_COUNTRY_LIST
            else -> -1
        }

        // If no unique identifier provided, return just the request ID
        if (uniqueIdentifier == null) {
            return requestId
        }

        // Combine request ID with hash of unique identifier
        // Use upper 16 bits for request ID and lower 16 bits for identifier hash
        val identifierHash = (uniqueIdentifier.hashCode() and 0xFFFF)
        return (requestId shl 16) or identifierHash
    }

    // Mark as deprecated to encourage use of new method
    @Deprecated("Use getUniqueCacheKey instead", ReplaceWith("getUniqueCacheKey(requestName)"))
    fun getNetworkRequestKey(requestName: String): Int = getUniqueCacheKey(requestName)

    // Mark as deprecated to encourage use of new method
    @Deprecated("Use getUniqueCacheKey instead", ReplaceWith("getUniqueCacheKey(REQUEST_NAME_COUNTRY, continentCode)"))
    fun getUniqueCountryCacheKey(continentCode: String): Int =
        getUniqueCacheKey(REQUEST_NAME_COUNTRY, continentCode)
}
