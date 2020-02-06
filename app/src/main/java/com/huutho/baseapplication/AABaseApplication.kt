package com.huutho.baseapplication

import android.util.Log
import com.huutho.baseapplication.main.MainRepository
import com.huutho.baseapplication.main.MainViewModel
import com.huutho.baseapplication.sharePrefer.MovieSharePref
import com.huutho.baseapplication.sharePrefer.UserSharePref
import com.huutho.baselibrary.BaseApplication
import com.huutho.baselibrary.network.RetrofitHelper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

class AABaseApplication : BaseApplication() {

    override val appModule: Module = module {
        single(named("UserSharePref")) {
            UserSharePref(
                get(),
                "UserSharePref",
                get()
            )
        }
        single(named("MovieSharePref")) {
            MovieSharePref(
                get(),
                "MovieSharePref",
                get()
            )
        }
    }

    override val networkModule: Module = module {
        single {
            val baseUrl = "https://api.themoviedb.org/3/"
            val queryParams = mapOf(Pair("api_key", "7cb8cec4620a4dd1c09bed93d0d0cc84"))

            RetrofitHelper.initial(
                context = this@AABaseApplication,
                gson = get(),
                baseUrl = baseUrl,
                queryParams = queryParams,
                serviceClazz = TheMovieDBService::class.java,
                captureResponseCode = { isSuccessful, code, message ->
                    Log.i("BaseApplication", "isSuccessful = $isSuccessful ; code = $code ; message = $message")
                }
            )
        }
    }

    override val viewModelModule = module {
        factory { MainRepository(get()) }
        viewModel { MainViewModel(get(), get()) }
    }
}