package com.ake3m.ldntravel.oyster.service;

import com.ake3m.ldntravel.oyster.model.Card;

public interface CardMachineService<T extends Card> {
    T createCard();
    double checkBalance(T card);
    void topUp(T card, double amount);
}
