package com.almaz.weather_aa.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.almaz.weather_aa.App
import com.almaz.weather_aa.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

open class BaseFragment : Fragment(), KodeinAware {
    lateinit var rootActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        rootActivity = activity as MainActivity
    }

    override val kodein: Kodein by lazy {
        (rootActivity.application as App).kodein
    }

    fun showSnackbar(message: String) {
        view?.let {
            Snackbar.make(
                it,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}