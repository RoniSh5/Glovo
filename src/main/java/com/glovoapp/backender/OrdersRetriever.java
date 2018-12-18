package com.glovoapp.backender;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class OrdersRetriever {

    private final Configuration configuration;
    private final OrderRepository orderRepository;

    OrdersRetriever(Configuration configuration, OrderRepository orderRepository) {
        this.configuration = configuration;
        this.orderRepository = orderRepository;
    }

    List<Order> findsOrdersAvailableForCourier(Courier courier) {
        return orderRepository.findAll()
                .stream()
                .filter(order -> isOrderAvailableAccordingToDescription(courier.getBox(), order.getDescription()))
                .filter(order -> isOrderAvailableAccordingToDistance(courier, order))
                .collect(Collectors.toList());
    }

    private boolean isOrderAvailableAccordingToDescription(Boolean box, String orderDescription) {
        return box || !isOrderSuitableForGlovoBox(orderDescription);
    }

    private boolean isOrderSuitableForGlovoBox(String orderDescription) {
        return configuration.getWordsRequiringBox()
                .stream()
                .anyMatch(word -> StringUtils.containsIgnoreCase(orderDescription, word));
    }

    private boolean isOrderAvailableAccordingToDistance(Courier courier, Order order) {
        return isCourierVehicleSuitableForLongDistance(courier.getVehicle()) ||
                isCourierCloseToOrderPickupLocation(courier.getLocation(), order.getPickup());
    }

    private boolean isCourierVehicleSuitableForLongDistance(Vehicle vehicle) {
        return vehicle == Vehicle.MOTORCYCLE || vehicle == Vehicle.ELECTRIC_SCOOTER;
    }

    private boolean isCourierCloseToOrderPickupLocation(Location courierLocation, Location orderPickup) {
        return DistanceCalculator.calculateDistance(courierLocation, orderPickup) <=
                configuration.getMinimalDistanceForVehicleWithEngine();
    }

    Map<Double, List<OrderWithProximity>> getOrdersWithProximityGroupedAndSortedByProximity(Courier courier, List<Order> orders) {
        return orders.stream()
                .map(order -> buildOrderWithProximity(courier.getLocation(), order))
                .collect(Collectors.groupingBy(OrderWithProximity::getProximityBucket))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private OrderWithProximity buildOrderWithProximity(Location courierLocation, Order order) {
        double proximity = DistanceCalculator.calculateDistance(courierLocation, order.getPickup()) / configuration.getDistanceFactor();
        return new OrderWithProximity()
                .withProximity(proximity)
                .withProximityBucket(Math.ceil(proximity))
                .withOrder(order);
    }

    List<OrderVM> getSortedOrderVMs(Map<Double, List<OrderWithProximity>> ordersWithProximity) {
        return ordersWithProximity.values()
                .stream()
                .flatMap(Collection::stream)
                .map(order -> new OrderVM(order.getId(), order.getDescription()))
                .collect(Collectors.toList());
    }
}
