package Pemesanan_Tiket.src.latihanpbo.tiket;

public abstract class Tiket {
    private String kodeTiket;
    private String tujuan;
    private double hargaPerTiket;
    private int kursiTersedia;

    public Tiket(String kodeTiket, String tujuan, double hargaPerTiket, int kursiTersedia) {
        if (kodeTiket == null || kodeTiket.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode tiket tidak boleh kosong");
        }
        if (tujuan == null || tujuan.trim().isEmpty()) {
            throw new IllegalArgumentException("Tujuan tidak boleh kosong");
        }
        if (hargaPerTiket <= 0) {
            throw new IllegalArgumentException("Harga per tiket harus lebih dari 0");
        }
        if (kursiTersedia < 0) {
            throw new IllegalArgumentException("Kursi tersedia tidak boleh negatif");
        }

        this.kodeTiket = kodeTiket.trim();
        this.tujuan = tujuan.trim();
        this.hargaPerTiket = hargaPerTiket;
        this.kursiTersedia = kursiTersedia;
    }

    public String getKodeTiket() {
        return kodeTiket;
    }

    public String getTujuan() {
        return tujuan;
    }

    public double getHargaPerTiket() {
        return hargaPerTiket;
    }

    public int getKursiTersedia() {
        return kursiTersedia;
    }

    public void kurangiKursi(int jumlah) throws KursiTidakTersediaException {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah kursi yang dikurangi harus lebih dari 0");
        }
        if (jumlah > kursiTersedia) {
            throw new KursiTidakTersediaException(
                    "Kursi tidak cukup. Tersedia: " + kursiTersedia + ", diminta: " + jumlah);
        }
        kursiTersedia -= jumlah;
    }

    public void tambahKursi(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah kursi yang ditambah harus lebih dari 0");
        }
        kursiTersedia += jumlah;
    }

    // Method abstrak
    public abstract double hitungTotalPembayaran(int jumlah);

    public abstract String getJenis();
}