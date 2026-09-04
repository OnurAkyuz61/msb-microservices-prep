// Bu sınıfın paketi: API hata cevapları ve exception sınıfları
package com.thy.miles.exception; // exception: merkezi hata yönetimi burada yaşar

// Lombok: tüm alanları alan constructor
import lombok.AllArgsConstructor; // ErrorResponse oluştururken kolaylık
// Lombok: getter/setter/toString
import lombok.Data; // JSON serileştirme için getter gerekir
// Lombok: boş constructor (Jackson uyumu)
import lombok.NoArgsConstructor; // gerekirse boş nesne

/*
 * @Data: error ve status alanları için getter/setter üretir;
 * Spring JSON cevabında {"error":"...","status":400} formatını üretebilir.
 */
@Data // Lombok: alan erişim metotlarını üretir
@NoArgsConstructor // Lombok: parametresiz constructor
@AllArgsConstructor // Lombok: (error, status) constructor
// ErrorResponse: istemciye dönen standart hata JSON gövdesi
public class ErrorResponse { // public class: GlobalExceptionHandler tarafından kullanılır

    // İnsan tarafından okunabilir hata mesajı
    private String error; // örn: "Geçersiz mesafe"

    // HTTP durum kodunun sayısal karşılığı
    private int status; // örn: 400

} // ErrorResponse sınıfının kapanış süslü parantezi
