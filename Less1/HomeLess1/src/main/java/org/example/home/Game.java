package org.example.home;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Player player;
    private List<Door> doors;

    public Game(Player player, List<Door> doors) {
        if (player == null) {
            throw new NullPointerException("Player cannot be null");
        }
        if (doors == null || doors.isEmpty()) {
            throw new IllegalArgumentException("Doors list cannot be empty");
        }
        this.player = player;
        this.doors = new ArrayList<>(doors);  // Создаём копию списка, чтобы избежать возможных изменений извне
    }

    public Game() {
        // Пустой конструктор
    }

    public Door round(int door) {
        if (doors == null || doors.isEmpty()) {
            throw new IllegalArgumentException("Doors list cannot be empty");
        }
        if (door < 0 || door >= doors.size()) {
            throw new IndexOutOfBoundsException("Invalid door index");
        }
        if (!player.getRisk()) {
            return doors.get(door);
        } else {
            doors.remove(door);  // Удаление элемента по индексу
            return doors.get(0).isPrize() ? doors.get(0) : doors.get(1);
        }
    }

}