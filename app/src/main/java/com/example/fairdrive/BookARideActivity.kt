package com.example.fairdrive

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fairdrive.databinding.ActivityBookArideBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class BookARideActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityBookArideBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var geocoder: Geocoder
    private lateinit var auth:FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookArideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        geocoder = Geocoder(this, Locale.getDefault())

        binding.btnSearch.setOnClickListener {
            val locationName = binding.txtLocation.text.toString()
            searchLocation(locationName)

            binding.btnBook.setOnClickListener {
                val currentLocation = "Akola"
                val destinationLocation = binding.txtLocation.text.toString()
                val currentTime = System.currentTimeMillis()

                saveRideDetailsToFirestore(currentLocation, destinationLocation, currentTime)

            }
        }
    }

    private fun saveRideDetailsToFirestore(currentLocation: String, destination: String, time: Long) {
        val rideData = HashMap<String, Any>()
        rideData["currentLocation"] = "Pune"
        rideData["destination"] = destination
        rideData["time"] = time

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            firestore.collection("users")
                .document(userId)
                .collection("rides")
                .add(rideData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Ride booked successfully!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to book ride: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun searchLocation(locationName: String) {
        if (locationName.isEmpty()) {
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val addresses = withContext(Dispatchers.IO) {
                    geocoder.getFromLocationName(locationName, 1)
                }

                if (addresses != null && addresses.isNotEmpty()) {
                    val location = addresses[0]
                    val latLng = LatLng(location.latitude, location.longitude)

                    googleMap.addMarker(MarkerOptions().position(latLng).title(locationName))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))


                    val currentLocation = LatLng(18.5, 73.8)
                    val distance = calculateDistance(currentLocation, latLng)
                     binding.txtDistance.text = "Distance: %.2f km".format(distance)
                } else {
                    Toast.makeText(this@BookARideActivity, "Location not found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@BookARideActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateDistance(start: LatLng, end: LatLng): Float {
        val startLocation = Location("start")
        startLocation.latitude = start.latitude
        startLocation.longitude = start.longitude

        val endLocation = Location("end")
        endLocation.latitude = end.latitude
        endLocation.longitude = end.longitude

        val distanceInMeters = startLocation.distanceTo(endLocation)
        return distanceInMeters / 1000
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val location = LatLng(18.5, 73.8)
        googleMap.addMarker(MarkerOptions().position(location).title("Marker in Pune"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }
}
