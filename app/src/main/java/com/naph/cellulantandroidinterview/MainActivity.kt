package com.naph.cellulantandroidinterview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.naph.cellulantandroidinterview.adapter.ViewPagerAdapter
import com.naph.cellulantandroidinterview.databinding.ActivityMainBinding
import com.naph.cellulantandroidinterview.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        viewModel.weatherResponse.observe(this) { weather ->
            binding.apply {
                val cloudvalue = weather.weather

                cloudData.text = cloudvalue[0].description
                temperature.text = weather.main.temp.toString()
                windData.text = weather.wind.speed.toString()
                pressureData.text = weather.main.pressure.toString()
                humidityData.text = weather.main.humidity.toString()
            }
        }
    }
}