package ru.mirea.andreevapk.mireaproject

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.mirea.andreevapk.mireaproject.databinding.FragmentFileHandlingBinding
import java.io.*

class FileHandlingFragment : Fragment() {

    private var _binding: FragmentFileHandlingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileHandlingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab: FloatingActionButton = binding.fabAddRecord
        fab.setOnClickListener {
            showAddFileDialog()
        }
    }

    private fun showAddFileDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Добавить запись")
        builder.setMessage("Введите текст записи")

        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton("Сохранить") { dialog, _ ->
            val text = input.text.toString()
            saveToFile(text)
            dialog.dismiss()
        }

        builder.setNegativeButton("Отмена") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun saveToFile(text: String) {
        val filename = "my_file.txt"
        val fileContents = text.toByteArray()
        val outputStream: FileOutputStream

        try {
            outputStream = requireContext().openFileOutput(filename, Context.MODE_PRIVATE)
            outputStream.write(fileContents)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
