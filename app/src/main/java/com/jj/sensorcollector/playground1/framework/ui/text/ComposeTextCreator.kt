package com.jj.sensorcollector.playground1.framework.ui.text

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.jj.sensorcollector.playground1.domain.ui.text.TextComponent
import com.jj.sensorcollector.playground1.domain.ui.text.TextCreator
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toTextColor

class ComposeTextCreator : TextCreator<AnnotatedString> {

    override fun buildColoredString(vararg textComponents: TextComponent): AnnotatedString =
        buildAnnotatedString {
            textComponents.forEach { component ->
                withStyle(style = SpanStyle(Color(component.domainColor.toTextColor()))) {
                    append(component.content)
                }
            }
        }
}