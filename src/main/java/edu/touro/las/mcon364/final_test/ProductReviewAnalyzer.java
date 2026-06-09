package edu.touro.las.mcon364.final_test;

import java.util.*;
import java.util.stream.*;

/**
 * Product Review Analyzer
 *
 * Scenario: an e-commerce platform collects customer reviews. Each review
 * contains a product category tag (e.g., "electronics", "books", "clothing").
 * You need to count how many reviews each category has received and then
 * answer several questions about those counts — all in sorted order.
 *
 * Requirements:
 * - The constructor receives the list of category tags to analyze.
 * - buildCategoryFrequencyMap() returns a TreeMap<String, Long> where every key
 *   is a unique category and every value is how many reviews that category received.
 * - getTopNCategories(n) returns the n categories with the most reviews, sorted
 *   descending by count. Ties may appear in any order.
 * - getCategoriesStartingWith(prefix) returns a sorted list of all category names
 *   whose first character equals the given prefix character (e.g., 'e').
 * - getMostReviewedInRange(from, to) returns the category with the highest review
 *   count among categories in the alphabetical range [from, to] inclusive.
 *   Return Optional.empty() if the range contains no categories.
 *
 * Do not use explicit loops anywhere. Use streams and collectors instead.
 */
public class ProductReviewAnalyzer {

    //TODO - uncomment this field and initialize it in the constructor to store categories.
    private final List<String> categories;

    /**
     * Store the category tags that this analyzer will examine.
     * Constructor should make a defensive copy of the list to prevent external modification of the internal state of this class.
     * If the input list is null, throw an IllegalArgumentException.
     */
    public ProductReviewAnalyzer(List<String> categories) {
      //TODO - implement this constructor
        if (categories == null)
            throw new NullPointerException();
        // Store an immutable defensive copy of the list.
        // Changes to the original list will not affect this object.
        this.categories = List.copyOf(categories);
    }

    /**
     * Counts how many reviews each category received.
     * The returned map must be sorted alphabetically by category name.
     *
     * @return sorted frequency map
     */
    public TreeMap<String, Long> buildCategoryFrequencyMap() {
        //TODO - implement this method
        return categories.stream()
                .collect(Collectors.groupingBy(
                        words -> words,                                    TreeMap::new,
                        Collectors.counting()                       ));
    }

    /**
     * Returns the n most reviewed categories, highest count first.
     *
     * @param n number of top categories to return
     * @return list of category names, most reviewed first
     */
    public List<String> getTopNCategories(int n) {
        //TODO - implement this method
        return buildCategoryFrequencyMap()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry<String, Long>::getValue)
                        .reversed()
                )
                        .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Returns all categories whose first letter equals the given prefix letter,
     * in alphabetical order.
     * @param prefix the starting letter (e.g., 'e')
     * @return sorted list of matching category names
     */
    public List<String> getCategoriesStartingWith(char prefix) {
        //TODO - implement this method
        return buildCategoryFrequencyMap()
                .keySet()
                .stream()
                .filter(category -> category.charAt(0) == prefix)
                .sorted()
                .collect(Collectors.toList());
    }


    /**
     * Finds the most reviewed category in the alphabetical range [from, to] inclusive.
     *
     * @param from lower bound category name (inclusive)
     * @param to   upper bound category name (inclusive)
     * @return Optional containing the most reviewed category in range, or empty if none
     */
    public Optional<String> getMostReviewedInRange(String from, String to) {
        //TODO - implement this method
        return buildCategoryFrequencyMap()
                .subMap(from, true, to, true)
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }
}


