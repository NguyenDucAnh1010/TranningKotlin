package com.ducanh.dictionarydemo.ui.fragment.detail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ducanh.dictionarydemo.R
import com.ducanh.dictionarydemo.data.entity.Word
import com.ducanh.dictionarydemo.databinding.FragmentDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val word = arguments?.getSerializable("word") as? Word
        word?.let {
            binding.customToolBar.tvWord.text = word.word
            word.av?.let {
                setHtmlText(binding.tvAv, word.av)
            }
        }

        val sharedPref =
            requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        val speed = sharedPref.getFloat("speed", 1.0f)

        context?.let {
            tts = TextToSpeech(it, this)
            tts?.setPitch(speed)
        }

        binding.customToolBar.ibSound.setOnClickListener {
            if (isTtsInitialized) {
                tts?.speak("Fuck Your Mother", TextToSpeech.QUEUE_ADD, null, null)
            } else {
                Log.e("TTS", "TextToSpeech chưa sẵn sàng!")
            }
        }

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE

        binding.customToolBar.ibBack.setOnClickListener {
            bottomNavigationView.visibility = View.VISIBLE
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    fun setHtmlText(textView: TextView, htmlByteArray: ByteArray) {
        val htmlString = htmlByteArray.toString(Charsets.UTF_8)
        val spanned: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(htmlString)
        }
        textView.text = spanned
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(word: Word?): DetailFragment {
            val args = Bundle().apply {
                putSerializable("word", word)
            }

            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}