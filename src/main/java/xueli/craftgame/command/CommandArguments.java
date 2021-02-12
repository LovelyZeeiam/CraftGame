package xueli.craftgame.command;

public class CommandArguments {

    private String[] args;

    public CommandArguments(String[] args) {
        this.args = args;

    }

    public int getInt(int i) {
        return Integer.parseInt(args[i]);
    }

    public String getString(int i) {
        return args[i];
    }

    public float getFloat(int i) throws CommandParseException {
        try {
            return Float.parseFloat(args[i]);
        } catch (NumberFormatException e) {
            throw new CommandParseException("In No." + i + " arguments: require float!");
        }
    }

    public boolean getBoolean(int i) throws CommandParseException {
        try {
            return Boolean.parseBoolean(args[i]);
        } catch (NumberFormatException e) {
            throw new CommandParseException("In No." + i + " arguments: require boolean!");
        }
    }

    public int getArgCount() { return args.length; }

}
