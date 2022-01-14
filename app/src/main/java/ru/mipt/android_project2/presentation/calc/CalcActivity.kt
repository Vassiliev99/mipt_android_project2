package ru.mipt.android_project2.presentation.calc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import ru.mipt.android_project2.R
import java.lang.Double.parseDouble
import java.lang.Float.parseFloat

class CalcActivity : AppCompatActivity() {
    private var price: Double = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)
        val priceStr = intent.getStringExtra("price") as String
        val name = intent.getStringExtra("name") as String
        val symbol = intent.getStringExtra("asset") as String

        price = parseDouble(priceStr)
        val asset2Value: EditText = findViewById(R.id.asset2Value)
        asset2Value.setText(priceStr)

        val asset1Value: EditText = findViewById(R.id.asset1Value)
        asset1Value.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                asset2Value.setText((parseFloat(s.toString()) * price).toString())
            }
        })

        val title: TextView = findViewById(R.id.calcTitle)
        title.text = name

        val asset1Label: TextView = findViewById(R.id.asset1Label)
        asset1Label.text = symbol
        val asset2Label: TextView = findViewById(R.id.asset2Label)

        val buttonChange: Button = findViewById(R.id.buttonChange)
        buttonChange.setOnClickListener {
            if (!price.equals(0.0)) {
                price = 1 / price
            }

            val temp = asset1Label.text
            asset1Label.text = asset2Label.text
            asset2Label.text = temp

            asset2Value.setText((parseFloat(asset1Value.text.toString()) * price).toString())
        }

    }

}