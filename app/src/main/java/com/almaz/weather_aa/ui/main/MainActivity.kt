package com.almaz.weather_aa.ui.main

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.almaz.weather_aa.App
import com.almaz.weather_aa.R
import com.almaz.weather_aa.ui.base.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class MainActivity : BaseActivity(), KodeinAware {

    private val viewModeFactory: ViewModelProvider.Factory by instance()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, this.viewModeFactory)
            .get(MainViewModel::class.java)
    }

    val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override val kodein: Kodein
        get() = App.app.kodein

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun setupView() {}

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
