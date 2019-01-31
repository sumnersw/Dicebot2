package Elements;

import java.util.ArrayList;

public class Deck {

    private static String suitList = "Hearts Diamonds Clubs Spades";
    private static String rankList = "Ace King Queen Jack Ten Nine Eight Seven Six Five Four Three Two";
    private static String joker = "Joker";

    private static String[] Suits = suitList.split(" ");
    private static String[] Ranks = rankList.split(" ");

    private String deckName;
    private ArrayList<Card> listOfCards;

    public Deck (String decktype, String name)
    {
        this.setDeckName(name);
        listOfCards = new ArrayList<>();
        int index=0;

        if (decktype.equalsIgnoreCase("standard"))
        {
            for (String rank:Ranks)
                for (String suit : Suits) {
                    listOfCards.add(new Card(suit, rank, "Deck", index));
                    index++;
                }
        }
        else if (decktype.equalsIgnoreCase("jokers")) {
            listOfCards.add(new Card(joker, joker, "Deck", index));
            index++;
            listOfCards.add(new Card(joker, joker, "Deck", index));
            index++;
            for (String rank : Ranks)
                for (String suit : Suits) {
                    listOfCards.add(new Card(suit, rank, "Deck", index));
                    index++;
                }
        }
    }

    public int cardsInDeck(Deck deck)
    {
        int length = getCards(deck).size();
        int cardsInDeck = 0;
        for (int i=0; i<length; i++)
        {
            if (getCards(deck).get(i).getOwner().contains("Deck"))
                cardsInDeck++;
        }


        return cardsInDeck;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public ArrayList<Card> getCards(Deck deck) {
        return listOfCards;
    }
}
