package hu.ait.constraintlayoutdemo

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val cityNames = arrayOf("Budapest", "Bukarest", "New York", "New Delhi", "New Orleans")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_demo)

        val cityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            cityNames
        )

        val ac = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewCities)
        ac.setAdapter(cityAdapter)


    }
}