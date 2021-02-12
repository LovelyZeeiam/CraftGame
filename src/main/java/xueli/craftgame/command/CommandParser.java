package xueli.craftgame.command;

import xueli.craftgame.world.World;

public abstract class CommandParser {

    private String name;

    public CommandParser(String name) {
        this.name = name;

    }

    public abstract void parse(CommandArguments args, World world) throws Exception;

    public abstract String getHelp();

    public String getName() {
        return name;
    }

}
