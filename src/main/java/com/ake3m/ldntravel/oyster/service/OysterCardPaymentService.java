package com.ake3m.ldntravel.oyster.service;

import com.ake3m.ldntravel.oyster.exception.InvalidCardException;
import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.oyster.repository.OysterCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OysterCardPaymentService implements CardPaymentService<OysterCard> {

    private final OysterCardRepository oysterRepository;

    @Override
    public OysterCard charge(OysterCard card, double amount) {
        return adjustBalance(card, -Math.abs(amount));
    }

    @Override
    public OysterCard refund(OysterCard card, double amount) {
        return adjustBalance(card, Math.abs(amount));
    }

    @Override
    public OysterCard getUpdatedCard(OysterCard card) {
        return oysterRepository
                .findById(card.getId())
                .orElseThrow(InvalidCardException::new);
    }

    private OysterCard adjustBalance(OysterCard card, double amount) {
        return oysterRepository
                .findById(card.getId())
                .map(oysterCard -> {
                    oysterCard.setBalance(oysterCard.getBalance() + amount);
                    return oysterRepository.save(oysterCard);
                })
                .orElseThrow(InvalidCardException::new);
    }
}
