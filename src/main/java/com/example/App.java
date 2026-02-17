package com.example;

import static spark.Spark.*;

public class App {

    public static String getMessage() {
        return "SonarCloud + Docker Working!";
    }

    public static void main(String[] args) {

        port(9090);

        get("/", (req, res) -> getMessage());
    }
}

