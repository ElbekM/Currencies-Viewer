package com.example.currenciesviewer.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currenciesviewer.R
import com.example.currenciesviewer.model.Valute

class ValuteViewAdapter : RecyclerView.Adapter<ValuteViewHolder>() {

    private var items = listOf<Valute>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteViewHolder =
        ValuteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_exchange_info, parent, false)
        )

    override fun onBindViewHolder(holder: ValuteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    internal fun setValutes(valutes: List<Valute>) {
        items = valutes
        notifyDataSetChanged()
    }
}
