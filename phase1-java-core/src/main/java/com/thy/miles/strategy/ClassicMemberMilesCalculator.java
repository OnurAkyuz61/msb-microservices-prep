// Bu dosyanın hangi pakette (klasör yolunda) yaşadığını Java'ya bildirir
package com.thy.miles.strategy; // strategy: mil hesaplama kurallarının paketi

// model paketindeki Flight sınıfını kullanmak için import ederiz
import com.thy.miles.model.Flight; // Flight: hesaplanacak uçuş nesnesi

// ClassicMemberMilesCalculator: Klasik üye için mil hesabı (eski MilesCalculator kuralları)
public class ClassicMemberMilesCalculator implements MilesCalculatorStrategy { // interface'i uygular (implements)

    // Temel mil katsayısı: her 1 km için kaç mil verileceğini tutar
    private static final int BASE_MILES_PER_KM = 1; // static final: sabit değer, değişmez

    // Business class için ekstra çarpan (örneğin 1.5 kat mil)
    private static final double BUSINESS_MULTIPLIER = 1.5; // business yolcuya daha fazla mil

    // Uzun uçuş eşiği: bu km'nin üstü "uzun uçuş" sayılır
    private static final int LONG_FLIGHT_THRESHOLD = 2000; // 2000 km üzeri uzun uçuş

    // Uzun uçuşlara eklenen bonus mil miktarı
    private static final int LONG_FLIGHT_BONUS = 500; // uzun uçuşa ekstra 500 mil

    // --- TEK BİR UÇUŞ İÇİN MİL HESAPLAMA (if / else kullanır) ---

    // Strategy arayüzündeki metodun klasik üye uygulaması
    @Override // bu metodun interface'ten geldiğini belirtir
    public int calculateMiles(Flight flight) { // parametre: hesaplanacak uçuş nesnesi
        int distance = flight.getDistanceKm(); // uçuşun mesafe bilgisini alıp distance değişkenine koyar
        boolean isBusiness = flight.isBusinessClass(); // business class mı bilgisini alır

        int baseMiles = distance * BASE_MILES_PER_KM; // mesafe x katsayı = temel mil
        double totalMiles = baseMiles; // şimdilik toplam mil = temel mil (double: ondalıklı olabilir)

        // if / else: Business class ise çarpan uygula, değilse ekonomi kurallarını kullan
        if (isBusiness) { // eğer yolcu business class ise
            totalMiles = baseMiles * BUSINESS_MULTIPLIER; // temel mili 1.5 ile çarp
            System.out.println("Classic üye | Business class: mil x " + BUSINESS_MULTIPLIER); // bilgi mesajı yazdır
        } else { // değilse (ekonomi sınıfı)
            totalMiles = baseMiles; // ekonomi için ekstra çarpan yok
            System.out.println("Classic üye | Ekonomi sınıfı: normal mil uygulanır"); // bilgi mesajı yazdır
        } // if-else bloğunun kapanış süslü parantezi

        // if: Mesafe uzun uçuş eşiğini aşıyorsa bonus mil ekle
        if (distance > LONG_FLIGHT_THRESHOLD) { // mesafe 2000 km'den büyük mü?
            totalMiles = totalMiles + LONG_FLIGHT_BONUS; // toplam mile 500 bonus ekle
            System.out.println("Classic üye | Uzun uçuş bonusu eklendi: +" + LONG_FLIGHT_BONUS); // bonus bilgisini yazdır
        } // uzun uçuş if bloğunun kapanış süslü parantezi

        int result = (int) totalMiles; // double değeri int'e çevir (ondalık kısmı atar)
        return result; // hesaplanan mil sonucunu çağıran yere geri döndür
    } // calculateMiles metodunun kapanış süslü parantezi

    // --- BİRDEN FAZLA UÇUŞ İÇİN TOPLAM MİL (for döngüsü kullanır) ---

    // Flight dizisindeki (array) tüm uçuşların millerini toplayıp döndürür
    public int calculateTotalMilesWithFor(Flight[] flights) { // parametre: Flight nesnelerinden oluşan dizi
        int total = 0; // toplam mil sayacını 0'dan başlat

        // for döngüsü: dizinin her elemanı için bir kez çalışır
        for (int i = 0; i < flights.length; i++) { // i = 0'dan başla, dizi bitene kadar 1 artır
            Flight current = flights[i]; // sıradaki (i. indexteki) Flight nesnesini al
            int miles = calculateMiles(current); // bu uçuş için mili hesapla
            total = total + miles; // hesaplanan mili toplama ekle
            System.out.println(current.getFlightNumber() + " -> " + miles + " mil"); // uçuşun milini yazdır
        } // for döngüsünün kapanış süslü parantezi

        return total; // tüm uçuşların toplam milini geri döndür
    } // calculateTotalMilesWithFor metodunun kapanış süslü parantezi

    // --- BİRDEN FAZLA UÇUŞ İÇİN TOPLAM MİL (while döngüsü kullanır) ---

    // Aynı toplamı while döngüsü ile hesaplayan alternatif metot
    public int calculateTotalMilesWithWhile(Flight[] flights) { // parametre: Flight dizisi
        int total = 0; // toplam mil sayacını 0'dan başlat
        int index = 0; // while için elle yöneteceğimiz index değişkeni

        // while döngüsü: index, dizinin uzunluğundan küçük olduğu sürece devam eder
        while (index < flights.length) { // henüz dizi bitmedi mi?
            Flight current = flights[index]; // o anki indexteki Flight nesnesini al
            int miles = calculateMiles(current); // bu uçuş için mili hesapla
            total = total + miles; // hesaplanan mili toplama ekle
            System.out.println(current.getFlightNumber() + " -> " + miles + " mil"); // sonucu yazdır
            index = index + 1; // index'i 1 artır (bir sonraki elemana geç)
        } // while döngüsünün kapanış süslü parantezi

        return total; // toplam mili geri döndür
    } // calculateTotalMilesWithWhile metodunun kapanış süslü parantezi

} // ClassicMemberMilesCalculator sınıfının kapanış süslü parantezi
