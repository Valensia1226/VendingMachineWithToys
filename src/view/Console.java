package view;

import presenter.Presenter;
import view.menu.MainMenu;

import java.util.Scanner;
//Класс для взамодействия с автоматом персонала, его обслуживающего. Функции добавления игрушек, внесения их в автомат
public class Console implements View {
    private Presenter presenter;
    private Scanner scanner;
    private MainMenu mainMenu;
    private boolean work;

    public Console() {
        this.presenter = new Presenter(this);
        this.scanner = new Scanner(System.in);
        this.mainMenu = new MainMenu(this);
        this.work = true;
    }

    @Override
    public void start() {
        while (work) {
            System.out.println();
            System.out.println(mainMenu.getMenu());
            int choice = takeChoice(mainMenu.getSize());
            if (choice != -1) mainMenu.execute(choice);
            else System.out.printf("Некорректно введена команда. Введите число от 1 до %d\n", mainMenu.getSize());
        }
    }

    private int takeChoice(int size) {
        String line = scanner.nextLine();
        int choice = isNumber(line);
        if ((choice > size) || (choice <= 0)) return -1;
        return choice;
    }

    @Override
    public void print(String str) {
        System.out.println(str);
    }

    private String getName() {
        print("Введите наименование игрушки: ");
        return scanner.nextLine();
    }

    private int getCount() {
        print("Введите количество игрушек с таким наименованием: ");
        String line = scanner.nextLine();
        int amount = isNumber(line);
        if (amount == -1) {
            print("Введено не число!");
            return getCount();
        } else return amount;
    }

    private int getWeight() {
        print("Введите вероятность выпадения игрушки в процентах (если суммарная вероятность для всех игрушек " +
                "будет больше или меньше 100, вероятность выпадения каждого типа игрушек будет перерасчитана!):");
        String line = scanner.nextLine();
        int weight = isNumber(line);
        if (weight == -1) {
            print("Введено не число!");
            return getWeight();
        } else return weight;
    }

    public void addToy() {
        String name = getName();
        int amount = getCount();
        int weight = getWeight();
        presenter.addToy(name, amount, weight);
    }

    private int isNumber(String line) {
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void addToMachine() {
        presenter.addToMachine();
    }

    public void finish() {
        this.work = false;
        print("До свидания!");
        presenter.saveMachine();
    }
}
