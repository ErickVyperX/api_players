package io.erickdev.api_players.servicelayer;

import io.erickdev.api_players.Exceptions.PlayerSuccessException;
import io.erickdev.api_players.datalayer.Player;
import io.erickdev.api_players.Exceptions.PlayerNotFoundException;
import io.erickdev.api_players.datalayer.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
                orElseThrow(() -> new PlayerNotFoundException(String.format("Player with ID: %d not found!", id)));
    }

    public void insertPlayer(Player player) {
        if (Objects.equals(playerRepository.save(player).getName(), player.getName())) {
            throw new PlayerSuccessException("Player added");
        }
    }

    public Player updatePlayerUnknownFields(int id, Map<String, Object> partialPlayer) {
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
        return playerRepository.findById(id).
                orElseThrow(() -> new PlayerNotFoundException(String.format("Player with ID: %d not found!", id)));
    }

    @Transactional
    public void updateTitles(int id, int titles) {
        if (playerRepository.findById(id).isPresent()) {
            playerRepository.updateTitles(id, titles);
        } else {
            throw new PlayerNotFoundException(String.format("Player with ID: %d not found!", id));
        }
    }

    public void deletePlayer(int id) {
        if (playerRepository.findById(id).isPresent()) {
            playerRepository.deleteById(id);
        } else {
            throw new PlayerNotFoundException(String.format("Player with ID: %d not found!", id));
        }
    }
}
