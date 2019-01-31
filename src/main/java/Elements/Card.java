package Elements;

public class Card {

    private String suit = null;
    private String rank = null;
    private String owner = null;
    private int index;

    public Card (String suit, String rank, String owner, int index)
    {
        this.setSuit(suit);
        this.setRank(rank);
        this.setOwner(owner);
        this.setIndex(index);

    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
