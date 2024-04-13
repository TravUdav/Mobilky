package ru.mirea.andreevapk.notebook

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.mirea.andreevapk.notebook.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSION = 100
    private var isWork = false
    private lateinit var editTextFileName: EditText
    private lateinit var editTextCitata: EditText
    private lateinit var buttonWrite: Button
    private lateinit var buttonRead: Button
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextCitata = binding.editTextCitata
        editTextFileName = binding.editTextFileName
        buttonWrite = binding.buttonWrite
        buttonRead = binding.buttonRead

        val storagePermissionStatus = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_CODE_PERMISSION
            )
        }

        buttonWrite.setOnClickListener {
            if (isExternalStorageWritable()) {
                val fileName = editTextFileName.text.toString()
                val citata = editTextCitata.text.toString()
                writeFileToExternalStorage(fileName, citata)
            }
        }

        buttonRead.setOnClickListener {
            if (isExternalStorageWritable()) {
                val fileName = editTextFileName.text.toString()
                readFileFromExternalStorage(fileName)
            }
        }
    }

    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    private fun writeFileToExternalStorage(fileName: String, citata: String) {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(path, "$fileName.txt")
        try {
            val fileOutputStream = FileOutputStream(file.absoluteFile)
            val output = OutputStreamWriter(fileOutputStream)
            output.write(citata)
            output.close()
            Toast.makeText(
                this,
                "Цитата добавлена",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Log.w("ExternalStorage", "Error writing $file", e)
        }
    }

    private fun readFileFromExternalStorage(fileName: String) {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(path, "$fileName.txt")
        try {
            val fileInputStream = FileInputStream(file.absoluteFile)
            val inputStreamReader = InputStreamReader(fileInputStream, StandardCharsets.UTF_8)
            val lines = mutableListOf<String>()
            val reader = BufferedReader(inputStreamReader)
            var line: String? = reader.readLine()
            while (line != null) {
                lines.add(line)
                line = reader.readLine()
            }
            editTextCitata.setText(lines[0])
            Log.w("ExternalStorage", "Read from file $file successful")
        } catch (e: Exception) {
            Log.w("ExternalStorage", "Read from file $file failed")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
    }
}
