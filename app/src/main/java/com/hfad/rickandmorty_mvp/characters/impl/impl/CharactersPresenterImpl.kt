package com.hfad.rickandmorty_mvp.characters.impl.impl


import com.hfad.rickandmorty_mvp.model.ResponseCharacters
import com.hfad.rickandmorty_mvp.services.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersPresenterImpl: CharactersContract.Presenter {

    private var mvpView: CharactersContract.View? = null
    private var api = Api.create()

    override fun responseData(page: Int) {
        mvpView?.let { view ->
            view.progressBar(true)
            api.getCharacter(page).enqueue(object : Callback<ResponseCharacters>{
                override fun onResponse(
                    call: Call<ResponseCharacters>,
                    response: Response<ResponseCharacters>
                ) {
                    if (response.isSuccessful) {
                        view.progressBar(false)
                        response.body()?.let { data ->
                            view.loadCharacters(data.characters)
                            view.onSuccessInfo(data.info)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseCharacters>, t: Throwable) {
                    view.progressBar(false)
                    view.error("Нет интернета")
                }
            })
        }
    }

    override fun attachView(view: CharactersContract.View) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
    }
}