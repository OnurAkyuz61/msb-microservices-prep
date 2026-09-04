// Bu dosyanın hangi pakette (klasör yolunda) yaşadığını Java'ya bildirir
package com.thy.miles.strategy; // strategy: mil hesaplama kurallarının paketi

// model paketindeki Flight sınıfını kullanmak için import ederiz
import com.thy.miles.model.Flight; // Flight: hesaplanacak uçuş nesnesi

// EliteMemberMilesCalculator: Elite üye için daha yüksek mil çarpanı kullanan strateji
public class EliteMemberMilesCalculator implements MilesCalculatorStrategy { // interface'i uygular

    // Elite üye için temel mil katsayısı (klasik üyeden daha yüksek)
    private static final int BASE_MILES_PER_KM = 2; // her 1 km için 2 mil

    // Elite + business class için ekstra çarpan
    private static final double BUSINESS_MULTIPLIER = 2.0; // business'ta 2 kat mil

    // Elite üyeye uzun uçuş eşiği
    private static final int LONG_FLIGHT_THRESHOLD = 2000; // 2000 km üzeri uzun uçuş

    // Elite üyeye uzun uçuş bonusu (klasikten daha yüksek)
    private static final int LONG_FLIGHT_BONUS = 1000; // uzun uçuşa ekstra 1000 mil

    // Strategy arayüzündeki metodun elite üye uygulaması
    @Override // bu metodun interface'ten geldiğini belirtir
    public int calculateMiles(Flight flight) { // parametre: hesaplanacak uçuş nesnesi
        int distance = flight.getDistanceKm(); // mesafe bilgisini alır
        boolean isBusiness = flight.isBusinessClass(); // business bilgisini alır

        int baseMiles = distance * BASE_MILES_PER_KM; // elite temel mil hesabı
        double totalMiles = baseMiles; // başlangıç toplamı

        if (isBusiness) { // business class ise
            totalMiles = baseMiles * BUSINESS_MULTIPLIER; // elite business çarpanı uygula
            System.out.println("Elite üye | Business class: mil x " + BUSINESS_MULTIPLIER); // bilgi yazdır
        } else { // ekonomi ise
            totalMiles = baseMiles; // sadece elite temel katsayı
            System.out.println("Elite üye | Ekonomi sınıfı: km x " + BASE_MILES_PER_KM); // bilgi yazdır
        } // if-else kapanışı

        if (distance > LONG_FLIGHT_THRESHOLD) { // uzun uçuş kontrolü
            totalMiles = totalMiles + LONG_FLIGHT_BONUS; // elite bonus ekle
            System.out.println("Elite üye | Uzun uçuş bonusu eklendi: +" + LONG_FLIGHT_BONUS); // bilgi yazdır
        } // if kapanışı

        int result = (int) totalMiles; // int'e çevir
        return result; // sonucu döndür
    } // calculateMiles metodunun kapanış süslü parantezi

} // EliteMemberMilesCalculator sınıfının kapanış süslü parantezi
