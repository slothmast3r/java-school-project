package items;

import characters.Player;

public class Item {
    protected String name;
    protected int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void use(Player player){
        System.out.println("Nothing happened");
    };

    @Override
    public String toString() {
        return name + " (Price: " + price + ")";
    }


}
