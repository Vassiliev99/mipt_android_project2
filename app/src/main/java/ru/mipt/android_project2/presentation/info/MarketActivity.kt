package ru.mipt.android_project2.presentation.info

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ru.mipt.android_project2.R
import ru.mipt.android_project2.data.MarketDataModel
import ru.mipt.android_project2.presentation.main.MainActivity

class MarketActivity : AppCompatActivity() {
    private var requestURL = "https://api.coincap.io/v2/assets/"
    private var cryptoSymbol: String? = null

    private var listView: ListView? = null
    internal lateinit var dataModelArrayList: ArrayList<MarketDataModel>
    private var marketListAdapter: MarketListViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_market)
        val cryptoId = intent.getStringExtra("id") as String
        cryptoSymbol = intent.getStringExtra("symbol") as String
        requestURL += cryptoId + "/markets"

        val title: TextView = findViewById(R.id.infoTitle)
        title.text = cryptoSymbol

        listView = findViewById(R.id.lv_markets)

        retrieveJSON()
    }


    private fun retrieveJSON() {
        MainActivity.showSimpleProgressDialog(this, "Loading...", "Fetching markets", true)

        val stringRequest = StringRequest(
            Request.Method.GET, requestURL,
            { response ->
                try {
                    val obj = JSONObject(response)
                    dataModelArrayList = ArrayList()
                    val dataArray = obj.getJSONArray("data")

                    for (i in 0 until dataArray.length()) {

                        val marketModel = MarketDataModel()
                        val dataobj = dataArray.getJSONObject(i)

                        marketModel.nameSet(dataobj.getString("exchangeId"))
                        marketModel.priceSet(dataobj.getString("priceUsd"))
                        marketModel.volumeSet(dataobj.getString("volumePercent"))
                        marketModel.assetSet(cryptoSymbol.toString())

                        dataModelArrayList.add(marketModel)
                    }

                    setupListView()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(applicationContext, "429", Toast.LENGTH_SHORT).show()
            })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun setupListView() {
        MainActivity.removeSimpleProgressDialog()
        marketListAdapter = MarketListViewAdapter(this, dataModelArrayList)
        listView!!.adapter = marketListAdapter
    }


}