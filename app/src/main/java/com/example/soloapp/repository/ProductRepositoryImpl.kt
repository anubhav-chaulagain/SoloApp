package com.example.soloapp.repository

import com.example.soloapp.model.ProductModel
import com.example.soloapp.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepositoryImpl: ProductRepository {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val reference : DatabaseReference = database.reference.child("products")

    override fun addProduct(
        productModel: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        var id = reference.push().key.toString()
        productModel.productId = id
        reference.child(id).setValue(productModel).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "product added successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(productId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product Updated Successfully!")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(productId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product deleted Successfully!")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        reference.child(productId).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    var model = snapshot.getValue(ProductModel::class.java)

                    callback(model, true, "Details fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        reference.addValueEventListener((object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var products = mutableListOf<ProductModel>()
                if(snapshot.exists()) {
                    for(eachProduct in snapshot.children) {
                        var model = eachProduct.getValue(ProductModel::class.java)
                        if(model != null) {
                            products.add(model)
                        }
                    }
                    callback(products, true, "fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message.toString())
            }

        }))
    }}
