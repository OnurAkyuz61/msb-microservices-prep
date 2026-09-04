// Bu sınıfın paketi: HTTP isteklerini karşılayan controller (API) katmanı
package com.thy.miles.controller; // controller: dış dünyaya açılan REST uçları burada

// List: uçuş listesi dönüş tipi için
import java.util.List; // getAllFlights cevabı

// model paketinden Flight'ı import ederiz
import com.thy.miles.model.Flight; // JSON gövdesinde gidecek / gelecek model
// service paketinden FlightService'i import ederiz
import com.thy.miles.service.FlightService; // iş mantığını çağıracağımız Bean

// Lombok: final alanlar için constructor üretir (DI için ideal)
import lombok.RequiredArgsConstructor; // @RequiredArgsConstructor
// Spring HTTP: 201 Created gibi durum kodlarını işaretlemek için
import org.springframework.http.HttpStatus; // HttpStatus.CREATED
// Spring Web: HTTP GET eşlemesi
import org.springframework.web.bind.annotation.GetMapping; // @GetMapping
// Spring Web: HTTP POST eşlemesi
import org.springframework.web.bind.annotation.PostMapping; // @PostMapping
// Spring Web: HTTP istek gövdesini Java nesnesine bağlar
import org.springframework.web.bind.annotation.RequestBody; // @RequestBody
// Spring Web: sınıf seviyesinde ortak URL yolu
import org.springframework.web.bind.annotation.RequestMapping; // @RequestMapping
// Spring Web: metodun HTTP cevap durum kodunu belirler
import org.springframework.web.bind.annotation.ResponseStatus; // @ResponseStatus
// Spring Web: REST controller işaretçisi
import org.springframework.web.bind.annotation.RestController; // @RestController

/*
 * @RestController Spring Boot ekosisteminde ne işe yarar?
 * - @Controller + @ResponseBody birleşimidir.
 * - Metodun dönüş değeri View (HTML) değil; doğrudan HTTP yanıt gövdesi (genelde JSON) olur.
 * - Spring, dönüşü Jackson ile otomatik JSON'a çevirir.
 * - Bu sınıf da bir Spring Bean'dir; Component Scan ile container'a girer.
 */
@RestController // Spring: bu sınıf REST API endpoint'leri yayınlar (JSON döner)
/*
 * @RequestMapping("/api/v1/flights") sınıf seviyesinde ortak URL önekini tanımlar.
 * Bu controller'daki tüm metotların yolu /api/v1/flights ile başlar.
 * Versiyonlama (v1) ileride API değişince eski istemcileri kırmamayı kolaylaştırır.
 */
@RequestMapping("/api/v1/flights") // Spring: bu controller'ın temel HTTP yolu
/*
 * @RequiredArgsConstructor (Lombok) Dependency Injection için ne işe yarar?
 * - final (veya @NonNull) alanlar için otomatik constructor üretir.
 * - Spring, üretilen constructor üzerinden FlightService Bean'ini enjekte eder
 *   (Constructor Injection). Bu, @Autowired alan enjeksiyonundan daha tercih edilen yaklaşımdır:
 *   zorunlu bağımlılıklar netleşir, test yazmak kolaylaşır, Bean döngüleri daha görünür olur.
 */
@RequiredArgsConstructor // Lombok: final alanlar için constructor üretir -> Spring DI bu constructor'ı kullanır
// FlightController: Uçuşları HTTP üzerinden dışarıya sunan REST controller
public class FlightController { // public class: DispatcherServlet bu Bean'e istek yönlendirir

    // flightService: iş mantığı Bean'i (final = constructor injection ile set edilir, sonra değişmez)
    private final FlightService flightService; // Spring, FlightService Bean'ini buraya enjekte eder

    /*
     * @GetMapping Spring Boot ekosisteminde ne işe yarar?
     * - HTTP GET isteklerini bu metoda bağlar.
     * - Sınıf seviyesindeki @RequestMapping ile birleşince tam yol: GET /api/v1/flights
     * - Tarayıcı veya curl ile bu URL çağrıldığında getAllFlights() çalışır.
     * - Dönüş List<Flight> olduğu için Spring otomatik olarak JSON dizi cevabı üretir.
     */
    @GetMapping // Spring: HTTP GET /api/v1/flights -> bu metot
    // getAllFlights: Servisteki uçuş listesini istemciye döner
    public List<Flight> getAllFlights() { // dönüş tipi JSON body olur (@RestController sayesinde)
        return flightService.getAllFlights(); // DI ile gelen service Bean'ini çağır, listeyi ilet
    } // getAllFlights metodunun kapanış süslü parantezi

    /*
     * @PostMapping Spring Boot ekosisteminde ne işe yarar?
     * - HTTP POST isteklerini bu metoda bağlar (yeni kaynak oluşturma için REST standardı).
     * - Tam yol: POST /api/v1/flights
     * - GET'ten farkı: istemci istek gövdesinde (body) veri gönderir; sunucu yeni kayıt üretir.
     */
    @PostMapping // Spring: HTTP POST /api/v1/flights -> bu metot
    /*
     * @ResponseStatus(HttpStatus.CREATED) Spring Boot ekosisteminde ne işe yarar?
     * - Metod başarıyla bittiğinde HTTP durum kodunu 201 Created yapar.
     * - REST'te yeni kaynak oluşturulduğunda 200 yerine 201 dönmek kurumsal best practice'tir.
     * - Varsayılan @RestController dönüşü 200 OK olurdu; bu anotasyon onu override eder.
     */
    @ResponseStatus(HttpStatus.CREATED) // Spring: başarılı cevapta 201 Created statüsü gönder
    // createFlight: JSON body'den gelen uçuşu kaydeder ve oluşturulan nesneyi döner
    public Flight createFlight(
            /*
             * @RequestBody Spring Boot ekosisteminde ne işe yarar?
             * - HTTP isteğinin gövdesindeki JSON'ı, belirtilen Java tipine (Flight) çevirir.
             * - Jackson kütüphanesi JSON alanlarını Flight property'lerine map eder
             *   (ör. "flightNumber" -> setFlightNumber / builder alanı).
             * - Olmadan parametre URL query/path'ten okunmaya çalışılır; body okunmaz.
             */
            @RequestBody Flight flight) { // Spring: gelen JSON -> Flight nesnesi
        return flightService.addFlight(flight); // serviste doğrula + ekle; sonucu JSON olarak dön
    } // createFlight metodunun kapanış süslü parantezi

} // FlightController sınıfının kapanış süslü parantezi
