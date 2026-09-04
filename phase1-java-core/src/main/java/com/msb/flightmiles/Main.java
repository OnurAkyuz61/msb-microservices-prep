// Bu dosyanın hangi pakette (klasör yolunda) yaşadığını Java'ya bildirir
package com.msb.flightmiles; // uygulama kök paketi: giriş noktası burada

// model paketinden Flight sınıfını kullanmak için import ederiz
import com.msb.flightmiles.model.Flight; // Flight: uçuş bilgisi tutan sınıf
// service paketinden MilesCalculator sınıfını kullanmak için import ederiz
import com.msb.flightmiles.service.MilesCalculator; // MilesCalculator: mil hesaplayan sınıf

// Main sınıfı: Uygulamanın başlangıç noktasıdır (program buradan çalışır)
public class Main { // Main adında bir sınıf tanımlıyoruz

    // main metodu: Java programı çalıştırıldığında ilk burası çalışır
    public static void main(String[] args) { // static: nesne oluşturmadan çağrılabilir; args: komut satırı argümanları

        System.out.println("=== Uçuş ve Mil Hesaplama Uygulaması ==="); // başlık satırını yazdır
        System.out.println(); // boş bir satır bırak (okunabilirlik için)

        // --- Flight NESNELERİ OLUŞTURMA (object creation) ---

        // 1. uçuş: kısa mesafe, ekonomi sınıfı
        Flight flight1 = new Flight("TK101", 850, false); // new: bellekte yeni bir Flight nesnesi yaratır
        // 2. uçuş: uzun mesafe, business sınıfı
        Flight flight2 = new Flight("TK250", 3200, true); // uçuş no TK250, 3200 km, business = true
        // 3. uçuş: orta mesafe, ekonomi sınıfı
        Flight flight3 = new Flight("PC404", 2100, false); // uçuş no PC404, 2100 km, business = false

        System.out.println("--- Uçuş Bilgileri ---"); // bölüm başlığı yazdır
        flight1.printFlightInfo(); // 1. uçuşun bilgilerini ekrana yaz
        System.out.println(); // boş satır
        flight2.printFlightInfo(); // 2. uçuşun bilgilerini ekrana yaz
        System.out.println(); // boş satır
        flight3.printFlightInfo(); // 3. uçuşun bilgilerini ekrana yaz
        System.out.println(); // boş satır

        // --- MilesCalculator NESNESİ OLUŞTURMA ---

        // Mil hesaplayıcı sınıfından bir nesne üret
        MilesCalculator calculator = new MilesCalculator(); // new ile MilesCalculator nesnesi oluştur

        System.out.println("--- Tek Uçuş Mil Hesabı ---"); // bölüm başlığı
        int miles1 = calculator.calculateMiles(flight1); // flight1 için mil hesapla ve miles1'e kaydet
        System.out.println(flight1.getFlightNumber() + " toplam mil: " + miles1); // sonucu yazdır
        System.out.println(); // boş satır

        int miles2 = calculator.calculateMiles(flight2); // flight2 için mil hesapla
        System.out.println(flight2.getFlightNumber() + " toplam mil: " + miles2); // sonucu yazdır
        System.out.println(); // boş satır

        // --- DİZİ (array) İLE BİRDEN FAZLA UÇUŞ ---

        // Üç uçuşu bir dizi içinde tutuyoruz
        Flight[] flights = new Flight[3]; // 3 elemanlı Flight dizisi oluştur
        flights[0] = flight1; // dizinin 0. indexine flight1'i koy
        flights[1] = flight2; // dizinin 1. indexine flight2'yi koy
        flights[2] = flight3; // dizinin 2. indexine flight3'ü koy

        System.out.println("--- for Döngüsü ile Toplam Mil ---"); // bölüm başlığı
        int totalWithFor = calculator.calculateTotalMilesWithFor(flights); // for ile toplam mil hesapla
        System.out.println("FOR sonucu - Toplam mil: " + totalWithFor); // for sonucunu yazdır
        System.out.println(); // boş satır

        System.out.println("--- while Döngüsü ile Toplam Mil ---"); // bölüm başlığı
        int totalWithWhile = calculator.calculateTotalMilesWithWhile(flights); // while ile toplam mil hesapla
        System.out.println("WHILE sonucu - Toplam mil: " + totalWithWhile); // while sonucunu yazdır
        System.out.println(); // boş satır

        System.out.println("=== Uygulama tamamlandı ==="); // bitiş mesajını yazdır

    } // main metodunun kapanış süslü parantezi

} // Main sınıfının kapanış süslü parantezi
