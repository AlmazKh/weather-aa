package com.almaz.weather_aa.ui.base

import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.almaz.weather_aa.App
import com.almaz.weather_aa.R
import com.almaz.weather_aa.ui.main.MainActivity
import com.almaz.weather_aa.utils.StatusBarState
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather.*
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

    fun setUpStatusBar(state: StatusBarState) {
        val decor: View = rootActivity.window.decorView
        when (state) {
            StatusBarState.LIGHT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                rootActivity.window.statusBarColor = resources.getColor(R.color.colorPrimary)
                rootActivity.window.clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }
            StatusBarState.TRANSPARENT -> {
                decor.systemUiVisibility = 0
                rootActivity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }
        }
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

    fun showLoading() {
        progress_weather_fragment.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progress_weather_fragment.visibility = View.GONE
    }
}
