package com.example.finalproject
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.finalproject.ui.theme.FinalprojectTheme
import com.example.finalproject.ui.navigation.NavigationController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalprojectTheme {
                NavigationController()
            }
        }
    }
}
