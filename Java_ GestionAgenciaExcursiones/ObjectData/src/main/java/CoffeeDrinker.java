public class CoffeeDrinker {
    private boolean compiling = true;
    private Coffee coffee = new Coffee();

    public void startDrinking() {
        while (compiling) {
            coffee.drink();
        }
    }

    public static void main(String[] args) {
        CoffeeDrinker drinker = new CoffeeDrinker();
        drinker.startDrinking();
    }
}

class Coffee {
    public void drink() {
        System.out.println("Drinking coffee...");
    }
}
