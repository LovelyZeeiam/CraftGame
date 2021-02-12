package xueli.craftgame.command.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import xueli.craftgame.command.CommandArguments;
import xueli.craftgame.command.CommandParser;
import xueli.craftgame.world.World;

public class CommandFunction extends CommandParser {
	
	private static final String FUNCTION_PATH_STRING = "functions/";

	public CommandFunction() {
		super("function");
	}

	@Override
	public void parse(CommandArguments args, World world) throws Exception {
		String functionName = args.getString(0);
		BufferedReader reader = new BufferedReader(new FileReader(new File(FUNCTION_PATH_STRING + functionName + ".cgfunction")));
		String lineString;
		while((lineString = reader.readLine()) != null) {
			if(lineString.startsWith("/function " + functionName)) {
				// 套娃? 不干
			} else {
				world.getWorldLogic().executeCommands(lineString);
				
			}
		}
		
	}

	@Override
	public String getHelp() {
		return "/function <FunctionName: String>";
	}

}
