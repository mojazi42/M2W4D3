package com.example.mvishopingapp.domain.repository

import com.example.mvishopingapp.data.model.CartItem

class FakeCartRepository : CartRepository {

    private val items = mutableListOf<CartItem>()

    override suspend fun getCartItems(): List<CartItem> {
        return items.toList()
    }

    override suspend fun addItemToCart(item: CartItem) {
        items.add(item)
    }

    override suspend fun removeItemFromCart(itemId: Int) {
        items.removeAll { it.id == itemId }
    }

    override suspend fun updateItemQuantity(itemId: Int, newQuantity: Int) {
        items.replaceAll { item ->
            if (item.id == itemId) item.copy(quantity = newQuantity) else item
        }
    }
}
