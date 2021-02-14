package xueli.craftgame.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import xueli.craftgame.world.World;

public class ConsoleCommandParser extends Thread {

    private Scanner scanner;
    private Commands commands;
    private World world;

    private boolean stop = false;

    public ConsoleCommandParser(Commands commands, World world) {
        this.scanner = new Scanner(System.in);
        this.commands = commands;
        this.world = world;

    }

    @Override
    public void run() {
        while(!stop) {
            String command = null;
            try {
            	command = scanner.nextLine();
            } catch (NoSuchElementException e) {
				xueli.utils.Logger.warn("Wanna use Ctrl-Z to crash the game~");
				System.exit(114514);
			}
            world.getWorldLogic().executeCommands(command);


        }

    }

    public void stopstopstop() {
        this.stop = true;
    }

}
