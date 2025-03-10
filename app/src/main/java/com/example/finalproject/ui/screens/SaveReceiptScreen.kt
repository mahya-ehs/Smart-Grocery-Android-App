package com.example.finalproject.ui.screens

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.finalproject.viewmodel.ShoppingHistoryViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TakeReceiptPhotoView(viewModel: ShoppingHistoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && imageUri != null) {
                val path = saveImageToInternalStorage(context, imageUri!!)
                viewModel.setReceiptImagePath(path)
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (cameraPermissionState.status.isGranted) {
                val uri = createImageUri(context)
                imageUri = uri
                if (uri != null) {
                    cameraLauncher.launch(uri)
                }
            } else {
                cameraPermissionState.launchPermissionRequest()
            }
        }) {
            Text("Take Receipt Photo")
        }

        viewModel.receiptImagePath.collectAsState().value?.let { path ->
            Text("Receipt photo saved", modifier = Modifier.padding(top = 8.dp))
        }
    }
}


fun saveImageToInternalStorage(context: Context, imageUri: Uri): String {
    val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
    val file = File(context.filesDir, "image_${System.currentTimeMillis()}.jpg")
    var path = ""
    inputStream?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }
    path = file.absolutePath

    return path
}


fun createImageUri(context: Context): Uri? {
    val timeS = System.currentTimeMillis()
    val storageDir = File(context.getExternalFilesDir(null), "Pictures")

    if (!storageDir.exists()) {
        storageDir.mkdirs()
    }

    val file = File(storageDir, "IMG_$timeS.jpg")

    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}

fun chooseImage(
    context: Context,
    gallerySelect: () -> Unit,
    cameraSelect: () -> Unit
) {

    AlertDialog.Builder(context).apply {
        setTitle("Choose Image Source")
        setItems(arrayOf("Gallery", "Camera")) { _, which ->
            if (which == 0) {
                gallerySelect()
            } else {
                cameraSelect()
            }
        }
        show()
    }
}

