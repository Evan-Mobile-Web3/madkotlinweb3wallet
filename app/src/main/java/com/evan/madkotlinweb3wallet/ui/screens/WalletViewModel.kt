package com.evan.madkotlinweb3wallet.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evan.madkotlinweb3wallet.domain.WalletAccount
import com.evan.madkotlinweb3wallet.domain.WalletConnectionStatus
import com.evan.madkotlinweb3wallet.domain.WalletUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random

/**
 * 钱包业务逻辑处理器
 */
class WalletViewModel : ViewModel() {

    // 内部持有的 UI 状态流，初始为 IDLE
    private val _uiState = MutableStateFlow(WalletUiState())
    // 暴露给外部（Compose）的只读状态流
    val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()

    // 定时器任务（心跳）
    private var heartbeatJob: Job? = null

    /**
     * 第一步：请求连接 MetaMask
     */
    fun connectMetaMask() {
        _uiState.update { it.copy(status = WalletConnectionStatus.CONNECTING, errorMessage = null) }
        
        // 模拟网络或 MetaMask 响应延迟
        viewModelScope.launch {
            delay(2000) // 停留在中转页/授权页 2 秒
            
            // 模拟 80% 概率成功
            if (Random.nextFloat() > 0.2) {
                val mockAccounts = listOf(
                    WalletAccount("0x123...abc", "Main Account"),
                    WalletAccount("0x456...def", "Savings Account"),
                    WalletAccount("0x789...ghi", "DeFi Wallet"),
                )
                _uiState.update { 
                    it.copy(
                        status = WalletConnectionStatus.SUCCESS,
                        accounts = mockAccounts,
                        selectedAccount = mockAccounts[0],
                    )
                }
                // 授权成功后开启心跳实时刷新余额
                startBalanceHeartbeat()
            } else {
                _uiState.update { 
                    it.copy(
                        status = WalletConnectionStatus.FAILED,
                        errorMessage = "用户拒绝授权或连接超时"
                    )
                }
            }
        }
    }

    /**
     * 第二步：切换当前选中的账户
     */
    fun selectAccount(account: WalletAccount) {
        _uiState.update { it.copy(selectedAccount = account) }
        // 切换账户后立即刷新一次余额
        refreshBalance()
    }

    /**
     * 第三步：重置状态（返回第一页时调用）
     */
    fun resetConnection() {
        stopBalanceHeartbeat()
        _uiState.update { WalletUiState() }
    }

    /**
     * 核心逻辑：定时心跳刷新余额
     */
    private fun startBalanceHeartbeat() {
        // 防止启动多个 Job
        heartbeatJob?.cancel()
        heartbeatJob = viewModelScope.launch {
            while (true) {
                refreshBalance()
                delay(5000) // 每 5 秒刷新一次
            }
        }
    }

    /**
     * 实际刷新余额的方法
     */
    private fun refreshBalance() {
        // 通过 .value 获取当前 Flow 中的最新状态
        _uiState.value.selectedAccount?.let {
            // 模拟从区块链获取余额
            val randomBalance = String.format(Locale.US, "%.4f", Random.nextDouble(0.1, 10.5))
            _uiState.update { state -> state.copy(balance = "$randomBalance ETH") }
        }
    }

    /**
     * 停止心跳
     */
    private fun stopBalanceHeartbeat() {
        heartbeatJob?.cancel()
    }

    /**
     * ViewModel 销毁时停止心跳，防止内存泄漏
     */
    override fun onCleared() {
        super.onCleared()
        stopBalanceHeartbeat()
    }
}
