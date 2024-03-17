package ru.mirea.andreevapk.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dialog.MyTimeDialogFragment

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
    }

    fun onClickShowDialog(view: View) {
        val dialogFragment = AlertDialogFragment()
        dialogFragment.show(supportFragmentManager, "mirea")
    }

    fun onOkClicked() {
        Toast.makeText(applicationContext, "Вы нажали кнопку \"Иду дальше\"!", Toast.LENGTH_LONG).show()
    }

    fun onCancelClicked() {
        Toast.makeText(applicationContext, "Вы нажали кнопку \"Нет\"!", Toast.LENGTH_LONG).show()
    }

    fun onNeutralClicked() {
        Toast.makeText(applicationContext, "Вы нажали кнопку \"На паузе\"!", Toast.LENGTH_LONG).show()
    }
    fun onClickShowTimePickerDialog(view: View) {
        val dialogFragment = MyTimeDialogFragment()
        dialogFragment.show(supportFragmentManager, "mirea")
    }

    fun onClickShowDatePickerDialog(view: View) {
        val dialogFragment = MyDateDialogFragment()
        dialogFragment.show(supportFragmentManager, "mirea")
    }

    fun onClickShowProgressDialog(view: View) {
        val dialogFragment = MyProgressDialogFragment()
        dialogFragment.show(supportFragmentManager, "mirea")
    }
}
