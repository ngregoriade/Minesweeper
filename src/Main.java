import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class Main extends Application {

    String filePath = null;
    int remainingTime;
    int marked_tiles=0;
    Timeline timeline;
    Label timerLabel = new Label();
    Label markedLabel = new Label();
    Label mineslabel = new Label();

    int secondsElapsed = 0;
    int  counter=0;
    int moves_counter=0;
    int minescounter=0;
    MenuItem createOption = new MenuItem("Create");
    MenuItem startOption = new MenuItem("Start");
    MenuItem loadOption = new MenuItem("Load");
    MenuItem roundsOption = new MenuItem("Rounds");
    MenuItem solutionOption = new MenuItem("Solution");
    Game game = new Game("src\\game.txt");
    int X_TILES= game.X_TILES;
    int Y_TILES= game.Y_TILES;
    int time= game.time_game;
    int mines_num= game.mines_num;
    int hypermine=game.hypermines; //if you have hypermine in the file, it will be 1, else 0
    boolean hyper_flag=false; //it is true if you have already marked a hypermine
    int x_yper, y_yper; //coordinates of hypermine

    private static final int TILE_SIZE = 40;


    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    private Scene scene;

    //    delete file
    public void deletefile() {
        File file = new File("mines.txt");
        if (file.delete()) {
//            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }


    public Main() throws InvalidValueException, IOException {
    }
    public void writeinfile(int x, int y, int ypernarki)
            throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("mines.txt", true));
        writer.append(x+", "+y+", "+ypernarki+" \n");

        writer.close();
    }
    public void writeinfile2(int x)
            throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("medialab\\results.txt", true));
        writer.append(x +" \n");

        writer.close();
    }
    @SuppressWarnings({})
    private Parent createContent() {
        SplitPane root = new SplitPane();
        Menu timerMenu = new Menu();
        timerMenu.setGraphic(timerLabel);
        Menu markedMenu = new Menu();
        markedMenu.setGraphic(markedLabel);
        Menu minesMenu = new Menu();
        minesMenu.setGraphic(mineslabel);


        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            @SuppressWarnings({})
            public void handle(ActionEvent event) {
                secondsElapsed++;
                remainingTime = time - secondsElapsed;
                markedLabel.setText("Marked:"+marked_tiles);
                if (remainingTime < 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText(null);
                    alert.setContentText("Your time is up! Try again!");
                    alert.showAndWait();

                    secondsElapsed=0;
                    moves_counter=0;
                    counter=0;
                    minescounter=0;
                    scene.setRoot(createContent());
                    return;
                }
                timerLabel.setText("Time:"+remainingTime);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        mineslabel.setText("Mines:"+mines_num);

        GridPane up =new GridPane();

        root.setOrientation(Orientation.VERTICAL);
        root.setPrefSize(45*X_TILES,45*Y_TILES);


        GridPane down = new GridPane();


        final Menu menu1 = new Menu("Application");
        final Menu menu2 = new Menu("Details");

        MenuItem exitOption = new MenuItem("Exit");



        exitOption.setOnAction(new exithandler());


        MenuBar menuBar = new MenuBar();
        menu1.getItems().addAll(createOption, loadOption, startOption, exitOption);
        menu2.getItems().addAll(roundsOption, solutionOption);
        menuBar.getMenus().addAll(menu1, menu2);
        menuBar.getMenus().add(timerMenu);
        menuBar.getMenus().add(markedMenu);
        menuBar.getMenus().add(minesMenu);
        menuBar.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);


        VBox vBox = new VBox(menuBar);
        up.add(vBox, 0,0, 3, 1);



        boolean hyper_flag=false;
        deletefile();



        int[][] flag = new int[X_TILES][Y_TILES];
        int[][] flag_for_file = new int[X_TILES][Y_TILES];


        for (int i = 0; i < X_TILES; i++) {
            for (int j = 0; j < Y_TILES; j++) {
                flag[i][j] = 0;
                flag_for_file[i][j] = 0;
            }
        }
        int count=mines_num;
        while (count>0) {

            int x = ThreadLocalRandom.current().nextInt(0, X_TILES );
            int y = ThreadLocalRandom.current().nextInt(0, Y_TILES);

            if (flag[x][y]==0) {
                if (hypermine==1 && !hyper_flag) {
                    x_yper = x;
                    y_yper = y;
                    hyper_flag = true;
                    flag_for_file[x][y] = 1; //its hypermine
                    flag[x][y] = 1; //it's a bomb
                }else {

                    flag[x][y] = 1; //it's a bomb
                    flag_for_file[x][y] = 0; //it's not a hypermine
                }
                count--;
            }
        }
        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile;
                if (flag[x][y] == 1) {
                    try {
                        writeinfile(x, y, flag_for_file[x][y]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    tile = new Tile(x, y, true, flag_for_file[x][y]==1);

                } else {
                    tile = new Tile(x, y, false, false);

                }
                grid[x][y] = tile;
                down.getChildren().add(tile);
            }
        }

        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = grid[x][y];

                if (tile.hasBomb)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0) {
                    tile.text.setText(String.valueOf(bombs));

                }
            }
        }
