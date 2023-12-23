package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import model.Menu;
import model.OrderItem;
import model.Order;

public class RestaurantController {
    private HashMap<String, Menu> menus = new HashMap<>();
    private ArrayList<Order> orders = new ArrayList<>();

    public void setUp() {
        Menu menu1 = new Menu("M01", "Nasi Goreng", 15000);
        Menu menu2 = new Menu("M02", "Ayam Goreng", 25000);
        Menu menu3 = new Menu("M03", "Es Teh Manis", 5000);

        menus.put(menu1.getIdMenu(), menu1);
        menus.put(menu2.getIdMenu(), menu2);
        menus.put(menu3.getIdMenu(), menu3);
    }

    public void addMenu() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("=== Tambah Menu ===");
            System.out.print("Masukkan ID Menu: ");
            String idMenu = input.readLine();
            System.out.print("Masukkan Nama Menu: ");
            String namaMenu = input.readLine();
            System.out.print("Masukkan Harga Menu: ");
            int hargaMenu = Integer.parseInt(input.readLine());

            Menu menu = new Menu(idMenu, namaMenu, hargaMenu);
            menus.put(menu.getIdMenu(), menu);
            System.out.println("Menu berhasil ditambahkan!");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewMenu() {
        System.out.println("==== Daftar Menu ====");
        System.out.printf("| %-10s | %-20s | %-10s |\n", "Kode Menu", "Nama Menu Makanan", "Harga");
        System.out.println("+------------+----------------------+------------+");
    
        for (Map.Entry<String, Menu> menu : menus.entrySet()) {
            String key = menu.getKey();
            Menu tMenu = menu.getValue();
            System.out.printf("| %-10s | %-20s | %-10d |\n", key, tMenu.getNamaMenu(), tMenu.getHargaMenu());
        }
    
        System.out.println("+------------+----------------------+------------+\n");
    }

    public void placeOrder() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        int increment = 0;
        String continueOrder = "";
        try {
            do {
                increment++;
                System.out.println();
                System.out.print("Silahkan masukkan kode menu\t: ");
                String menuCode = input.readLine();
                System.out.print("Jumlah yang akan dipesan\t: ");
                int quantity = Integer.valueOf(input.readLine());
                System.out.print("Tambah menu lain? (Y/N): ");
                continueOrder = input.readLine();

                OrderItem orderItem = new OrderItem();
                Menu tMenu = getMenu(menuCode);
                orderItem.setIdOrderItem("OI" + increment);
                orderItem.setMenu(tMenu);
                orderItem.setHargaSatuan(tMenu.getHargaMenu());
                orderItem.setJumlahPesan(quantity);
                orderItem.setSubTotal(tMenu.getHargaMenu() * quantity);
                orderItems.add(orderItem);

            } while (continueOrder.equalsIgnoreCase("Y"));

            System.out.println("");
            System.out.println("===Berikut Daftar Pesanan Anda===");
            displayOrderItems(orderItems);
            System.out.println("TOTAL: " + calculateTotalOrder(orderItems));
            BufferedReader inputConfirmation = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Konfirmasi pesanan (Y/N)?");
            String confirm = "n";
            confirm = inputConfirmation.readLine();
            if (confirm.equalsIgnoreCase("y")) {
                Order order = new Order();
                order.setOrderItems(orderItems);
                    order.setIdOrder("O" + (orders.size() + 1));
                    order.setTanggal(LocalDate.now());
                    order.setTotal(calculateTotalOrder(orderItems));
                orders.add(order);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewOrderHistory() {
        System.out.println("==== Riwayat Pesanan ====");
    
        for (Order order : orders) {
            System.out.println();
            System.out.printf("Kode Pesanan \t \t : %s\n", order.getIdOrder());
            System.out.printf("Tanggal pesanan \t : %s\n", order.getTanggal());
            System.out.println("=========================");
            ArrayList<OrderItem> orderItems = order.getOrderItems();
            displayOrderItems(orderItems);
            System.out.println("=========================");
            System.out.printf("Total \t \t : %s\n", order.getTotal());
        }
    
        System.out.println();
    }
    
    private void displayOrderItems(ArrayList<OrderItem> orderItems) {
        System.out.println("╔════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-20s %-20s %-20s         ║\n", "Nama Menu", "Harga", "Jumlah");
        System.out.println("╠════════════════════════════════════════════════════════════════════════╣");
    
        for (OrderItem orderItem : orderItems) {
            System.out.printf("║ %-20s %-20s %-20s         ║\n",
                    orderItem.getMenu().getNamaMenu(),
                    orderItem.getHargaSatuan(),
                    orderItem.getJumlahPesan());
        System.out.println("╠════════════════════════════════════════════════════════════════════════║");
        }
    
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝");
    }
    

    private int calculateTotalOrder(ArrayList<OrderItem> orderItems) {
        int total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getSubTotal();
        }
        return total;
    }

    private Menu getMenu(String menuCode) {
        return menus.get(menuCode);
    }
}

