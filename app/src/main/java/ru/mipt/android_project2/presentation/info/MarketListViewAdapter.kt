package ru.mipt.android_project2.presentation.info

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ru.mipt.android_project2.R
import ru.mipt.android_project2.data.MarketDataModel
import ru.mipt.android_project2.presentation.calc.CalcActivity

class MarketListViewAdapter(private val context: Context, private val dataModelArrayList: ArrayList<MarketDataModel>) : BaseAdapter() {
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
            convertView = inflater.inflate(R.layout.market_list, null, true)

            convertView.setOnClickListener{
                val intent = Intent(this.context, CalcActivity::class.java)
                val marketPrice = dataModelArrayList[position].priceGet()
                val marketName = dataModelArrayList[position].nameGet()
                val marketAsset = dataModelArrayList[position].assetGet()
                intent.putExtra("price", marketPrice)
                intent.putExtra("name", marketName)
                intent.putExtra("asset", marketAsset)

                parent.context.startActivity(intent)
            }

            holder.name = convertView.findViewById<View>(R.id.marketName) as? TextView
            holder.volume = convertView.findViewById<View>(R.id.marketVolume) as? TextView
            holder.price = convertView.findViewById<View>(R.id.marketPrice) as? TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.name!!.text = dataModelArrayList[position].nameGet()
        holder.volume!!.text = dataModelArrayList[position].volumeGet().subSequence(0, 4).toString() + "%"
        holder.price!!.text = dataModelArrayList[position].priceGet().subSequence(0, 8).toString() + "$"

        return convertView
    }

    private inner class ViewHolder {

        var name: TextView? = null
        var volume: TextView? = null
        var price: TextView? = null
    }

}