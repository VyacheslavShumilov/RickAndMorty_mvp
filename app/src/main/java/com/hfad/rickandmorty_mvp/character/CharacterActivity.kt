package com.hfad.rickandmorty_mvp.character

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.hfad.rickandmorty_mvp.App
import com.hfad.rickandmorty_mvp.character.impl.CharacterContract
import com.hfad.rickandmorty_mvp.character.impl.CharacterPresenterImpl
import com.hfad.rickandmorty_mvp.databinding.ActivityCharacterBinding
import com.hfad.rickandmorty_mvp.model.Character
import com.hfad.rickandmorty_mvp.room.CharacterDao
import com.hfad.rickandmorty_mvp.room.Model.Characters
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterActivity : AppCompatActivity(), CharacterContract.View {

    private lateinit var charactersDao: CharacterDao

    private lateinit var binding: ActivityCharacterBinding
    var id: String = ""
    private lateinit var presenter: CharacterPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args = intent.extras
        id = args?.get("ID").toString()

        charactersDao = (application as App).getDatabase().charactersDao()

        presenter = CharacterPresenterImpl()
        presenter.attachView(this)
        presenter.responseData(id.toInt())


    }

    override fun loadCharacter(character: Character) {
        with(binding) {
            Picasso.get().load(character.image).into(avatarImageView)
            nameTextView.text = character.name
            statusTextView.text = character.status
            speciesTextView.text = character.species
            genderTextView.text = character.gender

            addFavouriteBtn.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val _character = Characters(
                        0,
                        character.name,
                        character.status,
                        character.species,
                        character.gender,
                        character.image
                    )
                    charactersDao.insertCharacter(_character)
                }
            }
        }
    }


    override fun error(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun progressBar(show: Boolean) {
        if (show) {
            binding.characterConstraintLayout.visibility = View.GONE
            binding.progressBarCharacter.visibility = View.VISIBLE
        } else {
            binding.characterConstraintLayout.visibility = View.VISIBLE
            binding.progressBarCharacter.visibility = View.GONE
        }
    }
}