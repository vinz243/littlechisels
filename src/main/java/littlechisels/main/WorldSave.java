package littlechisels.main;

import java.io.File;

public class WorldSave {
    private final File levelDat;

    public WorldSave(File levelDat) {
        this.levelDat = levelDat;
    }

    public File getLevelDat() {
        return levelDat;
    }
}
