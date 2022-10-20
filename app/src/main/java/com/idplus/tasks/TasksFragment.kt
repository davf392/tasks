package com.idplus.tasks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.idplus.tasks.data.TaskDatabase
import com.idplus.tasks.databinding.FragmentTasksBinding


class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao
        val viewModelFactory = TasksViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TasksViewModel::class.java)

        binding.tasksViewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        val tasksAdapter = TaskItemAdapter()
        binding.tasksList.adapter = tasksAdapter

        binding.buttonSave.setOnClickListener {
            viewModel.addTask()
            binding.taskName.text.clear()
        }

        // pass the list of tasks as live data to the adapter
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                tasksAdapter.submitList(it) {
                    binding.tasksList.scrollToPosition(0)
                }
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}