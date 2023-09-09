package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VendingMachineWithToys implements Serializable {
    private static int id = 1;
    private List<Toy> listForTheDraw;
    private List<Toy> listOfToys;

    public VendingMachineWithToys() {
        this.listForTheDraw = new ArrayList<>();
        this.listOfToys = new ArrayList<>();
    }

    /**
     * Добавление новой игрушки. Если такая игрушка уже есть в автомате, изменится ее количество и вероятность выпадения
     * @param name имя игрушки
     * @param amount количество игрушек
     * @param weight вес игрушки
     */
    public void addNewToy(String name, int amount, int weight) {
        boolean flag = false;
        if (listOfToys.isEmpty()) {
            int id = VendingMachineWithToys.id;
            VendingMachineWithToys.id++;
            listOfToys.add(new Toy(id, name, amount, weight));
        } else {
            for (Toy el : this.listOfToys) {
                if (el.getName().equals(name)) {
                    amount += el.getAmount();
                    el.setAmount(amount);
                    el.setWeight(weight);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                int id = VendingMachineWithToys.id;
                VendingMachineWithToys.id++;
                listOfToys.add(new Toy(id, name, amount, weight));
            }
        }
    }

    /**
     * Добавленные игрушки вносятся в автомат. Метод создан отдельно, чтобы не происходил пересчет вероятностей
     * для каждой игрушки, так как при внесении нескольких игрушек за раз, первой будет присвоено 100%
     */
    public void addToVendingMachine(){
        probabilityCheck(); //проверяем, что сумма вероятностей выпадения игрушек == 100
        this.listForTheDraw = addToListForTheDraw(); //создаем список для розыгрыша
    }

    /**
     * Розыгрыш игрушки. Если игрушка выиграна, она считается сразу выданной (как выпадающая из автомата)
     * и записывается в файл выигранных игрушек
     */
    public void getToy(){
        Toy toy;
        if (listOfToys.isEmpty()) System.out.println("Игрушки закончились!");
        if (listOfToys.size() == 1) {
            toy = listOfToys.get(0);
            if (toy.getAmount() - 1 == 0) listOfToys.remove(toy);
        }
        else {
            Random random = new Random();
            int index = random.nextInt(listForTheDraw.size());
            toy = listForTheDraw.get(index);
            for (Toy el : listOfToys) {
                if (el.getId() == toy.getId()) {
                    if (el.getAmount() - 1 == 0) {
                        listOfToys.remove(el);
                        probabilityCheck();
                        this.listForTheDraw = addToListForTheDraw();
                    } else el.setAmount(el.getAmount() - 1);
                    break;
                }
            }
        }
        System.out.println("Выигранная игрушка: " + toy.toString());
        saveToy(toy);
    }

    /**
     * Запись в файл текущего состояния автомата, чтобы потом открывать его для игры. Исключение вылетает при записи,
     * "Ошибка записи", специально обернула каждый элемент в свой try-catch, чтобы проверить, что именно не срабатывает
     */
    public void saveMachine(){
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("machine.out");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Не найден файл для записи");
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка открытия потока вывода");
        }
        try {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи");
        }
        try {
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка закрытия потока записи");
        }
    }


    /**
     * Сохранение выиграных игрушек в файл
     * @param toy
     */
    private void saveToy(Toy toy) {
        Path path = Path.of("win.txt");
        StringBuilder sb = new StringBuilder();
        try {
            String all = Files.readString(path);
            sb.append(all);
            sb.append("model.Toy: id = ");
            sb.append(toy.getId());
            sb.append(", name = ");
            sb.append(toy.getName());
            sb.append("\n");
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения/записи");
        }
    }

    /**
     * Проверка вероятностей для игрушек в списке, сумма вероятностей должна быть равна 100.
     * Если она больше или меньше 100 производится перерасчет
     */
    private void probabilityCheck() {
        int sumWeight = 0;
        int newSumWeight = 0;
        for (Toy el : this.listOfToys) {
            sumWeight += el.getWeight();
        }
        for (Toy el : listOfToys) {
            double temp = Math.floor(el.getWeight() + (double) (100 - sumWeight) / listOfToys.size());
            int newWeight = (int) temp;
            el.setWeight(newWeight);
            newSumWeight += newWeight;
        }
        if (newSumWeight < 100) amendmentCheck(newSumWeight, true);
        else if (newSumWeight > 100) amendmentCheck(newSumWeight, false);
    }

    /**
     * Проверка оставшихся "свободных" процентов, если они не распределились равномерно между игрушками,
     * они назначаются игрушкам по мере уменьшения вероятности их выпадения
     * @param newSumWeight
     * @param flag
     */
    private void amendmentCheck(int newSumWeight, boolean flag) {
        List<Integer> listofWeight = new ArrayList<>();
        for (Toy el : listOfToys) {
            listofWeight.add(el.getWeight());
        }
        int amendment = 100 - newSumWeight;
        while (amendment > 0) {
            for (Toy el : listOfToys) {
                int max = Collections.max(listofWeight);
                if (el.getWeight() == max) {
                    if (flag) el.setWeight(el.getWeight() + 1);
                    else el.setWeight(el.getWeight() - 1);
                    amendment = amendment - 1;
                    if (amendment == 0) break;
                    listofWeight.remove(listofWeight.indexOf(max));
                }
            }
        }
    }

    /**
     * Игрушки после проверки вероятности вносятся в список для розыгрыша, их количество в списке равнозначно их вероятности выпадения
     * @return
     */
    private List<Toy> addToListForTheDraw() {
        List<Toy> listForTheDraw = new ArrayList<>();
        for (Toy el : listOfToys) {
            for (int i = 0; i < el.getWeight(); i++) {
                listForTheDraw.add(el);
            }
        }
        return listForTheDraw;
    }

    public List<Toy> getListForTheDraw() {
        return listForTheDraw;
    }

    public List<Toy> getListOfToys() {
        return listOfToys;
    }

}
