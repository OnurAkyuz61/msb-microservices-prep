# 🌱 Faz 2 — Spring Boot REST API (`phase2-spring-boot`)

> **Miles & Smiles** yolculuğunun ikinci fazı: Java Core bilgisini **3 katmanlı REST API** mimarisine taşıyoruz. HTTP, Dependency Injection ve merkezi hata yönetimi burada başlar.

---

## 🎯 Projenin Amacı

Faz 1’deki uçuş / mil dünyasını konsoldan çıkarıp **Spring Boot** ile dışarıya açılan bir API haline getirmek.

Bu fazda öğrendiklerin:

- Spring Boot ile gömülü web sunucusu (Tomcat)
- **Controller → Service** katmanlı mimari
- **Dependency Injection (DI)**
- REST: `GET` / `POST`
- **Global Exception Handling** ile standart JSON hata cevabı
- (Hazırlık) **JPA + H2** ile kalıcı veri katmanına geçiş mantığı

> 💡 *Şu an veri katmanı bellek içi `List` ile simüle ediliyor. Bu, Repository’ye geçmeden önce Service sözleşmesini oturtmak içindir.*

---

## 📂 Paket Yapısı

```text
phase2-spring-boot/
└── src/main/java/com/thy/miles/
    ├── MilesApplication.java              # @SpringBootApplication
    ├── controller/
    │   └── FlightController.java          # HTTP uçları (REST)
    ├── service/
    │   └── FlightService.java             # İş kuralları + geçici bellek deposu
    ├── model/
    │   └── Flight.java                    # API JSON modeli
    └── exception/
        ├── GlobalExceptionHandler.java    # @RestControllerAdvice
        └── ErrorResponse.java             # Standart hata JSON'u
```

---

## 🧠 Öğrenilen Kavramlar (Detaylı)

### 1) Katmanlı Mimari (Controller / Service / Repository)

Kurumsal Spring uygulamalarında tipik üçlü:

```text
İstemci (Postman / UI)
        │  HTTP JSON
        ▼
┌───────────────────┐
│    Controller     │  → HTTP protokolü, URL, status code
└─────────┬─────────┘
          │ method call
          ▼
┌───────────────────┐
│     Service       │  → iş kuralları, validasyon, orkestrasyon
└─────────┬─────────┘
          │ (ileride)
          ▼
┌───────────────────┐
│    Repository     │  → veritabanı erişimi (JPA)
└───────────────────┘
```

| Katman | Sorumluluk | Bu projedeki karşılık |
|--------|------------|------------------------|
| **Controller** | HTTP istek/cevap | `FlightController` |
| **Service** | İş kuralı | `FlightService` (`distanceKm <= 0` kontrolü) |
| **Repository** | Kalıcı veri | *Henüz yok* — şimdilik Service içindeki `List` |

> ✅ *Controller SQL veya iş kuralı bilmez. Service HTTP status bilmez. Bu ayrım mikroservislerde test edilebilirliği artırır.*

---

### 2) Dependency Injection (DI) — Spring sınıfları nasıl yönetir?

Spring Boot ayağa kalkınca:

1. `@SpringBootApplication` component scan başlatır
2. `@Service`, `@RestController`, `@RestControllerAdvice` sınıflarını **Bean** olarak kaydeder
3. İhtiyaç duyan sınıfa constructor ile verir

```java
@RequiredArgsConstructor // Lombok: final alan için constructor üretir
public class FlightController {
    private final FlightService flightService; // Spring bu Bean'i enjekte eder
}
```

**Neden alan enjeksiyonu (`@Autowired` field) yerine constructor?**

- Zorunlu bağımlılıklar netleşir
- Testte mock vermek kolaydır
- Nesne “yarım” doğmaz

> 🧩 *DI = “new ile her yeri birbirine kenetleme” yerine “kontratı söyle, Spring bağlasın”.*

---

### 3) Global Exception Handling

Service katmanında geçersiz mesafe:

```java
if (flight.getDistanceKm() <= 0) {
    throw new IllegalArgumentException("Geçersiz mesafe");
}
```

