package io.erickdev.api_players.servicelayer;

import io.erickdev.api_players.datalayer.Player;
import io.erickdev.api_players.datalayer.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerService {
    PlayerRepository playerRepository;
    Player player;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, Player player) {
        this.playerRepository = playerRepository;
        this.player = player;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(int id) {
        return playerRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("Player with %d not found!", id)));
    }

    public void insertPlayer(Player player) {
        playerRepository.save(player);
    }

    public void updatePlayer(int id, Player player) {
        if (playerRepository.findById(id).isPresent()) {
            Player playerFound = playerRepository.findById(id).get();
            playerFound.setName(player.getName());
            playerFound.setNationality(player.getNationality());
            playerFound.setBirth_date(player.getBirth_date());
            playerFound.setTitles(player.getTitles());
            playerRepository.save(playerFound);
        }
    }

    public void updateOnlyFields(int id, Map<String, Object> partialPlayer) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isPresent()) {
            partialPlayer.forEach( (key, value) -> {
                Field field = ReflectionUtils.findField(Player.class, key);
                assert field != null;
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, player.get(), value);
            });
            playerRepository.save(player.get());
        }
    }

    @Transactional
    public void updateTitles(int id, int titles) {
        playerRepository.updateTitles(id, titles);
    }

    public void deletePlayer(int id) {
        playerRepository.deleteById(id);
    }
}
