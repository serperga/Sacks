package com.sacks.codeexercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.error.ProductNotFoundException;
import com.sacks.codeexercise.model.OrderRefundInformation;
import com.sacks.codeexercise.model.ProductInformation;
import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.OrderStatus;
import com.sacks.codeexercise.model.entities.OrderStatusHistory;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.CustomerRepository;
import com.sacks.codeexercise.repository.OrderStatusHistoryRepository;
import com.sacks.codeexercise.repository.OrderStatusRepository;
import com.sacks.codeexercise.repository.OrdersRepository;
import com.sacks.codeexercise.repository.ProductRepository;

@Service
public class ProductRefundServiceImpl implements ProductRefundService {

    private final OrdersRepository ordersRepository;
    private final CustomerRepository customerRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final ProductRepository productRepository;
    private final OrderStatusRepository orderStatusRepository;

    private static final int ORDER_CANCELLED_NO_PRODUCT_IN_ORDER_STATUS = 8;

    @Autowired
    public ProductRefundServiceImpl(OrdersRepository ordersRepository,
        CustomerRepository customerRepository,
        OrderStatusHistoryRepository orderStatusHistoryRepository,
        ProductRepository productRepository,
        OrderStatusRepository orderStatusRepository) {
        this.ordersRepository = ordersRepository;
        this.customerRepository = customerRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.productRepository = productRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public OrderRefundInformation returnProductAndGetRefund(Long orderId, Long productId) {
        final Optional<Order> order = ordersRepository.findOrderByOrderId(orderId);
        OrderRefundInformation orderRefundInformation = new OrderRefundInformation();

        if(order.isPresent()){
            Order orderToRefund = order.get();
            List<Product> productListInOrder = orderToRefund.getProducts();
            Optional<Product> matchingProduct = productListInOrder.stream().
                filter(product -> (product.getProductId()) == productId).
                findFirst();
            if (matchingProduct.isPresent()){
                Product productToRefund = matchingProduct.get();
                int quantityAfterRefund = productToRefund.getQuantity() + 1;
                productToRefund.setQuantity(quantityAfterRefund);
                productRepository.save(productToRefund);
                Customer customerToBeRefunded = customerRepository.findByUsername(order.get().getBuyer().getUsername());
                Double amountToRefund = productToRefund.getPrice();
                Double orderAmountAfterRefund = orderToRefund.getAmount() - amountToRefund;
                Double customerWalletAfterRefund = customerToBeRefunded.getCurrentAmountInWallet() + amountToRefund;
                customerToBeRefunded.setCurrentAmountInWallet(customerWalletAfterRefund);
                customerToBeRefunded = customerRepository.save(customerToBeRefunded);
                orderToRefund.setBuyer(customerToBeRefunded);
                orderToRefund.setAmount(orderAmountAfterRefund);
                productListInOrder.removeIf(product -> product.getProductId() == productId);
                if(!productListInOrder.isEmpty()) {
                    orderToRefund.setProducts(productListInOrder);
                    orderToRefund = ordersRepository.save(orderToRefund);
                } else{
                    OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
                    orderStatusHistory.setOrderId(orderToRefund.getOrderId());
                    orderStatusHistory.setCompletedStatusInDays(0);
                    orderStatusHistory.setUsername(orderToRefund.getBuyer().getUsername());
                    orderStatusHistory.setOrderAmount(0);
                    orderStatusHistory.setStatusId(ORDER_CANCELLED_NO_PRODUCT_IN_ORDER_STATUS);

                    final Optional<OrderStatus> orderStatus = orderStatusRepository
                        .findOrderStatusByStatusId(ORDER_CANCELLED_NO_PRODUCT_IN_ORDER_STATUS);
                    if (orderStatus.isPresent()){
                        orderToRefund.setOrderStatus(orderStatus.get());
                    }

                    orderStatusHistoryRepository.save(orderStatusHistory);
                    ordersRepository.delete(orderToRefund);

                    orderRefundInformation = createOrderRefundedInformation(orderRefundInformation,orderToRefund,new ArrayList<Product>());
                }
                orderRefundInformation = createOrderRefundedInformation(orderRefundInformation,orderToRefund,productListInOrder);

            }else{
                throw new ProductNotFoundException("Product not found in order. Product can not be refunded");
            }
        }else{
            throw new NotFoundOrderErrorException("Order not found");
        }
        return orderRefundInformation;
    }

    private OrderRefundInformation createOrderRefundedInformation(OrderRefundInformation orderRefundInformation,Order orderToRefund, List<Product> productListInOrder){
        orderRefundInformation.setOrderId(orderToRefund.getOrderId());
        orderRefundInformation.setAmount(orderToRefund.getAmount());
        orderRefundInformation.setBuyer(orderToRefund.getBuyer().getUsername());
        orderRefundInformation.setOrderStatus(orderToRefund.getOrderStatus().getStatus());
        orderRefundInformation.setEstimatedDays(orderToRefund.getEstimatedDays());
        orderRefundInformation.setAmountInWalletAfterRefund(orderToRefund.getBuyer().getCurrentAmountInWallet());

        List<ProductInformation> productInformationList = new ArrayList<>();
        productListInOrder.forEach(product -> {
            ProductInformation productInformation = new ProductInformation(product.getName(),product.getPrice());
            productInformationList.add(productInformation);
        });
        orderRefundInformation.setProducts(productInformationList);

        return orderRefundInformation;
    }
}
