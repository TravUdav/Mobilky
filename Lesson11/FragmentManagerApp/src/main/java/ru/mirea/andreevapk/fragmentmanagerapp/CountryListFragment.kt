package ru.mirea.andreevapk.fragmentmanagerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.mirea.andreevapk.lesson11.R

class CountryListFragment : Fragment() {
    private var viewModel: SharedViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_country_list, container, false)

        val listView = view.findViewById<ListView>(R.id.country_list)
        val countries = arrayOf("Россия", "США", "Китай", "Германия", "Италия")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, countries)
        listView.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val selectedCountry = countries[position]
                viewModel!!.selectCountry(selectedCountry)

                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_container, DetailsFragment())
                    .commit()
            }

        return view
    }
}
