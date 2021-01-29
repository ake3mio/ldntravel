package com.ake3m.ldntravel.oyster.service;

import com.ake3m.ldntravel.oyster.exception.InvalidCardException;
import com.ake3m.ldntravel.oyster.model.OysterCard;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OysterCardMachineServiceTest {

    @Autowired
    private OysterCardMachineService cardMachineService;

    @Test
    void createCard() {
        var card = cardMachineService.createCard();

        assertNotNull(card);
    }

    @Test
    void canNotTopUpInvalidCard() {
        var amount = 30.00;
        var card = new OysterCard();

        assertThrows(InvalidCardException.class, () -> {
            cardMachineService.topUp(card, amount);
        });
    }
    @Test
    void canNotCheckBalanceOfInvalidCard() {
        var amount = 30.00;
        var card = new OysterCard();

        assertThrows(InvalidCardException.class, () -> {
            cardMachineService.topUp(card, amount);
        });
    }

    @Test
    void topupEqualsBalance() {
        var amount = 30.00;
        var card = cardMachineService.createCard();
        cardMachineService.topUp(card, amount);
        assertEquals(cardMachineService.checkBalance(card), amount);
    }
}