package com.hfad.rickandmorty_mvp.character.impl

import com.hfad.rickandmorty_mvp.model.Character
import com.hfad.rickandmorty_mvp.mvp.BaseContract

interface CharacterContract {
    interface View: BaseContract.View{
        fun loadCharacter(character: Character)
        fun error(message: String)
        fun progressBar(show: Boolean)
    }
    interface Presenter: BaseContract.Presenter<View> {
        fun responseData(id: Int)
    }

}