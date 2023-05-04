import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Optional;
@SuppressWarnings({})
public class createhandler implements EventHandler<ActionEvent> {


    public void handle(ActionEvent t) {
        // Create the dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create Game");

        // Create the form elements
        TextField scenarioIDField = new TextField();
        TextField numBombsField = new TextField();
        TextField timeLimitField = new TextField();
        TextField ypernaki= new TextField();
//        ToggleGroup difficultyGroup = new ToggleGroup();
//        RadioButton easyButton = new RadioButton("Easy");
//        easyButton.setToggleGroup(difficultyGroup);
//        RadioButton hardButton = new RadioButton("Hard");
//        hardButton.setToggleGroup(difficultyGroup);
        TextField difficulty= new TextField();

        // Add the form elements to the dialog
        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Scenario ID:"), scenarioIDField,
                new Label("Number of Mines:"), numBombsField,
                new Label("Time Limit:"), timeLimitField,
                new Label("Hypernaki"),ypernaki,
                new Label("Difficulty:"), difficulty
        );
        dialog.getDialogPane().setContent(content);

        // Add buttons to the dialog
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(createButtonType);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);

        // Show the dialog and wait for the user's response
        Optional<ButtonType> result = dialog.showAndWait();

        // Process the user's response
        if (result.isPresent() && result.get() == createButtonType) {
            // User clicked the Create button, get the form values
            String scenarioID = scenarioIDField.getText();
            int numBombs = Integer.parseInt(numBombsField.getText());
            int timeLimit = Integer.parseInt(timeLimitField.getText());
            int hypernaki = Integer.parseInt(ypernaki.getText());
            int diff = Integer.parseInt(difficulty.getText());
            // ...
            GameWriter.writeConfigFile(scenarioID, numBombs, hypernaki, diff, timeLimit);
        }
    }
}