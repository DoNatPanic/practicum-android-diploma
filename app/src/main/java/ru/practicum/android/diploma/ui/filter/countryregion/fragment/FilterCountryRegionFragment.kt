package ru.practicum.android.diploma.ui.filter.countryregion.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterCountryRegionBinding
import ru.practicum.android.diploma.ui.filter.countryregion.viewmodel.FilterCountryRegionViewModel
import ru.practicum.android.diploma.util.FilterNames

class FilterCountryRegionFragment : Fragment() {

    private var binding: FragmentFilterCountryRegionBinding? = null

    private val viewModel: FilterCountryRegionViewModel by lazy {
        FilterCountryRegionViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterCountryRegionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton()
        setupListeners()
        selectButton()
        handleArgumentsAndResults()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.countryName.combine(viewModel.regionName) { countryName, regionName ->
                countryName to regionName
            }.collect { (countryName, regionName) ->
                renderFields(countryName, regionName)
            }
        }
    }

    private fun handleArgumentsAndResults() {
        arguments?.let { bundle ->
            updateViewModelFromBundle(bundle)
        }

        parentFragmentManager.setFragmentResultListener(FilterNames.COUNTRY_RESULT, viewLifecycleOwner) { _, bundle ->
            updateViewModelFromBundle(bundle)
        }

        parentFragmentManager.setFragmentResultListener(FilterNames.REGION_RESULT, viewLifecycleOwner) { _, bundle ->
            updateViewModelFromBundle(bundle)
        }
    }

    private fun updateViewModelFromBundle(bundle: Bundle) {
        val countryId = bundle.getString(FilterNames.COUNTRY_ID)
        val countryName = bundle.getString(FilterNames.COUNTRY_NAME)
        val regionId = bundle.getString(FilterNames.REGION_ID)
        val regionName = bundle.getString(FilterNames.REGION_NAME)

        viewModel.setCountry(countryId, countryName)
        viewModel.setRegion(regionId, regionName)
    }

    private fun backButton() {
        binding?.toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupListeners() {
        binding?.let {
            setupFieldListeners(
                view = it.country,
                onClickEmptyField = {
                    findNavController().navigate(R.id.action_filterCountryRegionFragment_to_filterCountryFragment)
                },
            )
        }
        binding?.let {
            setupFieldListeners(
                view = it.region,
                onClickEmptyField = {
                    val bundle = Bundle().apply {
                        putString(FilterNames.COUNTRY_ID, viewModel.countryId.value)
                    }
                    findNavController().navigate(
                        R.id.action_filterCountryRegionFragment_to_filterRegionFragment,
                        bundle
                    )
                },
            )
        }
    }

    private fun setupFieldListeners(
        view: TextInputLayout,
        onClickEmptyField: () -> Unit,
    ) {
        view.editText?.setOnClickListener {
            onClickEmptyField()
        }

        view.setEndIconOnClickListener {
            if (view.editText?.text.isNullOrEmpty()) {
                onClickEmptyField()
            } else {
                renderField(view = view, text = null)
            }
        }
    }

    private fun renderFields(countryName: String?, regionName: String?) {
        renderField(binding?.country, countryName)
        renderField(binding?.region, if (countryName.isNullOrEmpty()) null else regionName)
        updateSelectButtonVisibility()
    }

    private fun renderField(view: TextInputLayout?, text: String?) {
        view?.editText?.setText(text)
        val typedArray = context?.theme?.obtainStyledAttributes(
            intArrayOf(R.attr.blackToWhite)
        )
        val colorStateList = typedArray?.getColorStateList(0)
        typedArray?.recycle()

        when {
            text.isNullOrEmpty() -> {
                view?.setEndIconDrawable(R.drawable.ic_arrow_forward_48)
                view?.defaultHintTextColor = requireContext().getColorStateList(R.color.gray)
            }

            else -> {
                view?.setEndIconDrawable(R.drawable.ic_close_24)
                view?.defaultHintTextColor = colorStateList
            }
        }
    }

    private fun updateSelectButtonVisibility() {
        val isCountrySelected = !binding?.country?.editText?.text.isNullOrEmpty()
        val isRegionSelected = !binding?.region?.editText?.text.isNullOrEmpty()
        binding?.buttonSelect?.visibility = if (isCountrySelected || isRegionSelected) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun selectButton() {
        binding?.buttonSelect?.setOnClickListener {
            val regionBundle = Bundle().apply {
                putString(FilterNames.COUNTRY_ID, viewModel.countryId.value)
                putString(FilterNames.COUNTRY_NAME, viewModel.countryName.value)
                putString(FilterNames.REGION_ID, viewModel.regionId.value)
                putString(FilterNames.REGION_NAME, viewModel.regionName.value)
            }
            parentFragmentManager.setFragmentResult(FilterNames.COUNTRY_REGION_RESULT, regionBundle)
            findNavController().popBackStack()
        }
    }
}
