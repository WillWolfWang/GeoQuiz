package com.will.geoquiz

import androidx.annotation.StringRes

// @StringRes 注解可以不加，但最好加上。首先，Android Studio 内置 Lint 代码检查器
// 有了该注解，它在编译时就知道构造函数会提供有效的资源 ID，如果提供了错误的会进行提示
// 阻止应用运行时崩溃；其次，注解可以方便其他人员阅读和理解
data class Question(@StringRes val textResId: Int, val answer: Boolean, var isAnswer: Boolean)