package view.menu;

import view.Console;

public class AddToy extends Command{
    public AddToy(Console console) {
        super(console);
        description = "Добавить игрушку";
    }

    @Override
    public void execute() {
        console.addToy();
    }
}
