package com.greenfox.memorycardgame.gui;

import com.greenfox.memorycardgame.memory.Guess;
import com.greenfox.memorycardgame.memory.ImageFileProvider;
import com.greenfox.memorycardgame.memory.MemoryGame;
import com.greenfox.memorycardgame.memory.MemoryGameBuilder;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by ${rudolfps} on 2017.01.26.
 */
public final class MemoryGameController implements Initializable{
    final static Logger logger = Logger.getLogger(MemoryGameController.class);
    private static final int CARDS_PER_ROW = 4;
    private final Image backSide = new Image("/resources/images/aku.png");

    private final EventHandler imageViewClickEventHandler = clickEventHandler();

    ImageFileProvider fileProvider = new ImageFileProvider(".");
    MemoryGame memoryGame;
    Guess guess;
    Label currentPlayer;
    Label currentPlayerl;

    @FXML
    Slider playerNumberSlider;

    @FXML
    Slider gameLevelSlider;

    @FXML
    GridPane memoryCardGrid;

    @FXML
    Button restartGameButton;

    @FXML
    Button chooseRootDirButton;

    @FXML
    GridPane playerCountersGrid;
    @FXML
    private Button btn1;

    @FXML
    private Button btn2;


    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == btn1) {
            //get reference to the button's stage
            stage = (Stage) btn1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("memory.fxml"));
        } else {
            stage = (Stage) btn2.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("frontpage.fxml"));
        }
        Scene scene = new Scene(root, 500, 600);

        stage.setScene(scene);
        stage.show();

    }


    @FXML
    Label score1;

    @FXML
    Label score2;

    @FXML
    Label score3;

    @FXML
    Label score4;


    @FXML
    Label score1l;

    @FXML
    Label score2l;

    @FXML
    Label score3l;

    @FXML
    Label score4l;

    @Override
    /**
     * Initializes the MemoryGame controller and attaches all event handlers,
     * sets appropriate labels to the game level slider.
     */
    public void initialize(URL location, ResourceBundle resources) {
        createNewGame();

        restartGameButton.setOnMouseClicked(event -> {
            createNewGame();
        });

        chooseRootDirButton.setOnMouseClicked(event -> {
            chooseRootDir();
        });

        gameLevelSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                if (value == 4) return "easy";
                else if (value == 6) return "normal";
                else return "hard";
            }
            @Override
            public Double fromString(String string) {
                return null;
            }
        });
    }




    /**
     *
     * executed when the user clicks "Choose a picture"
     */
    private void chooseRootDir() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        final File selectedRootDirectory = directoryChooser.showDialog(null);
        if (selectedRootDirectory != null) {
            logger.info("selected image root directory is " + selectedRootDirectory.getAbsolutePath());
            this.fileProvider.setImageRoot(selectedRootDirectory);
            createNewGame();
        }
        else {
            logger.debug("no valid image root directory selected");
        }
    }

    /**
     *
     * creates a new game by utilizing MemoryGameBuilder
     *
     */
    private void createNewGame() {

        /* detach mouse-click event handler */
        for (Node childNode : memoryCardGrid.getChildren()) {
            childNode.removeEventHandler(MouseEvent.MOUSE_CLICKED, imageViewClickEventHandler);
        }

        /* and remove all image views */
        memoryCardGrid.getChildren().clear();

        /* and remove all player counters */
//        playerCountersGrid.getChildren().clear();
        resetCounters();


//        memoryGame = new MemoryGameBuilder(fileProvider).maxNumberOfPairs((int)gameLevelSlider.getValue()).buildMemoryGame();
        memoryGame = new MemoryGameBuilder(fileProvider).maxNumberOfPairs((int)gameLevelSlider.getValue()).numberOfPlayers((int)playerNumberSlider.getValue()).buildMemoryGame();

        /* since the number of images depends on the selected game level we remove all imageViews first */
        /* there is room for optimization :-) */

        /* we process the rest only if there are tiles to draw */
        if (memoryGame.availableNumberOfCards() > 0 ){
            createImageViews(memoryGame.availableNumberOfCards());
            logger.debug("created new memory game with number of cards: " + memoryGame.availableNumberOfCards());
//            createPlayerCounters((int)playerNumberSlider.getValue());
            logger.debug("created player counters " + playerNumberSlider.getValue());
            guess = new Guess();
            currentPlayer = score1;
            currentPlayerl = score1l;
            currentPlayer.setFont(Font.font(30.0));
            currentPlayerl.setFont(Font.font(30.0));
        }
    }

    private void resetCounters() {
        score1.setText(Integer.toString(0));
        score2.setText(Integer.toString(0));
        score3.setText(Integer.toString(0));
        score4.setText(Integer.toString(0));
    }


    /**
     * creates a grid of imageViews with max 4 IV's in a row
     * @param size the number of Images
     */
    private void createImageViews(int size) {
        int rowIndex = 0;
        int colIndex = 0;

        for (int cardIndex = 0; cardIndex < size; cardIndex++) {
            ImageView imageView = new ImageView(backSide);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setEffect(new DropShadow(5, Color.BLACK));

            /* attaching a handler is essential */
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, imageViewClickEventHandler);

            memoryCardGrid.add(imageView, rowIndex, colIndex);
            rowIndex++;
            if (rowIndex % CARDS_PER_ROW == 0) {
                colIndex++;
                rowIndex = 0;
            }
        }
    }
