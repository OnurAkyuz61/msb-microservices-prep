// Bu dosyanın hangi pakette (klasör yolunda) yaşadığını Java'ya bildirir
package com.msb.flightmiles.model; // model: veri / varlık (entity) sınıflarının paketi

// Lombok: @AllArgsConstructor anotasyonunu içeri alır
import lombok.AllArgsConstructor; // derleme sırasında tüm alanları alan constructor üretir
// Lombok: @Builder anotasyonunu içeri alır
import lombok.Builder; // Flight.builder()...build() ile okunaklı nesne üretimi sağlar
// Lombok: @Data anotasyonunu içeri alır
import lombok.Data; // getter, setter, toString, equals, hashCode kodlarını otomatik üretir
// Lombok: @NoArgsConstructor anotasyonunu içeri alır
import lombok.NoArgsConstructor; // parametresiz boş constructor üretir

// @Data: arka planda getX/setX, toString, equals ve hashCode metotlarını yazar (elle yazmaya gerek kalmaz)
@Data // sınıf seviyesinde: tüm alanlar için boilerplate kodu derleme anında üretir
// @NoArgsConstructor: new Flight() şeklinde parametresiz nesne oluşturmayı mümkün kılar
@NoArgsConstructor // boş constructor: framework'ler (ileride JPA/Spring) için sık gerekir
// @AllArgsConstructor: tüm alanları parametre alan constructor üretir (flightNumber, destination, ...)
@AllArgsConstructor // dolu constructor: tüm private alanları tek seferde set eder
// @Builder: Builder deseni üretir; Flight.builder().flightNumber("TK101").build() yazılabilir
@Builder // zincirleme (fluent) API ile alan alan nesne kurmayı sağlar
// Flight sınıfı: Bir uçuşa ait bilgileri tutmak için kullanılır
public class Flight { // Flight adında bir sınıf (class) tanımlıyoruz

    // --- DEĞİŞKENLER (sınıfın alanları / fields) ---
    // Not: getter/setter/constructor artık Lombok tarafından üretilir; burada sadece alanlar kalır

    // Uçuş numarasını metin (String) olarak saklar, örn: "TK1903"
    private String flightNumber; // @Data sayesinde getFlightNumber() / setFlightNumber(...) oluşur

    // Uçuşun varış noktasını metin olarak saklar, örn: "Istanbul"
    private String destination; // @Data sayesinde getDestination() / setDestination(...) oluşur

    // Uçuş mesafesini kilometre cinsinden tam sayı (int) olarak saklar
    private int distanceKm; // @Data sayesinde getDistanceKm() / setDistanceKm(...) oluşur

    // Uçuşun business class olup olmadığını true/false (boolean) olarak saklar
    private boolean businessClass; // boolean için @Data genelde isBusinessClass() üretir

    // --- BİLGİ YAZDIRMA METODU (iş mantığı; Lombok bunu üretmez, elle yazarız) ---

    // Uçuş bilgilerini ekrana (konsola) yazdıran basit bir metot
    public void printFlightInfo() { // void: ekrana yazar, değer döndürmez
        System.out.println("Uçuş No   : " + flightNumber); // uçuş numarasını yazdırır
        System.out.println("Varış     : " + destination); // varış noktasını yazdırır
        System.out.println("Mesafe    : " + distanceKm + " km"); // mesafeyi km ile yazdırır
        System.out.println("Business  : " + businessClass); // business durumunu yazdırır
    } // printFlightInfo metodunun kapanış süslü parantezi

} // Flight sınıfının kapanış süslü parantezi
