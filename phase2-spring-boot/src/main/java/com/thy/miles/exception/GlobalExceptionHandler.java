// Bu sınıfın paketi: merkezi (global) exception yakalama
package com.thy.miles.exception; // exception: tüm controller hataları buraya düşebilir

// Spring HTTP: 400 Bad Request sabiti
import org.springframework.http.HttpStatus; // HttpStatus.BAD_REQUEST
// Spring Web: belirli exception tipini yakalayan metot işaretçisi
import org.springframework.web.bind.annotation.ExceptionHandler; // @ExceptionHandler
// Spring Web: metodun döneceği HTTP status kodu
import org.springframework.web.bind.annotation.ResponseStatus; // @ResponseStatus
// Spring Web: tüm @RestController'lar için ortak advice (AOP benzeri kesit)
import org.springframework.web.bind.annotation.RestControllerAdvice; // @RestControllerAdvice

/*
 * @RestControllerAdvice Spring Boot ekosisteminde ne işe yarar?
 * - @ControllerAdvice + @ResponseBody birleşimidir.
 * - Uygulamadaki (veya belirtilen paketlerdeki) tüm @RestController'lara "çapraz kesit"
 *   (cross-cutting) hata yönetimi uygular.
 * - Her controller'a ayrı try-catch yazmak yerine hataları tek yerde standart JSON'a çevirir.
 * - Kurumsal mikroservislerde tutarlı error contract (status + message) sağlamak için kullanılır.
 * - Bu sınıf da bir Spring Bean'dir; Component Scan ile otomatik bulunur.
 */
@RestControllerAdvice // Spring: tüm REST controller hatalarını merkezi dinleyen advice Bean'i
// GlobalExceptionHandler: Controller katmanından fırlayan exception'ları yakalar
public class GlobalExceptionHandler { // public class: cross-cutting exception handling

    /*
     * @ExceptionHandler(IllegalArgumentException.class) Spring Boot ekosisteminde ne işe yarar?
     * - Belirtilen exception tipi (veya alt tipleri) fırladığında bu metodu çalıştırır.
     * - FlightService.addFlight içinde mesafe <= 0 iken fırlayan IllegalArgumentException
     *   controller'a çıkmadan / çıktıktan sonra burada yakalanır.
     * - Dönüş değeri @RestControllerAdvice sayesinde otomatik JSON body olur.
     */
    @ExceptionHandler(IllegalArgumentException.class) // Spring: IllegalArgumentException gelince buraya gir
    /*
     * @ResponseStatus(HttpStatus.BAD_REQUEST) Spring Boot ekosisteminde ne işe yarar?
     * - Bu handler cevabının HTTP kodunu 400 Bad Request yapar.
     * - İstemciye "isteğin hatalı / validasyon başarısız" sinyali verir.
     * - 500 Internal Server Error yerine doğru semantik kod kullanmak API kalitesidir.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Spring: bu hata cevabında 400 dön
    // handleIllegalArgument: geçersiz argüman hatalarını standart ErrorResponse'a çevirir
    public ErrorResponse handleIllegalArgument(IllegalArgumentException ex) { // parametre: yakalanan hata
        // exception mesajını (ör. "Geçersiz mesafe") ve 400 kodunu JSON modele koy
        return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()); // {"error":"...","status":400}
    } // handleIllegalArgument metodunun kapanış süslü parantezi

} // GlobalExceptionHandler sınıfının kapanış süslü parantezi
