package com.evan.madkotlinweb3wallet.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import com.evan.madkotlinweb3wallet.R
import com.evan.madkotlinweb3wallet.domain.WalletConnectionStatus
import com.evan.madkotlinweb3wallet.domain.WalletUiState
import com.evan.madkotlinweb3wallet.domain.WalletAccount

// MetaMask 狐狸图标的官方资源链接
const val METAMASK_ICON_URL = "https://raw.githubusercontent.com/MetaMask/brand-resources/master/SVG/Metamask-Logo.svg"
// 备用 PNG 链接（以防 SVG 渲染有问题）
const val METAMASK_ICON_PNG = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/MetaMask_Fox.svg/512px-MetaMask_Fox.svg.png"

/**
 * 应用主导航入口
 */
@Composable
fun MainScreen(walletViewModel: WalletViewModel = viewModel()) {
    val navController = rememberNavController()
    val uiState by walletViewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = "connect") {
        composable("connect") {
            ConnectScreen(onConnectClick = {
                walletViewModel.connectMetaMask()
                navController.navigate("auth")
            })
        }

        composable("auth") {
            AuthScreen(
                status = uiState.status,
                error = uiState.errorMessage,
                onRetry = {
                    walletViewModel.resetConnection()
                    navController.popBackStack("connect", false)
                },
                onComplete = {
                    navController.navigate("dashboard") {
                        popUpTo("connect") { inclusive = true }
                    }
                }
            )
        }

        composable("dashboard") {
            WalletDashboardScreen(
                uiState = uiState,
                onAccountSelected = { walletViewModel.selectAccount(it) }
            )
        }
    }
}

/**
 * 页面一：初始连接页
 * 加入了 MetaMask 狐狸图标展示
 */
@Composable
fun ConnectScreen(onConnectClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // 使用本地 Vector 资源
            Image(
                painter = painterResource(id = R.drawable.ic_metamask_fox),
                contentDescription = "MetaMask Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("Web3 Wallet", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("安全连接您的数字资产", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onConnectClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_metamask_fox),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("连接 MetaMask 钱包")
                }
            }
        }
    }
}

/**
 * 页面二：授权与状态结果页
 */
@Composable
fun AuthScreen(
    status: WalletConnectionStatus,
    error: String?,
    onRetry: () -> Unit,
    onComplete: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_metamask_fox),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            when (status) {
                WalletConnectionStatus.CONNECTING -> {
                    CircularProgressIndicator(strokeWidth = 3.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("正在唤起 MetaMask...", fontWeight = FontWeight.Medium)
                }
                WalletConnectionStatus.SUCCESS -> {
                    Text("✅ 授权成功", color = Color(0xFF4CAF50), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = onComplete, modifier = Modifier.fillMaxWidth()) {
                        Text("进入我的钱包")
                    }
                }
                WalletConnectionStatus.FAILED -> {
                    Text("❌ 授权失败", color = MaterialTheme.colorScheme.error, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(error ?: "用户取消了请求", color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = onRetry, modifier = Modifier.fillMaxWidth()) {
                        Text("重试连接")
                    }
                }
                else -> {}
            }
        }
    }
}

/**
 * 页面三：钱包主页
 * 增加了带有狐狸头像的 TopBar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletDashboardScreen(
    uiState: WalletUiState,
    onAccountSelected: (WalletAccount) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            // 顶部的品牌 Bar
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_metamask_fox),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("MetaMask 钱包", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("当前总资产", fontSize = 14.sp, color = Color.Gray)
            
            Text(
                text = uiState.balance,
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box {
                OutlinedCard(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("当前选定地址", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                            Text(uiState.selectedAccount?.name ?: "未连接", fontWeight = FontWeight.Bold)
                            Text(uiState.selectedAccount?.address ?: "", fontSize = 12.sp, color = Color.Gray)
                        }
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "切换账户")
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    uiState.accounts.forEach { account ->
                        DropdownMenuItem(
                            text = { 
                                Column {
                                    Text(account.name, fontWeight = FontWeight.Bold)
                                    Text(account.address, fontSize = 12.sp)
                                }
                            },
                            onClick = {
                                onAccountSelected(account)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
