package com.neha.ShoppingCart.services.Customer.cart;

import com.neha.ShoppingCart.dto.AddProductInCartDto;

import com.neha.ShoppingCart.dto.CartItemsDto;
import com.neha.ShoppingCart.dto.CategoryDto;
import com.neha.ShoppingCart.dto.OrderDto;
import com.neha.ShoppingCart.entity.*;
import com.neha.ShoppingCart.enums.OrderStatus;
import com.neha.ShoppingCart.exceptions.ValidationException;
import com.neha.ShoppingCart.repository.*;
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

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        try {
            Order activeOrder = orderRepository.findByUserIdAndOrderStatus(
                    addProductInCartDto.getUserId(), OrderStatus.Pending);

            if (activeOrder == null) {
                activeOrder = new Order();
                User user = userRepository.findById(addProductInCartDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                activeOrder.setUser(user);
                activeOrder.setOrderStatus(OrderStatus.Pending);
                activeOrder.setDate(new Date());
                activeOrder.setAmount(0L);
                activeOrder.setTotalAmount(0L);
                activeOrder = orderRepository.save(activeOrder);
            }

            Product product = productRepository.findById(addProductInCartDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItems cartItem = new CartItems();
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(1L);
            cartItem.setUser(activeOrder.getUser());
            cartItem.setOrder(activeOrder);
            cartItemsRepository.save(cartItem);

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

            if(activeOrder.getCoupon() != null){
                orderDto.setCouponName(activeOrder.getCoupon().getName());
            }

            return orderDto;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching cart: " + e.getMessage());
        }
    }

    public OrderDto applyCoupon(Long userId, String code){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found"));

        if(couponIsExpired(coupon)){
            throw new ValidationException("Coupon has expired");
        }

        double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount((long)netAmount);
        activeOrder.setDiscount((long)discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon){
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return expirationDate != null && currentDate.after(expirationDate);
    }




}
