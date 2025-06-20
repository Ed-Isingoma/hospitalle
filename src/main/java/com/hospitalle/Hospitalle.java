package com.hospitalle;

import com.hospitalle.controllers.HospitalleContr;
import com.hospitalle.views.ConsoleView;

public class Hospitalle {
    public static void main(String[] args) {
        int action, table;
        do {
            ConsoleView.showMainMenu();
            action = ConsoleView.getIntInput();
            if (action < 1 || action > 5) continue;

            if (action == 5) break;

            ConsoleView.showTables();
            table = ConsoleView.getIntInput();
            if (table < 1 || table > 7) continue;

            HospitalleContr.handleUser(action, table);
        } while (true);
    }
}