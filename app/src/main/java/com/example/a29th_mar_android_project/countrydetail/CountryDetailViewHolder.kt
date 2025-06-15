package com.example.a29th_mar_android_project.countrydetail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R

/**
 * ViewHolder for displaying a single country detail attribute.
 * @param itemView The view representing a single row in the RecyclerView.
 */
class CountryDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val icon: ImageView = itemView.findViewById(R.id.ivFlag)
    val value: TextView = itemView.findViewById(R.id.tvAttribute)
}