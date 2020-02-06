package com.huutho.baseapplication.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.huutho.baseapplication.Movie
import com.huutho.baseapplication.TheMovieDBService
import com.huutho.baselibrary.BaseViewModel
import com.huutho.baselibrary.data.Resource
import com.huutho.baselibrary.data.callApiDefault
import com.huutho.baselibrary.data.emitDefault
import kotlinx.coroutines.launch

class MainViewModel(
    private val theMovieDBService: TheMovieDBService,
    private val mainRepos: MainRepository
) : BaseViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()




    //----------------------------------------------------------------------------------------------

    val onRefreshPopularMovie = MutableLiveData<Boolean>()

    val moviesLiveData = onRefreshPopularMovie.switchMap { b ->
        liveData<Resource<Movie>> {
            emitDefault(theMovieDBService.getPopular())
        }
    }





    //----------------------------------------------------------------------------------------------




    val popularMoviesLV = MutableLiveData<Resource<Movie>>()

    fun getTopRateMovies() {
        viewModelScope.launch {
            callApiDefault(popularMoviesLV, theMovieDBService.getPopular(), loadingLiveData)
        }
    }


}