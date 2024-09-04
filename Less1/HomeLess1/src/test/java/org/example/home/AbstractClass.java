package org.example.home;

import org.junit.jupiter.api.BeforeAll;

/**
 * абстрактный тест для игры
 */
public abstract class AbstractClass {

    static Game game;

    @BeforeAll
    static void init() {
        game = new Game();
    }
}
