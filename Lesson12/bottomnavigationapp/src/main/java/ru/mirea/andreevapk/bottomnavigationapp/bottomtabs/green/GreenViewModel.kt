package ru.mirea.andreevapk.bottomnavigationapp.bottomtabs.green

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mirea.andreevapk.bottomnavigationapp.R

class GreenViewModel : ViewModel() {
    private val _title = MutableLiveData<String>().apply {
        value = "Зеленый свет"
    }
    val title: LiveData<String> = _title

    private val _text = MutableLiveData<String>().apply {
        value =
            " Когда загорается зеленый свет, это означает начало фазы старта. Водители и пешеходы могут безопасно проезжать через перекресток при условии отсутствия других факторов, таких как переход пешеходов или встречное движение.\n"
    }
    val text: LiveData<String> = _text

    private val _imageResId = MutableLiveData<Int>().apply {
        value = R.drawable.green_shape
    }
    val imageResId: LiveData<Int> = _imageResId
}