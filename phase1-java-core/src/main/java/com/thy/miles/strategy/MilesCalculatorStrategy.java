// Bu dosyanın hangi pakette (klasör yolunda) yaşadığını Java'ya bildirir
package com.thy.miles.strategy; // strategy: mil hesaplama kurallarının (stratejilerin) paketi

// model paketindeki Flight sınıfını kullanmak için import ederiz
import com.thy.miles.model.Flight; // Flight: hesaplamaya girdi olan uçuş verisi

// MilesCalculatorStrategy: Farklı üye tipleri için mil hesabı sözleşmesi (interface)
public interface MilesCalculatorStrategy { // interface: "ne yapılacağını" tanımlar, "nasıl"ı sınıflar yazar

    // Verilen uçuş için kazanılacak mili hesaplar
    int calculateMiles(Flight flight); // her strateji sınıfı bu metodu kendi kuralıyla uygular

} // MilesCalculatorStrategy arayüzünün kapanış süslü parantezi
