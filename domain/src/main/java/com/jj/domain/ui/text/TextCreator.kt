package com.jj.domain.ui.text

interface TextCreator<T> {

    fun buildColoredString(vararg textComponents: TextComponent): T
}