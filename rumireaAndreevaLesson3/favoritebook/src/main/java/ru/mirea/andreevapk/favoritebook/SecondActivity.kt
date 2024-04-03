package ru.mirea.andreevapk.favoritebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        editText = findViewById(R.id.editTextText)
        val extras = intent.extras
        if (extras != null) {
            val ageView: TextView = findViewById(R.id.textViewBookDev)
            val university = extras.getString(MainActivity.KEY)
            ageView.text = String.format("Название Вашей любимой книги: %s", university)
        }
    }

    fun onSave(view: View) {
        val data = Intent()
        data.putExtra(MainActivity.USER_MESSAGE, editText.text.toString())
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
