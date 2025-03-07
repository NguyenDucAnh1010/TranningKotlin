package com.ducanh.dictionarydemo.ui.fragment.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.ducanh.dictionarydemo.R
import com.ducanh.dictionarydemo.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment(), TextToSpeech.OnInitListener {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        var sharedPref =
            requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        var speed = sharedPref.getFloat("speed", 1.0f)
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

        context?.let {
            tts = TextToSpeech(it, this)
            tts?.setPitch(speed)
        }

        binding.btnTestSound.setOnClickListener {
            if (isTtsInitialized) {
                tts?.speak("Fuck Your Mother", TextToSpeech.QUEUE_ADD, null, null)
            } else {
                Log.e("TTS", "TextToSpeech chưa sẵn sàng!")
            }
        }

        var textSize = sharedPref.getFloat("textSize", 1.0f)
        when (textSize) {
            14f -> binding.rbSmall.isChecked = true
            19f -> binding.rbNormal.isChecked = true
            24f -> binding.rbLarge.isChecked = true
            32f -> binding.rbExtraLarge.isChecked = true
            else -> binding.rbNormal.isChecked = true
        }

        binding.tvTestFontSize.textSize = textSize

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

        binding.btnShare.setOnClickListener {
            shareText(requireContext(), "https://app.slack.com/client/T05QVLHPLVB/D08F46489B5")
        }

        return binding.root
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Ngôn ngữ không được hỗ trợ!")
            } else {
                isTtsInitialized = true
            }
        } else {
            Log.e("TTS", "Khởi tạo TTS thất bại!")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tts?.stop()
        tts?.shutdown()
    }

    fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)

        context.startActivity(Intent.createChooser(intent, "Chia sẻ từ vựng"))
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}