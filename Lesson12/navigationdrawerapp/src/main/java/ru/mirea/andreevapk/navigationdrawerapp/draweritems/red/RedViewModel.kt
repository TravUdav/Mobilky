package ru.mirea.andreevapk.navigationdrawerapp.draweritems.red

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mirea.andreevapk.navigationdrawerapp.R

class RedViewModel : ViewModel() {
    private val _title = MutableLiveData<String>().apply {
        value = "Красный свет"
    }
    val title: LiveData<String> = _title

    private val _text = MutableLiveData<String>().apply {
        value =
            "Красный свет стратегически расположен в верхней части светофора, чтобы обеспечить максимальную видимость. Он действует как четкий сигнал для всех на дороге, указывая на то, что им следует терпеливо ждать, пока он не перейдет в другой цвет."

    }
    val text: LiveData<String> = _text

    private val _imageResId = MutableLiveData<Int>().apply {
        value = R.drawable.red_shape
    }
    val imageResId: LiveData<Int> = _imageResId
}