package ru.mirea.andreevapk.dialog


import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyProgressDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return ProgressDialog(requireActivity()).apply {
            setMessage("Загрузка...")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
        }
    }
}
