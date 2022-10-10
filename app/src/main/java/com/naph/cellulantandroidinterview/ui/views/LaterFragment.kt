package com.naph.cellulantandroidinterview.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.naph.cellulantandroidinterview.R
import com.naph.cellulantandroidinterview.adapter.LaterForecastAdapter
import com.naph.cellulantandroidinterview.adapter.TodayForecastAdapter
import com.naph.cellulantandroidinterview.adapter.TomorrowForecastAdapter
import com.naph.cellulantandroidinterview.api.WeatherApi
import com.naph.cellulantandroidinterview.databinding.FragmentLaterBinding
import com.naph.cellulantandroidinterview.databinding.FragmentTomorrowBinding
import com.naph.cellulantandroidinterview.models.forecast.OurData
import com.naph.cellulantandroidinterview.repository.WeatherRepository
import com.naph.cellulantandroidinterview.viewmodel.WeatherViewModel
import com.naph.cellulantandroidinterview.viewmodel.WeatherViewModelProviderFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaterFragment : Fragment() {

    private lateinit var factory: WeatherViewModelProviderFactory
    lateinit var binding: FragmentLaterBinding
    private lateinit var viewModel: WeatherViewModel

    private var laterWeather = listOf<OurData>()

    lateinit var weatherAdapter: LaterForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLaterBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = WeatherApi()
        val repository = WeatherRepository(api)

        factory = WeatherViewModelProviderFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)

        weatherAdapter = LaterForecastAdapter(laterWeather)
        viewModel.getLaterForecastWeather("dubai")

        binding.laterWeatherForecast.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.setHasFixedSize(true)
        }

        binding.laterWeatherForecast.adapter = weatherAdapter

        viewModel.weatherForecastResponse.observe(viewLifecycleOwner) {
            weatherAdapter.laterList = it.list
        }
    }

}