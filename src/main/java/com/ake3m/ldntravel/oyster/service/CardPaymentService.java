package com.ake3m.ldntravel.oyster.service;

import com.ake3m.ldntravel.oyster.model.Card;

public interface CardPaymentService<T extends Card> {
    T charge(T card, double amount);
    T refund(T card, double amount);
}
