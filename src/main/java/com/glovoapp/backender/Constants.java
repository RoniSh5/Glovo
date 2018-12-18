package com.glovoapp.backender;

import java.util.Comparator;

public interface Constants {

    String PROPERTIES_FILE = "/application.properties";
    String WORDS_REQUIRING_BOX_DELIMITER = ",";
    String VIP_THAN_FOOD_THAN_DISTANCE = "vipThanFoodThanDistance";
    String VIP_THAN_DISTANCE_THAN_FOOD = "vipThanDistanceThanFood";

    Comparator<OrderWithProximity> COMPARATOR_BY_VIP_THAN_FOOD_THAN_DISTANCE =
            Comparator.comparing(OrderWithProximity::getVip)
                    .thenComparing(OrderWithProximity::getFood).reversed()
                    .thenComparing(OrderWithProximity::getProximity);

    Comparator<OrderWithProximity> COMPARATOR_BY_VIP_THAN_DISTANCE_THAN_FOOD =
            Comparator.comparing(OrderWithProximity::getVip).reversed()
                    .thenComparing(OrderWithProximity::getProximity)
                    .thenComparing(OrderWithProximity::getFood);
}
