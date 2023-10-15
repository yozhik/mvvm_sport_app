package com.example.myapplication.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentScheduleBinding
import com.example.myapplication.ui.adapter.GenericAdapter
import com.example.myapplication.ui.adapter.viewholder.ScheduleEventViewHolder
import com.example.myapplication.ui.common.UIRequestState
import com.example.myapplication.ui.model.ScheduleEventUiModel

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val viewModel: ScheduleViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = GenericAdapter<ScheduleEventUiModel, ScheduleEventViewHolder>(
            onClick = {},
            viewHolderCreator = { view, onClick ->
                ScheduleEventViewHolder(view, onClick)
            }
        )
        binding.rvEvents.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvEvents.adapter = adapter

        binding.rootSwipeToRefresh.setOnRefreshListener {
            viewModel.loadScheduleEvents()
        }

        viewModel.scheduleEvents.observe(viewLifecycleOwner) { uiState ->
            handleUiUpdates(uiState)
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        viewModel.startLoadData()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopLoadData()
    }

    private fun handleUiUpdates(
        uiState: UIRequestState<List<ScheduleEventUiModel>>
    ) {
        when (uiState) {
            is UIRequestState.Error -> {
                showLoading(false)
                showToast(uiState.error)
            }

            UIRequestState.Idle -> showLoading(false)

            UIRequestState.Loading -> showLoading(true)

            is UIRequestState.Success -> {
                showLoading(false)
                val layoutManager = binding.rvEvents.layoutManager
                val firstVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                (binding.rvEvents.adapter as GenericAdapter<ScheduleEventUiModel, ScheduleEventViewHolder>).setData(
                    uiState.data
                )
                layoutManager.scrollToPosition(firstVisibleItemPosition)
            }
        }
    }

    private fun showLoading(showLoading: Boolean) {
        binding.rootSwipeToRefresh.isRefreshing = showLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}