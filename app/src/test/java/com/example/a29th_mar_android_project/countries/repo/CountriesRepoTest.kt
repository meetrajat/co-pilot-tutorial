// File: CountriesRepoTest.kt
package com.example.a29th_mar_android_project.countries.repo

import com.example.a29th_mar_android_project.countries.uidata.Country
import com.example.a29th_mar_android_project.network.countries.network.CountriesNetworkService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class CountriesRepoTest {

    private lateinit var networkService: CountriesNetworkService
    private lateinit var countriesRepo: CountriesRepo

    @Before
    fun setUp() {
        networkService = mock(CountriesNetworkService::class.java)
        countriesRepo = CountriesRepo(networkService)
    }

    @Test
    fun `getCountriesByContinent should return list of countries on success`() = runBlocking {
        // Arrange
        val continentCode = "EU"
        val mockCountries = mutableListOf(Country("France"), Country("Germany"))
        `when`(networkService.fetchCountriesByContinent(continentCode)).thenReturn(mockCountries)

        // Act
        val result = countriesRepo.getCountriesByContinent(continentCode)

        // Assert
        assertEquals(mockCountries, result)
        verify(networkService).fetchCountriesByContinent(continentCode)
    }

    @Test
    fun `getCountriesByContinent should throw exception on failure`() = runBlocking {
        // Arrange
        val continentCode = "EU"
        val errorMessage = "Network error"
        `when`(networkService.fetchCountriesByContinent(continentCode)).thenThrow(RuntimeException(errorMessage))

        // Act & Assert
        val exception = assertThrows(RuntimeException::class.java) {
            runBlocking {
                countriesRepo.getCountriesByContinent(continentCode)
            }
        }
        assertEquals(errorMessage, exception.message)
        verify(networkService).fetchCountriesByContinent(continentCode)
    }
}