package ru.mirea.andreevapk.mireaproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import ru.mirea.andreevapk.mireaproject.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var inputAgeNumber: EditText
    private lateinit var inputPhoneNumber: EditText
    private lateinit var inputCourseTitle: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputAgeNumber = binding.editGroupNumber
        inputPhoneNumber = binding.editListNumber
        inputCourseTitle = binding.editMovieTitle

        sharedPreferences = requireActivity().getSharedPreferences("info_settings", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        binding.buttonSaveSettings.setOnClickListener {
            editor.putString(AGE_NUMBER_KEY, inputAgeNumber.text.toString())
            editor.putInt(COURSE_NUMBER_KEY, inputPhoneNumber.text.toString().toInt())
            editor.putString(COURSE_TITLE_KEY, inputCourseTitle.text.toString())

            editor.apply()
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d("TAG", "${sharedPreferences.contains(AGE_NUMBER_KEY)}, ${sharedPreferences.contains(COURSE_NUMBER_KEY)}, ${sharedPreferences.contains(COURSE_TITLE_KEY)}")
        sharedPreferences.contains(AGE_NUMBER_KEY).let {
            if (it) {
                inputAgeNumber.setText(sharedPreferences.getString(AGE_NUMBER_KEY, ""))
            }
        }
        sharedPreferences.contains(COURSE_NUMBER_KEY).let {
            if (it) {
                inputPhoneNumber.setText(sharedPreferences.getInt(COURSE_NUMBER_KEY, 0).toString())
            }
        }
        sharedPreferences.contains(COURSE_TITLE_KEY).let {
            if (it) {
                inputCourseTitle.setText(sharedPreferences.getString(COURSE_TITLE_KEY, ""))
            }
        }
    }

    companion object {
        private const val AGE_NUMBER_KEY = "age_number"
        private const val COURSE_NUMBER_KEY = "course_number"
        private const val COURSE_TITLE_KEY = "course_title"
    }
}
