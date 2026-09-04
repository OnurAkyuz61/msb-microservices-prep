# ✈️ MSB Microservices Prep

> **Miles & Smiles — Uçuş ve Mil Hesaplama** temalı, Java 17 / Spring Boot mikroservis mimarisine hazırlık eğitim ve pratik deposu.

---

## 🎯 Proje Amacı

**MSB Microservices Prep**, üretim ortamında çalışan Java 17 ve Spring Boot tabanlı mikroservis mimarilerine (API Gateway, Config Server, domain servisleri vb.) geçiş öncesinde temel becerileri pekiştirmek için oluşturulmuş bir **öğrenme ve ısınma** projesidir.

Bu deponun hedefleri:

- **OOP**, koleksiyonlar, Stream API ve Strategy deseni ile sağlam bir Java Core zemini kurmak
- Maven paket yapısı ve katmanlı düşünme alışkanlığı kazanmak
- **Spring Boot** ile Controller → Service mimarisine ve REST API’ye geçmek
- Merkezi hata yönetimi (`@RestControllerAdvice`) ile kurumsal API standartlarına yaklaşmak
- **IntelliJ IDEA** ile profesyonel geliştirme akışını oturtmak

---

## 📚 Detaylı Dokümantasyon

Faz faz anlatımlar `docs/` klasöründedir:

| Doküman | İçerik |
|---------|--------|
| [docs/README.md](./docs/README.md) | Dokümantasyon indeksi |
| [docs/phase1-java-core.md](./docs/phase1-java-core.md) | Lombok, Stream, Strategy, Exception |
| [docs/phase2-spring-boot.md](./docs/phase2-spring-boot.md) | DI, REST, Global Exception, API örnekleri |

---

## 🛠️ Tech Stack

| Teknoloji | Rol | Durum |
|-----------|-----|--------|
| **Java 17** | Dil ve runtime (LTS) | ✅ |
| **Maven** | Bağımlılık yönetimi ve derleme | ✅ |
| **Lombok** | Boilerplate azaltma (`@Data`, `@Builder`) | ✅ |
| **IntelliJ IDEA** | Birincil IDE | ✅ |
| **Git / GitHub** | Versiyon kontrolü | ✅ |
| **Spring Boot 3.2** | REST API (`starter-web`) | ✅ Faz 2 |
| **Spring Cloud Gateway / Config** | Yönlendirme ve merkezi konfig | 🔜 Faz 3 |
| **JPA / Hibernate + H2** | Kalıcı veri katmanı | 🔜 Faz 4 |
| **JUnit, Logging, JWT** | Test, gözlemlenebilirlik, güvenlik | 🔜 Faz 5 |

---

## 🗺️ Yol Haritası

| Faz | Konu | Klasör | Durum |
|-----|------|--------|--------|
| **Faz 1** | Java Core, OOP, Collections, Stream, Strategy | `phase1-java-core/` | ✅ Tamamlandı |
| **Faz 2** | Spring Boot REST (Controller, Service, Exception) | `phase2-spring-boot/` | ✅ Tamamlandı |
| **Faz 3** | Config Server & Gateway | — | 🔜 Planlandı |
| **Faz 4** | Domain servisleri & JPA/H2 | — | 🔜 Planlandı |
| **Faz 5** | Test, loglama, JWT Security | — | 🔜 Planlandı |

> **Şu an:** Faz 1 + Faz 2 tamam. Sonraki adım: Gateway / Config ve kalıcı veri (JPA).

---

## 📂 Proje Yapısı

```text
msb-microservices-prep/
├── README.md
├── LICENSE
├── .gitignore
├── docs/                                 # Faz faz teknik dokümantasyon
│   ├── README.md
│   ├── phase1-java-core.md
│   └── phase2-spring-boot.md
├── phase1-java-core/                     # Faz 1: Java Core konsol uygulaması
│   └── src/main/java/com/thy/miles/
│       ├── app/Main.java
│       ├── model/Flight.java
│       ├── service/FlightManager.java
│       ├── strategy/...
│       └── exception/InvalidFlightDataException.java
└── phase2-spring-boot/                   # Faz 2: Spring Boot REST API
    └── src/main/java/com/thy/miles/
        ├── MilesApplication.java
        ├── controller/FlightController.java
        ├── service/FlightService.java
        ├── model/Flight.java
        └── exception/
            ├── GlobalExceptionHandler.java
            └── ErrorResponse.java
```

---

## 🚀 Nasıl Çalıştırılır?

### Ortak adımlar

```bash
git clone https://github.com/OnurAkyuz61/msb-microservices-prep.git
cd msb-microservices-prep
```

1. IntelliJ → **File → Open** → ilgili faz klasörünü veya kök depoyu aç  
2. **Project SDK = Java 17**  
3. Maven panelinden **Reload**

---

### Faz 1 — Java Core

**IntelliJ:** `phase1-java-core/.../app/Main.java` → ▶️ Run  

**Terminal:**

```bash
cd phase1-java-core
mvn -q compile
java -cp target/classes com.thy.miles.app.Main
```

---

### Faz 2 — Spring Boot REST API

**IntelliJ:** `phase2-spring-boot/.../MilesApplication.java` → ▶️ Run  

**Terminal:**

```bash
cd phase2-spring-boot
mvn spring-boot:run
```

Uygulama ayağa kalkınca:

| Method | URL | Açıklama |
|--------|-----|----------|
| `GET` | `http://localhost:8080/api/v1/flights` | Tüm uçuşları listele |
| `POST` | `http://localhost:8080/api/v1/flights` | Yeni uçuş ekle (`201 Created`) |

**Örnek POST:**

```bash
curl -i -X POST http://localhost:8080/api/v1/flights \
  -H 'Content-Type: application/json' \
  -d '{"flightNumber":"TK999","distanceKm":1500,"businessClass":true}'
```

**Geçersiz mesafe (`distanceKm <= 0`) → `400 Bad Request`:**

```json
{ "error": "Geçersiz mesafe", "status": 400 }
```

> Postman örnekleri ve kavram anlatımları için: [docs/phase2-spring-boot.md](./docs/phase2-spring-boot.md)

---

## 📄 Lisans

Bu proje [MIT License](./LICENSE) kapsamında lisanslanmıştır.

---

## 👤 Geliştirici

**Onur Akyüz** · IntelliJ IDEA ile geliştirilmektedir.

Depo: https://github.com/OnurAkyuz61/msb-microservices-prep
