# ldntravel

This is a minimal application of the London’s Oyster card system.

It is a cli app written with maven, java 15, junit 5, spring boot, hibernate and jpa.

## Requirements

- java 15
- maven

## Installing the app

In the terminal run:

```sh
./mvnw clean install
```

## Running the app

In the terminal run:

```sh
./mvnw spring-boot:run
```

## Instructions

Once the cli is running in the terminal, you can run the following commands:

| Command                   | Description                    |
| ------------------------- | ------------------------------ |  
| TOPUP_OYSTER              | Top-Up your oyster card        |  
| CHECK_OYSTER_CARD_BALANCE | Check your oyster card balance |  
| LIST_STATIONS             | Lists the available stations   |  
| START_JOURNEY             | Start a new journey            |  
| END_JOURNEY               | End current journey            |  
| HELP                      | See available commands         |  
| EXIT                      | Exit application               |  

### Stations and zones:

**Station Zone(s)**
- Holborn 1
- Earl’s Court 1, 2
- Wimbledon 3
- Hammersmith 2

### Fares:

**Journey Fare**

- Anywhere in Zone 1 £2.50
- Any one zone outside zone 1 £2.00
- Any two zones including zone 1 £3.00
- Any two zones excluding zone 1 £2.25
- Any three zones £3.20
- Any bus journey £1.80

The maximum fare is £3.20.