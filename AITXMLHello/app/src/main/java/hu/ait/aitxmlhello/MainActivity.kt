package hu.ait.aitxmlhello

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hu.ait.aitxmlhello.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    companion object
    {
        const val TAG_UI = "TAG_UI"
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()

        // R: resource = res
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        binding.btnShow.setOnClickListener {
            Log.d(TAG_UI, "Show button pressed")


            //binding.tvTime.setText("${Date(System.currentTimeMillis()).toString()}")

            val numA = binding.etNumA.text.toString().toInt()
            val numB = binding.etNumB.text.toString().toInt()

            val sum = numA + numB
            //binding.tvTime.text = getString(R.string.text_sum, sum, "Peter")
            //<string name="text_sum">Summary: %1$d %2$s</string>
            binding.tvTime.text = getString(R.string.text_sum, sum.toString())

            revealCard()

            Log.d(TAG_UI, "Calculation ready")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun revealCard() {
        val x = binding.cardView.getRight()
        val y = binding.cardView.getBottom()

        val startRadius = 0
        val endRadius = Math.hypot(binding.cardView.getWidth().toDouble(),
            binding.cardView.getHeight().toDouble()).toInt()

        val anim = ViewAnimationUtils.createCircularReveal(
            binding.cardView,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )
        //anim.setDuration(5000)

        binding.cardView.setVisibility(View.VISIBLE)
        anim.start()
    }


}