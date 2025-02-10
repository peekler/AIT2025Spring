package hu.ait.aitxmlhello

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hu.ait.aitxmlhello.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()

        // R: resource = res
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        binding.btnShow.setOnClickListener {
            //binding.tvTime.setText("${Date(System.currentTimeMillis()).toString()}")

            val numA = binding.etNumA.text.toString().toInt()
            val numB = binding.etNumA.text.toString().toInt()
            val sum = numA + numB
            binding.tvTime.text = "Sum: $sum"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}