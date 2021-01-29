package com.ake3m.ldntravel;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.oyster.service.OysterCardMachineService;
import com.ake3m.ldntravel.station.exception.InvalidStationException;
import com.ake3m.ldntravel.station.service.BusJourneyService;
import com.ake3m.ldntravel.station.service.StationService;
import com.ake3m.ldntravel.station.service.TubeJourneyService;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static java.util.Objects.nonNull;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "oyster", name = "cli", havingValue="enabled")
public class CommandLineApplication implements CommandLineRunner {

    private final OysterCardMachineService cardMachineService;
    private final StationService stationService;
    private final BusJourneyService busJourneyService;
    private final TubeJourneyService tubeJourneyService;

    private OysterCard card;
    private TransportMethodType transportMethodType;

    @Override
    public void run(String... args) {

        card = cardMachineService.createCard();

        var command = Map.<CommandType, Runnable>of(
                CommandType.TOPUP_OYSTER, this::topupOyster,
                CommandType.CHECK_OYSTER_CARD_BALANCE, this::checkOysterCardBalance,
                CommandType.LIST_STATIONS, this::listStations,
                CommandType.START_JOURNEY, this::startJourney,
                CommandType.END_JOURNEY, this::endJourney,
                CommandType.HELP, this::printHelp
        );

        var scanner = new Scanner(System.in);

        printHelp();

        while (true) {

            var commandOption = scanner.nextLine();
            var isValidOption = nonNull(commandOption) && commandOption.length() > 0;
            var commandType = isValidOption ?
                    CommandType.valueOf(commandOption.toUpperCase().trim()) : CommandType.HELP;

            if (commandType == CommandType.EXIT) {
                return;
            }

            command.get(commandType).run();
            System.out.print("What would you like to do next\n> ");
        }
    }

    private void topupOyster() {
        var scanner = new Scanner(System.in);
        System.out.print("Enter top-up amount (e.g: 30)\n> ");
        var amount = scanner.nextDouble();
        cardMachineService.topUp(card, amount);
        checkOysterCardBalance();
    }

    private void checkOysterCardBalance() {
        System.out.printf("Your balance is %s\n", cardMachineService.checkBalance(card));
    }

    private void listStations() {
        stationService.getZones().forEach(zone -> {
            System.out.printf("ZONE: %s\n", zone.getLevel());
            System.out.println("========");

            zone.getStations().forEach(station -> {
                System.out.printf("Station Name: %s\n", station.getName());
            });
            System.out.println("");
        });
    }

    private void startJourney() {

        System.out.print("Enter transport method (e.g: bus or tube)\n> ");

        var scanner = new Scanner(System.in);
        var option = scanner.nextLine();
        transportMethodType = TransportMethodType.valueOf(option.toUpperCase().trim());

        listStations();

        System.out.print("Enter the name of the station you're traveling from (stations listed above)\n> ");
        var stationName = scanner.nextLine();
        var station = stationService.findStation(stationName).orElseThrow(InvalidStationException::new);
        try {
            switch (transportMethodType) {
                case TUBE -> tubeJourneyService.startJourney(card, station);
                case BUS -> busJourneyService.startJourney(card, station);
            }
        } catch (Exception e) {
            System.out.printf("Could not start journey. Reason: %s \n", e.getMessage());
        }
    }

    private void endJourney() {

        listStations();

        System.out.print("Enter the name of the station you're leaving from (stations listed above)\n> ");
        var scanner = new Scanner(System.in);
        var stationName = scanner.nextLine();
        var station = stationService.findStation(stationName).orElseThrow(InvalidStationException::new);

        try {
            switch (transportMethodType) {
                case TUBE -> tubeJourneyService.endJourney(card, station);
                case BUS -> busJourneyService.endJourney(card, station);
            }
            System.out.println("Journey ended...");
        } catch (Exception e) {
            System.out.printf("Could not end journey. Reason: %s \n", e.getMessage());
        }

        checkOysterCardBalance();
    }

    private void printHelp() {
        System.out.println("\nAvailable Commands:\n");
        Arrays.stream(CommandType.values()).forEach(value -> {
            System.out.println("""
                    Command: %s
                    Description: %s 
                    """.formatted(value.name(), value.getDescription()));
        });

        System.out.print("> ");
    }

    @Getter
    @RequiredArgsConstructor
    enum CommandType {
        TOPUP_OYSTER("Top-Up your oyster card"),
        CHECK_OYSTER_CARD_BALANCE("Check your oyster card balance"),
        LIST_STATIONS("Lists the available stations"),
        START_JOURNEY("Start a new journey"),
        END_JOURNEY("End current journey"),
        HELP("See available commands"),
        EXIT("Exit application");

        private final String description;
    }
}
