package com.example.a29th_mar_android_project.continent_detail.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R
import com.example.a29th_mar_android_project.continent_detail.adapter.uidata.Continent

class ContinentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val continentNameTextView: TextView =
        itemView.findViewById(R.id.text_view_continent_name)

    fun bind(continent: Continent, onItemClick: (Continent) -> Unit) {
        continentNameTextView.text = continent.name
        itemView.setOnClickListener {
            onItemClick(continent)
        }
    }
}