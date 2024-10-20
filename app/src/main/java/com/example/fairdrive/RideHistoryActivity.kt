package com.example.fairdrive

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RideHistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var rideHistoryAdapter: RideHistoryAdapter
    private val rideList = mutableListOf<RideDetails>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_history)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rideHistoryAdapter = RideHistoryAdapter(rideList)
        recyclerView.adapter = rideHistoryAdapter

        fetchRideHistory()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchRideHistory() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("users")
                .document(userId)
                .collection("rides")
                .get()
                .addOnSuccessListener { documents ->
                    rideList.clear()
                    for (document in documents) {
                        val ride = document.toObject(RideDetails::class.java)
                        rideList.add(ride)
                    }
                    rideHistoryAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error fetching ride history", e)
                }
        }
    }
}


