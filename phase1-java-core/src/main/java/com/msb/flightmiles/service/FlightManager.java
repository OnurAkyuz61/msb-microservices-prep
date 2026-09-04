// Bu dosyanın hangi pakette (klasör yolunda) yaşadığını Java'ya bildirir
package com.msb.flightmiles.service; // service: iş kuralları / yönetim sınıflarının paketi

// ArrayList: sıralı, tekrarlı eleman tutabilen List uygulamasıdır
import java.util.ArrayList; // List arayüzünün en sık kullanılan somut sınıfı
// Collectors: Stream sonuçlarını List/Set gibi koleksiyonlara çevirir
import java.util.stream.Collectors; // collect(...) içinde kullanılır
// HashMap: anahtar-değer çiftlerini tutan Map uygulamasıdır
import java.util.HashMap; // uçuş no -> Flight eşlemesi için
// HashSet: tekrarsız eleman tutan Set uygulamasıdır
import java.util.HashSet; // varış noktalarını tekrarsız saklamak için
// List: sıralı koleksiyon arayüzü (interface)
import java.util.List; // tüm uçuşları liste halinde tutmak için
// Map: anahtar ile değer eşleyen koleksiyon arayüzü
import java.util.Map; // uçuş numarasına göre hızlı arama için
// Set: tekrarsız eleman koleksiyonu arayüzü
import java.util.Set; // benzersiz destinasyonlar için

// model paketindeki Flight sınıfını bu dosyada kullanmak için import ederiz
import com.msb.flightmiles.model.Flight; // Flight: Lombok @Data ile getter'ları üretilmiş model sınıfı

// FlightManager sınıfı: uçuşları List / Set / Map ile yönetir ve Stream API kullanır
// Not: Flight nesnesi burada üretilmez; Main Lombok @Builder ile üretir, biz sadece getter kullanırız
public class FlightManager { // FlightManager adında bir sınıf tanımlıyoruz

    // Sistemdeki tüm uçuşları sırayla tutan liste (List = sıralı koleksiyon)
    private List<Flight> flights; // ArrayList ile doldurulacak alan

    // Benzersiz (tekrarsız) varış noktalarını tutan küme (Set = tekrar kabul etmez)
    private Set<String> destinations; // HashSet ile doldurulacak alan

    // Uçuş numarasına göre Flight nesnesini hızlı bulmak için harita (Map = anahtar -> değer)
    private Map<String, Flight> flightsByNumber; // HashMap ile doldurulacak alan

    // Mil hesabında mevcut iş kurallarını yeniden kullanmak için hesaplayıcı
    private MilesCalculator milesCalculator; // business / bonus kurallarını uygular

    // --- CONSTRUCTOR: Manager oluşturulurken boş koleksiyonlar hazırlanır ---

    // Yeni bir FlightManager nesnesi yaratıldığında boş List/Set/Map oluşturur
    public FlightManager() { // parametresiz yapıcı metot
        this.flights = new ArrayList<>(); // boş bir ArrayList başlatır
        this.destinations = new HashSet<>(); // boş bir HashSet başlatır
        this.flightsByNumber = new HashMap<>(); // boş bir HashMap başlatır
        this.milesCalculator = new MilesCalculator(); // mil hesaplayıcı nesnesini oluşturur
    } // constructor metodunun kapanış süslü parantezi

    // --- UÇUŞ EKLEME: Üç koleksiyonu birden günceller ---

    // Verilen Flight nesnesini List, Set ve Map yapılarına ekler
    public void addFlight(Flight flight) { // parametre: eklenecek uçuş
        flights.add(flight); // List'e uçuşu sıranın sonuna ekler
        destinations.add(flight.getDestination()); // Set'e varışı ekler (aynı şehir tekrar eklenmez)
        flightsByNumber.put(flight.getFlightNumber(), flight); // Map'e "uçuşNo -> Flight" yazar
    } // addFlight metodunun kapanış süslü parantezi

    // --- GETTER METOTLARI: Koleksiyonları dışarıdan okumak için ---

    // Tüm uçuş listesini dışarıya döndürür
    public List<Flight> getFlights() { // dönüş tipi List<Flight>
        return flights; // flights listesinin referansını geri verir
    } // getFlights metodunun kapanış süslü parantezi

    // Benzersiz varış noktaları kümesini dışarıya döndürür
    public Set<String> getDestinations() { // dönüş tipi Set<String>
        return destinations; // destinations setinin referansını geri verir
    } // getDestinations metodunun kapanış süslü parantezi

    // Uçuş numarasına göre Map üzerinden Flight arar
    public Flight findFlightByNumber(String flightNumber) { // parametre: aranan uçuş no
        return flightsByNumber.get(flightNumber); // Map'te anahtar yoksa null döner
    } // findFlightByNumber metodunun kapanış süslü parantezi

    // --- STREAM API METOTLARI ---

    // Sadece Business Class olan uçuşları filtreleyip yeni bir List olarak döndürür
    public List<Flight> getBusinessClassFlights() { // dönüş: business uçuşların listesi
        return flights // listedeki tüm uçuşlardan başla
                .stream() // Stream API: koleksiyonu "akış" haline getirir (eleman eleman işlemek için)
                .filter(flight -> flight.isBusinessClass()) // filter: sadece businessClass == true olanları geçirir
                .collect(Collectors.toList()); // collect: akıştaki kalan elemanları yeni bir List'e toplar
    } // getBusinessClassFlights metodunun kapanış süslü parantezi

    // Listedeki tüm uçuşların mil değerlerini toplayıp tek bir int sonuç döndürür
    public int calculateTotalMilesWithStream() { // dönüş: toplam mil (int)
        return flights // listedeki tüm uçuşlardan başla
                .stream() // Stream API: uçuşları akışa çevirir
                .mapToInt(flight -> milesCalculator.calculateMiles(flight)) // mapToInt: her Flight'ı int mile çevirir (IntStream üretir)
                .sum(); // sum: IntStream içindeki tüm int değerleri toplar
    } // calculateTotalMilesWithStream metodunun kapanış süslü parantezi

    // Tüm uçuşları konsola yazdıran yardımcı metot (öğrenme / test için)
    public void printAllFlights() { // void: sadece ekrana yazar
        System.out.println("Toplam uçuş sayısı: " + flights.size()); // List'in eleman sayısını yazdırır
        // for-each: listedeki her Flight için bir kez döner
        for (Flight flight : flights) { // flights listesini gezer
            flight.printFlightInfo(); // her uçuşun bilgilerini yazdırır
            System.out.println("-----"); // uçuşlar arasına ayırıcı çizer
        } // for-each döngüsünün kapanış süslü parantezi
    } // printAllFlights metodunun kapanış süslü parantezi

    // Benzersiz varış noktalarını konsola yazdıran yardımcı metot
    public void printDestinations() { // void: sadece ekrana yazar
        System.out.println("Benzersiz varış noktaları (Set): " + destinations); // Set içeriğini yazdırır
    } // printDestinations metodunun kapanış süslü parantezi

} // FlightManager sınıfının kapanış süslü parantezi
