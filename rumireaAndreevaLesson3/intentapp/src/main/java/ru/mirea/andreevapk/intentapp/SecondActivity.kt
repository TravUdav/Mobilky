package ru.mirea.andreevapk.intentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.math.pow

class SecondActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val number = 1
        val squareValue = number.pow(2)
        val currentTime = intent.getStringExtra("date") ?: ""

        val text = "КВАДРАТ ЗНАЧЕНИЯ\n" +
                "МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ $squareValue, " +
                "а текущее время $currentTime"

        textView = findViewById(R.id.textView)
        textView.text = text
    }

    private fun Int.pow(exponent: Int): Int = this.toDouble().pow(exponent).toInt()
}
