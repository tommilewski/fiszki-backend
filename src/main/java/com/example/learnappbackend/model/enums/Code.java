package com.example.learnappbackend.model.enums;

public enum Code {

    SUCCESS("Operacja zakończona sukcesem"),
    PERMIT("Przyznano dostep"),
    A1("Nie udało się zalogować"),
    A2("Użytkownik o wskazanej nazwie nie istnieje"),
    A3("Wskazany token jest pusty lub nie ważny"),
    A4("Użytkownik o podanym mailu już istnieje"),
    A5("Użytkownik o podanej nazwie już istnieje"),
    A6("Fiszka nie istnieje");
    public final String label;
    Code(String label) {
        this.label = label;
    }
}
