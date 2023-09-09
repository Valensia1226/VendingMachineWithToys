package presenter;

import model.VendingMachineWithToys;
import view.View;

public class Presenter {
    private View view;
    private VendingMachineWithToys machine;
    public Presenter(View view){
        this.view = view;
        machine = new VendingMachineWithToys();
    }

    public void addToy(String name, int amount, int weight) {
        machine.addNewToy(name, amount, weight);
    }

    public void addToMachine() {
        machine.addToVendingMachine();
    }

    public void saveMachine() {
        machine.saveMachine();
    }
}
