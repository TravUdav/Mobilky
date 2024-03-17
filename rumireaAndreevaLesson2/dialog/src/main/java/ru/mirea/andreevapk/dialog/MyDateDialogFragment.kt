package ru.mirea.andreevapk.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MyDateDialogFragment : DialogFragment() {
    private var date = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), { view, year, month, dayOfMonth ->
            onDateSet(view, year, month, dayOfMonth)
        }, year, month, dayOfMonth)
    }

    private fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        date = if (month < 9) {
            "$dayOfMonth/0${month + 1}/$year"
        } else {
            "$dayOfMonth/${month + 1}/$year"
        }
        val rootView = requireActivity().window.decorView.findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, "Выбрано $date", Snackbar.LENGTH_LONG).show()
    }
}
