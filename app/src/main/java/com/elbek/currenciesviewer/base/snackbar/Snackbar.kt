package com.elbek.currenciesviewer.base.snackbar

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.elbek.currenciesviewer.R
import com.google.android.material.snackbar.Snackbar

class Snackbar {

    fun showMessageWithAction(view: View, context: Context, action: () -> Unit) {
        val snackbar = Snackbar.make(view, R.string.src_currency_screen_no_internet_connection, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(context, R.color.snackbar_background)
        )
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(ContextCompat.getColor(context, R.color.white))

        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .maxLines = 5
        snackbar.setAction(R.string.src_currency_screen_retry) { action() }
        snackbar.show()
    }
}
