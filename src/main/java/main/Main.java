package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Classes.Rates;


public class Main extends Application
{

   // private ObservableList<Rates> currencyList = FXCollections.observableArrayList();

    //public ObservableList<Rates> getCurrencyList()
//    {
//        return currencyList;
//    }

    public static void main(String[] args)
    {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/root.fxml"));
        primaryStage.setTitle("Narodowy Bank Polski - CENY WALUT");
        primaryStage.setScene(new Scene(root,800,610));
        primaryStage.show();
    }
}
