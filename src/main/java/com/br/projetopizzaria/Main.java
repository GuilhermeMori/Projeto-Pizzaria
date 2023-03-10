package com.br.projetopizzaria;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene mainScene;
	
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainSaboresIngredientes.fxml"));
			Parent parent = loader.load();
			
			
			mainScene = new Scene(parent);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Pizzaria do Mori");
			primaryStage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
