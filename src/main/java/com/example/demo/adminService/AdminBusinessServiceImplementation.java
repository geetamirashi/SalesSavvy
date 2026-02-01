package com.example.demo.adminService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Order_items;
import com.example.demo.entity.Orders;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.Order_itemRepository;
import com.example.demo.repository.ProductRepository;



@Service
public class AdminBusinessServiceImplementation implements AdminBusinessService {
	
	
	private final  OrderRepository orderRepository;
	 private final  ProductRepository productRepository;
	  private final  Order_itemRepository orderItemRepository;

	  
	  public AdminBusinessServiceImplementation(OrderRepository orderRepository, ProductRepository productRepository,Order_itemRepository orderItemRepository) {
		    this.orderRepository=orderRepository;
		    this.productRepository=productRepository;
		    this.orderItemRepository=orderItemRepository;
	  }
	  
	  
	  
	@Override
	public Map<String, Object> calculateMonthlyBusiness(int month, int year) {
		List<Orders>successfulOrders = orderRepository.findSuccessfulOrdersByMonthAndYear(month,year);
		return calculateBusinessMetrics(successfulOrders);
		
	}

	@Override
	public Map<String, Object> calculateDailyBusiness(LocalDate date) {
		List<Orders>successfulOrders = orderRepository.findSuccessfulOrdersByDate(date);
		return calculateBusinessMetrics(successfulOrders);
		
	}

	@Override
	public Map<String, Object> calculateYearlyBusiness(int year) {
		List<Orders>successfulOrders = orderRepository.findSuccessfulOrdersByYear(year);
		return calculateBusinessMetrics(successfulOrders);
		
	}

	@Override
	public Map<String, Object> calculateOverallBusiness() {
		List<Orders>successfulOrders = orderRepository.findAllByStatusForOverallBusiness();
		return calculateBusinessMetrics(successfulOrders);
		
	}
	
	private Map<String, Object> calculateBusinessMetrics(List<Orders> orders) {
        double totalRevenue = 0.0;
        Map<String, Integer> categorySales = new HashMap<>();

        for (Orders order : orders) {
            totalRevenue += order.getTotalamount().doubleValue();

            List<Order_items> items = orderItemRepository.findByOrderId(order.getOrderid());
            for (Order_items item : items) {
                String categoryName = productRepository.findCategoryNameByProductId(item.getProductid());
                categorySales.put(categoryName, categorySales.getOrDefault(categoryName, 0) + item.getQuantity());
            }
        }

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalRevenue", totalRevenue);
        metrics.put("categorySales", categorySales);
        return metrics;
    }

	
	

}
