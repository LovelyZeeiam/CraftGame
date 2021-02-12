package xueli.craftgame.command;

import xueli.craftgame.world.World;
import xueli.gamengine.utils.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleCommandParser extends Thread {

    private BufferedReader scanner;
    private Commands commands;
    private World world;

    private boolean stop = false;

    public ConsoleCommandParser(Commands commands, World world) {
        this.scanner = new BufferedReader(new InputStreamReader(System.in));
        this.commands = commands;
        this.world = world;

    }

    @Override
    public void run() {
        while(!stop) {
            try {
                String command = scanner.readLine();
                commands.parse(command);
            } catch (CommandParseException e) {
                Logger.error("Command Error: " + e.getMessage());
            } catch(Exception ignored) {}

        }

    }

    public void stopstopstop() {
        this.stop = true;
    }

}
