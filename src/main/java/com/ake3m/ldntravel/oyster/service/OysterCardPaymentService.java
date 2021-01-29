package com.ake3m.ldntravel.oyster.service;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import org.springframework.stereotype.Service;

@Service
public class OysterCardPaymentService implements CardPaymentService<OysterCard> {
    @Override
    public OysterCard charge(OysterCard card, double amount) {
        return null;
    }

    @Override
    public OysterCard refund(OysterCard card, double amount) {
        return null;
    }
}
