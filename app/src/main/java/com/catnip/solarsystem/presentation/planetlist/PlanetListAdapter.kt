package com.catnip.solarsystem.presentation.planetlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.catnip.solarsystem.core.ViewHolderBinder
import com.catnip.solarsystem.databinding.ItemGridPlanetBinding
import com.catnip.solarsystem.databinding.ItemLinearPlanetBinding
import com.catnip.solarsystem.model.Planet

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
enum class AdapterLayoutMode {
    GRID,
    LIST
}

class PlanetListAdapter(
    var adapterLayoutMode: AdapterLayoutMode,
    private val onItemClicked: (Planet) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Planet>() {
        override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    fun submitData(list: List<Planet>) = dataDiffer.submitList(list)

    fun refreshList() {
        notifyItemRangeChanged(0, dataDiffer.currentList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            AdapterLayoutMode.LIST.ordinal -> LinearPlanetItemViewHolder(
                ItemLinearPlanetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onItemClicked
            )

            else -> GridPlanetItemViewHolder(
                ItemGridPlanetBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onItemClicked
            )
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataDiffer.currentList[position]
        when (holder) {
            is GridPlanetItemViewHolder -> holder.bind(item)
            is LinearPlanetItemViewHolder -> holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }
}

class GridPlanetItemViewHolder(
    private val binding: ItemGridPlanetBinding,
    private val onItemClicked: (Planet) -> Unit
) : ViewHolder(binding.root), ViewHolderBinder<Planet> {

    override fun bind(item: Planet) {
        binding.root.setOnClickListener {
            onItemClicked.invoke(item)
        }
        binding.ivPlanet.load(item.imgUrl)
        binding.tvPlanetName.text = item.name
    }
}

class LinearPlanetItemViewHolder(
    private val binding: ItemLinearPlanetBinding,
    private val onItemClicked: (Planet) -> Unit
) : ViewHolder(binding.root), ViewHolderBinder<Planet> {

    override fun bind(item: Planet) {
        binding.root.setOnClickListener {
            onItemClicked.invoke(item)
        }
        binding.ivPlanet.load(item.imgUrl)
        binding.tvPlanetName.text = item.name
    }
}