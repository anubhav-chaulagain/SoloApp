package com.example.soloapp.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.soloapp.model.ProductModel
import com.example.soloapp.repository.ProductRepository

class ProductViewModel (val repository: ProductRepository) {
    fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {
        repository.addProduct(productModel, callback)
    }

    fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        repository.updateProduct(productId, data, callback)
    }

    fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        repository.deleteProduct(productId, callback)
    }

    var _products = MutableLiveData<ProductModel?>()
    var products = MutableLiveData<ProductModel?>()
        get() = _products // getter ho hai like java

    var _allProducts = MutableLiveData<List<ProductModel>?>()
    var allProducts = MutableLiveData<List<ProductModel>?>()
        get() = _allProducts


    var _loadingStateSingleProduct = MutableLiveData<Boolean>()
    var loadingStateSingleSingleProduct = MutableLiveData<Boolean>()
        get() = _loadingStateSingleProduct

    // call back parameter hatauni
    fun getProductById(productId: String) {
        _loadingStateSingleProduct.value = true
        repository.getProductById(productId) { product, success, message ->
            if (success) {
                _products.value = product
                _loadingStateSingleProduct.value = false
            }
        }
    }

    var _loadingState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState

    fun getAllProduct() {
        _loadingState.value = true
        repository.getAllProduct() { products, success, message ->
            if (success) {
                _allProducts.value = products
                _loadingState.value = false
            }
        }
    }

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit){
        repository.uploadImg(context, imageUri, callback)
    }
}
