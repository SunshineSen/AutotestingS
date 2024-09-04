package org.example.home;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



    public class GameTest {
            private Player player;
            private List<Door> doors;

            @BeforeEach
            void setup() {
                player = new Player("Игрок", true);
                doors = getDefaultDoors();
            }

            @Test
            void testNullPlayer() {
                Exception exception = assertThrows(NullPointerException.class, () -> {
                    new Game(null, doors);
                });
                assertEquals("Player cannot be null", exception.getMessage());
            }

            @Test
            void testEmptyDoors() {
                Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                    new Game(player, new ArrayList<>());
                });
                assertEquals("Doors list cannot be empty", exception.getMessage());
            }

            @Test
            void testInvalidDoorIndexNegative() {
                Game game = new Game(player, doors);
                Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                    game.round(-1);
                });
                assertEquals("Invalid door index", exception.getMessage());
            }

            @Test
            void testInvalidDoorIndexExceed() {
                Game game = new Game(player, doors);
                Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                    game.round(doors.size());
                });
                assertEquals("Invalid door index", exception.getMessage());
            }

            @Test
            void testDefaultConstructor() {
                Game game = new Game();
                Exception exception = assertThrows(NullPointerException.class, () -> {
                    game.round(0);
                });
                assertEquals("Player cannot be null", exception.getMessage());
            }

            private List<Door> getDefaultDoors() {
                List<Door> doors = new ArrayList<>();
                doors.add(new Door(true));  // Одна дверь с призом
                doors.add(new Door(false)); // Остальные пустые двери
                doors.add(new Door(false));
                Collections.shuffle(doors); // Перемешать
                return doors;
            }
        }

        // Дополнительные классы



/** static List<Door> doors;

        @BeforeEach
        void initDoors () {
            doors = new ArrayList<>();
            doors.add(new Door(true));
            doors.add(new Door(false));
            doors.add(new Door(false));
        }

        @Test
        void checkNotRiskWin () {
            //given
            Player player = new Player("Игрок", false);
            Game game = new Game(player, doors);
            //when
            Door door = game.round(0);
            //then
            Assertions.assertTrue(door.isPrize());
        }

        @Test
        void checkNotRiskLose() {
            //given
            Player player = new Player("Игрок", false);
            Game game = new Game(player, doors);
            //when
            Door door = game.round(1);
            //then
            Assertions.assertFalse(door.isPrize());
        }

        @Test
        void checkRiskWin () {
            //given
            Player player = new Player("Игрок", true);
            Game game = new Game(player, doors);
            //when
            Door door = game.round(1);
            //then
            Assertions.assertTrue(door.isPrize());
        }

        @Test
        void checkRiskLose () {
            //given
            Player player = new Player("Игрок", true);
            Game game = new Game(player, doors);
            //when
            Door door = game.round(0);
            //then
            Assertions.assertFalse(door.isPrize());
        }
    }
*/

