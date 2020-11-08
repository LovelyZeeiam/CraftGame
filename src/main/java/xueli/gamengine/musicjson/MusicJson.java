package xueli.gamengine.musicjson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xueli.gamengine.utils.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class MusicJson {

    static {
        try {
            Class.forName("xueli.gamengine.musicjson.NoteType");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Gson gson = new Gson();

    public MusicJson(String data) {
        JsonObject main_json = gson.fromJson(data, JsonObject.class);

        checkJsonHas("format_version", main_json);
        int version = main_json.get("format_version").getAsInt();
        switch(version) {
            case 0:
                read_version_0(main_json);
                break;
            default:
                Logger.error(new Exception("[MusicJson] Version " + version + " not supported!"));
                break;
        }

    }

    private String name;
    private String[] authors;
    private int bpm;
    private ArrayList<INoteCommand> notes = new ArrayList<>();

    private void read_version_0(JsonObject data) {
        checkJsonHas("name", data);
        checkJsonHas("bpm", data);
        checkJsonHas("author", data);

        // 获取name
        this.name = data.get("name").getAsString();
        // 获取authors
        JsonArray authorJson = data.getAsJsonArray("author");
        this.authors = new String[authorJson.size()];
        for(int i = 0;i < authorJson.size();i++)
            this.authors[i] = authorJson.get(i).getAsString();
        // 获取bpm
        this.bpm = data.get("bpm").getAsInt();

        JsonArray dataJson = data.get("data").getAsJsonArray();
        TreeMap<Integer, Note> notesCache = new TreeMap<>(Comparator.naturalOrder());

        dataJson.forEach((e) -> {
            JsonObject o = e.getAsJsonObject();

            int time = o.get("time").getAsInt();
            int type = NoteType.getBuffer(o.get("type").getAsString());
            int note = o.get("note").getAsInt();
            float rate = (float) Math.pow(2, (note - 12.0) / 12.0);

            Note n = new Note(type, rate);
            notesCache.put(time, n);

        });

        int lastTime = 0;
        notesCache.forEach((time,note) -> {
            int deltaTime = time - lastTime;
            if(deltaTime > 0)
                notes.add(new CommandWait(deltaTime));
            notes.add(note);

        });

    }

    private void checkJsonHas(String key, JsonObject object) {
        if(!object.has(key)) {
            Logger.error(new Exception("[MusicJson] Can't find param " + key + "!"));
        }

    }

    public void play() {
        notes.forEach((c) -> {
            if(c instanceof CommandWait){
                CommandWait wait = (CommandWait) c;
                synchronized (Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait(wait.getWaitTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if(c instanceof Note){
                Note note = (Note) c;
                NoteType.playSound(note.getType(), note.getRate());

            }

        });

    }

    public void release(){
        NoteType.release();

    }

}
