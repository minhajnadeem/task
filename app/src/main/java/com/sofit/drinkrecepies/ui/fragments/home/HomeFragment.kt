package com.sofit.drinkrecepies.ui.fragments.home

import com.sofit.drinkrecepies.R
import com.sofit.drinkrecepies.base.BaseFragment
import com.sofit.drinkrecepies.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: Class<HomeViewModel>
        get() = HomeViewModel::class.java
}