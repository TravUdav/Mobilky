package ru.mirea.andreevapk.buttonclicker


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var textViewStudent: TextView
    lateinit var btnWhoAmI: Button
    lateinit var btnItIsNotMe: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewStudent = findViewById(R.id.textViewStudent)
        btnWhoAmI = findViewById<Button>(R.id.btnWhoAmI)
        btnItIsNotMe = findViewById<Button>(R.id.btnItIsNotMe)
        val oclBtnWhoAmI =
            View.OnClickListener { textViewStudent.setText("Мой номер по списку № 1") }
        btnWhoAmI.setOnClickListener(oclBtnWhoAmI)
    }

    fun OK(view: View) {
        Toast.makeText(this, "Ещё один способ!", Toast.LENGTH_SHORT).show();
    }
}