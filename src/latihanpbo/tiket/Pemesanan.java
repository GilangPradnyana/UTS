package Pemesanan_Tiket.src.latihanpbo.tiket;

import java.time.LocalDate;

public class Pemesanan {
    private String kodePesan;
    private String namaPemesan;
    private Tiket tiket;
    private int jumlahTiket;
    private LocalDate tanggalPesan;
    private LocalDate tanggalKeberangkatan;
    private String status;

    public Pemesanan(String kodePesan, String namaPemesan, Tiket tiket,
                     int jumlahTiket, LocalDate tanggalPesan, LocalDate tanggalKeberangkatan)
            throws KursiTidakTersediaException, TanggalTidakValidException {

        if (kodePesan == null || kodePesan.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode pemesanan tidak boleh kosong");
        }
        if (namaPemesan == null || namaPemesan.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pemesan tidak boleh kosong");
        }
        if (tiket == null) {
            throw new IllegalArgumentException("Tiket tidak boleh null");
        }
        if (jumlahTiket <= 0) {
            throw new IllegalArgumentException("Jumlah tiket harus lebih dari 0");
        }
        if (tanggalPesan == null || tanggalKeberangkatan == null) {
            throw new TanggalTidakValidException("Tanggal tidak boleh null");
        }
        if (tanggalKeberangkatan.isBefore(tanggalPesan)) {
            throw new TanggalTidakValidException(
                    "Tanggal keberangkatan tidak boleh sebelum tanggal pemesanan");
        }

        // Kurangi kursi (bisa melempar KursiTidakTersediaException)
        tiket.kurangiKursi(jumlahTiket);

        this.kodePesan = kodePesan.trim();
        this.namaPemesan = namaPemesan.trim();
        this.tiket = tiket;
        this.jumlahTiket = jumlahTiket;
        this.tanggalPesan = tanggalPesan;
        this.tanggalKeberangkatan = tanggalKeberangkatan;
        this.status = "AKTIF";
    }

    public double hitungTotal() {
        return tiket.hitungTotalPembayaran(jumlahTiket);
    }

    public void cetakDetail() {
        System.out.println("\n========== DETAIL PEMESANAN ==========");
        System.out.println("Kode Pemesanan   : " + kodePesan);
        System.out.println("Nama Pemesan     : " + namaPemesan);
        System.out.println("Jenis Tiket      : " + tiket.getJenis());
        System.out.println("Kode Tiket       : " + tiket.getKodeTiket());
        System.out.println("Tujuan           : " + tiket.getTujuan());
        System.out.println("Harga per Tiket  : Rp" + String.format("%,.0f", tiket.getHargaPerTiket()));
        System.out.println("Jumlah Tiket     : " + jumlahTiket);
        System.out.println("Tanggal Pesan    : " + tanggalPesan);
        System.out.println("Tanggal Berangkat: " + tanggalKeberangkatan);
        System.out.println("Status           : " + status);
        System.out.println("Total Pembayaran : Rp" + String.format("%,.0f", hitungTotal()));
        System.out.println("======================================");
    }

    public void batalkan() {
        if ("DIBATALKAN".equals(status)) {
            return; // sudah dibatalkan, jangan kembalikan kursi lagi
        }
        status = "DIBATALKAN";
        tiket.tambahKursi(jumlahTiket);
    }

    // Getter
    public String getKodePesan() { return kodePesan; }
    public String getNamaPemesan() { return namaPemesan; }
    public Tiket getTiket() { return tiket; }
    public int getJumlahTiket() { return jumlahTiket; }
    public LocalDate getTanggalPesan() { return tanggalPesan; }
    public LocalDate getTanggalKeberangkatan() { return tanggalKeberangkatan; }
    public String getStatus() { return status; }
}