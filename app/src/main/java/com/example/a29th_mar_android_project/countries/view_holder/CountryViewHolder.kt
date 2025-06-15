package com.example.a29th_mar_android_project.countries.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R
import com.example.a29th_mar_android_project.countries.uidata.Country

class CountryViewHolder(itemView: View, val onItemClick: (Country) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val countryNameTextView: TextView =
        itemView.findViewById(R.id.text_view_country_name)

    fun bind(country: Country) {
        countryNameTextView.text = country.name
        itemView.setOnClickListener {
            // Handle click event, e.g., navigate to country detail
            // This can be implemented in the adapter or activity/fragment
            // For example, you might want to start a new activity with the country code
            // val intent = Intent(itemView.context, CountriesDetailActivity::class.java)
            // intent.putExtra("COUNTRY_CODE", country.code)
            // itemView.context.startActivity(intent)
            onItemClick(country)
        }
    }
}