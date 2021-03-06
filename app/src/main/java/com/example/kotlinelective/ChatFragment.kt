package com.example.kotlinelective

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinelective.databinding.ChatFragmentBinding

class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private var adapter: ChatRecyclerAdapter = ChatRecyclerAdapter(listOf())

    private lateinit var viewModel: ChatViewModel

    private lateinit var binding: ChatFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChatFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, {
            adapter = ChatRecyclerAdapter(it)
            binding.recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.adapter = adapter
        })
        binding.sendButton.setOnClickListener {
            if (binding.chatInputEditText.text.toString().isEmpty()) {
                return@setOnClickListener
            }
            viewModel.send(binding.chatInputEditText.text.toString())
            binding.chatInputEditText.text?.clear()
        }
    }

}