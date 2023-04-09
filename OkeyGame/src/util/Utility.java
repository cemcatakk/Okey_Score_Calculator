package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import objects.Player;
import objects.Tile;

public class Utility {
	
	 /**
     * Evaluates the hand of a player by calculating the maximum score
     * and updates the player's score accordingly.
     *
     * @param player   the player whose hand is to be evaluated
     * @param okeyTile the okey tile, which can act as a wildcard
     */
    public static void processHand(Player player, Tile okeyTile) {
        ArrayList<Tile> hand = player.getHand();

        int maxScore = 0;
        int[] orders = {0, 1, 2};
        int[] scores = new int[6];

        do {
            ArrayList<Tile> tempHand = new ArrayList<>(hand);
            scores[0] = calculateScore(tempHand, orders);
            maxScore = Math.max(maxScore, scores[0]);

            if (nextPermutation(orders)) {
                tempHand = new ArrayList<>(hand);
                tempHand.forEach(tile->tile.setUsed(false));
                scores[1] = calculateScore(tempHand, orders);
                maxScore = Math.max(maxScore, scores[1]);
            }

        } while (nextPermutation(orders));

        player.setScore(maxScore);
    }

    /**
     * Calculates the score of a hand based on the given order of operations.
     *
     * @param hand   the list of tiles in the player's hand
     * @param orders the order of operations for scoring (0: pairs, 1: sets, 2: series)
     * @return the score of the hand
     */
    private static int calculateScore(List<Tile> hand, int[] orders) {
        int score = 0;
        for (int order : orders) {
            if (order == 0) {
                score += countPairs(hand);
            } else if (order == 1) {
                score += countSets(hand);
            } else {
                score += countSeries(hand);
            }
        }
        score += countPairs(hand);
        return score;
    }

    /**
     * Generates the next permutation of the given array.
     * Allows us to try all possible combinations to calculate score.
     *
     * @param array the input array
     * @return true if the next permutation is generated, false if there are no more permutations
     */
    public static boolean nextPermutation(int[] array) {
        int i = array.length - 1;
        while (i > 0 && array[i - 1] >= array[i]) {
            i--;
        }

        if (i <= 0) {
            return false;
        }

        int j = array.length - 1;
        while (array[j] <= array[i - 1]) {
            j--;
        }

        int temp = array[i - 1];
        array[i - 1] = array[j];
        array[j] = temp;

        j = array.length - 1;
        while (i < j) {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }
        return true;
    }

    /**
     * Groups the given list of tiles by their color.
     *
     * @param hand the list of tiles to group
     * @return a HashMap with color keys and lists of tiles as values
     */
    private static HashMap<String, List<Tile>> groupByColor(List<Tile> hand) {
        HashMap<String, List<Tile>> colorGroups = new HashMap<>();
        for (Tile tile : hand) {
            if (!colorGroups.containsKey(tile.getColor())) {
                colorGroups.put(tile.getColor(), new ArrayList<>());
            }
            colorGroups.get(tile.getColor()).add(tile);
        }
        return colorGroups;
    }


    /**
     * Counts the number of pairs in the given list of tiles.
     *
     * @param tiles the list of tiles to search for pairs
     * @return the number of pairs found
     */
    private static int countPairs(List<Tile> tiles) {
        HashMap<String, HashMap<Integer, Integer>> colorValueCounts = new HashMap<>();
        for (Tile tile : tiles) {
            if (!tile.isUsed()) {
                if (!colorValueCounts.containsKey(tile.getColor()) && !tile.isWildcard()) {
                    colorValueCounts.put(tile.getColor(), new HashMap<>());
                }
                HashMap<Integer, Integer> valueCounts = colorValueCounts.get(tile.getColor());
                if(valueCounts!=null)
                	valueCounts.put(tile.getValue(), valueCounts.getOrDefault(tile.getValue(), 0) + 1);
            }
        }

        int pairs = 0;
        for (Map.Entry<String, HashMap<Integer, Integer>> colorEntry : colorValueCounts.entrySet()) {
            for (Map.Entry<Integer, Integer> valueEntry : colorEntry.getValue().entrySet()) {
                int count = valueEntry.getValue();
                if (count >= 2) {
                    pairs += count;
                    tiles.stream()
                        .filter(tile -> tile.getColor().equals(colorEntry.getKey()) && tile.getValue() == valueEntry.getKey() && !tile.isUsed())
                        .limit(2)
                        .forEach(tile -> tile.setUsed(true));
                }
            }
        }
        return pairs;
    }


    /**
     * Counts the number of series in the given list of tiles.
     *
     * @param tiles the list of tiles to search for series
     * @return the number of series found
     */
    private static int countSeries(List<Tile> tiles) {
        tiles = tiles.stream().filter(tile -> !tile.isUsed()).collect(Collectors.toList());
        tiles.sort((t1, t2) -> {
            int colorCompare = t1.getColor().compareTo(t2.getColor());
            if (colorCompare != 0) {
                return colorCompare;
            }
            return Integer.compare(t1.getValue(), t2.getValue());
        });

        int seriesCount = 0;
        int consecutiveCount = 0;
        for (int i = 1; i < tiles.size(); i++) {
            if ((tiles.get(i).getColor().equals(tiles.get(i - 1).getColor()) && tiles.get(i).getValue() - tiles.get(i - 1).getValue() == 1) 
            		|| (tiles.get(i).isWildcard() && !tiles.get(i).isUsed())) {
                consecutiveCount++;
            } else {
                if (consecutiveCount >= 2) {
                    seriesCount += consecutiveCount + 1;
                    for (int j = i - consecutiveCount - 1; j < i; j++) {
                        tiles.get(j).setUsed(true);
                    }
                }
                consecutiveCount = 0;
            }
        }

        if (consecutiveCount >= 2) {
            seriesCount += consecutiveCount + 1;
            for (int j = tiles.size() - consecutiveCount - 1; j < tiles.size(); j++) {
                tiles.get(j).setUsed(true);
            }
        }

        return seriesCount;
    }


    /**
     * Counts the number of sets in the given list of tiles.
     *
     * @param hand the list of tiles to search for sets
     * @return the number of sets found
     */
    private static int countSets(List<Tile> hand) {
        HashMap<String, List<Tile>> colorGroups = groupByColor(hand);
        HashMap<Integer, Integer> valueCounts = new HashMap<>();

        for (Tile tile : hand) {
            if (!tile.isUsed()) {
                valueCounts.put(tile.getValue(), valueCounts.getOrDefault(tile.getValue(), 0) + 1);
            }
        }

        int sets = 0;
        for (Map.Entry<Integer, Integer> entry : valueCounts.entrySet()) {
            int value = entry.getKey();
            int count = (int) colorGroups.values().stream()
                .filter(tiles -> tiles.stream().anyMatch(tile ->( tile.getValue() == value && !tile.isUsed()) || (tile.isWildcard() && !tile.isUsed())))
                .count();

            if (count >= 3) {
                sets += count;
                for (List<Tile> tiles : colorGroups.values()) {
                    tiles.stream()
                        .filter(tile -> (tile.getValue() == value && !tile.isUsed()) || (tile.isWildcard() && !tile.isUsed()))
                        .findFirst()
                        .ifPresent(tile -> tile.setUsed(true));
                }
            }
        }
        return sets;
    }
}