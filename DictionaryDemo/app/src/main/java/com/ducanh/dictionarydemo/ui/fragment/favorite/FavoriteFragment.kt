package com.ducanh.dictionarydemo.ui.fragment.favorite

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
import com.ducanh.dictionarydemo.databinding.FragmentFavoriteBinding
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

class FavoriteFragment : Fragment(), OnDictionaryClickListener, TextToSpeech.OnInitListener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordAdapter: WordAdapter
    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false

    private var index = 0
    private var count = 0

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
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.rvWords.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvWords.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (dy > 0 && lastVisibleItem >= totalItemCount - 1 && totalItemCount >= DISPLAY_LIST - count) {
                    lifecycleScope.launch {
                        showLoading(true)
                        delay(1000)
                        index += DISPLAY_LIST
                        val prevSize = viewModel.words.value?.size ?: 0
                        viewModel.getAllFavoriteWord(index)

                        viewModel.words.observeForever {
                            if (it.size >= prevSize) {
                                wordAdapter.notifyItemRangeInserted(prevSize, index+1)
                            } else {
                                index -= DISPLAY_LIST
                            }
                        }
                        showLoading(false)
                    }
                }
            }
        })

        wordAdapter = WordAdapter(requireContext(), mutableListOf(), this)
        binding.rvWords.adapter = wordAdapter
        viewModel.words.observe(viewLifecycleOwner) {
            wordAdapter.updateList(it)
        }

        viewModel.getAllFavoriteWord(index)

        val sharedPref =
            requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        val speed = sharedPref.getFloat("speed", 1.0f)

        context?.let {
            tts = TextToSpeech(it, this)
            tts?.setPitch(speed)
        }

        return binding.root
    }

    override fun onWordClick(word: Word) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, DetailFragment.newInstance(word))
            .addToBackStack(null)
            .commit()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSoundClick(word: Word) {
        if (isTtsInitialized) {
            tts?.speak(word.word, TextToSpeech.QUEUE_ADD, null, null)
        } else {
            Log.e("TTS", "TextToSpeech chưa sẵn sàng!")
        }
    }

    override fun onfavouriteClick(word: Word) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm")
            .setMessage("Are you sure you want to unfavorite this word?\n")
            .setPositiveButton("Yes") { _, _ ->
                word.isFavorite = false
                viewModel.updateFavouriteWord(word)
                count++
                Toast.makeText(
                    requireContext(),
                    "Unfavorite ${word.word} success!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
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
        @JvmStatic
        fun newInstance() =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}