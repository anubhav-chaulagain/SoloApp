package com.example.soloapp.repository

import com.example.soloapp.model.ProductModel

interface ProductRepository {

    fun addProduct(productModel: ProductModel, callback:(Boolean, String) -> Unit)

    // update garda model lidaina. So mutable map for key value pair
    fun updateProduct(productId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit)

    fun deleteProduct(productId:String, callback: (Boolean, String) -> Unit)

    fun getProductById(productId: String, callback: (ProductModel?, Boolean, String) -> Unit)

    fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit)
}