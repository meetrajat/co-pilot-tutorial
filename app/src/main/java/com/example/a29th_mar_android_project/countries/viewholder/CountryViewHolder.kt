package com.example.a29th_mar_android_project.countries.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R
import com.example.a29th_mar_android_project.countries.uidata.Country

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val countryNameTextView: TextView =
        itemView.findViewById(R.id.text_view_continent_name)

    fun bind(country: Country) {
        countryNameTextView.text = country.name
    }
}