Bunu her controller’da `try/catch` ile yakalamak yerine merkezi handler kullanırız:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
        return new ErrorResponse(ex.getMessage(), 400);
    }
}
```

| Anotasyon | Rolü |
|-----------|------|
| `@RestControllerAdvice` | Tüm REST controller’lara çapraz kesit hata yönetimi |
| `@ExceptionHandler` | Belirli exception tipini bu metoda yönlendirir |
| `@ResponseStatus(BAD_REQUEST)` | HTTP **400** döner |

**Örnek hata cevabı:**

```json
{
  "error": "Geçersiz mesafe",
  "status": 400
}
```

> 🏢 *Kurumsal API’lerde hata formatının tutarlı olması (error contract) istemci ekipleri için kritiktir.*

---

### 4) JPA ve H2 — Nereye gidiyoruz? (ORM hazırlığı)

#### Bugün (Faz 2 mevcut durum)

Veri **RAM’deki `List`** içinde:

```java
private final List<Flight> flights = new ArrayList<>();
```

| Avantaj | Dezavantaj |
|---------|------------|
| Kurulumu çok hızlı | Uygulama kapanınca veri kaybolur |
| Öğrenme odaklı | Gerçek mikroserviste kalıcılık yok |

#### Yarın (Faz 4 / bir sonraki adım)

| Parça | Ne işe yarar? |
|-------|----------------|
| **H2** | Dosya veya bellek tabanlı hafif SQL DB (geliştirme için ideal) |
| **`@Entity`** | Java sınıfını tablo satırına map eder |
| **`JpaRepository`** | `save`, `findAll` gibi metotları SQL yazmadan verir |
| **ORM** | Object-Relational Mapping: nesne ↔ tablo köprüsü |

**ORM fikri (özet):**

```text
Flight (Java nesnesi)  ←→  FLIGHT tablosu (SQL satırı)
         Hibernate / JPA
```

Örnek (henüz bu fazda kodda yok, mimari hedef):

```java
@Entity
public class Flight {
    @Id
    private String flightNumber;
    private int distanceKm;
    private boolean businessClass;
}

public interface FlightRepository extends JpaRepository<Flight, String> {
}
```

> 📌 *Şimdi Service’i doğru yazıyorsan, yarın sadece “List yerine Repository” değiştirmen yeterli olacak kadar temiz bir sınır çizmiş olursun.*

---

## 📡 API Kullanım Rehberi (Postman)

Base URL:

```text
http://localhost:8080
```

### ✅ GET — Tüm uçuşlar

- **Method:** `GET`
- **URL:** `/api/v1/flights`

**Örnek Response `200 OK`:**

```json
[
  {
    "flightNumber": "TK101",
    "distanceKm": 850,
    "businessClass": false
  },
  {
    "flightNumber": "TK1903",
    "distanceKm": 3200,
    "businessClass": true
  },
  {
    "flightNumber": "PC404",
    "distanceKm": 2100,
    "businessClass": false
  }
]
```

---

### ✅ POST — Yeni uçuş ekle

- **Method:** `POST`
- **URL:** `/api/v1/flights`
- **Headers:** `Content-Type: application/json`

**Request Body:**

```json
{
  "flightNumber": "TK999",
  "distanceKm": 1500,
  "businessClass": true
}
```

**Başarılı Response `201 Created`:**

```json
{
  "flightNumber": "TK999",
  "distanceKm": 1500,
  "businessClass": true
}
```

---

### ❌ POST — Geçersiz mesafe

**Request Body:**

```json
{
  "flightNumber": "BAD",
  "distanceKm": 0,
  "businessClass": false
}
```

**Hata Response `400 Bad Request`:**

```json
{
  "error": "Geçersiz mesafe",
  "status": 400
}
```

#### cURL ile hızlı test

```bash
# Listele
curl http://localhost:8080/api/v1/flights

# Ekle (201)
curl -i -X POST http://localhost:8080/api/v1/flights \
  -H 'Content-Type: application/json' \
  -d '{"flightNumber":"TK999","distanceKm":1500,"businessClass":true}'

# Hatalı ekle (400)
curl -i -X POST http://localhost:8080/api/v1/flights \
  -H 'Content-Type: application/json' \
  -d '{"flightNumber":"BAD","distanceKm":0,"businessClass":false}'
```

---

## 🚀 Nasıl Çalıştırılır?

### IntelliJ IDEA

1. **File → Open** → `phase2-spring-boot` (`pom.xml` olan klasör)
2. Maven reload
3. JDK **17** seçili olsun
4. `com.thy.miles.MilesApplication` → ▶️ **Run**
5. Konsolda `Tomcat started on port(s): 8080` benzeri log görünmeli

### Maven terminal

```bash
cd phase2-spring-boot
mvn spring-boot:run
```

veya:

```bash
mvn -q -DskipTests package
java -jar target/phase2-spring-boot-1.0.0-SNAPSHOT.jar
```

> 🔧 *Port çakışması olursa `src/main/resources/application.properties` içinde `server.port` değerini değiştir.*

---

## ✅ Bu Fazdan Çıkman Gereken Kazanımlar

- [ ] Controller / Service ayrımını kendi cümlemle anlatabiliyorum
- [ ] Constructor Injection’ın ne işe yaradığını biliyorum
- [ ] `GET` ve `POST` farkını REST dilinde açıklayabiliyorum
- [ ] `@RestControllerAdvice` ile 400 JSON üretebiliyorum
- [ ] Neden şimdilik `List`, sonra `JpaRepository` kullanacağımızı anlıyorum

> Önceki faz: [`phase1-java-core`](./phase1-java-core.md)  
> Sonraki hedef: Config Server / Gateway ve domain servisleriyle gerçek mikroservis ağı (Faz 3+)
