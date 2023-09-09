package view.menu;

import view.Console;

public class AddToMachine extends Command{
    public AddToMachine(Console console) {
        super(console);
        description = "Внести добавленные игрушки в автомат";
    }

    @Override
    public void execute() {
        console.addToMachine();
    }
}
