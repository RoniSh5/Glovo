package com.glovoapp.backender;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
class Configuration {

    private final String welcomeMessage;
    private final Double distanceFactor;
    private final Double minimalDistanceForVehicleWithEngine;
    private final Set<String> wordsRequiringBox;
    private final String sortOrder;
    private final Map<String, Comparator<OrderWithProximity>> comparatorMap;

    Configuration() {
        Properties properties = getProperties();
        this.welcomeMessage = properties.getProperty("backender.welcome_message");
        this.distanceFactor = Double.parseDouble(properties.getProperty("backender.distance_factor"));
        this.minimalDistanceForVehicleWithEngine = Double.parseDouble(properties.getProperty("backender.minimal_distance_for_vehicle_with_engine"));
        this.wordsRequiringBox = retrieveWordsRequiringBox(properties);
        this.sortOrder = properties.getProperty("backender.sort_order");
        comparatorMap = initializeComparatorMap();
    }

    private Properties getProperties() {
        try {
            Properties properties = new Properties();
            properties.load(Configuration.class.getResourceAsStream(Constants.PROPERTIES_FILE));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> retrieveWordsRequiringBox(Properties properties) {
        return Arrays.stream(properties.getProperty("backender.words_requiring_box")
                .split(Constants.WORDS_REQUIRING_BOX_DELIMITER))
                .collect(Collectors.toSet());
    }

    private Map<String, Comparator<OrderWithProximity>> initializeComparatorMap() {
        Map<String, Comparator<OrderWithProximity>> comparatorMap = new HashMap<>();
        comparatorMap.put(Constants.VIP_THAN_FOOD_THAN_DISTANCE, Constants.COMPARATOR_BY_VIP_THAN_FOOD_THAN_DISTANCE);
        comparatorMap.put(Constants.VIP_THAN_DISTANCE_THAN_FOOD, Constants.COMPARATOR_BY_VIP_THAN_DISTANCE_THAN_FOOD);
        return comparatorMap;
    }

    String getWelcomeMessage() {
        return welcomeMessage;
    }

    Double getDistanceFactor() {
        return distanceFactor;
    }

    Double getMinimalDistanceForVehicleWithEngine() {
        return minimalDistanceForVehicleWithEngine;
    }

    Set<String> getWordsRequiringBox() {
        return wordsRequiringBox;
    }

    String getSortOrder() {
        return sortOrder;
    }

    Map<String, Comparator<OrderWithProximity>> getComparatorMap() {
        return comparatorMap;
    }
}
