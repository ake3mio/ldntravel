package com.ake3m.ldntravel.oyster.service;

import com.ake3m.ldntravel.oyster.exception.InvalidCardException;
import com.ake3m.ldntravel.oyster.model.OysterCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OysterCardPaymentServiceTest {
    @Autowired
    private OysterCardMachineService cardMachineService;

    @Autowired
    private OysterCardPaymentService cardPaymentService;

    @Test
    void chargesTheValidAmount() {
        var amount = 30.00;
        var card = cardMachineService.createCard();
        card = cardMachineService.topUp(card, amount);
        card = cardPaymentService.charge(card, 10.00);
        assertEquals(card.getBalance(), 20.00);
    }

    @Test
    void refundsTheValidAmount() {
        var amount = 30.00;
        var card = cardMachineService.createCard();
        card = cardPaymentService.refund(card, amount);
        assertEquals(card.getBalance(), amount);
    }

    @Test
    void cannotChargeInvalidCard() {
        var card = new OysterCard();

        assertThrows(InvalidCardException.class, () -> {
            cardPaymentService.charge(card, 30.00);
        });
    }

    @Test
    void cannotRefundInvalidCard() {
        var card = new OysterCard();
        assertThrows(InvalidCardException.class, () -> {
            cardPaymentService.refund(card, 30.00);
        });
    }
}