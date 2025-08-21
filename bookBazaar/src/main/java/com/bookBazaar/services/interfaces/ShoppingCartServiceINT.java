package com.bookBazaar.services.interfaces;

import com.bookBazaar.models.dto.CartItemDTO;
import com.bookBazaar.models.dto.PurchaseDTO;

public interface ShoppingCartServiceINT {
    public CartItemDTO addBookToShoppingCart(CartItemDTO cartItem);
    public CartItemDTO changeQuantityOfCartItem(CartItemDTO cartItemDTO);
    public void deleteSpecificCartItem(CartItemDTO cartItemDTO);
    public void deleteAllCartItemsForSpecificUser(String username);
    public PurchaseDTO makePurchase(String username);
}