package com.huutho.baselibrary

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.*

abstract class BaseActivity<ViewBinding : ViewDataBinding, VM : ViewModel> : AppCompatActivity(){

    val binding: ViewBinding by lazy {
        DataBindingUtil.setContentView<ViewBinding>(this, getLayoutId())
    }

    abstract val viewModel: VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*if (!isTaskRoot
            && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
            && intent.action == Intent.ACTION_MAIN) {
            finish()
            return
        }*/

        binding.lifecycleOwner = this@BaseActivity
        setBindingForLayout(binding)
        onViewReady()
        onObserverListener(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun setBindingForLayout(binding: ViewBinding)

    abstract fun onViewReady()

    abstract fun onObserverListener(viewModel: VM)

}