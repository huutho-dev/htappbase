package com.huutho.baseapplication.main

import android.util.Log
import androidx.lifecycle.Observer
import com.huutho.baseapplication.R
import com.huutho.baseapplication.databinding.ActivityMainBinding
import com.huutho.baselibrary.BaseActivity
import com.huutho.baselibrary.data.handleDataDefault
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setBindingForLayout(binding: ActivityMainBinding) {
        binding.vm = viewModel
    }

    override fun onViewReady() {
        btn_call.setOnClickListener {
            viewModel.onRefreshPopularMovie.value = true
        }

        btn_refresh.setOnClickListener {
            viewModel.getTopRateMovies()
        }
    }

    override fun onObserverListener(viewModel: MainViewModel) {

        viewModel.popularMoviesLV.observe(this, Observer {
            handleDataDefault(it,
                onLoading = { Log.e("ThoNH", "LOADING}") },
                onSuccess = { Log.e("ThoNH", "SUCCESS") },
                onFailure = { Log.e("ThoNH", "FAILURE") },
                onComplete = { Log.e("ThoNH", "COMPLETE") }
            )
        })

        viewModel.moviesLiveData.observe(this, Observer {
            handleDataDefault(it,
                onLoading = { Log.e("ThoNH", "LOADING}") },
                onSuccess = { Log.e("ThoNH", "SUCCESS") },
                onFailure = { Log.e("ThoNH", "FAILURE") },
                onComplete = { Log.e("ThoNH", "COMPLETE") }
            )
        })
    }
}
