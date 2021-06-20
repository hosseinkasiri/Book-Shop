package com.example.noavar.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noavar.customViews.ListProductAdapter
import com.example.noavar.databinding.FragmentHomeBinding
import com.example.noavar.utils.ItemClickListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var productAdapter: ListProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        productAdapter = ListProductAdapter(ItemClickListener {

        })
        viewModel.products.observe(viewLifecycleOwner, Observer {
            productAdapter.submitList(it)
        })
        binding.productsRecycler.adapter = productAdapter
        return binding.root
    }
}