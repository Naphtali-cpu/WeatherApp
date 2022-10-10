package com.naph.cellulantandroidinterview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naph.cellulantandroidinterview.databinding.TodayForecastListBinding
import com.naph.cellulantandroidinterview.models.forecast.OurData

class TodayForecastAdapter(var todayList: List<OurData>) : RecyclerView.Adapter<TodayForecastAdapter.TodayHolder>() {

    class TodayHolder(val binding: TodayForecastListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: OurData) {
            binding.todayDate.text = get.dt_txt
            binding.todayStatus.text = get.weather[0].description
            binding.todayWind.text = get.wind.speed.toString()
            binding.todayPressure.text = get.main.pressure.toString()
            binding.todayHumidity.text = get.main.humidity.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodayForecastAdapter.TodayHolder {
        val binding = TodayForecastListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TodayHolder(binding)
    }

    override fun onBindViewHolder(holder: TodayForecastAdapter.TodayHolder, position: Int) {
        holder.bind(todayList.get(position))
    }

    override fun getItemCount(): Int {
        return todayList.size
    }
}