//        up.getItems().addAll(down);
        root.getItems().addAll(up, down);

        root.setDividerPosition(0, 0.05);

        return root ;
    }
    @SuppressWarnings({})
    private List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();


        int[] points = new int[] {
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dy = points[i];
            int dx = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < X_TILES
                    && newY >= 0 && newY < Y_TILES) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }

    @SuppressWarnings({})
    private class Tile extends StackPane {
        private int x, y;
        private boolean hasBomb, hasYper;
        private boolean isOpen = false;
        private boolean marked = false;

        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text text = new Text();
        private Text mark = new Text();

        public Tile(int x, int y, boolean hasBomb, boolean hasYper) {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;
            this.hasYper = hasYper;

            border.setStroke(Color.LIGHTGRAY);
            border.setFill(Color.BLUEVIOLET);

            text.setFont(Font.font(18));

//            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);

            if (hasBomb) {
                text.setText("X");
                text.setFill(Color.RED);
                text.setVisible(false);
            }
            if (hasYper) {
                text.setFill(Color.BLUE);
                text.setVisible(false);
            }
///            text.setVisible(false);
//
            getChildren().addAll(border, text);

            setTranslateX(y * TILE_SIZE);
            setTranslateY(x * TILE_SIZE);

            setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    moves_counter++;
                    open();
                } else if(e.getButton()==MouseButton.SECONDARY){
                    mark();
                }
            }
            );


        }

        public void open() {
            if (isOpen)
                return;

            if ((hasBomb && !hasYper)||(hasYper && counter>4)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("You lost! Try again!");

                // Show the alert
                alert.showAndWait();
                try {
                    writeinfile2(mines_num);
                    writeinfile2(moves_counter);
                    writeinfile2(time-remainingTime);
                    writeinfile2(0); //0 means lost
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                moves_counter=0;
                secondsElapsed=0;
                marked_tiles=0;
                counter=0;
                minescounter=0;
                scene.setRoot(createContent());
                return;
            }
            if (hasBomb && hasYper &&moves_counter<5) {
                for (int i=0; i<X_TILES; i++){
                    grid[i][y_yper].isOpen = true;

                    grid[i][y_yper].text.setVisible(true);


                }
                for (int i=0; i<Y_TILES; i++){
                    grid[x_yper][i].text.setVisible(true);
                    grid[x_yper][i].isOpen = true;


                }


            }

            isOpen = true;
            counter++;
            if (counter==X_TILES*Y_TILES-mines_num){
                System.out.println("Congrats you won");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("You won! Play again!");
                alert.showAndWait();

                try {
                    writeinfile2(mines_num);
                    writeinfile2(moves_counter);
                    writeinfile2(time-remainingTime);
                    writeinfile2(1); //1 means win
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                moves_counter=0;
                counter=0;
                minescounter=0;
                marked_tiles =0;
                secondsElapsed=0;

                scene.setRoot(createContent());

            }

            text.setVisible(true);
            border.setFill(null);

            if (text.getText().isEmpty()) {
                getNeighbors(this).forEach(Tile::open);

            }
        }
        public void mark(){

            if (marked){
                marked_tiles--;
                marked=false;
//                text.setText(hasBomb ? "X" : "");
                isOpen=false;


                text.setVisible(false);

                border.setFill(Color.BLUEVIOLET);
                minescounter--;
            }else {

                marked=true;
                isOpen=true;
                marked_tiles++;
//                text.setText("o");
//                text.setFill(Color.RED);
//                grid[x][y].text.setVisible(true);
                border.setFill(Color.GREEN);
                minescounter++;

            }
        }
    }

    /**
     *
     * @param stage
     *
     */
    @Override
    public void start(Stage stage) {
        scene = new Scene(createContent());

        stage.setTitle("Medialab Minesweeper");
        stage.setScene(scene);
        stage.show();

        loadOption.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open .txt File");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

                // Set the starting folder to the multimedia directory
                String currentDirectory = System.getProperty("user.dir");
                fileChooser.setInitialDirectory(new File(currentDirectory + "/medialab"));
                // Show the file chooser and wait for the user to select a file
                File file = fileChooser.showOpenDialog(stage);
                String filePath = null;
                if (file == null) {
                    System.out.println("File not found");
                    System.exit(0);
                } else {
                    filePath = file.getPath();
                    try {
                        Game game = new Game(filePath);
                        X_TILES=game.X_TILES;
                        Y_TILES = game.Y_TILES;
                        time = game.time_game;
                        secondsElapsed=0;
                        marked_tiles=0;
                        mines_num = game.mines_num;
                        hypermine = game.hypermines;
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidValueException e) {
                        throw new RuntimeException(e);
                    }
                }
//


            }

        });
        roundsOption.setOnAction(new roundshandler());
        solutionOption.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                for(int i = 0; i < X_TILES; i++)
                for (int j = 0; j < Y_TILES; j++) {
                    if (grid[i][j].hasBomb){
                        grid[i][j].text.setVisible(true);
                    }


            }

            }
            });


        createOption.setOnAction(new createhandler());
        startOption.setOnAction(new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent t) {
                                        counter=0;
                                        minescounter=0;
                                        moves_counter=0;
                                        secondsElapsed=0;
                                        marked_tiles=0;
                                        scene = new Scene(createContent());
                                        stage.setTitle("Medialab Minesweeper");
                                        stage.setScene(scene);
                                        stage.show();

                                    }
                                });

    }


    @SuppressWarnings({})
    public static void main(String[] args) {
        launch(args);
    }
}