package ru.mipt.android_project2.data

class CryptoDataModel {
    var id: String? = null
    var name: String? = null
    var symbol: String? = null
    var price: String? = null

    fun idGet(): String {
        return id.toString()
    }

    fun idSet(id: String) {
        this.id = id
    }

    fun nameGet(): String {
        return name.toString()
    }

    fun nameSet(name: String) {
        this.name = name
    }

    fun symbolGet(): String {
        return symbol.toString()
    }

    fun symbolSet(symbol: String) {
        this.symbol = symbol
    }

    fun priceGet(): String {
        return price.toString()
    }

    fun priceSet(price: String) {
        this.price = price
    }

}