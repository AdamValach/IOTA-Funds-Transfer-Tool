package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainView {
    public Stage primaryStage;
    public Pane grid = new Pane();
    public Scene scene;
    public Button button0 = new Button("Connect");
    public Button button1 = new Button("Send");
    public Button button2 = new Button("My address");
    public Button button3 = new Button("My balance");
    public TextField field1 = new TextField();
    public TextField field2 = new TextField();
    public TextField field3 = new TextField();
    public TextField field4 = new TextField();
    public TextField field5 = new TextField();
    public Label label1 = new Label("Not connected");
    public Label label2 = new Label("Error message label");

    public MainView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setup() {
        scene = new Scene(grid, 1000,225);
        field1.setTranslateX(40);
        field2.setTranslateX(40);
        field5.setTranslateX(40);

        field1.setTranslateY(50);
        field2.setTranslateY(90);
        field5.setTranslateY(130);

        field1.setPromptText("Seed");
        field2.setPromptText("Address");
        field3.setPromptText("Tag");
        field4.setPromptText("Message (Optional)");
        field5.setPromptText("Amount");

        field1.setPrefWidth(920);
        field2.setPrefWidth(920);
        field5.setPrefWidth(920);

        button1.setTranslateX(275);
        button1.setTranslateY(170);
        button1.setPrefWidth(100);

        button2.setTranslateX(400);
        button2.setTranslateY(170);
        button2.setPrefWidth(100);

        button3.setTranslateX(525);
        button3.setTranslateY(170);
        button3.setPrefWidth(100);

        button0.setTranslateX(450);
        button0.setTranslateY(170);
        button0.setPrefWidth(100);

        grid.getChildren().addAll(button0, label1);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void errorWindow(String message, String title) {
        Stage window = new Stage();
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        label2.setText(message);
        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e -> window.close());

        layout.getChildren().addAll(label2, closeButton);
        layout.setAlignment(Pos.CENTER);

        window.setScene(scene);
        window.showAndWait();
    }

    public void connectionUpdate(String IP) {
        label1.setText("Connected to: " + IP);
        grid.getChildren().remove(button0);
        grid.getChildren().addAll(field1, field2, field5, button1, button2, button3);
    }

    public void incorrectField(int field) {
        scene.getStylesheets().add("CSS/stylesheet.css");
        if(String.valueOf(field).contains("1")) {
            field1.getStyleClass().remove("error");
            field1.getStyleClass().add("error");
        }
        if(String.valueOf(field).contains("2")) {
            field2.getStyleClass().remove("error");
            field2.getStyleClass().add("error");
        }
        if(String.valueOf(field).contains("3")) {
            field3.getStyleClass().remove("error");
            field3.getStyleClass().add("error");
        }
        if(String.valueOf(field).contains("4")) {
            field4.getStyleClass().remove("error");
            field4.getStyleClass().add("error");
        }
        if(String.valueOf(field).contains("5")) {
            field5.getStyleClass().remove("error");
            field5.getStyleClass().add("error");
        }
    }

    public void resetFields(int field) {
        scene.getStylesheets().add("CSS/stylesheet.css");
        if(String.valueOf(field).contains("1")) field1.getStyleClass().remove("error");
        if(String.valueOf(field).contains("2")) field2.getStyleClass().remove("error");
        if(String.valueOf(field).contains("3")) field3.getStyleClass().remove("error");
        if(String.valueOf(field).contains("4")) field4.getStyleClass().remove("error");
        if(String.valueOf(field).contains("5")) field5.getStyleClass().remove("error");
    }
}