package com.david.archetype.fundaments.extensions

import android.content.Intent
import androidx.annotation.StyleRes

internal const val EXTRA_SCREEN_THEME = "screenTheme"

fun Intent.putTheme(@StyleRes theme: Int?) {
    theme?.let { putExtra(EXTRA_SCREEN_THEME, theme) }
}