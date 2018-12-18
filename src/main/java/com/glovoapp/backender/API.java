package com.glovoapp.backender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@ComponentScan("com.glovoapp.backender")
@EnableAutoConfiguration
class API {

    private final Configuration configuration;
    private final OrdersRetriever ordersRetriever;
    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;

    @Autowired
    API(Configuration configuration, OrdersRetriever ordersRetriever,
        OrderRepository orderRepository, CourierRepository courierRepository) {
        this.configuration = configuration;
        this.ordersRetriever = ordersRetriever;
        this.orderRepository = orderRepository;
        this.courierRepository = courierRepository;
    }

    @RequestMapping("/")
    @ResponseBody
    String root() {
        return configuration.getWelcomeMessage();
    }

    @RequestMapping("/orders")
    @ResponseBody
    List<OrderVM> orders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> new OrderVM(order.getId(), order.getDescription()))
                .collect(Collectors.toList());
    }

    @RequestMapping("/orders/:{courierId}")
    @ResponseBody
    List<OrderVM> ordersOfGivenCourierId(@PathVariable("courierId") String courierId) {
        Courier courier = courierRepository.findById(courierId);
        if (courier == null) {
            return new ArrayList<>();
        }
        List<Order> orders = ordersRetriever.findsOrdersAvailableForCourier(courier);
        Map<Double, List<OrderWithProximity>> ordersWithProximity = ordersRetriever.getOrdersWithProximityGroupedAndSortedByProximity(courier, orders);
        Comparator<OrderWithProximity> givenSortOrderComparator = configuration.getComparatorMap().get(configuration.getSortOrder());
        ordersWithProximity.values().forEach(listOfOrders -> listOfOrders.sort(givenSortOrderComparator));
        return ordersRetriever.getSortedOrderVMs(ordersWithProximity);
    }

    public static void main(String[] args) {
        SpringApplication.run(API.class);
    }
}
