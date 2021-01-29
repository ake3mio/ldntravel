package com.ake3m.ldntravel.oyster.service;

import com.ake3m.ldntravel.oyster.exception.InvalidCardException;
import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.oyster.repository.OysterCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service(value = "oysterCardMachineService")
public class OysterCardMachineService implements CardMachineService<OysterCard> {

    private final OysterCardRepository oysterRepository;

    @Override
    public OysterCard createCard() {
        var card = new OysterCard();
        return oysterRepository.save(card);
    }

    @Override
    public double checkBalance(OysterCard card) {
        return oysterRepository
                .findById(card.getId())
                .map(OysterCard::getBalance)
                .orElseThrow(InvalidCardException::new);
    }

    @Override
    public OysterCard topUp(OysterCard card, double amount) {
        return oysterRepository
                .findById(card.getId())
                .map(oysterCard -> {
                    oysterCard.setBalance(oysterCard.getBalance() + Math.abs(amount));
                    return oysterRepository.save(oysterCard);
                })
                .orElseThrow(InvalidCardException::new);

    }
}
