package com.hfad.rickandmorty_mvp.characters.impl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.rickandmorty_mvp.databinding.ItemCharactersBinding
import com.hfad.rickandmorty_mvp.databinding.ItemLoadingBinding
import com.hfad.rickandmorty_mvp.databinding.ItemNoInternetBinding
import com.hfad.rickandmorty_mvp.model.Character
import com.squareup.picasso.Picasso

class AdapterCharacters(
    var _characters: ArrayList<Character>,
    private val listener: SetOnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoadingAdded = false
    private var errorConnection = false

    inner class CharactersViewHolder(private var binding: ItemCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(character: Character) {
            with(binding) {
                Picasso.get().load(character.image).into(binding.imageView)
                nameTextView.text = character.name
                speciesTextView.text = character.species
                statusTextView.text = character.status
            }
            itemView.setOnClickListener {
                listener.onNextCharacter(character.id)
            }
        }
    }

    inner class LoadingViewHolder(val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class NoInternetViewHolder(val binding: ItemNoInternetBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            LOADING -> {
                val viewLoading =
                    ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
            ITEM -> {
                val viewItem = ItemCharactersBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = CharactersViewHolder(viewItem)
            }
            ERROR_CONNECTION -> {
                val viewError = ItemNoInternetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = NoInternetViewHolder(viewError)
            }
        }
        return viewHolder!!
    }

    fun addAllCharacters(characters: ArrayList<Character>) {
        _characters.addAll(characters)
        notifyItemInserted(_characters.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == _characters.size - 1 && isLoadingAdded) LOADING
        else if (position == _characters.size - 1 && errorConnection) ERROR_CONNECTION
        else ITEM
    }

    fun addLoadingFooter(show: Boolean) {
        isLoadingAdded = show
    }

    fun showReply(show: Boolean) {
        errorConnection = show
        notifyItemChanged(_characters.size)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.binding.progressBar.visibility = View.VISIBLE
            }
            ITEM -> {
                val characters = _characters[position]
                val charactersViewHolder = holder as CharactersViewHolder
                charactersViewHolder.bindView(characters)
            }
            ERROR_CONNECTION -> {
                val noInternetViewHolder = holder as NoInternetViewHolder
                noInternetViewHolder.binding.btnRepeat.setOnClickListener {
                    listener.onClickReply()
                }
            }
        }
    }

    override fun getItemCount(): Int = _characters.size

    interface SetOnClickListener {
        fun onClickReply()
        fun onNextCharacter(id: Int)
    }

    companion object {
        const val ITEM = 0
        const val LOADING = 1
        const val ERROR_CONNECTION = 2
    }
}