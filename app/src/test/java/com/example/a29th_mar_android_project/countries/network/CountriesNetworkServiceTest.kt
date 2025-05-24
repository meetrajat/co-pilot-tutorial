package com.example.a29th_mar_android_project.countries.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.a29th_mar_android_project.CountriesByContinentQuery
import com.example.a29th_mar_android_project.network.countries.network.CountriesNetworkService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.UUID
import com.apollographql.apollo3.ApolloCall
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

class CountriesNetworkServiceTest {

    private lateinit var apolloClient: ApolloClient
    private lateinit var countriesNetworkService: CountriesNetworkService

    @Before
    fun setUp() {
        apolloClient = mock(ApolloClient::class.java)
        countriesNetworkService = CountriesNetworkService(apolloClient)
    }

@Test
fun `fetchCountriesByContinent should return list of countries on success`() = runTest {
    // Arrange
    val mockData = CountriesByContinentQuery.Data(
        CountriesByContinentQuery.Continent(
            listOf(
                CountriesByContinentQuery.Country("1", "France"),
                CountriesByContinentQuery.Country("2", "Germany")
            )
        )
    )

    val mockResponse = ApolloResponse.Builder(
        operation = CountriesByContinentQuery("EU"),
        requestUuid = UUID.randomUUID(),
        data = mockData
    ).build()

    // Create a mock ApolloCall
    val mockApolloCall = mock(ApolloCall::class.java) as ApolloCall<CountriesByContinentQuery.Data>
    `when`(mockApolloCall.execute()).thenReturn(mockResponse)

    // Mock the Apollo client query execution
    `when`(apolloClient.query(any<CountriesByContinentQuery>())).thenReturn(mockApolloCall)

    // Act
    val result = countriesNetworkService.fetchCountriesByContinent("EU")

    // Assert
    assertEquals(2, result.size)
    assertEquals("France", result[0].name)
    assertEquals("Germany", result[1].name)
}
}
