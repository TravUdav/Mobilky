package ru.mirea.andreevapk.favoritebook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var textViewUserBook: TextView

    private var name = "Не указано"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewUserBook = findViewById(R.id.textViewBook)

        val callback = ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val userBook = data?.getStringExtra(USER_MESSAGE)
                name = userBook ?: name
                textViewUserBook.text = "Название Вашей любимой книги: $userBook"
            }
        }

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            callback
        )
    }

    fun onGetInfoAboutBook(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(KEY, name)
        activityResultLauncher.launch(intent)
    }

    companion object {
        const val KEY = "book_name"
        const val USER_MESSAGE = "MESSAGE"
    }
}
