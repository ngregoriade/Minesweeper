import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class exithandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent t) {
        System.exit(0);
    }
}
