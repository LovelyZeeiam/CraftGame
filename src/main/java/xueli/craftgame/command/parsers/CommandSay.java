package xueli.craftgame.command.parsers;

import xueli.craftgame.command.CommandArguments;
import xueli.craftgame.command.CommandParseException;
import xueli.craftgame.command.CommandParser;
import xueli.craftgame.world.World;

public class CommandSay extends CommandParser {

    public CommandSay() {
        super("say");
    }

    @Override
    public void parse(CommandArguments args, World world) throws CommandParseException {
        StringBuilder message = new StringBuilder();
        int count = args.getArgCount();

        for(int i = 0; i < count; i++) {
            message.append(args.getString(i)).append(" ");
        }

        System.out.println(message.toString());

    }

    @Override
    public String getHelp() {
        return "/say <Message: String>";
    }

}
