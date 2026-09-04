// Bu sınıfın paketi: iş mantığı (business logic) servis katmanı
package com.thy.miles.service; // service: Controller ile veri/kurallar arasında köprü

// ArrayList: sahte uçuş listesini tutmak için
import java.util.ArrayList; // List uygulaması
// List: uçuş koleksiyonunun arayüz tipi
import java.util.List; // getAllFlights dönüş tipi

// model paketindeki Flight sınıfını import ederiz
import com.thy.miles.model.Flight; // Flight: döndüreceğimiz model

// @Service anotasyonunu Spring'ten alırız
import org.springframework.stereotype.Service; // Bean olarak kaydetmek için

/*
 * @Service Spring Boot ekosisteminde ne işe yarar?
 * - Bu sınıfı Spring IoC (Inversion of Control) Container'a "Service Bean" olarak kaydeder.
 * - @Component'in özel (stereotype) bir türüdür; iş mantığı katmanını işaretler.
 * - Başka sınıflar (ör. FlightController) bu Bean'i Dependency Injection ile alabilir.
 * - Uygulama ayağa kalkarken Component Scan (@SpringBootApplication) bu sınıfı otomatik bulur.
 */
@Service // Spring: "bu bir servis Bean'idir" der; DI ile enjekte edilebilir hale getirir
// FlightService: Uçuş listesini sağlayan iş mantığı sınıfı
public class FlightService { // public class: controller tarafından kullanılır

    // getAllFlights: Şimdilik veritabanı yok; sahte (mock) uçuş listesi döner
    public List<Flight> getAllFlights() { // dönüş: List<Flight> (JSON dizisine çevrilecek)
        List<Flight> flights = new ArrayList<>(); // boş liste oluştur

        // 1. sahte uçuş: ekonomi, kısa mesafe
        Flight flight1 = Flight.builder() // Lombok @Builder ile nesne kur
                .flightNumber("TK101") // uçuş no
                .distanceKm(850) // mesafe km
                .businessClass(false) // ekonomi
                .build(); // Flight nesnesini üret

        // 2. sahte uçuş: business, uzun mesafe
        Flight flight2 = Flight.builder() // Builder başlat
                .flightNumber("TK1903") // uçuş no
                .distanceKm(3200) // mesafe km
                .businessClass(true) // business
                .build(); // Flight nesnesini üret

        // 3. sahte uçuş: ekonomi, orta mesafe
        Flight flight3 = Flight.builder() // Builder başlat
                .flightNumber("PC404") // uçuş no
                .distanceKm(2100) // mesafe km
                .businessClass(false) // ekonomi
                .build(); // Flight nesnesini üret

        flights.add(flight1); // 1. uçuşu listeye ekle
        flights.add(flight2); // 2. uçuşu listeye ekle
        flights.add(flight3); // 3. uçuşu listeye ekle

        return flights; // mock listeyi çağırana (Controller) geri ver
    } // getAllFlights metodunun kapanış süslü parantezi

} // FlightService sınıfının kapanış süslü parantezi
