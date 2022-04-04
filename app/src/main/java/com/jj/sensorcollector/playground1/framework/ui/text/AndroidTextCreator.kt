package com.jj.sensorcollector.playground1.framework.ui.text

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.text.toSpannable
import com.jj.sensorcollector.playground1.domain.ui.DomainColor
import com.jj.sensorcollector.playground1.domain.ui.text.TextComponent
import com.jj.sensorcollector.playground1.domain.ui.text.TextCreator
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toTextColor

class AndroidTextCreator : TextCreator<Spannable> {

    override fun buildColoredString(vararg textComponents: TextComponent): Spannable {
        val spannableStringBuilder = SpannableStringBuilder()
        textComponents.forEach { component ->
            spannableStringBuilder.append(colourString(component))
        }
        return spannableStringBuilder.toSpannable()
    }

    private fun colourString(component: TextComponent) =
        SpannableString(component.content).apply {
            if (component.domainColor !is DomainColor.Default) {
                setSpan(
                    ForegroundColorSpan(component.domainColor.toTextColor()),
                    0,
                    component.content.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
}