package ru.mirea.andreevapk.resultapifragmentapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, DataFragment(), "DataFragment")
                .commit()
        }

        supportFragmentManager.setFragmentResultListener(
            "requestKey",
            this
        ) { requestKey: String?, bundle: Bundle ->
            val result = bundle.getString("key")
            Log.d("MainActivity", "Результат из BottomSheetFragment: $result")
        }
    }
}