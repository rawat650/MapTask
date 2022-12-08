package com.example.aroundme

import android.annotation.SuppressLint

import com.google.gson.Gson

import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.aroundme.Constants.AppConstants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import okhttp3.OkHttpClient
import okhttp3.Request


class distanceActivity : BaseActivity() {
    lateinit var googleMap: GoogleMap
    lateinit var currentLocation: Location
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.distance_map_layout)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }

        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val supportMapFragment =
                    (supportFragmentManager.findFragmentById(R.id.distanceFfragment) as SupportMapFragment?)
                supportMapFragment!!.getMapAsync {
                    googleMap = it
                    val currentLocation =
                        LatLng(currentLocation!!.latitude, currentLocation!!.longitude)

                    val lat = intent.getDoubleExtra(AppConstants.LATITUDE, 0.00)
                    val long = intent.getDoubleExtra(AppConstants.LONGITUDE, 0.00)
                    googleMap.addMarker(MarkerOptions().position(currentLocation))
                    val destinationLocation = LatLng(lat, long)
                    googleMap.addMarker(MarkerOptions().position(destinationLocation))
                    val urll = getDirectionURL(currentLocation, destinationLocation)
                    GetDirection(urll).execute()
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14F))
                }
            }
        }
    }

    private fun getDirectionURL(origin: LatLng, dest: LatLng): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude},&key=AIzaSyB87kPtI2lAssHeq0jlsxzBvUURzTVM6oY"
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetDirection(val url: String) :
        AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg p0: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body.toString()
            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, MapParameter::class.java)
                val path = ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size - 1)) {
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices) {
                lineoption.addAll(result[i])
                lineoption.width(15f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
                lineoption.visible(true)
            }
            googleMap.addPolyline(lineoption)
        }
    }


}


