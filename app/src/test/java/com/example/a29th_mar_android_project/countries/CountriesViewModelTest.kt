// File: CountriesViewModelTest.kt
package com.example.a29th_mar_android_project.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.a29th_mar_android_project.countries.repo.CountriesRepo
import com.example.a29th_mar_android_project.countries.uidata.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CountriesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // For LiveData testing

    @Mock
    private lateinit var mockRepo: CountriesRepo

    private lateinit var viewModel: CountriesViewModel

    @Mock
    private lateinit var countriesObserver: Observer<List<Country>>

    @Mock
    private lateinit var errorObserver: Observer<String>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CountriesViewModel(mockRepo)
        viewModel.countriesLiveData.observeForever(countriesObserver)
        viewModel.errorLiveData.observeForever(errorObserver)
    }

    @Test
    fun `fetchCountriesByContinent should update countriesLiveData on success`() = runBlockingTest {
        // Arrange
        val mockCountries = mutableListOf(Country("France"), Country("Germany"))
        `when`(mockRepo.getCountriesByContinent("EU")).thenReturn(mockCountries)

        // Act
        viewModel.fetchCountriesByContinent("EU")

        // Assert
        verify(countriesObserver).onChanged(mockCountries)
        verify(errorObserver, never()).onChanged(anyString())
    }

    @Test
    fun `fetchCountriesByContinent should update errorLiveData on failure`() = runBlockingTest {
        // Arrange
        val errorMessage = "Network error"
        `when`(mockRepo.getCountriesByContinent("EU")).thenThrow(RuntimeException(errorMessage))

        // Act
        viewModel.fetchCountriesByContinent("EU")

        // Assert
        verify(errorObserver).onChanged(errorMessage)
        verify(countriesObserver, never()).onChanged(anyList())
    }
}