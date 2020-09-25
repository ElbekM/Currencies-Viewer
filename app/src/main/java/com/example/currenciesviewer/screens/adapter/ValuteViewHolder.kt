package com.example.currenciesviewer.screens.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.currenciesviewer.model.Valute
import kotlinx.android.synthetic.main.item_exchange_info.view.*

class ValuteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(valuteInfo: Valute) {
        with(itemView) {
            valuteNameTextView.text = valuteInfo.name
            valuteCharCodeValueTextView.text = valuteInfo.charCode
            valuteNumCodeValueTextView.text = valuteInfo.numCode
            valuteNominalValueTextView.text = valuteInfo.nominal
            currentRateValueTextView.text = valuteInfo.value
            deltaValueTextView.text = valuteInfo.delta
            deltaStateUpImageView.visibility = visible(valuteInfo.deltaUp)
            deltaStateDownImageView.visibility = visible(!valuteInfo.deltaUp)
        }
    }

    private fun visible(visibility: Boolean) = if (visibility) View.VISIBLE else View.GONE
}
