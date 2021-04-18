package com.sofit.drinkrecepies.ui.fragments.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sofit.drinkrecepies.R
import com.sofit.drinkrecepies.data.model.Drinks

class FavoritesAdapter(val list: MutableList<Drinks>) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDrinkName: TextView = itemView.findViewById(R.id.tvDrinkName)
        val tvDrinkRecepie: TextView = itemView.findViewById(R.id.tvDrinkRecepie)
        val ivDrink: ImageView = itemView.findViewById(R.id.ivDrink)
        val ivFav: ImageView = itemView.findViewById(R.id.ivFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_drinks, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.tvDrinkName.text = data.strDrink
        holder.tvDrinkRecepie.text = data.strInstructions
        Glide.with(holder.ivDrink.context)
            .load(data.strDrinkThumb)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.ivDrink)
        if (data.isFavorite) {
            holder.ivFav.setImageResource(R.drawable.ic_star_filled)
        } else {
            holder.ivFav.setImageResource(R.drawable.ic_star_empty)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<Drinks>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}