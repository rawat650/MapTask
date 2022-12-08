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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            // get the data from nearbyFragment
            val latitude = intent.getDoubleExtra(AppConstants.LATITUDE, 0.00)
            val longitude = intent.getDoubleExtra(AppConstants.LONGITUDE, 0.00)
            val name = intent.getStringExtra(AppConstants.NAME)
             intent.getStringExtra(AppConstants.ADDRESS).let {
                 tvDescription.setText(it)
             }
            val bundle = intent.extras
            if (bundle != null) {
                var imageview = bundle.getInt(AppConstants.IMAGE)
                ivImage.setImageResource(imageview)
            }
            txtItemName.setText(name)


            val location1 = LatLng(latitude, longitude)
            googleMap.addMarker(MarkerOptions().position(location1).title(name))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 70f))

            val ivDistanceLocation = findViewById<ImageView>(R.id.ivDistanceLocation)
            ivDistanceLocation.setOnClickListener {
                Intent(this, distanceActivity::class.java).apply {
                    putExtra(AppConstants.LATITUDE, latitude)
                    putExtra(AppConstants.LONGITUDE, longitude)
                    startActivity(this)
                }
            }


        }
    }
}
