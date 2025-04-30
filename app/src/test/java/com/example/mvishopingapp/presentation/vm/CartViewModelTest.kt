package com.example.mvishopingapp.presentation.vm

import com.example.mvishopingapp.data.model.CartItem
import com.example.mvishopingapp.domain.repository.FakeCartRepository
import com.example.mvishopingapp.domain.usecase.AddItemToCartUseCase
import com.example.mvishopingapp.domain.usecase.GetCartItemsUseCase
import com.example.mvishopingapp.domain.usecase.RemoveItemFromCartUseCase
import com.example.mvishopingapp.domain.usecase.UpdateItemQuantityUseCase
import com.example.mvishopingapp.presentation.intent.CartIntent
import com.example.mvishopingapp.presentation.state.CartState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        val repository = FakeCartRepository()
        val getCartItemsUseCase = GetCartItemsUseCase(repository)
        val addItemToCartUseCase = AddItemToCartUseCase(repository)
        val removeItemFromCartUseCase = RemoveItemFromCartUseCase(repository)
        val updateItemQuantityUseCase = UpdateItemQuantityUseCase(repository)

        viewModel = CartViewModel(
            getCartItemsUseCase,
            addItemToCartUseCase,
            removeItemFromCartUseCase,
            updateItemQuantityUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when LoadCart is triggered, then state is Success`() = runTest(testDispatcher) {
        viewModel.handleIntent(CartIntent.LoadCart)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is CartState.Success)
    }

    @Test
    fun `when AddItem is triggered, cart contains the item`() = runTest(testDispatcher) {
        val item = CartItem(1, "Test Item", 10.0, 1)
        viewModel.handleIntent(CartIntent.AddItem(item))
        viewModel.handleIntent(CartIntent.LoadCart)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value as CartState.Success
        assertEquals(1, state.items.size)
        assertEquals("Test Item", state.items.first().name)
    }

    @Test
    fun `when RemoveItem is triggered, cart item is removed`() = runTest(testDispatcher) {
        val item = CartItem(1, "Test Item", 10.0, 1)
        viewModel.handleIntent(CartIntent.AddItem(item))
        viewModel.handleIntent(CartIntent.RemoveItem(1))
        viewModel.handleIntent(CartIntent.LoadCart)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value as CartState.Success
        assertEquals(0, state.items.size)
    }

    @Test
    fun `when UpdateQuantity is triggered, item quantity is updated`() = runTest(testDispatcher) {
        val item = CartItem(1, "Test Item", 10.0, 1)
        viewModel.handleIntent(CartIntent.AddItem(item))
        viewModel.handleIntent(CartIntent.UpdateQuantity(1, 5))
        viewModel.handleIntent(CartIntent.LoadCart)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value as CartState.Success
        assertEquals(5, state.items.first().quantity)
    }
}
