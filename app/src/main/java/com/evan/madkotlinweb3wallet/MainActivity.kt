package com.evan.madkotlinweb3wallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.evan.madkotlinweb3wallet.ui.screens.MainScreen
import com.evan.madkotlinweb3wallet.ui.theme.MadKotlinWeb3WalletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MadKotlinWeb3WalletTheme {
                // for test use
                MainScreen()
            }
        }
    }
}
