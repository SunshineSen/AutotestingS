package org.example.home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayGameTest {

    private Player player;

    @BeforeEach
    void setup() {
        player = new Player("Игрок", true);
    }

    @ParameterizedTest
    @CsvSource({
            "true, 0.33",
            "false, 0.66"
    })
    void testGameRounds(boolean playerRisk, double expectedWinRate) {
        player.setRisk(playerRisk);
        int statisticWin = 0;
        int step;
        int totalSteps = 1000;

        for (step = 0; step < totalSteps; step++) {
            Game game = new Game(player, getDoors());
            if (game.round(0).isPrize()) statisticWin++;
        }

        double actualWinRate = (double) statisticWin / totalSteps;
        assertTrue(Math.abs(actualWinRate - expectedWinRate) < 0.1,
                "Expected win rate: " + expectedWinRate + ", but got: " + actualWinRate);
    }

    private List<Door> getDoors() {
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(true));
        doors.add(new Door(false));
        doors.add(new Door(false));
        Collections.shuffle(doors);
        return doors;
    }
}
