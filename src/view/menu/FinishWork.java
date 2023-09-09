package view.menu;

import view.Console;

public class FinishWork extends Command{
    public FinishWork(Console console) {
        super(console);
        description = "Завершение работы";
    }

    @Override
    public void execute() {
        console.finish();
    }
}
