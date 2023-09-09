import model.VendingMachineWithToys;
import view.Console;

public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        //console.start();
        VendingMachineWithToys machine = new VendingMachineWithToys();
        machine.addNewToy("robot", 2, 30);
        machine.addNewToy("doll", 3, 10);
        machine.addNewToy("cat", 5, 70);
        machine.addNewToy("bear", 2, 35);
        machine.addToVendingMachine();
        machine.getToy();
        machine.getToy();
        machine.getToy();
    }
}

