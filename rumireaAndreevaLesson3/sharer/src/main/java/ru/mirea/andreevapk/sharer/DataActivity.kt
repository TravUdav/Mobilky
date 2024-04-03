package ru.mirea.andreevapk.sharer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class DataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val filePickerIntent = Intent(Intent.ACTION_PICK)
        filePickerIntent.type = "*/*"

        val filePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedFileUri = result.data?.data
                    Log.d(DataActivity::class.simpleName, "Selected file URI: $selectedFileUri")
                }
            }

        filePickerLauncher.launch(filePickerIntent)
    }
}
