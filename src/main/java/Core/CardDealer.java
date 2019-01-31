package Core;

import Elements.Card;
import Elements.Deck;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 *      The CardDealer class is a class of methods to handle elements of the Card
 *      and Deck classes. When the class is initialized, it builds a standard deck
 *      with no jokers to add to an ArrayList of decks.
 *
 *      The methods contained in the Card Dealer class are deal(), deckParser(),
 *      buildDeck(), getDecks(), getInstanceNumber(), and setInstanceNumber.
 *
 * @author Stephen Sumner
 */

public class CardDealer
{
    private static ArrayList<Deck> decks = new ArrayList<Deck>();
    private static int instanceNumber;
    private static String[] keywords = {"+owned","+jokers", "+private"};

    /** Constructor for the CardDealer class. Most methods can be called without an
     * instance of this class. It takes no parameters, but sets the instanceNumber
     * and builds the initial card deck.
     */

    public CardDealer()
    {
        getDecks().add(new Deck("standard","DMBot"));
        setInstanceNumber(1);
    }

    public static void showHand(GuildMessageReceivedEvent event)
    {
        String userID = event.getMember().getNickname();
        String title = userID + "'s Hand of cards";
        String description = "";
        EmbedBuilder embed = new EmbedBuilder();
        MessageBuilder message = new MessageBuilder();

        for (Deck deck: getDecks())
        {
            for (Card card: deck.getCards(deck))
            {
                if (card.getOwner().equals(userID))
                {
                    if (card.getRank().equals("Joker"))
                        description += card.getRank();
                    else
                        description += card.getRank() + " of " + card.getSuit();
                    description += " from deck: " + deck.getDeckName();
                }
            }
        }

        embed.setTitle(title);
        embed.setDescription(description);
        message.setEmbed(embed.build());
        event.getChannel().sendMessage(message.build()).queue();
    }

    public static void discard(GuildMessageReceivedEvent event)
    {
        String description = "";
        String title = "These are the Cards you are discarding";
        String userID = event.getMember().getNickname();
        EmbedBuilder embed = new EmbedBuilder();
        MessageBuilder message = new MessageBuilder();

        for (Deck deck: getDecks())
        {
            for (Card card: deck.getCards(deck))
            {
                if (card.getOwner().equals(userID))
                {
                    card.setOwner("discarded");
                    if (card.getRank().equals("Joker"))
                    {
                        description += card.getRank() + " from deck: " + deck.getDeckName() + "\n";
                    }
                    else
                    {
                        description += card.getRank() + " of " + card.getSuit();
                        description += " from deck: " + deck.getDeckName() + "\n";
                    }
                }
            }
        }

        embed.setTitle(title);
        embed.setDescription(description);
        message.setEmbed(embed.build());
        event.getChannel().sendMessage(message.build()).queue();

    }

    public static void shuffle(ArrayList<String> decklist, GuildMessageReceivedEvent event)
    {
        String title = "DMBot Deck shuffler";
        String description = "";
        String fieldtitle = "The following cards were discarded";
        String fieldmessage = "";
        String userID = event.getMember().getNickname();

        if (decklist.contains("+owned"))
        {
            for (Deck deck: getDecks())
            {
                if (deck.getDeckName().equals(userID))
                {
                    description += deck.getDeckName() + " is the deck owner.\n";
                    description += "All cards not in the deck will be discarded and shuffled back in";

                    for (Card card: deck.getCards(deck))
                    {
                        if (!card.getOwner().equals("Deck"))
                        {
                            if (card.getRank().equals("Joker"))
                                fieldmessage += card.getRank();
                            else
                                fieldmessage += card.getRank() + " of " + card.getSuit();
                            if (card.getOwner().equals("discarded"))
                                fieldmessage += " from the discard pile.\n";
                            else
                                fieldmessage += " from " + card.getOwner() + "'s hand.\n";

                            card.setOwner("Deck");
                        }
                    }
                }
            }
        }
        else
        {
            for (Deck deck: getDecks())
            {
                if (deck.getDeckName().equals("DMBot"))
                {
                    description += "DMBot is the deck owner.\n";
                    description += "All cards not in the deck will be discarded and shuffled back in";

                    for (Card card: deck.getCards(deck))
                    {
                        if (!card.getOwner().equals("Deck"))
                        {
                            if (card.getRank().equals("Joker"))
                                fieldmessage += card.getRank();
                            else
                                fieldmessage += card.getRank() + " of " + card.getSuit();
                            if (card.getOwner().equals("discarded"))
                                fieldmessage += " from the discard pile.\n";
                            else
                                fieldmessage += " from " +card.getOwner() + "'s hand.\n";

                            card.setOwner("Deck");
                        }
                    }
                }
            }
        }

        EmbedBuilder embed = new EmbedBuilder();
        MessageBuilder message = new MessageBuilder();
        embed.setTitle(title);
        embed.setDescription(description);
        embed.addField(fieldtitle,fieldmessage,false);
        message.setEmbed(embed.build());
        event.getChannel().sendMessage(message.build()).queue();
    }

