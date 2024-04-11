package ru.mirea.andreevapk.rumireaandreevalesson4

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.andreevapk.rumireaandreevalesson4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextMirea.setText("Мой номер по списку №") //1
        binding.buttonMirea.setOnClickListener {
            Log.d(MainActivity::class.simpleName, "onClickListener")
        }
    }
}
