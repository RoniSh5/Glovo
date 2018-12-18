package com.glovoapp.backender;

import java.util.Objects;

public class OrderWithProximity extends Order {

    private Double proximity;
    private Double proximityBucket;

    Double getProximity() {
        return proximity;
    }

    OrderWithProximity withProximity(Double proximity) {
        this.proximity = proximity;
        return this;
    }

    Double getProximityBucket() {
        return proximityBucket;
    }

    OrderWithProximity withProximityBucket(Double proximityBucket) {
        this.proximityBucket = proximityBucket;
        return this;
    }

    OrderWithProximity withOrder(Order order) {
        this.withId(order.getId())
                .withDescription(order.getDescription())
                .withFood(order.getFood())
                .withVip(order.getVip())
                .withPickup(order.getPickup())
                .withDelivery(order.getDelivery());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderWithProximity that = (OrderWithProximity) o;
        return Objects.equals(proximity, that.proximity) &&
                Objects.equals(proximityBucket, that.proximityBucket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), proximity, proximityBucket);
    }
}
