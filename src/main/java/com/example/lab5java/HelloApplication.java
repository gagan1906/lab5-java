package com.example.lab5java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // Load and parse the JSON file
        Gson gson = new Gson();
        try (InputStream inputStream = getClass().getResourceAsStream("/products.json");
             Reader reader = new InputStreamReader(inputStream)) {

            if (inputStream == null) {
                throw new NullPointerException("JSON file not found");
            }

            Type productListType = new TypeToken<List<Product>>(){}.getType();
            List<Product> products = gson.fromJson(reader, productListType);

            // Print the products to verify
            if (products != null) {
                for (Product product : products) {
                    System.out.println("Product ID: " + product.getId());
                    System.out.println("Product Name: " + product.getName());
                    System.out.println("Product Price: " + product.getPrice());
                    System.out.println();
                }
            } else {
                System.out.println("No products found in the JSON file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
