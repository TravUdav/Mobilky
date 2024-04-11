package ru.mirea.andreevapk.thread

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.andreevapk.thread.databinding.ActivityMainBinding
import java.util.*
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = ViewCompat.getWindowInsetsController(window.decorView)
        controller?.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())

        val infoTextView: TextView = findViewById(R.id.textView)
        val mainThread: Thread = Thread.currentThread()

        infoTextView.text = "Имя текущего потока: ${mainThread.name}"

        mainThread.name = "МОЙ НОМЕР ГРУППЫ: 11, НОМЕР ПО СПИСКУ: 1, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Автомат по предмету"
        infoTextView.append("\nНовое имя потока: ${mainThread.name}")

        Log.d(MainActivity::class.simpleName, "Stack: ${Arrays.toString(mainThread.stackTrace)}")

        val lock = Object()

        binding.buttonMirea.setOnClickListener {
            Thread {
                val numberThread = counter++
                Log.d("ThreadProject", "Запущен поток № $numberThread студентом группы БСБО-11-21 номер по списку № 1")
                val endTime = System.currentTimeMillis() + 20 * 1000
                synchronized(lock) {
                    while (System.currentTimeMillis() < endTime) {
                        try {
                            lock.wait(endTime - System.currentTimeMillis())
                            Log.d(MainActivity::class.simpleName, "Endtime: $endTime")
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
                Log.d("ThreadProject", "Выполнен поток № $numberThread")
            }.start()
        }

        Log.d(MainActivity::class.simpleName, "Group: ${mainThread.threadGroup}")
    }
}