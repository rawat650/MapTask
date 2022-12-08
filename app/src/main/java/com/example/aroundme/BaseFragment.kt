package com.example.aroundme

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.aroundme.adapter.RestaurantAdapter
import com.example.aroundme.model.RestaurantData
import com.example.aroundme.viemodel.RestaurantViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_wish_list.*

open class BaseFragment : Fragment() {
    fun addData(list: ArrayList<RestaurantData>) {
        list.add(RestaurantData(1, getString(R.string.res_one),getString(R.string.add_one), R.drawable.restaurant1, 19.1138, 72.8646))
        list.add(RestaurantData(2, getString(R.string.res_two), getString(R.string.add_two), R.drawable.restaurant2, 18.9217, 72.8332))
        list.add(RestaurantData(3, getString(R.string.res_three), getString(R.string.add_three), R.drawable.restaurant3, 19.0777, 72.8512))
        list.add(RestaurantData(4, getString(R.string.res_four), getString(R.string.add_four), R.drawable.restaurant4, 19.0957, 72.8538))
        list.add(RestaurantData(5, getString(R.string.res_five), getString(R.string.add_five), R.drawable.restaurant5, 18.9242, 72.8331))
    }

    fun deleData(resAdapter:RestaurantAdapter,viewModel:RestaurantViewModel) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val resData = resAdapter.list[position]
                viewModel.deleteData(resData)
                view?.let { Snackbar.make(it, "deleted successfully", Snackbar.LENGTH_LONG).show() }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(ivWishlistRecyclerView)
        }
    }




    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}