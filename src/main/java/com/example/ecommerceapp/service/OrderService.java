package com.example.ecommerceapp.service;

import com.example.ecommerceapp.dto.CreateOrderRequest;
import com.example.ecommerceapp.dto.OrderDto;
import com.example.ecommerceapp.entity.Cart;
import com.example.ecommerceapp.entity.CartItem;
import com.example.ecommerceapp.entity.Customer;
import com.example.ecommerceapp.entity.Order;
import com.example.ecommerceapp.entity.OrderItem;
import com.example.ecommerceapp.entity.OrderStatus;
import com.example.ecommerceapp.entity.Product;
import com.example.ecommerceapp.exception.CartNotFoundException;
import com.example.ecommerceapp.exception.CustomerNotFoundException;
import com.example.ecommerceapp.exception.InsufficientStockException;
import com.example.ecommerceapp.exception.OrderNotFoundException;
import com.example.ecommerceapp.repository.CartRepository;
import com.example.ecommerceapp.repository.CustomerRepository;
import com.example.ecommerceapp.repository.OrderItemRepository;
import com.example.ecommerceapp.repository.OrderRepository;
import com.example.ecommerceapp.repository.ProductRepository;
import com.example.ecommerceapp.repository.CartItemRepository; // Make sure this import is present
import com.example.ecommerceapp.util.DtoConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final DtoConverter dtoConverter;
    private final CartItemRepository cartItemRepository; // <--- Add this line

    // Tüm siparişleri getirir
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(dtoConverter::convertToOrderDto)
                .collect(Collectors.toList());
    }

    // Belirli bir siparişi ID'ye göre getirir
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return dtoConverter.convertToOrderDto(order);
    }

    // Yeni sipariş oluşturur (sepetteki ürünlerden)
    @Transactional
    public OrderDto createOrderFromCart(CreateOrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + request.getCustomerId()));

        Cart cart = cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for customer id: " + request.getCustomerId()));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cannot create an order from an empty cart.");
        }

        // Stok kontrolü ve OrderItem oluşturma
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING); // Başlangıçta sipariş durumu beklemede
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(request.getShippingAddress());
        order.setBillingAddress(request.getBillingAddress());

        double totalAmount = 0.0;
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            Integer requestedQuantity = cartItem.getQuantity();

            if (product.getStockQuantity() < requestedQuantity) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName() + ". Available: " + product.getStockQuantity());
            }

            // Stoktan düşme
            product.setStockQuantity(product.getStockQuantity() - requestedQuantity);
            productRepository.save(product); // Güncellenmiş ürünü kaydet

            // OrderItem oluştur
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(requestedQuantity);
            orderItem.setPurchasedPrice(cartItem.getUnitPrice()); // Sepetteki fiyatı al
            order.getOrderItems().add(orderItem);

            totalAmount += orderItem.getPurchasedPrice() * orderItem.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order); // Siparişi kaydet

        // Sepeti temizle
        cart.getCartItems().forEach(cartItemRepository::delete); // <--- This line uses cartItemRepository
        cart.getCartItems().clear(); // Cart objesindeki listeyi de temizle
        cartRepository.save(cart); // Değişiklikleri kaydet

        return dtoConverter.convertToOrderDto(order);
    }

    // Sipariş durumunu günceller
    @Transactional
    public OrderDto updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        order.setStatus(newStatus);
        order = orderRepository.save(order);
        return dtoConverter.convertToOrderDto(order);
    }

    // Siparişi siler
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}