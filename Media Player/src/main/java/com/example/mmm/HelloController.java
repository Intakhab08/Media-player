package com.example.mmm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    MediaPlayer player;
    @FXML
    private MediaView mediaView;
    @FXML
    private Button playbtn;
    @FXML
    private Button prebtn;
    @FXML
    private Button nextbtn;
    @FXML
    private Slider timeSlider;
//    @FXML
//    private Label welcomeText;

    @FXML
    void openSongMenu(ActionEvent event) {
        try {
            System.out.println("Open Song Clicked");
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);

            Media m = new Media(file.toURI().toURL().toString());

            if(player!=null){
                player.dispose();
            }

            player = new MediaPlayer(m);

            mediaView.setMediaPlayer(player);

            //time slider
            player.setOnReady(()->{
                //when player gets ready..
                timeSlider.setMin(0);
                timeSlider.setMax(player.getMedia().getDuration().toMinutes());
                timeSlider.setValue(0);
                try {
                    playbtn.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Faiz Khan\\IdeaProjects\\MMM\\src\\play.png"))));
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            });
            //Listener on player..
            player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    //this code runs when your video runs as time
                    Duration d=player.getCurrentTime();
                    timeSlider.setValue(d.toMinutes());
                }
            });
            // Time Slider
            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    if(timeSlider.isPressed()){
                        double val=timeSlider.getValue();
                        player.seek(new Duration(val*60*1000));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void play(ActionEvent event) {
       try{
           MediaPlayer.Status status= player.getStatus();
           if(status== MediaPlayer.Status.PLAYING){
               //agr song chal rha hai to pause karna hai
               player.pause();
               // playbtn.setText("Play");
               playbtn.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Faiz Khan\\IdeaProjects\\MMM\\src\\play.png"))));
           }else{
               player.play();
               // playbtn.setText("Pause");
               playbtn.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Faiz Khan\\IdeaProjects\\MMM\\src\\pause.png"))));
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

//        @FXML
//        protected void onHelloButtonClick () {
//            welcomeText.setText("Welcome to JavaFX Application!");
//        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {


            playbtn.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Faiz Khan\\IdeaProjects\\MMM\\src\\play.png"))));
            prebtn.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Faiz Khan\\IdeaProjects\\MMM\\src\\previous.png"))));
            nextbtn.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Faiz Khan\\IdeaProjects\\MMM\\src\\next.png"))));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void prebuttonClick(ActionEvent event) {
        double d = player.getCurrentTime().toSeconds();
        d= d-10;
        player.seek(new Duration(d*1000));
    }
    @FXML
    void nextbuttonClick(ActionEvent event) {
        double d = player.getCurrentTime().toSeconds();
        d= d+10;
        player.seek(new Duration(d*1000));
    }
}