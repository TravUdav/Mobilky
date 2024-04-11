package ru.mirea.andreevapk.audiorecord

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.mirea.andreevapk.audiorecord.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "Record"
    private val REQUEST_CODE_PERMISSION = 200
    private var isWork = false
    private var isStartPlaying = true
    private var isStartRecording = true
    private lateinit var recordFilePath: String
    private lateinit var recordButton: Button
    private lateinit var playButton: Button
    private var player: MediaPlayer? = null
    private var recorder: MediaRecorder? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recordButton = binding.buttonRec
        playButton = binding.buttonPlay
        playButton.isEnabled = false
        recordFilePath = File(
            getExternalFilesDir(Environment.DIRECTORY_MUSIC),
            "/audiorecordtest.3gp"
        ).absolutePath

        val audioRecordPermissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        val storagePermissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED &&
            storagePermissionStatus == PackageManager.PERMISSION_GRANTED
        ) {
            isWork = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION
            )
        }

        recordButton.setOnClickListener {
            if (isStartRecording) {
                recordButton.text = "Остановить запись"
                playButton.isEnabled = false
                startRecording()
            } else {
                recordButton.text = "Начать запись"
                playButton.isEnabled = true
                stopRecording()
            }
            isStartRecording = !isStartRecording
        }

        playButton.setOnClickListener {
            if (isStartPlaying) {
                playButton.text = "Остановить воспроизведение"
                recordButton.isEnabled = false
                startPlaying()
            } else {
                playButton.text = "Начать воспроизведение"
                recordButton.isEnabled = true
                stopPlaying()
            }
            isStartPlaying = !isStartPlaying
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (!isWork) {
            finish()
        }
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(recordFilePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
            } catch (e: IOException) {
                Log.d(TAG, "startRecording prepare() FAIL")
            }
            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(recordFilePath)
                isLooping = true
                prepare()
                start()
            } catch (e: IOException) {
                Log.d(TAG, "startPlaying prepare() FAIL")
            }
        }
    }

    private fun stopPlaying() {
        player?.apply {
            stop()
            release()
        }
        player = null
    }
}