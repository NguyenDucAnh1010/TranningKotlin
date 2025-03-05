package com.ducanh.dictionarydemo.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ducanh.dictionarydemo.R
import com.ducanh.dictionarydemo.data.entity.Word
import com.ducanh.dictionarydemo.databinding.FragmentFavoriteBinding
import com.ducanh.dictionarydemo.presentation.repository.DictionaryRepositoryImpl
import com.ducanh.dictionarydemo.ui.adapter.OnDictionaryClickListener
import com.ducanh.dictionarydemo.ui.fragment.detail.DetailFragment
import com.ducanh.dictionarydemo.ui.viewmodel.DictionaryViewModel
import com.ducanh.dictionarydemo.ui.viewmodel.DictionaryViewModelFactory
import com.example.androidtraining2.data.local.DictionaryDatabase
import com.example.androidtraining2.ui.adapter.WordAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment(), OnDictionaryClickListener {
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

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var wordAdapter: WordAdapter

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
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.rvWords.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.words.observe(viewLifecycleOwner) {
            wordAdapter = WordAdapter(requireContext(),it, this)
            binding.rvWords.adapter = wordAdapter
        }

        viewModel.getAllFavoriteWord()

        return binding.root
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

    }

    override fun onfavouriteClick(word: Word) {
        word.isFavorite = false
        viewModel.updateWord(word)
        wordAdapter.notifyDataSetChanged()
    }

    override fun onShareClick(word: Word) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}