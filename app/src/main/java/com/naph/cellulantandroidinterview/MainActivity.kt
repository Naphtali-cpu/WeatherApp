package com.naph.cellulantandroidinterview

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.naph.cellulantandroidinterview.adapter.ViewPagerAdapter
import com.naph.cellulantandroidinterview.databinding.ActivityMainBinding
import com.naph.cellulantandroidinterview.util.Constants.Companion.SEARCH_WEATHER_TIME_DELAY
import com.naph.cellulantandroidinterview.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.time.Instant
import java.time.ZoneId

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var job: Job? = null

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)


        binding.searchWeather.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.weatherResponse.observe(this) { weather ->
                    binding.apply {
                        cityName.text = weather.name
                        val cloudvalue = weather.weather

                        cloudData.text = cloudvalue[0].description
//                        temperature.text = weather.main.temp.toString()
                        temperature.text = convertToCelcius(weather.main.temp).toString()
                        windData.text = weather.wind.speed.toString()
                        pressureData.text = weather.main.pressure.toString()
                        humidityData.text = weather.main.humidity.toString()
                        sunriseData.text = convertTimeStampToLocalData(weather.sys.sunrise.toLong())
                        sunsetData.text = convertTimeStampToLocalData(weather.sys.sunset.toLong())
                    }
                }
                val view = this.currentFocus
                if (view != null) {
                    val input: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    input.hideSoftInputFromWindow(view.windowToken, 0)
                    binding.searchWeather.clearFocus()
                }
                true
            } else false
        }



    }

    private fun convertToCelcius(temp: Double): Double {
        var intTemp = temp
        intTemp = intTemp.minus(273)
        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertTimeStampToLocalData(timeStamp: Long): String {
        val localTime = timeStamp.let {
            Instant.ofEpochSecond(it)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
        }
        return localTime.toString()
    }
}