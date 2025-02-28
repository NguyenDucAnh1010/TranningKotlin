package com.ducanh.roomdemo.presentation.Task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ducanh.roomdemo.R
import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.data.model.UserWithTasks
import com.ducanh.roomdemo.databinding.FragmentTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment(private var userWithTasks: UserWithTasks) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        binding.edtUserId.setText(userWithTasks.user.id.toString())

        binding.edtChoice.setOnClickListener {
            val options = arrayOf("True", "False")

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Chọn giá trị")
                .setItems(options) { _, which ->
                    binding.edtChoice.setText(options[which])
                }
                .show()
        }

        binding.ibBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(userWithTasks: UserWithTasks): TaskFragment {
            val fragment = TaskFragment(userWithTasks)
            return fragment
        }
    }
}