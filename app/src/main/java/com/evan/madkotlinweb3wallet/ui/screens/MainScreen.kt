package com.evan.madkotlinweb3wallet.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.evan.madkotlinweb3wallet.ui.components.Greeting
import com.evan.madkotlinweb3wallet.ui.theme.MadKotlinWeb3WalletTheme

@Composable
fun MainScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Greeting(name = "Android")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MadKotlinWeb3WalletTheme {
        MainScreen()
    }
}
