// Bu dosyanın hangi pakette (klasör yolunda) yaşadığını Java'ya bildirir
package com.msb.flightmiles; // uygulama kök paketi: giriş noktası burada

// List arayüzünü kullanmak için import ederiz (business uçuş listesini tutmak için)
import java.util.List; // Stream'den dönen List<Flight> tipi için gerekir

// model paketinden Flight sınıfını kullanmak için import ederiz
import com.msb.flightmiles.model.Flight; // Flight: uçuş bilgisi tutan sınıf (Lombok @Builder ile)
// service paketinden FlightManager sınıfını kullanmak için import ederiz
import com.msb.flightmiles.service.FlightManager; // FlightManager: List/Set/Map + Stream yönetimi
// service paketinden MilesCalculator sınıfını kullanmak için import ederiz
import com.msb.flightmiles.service.MilesCalculator; // MilesCalculator: klasik mil hesabı (Faz 1 başı)

// Main sınıfı: Uygulamanın başlangıç noktasıdır (program buradan çalışır)
public class Main { // Main adında bir sınıf tanımlıyoruz

    // main metodu: Java programı çalıştırıldığında ilk burası çalışır
    public static void main(String[] args) { // static: nesne oluşturmadan çağrılabilir; args: komut satırı argümanları

        System.out.println("=== Uçuş ve Mil Hesaplama Uygulaması ==="); // başlık satırını yazdır
        System.out.println(); // boş bir satır bırak (okunabilirlik için)

        // --- Flight NESNELERİ: Lombok @Builder deseni ile oluşturma ---
        // new Flight(...) yerine Flight.builder()...build() kullanıyoruz (alan isimleri okunaklı)

        // 1. uçuş: İstanbul, kısa mesafe, ekonomi — Builder zinciri başlar
        Flight flight1 = Flight.builder() // @Builder: boş bir Flight.Builder nesnesi üretir
                .flightNumber("TK101") // Builder: flightNumber alanını "TK101" yapar
                .destination("Istanbul") // Builder: destination alanını "Istanbul" yapar
                .distanceKm(850) // Builder: distanceKm alanını 850 yapar
                .businessClass(false) // Builder: businessClass alanını false (ekonomi) yapar
                .build(); // build(): ayarlanan alanlarla gerçek Flight nesnesini oluşturur

        // 2. uçuş: New York, uzun mesafe, business
        Flight flight2 = Flight.builder() // @Builder ile yeni Builder başlat
                .flightNumber("TK1903") // uçuş numarası TK1903
                .destination("New York") // varış: New York
                .distanceKm(3200) // mesafe: 3200 km
                .businessClass(true) // business class: true
                .build(); // Flight nesnesini üret ve flight2'ye ata

        // 3. uçuş: Antalya, orta mesafe, ekonomi
        Flight flight3 = Flight.builder() // @Builder ile yeni Builder başlat
                .flightNumber("PC404") // uçuş numarası PC404
                .destination("Antalya") // varış: Antalya
                .distanceKm(2100) // mesafe: 2100 km
                .businessClass(false) // ekonomi sınıfı
                .build(); // Flight nesnesini üret ve flight3'e ata

        // 4. uçuş: yine İstanbul (Set'te "Istanbul" tekrar eklenmeyecek)
        Flight flight4 = Flight.builder() // @Builder ile yeni Builder başlat
                .flightNumber("TK250") // uçuş numarası TK250
                .destination("Istanbul") // varış tekrar İstanbul (Set tekrarı engeller)
                .distanceKm(450) // mesafe: 450 km
                .businessClass(true) // ikinci business uçuş
                .build(); // Flight nesnesini üret ve flight4'e ata

        // --- FlightManager İLE KOLEKSİYONLARI DOLDURMA ---

        // Uçuş yöneticisi nesnesini oluştur (içinde boş List, Set, Map hazır gelir)
        FlightManager manager = new FlightManager(); // new ile FlightManager oluştur

        manager.addFlight(flight1); // 1. uçuşu List + Set + Map'e ekle
        manager.addFlight(flight2); // 2. uçuşu List + Set + Map'e ekle
        manager.addFlight(flight3); // 3. uçuşu List + Set + Map'e ekle
        manager.addFlight(flight4); // 4. uçuşu List + Set + Map'e ekle

        System.out.println("--- Tüm Uçuşlar (List) ---"); // bölüm başlığı
        manager.printAllFlights(); // listedeki tüm uçuşları yazdır
        System.out.println(); // boş satır

        System.out.println("--- Benzersiz Varışlar (Set) ---"); // bölüm başlığı
        manager.printDestinations(); // Set içeriğini yazdır (Istanbul bir kez görünür)
        System.out.println(); // boş satır

        System.out.println("--- Map ile Hızlı Arama ---"); // bölüm başlığı
        Flight found = manager.findFlightByNumber("TK1903"); // Map'ten "TK1903" anahtarını ara
        if (found != null) { // eğer uçuş bulunduysa (null değilse)
            System.out.println("Bulunan uçuş:"); // bilgilendirme mesajı
            found.printFlightInfo(); // bulunan uçuşun detaylarını yazdır
        } else { // uçuş bulunamadıysa
            System.out.println("Uçuş bulunamadı."); // hata / bilgi mesajı
        } // if-else bloğunun kapanış süslü parantezi
        System.out.println(); // boş satır

        // --- STREAM API: Business Class filtreleme ---

        System.out.println("--- Stream filter: Business Class Uçuşlar ---"); // bölüm başlığı
        List<Flight> businessFlights = manager.getBusinessClassFlights(); // filter + collect sonucu
        System.out.println("Business uçuş sayısı: " + businessFlights.size()); // kaç business uçuş var
        // for-each ile filtrelenmiş listeyi gezip yazdır
        for (Flight businessFlight : businessFlights) { // her business uçuş için döner
            businessFlight.printFlightInfo(); // uçuş bilgilerini yazdır (getter'lar Lombok @Data'dan)
            System.out.println("-----"); // ayırıcı çizgi
        } // for-each döngüsünün kapanış süslü parantezi
        System.out.println(); // boş satır

        // --- STREAM API: mapToInt + sum ile toplam mil ---

        System.out.println("--- Stream mapToInt/sum: Toplam Mil ---"); // bölüm başlığı
        int totalMiles = manager.calculateTotalMilesWithStream(); // tüm uçuşların mil toplamı
        System.out.println("STREAM sonucu - Toplam mil: " + totalMiles); // toplamı yazdır
        System.out.println(); // boş satır

        // --- ESKİ MILES CALCULATOR DEMOSU (Faz 1 başındaki for/while hatırlatması) ---

        System.out.println("--- Klasik MilesCalculator (tek uçuş) ---"); // bölüm başlığı
        MilesCalculator calculator = new MilesCalculator(); // klasik hesaplayıcı nesnesi
        int milesForTk1903 = calculator.calculateMiles(flight2); // TK1903 için mil hesapla
        System.out.println(flight2.getFlightNumber() + " toplam mil: " + milesForTk1903); // getFlightNumber = Lombok @Data
        System.out.println(); // boş satır

        System.out.println("=== Uygulama tamamlandı ==="); // bitiş mesajını yazdır

    } // main metodunun kapanış süslü parantezi

} // Main sınıfının kapanış süslü parantezi
