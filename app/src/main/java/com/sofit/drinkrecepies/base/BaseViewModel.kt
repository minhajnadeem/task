package com.sofit.drinkrecepies.base

import androidx.lifecycle.ViewModel
import com.sofit.drinkrecepies.utils.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    val eventShowHideProgress = SingleLiveEvent<Boolean>()

    fun showLoading() {
        eventShowHideProgress.value = true
    }

    fun hideLoading() {
        eventShowHideProgress.value = false
    }
}