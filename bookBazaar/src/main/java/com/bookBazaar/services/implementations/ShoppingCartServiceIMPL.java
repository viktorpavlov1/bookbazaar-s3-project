package com.bookBazaar.services.implementations;

import com.bookBazaar.models.dto.CartItemDTO;
import com.bookBazaar.models.dto.PurchaseDTO;
import com.bookBazaar.models.dto.PurchasedItemDTO;
import com.bookBazaar.models.entities.BookEntity;
import com.bookBazaar.models.entities.CartItemEntity;
import com.bookBazaar.models.entities.PurchaseEntity;
import com.bookBazaar.models.entities.PurchasedItemEntity;
import com.bookBazaar.models.other.ModelConverter;
import com.bookBazaar.models.repositories.BookRepository;
import com.bookBazaar.models.repositories.PurchaseRepository;
import com.bookBazaar.models.repositories.PurchasedItemRepository;
import com.bookBazaar.models.repositories.ShoppingCartRepository;
import com.bookBazaar.services.interfaces.ShoppingCartServiceINT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceIMPL implements ShoppingCartServiceINT {

    private final ShoppingCartRepository cartRepository;
    private final BookRepository bookRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchasedItemRepository purchasedItemRepository;
    private final ModelConverter modelConverter = new ModelConverter();

    @Override
    public CartItemDTO addBookToShoppingCart(CartItemDTO cartItem) {
        CartItemEntity preparedCartEntity = modelConverter.convertCartItemDTOToCartItemEntity(cartItem);
        CartItemEntity savedItem = cartRepository.save(preparedCartEntity);
        CartItemDTO savedItemDTO = modelConverter.convertCartItemEntityToCartItemDTO(savedItem);
        return savedItemDTO;
    }

    @Override
    public CartItemDTO changeQuantityOfCartItem(CartItemDTO cartItemDTO) {
        if(cartItemDTO.getId().isPresent())
        {
            Long cartItemID = cartItemDTO.getId().get();
            CartItemEntity cartItemEntity = cartRepository.findById(cartItemID).orElseThrow(() -> new RuntimeException("Cart item not found"));
            int currentQty = cartItemEntity.getQty();
            cartItemEntity.setQty(currentQty + cartItemDTO.getQty());
            CartItemEntity savedCartItem = cartRepository.save(cartItemEntity);
            return modelConverter.convertCartItemEntityToCartItemDTO(savedCartItem);
        }
        else
        {
            throw new RuntimeException("Unable to process the quantity change of cart item");
        }
    }

    @Override
    public void deleteSpecificCartItem(CartItemDTO cartItemDTO) {
        Long cartItemID = cartItemDTO.getId().get();
        CartItemEntity cartItemEntity = cartRepository.findById(cartItemID).orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartRepository.deleteById(cartItemEntity.getId());
        Optional<CartItemEntity> optionalItem = cartRepository.findById(cartItemID);
        if(optionalItem.isPresent())
        {
            throw new RuntimeException("The item was not removed from the shopping cart.");
        }
    }

    @Override
    public void deleteAllCartItemsForSpecificUser(String username) {
        try
        {
            cartRepository.deleteAllByUsernameIgnoreCase(username);
        }
        catch(Exception ex)
        {
            throw new RuntimeException("This user does not have any shopping cart items.");
        }
    }

    @Override
    public PurchaseDTO makePurchase(String username) {
        Date today = new Date();
        double totalPrice = 0;
        List<CartItemEntity> cartItemEntities = cartRepository.getAllByUsernameIgnoreCase(username);
        List<PurchasedItemEntity> purchasedItemsEntities = new ArrayList<>();

        // Get the total price of the purchased books + start preparing the purchased item table + update book collection
        for (CartItemEntity cartItem : cartItemEntities)
        {
            BookEntity bookEntity = bookRepository.findByTitleIgnoreCase(cartItem.getProduct_name());
            if(bookEntity.getQuantity() < cartItem.getQty())
            {
                String message = String.format("Too many items are requested to be purchased. Requested: %s ; In stock: %d", cartItem.getQty(), bookEntity.getQuantity());
                throw new RuntimeException(message);
            }

            totalPrice = totalPrice + (cartItem.getQty() * bookEntity.getPrice());

            PurchasedItemEntity itemEntity = PurchasedItemEntity.builder()
                    .title(cartItem.getProduct_name())
                    .purchased_qty(cartItem.getQty())
                    .single_price(bookEntity.getPrice())
                    .build();
            purchasedItemsEntities.add(itemEntity);

            bookEntity.setQuantity(bookEntity.getQuantity() - cartItem.getQty());
            BookEntity savedUpdatedBook = bookRepository.save(bookEntity);
            if(savedUpdatedBook.getQuantity() <= 0)
            {
                bookRepository.deleteById(savedUpdatedBook.getId());
            }
        }

        //Create the purchase
        PurchaseEntity purchaseEntity = PurchaseEntity.builder()
                .username(username)
                .purchased_date(today)
                .total_price(totalPrice)
                .build();
        PurchaseEntity savedPurchase = purchaseRepository.save(purchaseEntity);


        //Record the purchased items + Prepare the list with purchased items
        List<PurchasedItemDTO> purchasedItemsDTO = new ArrayList<>();
        for(PurchasedItemEntity purchasedItem : purchasedItemsEntities)
        {
            purchasedItem.setPurchase_id(savedPurchase.getId());
            PurchasedItemEntity savedPurchasedItem = purchasedItemRepository.save(purchasedItem);
            purchasedItemsDTO.add(modelConverter.convertPurchasedItemEntityToPurchasedItemDTO(savedPurchasedItem));
        }

        //Update the shopping cart
        this.deleteAllCartItemsForSpecificUser(username);

        //Construct the response
        return PurchaseDTO.builder()
                .id(savedPurchase.getId())
                .username(savedPurchase.getUsername())
                .purchased_date(savedPurchase.getPurchased_date())
                .total_price(savedPurchase.getTotal_price())
                .items(purchasedItemsDTO)
                .build();
    }


}