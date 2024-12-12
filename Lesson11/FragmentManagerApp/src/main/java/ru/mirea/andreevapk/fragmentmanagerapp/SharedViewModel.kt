package ru.mirea.andreevapk.fragmentmanagerapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val selectedCountry = MutableLiveData<String>()

    fun selectCountry(country: String) {
        selectedCountry.value = country
    }

    fun getSelectedCountry(): LiveData<String> {
        return selectedCountry
    }
}
