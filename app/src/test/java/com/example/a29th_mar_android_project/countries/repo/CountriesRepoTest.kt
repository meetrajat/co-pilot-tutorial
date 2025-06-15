package com.example.a29th_mar_android_project.countries.repo

import com.example.a29th_mar_android_project.countries.uidata.Country
import com.example.a29th_mar_android_project.network.countries.network.CountriesNetworkService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CountriesRepoTest {

    @Mock
    private lateinit var networkService: CountriesNetworkService
    private lateinit var countriesRepo: CountriesRepo

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        countriesRepo = CountriesRepo(networkService)
    }

    @JvmName("getCountriesByContinent_should_return_list_of_countries")
    @Test
    fun `getCountriesByContinent should return list of countries`() {
        runBlocking {
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
    }
}