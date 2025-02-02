package ru.practicum.android.diploma.ui.filter.country.fragment

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemListBinding
import ru.practicum.android.diploma.domain.models.Area

class AreaViewHolder(
    private val binding: ItemListBinding,
    private val onClick: (id: String, name: String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Area) {
        binding.itemText.text = model.name
        binding.root.setOnClickListener { _ -> model.name?.let { onClick(model.id, it) } }
    }
}
