package xueli.gamengine.musicjson;

public class Note extends INoteCommand {

    private int type;
    private float rate;

    public Note(int type, float rate) {
        this.type = type;
        this.rate = rate;
    }

    public int getType() {
        return type;
    }

    public float getRate() {
        return rate;
    }
}
