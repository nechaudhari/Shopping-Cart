package com.neha.ShoppingCart.services.Customer.cart;

import com.neha.ShoppingCart.dto.AddProductInCartDto;

import com.neha.ShoppingCart.dto.CartItemsDto;
import com.neha.ShoppingCart.dto.CategoryDto;
import com.neha.ShoppingCart.dto.OrderDto;
import com.neha.ShoppingCart.entity.CartItems;
import com.neha.ShoppingCart.entity.Order;
import com.neha.ShoppingCart.entity.Product;
import com.neha.ShoppingCart.entity.User;
import com.neha.ShoppingCart.enums.OrderStatus;
import com.neha.ShoppingCart.repository.CartItemsRepository;
import com.neha.ShoppingCart.repository.OrderRepository;
import com.neha.ShoppingCart.repository.ProductRepository;
import com.neha.ShoppingCart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {


//        if(optionalCartItems.isPresent()){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//        }else{
//            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
//            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());
//
//            if(optionalProduct.isPresent() && optionalUser.isPresent()){
//                CartItems cart = new CartItems();
//                cart.setProduct(optionalProduct.get());
//                cart.setPrice(optionalProduct.get().getPrice());
//                cart.setQuantity(1L);
//                cart.setUser(optionalUser.get());
//                cart.setOrder(activeOrder);
//
//                CartItems updatedCart = cartItemsRepository.save(cart);
//
//                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice() );
//                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice() );
//                activeOrder.getCartItems().add(cart);
//
//                orderRepository.save(activeOrder);
//
//                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
//
//            }else{
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found");
//            }
//       }
        try {
            // Find active order or create a new one
            Order activeOrder = orderRepository.findByUserIdAndOrderStatus(
                    addProductInCartDto.getUserId(), OrderStatus.Pending);

            if (activeOrder == null) {
                // Create a new order if no active order found
                activeOrder = new Order();
                User user = userRepository.findById(addProductInCartDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                activeOrder.setUser(user);
                activeOrder.setOrderStatus(OrderStatus.Pending);
                activeOrder.setDate(new Date());
                activeOrder.setAmount(0L); // Initial amount
                activeOrder.setTotalAmount(0L); // Initial total amount
                activeOrder = orderRepository.save(activeOrder);
            }

            // Find the product to add to cart
            Product product = productRepository.findById(addProductInCartDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Create cart item and save to database
            CartItems cartItem = new CartItems();
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(1L); // Assuming adding one quantity for now
            cartItem.setUser(activeOrder.getUser());
            cartItem.setOrder(activeOrder);
            cartItemsRepository.save(cartItem);

            // Update order total amount
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());
            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            orderRepository.save(activeOrder);

            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product to cart: " + e.getMessage());
        }
    }

    @Override
    public OrderDto getCartByUserId(Long userId){
        try {
            Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
            List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream()
                    .map(CartItems::getCartDto)
                    .collect(Collectors.toList());

            OrderDto orderDto = new OrderDto();
            orderDto.setAmount(activeOrder.getAmount());
            orderDto.setId(activeOrder.getId());
            orderDto.setOrderStatus(activeOrder.getOrderStatus());
            orderDto.setDiscount(activeOrder.getDiscount());
            orderDto.setTotalAmount(activeOrder.getTotalAmount());
            orderDto.setCartItems(cartItemsDtoList);

            return orderDto;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching cart: " + e.getMessage());
        }
    }


}
