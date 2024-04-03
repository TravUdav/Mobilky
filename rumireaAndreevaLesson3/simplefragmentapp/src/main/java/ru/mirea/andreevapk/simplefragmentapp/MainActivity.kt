package ru.mirea.andreevapk.simplefragmentapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var fragment1: Fragment
    private lateinit var fragment2: Fragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment1 = FirstFragment()
        fragment2 = SecondFragment()
    }

    fun onClick(view: View) {
        fragmentManager = supportFragmentManager
        val id = view.id
        if (id == R.id.btnFirstFragment) {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment1).commit()
        } else if (id == R.id.btnSecondFragment) {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment2).commit()
        }
    }
}
