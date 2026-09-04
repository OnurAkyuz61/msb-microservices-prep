// Bu sınıfın paketi: Spring Boot uygulamasının kök (root) paketi
package com.thy.miles; // Component Scan buradan başlayarak alt paketleri tarar

// SpringApplication: uygulamayı başlatan yardımcı sınıf
import org.springframework.boot.SpringApplication; // run(...) ile context ayağa kalkar
// @SpringBootApplication: üç kritik anotasyonu tek pakette birleştirir
import org.springframework.boot.autoconfigure.SpringBootApplication; // Boot'un ana giriş anotasyonu

/*
 * @SpringBootApplication Spring Boot ekosisteminde ne işe yarar?
 * 1) @Configuration  -> Bu sınıfı yapılandırma (config) sınıfı yapar; @Bean tanımları yazılabilir.
 * 2) @EnableAutoConfiguration -> Classpath'teki kütüphanelere göre otomatik Bean kurar
 *    (ör. spring-boot-starter-web varsa gömülü Tomcat + DispatcherServlet hazırlanır).
 * 3) @ComponentScan  -> Bu paketten (com.thy.miles) itibaren @Component/@Service/@RestController
 *    sınıflarını tarayıp Spring IoC Container'a Bean olarak kaydeder (Dependency Injection temeli).
 */
@SpringBootApplication // Uygulamanın "başlangıç düğmesi" anotasyonu (otomatik yapılandırma + tarama)
// MilesApplication: Spring Boot REST API'sinin ana (main) sınıfı
public class MilesApplication { // public class: JVM'in giriş noktası burada yaşar

    // main metodu: java -jar veya IDE Run ile ilk burası çalışır
    public static void main(String[] args) { // args: komut satırı argümanları
        // SpringApplication.run: ApplicationContext'i oluşturur, Bean'leri yükler, web sunucusunu açar
        SpringApplication.run(MilesApplication.class, args); // class + args ile Boot'u başlat
    } // main metodunun kapanış süslü parantezi

} // MilesApplication sınıfının kapanış süslü parantezi
