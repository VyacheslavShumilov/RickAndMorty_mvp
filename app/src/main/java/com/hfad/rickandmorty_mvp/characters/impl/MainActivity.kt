package com.hfad.rickandmorty_mvp.characters.impl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hfad.rickandmorty_mvp.character.CharacterActivity
import com.hfad.rickandmorty_mvp.characters.impl.adapter.AdapterCharacters
import com.hfad.rickandmorty_mvp.characters.impl.impl.CharactersContract
import com.hfad.rickandmorty_mvp.characters.impl.impl.CharactersPresenterImpl
import com.hfad.rickandmorty_mvp.databinding.ActivityMainBinding
import com.hfad.rickandmorty_mvp.fovourite.FavouriteActivity
import com.hfad.rickandmorty_mvp.model.Character
import com.hfad.rickandmorty_mvp.model.Info
import com.hfad.rickandmorty_mvp.utils.EndlessScrollEventListener

class MainActivity : AppCompatActivity(), CharactersContract.View,
    AdapterCharacters.SetOnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: CharactersPresenterImpl
    private lateinit var endlessScrollEventListener: EndlessScrollEventListener
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapterCharacters: AdapterCharacters
    private var errorConnection = false
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter = CharactersPresenterImpl()
        presenter.attachView(this)
        presenter.responseData(1)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        setEndlessListenerScrollView()
        binding.recyclerView.addOnScrollListener(endlessScrollEventListener)

        binding.btnFavourite.setOnClickListener {
            startActivity(Intent(this, FavouriteActivity::class.java))
        }
    }


    private fun setEndlessListenerScrollView() {
        endlessScrollEventListener = object : EndlessScrollEventListener(layoutManager) {
            override fun onLoadMore(recyclerView: RecyclerView) {
                isLoading = true
                if (currentPage != 0) {
                    currentPage++
                    presenter.responseData(currentPage)
                }
            }
        }
    }

    override fun loadCharacters(character: ArrayList<Character>) {
        if (currentPage == 1) {
            adapterCharacters = AdapterCharacters(character, this)
            binding.recyclerView.adapter = adapterCharacters
        } else {
            adapterCharacters.addAllCharacters(character)
            isLoading = false
            adapterCharacters.addLoadingFooter(false)
        }
    }

    override fun progressBar(show: Boolean) {
        if (show) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    override fun error(message: String) {
        if (currentPage == 1) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            errorConnection = true
            adapterCharacters.addLoadingFooter(false)
            adapterCharacters.showReply(true)
        }
    }

    override fun onSuccessInfo(info: Info) {
        if (currentPage == 1 && info.next != null) {
            if (currentPage <= info.pages) adapterCharacters.addLoadingFooter(true)
            else isLastPage = true
        } else {
            if (currentPage != info.pages) adapterCharacters.addLoadingFooter(true)
            else isLastPage = true
        }
    }

    override fun onClickReply() {
        errorConnection = false
        adapterCharacters.showReply(false)
        adapterCharacters.addLoadingFooter(true)

        presenter.responseData(currentPage)
    }

    override fun onNextCharacter(id: Int) {
        val intent = Intent(this, CharacterActivity::class.java)
        intent.putExtra("ID", id.toString())
        startActivity(intent)
    }

}