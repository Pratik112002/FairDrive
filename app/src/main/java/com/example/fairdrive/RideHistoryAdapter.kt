package com.example.fairdrive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RideHistoryAdapter(private val rideList: List<RideDetails>) :
    RecyclerView.Adapter<RideHistoryAdapter.RideHistoryViewHolder>() {

    class RideHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationText: TextView = itemView.findViewById(R.id.txt_location) // Location TextView
        val destinationText: TextView = itemView.findViewById(R.id.txt_destination) // Destination TextView
        val timeText: TextView = itemView.findViewById(R.id.txt_time) // Time TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideHistoryViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ride, parent, false)
        return RideHistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RideHistoryViewHolder, position: Int) {

        val ride = rideList[position]

        holder.locationText.text = ride.currentLocation
        holder.destinationText.text = ride.destination


        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        holder.timeText.text = sdf.format(Date(ride.time))
    }

    override fun getItemCount(): Int {
        return rideList.size
    }
}
