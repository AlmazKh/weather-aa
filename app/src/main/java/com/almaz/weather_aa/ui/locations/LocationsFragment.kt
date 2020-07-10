package com.almaz.weather_aa.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.weather_aa.R
import com.almaz.weather_aa.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_locations.*
import kotlinx.android.synthetic.main.fragment_weather.*
import org.kodein.di.generic.instance

const val SPACE_BETWEEN_ITEMS = 8

class LocationsFragment : BaseFragment() {

    private val viewModelFactory: ViewModelProvider.Factory by instance()
    private val viewModel: LocationsViewModel by lazy {
        ViewModelProvider(this, this.viewModelFactory)
            .get(LocationsViewModel::class.java)
    }
    private lateinit var locationsAdapter: LocationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_locations.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        initAdapter()

        btn_back.setOnClickListener {
            rootActivity.navController.navigateUp()
        }

        viewModel.getLocations()
        observeLocationsLiveData()
    }

    private fun initAdapter() {
        locationsAdapter = LocationsAdapter()
        rv_locations.adapter = locationsAdapter
        rv_locations.addItemDecoration(
            SpacingBetweenItemsDecoration(SPACE_BETWEEN_ITEMS)
        )
    }

    private fun observeLocationsLiveData() =
        viewModel.locationsWeatherLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    locationsAdapter.submitList(it.data)
                    rv_locations.adapter = locationsAdapter
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })
}