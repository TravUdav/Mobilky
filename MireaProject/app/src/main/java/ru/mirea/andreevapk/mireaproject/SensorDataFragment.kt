package ru.mirea.andreevapk.mireaproject

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SensorDataFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private lateinit var sensorDataTextView: TextView
    private lateinit var orientationTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sensor_data, container, false)

        sensorDataTextView = rootView.findViewById(R.id.sensorDataTextView)
        orientationTextView = rootView.findViewById(R.id.orientationTextView)

        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val y = it.values[1]

                val orientation = if (y > 0) {
                    "Portrait"
                } else {
                    "Landscape"
                }

                val accelerometerData = "X: ${it.values[0]}, Y: ${it.values[1]}, Z: ${it.values[2]}"
                sensorDataTextView.text = accelerometerData
                orientationTextView.text = "Device Orientation: $orientation"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        sensor?.let {
            when (accuracy) {
                SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> {
                    // Точность датчика высокая
                    Log.d("SensorDataFragment", "${sensor.name}: High accuracy")
                }
                SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> {
                    // Точность датчика средняя
                    Log.d("SensorDataFragment", "${sensor.name}: Medium accuracy")
                }
                SensorManager.SENSOR_STATUS_ACCURACY_LOW -> {
                    // Точность датчика низкая
                    Log.d("SensorDataFragment", "${sensor.name}: Low accuracy")
                }
                SensorManager.SENSOR_STATUS_UNRELIABLE -> {
                    // Датчик ненадежен
                    Log.d("SensorDataFragment", "${sensor.name}: Unreliable")
                }
                else -> {
                    // Неизвестный уровень точности
                    Log.d("SensorDataFragment", "${sensor.name}: Unknown accuracy level")
                }
            }
        }
    }
}


