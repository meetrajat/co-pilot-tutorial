package com.example.a29th_mar_android_project.countrydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R

/**
 * Adapter for displaying country details in a RecyclerView.
 * @param details List of triples of icon resource, country name, and country capital.
 */
class CountryDetailAdapter(
    private val details: List<Triple<String, String, String>>
) : RecyclerView.Adapter<CountryDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country_detail, parent, false)
        return CountryDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryDetailViewHolder, position: Int) {
        val (flagEmoji, countryName, countryCapital) = details[position]
        // Set emoji as text in a TextView instead of using ImageView
        holder.countryFlagTextView.text = flagEmoji
        holder.countryName.text = countryName
        holder.countryCapital.text = countryCapital
    }

    override fun getItemCount() = details.size
}