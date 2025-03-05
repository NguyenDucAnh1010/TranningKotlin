package com.ducanh.dictionarydemo.ui.fragment.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.ducanh.dictionarydemo.R
import com.ducanh.dictionarydemo.databinding.FragmentSettingsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        var sharedPref =
            requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        var speed = sharedPref.getFloat("speed", 1.0f) // Giá trị mặc định là 1.0f nếu chưa có
        binding.seekBar.progress = (speed * 50).toInt()

        var formattedText = getString(R.string.settings_speed, "${speed}")
        binding.tvSpeed.text = formattedText

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                speed = progress / 50.0f
                val speedText = String.format("%.1f", speed)
                formattedText = getString(R.string.settings_speed, "${speedText}")
                binding.tvSpeed.text = formattedText
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                with(sharedPref.edit()) {
                    putFloat("speed", speed)
                    apply()
                }
            }
        })

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedSize = when (checkedId) {
                R.id.rbSmall -> 14f
                R.id.rbNormal -> 19f
                R.id.rbLarge -> 24f
                R.id.rbExtraLarge -> 32f
                else -> 19f
            }
            binding.tvTestFontSize.textSize = selectedSize
            sharedPref.edit().putFloat("textSize", selectedSize).apply()
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}