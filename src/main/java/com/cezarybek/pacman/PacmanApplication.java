package com.cezarybek.pacman;

import com.cezarybek.pacman.service.Pacman;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PacmanApplication {

    public static void main(String[] args) {
        SpringApplication.run(PacmanApplication.class, args);
        Pacman pacman = new Pacman();
        int result = pacman.startGame();
        System.out.printf("Pacman scored %s - Try to modify your template implementing fruits or ghosts!", result);
    }


}
