package com.monaser.nstore.ui.screens.home

import com.monaser.nstore.data.remote.models.ImageData

interface HomeInteractionListener {
    fun onDeleteClicked(id: String)
    fun onItemClicked(id: String)
    fun onAddClicked(createImage: ImageData)
}