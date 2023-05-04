import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameWriter {

    static void writeConfigFile(String scenarioID, int numBombs, int superbomb, int difficulty, int timeLimit) {
        String currentDirectory = System.getProperty("user.dir");
        String fileName = currentDirectory + "/medialab/" + scenarioID + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(String.valueOf(difficulty));
            writer.newLine();
            writer.write(String.valueOf(numBombs));
            writer.newLine();
            writer.write(String.valueOf(timeLimit));
            writer.newLine();
            writer.write(String.valueOf(superbomb));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}