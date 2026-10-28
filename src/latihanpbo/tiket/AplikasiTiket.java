package Pemesanan_Tiket.src.latihanpbo.tiket;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AplikasiTiket {
    private static Scanner scanner = new Scanner(System.in);
    private static Tiket tiketBus = null;
    private static Tiket tiketKereta = null;
    private static Pemesanan pemesananAktif = null;

    public static void main(String[] args) {
        System.out.println("=== SISTEM PEMESANAN TIKET TRANSPORTASI ===");
        System.out.println("Silakan siapkan data tiket terlebih dahulu.\n");

        // Siapkan data awal
        siapkanTiketBus();
        siapkanTiketKereta();

        int pilihan;
        do {
            tampilkanMenu();
            pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    siapkanTiketBus();
                    break;
                case 2:
                    siapkanTiketKereta();
                    break;
                case 3:
                    buatPemesanan();
                    break;
                case 4:
                    tampilkanDetailPemesanan();
                    break;
                case 5:
                    batalkanPemesanan();
                    break;
                case 0:
                    System.out.println("Terima kasih. Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);

        scanner.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\n=========== MENU ===========");
        System.out.println("1. Siapkan / Ubah Tiket Bus");
        System.out.println("2. Siapkan / Ubah Tiket Kereta");
        System.out.println("3. Buat Pemesanan");
        System.out.println("4. Tampilkan Detail Pemesanan");
        System.out.println("5. Batalkan Pemesanan");
        System.out.println("0. Keluar");
        System.out.println("============================");
    }

    // ========== Helper Input ==========
    private static String bacaTeks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input tidak boleh kosong. Coba lagi.");
        }
    }

    private static int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }

    private static double bacaDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Tolak input yang mengandung huruf (contoh: 12D, 15.5abc, dll)
            if (!input.matches("-?\\d+(\\.\\d+)?")) {
                System.out.println("Input tidak valid (hanya angka diperbolehkan). Coba lagi.");
                continue;
            }

            try {
                double nilai = Double.parseDouble(input);
                if (Double.isNaN(nilai) || Double.isInfinite(nilai)) {
                    System.out.println("Nilai tidak valid. Coba lagi.");
                    continue;
                }
                return nilai;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Coba lagi.");
            }
        }
    }

    private static LocalDate bacaTanggal(String prompt) {
        while (true) {
            System.out.print(prompt + " (format: YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal salah. Gunakan YYYY-MM-DD. Coba lagi.");
            }
        }
    }

    // ========== Fitur ==========
    private static void siapkanTiketBus() {
        System.out.println("\n--- Data Tiket Bus ---");
        try {
            String kode = bacaTeks("Kode Tiket Bus      : ");
            String tujuan = bacaTeks("Tujuan              : ");
            double harga = bacaDouble("Harga per Tiket     : ");
            int kursi = bacaInt("Jumlah Kursi Tersedia: ");

            if (harga <= 0) {
                System.out.println("Harga harus lebih dari 0!");
                return;
            }
            if (kursi < 0) {
                System.out.println("Kursi tidak boleh negatif!");
                return;
            }

            tiketBus = new Bus(kode, tujuan, harga, kursi);
            System.out.println("Tiket Bus berhasil disiapkan.");
            System.out.println("Kursi tersedia saat ini: " + tiketBus.getKursiTersedia());
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private static void siapkanTiketKereta() {
        System.out.println("\n--- Data Tiket Kereta ---");
        try {
            String kode = bacaTeks("Kode Tiket Kereta   : ");
            String tujuan = bacaTeks("Tujuan              : ");
            double harga = bacaDouble("Harga per Tiket     : ");
            int kursi = bacaInt("Jumlah Kursi Tersedia: ");
            double biayaLayanan = bacaDouble("Biaya Layanan       : ");

            if (harga <= 0) {
                System.out.println("Harga harus lebih dari 0!");
                return;
            }
            if (kursi < 0) {
                System.out.println("Kursi tidak boleh negatif!");
                return;
            }
            if (biayaLayanan < 0) {
                System.out.println("Biaya layanan tidak boleh negatif!");
                return;
            }

            tiketKereta = new Kereta(kode, tujuan, harga, kursi, biayaLayanan);
            System.out.println("Tiket Kereta berhasil disiapkan.");
            System.out.println("Kursi tersedia saat ini: " + tiketKereta.getKursiTersedia());
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private static void buatPemesanan() {
        if (tiketBus == null && tiketKereta == null) {
            System.out.println("Belum ada tiket yang disiapkan!");
            return;
        }

        System.out.println("\n--- Buat Pemesanan ---");
        System.out.println("1. Bus");
        System.out.println("2. Kereta");
        int jenis = bacaInt("Pilih jenis tiket: ");

        Tiket tiketDipilih = null;
        if (jenis == 1) {
            if (tiketBus == null) {
                System.out.println("Tiket Bus belum disiapkan!");
                return;
            }
            tiketDipilih = tiketBus;
        } else if (jenis == 2) {
            if (tiketKereta == null) {
                System.out.println("Tiket Kereta belum disiapkan!");
                return;
            }
            tiketDipilih = tiketKereta;
        } else {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        try {
            String kodePesan = bacaTeks("Kode Pemesanan      : ");
            String nama = bacaTeks("Nama Pemesan        : ");
            int jumlah = bacaInt("Jumlah Tiket        : ");
            LocalDate tglPesan = bacaTanggal("Tanggal Pemesanan");
            LocalDate tglBerangkat = bacaTanggal("Tanggal Keberangkatan");

            pemesananAktif = new Pemesanan(kodePesan, nama, tiketDipilih,
                    jumlah, tglPesan, tglBerangkat);

            System.out.println("\nPemesanan berhasil dibuat!");
            pemesananAktif.cetakDetail();
            System.out.println("Sisa kursi " + tiketDipilih.getJenis() + ": " + tiketDipilih.getKursiTersedia());

        } catch (KursiTidakTersediaException | TanggalTidakValidException | IllegalArgumentException e) {
            System.out.println("Gagal membuat pemesanan: " + e.getMessage());
        }
    }

    private static void tampilkanDetailPemesanan() {
        if (pemesananAktif == null) {
            System.out.println("Belum ada pemesanan.");
            return;
        }
        pemesananAktif.cetakDetail();
    }

    private static void batalkanPemesanan() {
        if (pemesananAktif == null) {
            System.out.println("Belum ada pemesanan.");
            return;
        }
        if ("DIBATALKAN".equals(pemesananAktif.getStatus())) {
            System.out.println("Pemesanan sudah dibatalkan sebelumnya.");
            return;
        }

        try {
            System.out.println("\n--- Pembatalan Pemesanan ---");
            LocalDate tglBatal = bacaTanggal("Tanggal Pembatalan");
            double biaya = bacaDouble("Biaya Pembatalan    : ");

            Pembatalan pembatalan = new Pembatalan(pemesananAktif, tglBatal, biaya);
            pembatalan.prosesPembatalan();

            System.out.println("Pembatalan berhasil!");
            System.out.println("Biaya pembatalan: Rp" + String.format("%,.0f", pembatalan.hitungDenda()));
            System.out.println("Status sekarang : " + pemesananAktif.getStatus());
            System.out.println("Sisa kursi " + pemesananAktif.getTiket().getJenis() + ": "
                    + pemesananAktif.getTiket().getKursiTersedia());

        } catch (TanggalTidakValidException | IllegalArgumentException e) {
            System.out.println("Gagal membatalkan: " + e.getMessage());
        }
    }
}