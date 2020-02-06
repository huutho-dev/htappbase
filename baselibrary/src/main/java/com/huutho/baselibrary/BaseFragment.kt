package com.huutho.baselibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.*

abstract class BaseFragment<ViewBinding : ViewDataBinding, VM : ViewModel> : Fragment() {

    companion object {
        fun <T : Fragment> T.withArgs(
            argsBuilder: Bundle.() -> Unit
        ): T = this.apply {
            arguments = Bundle().apply(argsBuilder)
        }
    }

    open lateinit var binding: ViewBinding

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBindingForLayout(binding)
        onViewReady(view)
        onObserverListener(viewModel)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun setBindingForLayout(binding: ViewBinding)

    abstract fun onViewReady(view: View)

    abstract fun onObserverListener(viewModel: VM)

    override fun onDestroyView() {
        super.onDestroyView()
        clearFindViewByIdCache()
    }

    open fun onBackPressed() = true
}