package objects;

import java.util.ArrayList;

public class Player {
    private ArrayList<Tile> hand;
    private int playerNumber;
    private int score;
    
    /**
     * Creates a new Player object with the specified player number.
     * 
     * @param playerNumber The number assigned to the player.
     */
	public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        hand = new ArrayList<>();
    }

	 /**
     * Adds a Tile object to the player's hand.
     * 
     * @param tile The tile to be added to the player's hand.
     */
    public void addTile(Tile tile) {
        hand.add(tile);
    }
    
    /**
     * Sets the player's hand with a new list of tiles.
     * 
     * @param hand An ArrayList of Tile objects representing the new hand.
     */
    public void setHand(ArrayList<Tile> hand) {
    	this.hand = hand;
    }
    
    /**
     * Returns the player's hand of tiles.
     * 
     * @return An ArrayList of Tile objects representing the player's hand.
     */
    public ArrayList<Tile> getHand() {
        return hand;
    }
    
    /**
     * Returns the player's assigned number.
     * 
     * @return An integer representing the player number.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Returns the player's current score.
     * 
     * @return An integer representing the player's score.
     */
    public int getScore() {
		return score;
	}

    /**
     * Sets the player's score to the specified value.
     * 
     * @param score The new score value for the player.
     */
	public void setScore(int score) {
		this.score = score;
	}

	 /**
     * Returns a string representation of the Player object, including the player number, hand, and score.
     * 
     * @return A string representing the player's information.
     */
    @Override
    public String toString() {
    	return String.format("Oyuncu %d: %s (Skor: %d)", getPlayerNumber(),hand,getScore());
    }
}
