package com.elbek.currenciesviewer.screens.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.elbek.currenciesviewer.base.utils.Utils.setVisibility
import com.elbek.currenciesviewer.model.Valute
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
            deltaStateUpImageView.visibility = setVisibility(valuteInfo.deltaUp)
            deltaStateDownImageView.visibility = setVisibility(!valuteInfo.deltaUp)
        }
    }
}
