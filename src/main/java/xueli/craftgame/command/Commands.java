package xueli.craftgame.command;

import java.util.HashMap;

import xueli.craftgame.command.parsers.CommandFunction;
import xueli.craftgame.command.parsers.CommandPlaysound;
import xueli.craftgame.command.parsers.CommandSay;
import xueli.craftgame.command.parsers.CommandSchedule;
import xueli.craftgame.world.World;
import xueli.utils.Logger;

public class Commands {
	
	private World world;
	private HashMap<String, CommandParser> parsers = new HashMap<>();
	
	public Commands(World world) {
		this.world = world;
		init();
		
	}
	
	private void init() {
		register(new CommandSay());
		register(new CommandPlaysound());
		register(new CommandSchedule());
		register(new CommandFunction());

	}

	private void register(CommandParser parser) {
		parsers.put(parser.getName(), parser);

	}

	public void parse(String command) throws CommandParseException {
		if(command == null || !command.startsWith("/"))
			return;
		String realCommand = command.substring(1);

		try {
			String[] tokens = realCommand.split("\\s+");
			CommandParser parser = parsers.get(tokens[0]);
			if (parser == null) {
				throw new CommandParseException("Can't find command: " + tokens[0]);
			}

			if(tokens[1].equals("--help")) {
				Logger.info("Command Helper: " + parser.getHelp());

			} else {
				String[] args = new String[tokens.length - 1];
				System.arraycopy(tokens, 1, args, 0, args.length);
				parser.parse(new CommandArguments(args), world);

			}
		} catch (Exception e) {
			throw new CommandParseException(e);
		}

	}
	
}
