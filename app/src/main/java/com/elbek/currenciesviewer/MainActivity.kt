package com.elbek.currenciesviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.elbek.currenciesviewer.base.showAllowingStateLoss
import com.elbek.currenciesviewer.screens.CurrencyFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this).apply { fitsSystemWindows = true })

        launchCurrencyScreen()
    }

    private fun launchCurrencyScreen() {
        CurrencyFragment
            .newInstance()
            .showAllowingStateLoss(supportFragmentManager)
    }
}
