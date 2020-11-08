package xueli.gamengine.musicjson;

public class CommandWait extends INoteCommand {

    private int waitTime;

    public CommandWait(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getWaitTime() {
        return waitTime;
    }
}
