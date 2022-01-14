package ru.mipt.android_project2.presentation.main

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import ru.mipt.android_project2.R
import ru.mipt.android_project2.data.CryptoDataModel

class MainActivity : AppCompatActivity() {

    private val requestURL = "https://api.coincap.io/v2/assets"
    private var listView: ListView? = null
    internal lateinit var dataModelArrayList: ArrayList<CryptoDataModel>
    private var cryptoListAdapter: CryptoListViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lv)

        retrieveJSON()
    }

    private fun retrieveJSON() {
        showSimpleProgressDialog(this, "Loading...", "Fetching assets", true)

        val stringRequest = StringRequest(
            Request.Method.GET, requestURL,
            { response ->
                try {
                    val obj = JSONObject(response)
                    dataModelArrayList = ArrayList()
                    val dataArray = obj.getJSONArray("data")

                    for (i in 0 until dataArray.length()) {

                        val cryptoModel = CryptoDataModel()
                        val dataobj = dataArray.getJSONObject(i)

                        cryptoModel.idSet(dataobj.getString("id"))
                        cryptoModel.nameSet(dataobj.getString("name"))
                        cryptoModel.symbolSet(dataobj.getString("symbol"))
                        cryptoModel.priceSet(dataobj.getString("priceUsd"))

                        dataModelArrayList.add(cryptoModel)
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
        removeSimpleProgressDialog()
        cryptoListAdapter = CryptoListViewAdapter(this, dataModelArrayList)
        listView!!.adapter = cryptoListAdapter
    }

    companion object {
        private var mProgressDialog: ProgressDialog? = null

        fun removeSimpleProgressDialog() {
            try {
                if (mProgressDialog != null) {
                    if (mProgressDialog!!.isShowing) {
                        mProgressDialog!!.dismiss()
                        mProgressDialog = null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun showSimpleProgressDialog(
            context: Context, title: String,
            msg: String, isCancelable: Boolean
        ) {
            try {
                if (mProgressDialog == null) {
                    mProgressDialog = ProgressDialog.show(context, title, msg)
                    mProgressDialog!!.setCancelable(isCancelable)
                }
                if (!mProgressDialog!!.isShowing) {
                    mProgressDialog!!.show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
