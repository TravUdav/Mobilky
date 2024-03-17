package com.example.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MyTimeDialogFragment : DialogFragment() {
    private var time = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            requireActivity(),
            { view, hour, minute -> onTimeSet(view, hour, minute) },
            hour,
            minute,
            DateFormat.is24HourFormat(requireActivity())
        )
    }

    private fun onTimeSet(view: TimePicker, hour: Int, minute: Int) {
        time = "$hour:$minute"
        val rootView = requireActivity().window.decorView.findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, "Выбрано $time", Snackbar.LENGTH_LONG).show()
    }
}
