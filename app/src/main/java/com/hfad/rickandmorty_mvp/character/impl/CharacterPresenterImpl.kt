package com.hfad.rickandmorty_mvp.character.impl

import com.hfad.rickandmorty_mvp.model.Character
import com.hfad.rickandmorty_mvp.services.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterPresenterImpl : CharacterContract.Presenter {

    private var mvpView: CharacterContract.View? = null
    private var api = Api.create()

    override fun responseData(id: Int) {
        mvpView?.let { view ->
            view.progressBar(true)
            api.getCharacterInfo(id).enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    if (response.isSuccessful) {
                        view.progressBar(false)
                        response.body()?.let { data ->
                            view.loadCharacter(data)
                        }
                    }
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    view.progressBar(true)
                    view.error("Нет интернета")
                }
            })
        }
    }

    override fun attachView(view: CharacterContract.View) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
    }
}