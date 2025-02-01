package ru.practicum.android.diploma.ui.filter.industry.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.filter.industry.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.util.coroutine.CoroutineUtils

class FilterIndustryFragment : Fragment() {

    private companion object {
        const val DELAY = 2000L
    }

    private enum class PlaceholderState {
        ERROR, LOAD
    }

    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterIndustryViewModel by viewModel()
    private var adapter: IndustryAdapter? = null
    private var selectedIndustry: Industry? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearch()

        adapter = IndustryAdapter { selectedIndustry ->
            adapter?.selectIndustry(selectedIndustry)
            binding.buttonSelect.isVisible = true
        }

        binding.industryRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FilterIndustryFragment.adapter
        }

        viewModel.industries.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    renderList(PlaceholderState.LOAD)
                    resource.value?.let {
                        adapter?.industriesFull = resource.value
                    }
                    adapter?.submitList(resource.value)
                }

                is Resource.Error -> renderList(PlaceholderState.ERROR)
            }
        }

        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderList(state: PlaceholderState) {
        when (state) {
            PlaceholderState.ERROR -> {
                binding.errorPlaceholderIndustry.isVisible = true
                binding.industryRecyclerView.isVisible = false
                binding.buttonSelect.isVisible = false
            }

            PlaceholderState.LOAD -> {
                binding.errorPlaceholderIndustry.isVisible = false
                binding.industryRecyclerView.isVisible = true
                binding.buttonSelect.isVisible = false
            }
        }
    }

    private fun setupListeners() {
        binding.buttonSelect.setOnClickListener {
            val bundle = Bundle().apply {
                putString("industry_id", selectedIndustry?.id)
                putString("industry_name", selectedIndustry?.name)
            }
            findNavController().navigate(R.id.action_filterIndustryFragment_to_filterCommonFragment, bundle)
        }
    }

    private fun setupSearch() {
        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            CoroutineUtils.debounce(viewLifecycleOwner.lifecycleScope, DELAY) {
                val query = text?.toString()?.trim().orEmpty()
                adapter?.filter(query)
            }
        }
    }
}
