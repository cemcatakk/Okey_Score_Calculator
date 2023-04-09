package objects;
import java.util.Objects;

import util.ConsoleColors;;
public class Tile implements Comparable<Tile>{
    private int value;
    private String color;
    private boolean used;
    private boolean fakeOkey;
    private boolean isWildcard;
    
    /**
     * Constructs a Tile object with the specified value and color.
     * 
     * @param value The value of the tile.
     * @param color The color of the tile.
     */
	public Tile(int value, String color) {
        this.value = value;
        this.color = color;
        this.fakeOkey = value == -1;
        used = false;
        isWildcard = false;
    }

	/**
     * Returns true if the tile is a wildcard, otherwise false.
     * 
     * @return A boolean representing whether the tile is a wildcard.
     */
	public boolean isWildcard() {
		return isWildcard;
	}
	
	/**
     * Sets the wildcard status of the tile.
     * 
     * @param isWildcard A boolean representing whether the tile is a wildcard.
     */
	public void setWildcard(boolean isWildcard) {
		this.isWildcard = isWildcard;
	}

	/**
     * Returns true if the tile is a fake Okey, otherwise false.
     * 
     * @return A boolean representing whether the tile is a fake Okey.
     */
	public boolean isFakeOkey() {
		return fakeOkey;
	}
	
	 /**
     * Sets the fake Okey status of the tile.
     * 
     * @param fakeOkey A boolean representing whether the tile is a fake Okey.
     */
	public void setFakeOkey(boolean fakeOkey) {
		this.fakeOkey = fakeOkey;
	}

	/**
     * Returns the value of the tile.
     * 
     * @return An integer representing the value of the tile.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the color of the tile.
     * 
     * @return A String representing the color of the tile.
     */
    public String getColor() {
        return color;
    }

    /**
     * Returns true if the tile is used, otherwise false.
     * 
     * @return A boolean representing whether the tile is used.
     */
    public boolean isUsed() {
		return used;
	}

    /**
     * Sets the used status of the tile.
     * 
     * @param used A boolean representing whether the tile is used.
     */
	public void setUsed(boolean used) {
		this.used = used;
	}

	/**
     * Returns a String representation of the Tile object.
     * 
     * @return A String representing the tile.
     */
    @Override
    public String toString() {
    	StringBuilder stringBuilder = new StringBuilder();
    	switch(color) {
    		case "Kirmizi":
    			stringBuilder.append(ConsoleColors.ANSI_RED);
    			break;
    		case "Mavi":
    			stringBuilder.append(ConsoleColors.ANSI_BLUE);
    			break;
    		case "Sari":
    			stringBuilder.append(ConsoleColors.ANSI_YELLOW);
    			break;
    		case "Siyah":
    			stringBuilder.append(ConsoleColors.ANSI_BLACK);
    			break;
    		default:
    			stringBuilder.append(ConsoleColors.ANSI_BLACK);
    			break;
    	}
    	stringBuilder.append(fakeOkey ? "SO" : value);
    	stringBuilder.append(ConsoleColors.ANSI_BLACK);
    	
        return stringBuilder.toString();
    }
    
    /**
    * Compares this tile with the specified tile for order.
    * 
    * @param o The tile to be compared.
   	* @return A negative, zero, or positive integer as this tile is less than, equal to, or greater than the specified tile.
	*/
	@Override
	public int compareTo(Tile o) {
		if(getColor().equals(o.getColor())) {
			return getValue() - o.getValue();
		}
		else {
			return getColor().compareTo(o.getColor());
		}
	}

	/**
	 * Returns a hash code value for the Tile object.
	 * 
	 * @return An integer representing the hash code value for the Tile object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(color, value);
	}

	/**
	 * Indicates whether some other object is "equal to" this Tile object.
	 * 
	 * @param obj The reference object with which to compare.
	 * @return A boolean representing whether the specified object is equal to this Tile object.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		return Objects.equals(color, other.color) && value == other.value;
	}
	
}
