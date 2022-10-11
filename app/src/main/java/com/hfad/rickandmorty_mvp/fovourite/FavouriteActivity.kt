package com.hfad.rickandmorty_mvp.fovourite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hfad.rickandmorty_mvp.App
import com.hfad.rickandmorty_mvp.databinding.ActivityFavouriteBinding
import com.hfad.rickandmorty_mvp.fovourite.adapter.AdapterFavourite
import com.hfad.rickandmorty_mvp.room.CharacterDao
import com.hfad.rickandmorty_mvp.room.Model.Characters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var characterDao: CharacterDao
    private var favourite: ArrayList<Characters> = arrayListOf()

    private lateinit var adapterCharactersFavourite: AdapterFavourite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        characterDao = (application as App).getDatabase().charactersDao()

        lifecycleScope.launch(Dispatchers.IO) {
            favourite.addAll(characterDao.getAllCharacters())

            withContext(Dispatchers.Main) {
                adapterCharactersFavourite = AdapterFavourite(favourite)
                binding.recyclerView.adapter = adapterCharactersFavourite
            }
        }
    }
}