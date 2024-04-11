package ru.mirea.andreevapk.player

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.andreevapk.player.databinding.ActivityAudioBinding

class AudioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioBinding
    private lateinit var mediaPlayer: MediaPlayer

    private var isPlaying = false
    private var currentTrack = R.raw.music_1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, currentTrack)

        binding.playPauseButton.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.pause()
                (binding.playPauseButton as ImageButton).setImageResource(R.drawable.play)
            } else {
                mediaPlayer.start()
                (binding.playPauseButton as ImageButton).setImageResource(R.drawable.pause)
            }
            isPlaying = !isPlaying
        }

        binding.previousButton?.setOnClickListener {
            if (currentTrack == R.raw.music_1) {
                currentTrack = R.raw.music_2
            } else {
                currentTrack = R.raw.music_1
            }
            mediaPlayer.reset()
            mediaPlayer = MediaPlayer.create(this, currentTrack)
            mediaPlayer.start()
            isPlaying = true
            (binding.playPauseButton as ImageButton).setImageResource(R.drawable.pause)
        }


        binding.nextButton?.setOnClickListener {
            if (currentTrack == R.raw.music_1) {
                currentTrack = R.raw.music_2
            } else {
                currentTrack = R.raw.music_1
            }
            mediaPlayer.reset()
            mediaPlayer = MediaPlayer.create(this, currentTrack)
            mediaPlayer.start()
            isPlaying = true
            (binding.playPauseButton as ImageButton).setImageResource(R.drawable.pause)
        }

        mediaPlayer.setOnCompletionListener {
            isPlaying = false
            (binding.playPauseButton as ImageButton).setImageResource(R.drawable.pause)
            val isLastTrack = currentTrack == R.raw.music_2
            if (!isLastTrack) {
                currentTrack = if (currentTrack == R.raw.music_1) R.raw.music_2 else R.raw.music_1
                mediaPlayer.reset()
                mediaPlayer = MediaPlayer.create(this, currentTrack)
                mediaPlayer.start()
                isPlaying = true
                (binding.playPauseButton as ImageButton).setImageResource(R.drawable.pause)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
