// Bu dosyanın hangi pakette (klasör yolunda) yaşadığını Java'ya bildirir
package com.thy.miles.exception; // exception: özel hata (exception) sınıflarının paketi

// InvalidFlightDataException: Geçersiz uçuş verisi durumunda fırlatılan özel hata sınıfı
public class InvalidFlightDataException extends RuntimeException { // RuntimeException: kontrolsüz (unchecked) hata

    // Hata mesajını alan yapıcı metot
    public InvalidFlightDataException(String message) { // parametre: kullanıcıya / log'a gidecek mesaj
        super(message); // üst sınıfa (RuntimeException) hata mesajını iletir
    } // constructor metodunun kapanış süslü parantezi

} // InvalidFlightDataException sınıfının kapanış süslü parantezi
