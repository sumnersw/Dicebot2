import Core.CardDealer;
import Core.DiceRoller;
import Core.MessageHandler;
import Core.MusicPlayer;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        JDABuilder dicebot = new JDABuilder(AccountType.BOT);
        DiceRoller diceRoller = new DiceRoller();
        CardDealer cardDealer = new CardDealer();
        MusicPlayer musicPlayer = new MusicPlayer();

        String token = "insert your token here";
        System.out.println(token);
        dicebot.setToken(token);
        dicebot.addEventListener(new MessageHandler());
        dicebot.build();




    }


}
