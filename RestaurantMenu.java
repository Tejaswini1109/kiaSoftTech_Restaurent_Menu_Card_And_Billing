package resto;

import java.text.SimpleDateFormat;
import java.util.*;

class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Menu {
    private Map<String, List<MenuItem>> sections;

    public Menu() {
        sections = new HashMap<>();
    }

    public void addItem(String section, MenuItem item) {
        List<MenuItem> items = sections.getOrDefault(section, new ArrayList<>());
        items.add(item);
        sections.put(section, items);
    }

    public List<MenuItem> getItemsInSection(String section) {
        return sections.getOrDefault(section, new ArrayList<>());
    }
}

class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }
}

class Order {
    private List<OrderItem> orderItems;
    private Date date;

    public Order() {
        orderItems = new ArrayList<>();
        date = new Date();
    }

    public void addItem(OrderItem item) {
        orderItems.add(item);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double calculateTotal() {
        double total = 0.0;
        for (OrderItem item : orderItems) {
            total += item.getMenuItem().getPrice() * item.getQuantity();
        }
        return total;
    }

    public Date getDate() {
        return date;
    }
}

public class RestaurantMenu {
    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // Create menu items
        MenuItem item1 = new MenuItem("Idli", 30.0);
        MenuItem item2 = new MenuItem("Dosa", 40.0);
        MenuItem item3 = new MenuItem("Pulav", 60.0);
        MenuItem item10 = new MenuItem("Vada", 10.0);
        MenuItem item4 = new MenuItem("Coffee", 20.0);
        MenuItem item5 = new MenuItem("Tea", 15.0);
        MenuItem item6 = new MenuItem("Badam Milk", 25.0);
        MenuItem item7 = new MenuItem("Gobi Manchuri", 50.0);
        MenuItem item8 = new MenuItem("Pani Puri", 30.0);
        MenuItem item9 = new MenuItem("Noodles", 45.0);

        // Create menu
        Menu menu = new Menu();
        menu.addItem("Lunch", item1);
        menu.addItem("Lunch", item2);
        menu.addItem("Lunch", item3);
        menu.addItem("Lunch", item10);
        menu.addItem("Drinks", item4);
        menu.addItem("Drinks", item5);
        menu.addItem("Drinks", item6);
        menu.addItem("Junk Food", item7);
        menu.addItem("Junk Food", item8);
        menu.addItem("Junk Food", item9);

        // Display menu items
        System.out.println("Menu Card:");
        displaySection(menu, "Lunch");
        displaySection(menu, "Drinks");
        displaySection(menu, "Junk Food");
        System.out.println();

        // Take order from the customer
        Scanner scanner = new Scanner(System.in);
        Order order = new Order();
        boolean orderMore = true;
        while (orderMore) {
            System.out.print("Enter the section name: ");
            String section = scanner.nextLine();
            System.out.print("Enter the item number: ");
            int itemNumber = scanner.nextInt();
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            List<MenuItem> itemsInSection = menu.getItemsInSection(section);
            if (itemNumber > 0 && itemNumber <= itemsInSection.size()) {
                MenuItem selectedMenuItem = itemsInSection.get(itemNumber - 1);
                OrderItem orderItem = new OrderItem(selectedMenuItem, quantity);
                order.addItem(orderItem);
            } else {
                System.out.println("Invalid item number!");
            }

            System.out.print("Do you want to order more items? (yes/no): ");
            String choice = scanner.nextLine();
            orderMore = choice.equalsIgnoreCase("yes");
            System.out.println();
        }

        System.out.println("Date and Time: " + dateFormat.format(order.getDate()));

        
        // Display the order and bill
        System.out.println("Order Details:");
        for (OrderItem item : order.getOrderItems()) {
            System.out.println(item.getMenuItem().getName() + " x " + item.getQuantity() + " - Rs. " +
                    item.getMenuItem().getPrice() * item.getQuantity());
        }

        double total = order.calculateTotal();
        double gst = total * 0.18;
        double totalWithGst = total + gst;

        System.out.println("-------------------------------");
        System.out.println("Total Amount: Rs. " + total);
        System.out.println("GST (18%): Rs. " + gst);
        System.out.println("Total Bill (including GST): Rs. " + totalWithGst);
        System.out.println("-------------------------------");
        System.out.println("Thank You! Visit Again.");

        scanner.close();
    }

    private static void displaySection(Menu menu, String section) {
        System.out.println(section + ":");
        List<MenuItem> items = menu.getItemsInSection(section);
        for (int i = 0; i < items.size(); i++) {
            MenuItem menuItem = items.get(i);
            System.out.println((i + 1) + ". " + menuItem.getName() + " - Rs. " + menuItem.getPrice());
        }
        System.out.println();
    }
}
