package com.evan.madkotlinweb3wallet.domain

// 钱包连接状态枚举
enum class WalletConnectionStatus {
    IDLE,       // 初始状态
    CONNECTING, // 连接/授权中
    SUCCESS,    // 授权成功
    FAILED      // 授权失败
}

// 钱包账户信息数据模型
data class WalletAccount(
    val address: String, // 钱包地址
    val name: String,    // 账户别名（如 Account 1）
)

// 整个钱包界面的 UI 状态
data class WalletUiState(
    val status: WalletConnectionStatus = WalletConnectionStatus.IDLE, // 当前连接状态
    val accounts: List<WalletAccount> = emptyList(),                 // 账户列表
    val selectedAccount: WalletAccount? = null,                       // 当前选中的账户
    val balance: String = "0.00",                                     // 当前余额
    val errorMessage: String? = null,                                 // 错误提示信息
)
