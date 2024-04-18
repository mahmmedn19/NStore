package com.monaser.nstore.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monaser.nstore.ui.screens.home.HomeInteractionListener
import com.monaser.nstore.ui.screens.home.HomeUiModel
import com.monaser.nstore.ui.utils.DateTimeUtils
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageList(
    images: List<HomeUiModel>,
    interactionListener: HomeInteractionListener
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images.size) { index ->
            val image = images[index]
            Card(
                onClick = {
                    interactionListener.onItemClicked(image.id)
                }
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        GlideImage(
                            modifier = Modifier
                                .width(120.dp)
                                .fillMaxHeight(),
                            imageModel = { image.imageUrl },
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
                        Column(Modifier.padding(start = 8.dp, top = 8.dp)) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                ),
                                text = "#Id: ${image.id}"
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .fillMaxWidth(),
                                text = image.title,
                                style = MaterialTheme.typography.labelLarge
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth(),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                text = image.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primary)
                                    .wrapContentSize()
                                    .padding(16.dp),
                                textAlign = TextAlign.End,
                                text = DateTimeUtils.formatDateTime(image.createdAt),
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
