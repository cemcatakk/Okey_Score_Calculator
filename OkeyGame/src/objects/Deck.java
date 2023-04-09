package objects;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Tile> tiles;

    /**
     * Initializes a new Deck by creating an empty list of tiles, creating the deck, and shuffling it.
     */
    public Deck() {
        tiles = new ArrayList<>();
        createDeck();
        shuffleDeck();
    }

    /**
     * Creates the deck by adding tiles of each color and value, as well as two fake Okey tiles.
     */
    private void createDeck() {
        String[] colors = {"Sari", "Mavi", "Siyah", "Kirmizi"};
        for (int i = 0; i < 2; i++) {
            for (String color : colors) {
                for (int value = 1; value <= 13; value++) {
                    tiles.add(new Tile(value, color));
                }
            }
        }
        tiles.add(new Tile(-1, "Sahte Okey"));
        tiles.add(new Tile(-1, "Sahte Okey"));
    }

    /**
     * Shuffles the deck using Java's Collections.shuffle() method.
     */
    private void shuffleDeck() {
        Collections.shuffle(tiles);
    }

    /**
     * Draws a tile from the top of the deck and removes it from the deck.
     * 
     * @return The drawn Tile.
     */
    public Tile drawTile() {
        return tiles.remove(0);
    }
    
    /**
     * Draws a non-fake Okey tile from the deck and removes it from the deck.
     * 
     * @return The drawn non-fake Okey Tile.
     */
    public Tile drawNonFakeTile() {
    	int index = 0;
    	while(tiles.get(index).isFakeOkey()) {
    		index++;
    	}
    	return tiles.remove(index);
    }
}
