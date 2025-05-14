package com.example.a29th_mar_android_project.countries

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R
import com.example.a29th_mar_android_project.countries.adapter.CountriesAdapter
import com.example.a29th_mar_android_project.countries.repo.CountriesRepo
import com.example.a29th_mar_android_project.network.apollo.base.ApolloClientProvider
import com.example.a29th_mar_android_project.network.countries.network.CountriesNetworkService
import com.example.a29th_mar_android_project.countries.uidata.Country

class CountriesActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountriesAdapter

    //TODO:- ViewModel with manual dependency injection : Understand this code
    private val viewModel: CountriesViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val apolloClient = ApolloClientProvider.apolloClient
                val networkService = CountriesNetworkService(apolloClient)
                val repo = CountriesRepo(networkService)
                return CountriesViewModel(repo) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)

        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.recycler_view_countries)

        setupRecyclerView()

        observeViewModel()

        // Fetch countries
        viewModel.fetchCountriesByContinent("EU")
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CountriesAdapter(emptyList())
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.countriesLiveData.observe(this) { response ->
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            val countries = response.map { Country(it.name) } ?: emptyList()
            adapter.updateCountries(countries)
        }

        viewModel.errorLiveData.observe(this) { error ->
            progressBar.visibility = View.GONE
            // Handle error (e.g., show a Toast or Snackbar)
        }
    }
}