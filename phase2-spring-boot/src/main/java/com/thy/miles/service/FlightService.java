// Bu sınıfın paketi: iş mantığı (business logic) servis katmanı
package com.thy.miles.service; // service: Controller ile veri/kurallar arasında köprü

// ArrayList: bellek içi uçuş listesini tutmak için
import java.util.ArrayList; // List uygulaması
// List: uçuş koleksiyonunun arayüz tipi
import java.util.List; // getAllFlights / addFlight için

// model paketindeki Flight sınıfını import ederiz
import com.thy.miles.model.Flight; // Flight: ekleyip döndüreceğimiz model

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
// FlightService: Uçuş listesini yöneten iş mantığı sınıfı (şimdilik bellek içi mock depo)
public class FlightService { // public class: controller tarafından kullanılır

    // flights: uygulama ayaktayken yaşayan bellek içi liste (veritabanı yerine)
    private final List<Flight> flights = new ArrayList<>(); // final: referans değişmez, içerik değişebilir

    // Yapıcı metot: servis Bean'i oluşurken başlangıç mock verilerini yükler
    public FlightService() { // Spring, Bean yaratırken bu constructor'ı çağırır
        flights.add(Flight.builder() // 1. sahte uçuş
                .flightNumber("TK101") // uçuş no
                .distanceKm(850) // mesafe km
                .businessClass(false) // ekonomi
                .build()); // Flight nesnesini üret ve listeye ekle
        flights.add(Flight.builder() // 2. sahte uçuş
                .flightNumber("TK1903") // uçuş no
                .distanceKm(3200) // mesafe km
                .businessClass(true) // business
                .build()); // Flight nesnesini üret ve listeye ekle
        flights.add(Flight.builder() // 3. sahte uçuş
                .flightNumber("PC404") // uçuş no
                .distanceKm(2100) // mesafe km
                .businessClass(false) // ekonomi
                .build()); // Flight nesnesini üret ve listeye ekle
    } // constructor kapanışı

    // getAllFlights: bellek içindeki tüm uçuşları döner
    public List<Flight> getAllFlights() { // dönüş: List<Flight> (JSON dizisine çevrilecek)
        return flights; // aynı listeyi döndür (POST ile eklenenler de görünür)
    } // getAllFlights metodunun kapanış süslü parantezi

    // addFlight: dışarıdan gelen Flight'ı doğrulayıp listeye ekler
    public Flight addFlight(Flight flight) { // parametre: kaydedilecek uçuş; dönüş: kaydedilen nesne
        // Kurumsal kural: mesafe 0 veya negatif olamaz
        if (flight.getDistanceKm() <= 0) { // geçersiz mesafe kontrolü
            // IllegalArgumentException: yanlış argüman verildiğinde fırlatılan standart Java hatası
            throw new IllegalArgumentException("Geçersiz mesafe"); // GlobalExceptionHandler bunu yakalayacak
        } // if bloğunun kapanışı
        flights.add(flight); // doğrulama geçtiyse listeye ekle
        return flight; // eklenen uçuşu geri döndür (201 cevabının gövdesi olur)
    } // addFlight metodunun kapanış süslü parantezi

} // FlightService sınıfının kapanış süslü parantezi
