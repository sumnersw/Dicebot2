/**
 * Part of the Core package
 * @author Stephen Sumner
 */
package Core;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Class that evaluates every message sent in a discord guild for keywords
 * that relate to commands, then executes those commands. The class extends
 * ListenerAdapter from the Java Discord API, which in turn extends EventListener
 */

public class MessageHandler extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String message = event.getMessage().getContentRaw();

        if (message.toLowerCase().contains("/roll"))
        {
            ArrayList<String> dicelist = DiceRoller.diceParser(message);
            DiceRoller.rollDice(dicelist,event);
        }
        else if (message.toLowerCase().contains("/newdeck"))
        {
            ArrayList<String> decklist = CardDealer.deckParser(message);
            CardDealer.buildDeck(decklist, event);
        }
        else if (message.toLowerCase().contains("/deal"))
        {
            ArrayList<String> decklist = CardDealer.deckParser(message);
            CardDealer.deal(decklist, event);
        }
        else if (message.toLowerCase().contains("/discard"))
        {
            CardDealer.discard(event);
        }
        else if (message.toLowerCase().contains("/decks"))
        {
            CardDealer.listDecks(event);
        }
        else if (message.toLowerCase().contains("/shuffle"))
        {
            ArrayList<String> decklist = CardDealer.deckParser(message);
            CardDealer.shuffle(decklist, event);
        }
        else if (message.toLowerCase().contains("/hand"))
        {
            CardDealer.showHand(event);
        }
        else if (message.toLowerCase().contains("/play"))
        {

        }

    }

    @Override
    public void onReady(ReadyEvent event)
    {

    }
}
