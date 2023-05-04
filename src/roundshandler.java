import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

//@SuppressWarnings({})
public class roundshandler implements EventHandler<ActionEvent> {

    ArrayList<String> mines_in_game = new ArrayList<String>();
    ArrayList<String> efforts = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();

    ArrayList<String> winner = new ArrayList<String>();
//
int counter = 0;

    public void handle(ActionEvent t) {
        String fileName = "C:\\Users\\ΒΑΣΙΛΗΣ\\IdeaProjects\\minesweeper\\medialab\\results.txt";
        ArrayList<String> lines = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            Collections.reverse(lines);


        while (counter <20){
            winner.add(lines.get(counter));
            counter++;
            time.add(lines.get(counter));
            counter++;
            efforts.add(lines.get(counter));
            counter++;
            mines_in_game.add(lines.get(counter));
            counter++;
        }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Results");
        alert.setHeaderText(null);
        alert.setContentText("Mines: " + mines_in_game.toString() + "\nEfforts: " + efforts.toString() + "\nTime: "
                + time.toString() + "\nWinner: " + winner.toString());

        // Show the alert
        alert.showAndWait();

    }
}