// Bu sınıfın paketi: veri / model (entity benzeri DTO) sınıfları burada tutulur
package com.thy.miles.model; // model: API'de JSON'a serileştirilecek uçuş verisi

// Lombok: tüm alanları alan constructor üretir
import lombok.AllArgsConstructor; // @AllArgsConstructor için gerekli import
// Lombok: Builder deseni üretir
import lombok.Builder; // Flight.builder()...build() için
// Lombok: getter/setter/toString/equals/hashCode üretir
import lombok.Data; // @Data için gerekli import
// Lombok: parametresiz constructor üretir
import lombok.NoArgsConstructor; // Jackson / Spring'in nesne yaratması için sık gerekir

/*
 * @Data (Lombok): Derleme anında getFlightNumber(), setFlightNumber(...), toString(),
 * equals() ve hashCode() metotlarını otomatik üretir; elle boilerplate yazmaya gerek kalmaz.
 */
@Data // Lombok: alanlar için getter/setter ve yardımcı metotları üretir
/*
 * @NoArgsConstructor: new Flight() ile boş nesne yaratmayı sağlar.
 * Spring / Jackson JSON -> Java dönüşümünde genelde boş constructor ister.
 */
@NoArgsConstructor // Lombok: parametresiz constructor
/*
 * @AllArgsConstructor: Tüm alanları parametre alan constructor üretir
 * (flightNumber, distanceKm, businessClass).
 */
@AllArgsConstructor // Lombok: tüm alanları alan constructor
/*
 * @Builder: Okunaklı nesne üretimi sağlar:
 * Flight.builder().flightNumber("TK101").distanceKm(850).businessClass(false).build();
 */
@Builder // Lombok: Builder deseni (fluent API)
// Flight: Uçuş bilgilerini taşıyan model sınıfı (REST cevabında JSON olur)
public class Flight { // public class: controller/service katmanlarından erişilir

    // Uçuş numarası (ör. "TK1903")
    private String flightNumber; // String: metin tipinde uçuş kodu

    // Uçuş mesafesi (kilometre)
    private int distanceKm; // int: tam sayı mesafe

    // Business class mı? true = business, false = ekonomi
    private boolean businessClass; // boolean: kabin sınıfı bilgisi

} // Flight sınıfının kapanış süslü parantezi
