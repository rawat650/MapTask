package com.example.aroundme

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aroundme.Constants.AppConstants
import com.example.aroundme.adapter.RestaurantAdapter
import com.example.aroundme.db.RestaurantDb
//import com.example.aroundme.db.ArticleDatabase
//import com.example.aroundme.db.RestaurantViewModel
import com.example.aroundme.model.RestaurantData
import com.example.aroundme.viemodel.RestaurantViewModel
import kotlinx.android.synthetic.main.acitivity_item_view.*
import kotlinx.android.synthetic.main.fragment_nearby.*

class NearbyFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list: ArrayList<RestaurantData> = ArrayList()
        addData(list)
        var rAdapter = RestaurantAdapter(requireContext(), list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = rAdapter

        }


        val viewModel: RestaurantViewModel = ViewModelProvider(
            requireActivity(),
            factory(Repository(RestaurantDb(requireContext())))
        ).get(RestaurantViewModel::class.java)




        rAdapter.ItemClickListener(object : RestaurantAdapter.OnItemClickListener {
            override fun onItemclick(position: Int, restaurantData: RestaurantData) {
                Intent(context, MapActivity::class.java).apply {
                    putExtra(AppConstants.LATITUDE, restaurantData.latitude)
                    putExtra(AppConstants.LONGITUDE, restaurantData.longitude)
                    putExtra(AppConstants.NAME, restaurantData.name)
                    putExtra(AppConstants.IMAGE, restaurantData.image)
                    putExtra(AppConstants.ADDRESS, restaurantData.address)
                    startActivity(this)

                }
            }

            //     insert the data into wishlist
            override fun addWishList(position: Int, restaurantData: RestaurantData) {
                ivWishlist.visibility = View.GONE
                ivDarkWishlist.visibility = View.VISIBLE
                showToast("item is added into wishList")
                viewModel.insertdata(restaurantData)
            }

        })
    }
}



