package com.example.touristtrips.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.touristtrips.databinding.FragmentSettingsBinding
import java.util.*
import android.app.Activity


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lithuanianTextView.setOnClickListener {
            setLocale(requireActivity(), "lt")
            requireActivity().recreate()
        }

        binding.englishTextView.setOnClickListener {
            setLocale(requireActivity(), "en")
            requireActivity().recreate()
        }
    }

    private fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    /*private fun setCurrentLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSelectedLanguage(requireContext().resources.configuration.locales.get(0).toLanguageTag())
        } else {
            setSelectedLanguage(requireContext().resources.configuration.locale.toLanguageTag())
        }
    }

    private fun setSelectedLanguage(language: String) {
        when (language) {
            "lt" -> binding.lithuanianTextView.setTextColor(Color.MAGENTA)
            "en" -> binding.lithuanianTextView.setTextColor(Color.MAGENTA)
        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
