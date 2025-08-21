package com.bookBazaar.controllers;

import com.bookBazaar.models.dto.CartItemDTO;
import com.bookBazaar.models.dto.PurchaseDTO;
import com.bookBazaar.services.interfaces.ShoppingCartServiceINT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shoppingCart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {})
public class ShoppingCartController {

    //add to shopping cart X
    //add qty X
    //delete qty X
    //delete from shopping cart X
    //clear shopping cart X
    //buy

    private final ShoppingCartServiceINT cartService;
    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addItemToShoppingCart(@RequestBody CartItemDTO cartItem) {
        CartItemDTO response = cartService.addBookToShoppingCart(cartItem);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change")
    public ResponseEntity<CartItemDTO> changeQtyOfBook(@RequestBody CartItemDTO cartItem) {
        try
        {
            CartItemDTO response = cartService.changeQuantityOfCartItem(cartItem);
            return ResponseEntity.ok().body(response);
        }
        catch (Exception ex)
        {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody CartItemDTO cartItem) {
        cartService.deleteSpecificCartItem(cartItem);
        return ResponseEntity.ok().body("The item was removed from the cart!");
    }

    @DeleteMapping("/clear/{id}")
    public ResponseEntity<String> clearShoppingCart(@PathVariable(value="id") final String username) {
        try
        {
            cartService.deleteAllCartItemsForSpecificUser(username);
            return ResponseEntity.ok().body("Shopping cart emptied successfully for the user!");
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/purchase/{id}")
    public ResponseEntity<PurchaseDTO> makePurchase (@PathVariable(value="id") final String username) {
        PurchaseDTO response = cartService.makePurchase(username);
        return ResponseEntity.ok().body(response);
    }
}