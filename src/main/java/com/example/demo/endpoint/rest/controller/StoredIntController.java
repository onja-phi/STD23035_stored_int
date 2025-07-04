package com.example.demo.endpoint.rest.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoredIntController {

  // private static final String FILE_PATH = "/tmp/stored-int.txt";
  // private static final String FILE_PATH = "stored-int.txt";
  private static final String FILE_PATH = System.getProperty("java.io.tmpdir") + "/stored-int.txt";

  @GetMapping("/stored-int")
  public String getStoredInt() {
    int value;
    File file = new File(FILE_PATH);
    Path path = Paths.get(FILE_PATH);

    if (file.exists()) {
      try {
        String content = Files.readString(path).trim();
        value = Integer.parseInt(content);
      } catch (IOException | NumberFormatException e) {
        return "Erreur de lecture: " + e.getMessage();
      }
    } else {
      value = new Random().nextInt(100_000);
      try {
        Files.writeString(path, String.valueOf(value));
      } catch (IOException e) {
        return "Erreur d'écriture: " + e.getMessage();
      }
    }

    return String.valueOf(value);
  }
}
