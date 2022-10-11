package com.example.aroundme


import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.ivnearby -> loadFragment(NearbyFragment())
                R.id.savedNewsFragment2 -> loadFragment(wishListFragment())
                else -> {
                    showToast("no fragment is found ")

                }

            }
            true
        }
    }


}