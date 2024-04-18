package com.monaser.nstore.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.monaser.nstore.data.repository.NStoreKey
import com.monaser.nstore.domain.entity.Response
import com.monaser.nstore.domain.repository.NStoreRepo
import com.monaser.nstore.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val nStoreRepository: NStoreRepo,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailsUiState, Int>(DetailsUiState()) {
    override val TAG: String = "DetailsViewModel"
    private val args = DetailsArgs(savedStateHandle)

    init {
        getDetails(args.id)
    }

    fun getDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = StoreReadRequest.fresh(NStoreKey.ReadInfo.GetImageInfoById(id))
            nStoreRepository.getImageInfoNStore().stream(request)
                .collect { response ->
                    log("response: $response")
                    when (response) {
                        is StoreReadResponse.Data -> {
                            val details = response.value as Response
                            _state.update {
                                it.copy(
                                    data = details.toDetailsUiModel(),
                                    isLoading = false,
                                    isError = false
                                )
                            }
                        }

                        is StoreReadResponse.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isError = true,
                                    errorMessage = response.errorMessageOrNull() ?: "",
                                )
                            }
                        }

                        else -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isError = true,
                                    errorMessage = "Error",
                                )
                            }
                        }
                    }
                }
        }
    }
}