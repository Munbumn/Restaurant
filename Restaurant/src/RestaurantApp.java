import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.RestaurantController;

class RestaurantApp {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public static void main(String[] args) throws Exception {
        RestaurantController restaurantController = new RestaurantController();
        restaurantController.setUp();
        boolean continueExecution = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Selamat datang di Restoran And Lounge Cibiru!");
        System.out.println("Silakan tekan Enter untuk memulai");
        reader.readLine(); // Menunggu user menekan Enter

        boolean isAdmin = chooseLoginMode(reader);

        while (continueExecution) {
            if (isAdmin) {
                if (loginAdmin(reader)) {
                    System.out.println("Login berhasil. Selamat datang, Admin!");
                    while (continueExecution && isAdmin) {
                        displayAdminMenu();
                        System.out.print("Pilih menu:");
                        String adminChoice = reader.readLine();
                        switch (adminChoice) {
                            case "1":
                                restaurantController.addMenu();
                                break;
                            case "2":
                                restaurantController.viewMenu();
                                break;
                            case "3":
                                isAdmin = false; // Mengubah mode admin ke false untuk beralih ke mode pembeli
                                break;
                            case "4":
                                continueExecution = false;
                                break;
                            default:
                                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                                break;
                        }
                    }
                } else {
                    System.out.println("Login gagal. Silakan coba lagi.");
                }
            } else {
                System.out.println("Hai, selamat datang!");
                while (continueExecution && !isAdmin) {
                    displayCustomerMenu();
                    System.out.print("Pilih menu:");
                    String customerChoice = reader.readLine();
                    switch (customerChoice) {
                        case "1":
                            restaurantController.viewMenu();
                            break;
                        case "2":
                            restaurantController.placeOrder();
                            break;
                        case "3":
                            restaurantController.viewOrderHistory();
                            break;
                        case "4":
                            continueExecution = false;
                            break;
                        case "5":
                            isAdmin = loginAdmin(reader); // Coba login kembali sebagai admin
                            if (isAdmin) {
                                System.out.println("Login berhasil. Selamat datang, Admin!");
                                break;
                            }
                            System.out.println("Login gagal. Silakan coba lagi.");
                            break;
                        default:
                            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                            break;
                    }
                }
            }
        }

        System.out.println("Terima kasih telah mengunjungi Restoran Cibiru!");
    }

    private static boolean chooseLoginMode(BufferedReader reader) throws IOException {
        System.out.println("Pilih Mode Login:");
        System.out.println("1. Staff (Admin)");
        System.out.println("2. Pelanggan");
        System.out.print("Masukkan pilihan: ");
        String mode = reader.readLine();
        return "1".equals(mode);
    }

    private static boolean loginAdmin(BufferedReader reader) throws IOException {
        System.out.print("Masukkan username Admin: ");
        String username = reader.readLine();
        System.out.print("Masukkan password Admin: ");
        String password = reader.readLine();
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    private static void displayAdminMenu() {
        System.out.println("1. Tambah Menu");
        System.out.println("2. Lihat Menu");
        System.out.println("3. Switch to Pembeli");
        System.out.println("4. Keluar");
    }

    private static void displayCustomerMenu() {
        System.out.println("1. Lihat Menu");
        System.out.println("2. Pesan Makanan");
        System.out.println("3. Riwayat Pesanan");
        System.out.println("4. Keluar");
        System.out.println("5. Switch to Admin");
    }
}




