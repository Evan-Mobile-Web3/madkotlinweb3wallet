// 1. 引入 Express 框架。require 类似于 Kotlin 的 import，把 express 库加载进来
const express = require('express');

// 2. 创建一个 Express 应用实例，app 就是我们后端的“核心指挥部”
const app = express();

// 3. 定义后端运行的端口号。3000 是 Node.js 社区默认习惯用的端口
const port = 3000;

// 4. [模拟数据库变量]：在内存中存一个布尔值，决定用户是否能登录
let isLoginAllowed = true;

// 5. [模拟数据库变量]：记录移动端一共发来了多少次请求
let messageCount = 0;

/**
 * 接口 A: 给移动端使用的状态检查接口
 * app.get 表示这是一个 HTTP GET 请求接口
 * '/status' 是请求的路径 (例如: http://localhost:3000/status)
 * (req, res) 是回调函数：req (request) 包含请求信息，res (response) 用于给对方回话
 */
app.get('/status', (req, res) => {
    // 每次移动端访问，计数器加 1
    messageCount++;

    // 在 Mac 的控制台打印一行日志，方便我们观察
    console.log(`[Mobile] 收到移动端轮询, 次数: ${messageCount}`);

    // 逻辑判断：如果后台关闭了登录权限
    if (!isLoginAllowed) {
        // res.json 会把 JS 对象转成标准的 JSON 字符串发给手机
        return res.json({
            status: "forbidden",
            message: "已限制登录",
            code: 403
        });
    }

    // 如果权限正常，返回正常的消息
    res.json({
        status: "ok",
        message: `接收到服务端消息 (第 ${messageCount} 次)`,
        code: 200
    });
});

/**
 * 接口 B: 模拟管理后台切换状态的接口
 * 实际开发中，这应该是管理后台（React 页面）点按钮时触发的
 */
app.get('/toggle', (req, res) => {
    // 把 true 变 false，或者把 false 变 true (取反)
    isLoginAllowed = !isLoginAllowed;

    // 定义一个文案用于控制台显示
    const state = isLoginAllowed ? "允许登录" : "禁止登录";

    console.log(`[Admin] 管理后台切换了状态: ${state}`);

    // res.send 返回的是普通字符串，不是 JSON，适合给人看
    res.send(`管理后台操作成功：当前状态已切换为 [${state}]`);
});

/**
 * 启动服务：让指挥部开始在指定的端口“摆摊营业”
 */
app.listen(port, () => {
    console.log(`-------------------------------------------`);
    console.log(`轻量级后端已启动！`);
    console.log(`Mac 本地访问地址: http://localhost:${port}`);
    // 10.0.2.2 是 Android 模拟器访问电脑主机的特殊 IP 映射
    console.log(`模拟器访问地址: http://10.0.2.2:${port}`);
    console.log(`-------------------------------------------`);
});
