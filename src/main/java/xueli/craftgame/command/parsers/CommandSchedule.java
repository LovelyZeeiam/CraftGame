package xueli.craftgame.command.parsers;

import xueli.craftgame.command.CommandArguments;
import xueli.craftgame.command.CommandParser;
import xueli.craftgame.world.World;

public class CommandSchedule extends CommandParser {

    public CommandSchedule() {
        super("schedule");
    }

    @Override
    public void parse(CommandArguments args, World world) throws Exception {
        int millsecond = args.getInt(0);

        StringBuilder scheduleCommand = new StringBuilder();
        int count = args.getArgCount();
        for(int i = 1; i < count; i++) {
        	scheduleCommand.append(args.getString(i)).append(" ");
        }

        world.getWorldLogic().getCg().timerQueue.addQueue(millsecond, () -> {
        	if(world.getWorldLogic().running) {
        		world.getWorldLogic().executeCommands(scheduleCommand.toString());
        	}
        });

    }

    @Override
    public String getHelp() {
        return "/schedule <ScheduleMillisecond: Integer> <ScheduleCommand: String>";
    }

}
