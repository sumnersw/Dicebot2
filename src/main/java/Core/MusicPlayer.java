package Core;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class MusicPlayer
{

    public MusicPlayer()
    {
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioPlayer player = playerManager.createPlayer();
    }
}
