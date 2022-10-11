package com.example.aroundme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aroundme.adapter.RestaurantAdapter
import com.example.aroundme.db.RestaurantDb
import com.example.aroundme.model.RestaurantData
import com.example.aroundme.viemodel.RestaurantViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_wish_list.*

class wishListFragment : BaseFragment() {

    lateinit var viewModel: RestaurantViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: RestaurantViewModel = ViewModelProvider(requireActivity(), factory(Repository(RestaurantDb(requireContext())))).get(RestaurantViewModel::class.java)

        // get the  wishlist data and set in the recyclerview
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            var resAdapter = RestaurantAdapter(requireContext(), it as ArrayList<RestaurantData>)
            ivWishlistRecyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = resAdapter
              swipe(resAdapter,viewModel)


    }
    })
}
}

