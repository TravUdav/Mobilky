package ru.mirea.andreevapk.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

import android.content.DialogInterface

class AlertDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = android.app.AlertDialog.Builder(requireActivity())
        builder.setTitle("Здравствуй, МИРЭА!")
            .setMessage("Успех близок?")
            .setIcon(R.mipmap.ic_launcher_round)
            .setPositiveButton("Иду дальше") { dialog, _ ->
                (activity as? MainActivity)?.onOkClicked()
                dialog.cancel()
            }
            .setNeutralButton("На паузе") { dialog, _ ->
                (activity as? MainActivity)?.onNeutralClicked()
                dialog.cancel()
            }
            .setNegativeButton("Нет") { dialog, _ ->
                (activity as? MainActivity)?.onCancelClicked()
                dialog.cancel()
            }
        return builder.create()
    }
}
