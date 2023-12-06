package com.mauro.magicassistantultrarapidoptimized.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardsService {

    /**
     * This method takes a list of dirty strings and once converted into HashMaps by the method "offersDecrypter"
     * returns the stores who sell all of them sorted by price
     */
    public HashMap<String, Float> compareStores(List<String> request) {

        // Create a List of all the HashMaps to compare
        List<HashMap<String, Float>> mapsToCompare = new ArrayList<>();
        request.forEach(str -> {
            mapsToCompare.add(offersDecrypter(str));
        });
        HashMap<String, Float> unorderedMap = new HashMap<>();

        // Merge all the maps into one
        mapsToCompare.forEach(map -> {
            map.forEach((store, price) -> {
                unorderedMap.put(store, 0f);
                mapsToCompare.forEach(mapToInspect -> {
                    if (!mapToInspect.containsKey(store)) {
                        unorderedMap.remove(store);
                    }
                });
            });
        });

        //Now we sum the price of all the cards in common
        unorderedMap.forEach((store, price) -> {
            mapsToCompare.forEach(map -> {
                unorderedMap.put(store, unorderedMap.get(store) + map.get(store));
            });
        });
        //Sort the map by value

        return unorderedMap.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    /**
     * This method transforms a dirty string of data into an HashMap (store, price)
     */
    public HashMap<String, Float> offersDecrypter(String request) {
        String[] partialResult = request.split(" ");
        HashMap<String, Float> result = new HashMap<>();
        int i = 0;
        int j;

        //There we make a first clear of '€', cards status and all quantity numbers
        while (i < partialResult.length) {
            if (partialResult[i].equals("PO") || partialResult[i].equals("PL") || partialResult[i].equals("LP") ||
                partialResult[i].equals("GD") || partialResult[i].equals("EX") || partialResult[i].equals("NM") ||
                partialResult[i].equals("M") || isNumber(partialResult[i]) || partialResult[i].endsWith("€") ||
                (isNumber(partialResult[i].substring(0, partialResult[i].length() - 1)) && partialResult[i].endsWith("K"))) {
                partialResult[i] = "";
            }
            i++;
        }

        //There we remove all the empty strings
        partialResult = Arrays.stream(partialResult).filter(x -> !x.isEmpty()).toList().toArray(new String[0]);

        // There we transform all strings representing the price into float prices
        i = 0;
        while (i < partialResult.length) {
            if (partialResult[i].contains(",")) {
                partialResult[i] = partialResult[i].replace(",", ".");
            }
            i++;
        }

        //There we assemble the result skipping not useful strings
        i = partialResult[0].isEmpty() ? 1 : 0;
        j = i + 1;
        while (i < partialResult.length) {
            while (!isFloat(partialResult[j])) {
                j++;
            }
            if (!result.containsKey(partialResult[i])) {
                result.put(partialResult[i], Float.parseFloat(partialResult[j]));
            }
            i = j + 1;
            j = i + 1;
        }
        return result;
    }

    public boolean isNumber(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
