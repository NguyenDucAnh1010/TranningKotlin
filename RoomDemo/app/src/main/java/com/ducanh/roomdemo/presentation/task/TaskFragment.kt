package com.ducanh.roomdemo.presentation.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ducanh.roomdemo.data.dao.TaskDao
import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.data.model.UserWithTasks
import com.ducanh.roomdemo.data.room.UserDatabase
import com.ducanh.roomdemo.databinding.FragmentTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    private lateinit var taskAdapter: TaskAdapter
//    private lateinit var userDatabase: UserDatabase
//    private lateinit var viewModel: TaskViewModel

    private lateinit var dao: TaskDao
    private val viewModel: TaskViewModel by viewModels { TaskViewModelFactory(userWithTasks.user.id, dao) }

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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        val userId = userWithTasks.user.id
        binding.edtUserId.setText(userId.toString())

        binding.edtChoice.setOnClickListener {
            val options = arrayOf("true", "false")

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

        binding.btnCreateNew.setOnClickListener {
            binding.btnCreateNew.setOnClickListener {
                val name = binding.edtName.text ?: ""
                val choiceTxt = binding.edtChoice.text ?: ""

                if (name.isNotEmpty() && choiceTxt.isNotEmpty()) {
                    val choice = choiceTxt.toString().toBooleanStrictOrNull()
                    if (choice != null) {
                        val task =
                            Task(userId = userId, taskName = name.toString(), isCompleted = choice)
                        viewModel.addTask(task)
                        taskAdapter.refresh()
                    }
                }
            }
        }

        binding.rvTasks.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

//        userDatabase = UserDatabase.getDatabase(requireContext())!!
//
//        val repository = TaskRepositoryImpl(userDatabase.taskDao())
//
//        viewModel = TaskViewModel(repository)
//
//        viewModel.tasks.observe(requireActivity()) {
//            taskAdapter = TaskAdapter(it)
//            binding.rvTasks.adapter = taskAdapter
//        }
//
//        viewModel.getTasksByUserId(userId)

        dao = UserDatabase.getDatabase(requireContext())!!.taskDao()

        taskAdapter = TaskAdapter(userWithTasks.tasks)
        binding.rvTasks.adapter = taskAdapter.withLoadStateFooter(
            TaskLoadStateAdapter()
        )

        lifecycleScope.launch {
            viewModel.data.collectLatest {
                taskAdapter.submitData(it)
            }
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