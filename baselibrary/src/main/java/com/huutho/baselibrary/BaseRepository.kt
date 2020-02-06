package com.huutho.baselibrary

import com.huutho.baselibrary.data.Entity
import com.huutho.baselibrary.data.Resource
import org.koin.core.KoinComponent

open class BaseRepository : KoinComponent {

    suspend fun <T : Entity> handleDefault(source: suspend () -> T): Resource<T> {
        return try {
            val data = source.invoke()
            Resource.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.failure(e)
        }
    }

    suspend fun <T : Entity> handleDefault(source: T): Resource<T> {
        return try {
            Resource.success(source)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.failure(e)
        }
    }
}