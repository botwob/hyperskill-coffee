package machine;
import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Machine coffeemachine = new Machine(400, 540, 120,9, 550);

        int water, milk, beans, cups;

        while (coffeemachine.state != Machine.MachineState.EXIT) {
            System.out.print("Write action (buy, fill, take, remaining, exit): ");
            coffeemachine.updateCoffeeState(scanner.nextLine());
            System.out.println();

            if (coffeemachine.state == Machine.MachineState.BUY) {
                System.out.print("What do you want to buy? 1 - espresso, 2 - latte, " +
                        "3 - cappuccino, back - to main menu: ");
                coffeemachine.updateBuyState(scanner.nextLine());

            } else if (coffeemachine.state == Machine.MachineState.REMAINING) {
                coffeemachine.remaining();

            } else if (coffeemachine.state == Machine.MachineState.FILL) {
                System.out.print("Write how many ml of water do you want to add: ");
                water = Integer.parseInt(scanner.nextLine());
                System.out.print("Write how many ml of milk do you want to add: ");
                milk = Integer.parseInt(scanner.nextLine());
                System.out.print("Write how many grams of coffee beans do you want to add: ");
                beans = Integer.parseInt(scanner.nextLine());
                System.out.print("Write how many of disposable cups of coffee do you want to add: ");
                cups = Integer.parseInt(scanner.nextLine());
                coffeemachine.fill(water, milk, beans, cups);
                System.out.println();

            } else if (coffeemachine.state == Machine.MachineState.TAKE) {
                coffeemachine.take();
            }
        }
    }
}

class Machine {
    int water;
    int milk;
    int beans;
    int cups;
    int cash;
    MachineState state;
    BuyState bState;

    enum MachineState {
        BUY, FILL, TAKE, REMAINING, EXIT, DEFAULT
    }

    enum BuyState {
        BACK, ESPRESSO, LATTE, CAPPUCCINO
    }

    public void updateBuyState(String action) {
        switch (action) {
            case "1":
                this.bState = BuyState.ESPRESSO;
                buy();
                break;

            case "2":
                this.bState = BuyState.LATTE;
                buy();
                break;

            case "3":
                this.bState = BuyState.CAPPUCCINO;
                buy();
                break;

            case "back":
                this.bState = BuyState.BACK;
                break;

            default:
                System.out.println("enter a correct option");
                this.bState = BuyState.BACK;
                break;
        }
    }

    public void updateCoffeeState(String state) {
        switch (state) {
            case "remaining":
                this.state = MachineState.REMAINING;
                break;

            case "buy":
                this.state = MachineState.BUY;
                break;

            case "fill":
                this.state = MachineState.FILL;
                break;

            case "take":
                this.state = MachineState.TAKE;
                break;

            case "exit":
                this.state = MachineState.EXIT;
                break;

            default:
                this.state = MachineState.DEFAULT;
        }
    }

    public Machine(int water, int milk, int beans, int cups, int cash) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.cash = cash;
        this.state = MachineState.DEFAULT;
        this.bState = BuyState.BACK;
    }

    public void remaining() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println("$" + cash + " of money\n");
    }

    public void take() {
        System.out.println("I gave you $" + cash);
        System.out.println();
        this.cash = 0;
    }

    public boolean check (int water, int milk, int beans, int cups) {
        if (this.water < water) {
            System.out.println("Sorry, not enough water!\n");
            return false;
        } else if (this.milk < milk) {
            System.out.println("Sorry, not enough milk!\n");
            return false;
        } else if (this.beans < beans) {
            System.out.println("Sorry, not enough coffee beans!\n");
            return false;
        } else if (this.cups < cups) {
            System.out.println("Sorry, not enough disposable cups\n");
            return false;
        }
        return true;
    }

    public void fill(int water, int milk, int beans, int cups) {
        this.water += water;
        this.milk += milk;
        this.beans += beans;
        this.cups += cups;
    }

    public void buy() {
        switch (bState) {
            case ESPRESSO:
                if (check(250, 0, 16, 1)) {
                    this.water -= 250;
                    this.beans -= 16;
                    this.cups -=1;
                    this.cash +=4;
                    System.out.println("I have enough resources, making you a coffee!\n");
                }
                break;

            case LATTE:
                if (check(350,75,20,1)) {
                    this.water -= 350;
                    this.milk -= 75;
                    this.beans -= 20;
                    this.cups -=1;
                    this.cash +=7;
                    System.out.println("I have enough resources, making you a coffee!\n");
                }
                break;

            case CAPPUCCINO:
                if (check(200, 100, 12, 1)) {
                    this.cups -= 200;
                    this.milk -= 100;
                    this.beans -= 12;
                    this.cups -= 1;
                    this.cash += 6;
                    System.out.println("I have enough resources, making you a coffee!\n");
                }
                break;
        }
    }
}
