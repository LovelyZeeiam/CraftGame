package xueli.craftgame.command.parsers;

import xueli.craftgame.command.CommandArguments;
import xueli.craftgame.command.CommandParser;
import xueli.craftgame.world.World;
import xueli.gamengine.utils.resource.SoundManager;

public class CommandPlaysound extends CommandParser {

    public CommandPlaysound() {
        super("playsound");
    }

    @Override
    public void parse(CommandArguments args, World world) throws Exception {
        String soundName = args.getString(0);
        float pitch = args.getFloat(1);
        float volume = args.getFloat(2);

        SoundManager.play(soundName, volume, pitch);

    }

    @Override
    public String getHelp() {
        return "/playsound <SoundName: String> <Pitch: Float> <Volume: Float>";
    }

}
