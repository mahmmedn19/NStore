package com.monaser.nstore.data.repository

import android.util.Log
import com.monaser.nstore.data.local.NStoreDao
import com.monaser.nstore.data.local.NStoreInfoDao
import com.monaser.nstore.data.local.entity.ResponseEntity
import com.monaser.nstore.data.local.entity.mappers.toDomain
import com.monaser.nstore.data.local.entity.mappers.toEntity
import com.monaser.nstore.data.remote.NStoreApi
import com.monaser.nstore.data.remote.models.ResponseDto
import com.monaser.nstore.domain.entity.Response
import com.monaser.nstore.domain.repository.NStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.core5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult
import org.mobilenativefoundation.store.store5.impl.extensions.asMutableStore
import javax.inject.Inject

class NStoreRepoImpl @Inject constructor(
    private val nStoreDao: NStoreDao,
    private val nStoreInfoDao: NStoreInfoDao,
    private val nStoreApi: NStoreApi,
) : NStoreRepo {

    @OptIn(ExperimentalStoreApi::class)
    override suspend fun getImageNStore(): MutableStore<NStoreKey, Any> {
        val fetcher: Fetcher<NStoreKey, Any> = Fetcher.ofFlow { key: NStoreKey ->
            require(key is NStoreKey.Read)
            when (key) {
                is NStoreKey.Read.GetImages -> {
                    flow { emitAll(fetchDataFromApi() )}
                }
            }
        }

        val sourceOfTruth = getStoreSourceOfTruth()
        return StoreBuilder.from(
            fetcher = fetcher,
            sourceOfTruth = sourceOfTruth
        ).build().asMutableStore<NStoreKey, Any, Any, Any, Any>(
            updater = getStoreUpdater(),
            bookkeeper = null
        )
    }


    private suspend fun fetchDataFromApi(): Flow<List<Response>> {
        return nStoreApi.getImages().map { it.toDomain() }.let { images: List<Response> ->
            Log.d("NStoreRepoImpl", "fetchDataFromApi: $images")
            if (images.isNotEmpty()) {
                flowOf(images)
            } else {
                flow {
                    throw Exception("No data from API")
                }
            }
        }.catch { exception: Throwable ->
            Log.e("NStoreRepoImpl", "Failed to fetch data from API", exception)
            throw exception
        }
    }

    override suspend fun getImageInfoNStore(): Store<NStoreKey, Any> {
        val fetcher: Fetcher<NStoreKey, Any> = Fetcher.ofFlow { key: NStoreKey ->
            require(key is NStoreKey.ReadInfo)
            when (key) {
                is NStoreKey.ReadInfo.GetImageInfoById -> flow {
                    emit(fetchInfoById(key.id))
                }
            }
        }

        val sourceOfTruth = getStoreInfoSourceOfTruth()
        return StoreBuilder.from(
            fetcher = fetcher,
            sourceOfTruth = sourceOfTruth
        ).build()
    }

    private suspend fun fetchInfoById(id: String): Flow<Response> {
        return nStoreApi.getDetailsById(id).toDomain().let { response: Response ->
            Log.d("NStoreRepoImpl", "fetchInfoById: $response")
            if (response.id.isNotEmpty()) {
                flowOf(response)
            } else {
                flow {
                    throw Exception("No data from API")
                }
            }
        }.catch { exception: Throwable ->
            Log.e("NStoreRepoImpl", "Failed to fetch data from API", exception)
            throw exception
        }
    }

    private suspend fun getStoreInfoSourceOfTruth(): SourceOfTruth<NStoreKey, Any, Any> {
        return SourceOfTruth.Companion.of(
            reader = { key: NStoreKey ->
                require(key is NStoreKey.ReadInfo)
                flow {
                    when (key) {
                        is NStoreKey.ReadInfo.GetImageInfoById -> {
                            emit(nStoreInfoDao.getInfo(key.id))
                        }
                    }
                }
            },
            writer = { key: NStoreKey, value: Any ->
                require(key is NStoreKey.Write)
                when (key) {
                    NStoreKey.Write.Add -> nStoreInfoDao.insertInfo(value as ResponseEntity)
                    NStoreKey.Write.Update -> nStoreInfoDao.updateImage(value as ResponseEntity)
                    else -> {}
                }
            },
        )
    }


    private suspend fun getStoreSourceOfTruth(): SourceOfTruth<NStoreKey, Any, Any> {
        return SourceOfTruth.of(
            reader = { key: NStoreKey ->
                require(key is NStoreKey.Read)
                when (key) {
                    is NStoreKey.Read.GetImages -> {
                        nStoreDao.getAllImages()
                            .map { images ->
                                images.map { it.toDomain() }
                            }
                    }
                }
            },
            writer = { key: NStoreKey, value: Any ->
                when (key) {
                    is NStoreKey.Read.GetImages -> {
                        if (value is List<*>) {
                            val responseList = value.filterIsInstance<ResponseEntity>()
                            nStoreDao.insertAll(responseList)
                            nStoreDao.updateAllImages(responseList)
                            Log.d("NStoreRepoImpl", "Updated images${responseList.size}")
                        }
                    }

                    is NStoreKey.ReadInfo.GetImageInfoById -> {
                        if (value is ResponseDto) {
                            nStoreDao.updateImage(value.toEntity())
                        }
                    }

                    NStoreKey.Write.Add -> {
                        if (value is List<*>) {
                            val responseList = value.filterIsInstance<ResponseDto>()
                            nStoreDao.insertAll(responseList.map { it.toEntity() })
                            Log.d("NStoreRepoImpl", "insertAll images${responseList.size}")

                        }
                    }

                    NStoreKey.Write.UpdateAllI -> {
                        if (value is List<*>) {
                            val responseList = value.filterIsInstance<ResponseDto>()
                            nStoreDao.updateAllImages(responseList.map { it.toEntity() })
                            Log.d("NStoreRepoImpl", "updateAllImages images${responseList.size}")

                        }
                    }

                    NStoreKey.Write.Update -> {
                        if (value is ResponseDto) {
                            nStoreDao.updateImage(value.toEntity())
                        }
                    }

                    else -> {}
                }
            },
            delete = { key: NStoreKey ->
                when (key) {
                    is NStoreKey.Delete.DeleteImageById -> nStoreDao.deleteImageById(key.id)
                    is NStoreKey.Delete.DeleteAllImages -> nStoreDao.deleteAll()
                    else -> throw IllegalArgumentException("Invalid key for delete operation")
                }
            }
        )
    }



    private fun getStoreUpdater(): Updater<NStoreKey, Any, Any> {
        return Updater.by(
            post = { key: NStoreKey, value: Any ->
                when (key) {
                    is NStoreKey.Write.Add -> {
                        require(value is ResponseDto)
                        UpdaterResult.Success.Untyped(
                            nStoreApi.addImage(value)
                        )
                    }

                    is NStoreKey.Delete.DeleteImageById -> {
                        UpdaterResult.Success.Untyped(
                            nStoreApi.deleteImageById(key.id)
                        )
                    }

                    else -> throw IllegalArgumentException("Invalid key")
                }
            }
        )
    }

}