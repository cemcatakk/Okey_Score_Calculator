package objects;
import java.util.ArrayList;
import java.util.Collections;

import util.Utility;

public class OkeyGame {

	public static final int PLAYER_COUNT = 4;
	public static final int TILE_COUNT = 52;
	public static final int TILE_PER_HAND = 14;
	
	private Tile indicatorTile;
	private Tile okeyTile;
	private Deck deck;
	private ArrayList<Player> players;
	
	/**
     * Initializes a new Okey game by creating an empty list of players and a new deck.
     */
	public OkeyGame() {
		players = new ArrayList<Player>();
		deck = new Deck();
	}
	
	 /**
     * Starts the game by creating players, choosing the Okey tile, drawing hands, calculating scores,
     * and printing game information and the best players.
     */
	public void startGame() {
		createPlayers();
		chooseOkeyTile();
		drawHands();
		calculatePlayerScores();
		printGameInformation();
		printBestPlayers();
	}
	
	/**
     * Draws and distributes tiles to each player's hand.
     */
	private void drawHands() {
		System.out.println("Oyuncularin elleri dagitiliyor...");
		int firstPlayerIndex = (int) (Math.random() * PLAYER_COUNT);
        for (int i = 0; i < PLAYER_COUNT; i++) {
            int tilesToDraw = (i == firstPlayerIndex) ? TILE_PER_HAND + 1  : TILE_PER_HAND;
            for (int j = 0; j < tilesToDraw; j++) {
            	Tile tile = deck.drawTile();
            	if(tile.isFakeOkey()) {
            		tile = new Tile(okeyTile.getValue(),okeyTile.getColor());
            		tile.setFakeOkey(true);
            	}
            	else if(tile.equals(okeyTile)) {
            		tile.setWildcard(true);
            	}
                players.get(i).addTile(tile);
            }
            Collections.sort(players.get(i).getHand());
        }
	}
	
	/**
     * Calculates the scores of each player's hand.
     */
	private void calculatePlayerScores() {
		System.out.println("Oyuncularin skorlari hesaplaniyor...");
		for (Player player:players) {
			Utility.processHand(player,okeyTile);
		}
	}
	
	 /**
     * Prints the best players based on their scores.
     */
	void printBestPlayers() {
        int bestScore = -1;
        ArrayList<Integer> bestPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
        	Player player = players.get(i);
            int score = player.getScore();
            if (score > bestScore) {
                bestScore = score;
                bestPlayers.clear();
                bestPlayers.add(i);
            } else if (score == bestScore) {
                bestPlayers.add(i);
            }
        }
        System.out.println("En Iyi El(ler):");
        for (int playerIndex : bestPlayers) {
            System.out.println("Oyuncu " + (playerIndex + 1) + ": " + players.get(playerIndex));
        }
	}
	
	 /**
     * Creates player instances and adds them to the game.
     */
	void createPlayers() {
		System.out.println("Oyuncular Hazirlaniyor...");
		for (int i = 0; i < PLAYER_COUNT; i++) {
            players.add(new Player(i+1));
        }
	}
	
	 /**
     * Prints the game information, including the Okey tile and each player's hand.
     */
	void printGameInformation() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("Okey: %s\n", okeyTile));
		stringBuilder.append("Oyuncularin Elleri:\n");
		for(Player player:players) {
			stringBuilder.append(String.format("%s\n", player));
		}
		System.out.println(stringBuilder.toString());
	}
	 
	 /**
     * Chooses the Okey tile based on the indicator tile drawn from the deck.
     */
    private void chooseOkeyTile() {
    	System.out.println("Okey belirleniyor...");
    	indicatorTile = deck.drawNonFakeTile();
    	okeyTile = new Tile((indicatorTile.getValue() % 13) + 1, indicatorTile.getColor());
    }
}