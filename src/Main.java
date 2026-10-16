import java.util.Scanner;
import java.util.Locale;

class Main {

    private static final int BIAYA_PELAYANAN = 20000;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Array berisi 4 menu makanan dan 4 menu minuman
        Menu[] daftarMenu = Menu.buatDaftarMenu();

        // Array untuk menyimpan maksimal 4 jenis pesanan
        Menu[] pesanan = new Menu[4];
        int[] jumlahPesanan = new int[4];

        tampilkanMenu(daftarMenu);

        System.out.println("\nMasukkan maksimal 4 pesanan.");
        System.out.println("Format: Nama Menu = jumlah");
        System.out.println("Contoh: Ayam Geprek = 2");
        System.out.println("Tekan Enter jika tidak ingin mengisi slot tersebut.\n");

        System.out.print("Pesanan 1: ");
        prosesPesanan(input.nextLine(), daftarMenu,
                pesanan, jumlahPesanan, 0);

        System.out.print("Pesanan 2: ");
        prosesPesanan(input.nextLine(), daftarMenu,
                pesanan, jumlahPesanan, 1);

        System.out.print("Pesanan 3: ");
        prosesPesanan(input.nextLine(), daftarMenu,
                pesanan, jumlahPesanan, 2);

        System.out.print("Pesanan 4: ");
        prosesPesanan(input.nextLine(), daftarMenu,
                pesanan, jumlahPesanan, 3);

        int subtotal = hitungSubtotal(pesanan, jumlahPesanan);

        if (subtotal == 0) {
            System.out.println("\nTidak ada pesanan yang valid.");
            input.close();
            return;
        }

        // Diskon 10% jika subtotal melebihi Rp100.000
        int diskon;

        if (subtotal > 100000) {
            diskon = (int) (subtotal * 0.10);
        } else {
            diskon = 0;
        }

        int subtotalSetelahDiskon = subtotal - diskon;

        // Promo BOGO jika subtotal melebihi Rp50.000
        Menu minumanGratis = null;
        boolean promoBogo = false;

        if (subtotal > 50000) {
            minumanGratis = cariMinumanPromo(pesanan);

            if (minumanGratis != null) {
                promoBogo = true;
            } else {
                promoBogo = false;
            }
        } else {
            promoBogo = false;
        }

        // Pajak dihitung dari subtotal setelah diskon
        int pajak = (int) (subtotalSetelahDiskon * 0.10);

        int totalAkhir = subtotalSetelahDiskon
                + pajak
                + BIAYA_PELAYANAN;

        cetakStruk(
                pesanan,
                jumlahPesanan,
                subtotal,
                diskon,
                subtotalSetelahDiskon,
                pajak,
                BIAYA_PELAYANAN,
                totalAkhir,
                promoBogo,
                minumanGratis
        );

