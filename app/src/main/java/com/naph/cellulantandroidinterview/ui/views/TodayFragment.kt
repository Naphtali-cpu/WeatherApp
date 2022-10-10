package com.naph.cellulantandroidinterview.ui.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naph.cellulantandroidinterview.R
import com.naph.cellulantandroidinterview.adapter.TodayForecastAdapter
import com.naph.cellulantandroidinterview.api.WeatherApi
import com.naph.cellulantandroidinterview.databinding.FragmentTodayBinding
import com.naph.cellulantandroidinterview.databinding.TodayForecastListBinding
import com.naph.cellulantandroidinterview.models.forecast.OurData
import com.naph.cellulantandroidinterview.repository.WeatherRepository
import com.naph.cellulantandroidinterview.viewmodel.WeatherViewModel
import com.naph.cellulantandroidinterview.viewmodel.WeatherViewModelProviderFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodayFragment : Fragment() {

    private lateinit var factory: WeatherViewModelProviderFactory
    lateinit var binding: FragmentTodayBinding
    private lateinit var viewModel: WeatherViewModel

    private var todayWeather = listOf<OurData>()

    lateinit var weatherAdapter: TodayForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTodayBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = WeatherApi()
        val repository = WeatherRepository(api)

        factory = WeatherViewModelProviderFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)

        weatherAdapter = TodayForecastAdapter(todayWeather)
        viewModel.getTodayForecastWeather("London, ca")

        binding.todayWeatherForecast.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.setHasFixedSize(true)
        }

        binding.todayWeatherForecast.adapter = weatherAdapter

        viewModel.weatherForecastResponse.observe(viewLifecycleOwner) {
            weatherAdapter.todayList = it.list
            Log.i("DATA", it.toString())
        }


    }

}