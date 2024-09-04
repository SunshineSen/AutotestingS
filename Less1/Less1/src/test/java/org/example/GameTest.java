package org.example;

import org.example.seminar.*;
import org.example.seminar.type.Ranks;
import org.example.seminar.type.Suits;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    void testPlayerWin() {
        //given
        Game game = new Game();
        //when
        game.getGamer().addCard(new Card(Ranks.TEN, Suits.CLUBS));
        game.getGamer().addCard(new Card(Ranks.ACE, Suits.CLUBS));
        game.getCasino().addCard(new Card(Ranks.EIGHT, Suits.CLUBS));
        game.getCasino().addCard(new Card(Ranks.FOUR, Suits.CLUBS));
        Player player = game.getWinner(game.getGamer(), game.getCasino());
        //then
        Assertions.assertEquals(game.getGamer(), player);
    }

    @Test
    void testCasinoWin() {
        //given
        Game game = new Game();
        //when
        game.getGamer().addCard(new Card(Ranks.FOUR, Suits.HEARTS));
        game.getGamer().addCard(new Card(Ranks.FIVE, Suits.CLUBS));
        game.getCasino().addCard(new Card(Ranks.EIGHT, Suits.DIAMONDS));
        game.getCasino().addCard(new Card(Ranks.ACE, Suits.CLUBS));
        Player casino = game.getWinner(game.getCasino(), game.getGamer());
        //then
        Assertions.assertEquals(game.getCasino(), casino);
    }

    @Test
    void testCasinoWin2() {
        //given
        Gamer gamer = new Gamer(4);
        Casino casino = new Casino(0);
        Game game = new Game(gamer, casino);
        //when
        gamer.addCard(new Card(Ranks.ACE, Suits.CLUBS));
        gamer.addCard(new Card(Ranks.SEVEN, Suits.SPADES));
        casino.addCard(new Card(Ranks.EIGHT, Suits.CLUBS));
        casino.addCard(new Card(Ranks.SEVEN, Suits.DIAMONDS));
        casino.addCard(new Card(Ranks.FIVE, Suits.DIAMONDS));
        //then
        Assertions.assertEquals(casino, game.getWinner(gamer, casino));
    }
}
