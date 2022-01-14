package ru.mipt.android_project2.presentation.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ru.mipt.android_project2.R
import ru.mipt.android_project2.data.CryptoDataModel
import ru.mipt.android_project2.presentation.info.MarketActivity

class CryptoListViewAdapter(private val context: Context, private val dataModelArrayList: ArrayList<CryptoDataModel>) : BaseAdapter() {
    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getCount(): Int {
        return dataModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return dataModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.crypto_list, null, true)

            convertView.setOnClickListener{
                val intent = Intent(this.context, MarketActivity::class.java)
                val elementId = dataModelArrayList[position].idGet()
                val elementSymbol = dataModelArrayList[position].symbolGet()
                intent.putExtra("id", elementId)
                intent.putExtra("symbol", elementSymbol)
                parent.context.startActivity(intent)
            }

            holder.symbol = convertView.findViewById<View>(R.id.cryptoSymbol) as? TextView
            holder.name = convertView.findViewById<View>(R.id.cryptoName) as? TextView
            holder.price = convertView.findViewById<View>(R.id.cryptoPrice) as? TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.symbol!!.text = dataModelArrayList[position].symbolGet()
        holder.name!!.text = dataModelArrayList[position].nameGet()
        holder.price!!.text = dataModelArrayList[position].priceGet().subSequence(0, 8).toString() + "$"

        return convertView
    }

    private inner class ViewHolder {

        var symbol: TextView? = null
        var name: TextView? = null
        var price: TextView? = null
    }
}