    public static void listDecks(GuildMessageReceivedEvent event)
    {
        String sendMessage = "";
        String title = "DMBot List of Decks";
        String description = "A list of all the decks DMBot is keeping track of";

        for (Deck deck: getDecks())
            sendMessage = sendMessage + deck.getDeckName() + "\n";


        EmbedBuilder embed = new EmbedBuilder();
        MessageBuilder message = new MessageBuilder();
        embed.setTitle(title);
        embed.setDescription(description);
        embed.addField("List of Decks", sendMessage, false);
        message.setEmbed(embed.build());
        event.getChannel().sendMessage(message.build()).queue();
    }

    public static void deal(ArrayList<String> decklist, GuildMessageReceivedEvent event)
    {
        Random randomCard = new Random();
        Boolean cardDealt = false;
        String userID = event.getMember().getNickname();
        String dealtCard = "You were dealt a ";

        while (!cardDealt)
        {
            int index = randomCard.nextInt(52);

            if (decklist.contains("+owned"))
            {
                for (Deck deck: getDecks())
                {
                    if (deck.getDeckName().equals(userID))
                    {
                        if (deck.getCards(deck).get(index).getOwner().equals("Deck"))
                        {
                            deck.getCards(deck).get(index).setOwner(userID);
                            String rank = deck.getCards(deck).get(index).getRank();
                            String suit = deck.getCards(deck).get(index).getSuit();
                            if (rank.equals("Joker"))
                                dealtCard = dealtCard + rank;
                            else
                                dealtCard = dealtCard + rank + " of " + suit;
                            dealtCard += " from deck: " +deck.getDeckName();
                            event.getChannel().sendMessage(dealtCard).queue();
                            cardDealt = true;
                        }
                    }
                }
            }
            else
            {
                for (Deck deck: getDecks())
                {
                    if (deck.getDeckName().equals("DMBot"))
                    {
                        if (deck.getCards(deck).get(index).getOwner().equals("Deck"))
                        {
                            deck.getCards(deck).get(index).setOwner(userID);
                            String rank = deck.getCards(deck).get(index).getRank();
                            String suit = deck.getCards(deck).get(index).getSuit();
                            if (rank.equals("Joker"))
                                dealtCard = dealtCard + rank;
                            else
                                dealtCard = dealtCard + rank + " of " + suit;
                            dealtCard += " from deck: " +deck.getDeckName();
                            event.getChannel().sendMessage(dealtCard).queue();
                            cardDealt = true;
                        }
                    }
                }
            }
        }

    }

    /** This method takes in the string representing the discord message and breaks it
     * into tokens that are used in the other methods of this class like deal and buildDeck.
     *
     * @param str
     * @return ArrayList<>
     */

    public static ArrayList<String> deckParser(String str)
    {

        ArrayList<String> deckParameters = new ArrayList<String>();
        for (String key: keywords)
        {
            if (str.toLowerCase().contains(key))
            {
                deckParameters.add(key);
            }
        }

        return deckParameters;
    }

    public static void buildDeck(ArrayList<String> deckParameters, GuildMessageReceivedEvent event)
    {
        String name;
        String type;

        if (deckParameters.contains("+owned"))
        {
            name = event.getMember().getNickname();
        }
        else
        {
            setInstanceNumber(getInstanceNumber() + 1);
            name = "DMBot" + CardDealer.getInstanceNumber();
        }

        if (deckParameters.contains("+jokers"))
        {
            type = "jokers";
        }
        else
        {
            type = "standard";
        }

        Deck newdeck = new Deck(type, name);
        getDecks().add(newdeck);
        String message = "A new deck was built using the name: " + newdeck.getDeckName();
        event.getChannel().sendMessage(message).queue();

    }

    public static ArrayList<Deck> getDecks() {
        return decks;
    }

    public static int getInstanceNumber() {
        return instanceNumber;
    }

    public static void setInstanceNumber(int newNumber) {
        instanceNumber = newNumber;
    }
}
