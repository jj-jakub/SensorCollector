package com.jj.core.domain.ui.text

interface TextCreator<T> {

    fun buildColoredString(vararg textComponents: TextComponent): T
}