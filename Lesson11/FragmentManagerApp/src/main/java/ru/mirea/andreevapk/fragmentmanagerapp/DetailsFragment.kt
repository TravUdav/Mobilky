package ru.mirea.andreevapk.fragmentmanagerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.mirea.andreevapk.lesson11.R

class DetailsFragment : Fragment() {
    private var viewModel: SharedViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        val textView = view.findViewById<TextView>(R.id.country_details)

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        viewModel!!.getSelectedCountry().observe(viewLifecycleOwner) { value -> textView.text = "Вы выбрали: $value" }

        return view
    }
}