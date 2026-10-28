package Pemesanan_Tiket.src.latihanpbo.tiket;
public class Kereta extends Tiket {
    private double biayaLayanan;

    public Kereta(String kodeTiket, String tujuan, double hargaPerTiket, int kursiTersedia, double biayaLayanan) {
        super(kodeTiket, tujuan, hargaPerTiket, kursiTersedia);
        if (biayaLayanan < 0) {
            throw new IllegalArgumentException("Biaya layanan tidak boleh negatif");
        }
        this.biayaLayanan = biayaLayanan;
    }

    public double getBiayaLayanan() {
        return biayaLayanan;
    }

    @Override
    public double hitungTotalPembayaran(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah tiket harus lebih dari 0");
        }
        // sesuai UML: biayaLayanan ditambahkan sekali, bukan dikalikan jumlah
        return (getHargaPerTiket() * jumlah) + biayaLayanan;
    }

    @Override
    public String getJenis() {
        return "Kereta";
    }
}