package ru.mirea.andreevapk.httpurlconnection

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ru.mirea.andreevapk.httpurlconnection.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private companion object {
        val TAG = MainActivity::class.java.simpleName
        const val URL_JSON = "https://ipinfo.io/json"
        const val URL_WEATHER = "https://api.openweathermap.org/data/2.5/weather"
        const val API_KEY = "26783d96cedd5e1df2a6ad6f3b28482e"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onClick(view: View?) {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (networkCapabilities != null &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ) {
            lifecycleScope.launch {
                val locationResult = downloadPage(URL_JSON)
                locationResult?.let { locationJson ->
                    val ip = locationJson.getString("ip")
                    val city = locationJson.getString("city")
                    val region = locationJson.getString("region")
                    val country = locationJson.getString("country")
                    val loc = locationJson.getString("loc")
                    val org = locationJson.getString("org")
                    val postal = locationJson.getString("postal")
                    val timezone = locationJson.getString("timezone")

                    val locationText = getString(
                        R.string.location_info,
                        ip,
                        city,
                        region,
                        country,
                        loc,
                        org,
                        postal,
                        timezone
                    )
                    binding.textLocation.text = locationText

                    val weatherResult = downloadPage("$URL_WEATHER?q=$city&appid=$API_KEY")
                    weatherResult?.let { weatherJson ->
                        val weatherDescription = weatherJson.getJSONArray("weather").getJSONObject(0).getString("description")
                        val temperature = (weatherJson.getJSONObject("main").getDouble("temp") - 273.15).toInt() // Convert Kelvin to Celsius
                        val weatherText = getString(
                            R.string.weather_info,
                            weatherDescription,
                            temperature
                        )
                        binding.textWeather.text = weatherText
                    }
                }
            }
        } else {
            Toast.makeText(this, "Нет интернета", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun downloadPage(url: String): JSONObject? {
        var result: JSONObject? = null
        var link: URL? = null
        try {
            link = URL(url)
            val resultJson = downloadInfo(link)
            Log.d(TAG, resultJson)
            result = JSONObject(resultJson)
            Log.d(TAG, "Response: $result")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    private suspend fun downloadPage(): JSONObject? {
        var result: JSONObject? = null
        var link: URL? = null
        try {
            link = URL(URL_JSON)
            val resultJson = downloadInfo(link)
            Log.d(TAG, resultJson)

            val responseJson = JSONObject(resultJson)
            Log.d(TAG, "Response: $responseJson")

            link = URL("$URL_WEATHER?q=${responseJson.getString("city")},DE&appid=$API_KEY")
            val weatherJson = downloadInfo(link)
            if (!weatherJson.contains("Error Code:")) {
                Log.d(TAG, weatherJson)
            }
            result = responseJson
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    private suspend fun downloadInfo(url: URL): String =
        withContext(Dispatchers.IO) {
            var inputStream: InputStream? = null
            var data = ""
            try {
                val connection = url.openConnection() as HttpURLConnection

                connection.readTimeout = 100000
                connection.connectTimeout = 100000
                connection.requestMethod = "GET"
                connection.instanceFollowRedirects = true
                connection.useCaches = false
                connection.doInput = true

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.inputStream
                    data = inputStream.bufferedReader().use(BufferedReader::readText)
                } else {
                    data = "${connection.responseMessage}. Error Code: $responseCode"
                }
                connection.disconnect()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream?.close()
            }
            data
        }
}