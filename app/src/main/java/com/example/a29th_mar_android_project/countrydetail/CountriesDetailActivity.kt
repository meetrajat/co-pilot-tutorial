package com.example.a29th_mar_android_project.countrydetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R
import com.example.a29th_mar_android_project.countrydetail.network.CountryDetailNetworkImpl
import com.example.a29th_mar_android_project.countrydetail.repo.CountryDetailRepository
import com.example.a29th_mar_android_project.network.apollo.base.ApolloClientProvider

/**
 * Activity to display details of a country.
 */
/**
 * Activity to display details of a country.
 * Uses MVVM architecture and observes the ViewModel for data updates.
 */
class CountriesDetailActivity : AppCompatActivity() {

    private val viewModel: CountryDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val apolloClient = ApolloClientProvider.apolloClient
                val networkService = CountryDetailNetworkImpl(apolloClient)
                val repo = CountryDetailRepository(networkService)
                return CountryDetailViewModel(repo) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries_detail)

        val recyclerView = findViewById<RecyclerView>(R.id.rvCountryDetails)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val code = intent.getStringExtra("COUNTRY_CODE") ?: return

        viewModel.countryDetail.observe(this) { detail ->
            val details = listOf(
                Triple(detail.flag, detail.name, detail.capital)
            )
            recyclerView.adapter = CountryDetailAdapter(details)
        }

        viewModel.fetchCountryDetail(code)
    }
}