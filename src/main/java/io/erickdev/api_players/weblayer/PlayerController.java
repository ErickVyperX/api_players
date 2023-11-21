package io.erickdev.api_players.weblayer;

import io.erickdev.api_players.datalayer.Player;
import io.erickdev.api_players.servicelayer.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class PlayerController {
    PlayerService playerService;
    Player player;

    @Autowired
    public PlayerController(PlayerService playerService, Player player) {
        this.playerService = playerService;
        this.player = player;
    }

    //@RequestMapping(method = RequestMethod.GET, value = "/welcome")
    @GetMapping("/welcome")
    public String welcome() {
        return "Players REST API";
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/players/{id}")
    public Player getPlayerById(@PathVariable int id) {
        return playerService.getPlayerById(id);
    }

    @PostMapping("/players/")
    public void insertPlayer(@RequestBody Player player) {
        playerService.insertPlayer(player);
    }

    @PutMapping("/players/{id}")
    public void updatePlayer(@PathVariable int id, @RequestBody Player player) {
        playerService.updatePlayer(id, player);
    }

    @PatchMapping("/players/{id}")
    public void updateOnlyFields(@PathVariable int id, @RequestBody Map<String, Object> partialPlayer) {
        playerService.updateOnlyFields(id, partialPlayer);
    }

    @PatchMapping("/players/{id}/titles")
    public void updateTitles(@PathVariable int id, @RequestBody int titles) {
        playerService.updateTitles(id, titles);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable int id) {
        playerService.deletePlayer(id);
    }
}
