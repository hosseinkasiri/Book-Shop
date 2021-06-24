package com.example.noavar.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noavar.R
import com.example.noavar.adapters.ListProductAdapter
import com.example.noavar.databinding.FragmentHomeBinding
import com.example.noavar.utils.ApiStatus
import com.example.noavar.utils.ItemClickListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var factory: HomeFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var productAdapter: ListProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        factory = HomeFactory((activity)!!.application)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        productAdapter = ListProductAdapter(ItemClickListener { product ->
            binding.webView.loadUrl(product.url.toString())
        })
        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            productAdapter.submitList(products)
        })
        binding.productsRecycler.adapter = productAdapter
        handleExceptions()
        return binding.root
    }

    private fun handleExceptions() {
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                ApiStatus.LOADING -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.loading_animation)
                }
                ApiStatus.DONE -> {
                    binding.statusImage.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    val dialog = AlertDialog.Builder(activity)
                    dialog.apply {
                        setTitle("مشکل در اتصال به سرور")
                        setPositiveButton("تلاش مجدد") { _, _ ->
                            viewModel.reloading()
                        }
                        if (!viewModel.isEmptyDatabase.value!!){
                            setNegativeButton("استفاده آفلاین") { _, _ ->
                                viewModel.offlineMode()
                                binding.statusImage.visibility = View.GONE
                            }
                        }
                    }.create().show()
                }
            }
        })
    }
}