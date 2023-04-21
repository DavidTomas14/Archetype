package com.david.archetype.fundaments.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.david.archetype.fundaments.extensions.EXTRA_SCREEN_THEME

abstract class DTActivity<S : DTState>(
    private val layoutId: Int,
    private val graphId: Int? = null,
    private val navControllerId: Int? = null,
    private val isScreenShotDisabled: Boolean = false
) : AppCompatActivity(), DTStateListener<S> {

    private var innerNavigator: NavController? = null

    protected open val binding: ViewBinding
        get() = throw ViewBindingRuntimeException("You must define binding")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(binding.root)
        } catch (e: ViewBindingRuntimeException) {
            setContentView(layoutId)
        }
        if (navControllerId != null && graphId != null) {
            findNavController(navControllerId).setGraph(graphId, intent?.extras)
        }
    }

    @CallSuper
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? =
        super.onCreateView(name, context, attrs)?.apply {
            innerNavigator = Navigation.findNavController(this)
        }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        intent?.extras?.let {
            if (it.containsKey(EXTRA_SCREEN_THEME)) {
                theme.applyStyle(it.getInt(EXTRA_SCREEN_THEME), true)
            }
        }
        return theme
    }

    override fun onStateChanged(state: S) {
        /** to override for state Change **/
    }


    private class ViewBindingRuntimeException(msg: String) : RuntimeException(msg)
}