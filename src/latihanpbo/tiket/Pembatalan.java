package Pemesanan_Tiket.src.latihanpbo.tiket;
import java.time.LocalDate;

public class Pembatalan {
    private Pemesanan pemesanan;
    private LocalDate tanggalPembatalan;
    private double biayaPembatalan;

    public Pembatalan(Pemesanan pemesanan, LocalDate tanggalPembatalan, double biayaPembatalan)
            throws TanggalTidakValidException {

        if (pemesanan == null) {
            throw new IllegalArgumentException("Pemesanan tidak boleh null");
        }
        if (!"AKTIF".equals(pemesanan.getStatus())) {
            throw new IllegalArgumentException("Pemesanan sudah tidak aktif / sudah dibatalkan");
        }
        if (tanggalPembatalan == null) {
            throw new TanggalTidakValidException("Tanggal pembatalan tidak boleh null");
        }
        if (tanggalPembatalan.isBefore(pemesanan.getTanggalPesan())) {
            throw new TanggalTidakValidException(
                    "Tanggal pembatalan tidak boleh sebelum tanggal pemesanan");
        }
        if (tanggalPembatalan.isAfter(pemesanan.getTanggalKeberangkatan())) {
            throw new TanggalTidakValidException(
                    "Tidak bisa membatalkan setelah tanggal keberangkatan");
        }
        if (biayaPembatalan < 0) {
            throw new IllegalArgumentException("Biaya pembatalan tidak boleh negatif");
        }

        this.pemesanan = pemesanan;
        this.tanggalPembatalan = tanggalPembatalan;
        this.biayaPembatalan = biayaPembatalan;
    }

    public double hitungDenda() {
        return biayaPembatalan;
    }

    public void prosesPembatalan() {
        pemesanan.batalkan();
    }

    public LocalDate getTanggalPembatalan() {
        return tanggalPembatalan;
    }

    public double getBiayaPembatalan() {
        return biayaPembatalan;
    }
}