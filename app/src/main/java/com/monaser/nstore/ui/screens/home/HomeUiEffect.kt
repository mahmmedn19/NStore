package com.monaser.nstore.ui.screens.home

import com.monaser.nstore.ui.screens.base.BaseUiEffect

sealed interface HomeUiEffect : BaseUiEffect{

    data class NavigateToDetails(val id: String) : HomeUiEffect
    data class ShowMessage(val message: String) : HomeUiEffect
}