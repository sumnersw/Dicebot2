This is a java version of my own dice rolling bot for a discord server that I'm working on. It has only basic functionality right now, but I plan on adding the ability to change settings for the dice roller. This is mainly to assist in the running of play by post RPG games. 

Some of the functionality I plan on adding:
- System settings
- dice properties (exploding dice, special faces etc.)
- music player (for game masters that want to add atmosphere)


Some of the things I've learned while working on this project:
- implementing the discord bot required getting familiar with Gradle or Maven. I chose Gradle.
- reading and understanding third party API documentation
- the nature of the fact that the bot needs to connect to a server and read messages from the server makes unit testing some of the functions particularly difficult. Old School testing was required by manually typing messages in the server to test functions because it was easier.
