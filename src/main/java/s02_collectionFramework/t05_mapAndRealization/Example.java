package s02_collectionFramework.t05_mapAndRealization;

import java.util.*;

public class Example {
    public static void main(String[] args) {

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Milk",20);
        inventory.put("Bread",15);
        inventory.put("Apple",50);

        System.out.println("Storage: ");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Product: "+entry.getKey()+" || Value: "+entry.getValue());
        }

        inventory.put("Milk",30);
        System.out.println("\nNew Storage:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Product: "+entry.getKey()+" || Value: "+entry.getValue());
        }
    }
}

class ExampleTwo {
    public static void main(String[] args) {

        Map<String, String> orderMap = new LinkedHashMap<>();
        orderMap.put("First","Java");
        orderMap.put("Second","Python");
        orderMap.put("Third","C++");

        System.out.println("Keys:");
        for (String keys : orderMap.keySet()) {
            System.out.println(keys);
        }

        System.out.println("Values:");
        for (String values : orderMap.values()) {
            System.out.println(values);
        }

        System.out.println("LinkedHashMap:");
        for (String key : orderMap.keySet()) {
            System.out.println("Key: "+key+" || Value: "+orderMap.get(key));
        }

      /*  System.out.println("LinkedHashMap:");
        for (Map.Entry<String, String> entry : orderMap.entrySet()) {
            System.out.println("Key: "+entry.getKey()+" || Value: "+entry.getValue());
        }
      */
    }
}

class ExampleThree {
    public static void main(String[] args) {

        Map<String, Integer> sortedMap = new TreeMap<>();
        sortedMap.put("Banana",20);
        sortedMap.put("Apple",50);
        sortedMap.put("Coconut",15);

        System.out.println("TreeMap:");
        for (String key : sortedMap.keySet()) {
            System.out.println("Key: "+key+" || Value: "+sortedMap.get(key));
        }

       /* System.out.println("TreeMap:");
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            System.out.println("Key: "+entry.getKey()+" || Value: "+entry.getValue());
        }
       */
    }
}