        input.close();
    }

    // Menampilkan menu berdasarkan kategori
    public static void tampilkanMenu(Menu[] daftarMenu) {

        System.out.println("====================================");
        System.out.println("          MENU RESTORAN");
        System.out.println("====================================");

        System.out.println("\n--- MAKANAN ---");
        cetakSatuMenu(daftarMenu[0]);
        cetakSatuMenu(daftarMenu[1]);
        cetakSatuMenu(daftarMenu[2]);
        cetakSatuMenu(daftarMenu[3]);

        System.out.println("\n--- MINUMAN ---");
        cetakSatuMenu(daftarMenu[4]);
        cetakSatuMenu(daftarMenu[5]);
        cetakSatuMenu(daftarMenu[6]);
        cetakSatuMenu(daftarMenu[7]);

        System.out.println("====================================");
    }

    public static void cetakSatuMenu(Menu menu) {
        System.out.println(
                menu.getNama() + " - " + formatRupiah(menu.getHarga())
        );
    }

    // Menerima nama menu dalam bentuk teks
    public static void prosesPesanan(
            String inputPesanan,
            Menu[] daftarMenu,
            Menu[] pesanan,
            int[] jumlahPesanan,
            int indeks) {

        if (inputPesanan == null || inputPesanan.trim().isEmpty()) {
            return;
        }

        String[] bagian = inputPesanan.split("=", 2);

        if (bagian.length != 2) {
            System.out.println(
                    "Format salah. Gunakan: Nama Menu = jumlah"
            );
            return;
        }

        String namaMenu = bagian[0].trim().toLowerCase(Locale.ROOT);
        int jumlah;

        try {
            jumlah = Integer.parseInt(bagian[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Jumlah harus berupa angka.");
            return;
        }

        if (jumlah <= 0) {
            System.out.println("Jumlah pesanan harus lebih dari 0.");
            return;
        }

        Menu menuDipilih = null;

        // Nama menu dicocokkan menggunakan switch
        switch (namaMenu) {
            case "ayam geprek":
                menuDipilih = daftarMenu[0];
                break;

            case "nasi kuning":
                menuDipilih = daftarMenu[1];
                break;

            case "mie goreng jawa":
                menuDipilih = daftarMenu[2];
                break;

            case "sate taichan":
                menuDipilih = daftarMenu[3];
                break;

            case "es teh lemon":
                menuDipilih = daftarMenu[4];
                break;

            case "kopi susu":
                menuDipilih = daftarMenu[5];
                break;

            case "jus mangga":
                menuDipilih = daftarMenu[6];
                break;

            case "cokelat dingin":
                menuDipilih = daftarMenu[7];
                break;

            default:
                System.out.println(
                        "Menu tidak ditemukan: " + bagian[0].trim()
                );
                return;
        }

        // Struktur if - else if
        if (menuDipilih.getKategori().equals("Makanan")) {
            System.out.println("Kategori: Makanan");
        } else if (menuDipilih.getKategori().equals("Minuman")) {
            System.out.println("Kategori: Minuman");
        } else {
            System.out.println("Kategori menu tidak dikenal.");
            return;
        }

        pesanan[indeks] = menuDipilih;
        jumlahPesanan[indeks] = jumlah;

        System.out.println(
                "Pesanan diterima: "
                        + menuDipilih.getNama()
                        + " x "
                        + jumlah
        );
    }

    // Menghitung subtotal tanpa menggunakan pengulangan
    public static int hitungSubtotal(
            Menu[] pesanan,
            int[] jumlahPesanan) {

        int subtotal = 0;

        if (pesanan[0] != null) {
            subtotal += pesanan[0].getHarga() * jumlahPesanan[0];
        }

        if (pesanan[1] != null) {
            subtotal += pesanan[1].getHarga() * jumlahPesanan[1];
        }

        if (pesanan[2] != null) {
            subtotal += pesanan[2].getHarga() * jumlahPesanan[2];
        }

        if (pesanan[3] != null) {
            subtotal += pesanan[3].getHarga() * jumlahPesanan[3];
        }

        return subtotal;
    }

    // Memilih minuman pertama yang ditemukan dalam pesanan
    public static Menu cariMinumanPromo(Menu[] pesanan) {

        if (pesanan[0] != null
                && pesanan[0].getKategori().equals("Minuman")) {
            return pesanan[0];
        }

        if (pesanan[1] != null
                && pesanan[1].getKategori().equals("Minuman")) {
            return pesanan[1];
        }

        if (pesanan[2] != null
                && pesanan[2].getKategori().equals("Minuman")) {
            return pesanan[2];
        }

        if (pesanan[3] != null
                && pesanan[3].getKategori().equals("Minuman")) {
            return pesanan[3];
        }

        return null;
    }

    // Mencetak struk pembayaran
    public static void cetakStruk(
            Menu[] pesanan,
            int[] jumlahPesanan,
            int subtotal,
            int diskon,
            int subtotalSetelahDiskon,
            int pajak,
            int biayaPelayanan,
            int totalAkhir,
            boolean promoBogo,
            Menu minumanGratis) {

        System.out.println("\n====================================");
        System.out.println("             STRUK PESANAN");
        System.out.println("====================================");

        cetakDetailPesanan(pesanan[0], jumlahPesanan[0]);
        cetakDetailPesanan(pesanan[1], jumlahPesanan[1]);
        cetakDetailPesanan(pesanan[2], jumlahPesanan[2]);
        cetakDetailPesanan(pesanan[3], jumlahPesanan[3]);

        System.out.println("------------------------------------");
        System.out.println("Subtotal           : " + formatRupiah(subtotal));

        if (diskon > 0) {
            System.out.println(
                    "Diskon 10%         : -" + formatRupiah(diskon)
            );
        } else {
            System.out.println("Diskon             : Rp0");
        }

        System.out.println(
                "Subtotal setelah diskon: "
                        + formatRupiah(subtotalSetelahDiskon)
        );

        System.out.println(
                "Pajak 10%          : " + formatRupiah(pajak)
        );

        System.out.println(
                "Biaya pelayanan    : " + formatRupiah(biayaPelayanan)
        );

        // Nested if untuk memeriksa promo dan ketersediaan minuman
        if (promoBogo) {
            if (minumanGratis != null) {
                System.out.println("------------------------------------");
                System.out.println("PROMO BELI 1 GRATIS 1");
                System.out.println(
                        minumanGratis.getNama() + " x 1 GRATIS"
                );
                System.out.println(
                        "Nilai minuman gratis: "
                                + formatRupiah(minumanGratis.getHarga())
                );
                System.out.println(
                        "Harga minuman bonus : Rp0"
                );
            }
        } else {
            System.out.println("Promo BOGO         : Tidak berlaku");
        }

        System.out.println("------------------------------------");
        System.out.println(
                "TOTAL BAYAR        : " + formatRupiah(totalAkhir)
        );
        System.out.println("====================================");
        System.out.println("Terima kasih telah berkunjung!");
    }

    public static void cetakDetailPesanan(Menu menu, int jumlah) {

        if (menu != null) {
            int totalItem = menu.getHarga() * jumlah;

            System.out.println(menu.getNama());
            System.out.println(
                    "  " + jumlah
                            + " x " + formatRupiah(menu.getHarga())
                            + " = " + formatRupiah(totalItem)
            );
        }
    }

    // Mengubah angka menjadi format Rupiah
    public static String formatRupiah(int nilai) {
        return "Rp" + String.format(Locale.US, "%,d", nilai)
                .replace(',', '.');
    }
}