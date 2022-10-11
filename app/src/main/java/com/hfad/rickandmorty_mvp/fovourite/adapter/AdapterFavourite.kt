package com.hfad.rickandmorty_mvp.fovourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.rickandmorty_mvp.databinding.ItemCharacterFavouriteBinding
import com.hfad.rickandmorty_mvp.room.Model.Characters
import com.squareup.picasso.Picasso

class AdapterFavourite(var characters: ArrayList<Characters>): RecyclerView.Adapter<AdapterFavourite.ViewHolder>()  {

    inner class ViewHolder(var binding: ItemCharacterFavouriteBinding): RecyclerView.ViewHolder(binding.root){
        fun bindView(characters: Characters) {
            with(binding){
                Picasso.get().load(characters.image).into(imageView)
                nameTextView.text = characters.name
                statusTextView.text = characters.status
                speciesTextView.text = characters.species
            }
        }

 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCharacterFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }
}