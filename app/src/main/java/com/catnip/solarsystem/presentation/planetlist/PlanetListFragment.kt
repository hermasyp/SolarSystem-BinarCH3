package com.catnip.solarsystem.presentation.planetlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.catnip.solarsystem.data.PlanetDataSource
import com.catnip.solarsystem.data.PlanetDataSourceImpl
import com.catnip.solarsystem.databinding.FragmentPlanetListBinding
import com.catnip.solarsystem.model.Planet
import com.catnip.solarsystem.presentation.planetlist.adapter.AdapterLayoutMode
import com.catnip.solarsystem.presentation.planetlist.adapter.PlanetListAdapter

class PlanetListFragment : Fragment() {

    private lateinit var binding: FragmentPlanetListBinding

    private val datasource: PlanetDataSource by lazy {
        PlanetDataSourceImpl()
    }
    private val adapter: PlanetListAdapter by lazy {
        PlanetListAdapter(AdapterLayoutMode.LINEAR) { planet: Planet ->
            navigateToDetail(planet)
        }
    }

    private fun navigateToDetail(planet: Planet) {
        findNavController().navigate(
            PlanetListFragmentDirections.actionPlanetListFragmentToPlanetDetailFragment(planet)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPlanetListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupSwitch()
    }

    private fun setupList() {
        val span = if(adapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvPlanet.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this@PlanetListFragment.adapter
        }
        adapter.submitData(datasource.getPlanets())
    }

    private fun setupSwitch() {
        binding.switchListGrid.setOnCheckedChangeListener { _, isChecked ->
            (binding.rvPlanet.layoutManager as GridLayoutManager).spanCount = if (isChecked) 2 else 1
            adapter.adapterLayoutMode = if(isChecked) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            adapter.refreshList()
        }
    }

}