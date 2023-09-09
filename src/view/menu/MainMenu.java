package view.menu;

import view.Console;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private List<Command> commandList;
    private Console console;
    public MainMenu(Console console){
        this.console = console;
        commandList = new ArrayList<>();
        commandList.add(new AddToy(this.console));
        commandList.add(new AddToMachine(this.console));
        commandList.add(new FinishWork(this.console));
    }
    public String getMenu(){
        StringBuilder sb = new StringBuilder("Выберите действие:\n");
        for (int i = 0; i < commandList.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(commandList.get(i).getDescription());
            sb.append("\n");
        }
        return sb.toString();
    }
    public void execute(int choice){
        Command command = commandList.get(choice - 1);
        command.execute();
    }
    public int getSize(){
        return commandList.size();
    }

}
