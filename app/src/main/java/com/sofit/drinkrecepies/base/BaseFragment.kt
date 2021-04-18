package com.sofit.drinkrecepies.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sofit.drinkrecepies.BR
import com.sofit.drinkrecepies.MainActivity
import com.sofit.drinkrecepies.data.local.datastore.DataStoreProvider

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    abstract val layoutId: Int
    lateinit var mViewDataBinding: T
    protected lateinit var mViewModel: V
    abstract val viewModel: Class<V>
    abstract val bindingVariable: Int
    lateinit var sharedViewModel: SharedViewModel
    lateinit var dataStoreProvider: DataStoreProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(viewModel)
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStoreProvider = (requireActivity() as MainActivity).dataStoreProvider
    }
}