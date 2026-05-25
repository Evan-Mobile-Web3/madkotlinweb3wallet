package com.evan.madkotlinweb3wallet.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evan.madkotlinweb3wallet.ui.theme.MadKotlinWeb3WalletTheme

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // 增加一个 Surface 作为组件的“底板”，以便区分背景
    Surface(
        modifier = modifier,
        color = Color.White, // 组件自己的背景色
        shape = MaterialTheme.shapes.medium, // 圆角
        shadowElevation = 4.dp // 增加阴影，更有立体感
    ) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.padding(24.dp), // 在文字周围留出空白，露出白色背景
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    MadKotlinWeb3WalletTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFBCBCBC) // 明显的深灰色背景
        ) {
            Box(contentAlignment = Alignment.Center) {
                Greeting("Android")
            }
        }
    }
}
