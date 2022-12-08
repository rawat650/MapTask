package com.example.aroundme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aroundme.R
import com.example.aroundme.model.RestaurantData
import kotlinx.android.synthetic.main.acitivity_item_view.view.*
import kotlinx.android.synthetic.main.acitivity_item_view.view.ivLocation
import kotlinx.android.synthetic.main.activity_map.view.*

class RestaurantAdapter( val context: Context, val list: ArrayList<RestaurantData>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    // view binding
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        val txtItemName = itemView.findViewById(R.id.txtItemName) as TextView
        val tvDescription = itemView.findViewById(R.id.tvDescription) as TextView
        val ivLocation = itemView.findViewById(R.id.ivLocation) as ImageView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.acitivity_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resData = list[position]   // get the data from the list
        holder.apply {
            ivImage.setImageResource(resData.image)
            txtItemName.setText(resData.name)
            tvDescription.setText(resData.address)
            ivLocation.setOnClickListener() {
                notifyDataSetChanged()
                mListener.onItemclick(position, restaurantData = resData)

            }
            itemView.ivWishlist.setOnClickListener {
                mListener.addWishList(position, resData)
            }


        }

    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onItemclick(position: Int, restaurantData: RestaurantData)
        fun addWishList(position: Int, restaurantData: RestaurantData)

    }

    fun ItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


}














