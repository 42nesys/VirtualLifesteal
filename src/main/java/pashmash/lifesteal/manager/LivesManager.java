package pashmash.lifesteal.manager;

import lombok.Getter;
import pashmash.lifesteal.Lifesteal;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class LivesManager {

    private final HashMap<UUID, Integer> localStorage = new HashMap<>();

    public LivesManager() {
        ResultSet resultSet = Lifesteal.getInstance().getSqlUtil().getResult("SELECT * FROM Lives");
        try {
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("UUID"));
                int lives = resultSet.getInt("lives");
                localStorage.put(uuid, lives);
            }
        } catch (Exception ignored) { }
    }

    public void loadLocal(UUID uuid) {
        ResultSet resultSet = Lifesteal.getInstance().getSqlUtil().getResult("SELECT * FROM Lives WHERE UUID='" + uuid.toString() + "'");
        try {
            if (resultSet.next()) {
                int lives = resultSet.getInt("lives");
                localStorage.put(uuid, lives);
            }
        } catch (Exception ignored) { }
    }

    public void set(UUID uuid, int lives) {
        Lifesteal.getInstance().getSqlUtil().update("UPDATE Lives SET lives=" + lives + " WHERE UUID='" + uuid.toString() + "'");
        localStorage.put(uuid, lives);
    }

    public void delete(UUID uuid) {
        Lifesteal.getInstance().getSqlUtil().update("DELETE FROM Lives WHERE UUID='" + uuid.toString() + "'");
        localStorage.remove(uuid);
    }

    public int get(UUID uuid) {
        if (localStorage.containsKey(uuid)) {
            return localStorage.get(uuid);
        } else {
            String livesString = Lifesteal.getInstance().getSqlUtil().get("Lives", "lives", "UUID='" + uuid.toString() + "'");
            int lives = livesString != null ? Integer.parseInt(livesString) : 0;
            localStorage.put(uuid, lives);
            return lives;
        }
    }
}