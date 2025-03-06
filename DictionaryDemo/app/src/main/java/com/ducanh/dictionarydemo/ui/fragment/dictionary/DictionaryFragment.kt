package com.ducanh.dictionarydemo.ui.fragment.dictionary

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.dictionarydemo.R
import com.ducanh.dictionarydemo.data.entity.Word
import com.ducanh.dictionarydemo.databinding.FragmentDictionaryBinding
import com.ducanh.dictionarydemo.presentation.repository.DictionaryRepositoryImpl
import com.ducanh.dictionarydemo.ui.adapter.OnDictionaryClickListener
import com.ducanh.dictionarydemo.ui.fragment.detail.DetailFragment
import com.ducanh.dictionarydemo.ui.viewmodel.DictionaryViewModel
import com.ducanh.dictionarydemo.ui.viewmodel.DictionaryViewModelFactory
import com.ducanh.dictionarydemo.utils.Constants.DISPLAY_LIST
import com.example.androidtraining2.data.local.DictionaryDatabase
import com.example.androidtraining2.ui.adapter.WordAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DictionaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class DictionaryFragment : Fragment(), OnDictionaryClickListener, TextToSpeech.OnInitListener {
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

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordAdapter: WordAdapter
    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false

    private var index = 0

    private val viewModel by viewModels<DictionaryViewModel> {
        DictionaryViewModelFactory(
            DictionaryRepositoryImpl(
                DictionaryDatabase.getDatabase(requireContext())!!
                    .wordDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)

        binding.rvWords.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvWords.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (dy > 0 && lastVisibleItem >= totalItemCount - 2  && totalItemCount >= DISPLAY_LIST) {
                    lifecycleScope.launch {
                        showLoading(true)
                        delay(1500)
                        index += DISPLAY_LIST
                        viewModel.getAllWord(index)
                        viewModel.words.value?.let {
                            if (it.isEmpty()) {
                                index -= DISPLAY_LIST
                                viewModel.getAllWord(index)
                            }
                        }
                        showLoading(false)
                    }
                }

                if (dy < 0 && firstVisibleItem <= 2 && index > 0) {
                    lifecycleScope.launch {
                        showLoading(true)
                        delay(1500)
                        index -= DISPLAY_LIST
                        if (index < 0) index = 0
                        viewModel.getAllWord(index)
                        showLoading(false)
                    }
                }
            }
        })

        viewModel.words.observe(viewLifecycleOwner) {
            wordAdapter = WordAdapter(requireContext(), it, this)
            binding.rvWords.adapter = wordAdapter
        }

        viewModel.getAllWord(index)

        var sharedPref =
            requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        var speed = sharedPref.getFloat("speed", 1.0f)

        context?.let {
            tts = TextToSpeech(it, this)
            tts?.setPitch(speed)
        }

        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onWordClick(word: Word) {
        val bundle = Bundle()
        bundle.putSerializable("word", word)
        val fragment = DetailFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onSoundClick(word: Word) {
        if (isTtsInitialized) {
            tts?.speak(word.word, TextToSpeech.QUEUE_ADD, null, null)
        } else {
            Log.e("TTS", "TextToSpeech chưa sẵn sàng!")
        }
    }

    override fun onfavouriteClick(word: Word) {
        if (word.isFavorite){
            AlertDialog.Builder(requireContext())
                .setTitle("Confirm")
                .setMessage("Are you sure you want to unfavorite this word?\n")
                .setPositiveButton("Yes") { _, _ ->
                    word.isFavorite = false
                    viewModel.updateAllWord(word, index)
                    Toast.makeText(requireContext(), "Unfavorite ${word.word} success!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }else{
            word.isFavorite = true
            viewModel.updateAllWord(word, index)
            Toast.makeText(requireContext(), "Added ${word.word} to favorites success!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onShareClick(word: Word) {
        shareText(requireContext(), "${word.word}\nmean: ${word.mean}")
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DictionaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DictionaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}