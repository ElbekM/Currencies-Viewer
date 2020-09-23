package com.example.currenciesviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currenciesviewer.base.showAllowingStateLoss
import com.example.currenciesviewer.screens.CurrencyFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchCurrencyScreen()
    }

    private fun launchCurrencyScreen() {
        CurrencyFragment
            .newInstance()
            .showAllowingStateLoss(supportFragmentManager)
    }
}
