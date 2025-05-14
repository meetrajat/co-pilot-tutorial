package com.example.a29th_mar_android_project.continent_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a29th_mar_android_project.R
import com.example.a29th_mar_android_project.continent_detail.adapter.uidata.Continent

class ContinentAdapter(
    private val continentList: List<Continent>,
    private val onItemClick: (Continent) -> Unit
) : RecyclerView.Adapter<ContinentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContinentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.continent_detail_item, parent, false)
        return ContinentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContinentViewHolder, position: Int) {
        val continent = continentList[position]
        holder.bind(continent, onItemClick)
    }

    override fun getItemCount(): Int = continentList.size
}