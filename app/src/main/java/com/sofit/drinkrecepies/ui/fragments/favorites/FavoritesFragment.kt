package com.sofit.drinkrecepies.ui.fragments.favorites

import com.sofit.drinkrecepies.R
import com.sofit.drinkrecepies.base.BaseFragment
import com.sofit.drinkrecepies.databinding.FragmentFavoritesBinding

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_favorites
    override val viewModel: Class<FavoritesViewModel>
        get() = FavoritesViewModel::class.java
}