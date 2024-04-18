package com.monaser.nstore.data.repository

import com.monaser.nstore.data.remote.models.ImageData
import com.monaser.nstore.data.remote.models.ResponseDto

sealed class NStoreKey {
    sealed class Read : NStoreKey() {
        object GetImages : Read()
    }
    sealed class ReadInfo : NStoreKey() {
        data class GetImageInfoById(val id: String) : ReadInfo()
    }

    sealed class Write : NStoreKey() {
        data object Add : Write()
        data object Update : Write()
        data object UpdateAllI : Write()

    }


    sealed class Delete : NStoreKey() {
        data class DeleteImageById(val id: String) : Delete()
        data object DeleteAllImages : Delete()
    }
}
