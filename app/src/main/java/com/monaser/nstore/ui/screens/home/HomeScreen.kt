package com.monaser.nstore.ui.screens.home

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.monaser.nstore.ui.composable.ImageList
import com.monaser.nstore.ui.screens.details.navigateToDetails
import com.monaser.nstore.ui.utils.EffectHandler
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet

@Composable
fun HomeScreen(
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.state.collectAsState()
    EffectHandler(effects = viewModel.effect) { effect, navController ->
        when (effect) {
            is HomeUiEffect.NavigateToDetails -> {
                navController.navigateToDetails(effect.id)
            }

            is HomeUiEffect.ShowMessage -> {
                Toast.makeText(navController.context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    HomeContent(
        uiState = uiState,
        interactionListener = viewModel
    )
}

@OptIn(ExperimentalSheetApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: HomeUiState,
    interactionListener: HomeInteractionListener,
) {
    var visible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "NStore",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    visible = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp)
            ) {
                ImageList(
                    images = uiState.data,
                    interactionListener = interactionListener
                )
            }
            ModalSheet(
                visible = visible,
                onVisibleChange = { visible = it },
                shape = MaterialTheme.shapes.large,
            ) {
                BottomSheetContent {
                    visible = false
                }
            }
        }
    )
}

@Composable
fun BottomSheetContent(
    onAddClicked: () -> Unit
) {
    val context = LocalContext.current
    val imagePicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Toast.makeText(context, "Picked image: $it", Toast.LENGTH_SHORT).show()
            }
        }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Date Picker") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        )
        Button(
            onClick = { imagePicker.launch("image/*") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text("Pick Image")
        }
        Button(
            onClick = onAddClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text("Add")
        }
    }
}
