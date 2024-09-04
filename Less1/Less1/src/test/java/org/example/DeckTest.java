package org.example;


import org.example.seminar.Deck;
import org.example.seminar.DeckService;
import org.example.seminar.exception.DeckEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class DeckTest {
    @Test
    void testDeckException() throws DeckEmptyException {
        //given

        DeckService deckService = new DeckService();
        Deck deck = deckService.getNewDeck();
        //when
        for (int i = 0; i < 52; i++) {
            deck.getCard();
        }
        //thenn
        Assertions.assertThrows(DeckEmptyException.class,  () -> deck.getCard());
    }
}