//    private void createPlayerCounters(int numberOfPlayers){
//        for (int i = 1; i < numberOfPlayers+1; i++) {
//            playerCountersGrid.add(new Label("P"+i),i,1);
//        }
//    }

    /**
     * the provider for the imageView click handler
     *
     * @return
     */
    private EventHandler clickEventHandler() {


        /**
         * handler for all click events
         */
        return new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!(event.getSource() instanceof ImageView)) return;

                ImageView clickedImageView = (ImageView)event.getSource();

                int col = GridPane.getColumnIndex(clickedImageView);
                int row = GridPane.getRowIndex(clickedImageView);

                int selectedCardIndex = row * CARDS_PER_ROW + col;
                logger.debug("selected memory card index: " + selectedCardIndex);

                /* with large images this is a performance bottleneck! */
                /* v2 should use an image cache */
                if (guess.addGuess(selectedCardIndex)) {
                    clickedImageView.setImage(new Image(memoryGame.getCard(selectedCardIndex).toURI().toString()));
                }

                if (guess.validGuesses()) {
                    if (memoryGame.isMatch(guess)) {
                        logger.info("found a matching pair!");
                        memoryCardGrid.getChildren().get(guess.getFirstGuessIndex()).removeEventHandler(MouseEvent.MOUSE_CLICKED, imageViewClickEventHandler);
                        memoryCardGrid.getChildren().get(guess.getSecondGuessIndex()).removeEventHandler(MouseEvent.MOUSE_CLICKED, imageViewClickEventHandler);
                        guess = new Guess();
                        increaseCurrentPlayerCounter(currentPlayer);
                    }
                    if (guess.isScrewed()) {
                        logger.info("no matching pair");
                        ((ImageView)memoryCardGrid.getChildren().get(guess.getFirstGuessIndex())).setImage(backSide);
                        ((ImageView)memoryCardGrid.getChildren().get(guess.getSecondGuessIndex())).setImage(backSide);
                        guess = new Guess();
                        setNextPlayer();
                    }
                }
            }
        };
    }

    private void setNextPlayer() {
        int i = (int)playerNumberSlider.getValue();
        final double MAX_FONT_SIZE = 35.0; // define max font size you need
        final double REG_FONT_SIZE = 15.0; // define max font size you need
        String name;
        currentPlayer.setFont(new Font(REG_FONT_SIZE));
        currentPlayerl.setFont(new Font(REG_FONT_SIZE));

        if ( i == 1){

        }else if (i==2){
            if (currentPlayer == score1){
                currentPlayer = score2;
                currentPlayerl = score2l;
            }else if (currentPlayer == score2) {
                currentPlayer = score1;
                currentPlayerl = score1l;
            }
        }else if (i == 3){
            if (currentPlayer == score1){
                currentPlayer = score2;
                currentPlayerl = score2l;
            }else if (currentPlayer == score2){
                currentPlayer = score3;
                currentPlayerl = score3l;
            }else if (currentPlayer == score3) {
                currentPlayer = score1;
                currentPlayerl = score1l;
            }
        }else {

            if (currentPlayer == score1) {
                currentPlayer = score2;
                currentPlayerl = score2l;
            } else if (currentPlayer == score2) {
                currentPlayer = score3;
                currentPlayerl = score3l;
            } else if (currentPlayer == score3) {
                currentPlayer = score4;
                currentPlayerl = score4l;
            } else if (currentPlayer == score4) {
                currentPlayer = score1;
                currentPlayerl = score1l;
            }
        }
        currentPlayer.setFont(new Font(MAX_FONT_SIZE));
        currentPlayerl.setFont(new Font(MAX_FONT_SIZE));
    }


    private void increaseCurrentPlayerCounter(Label currentPlayer) {
        int i = Integer.parseInt(currentPlayer.getText());
        i++;
        currentPlayer.setText(Integer.toString(i));
    }
}
