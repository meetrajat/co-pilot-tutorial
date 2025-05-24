package com.example.a29th_mar_android_project.countrydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R

/**
 * Adapter for displaying country details in a RecyclerView.
 * @param details List of pairs of icon resource and value string.
 */
class CountryDetailAdapter(
    private val details: List<Pair<Int, String>>
) : RecyclerView.Adapter<CountryDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country_detail, parent, false)
        return CountryDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryDetailViewHolder, position: Int) {
        val (iconRes, value) = details[position]
        holder.icon.setImageResource(iconRes)
        holder.value.text = value
    }

    override fun getItemCount() = details.size
}