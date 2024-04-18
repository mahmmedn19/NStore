package com.monaser.nstore.ui.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.monaser.nstore.data.remote.models.ImageData
import com.monaser.nstore.data.repository.NStoreKey
import com.monaser.nstore.domain.entity.Response
import com.monaser.nstore.domain.repository.NStoreRepo
import com.monaser.nstore.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.core5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreWriteRequest
import org.mobilenativefoundation.store.store5.StoreWriteResponse
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nStoreRepository: NStoreRepo
) : BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()), HomeInteractionListener {
    override val TAG: String = "HomeViewModel"

    init {
        getAllImages()
    }


    @OptIn(ExperimentalStoreApi::class)
    fun getAllImages() {
        viewModelScope.launch(Dispatchers.IO) {
            val request = StoreReadRequest.fresh(NStoreKey.Read.GetImages)
            nStoreRepository.getImageNStore().stream<Response>(request)
                .collect { response ->
                    log("response: $response")
                    when (response) {
                        is StoreReadResponse.Data -> {
                            val images = when (val value = response.value) {
                                is List<*> -> value.filterIsInstance<Response>()
                                else -> emptyList()
                            }
                            log("images: $images")
                            Log.d(TAG, "images: $images")
                            _state.update {
                                it.copy(
                                    data = images.map { it.toHomeUiModel() },
                                    isLoading = false,
                                    isError = false
                                )
                            }
                        }

                        is StoreReadResponse.Error -> {
                            _state.update {
                                it.copy(
                                    isError = true,
                                    errorMessage = response.errorMessageOrNull() ?: "",
                                    isLoading = false
                                )
                            }
                        }

                        is StoreReadResponse.Loading -> {
                            _state.update { it.copy(isLoading = true, isError = false) }
                        }

                        else -> {
                            _state.update { it.copy(isLoading = false, isError = false) }
                        }
                    }
                }
        }
    }


    @OptIn(ExperimentalStoreApi::class)
    override fun onDeleteClicked(id: String) {
        viewModelScope.launch {
            nStoreRepository.getImageNStore()
                .write(StoreWriteRequest.of(NStoreKey.Delete.DeleteImageById(id), ""))
                .let { result ->
                    when (result) {
                        is StoreWriteResponse.Success -> {
                            effectActionExecutor(_effect, HomeUiEffect.ShowMessage("Image deleted successfully"))
                        }

                        is StoreWriteResponse.Error -> {
                            effectActionExecutor(_effect, HomeUiEffect.ShowMessage("Error deleting image"))
                        }
                    }
                }
        }
    }

    @OptIn(ExperimentalStoreApi::class)
    override fun onAddClicked(createImage: ImageData) {
        viewModelScope.launch {
            nStoreRepository.getImageNStore()
                .write(StoreWriteRequest.of(NStoreKey.Write.Add, createImage))
                .let { result ->
                    when (result) {
                        is StoreWriteResponse.Success -> {
                            effectActionExecutor(_effect, HomeUiEffect.ShowMessage("Image added successfully"))
                        }
                        is StoreWriteResponse.Error -> {
                            effectActionExecutor(_effect, HomeUiEffect.ShowMessage("Error adding image"))
                        }
                    }
                }

        }
    }

    override fun onItemClicked(id: String) {
            effectActionExecutor(_effect, HomeUiEffect.NavigateToDetails(id))
    }


}