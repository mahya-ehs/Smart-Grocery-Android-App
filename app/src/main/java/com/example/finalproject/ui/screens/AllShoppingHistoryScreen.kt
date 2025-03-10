package com.example.finalproject.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.data.local.entities.ShoppingHistory
import com.example.finalproject.ui.components.BottomNavigationBar
import com.example.finalproject.viewmodel.ShoppingHistoryViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import android.Manifest

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AllShoppingHistoryScreen(navController: NavController,
                               viewModel: ShoppingHistoryViewModel = viewModel()
) {
    val context = LocalContext.current
    var selectedHistory by remember { mutableStateOf<ShoppingHistory?>(null) }
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.attachReceiptToHistory(selectedHistory?.historyId ?: return@let, it, context)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            cameraImageUri?.let {
                viewModel.attachReceiptToHistory(selectedHistory?.historyId ?: return@let, it, context)
            }
        }
    }

    val lists by viewModel.allHistory.collectAsState(initial = emptyList())
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.blob2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column (modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.plantdoodle),
                contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(text = "These are your shopping histories!", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))


            LazyColumn {
                items(lists, key = { it.historyId }) { list ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                navController.navigate("shopping_detail_screen/${list.historyId}")
                            },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_list), // Or another cute icon
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = list.storeName,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Tap to see the list",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            IconButton(onClick = { viewModel.deleteShoppingHistory(list) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_delete),
                                    contentDescription = "Delete"
                                )
                            }
                            // 📸 Add Receipt Button
                            IconButton(onClick = {
                                selectedHistory = list
                                chooseImage(
                                    context = context,
                                    gallerySelect = { galleryLauncher.launch("image/*") },
                                    cameraSelect = {
                                        if (cameraPermissionState.status.isGranted) {
                                            val uri = createImageUri(context)
                                            cameraImageUri = uri
                                            if (uri != null) {
                                                cameraLauncher.launch(uri)
                                            }
                                        } else {
                                            cameraPermissionState.launchPermissionRequest()
                                        }
                                    }
                                )
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add), // add your icon
                                    contentDescription = "Add Receipt"
                                )
                            }

                        }
                    }
                }
            }

        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        ){
            BottomNavigationBar(navController, 0)
        }

    }



}
