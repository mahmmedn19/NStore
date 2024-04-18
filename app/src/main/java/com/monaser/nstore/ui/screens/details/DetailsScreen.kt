package com.monaser.nstore.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.monaser.nstore.ui.utils.DateTimeUtils
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailsScreen() {
    val viewModel: DetailsViewModel = hiltViewModel()
    val uiState by viewModel.state.collectAsState()
    DetailsContent(
        uiState = uiState,
    )
}

@Composable
fun DetailsContent(
    uiState: DetailsUiState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            imageModel = { uiState.data.imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            loading = {
                Box(Modifier.matchParentSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            },
            failure = {
                Text(text = "image request failed.")
            }
        )
        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            text = uiState.data.title,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = uiState.data.description,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .wrapContentSize()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = DateTimeUtils.formatDateTime(uiState.data.createdAt),
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
        )
    }
}