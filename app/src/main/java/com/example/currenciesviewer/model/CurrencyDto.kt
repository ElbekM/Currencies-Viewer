package com.example.currenciesviewer.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class CurrencyDto {
    @SerializedName("Date")
    var date: String? = null

    @SerializedName("PreviousDate")
    var previousDate: String? = null

    @SerializedName("Valute")
    var valute: JsonObject? = null

    fun toModel(): Currency =
        Currency(
            date = date ?: "Unknown",
            previousDate = previousDate ?: "Unknown",
            valute = jsonToModel(valute!!)
        )

    private fun jsonToModel(json: JsonObject): List<Valute> = json.keySet().map { key ->
        val delta = json[key].asJsonObject.getAsJsonPrimitive("Value").asDouble -
                json[key].asJsonObject.getAsJsonPrimitive("Previous").asDouble
        Valute(
            id = json[key].asJsonObject.getAsJsonPrimitive("ID").asString,
            numCode = json[key].asJsonObject.getAsJsonPrimitive("NumCode").asString,
            charCode = json[key].asJsonObject.getAsJsonPrimitive("CharCode").asString,
            nominal = json[key].asJsonObject.getAsJsonPrimitive("Nominal").asString,
            name = json[key].asJsonObject.getAsJsonPrimitive("Name").asString,
            value = json[key].asJsonObject.getAsJsonPrimitive("Value").asString,
            delta = delta.toString(),
            deltaUp = delta >= 0
        )
    }
}
