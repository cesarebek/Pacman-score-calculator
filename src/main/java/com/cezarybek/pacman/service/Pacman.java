package com.cezarybek.pacman.service;

import com.cezarybek.pacman.model.Entity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Pacman {
    public int score = 5000;
    public int lives = 3;
    public int eatenVulnerableGhosts = 0;
    public boolean hasExtraLife = false;

    public int startGame() {

        //1. Load the file
        List<String> tempGameBoard = loadBoard();
        //2. Prepare the board
        List<Entity> gameBoard = generateFinalBoard(tempGameBoard);
        //3. Calculate the final score
        calculateScore(gameBoard);
        //4. Print it out!
        return score;
    }

    public List<String> loadBoard(){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("beanTech-seq.txt");
        List<String> gameBoard = new ArrayList<>();

        try {
            byte[] bFile = FileCopyUtils.copyToByteArray(inputStream);
            String file = new String(bFile, StandardCharsets.UTF_8);
            gameBoard = Arrays.stream(file.split(","))
                    .collect(Collectors.toList());
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return gameBoard;
    }

    public List<Entity> generateFinalBoard(List<String> tempGameBoard) {

        List<Entity> gameBoard = new ArrayList<>();

        tempGameBoard.forEach(entity -> {
            switch (entity){
                case "Dot":
                    gameBoard.add(new Entity("Dot", 10));
                    break;
                case "InvincibleGhost":
                    gameBoard.add(new Entity("InvincibleGhost", 10));
                    break;
                case "VulnerableGhost":
                    gameBoard.add(new Entity("VulnerableGhost", 10));
                    break;
                case "Melon":
                    gameBoard.add(new Entity("Melon", 1000));
                    break;
                case "Galaxian":
                    gameBoard.add(new Entity("Galaxian", 2000));
                    break;
                case "Bell":
                    gameBoard.add(new Entity("Bell", 3000));
                    break;
                case "Cherry":
                    gameBoard.add(new Entity("Cherry", 100));
                    break;
                case "Strawberry":
                    gameBoard.add(new Entity("Strawberry", 300));
                    break;
                case "Orange":
                    gameBoard.add(new Entity("Orange", 500));
                    break;
                case "Apple":
                    gameBoard.add(new Entity("Apple", 700));
                    break;
                case "Key":
                    gameBoard.add(new Entity("Key", 5000));
                    break;
            }
        });

        return gameBoard;
    }

    private void calculateScore(List<Entity> gameBoard) {

        gameBoard.forEach(entity -> {
            if (lives == 0) return;

            switch (entity.getName()){
                case "VulnerableGhosts":
                    eatenVulnerableGhosts++;
                    score += entity.getPoints() * eatenVulnerableGhosts;
                    break;
                case "InvincibleGhost":
                    lives --;
                    break;
                case "Dot":
                case "Key":
                case "Galaxian":
                case "Apple":
                case "Orange":
                case "Strawberry":
                case "Cherry":
                case "Bell":
                case "Melon":
                    score += entity.getPoints();
            }

            //Check for only one bonus life
           if (score >= 10000 && !hasExtraLife){
               lives++;
               hasExtraLife = true;
           }
        });
    }
}
