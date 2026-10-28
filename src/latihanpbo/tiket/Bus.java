package Pemesanan_Tiket.src.latihanpbo.tiket;
public class Bus extends Tiket {

    public Bus(String kodeTiket, String tujuan, double hargaPerTiket, int kursiTersedia) {
        super(kodeTiket, tujuan, hargaPerTiket, kursiTersedia);
    }

    @Override
    public double hitungTotalPembayaran(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah tiket harus lebih dari 0");
        }
        return getHargaPerTiket() * jumlah;
    }

    @Override
    public String getJenis() {
        return "Bus";
    }
}