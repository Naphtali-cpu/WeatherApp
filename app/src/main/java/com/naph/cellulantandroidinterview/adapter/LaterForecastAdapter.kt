package com.naph.cellulantandroidinterview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naph.cellulantandroidinterview.databinding.LaterForecastListBinding
import com.naph.cellulantandroidinterview.models.forecast.OurData

class LaterForecastAdapter(var laterList: List<OurData>) : RecyclerView.Adapter<LaterForecastAdapter.LaterHolder>() {

    class LaterHolder(val binding: LaterForecastListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: OurData) {
            binding.laterDate.text = get.dt_txt
            binding.laterStatus.text = get.weather[0].description
            binding.laterWind.text = get.wind.speed.toString()
            binding.laterPressure.text = get.main.pressure.toString()
            binding.laterHumidity.text = get.main.humidity.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LaterForecastAdapter.LaterHolder {
        val binding = LaterForecastListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return LaterHolder(binding)
    }

    override fun onBindViewHolder(holder: LaterForecastAdapter.LaterHolder, position: Int) {
        holder.bind(laterList.get(position))
    }

    override fun getItemCount(): Int {
        return laterList.size
    }
}