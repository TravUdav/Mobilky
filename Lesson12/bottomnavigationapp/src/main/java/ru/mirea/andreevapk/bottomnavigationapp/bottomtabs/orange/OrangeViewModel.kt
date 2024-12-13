package ru.mirea.andreevapk.bottomnavigationapp.bottomtabs.orange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mirea.andreevapk.bottomnavigationapp.R

class OrangeViewModel : ViewModel() {
    private val _title = MutableLiveData<String>().apply {
        value = "Желтый свет"
    }
    val title: LiveData<String> = _title

    private val _text = MutableLiveData<String>().apply {
        value =
            "Желтый свет обычно появляется после красного света и перед зеленым светом в цикле светофора. Его цель – служить предупреждающим знаком, подготавливая водителей и пешеходов к предстоящему переходу"
    }
    val text: LiveData<String> = _text

    private val _imageResId = MutableLiveData<Int>().apply {
        value = R.drawable.orange_shape
    }
    val imageResId: LiveData<Int> = _imageResId
}