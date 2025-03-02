package com.example.soloapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soloapp.databinding.ActivityProductDashboardBinding
import com.example.soloapp.viewmodel.ProductViewModel
import com.example.soloapp.Adapter.ProductAdapter
import com.example.soloapp.repository.ProductRepositoryImpl

class ProductDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDashboardBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        // Initialize adapter
        adapter = ProductAdapter(this, ArrayList())

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observe product list
        productViewModel.getAllProduct()
        productViewModel.allProducts.observe(this) { productList ->
            productList?.let {
                adapter.updateData(it)
            }
        }

        // Observe loading state
        productViewModel.loadingState.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Floating Button Actions
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
        binding.floatingProfileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Window Insets Handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
