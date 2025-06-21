package com.example.a29th_mar_android_project.countrydetail

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R

/**
 * ViewHolder for displaying a single country detail attribute.
 * @param itemView The view representing a single row in the RecyclerView.
 */
class CountryDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val countryFlagTextView: TextView = itemView.findViewById(R.id.txtViewCountryFlag)
    val countryName: TextView = itemView.findViewById(R.id.txtViewCountryName)
    val countryCapital: TextView = itemView.findViewById(R.id.txtViewCountryCapital)
}