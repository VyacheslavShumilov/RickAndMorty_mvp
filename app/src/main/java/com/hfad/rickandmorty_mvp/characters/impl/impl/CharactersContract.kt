package com.hfad.rickandmorty_mvp.characters.impl.impl

import com.hfad.rickandmorty_mvp.model.Character
import com.hfad.rickandmorty_mvp.model.Info
import com.hfad.rickandmorty_mvp.mvp.BaseContract

interface CharactersContract {
    interface View: BaseContract.View {
        fun loadCharacters(character: ArrayList<Character>)

        fun error(message: String)

        fun onSuccessInfo(info: Info)

        fun progressBar(show: Boolean)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun responseData(page: Int)
    }
}