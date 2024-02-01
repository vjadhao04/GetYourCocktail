package com.drinks.getyourcocktail.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drinks.getyourcocktail.DetailsOnClickListener
import com.drinks.getyourcocktail.databinding.DrinkcardBinding
import com.drinks.getyourcocktail.models.CocktailData

class DrinkListRVAdapter(private val cocktailData: CocktailData, private val context: Context, private val detailsOnClickListener: DetailsOnClickListener) : RecyclerView.Adapter<DrinkListRVAdapter.DrinkViewHolder>() {
    class DrinkViewHolder(val binding: DrinkcardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val binding=DrinkcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinkViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return cocktailData.drinks.size
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        if (position!=null){
            val data=cocktailData.drinks[position]
            Glide.with(context).load(data.strDrinkThumb.toUri()).into(holder.binding.imageView)
            holder.binding.apply {
                cocktailName.text=data.strDrink
                cocktailCategory.text=data.strCategory
            }
            holder.binding.imageView.setOnClickListener {
                detailsOnClickListener.onClick(data.idDrink.toInt())
            }

        }
    }
}