package com.example.currenciesviewer.api

import com.example.currenciesviewer.model.CurrencyDto
import io.reactivex.Single
import retrofit2.http.GET

interface ApiController {

    @GET("/daily_json.js")
    fun getCurrencyList() : Single<CurrencyDto>
}
