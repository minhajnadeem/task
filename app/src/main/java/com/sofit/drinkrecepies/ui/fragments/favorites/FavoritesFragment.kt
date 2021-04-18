package com.sofit.drinkrecepies.ui.fragments.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.sofit.drinkrecepies.BR
import com.sofit.drinkrecepies.R
import com.sofit.drinkrecepies.base.BaseFragment
import com.sofit.drinkrecepies.data.model.Drinks
import com.sofit.drinkrecepies.databinding.FragmentFavoritesBinding
import com.sofit.drinkrecepies.ui.fragments.favorites.adapter.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_favorites
    override val viewModel: Class<FavoritesViewModel>
        get() = FavoritesViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private val list = mutableListOf<Drinks>()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = FavoritesAdapter(list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeShareViewModel()
    }

    private fun init() {
        rvFavorites.adapter = adapter
    }

    private fun observeShareViewModel() {
        sharedViewModel.liveFavorites.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }
}