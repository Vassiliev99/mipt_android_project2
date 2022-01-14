package ru.mipt.android_project2.data

class MarketDataModel {
    var name: String? = null
    var volume: String? = null
    var price: String? = null
    var asset: String? = null

    fun nameGet(): String {
        return name.toString()
    }

    fun nameSet(name: String) {
        this.name = name
    }

    fun volumeGet(): String {
        return volume.toString()
    }

    fun volumeSet(volume: String) {
        this.volume = volume
    }

    fun priceGet(): String {
        return price.toString()
    }

    fun priceSet(price: String) {
        this.price = price
    }

    fun assetGet(): String {
        return asset.toString()
    }

    fun assetSet(asset: String) {
        this.asset = asset
    }

}