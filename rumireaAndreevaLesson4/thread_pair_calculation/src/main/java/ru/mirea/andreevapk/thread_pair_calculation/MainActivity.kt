package ru.mirea.andreevapk.thread_pair_calculation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.mirea.andreevapk.thread_pair_calculation.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun areFieldsFilled(): Boolean {
        return binding.editTextPair.length() > 0 && binding.editTextMonth.length() > 0
    }

    fun onClick(view: View) {
        Thread {
            if (areFieldsFilled()) {
                try {
                    Thread.sleep(5 * 1000L)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                val pair = binding.editTextPair.text.toString().toIntOrNull()
                val month = binding.editTextMonth.text.toString().toIntOrNull()

                if (pair != null && month != null && month != 0) {

                    val i = (pair.toDouble() / month.toDouble()).roundToInt()


                    runOnUiThread { binding.textView.text = "Среднее число пар в день - $i" }
                } else {

                    runOnUiThread { binding.textView.text = "Проверьте введенные данные" }
                }
            } else {

                runOnUiThread { binding.textView.text = "Заполните все поля" }
            }
        }.start()
    }
}
