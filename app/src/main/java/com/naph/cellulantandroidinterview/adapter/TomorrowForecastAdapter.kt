package com.naph.cellulantandroidinterview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naph.cellulantandroidinterview.databinding.TomorrowForecastListBinding
import com.naph.cellulantandroidinterview.models.forecast.OurData

class TomorrowForecastAdapter(var tomorrowList: List<OurData>) : RecyclerView.Adapter<TomorrowForecastAdapter.TomorrowHolder>() {

    class TomorrowHolder(val binding: TomorrowForecastListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: OurData) {
            binding.tomorrowDate.text = get.dt_txt
            binding.tomorrowStatus.text = get.weather[0].description
            binding.tomorrowWind.text = get.wind.speed.toString()
            binding.tomorrowPressure.text = get.main.pressure.toString()
            binding.tomorrowHumidity.text = get.main.humidity.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TomorrowForecastAdapter.TomorrowHolder {
        val binding = TomorrowForecastListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TomorrowHolder(binding)
    }

    override fun onBindViewHolder(holder: TomorrowForecastAdapter.TomorrowHolder, position: Int) {
        holder.bind(tomorrowList.get(position))
    }

    override fun getItemCount(): Int {
        return tomorrowList.size
    }
}