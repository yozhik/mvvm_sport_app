package com.example.myapplication.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentEventsBinding
import com.example.myapplication.ui.adapter.GenericAdapter
import com.example.myapplication.ui.adapter.viewholder.SportEventViewHolder
import com.example.myapplication.ui.common.UIRequestState
import com.example.myapplication.ui.model.SportEventUiModel

class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val viewModel: EventsViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = GenericAdapter<SportEventUiModel, SportEventViewHolder>(
            onClick = { sportEvent ->
                onEventClickHandler(sportEvent)
            },
            viewHolderCreator = { view, onClick ->
                SportEventViewHolder(view, onClick)
            }
        )
        binding.rvEvents.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvEvents.adapter = adapter

        binding.rootSwipeToRefresh.setOnRefreshListener {
            viewModel.loadSportEvents()
        }

        viewModel.sportEvents.observe(viewLifecycleOwner) { uiState ->
            handleUiUpdates(uiState)
        }

        viewModel.loadSportEvents()

        return root
    }

    private fun handleUiUpdates(
        uiState: UIRequestState<List<SportEventUiModel>>
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

                (binding.rvEvents.adapter as GenericAdapter<SportEventUiModel, SportEventViewHolder>).setData(
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
        Toast.makeText(requireActivity(), message, LENGTH_SHORT).show()
    }

    private fun onEventClickHandler(sportEvent: SportEventUiModel) {
        sportEvent.videoUrl?.let {
            findNavController().navigate(
                directions = EventsFragmentDirections.actionNavigationEventsToNavigationPlayback(
                    videoUrl = sportEvent.videoUrl
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}