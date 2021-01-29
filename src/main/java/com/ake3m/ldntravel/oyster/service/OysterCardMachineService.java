package com.ake3m.ldntravel.oyster.service;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import org.springframework.stereotype.Service;

@Service(value = "oysterCardMachineService")
public class OysterCardMachineService implements CardMachineService<OysterCard> {

    @Override
    public OysterCard createCard() {
        return null;
    }

    @Override
    public double checkBalance(OysterCard card) {
        return 0;
    }

    @Override
    public void topUp(OysterCard card, double amount) {

    }
}
