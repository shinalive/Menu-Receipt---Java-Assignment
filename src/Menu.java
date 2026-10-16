public class Menu {
    private String nama;
    private int harga;
    private String kategori;

    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    // Daftar menu makanan dan minuman
    public static Menu[] buatDaftarMenu() {
        return new Menu[] {
                new Menu("Ayam Geprek", 25000, "Makanan"),
                new Menu("Nasi Kuning", 22000, "Makanan"),
                new Menu("Mie Goreng Jawa", 24000, "Makanan"),
                new Menu("Sate Taichan", 30000, "Makanan"),

                new Menu("Es Teh Lemon", 8000, "Minuman"),
                new Menu("Kopi Susu", 15000, "Minuman"),
                new Menu("Jus Mangga", 12000, "Minuman"),
                new Menu("Cokelat Dingin", 14000, "Minuman")
        };
    }
}