/**
 * Part of the Core package. The DiceRoller Class has methods dealing with
 * rolling dice. There are fields that have to do with particular game
 * settings, and a method can be called to change several settings based on
 * the rule set being used.
 * @author Stephen Sumner
 */
package Core;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceRoller {

    private static boolean explodingDice = false;
    private static boolean specialDice = false;
    private static boolean wildDie = false;
    private static boolean useEmbeds = true;

    public DiceRoller()
    {
        this.setExplodingDice(false);
        this.setSpecialDice(false);
        this.setWildDie(false);
        this.setUseEmbeds(true);
    }

    /**
     * A static method that evaluates a message and picks out parts of the
     * message that can be dice or modifiers, or operators. For example: 2d6,
     * 1d8, 10d100, +, -, and any plain digits. It returns an array list of
     * strings. and takes a string as a parameter.
     *
     * @param str
     * @return ArrayList<String>
     */
    public static ArrayList<String> diceParser(String str)
    {
        ArrayList<String> parameters = new ArrayList<>();
        Pattern pat = Pattern.compile("[+|-]|\\d+[d]\\d+|\\d+|[d]\\d+");
        Matcher mat = pat.matcher(str);

        while(mat.find())
        {
            String subsequence = mat.group();
            parameters.add(subsequence);
        }

        return parameters;
    }

    /**
     * This method takes in a string in the format of either NdM where N and M are integers,
     * or dX where X is an integer. It also takes in a message embed to add fields to before
     * the rollDice method posts the embed as a message later.
     * @param str
     * @param embed
     * @return
     */

    public static int roll(String str, EmbedBuilder embed)
    {
        int result = 0;
        String[] diceParameters = new String[2];
        String testString = str;
        String[] testArray = testString.split("d");

        if (testArray[0].contentEquals(""))
        {
            diceParameters[0] = "1";
            diceParameters[1] = testArray[1];
        }
        else
        {
            diceParameters[0] = testArray[0];
            diceParameters[1] = testArray[1];
        }

        int numOfDice = Integer.parseInt(diceParameters[0]);
        int sizeOfDice = Integer.parseInt(diceParameters[1]);
        String resultField = "";
        String resultTitle = "Rolling " + str;

        Random dice = new Random();

        for (int i =0; i < numOfDice; i++)
        {
            int n = dice.nextInt(sizeOfDice)+1;
            String dieResult = Integer.toString(n);
            resultField = resultField + dieResult + ", ";
            result += n;

        }

        embed.addField(resultTitle,resultField, false);
        return result;
    }

    /**
     * This method takes in an ArrayList of strings which have been parsed by the diceParser
     * method and will go through each element to either pass the element onto the roll method,
     * or will add to the total result. It will then post a message to the same discord channel
     * that the event passed into was posted in, in direct response to the event.
     * @param dicelist
     * @param event
     */
    public static void rollDice(ArrayList<String> dicelist, GuildMessageReceivedEvent event)
    {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.lightGray);
        MessageBuilder rollResult = new MessageBuilder();

        int index = 0;
        int total = 0;
        int size = dicelist.size();
        boolean subtract = false;

        String description = "Ok, Rolling the following:" + dicelist;
        String title = "DMBot Diceroller";
        embed.setTitle(title);
        embed.setDescription(description);

        while (index < size)
        {
            String nextstr = dicelist.get(index);

            if (nextstr.contentEquals("+"))
            {
                subtract = false;
            }
            else if (nextstr.contentEquals("-"))
            {
                subtract = true;
            }
            else if (nextstr.contains("d"))
            {
                int n = roll(nextstr, embed);
                if (subtract)
                    total -= n;
                else
                    total += n;
            }
            else
            {
                int y = Integer.parseInt(nextstr);
                if (subtract)
                    total -= y;
                else
                    total += y;
            }

            index++;
        }


        String result = "The total result is " + total;
        embed.addField(String.valueOf(dicelist),result, false);
        rollResult.setEmbed(embed.build());
        event.getChannel().sendMessage(rollResult.build()).queue();
    }

    public static void setSystem()
    {

    }

// Todo: create and alter methods to take into consideration the fields of explodingDice, wildDie, and SpecialDice.

    public static boolean isExplodingDice() {
        return explodingDice;
    }

    public void setExplodingDice(boolean explodingDice) {
        this.explodingDice = explodingDice;
    }

    public static boolean isSpecialDice() {
        return specialDice;
    }

    public void setSpecialDice(boolean specialDice) {
        this.specialDice = specialDice;
    }

    public static boolean isWildDie() {
        return wildDie;
    }

    public void setWildDie(boolean wildDie) {
        this.wildDie = wildDie;
    }

    public static boolean isUseEmbeds() {
        return useEmbeds;
    }

    public void setUseEmbeds(boolean useEmbeds) {
        this.useEmbeds = useEmbeds;
    }
}
