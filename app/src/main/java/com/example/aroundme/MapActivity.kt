package com.example.aroundme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.aroundme.Constants.AppConstants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : AppCompatActivity() {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val REQUEST_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return@OnMapReadyCallback
            }
            googleMap.isMyLocationEnabled = true
            // get the data from nearbyFragment
            var latitude = intent.getDoubleExtra(AppConstants.LATITUDE, 0.00)
            var longitude = intent.getDoubleExtra(AppConstants.LONGITUDE, 0.00)
            var name = intent.getStringExtra(AppConstants.NAME)
            var address = intent.getStringExtra(AppConstants.ADDRESS)
            var bundle = intent.extras
            if (bundle != null) {
                var imageview = bundle.getInt(AppConstants.IMAGE)
                ivImage.setImageResource(imageview)
            }

            txtItemName.setText(name)

            tvDescription.setText(address)
            val location1 = LatLng(latitude, longitude)
            googleMap.addMarker(MarkerOptions().position(location1).title(name))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 70f))

            val ivDistanceLocation = findViewById<ImageView>(R.id.ivDistanceLocation)
            ivDistanceLocation.setOnClickListener {
                val intent = Intent(this, distanceActivity::class.java)
                intent.putExtra(AppConstants.LATITUDE, latitude)
                intent.putExtra(AppConstants.LONGITUDE, longitude)
                startActivity(intent)
            }


        })
    }

    private fun fetchLocation() {
        //Helper for accessing features in Activity
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

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}