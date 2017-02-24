package atlantis;

import atlantis.util.AtlantisUtilities;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafal Poniatowski <ravaelles@gmail.com>
 */
public class AtlantisIgniter {

//    private static final String RACE = "Terran";
//    private static final String RACE = "Protoss";
    private static final String OUR_RACE = "Zerg";

//    private static final String ENEMY_RACE = "Terran";
//    private static final String ENEMY_RACE = "Protoss";
    private static final String ENEMY_RACE = "Zerg";
    
    // =========================================================
    
    private static boolean shouldUpdateFileContent = false;
    private static String bwapiIniPath = null;
    private static String[] fileContent = null;
    
    // =========================================================
    
    public static void modifyBwapiFileIfNeeded() {
        
        // Try locating bwap.ini file
        bwapiIniPath = defineBwapiIniPath();
        if (bwapiIniPath == null) {
            System.err.println("Couldn't locate bwapi.ini file, not changing it.");
            return;
        }
        
        // Read every single line
        ArrayList<String> linesList = AtlantisUtilities.readTextFileToList(bwapiIniPath);
        fileContent = new String[linesList.size()];
        fileContent = linesList.toArray(fileContent);
        
        // === Modify some sections ================================
        
        updateOurRace();
        updateEnemyRace();
        
        // =========================================================
        
        // Write to bwapi.ini new, modified file version
        if (shouldUpdateFileContent) {
            writeToBwapiIni();
        }
    }
    
    // =========================================================

    private static String defineBwapiIniPath() {
        File file;
        String path;
        
        path = "D:/GRY/StarCraft/bwapi-data/bwapi.ini";
        file = new File(path);
        if (file.exists()) {
            return path;
        }
        
        path = "C:/Program files/StarCraft/bwapi-data/bwapi.ini";
        file = new File(path);
        if (file.exists()) {
            return path;
        }
        
        path = "C:/Program files (x86)/StarCraft/bwapi-data/bwapi.ini";
        file = new File(path);
        if (file.exists()) {
            return path;
        }
        
        return null;
    }

    private static void updateOurRace() {
        for (int i = 0; i < fileContent.length; i++) {
            String line = fileContent[i];
            if (line.startsWith("race = ")) {
                fileContent[i] = "race = " + OUR_RACE;
                
                if (!fileContent[i].equals(line)) {
                    shouldUpdateFileContent = true;
                    System.out.println("Updated our race in bwapi.ini to: " + OUR_RACE);
                }
                return;
            }
        }
    }

    private static void updateEnemyRace() {
        for (int i = 0; i < fileContent.length; i++) {
            String line = fileContent[i];
            if (line.startsWith("enemy_race = ")) {
                fileContent[i] = "enemy_race = " + ENEMY_RACE;
                
                if (!fileContent[i].equals(line)) {
                    shouldUpdateFileContent = true;
                    System.out.println("Updated enemy race in bwapi.ini to: " + ENEMY_RACE);
                }
                return;
            }
        }
    }

    private static void writeToBwapiIni() {
        String finalContent = "";
        
        for (String line : fileContent) {
            finalContent += line + "\n";
        }
        
        AtlantisUtilities.saveToFile(bwapiIniPath, finalContent, true);
    }
    
}
