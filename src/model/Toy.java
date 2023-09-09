package model;

public class Toy {
    private int id;
    private String name;
    private int amount;
    private int weight;

    public Toy(int id, String name, int amount, int weight) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("model.Toy: id = ");
        sb.append(id);
        sb.append(", name = ");
        sb.append(name);
        sb.append(", amount = ");
        sb.append(amount);
        sb.append(", weight = ");
        sb.append(weight);
        return sb.toString();
    }
}
