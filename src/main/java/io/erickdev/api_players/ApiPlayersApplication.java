package io.erickdev.api_players;

import io.erickdev.api_players.servicelayer.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiPlayersApplication implements CommandLineRunner {
    PlayerService playerService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ApiPlayersApplication(PlayerService playerService) {
        this.playerService = playerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiPlayersApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(">>Listing: {}", playerService.getAllPlayers());
    }
}
