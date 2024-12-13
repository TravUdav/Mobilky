package ru.mirea.andreevapk.bottomnavigationapp.bottomtabs.red

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.mirea.andreevapk.bottomnavigationapp.databinding.FragmentTabBinding

class RedFragment : Fragment() {

    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBinding.inflate(inflater, container, false)

        viewModel.title.observe(viewLifecycleOwner) { title ->
            binding.textTitle.text = title
        }

        viewModel.text.observe(viewLifecycleOwner) { text ->
            binding.textHome.text = text
        }

        viewModel.imageResId.observe(viewLifecycleOwner) { resId ->
            binding.imageHome.setImageResource(resId)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}