package ru.mirea.andreevapk.intentfilter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<View>(R.id.openBrowserButton).setOnClickListener {
            val address = Uri.parse("https://www.mirea.ru/")
            val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
            startActivity(openLinkIntent)
        }

        findViewById<View>(R.id.shareButton).setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val text = "MIREA\nАНДРЕЕВА ПОЛИНА КИРИЛЛОВНА"
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(shareIntent, "МОИ ФИО"))
        }
    }
}