package com.example.a29th_mar_android_project.continent_detail

import ContinentRepo
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a29th_mar_android_project.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.continent_detail.adapter.ContinentAdapter
import com.example.a29th_mar_android_project.continent_detail.adapter.uidata.Continent
import com.example.a29th_mar_android_project.continent_detail.network.remote.ContinentRemoteDataSource
import com.example.a29th_mar_android_project.continent_detail.network.remote.ContinentRepoGQLImpl
import com.example.a29th_mar_android_project.countries.CountriesActivity
import com.example.a29th_mar_android_project.network.apollo.cache.GraphQLCacheManager

class ContinentDetailActivity : AppCompatActivity() {

    private val continentDetailViewModel : ContinentDetailViewModel by viewModels(){
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val continentRepoGQLImpl = ContinentRepoGQLImpl()
                val cacheManager = GraphQLCacheManager(this@ContinentDetailActivity);
                val continentRemoteDataSource = ContinentRemoteDataSource(continentRepoGQLImpl);
                val repo = ContinentRepo(continentRemoteDataSource,cacheManager)
                return ContinentDetailViewModel(repo) as T
            }
        }
    }
    private lateinit var progressBar: ProgressBar;
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContinentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_continent_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupProgressBar();
        observeProgressBar();
        setupRecyclerView()
        observeContinents();
        makeContientAPICall();
    }

    fun makeContientAPICall(){
        continentDetailViewModel.fetchContinent("");
    }

    fun setupProgressBar(){
        progressBar = findViewById(R.id.progress_bar_continent_detail);
    }

    fun observeProgressBar(){
        continentDetailViewModel.progressBarVisibility.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_continent_detail)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContinentAdapter(emptyList()){
            continentDetailViewModel.fetchContinent(it.name)
        }
        recyclerView.adapter = adapter

    }

    private fun observeContinents() {
        continentDetailViewModel.continentsLiveData.observe(this) { listContinents ->
            val continentList = listContinents.map { Continent(it.name,it.code) }
            adapter = ContinentAdapter(continentList){
                Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show();
                val intent = Intent(this, CountriesActivity::class.java).apply {
                    putExtra("CONTINENT_NAME", it.name)
                    putExtra("CONTINENT_CODE", it.code)
                }
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        }
    }
}