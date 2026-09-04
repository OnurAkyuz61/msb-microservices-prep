# ☕ Faz 1 — Java Core (`phase1-java-core`)

> **Miles & Smiles** hazırlık yolculuğunun temeli: Java 17, OOP, koleksiyonlar, Stream API, Strategy deseni ve özel exception yönetimi.

---

## 🎯 Projenin Amacı

Bu fazın hedefi, ileride kuracağın **Spring Boot mikroservislerine** geçmeden önce sağlam bir Java Core zemini oluşturmaktır.

Burada şunları pratik edersin:

- **Java 17** ile modern dil özellikleri
- **OOP** (sınıf, paket, encapsulation)
- **Collections** (`List`, `Set`, `Map`)
- **Stream API** (`filter`, `mapToInt`, `sum`, `collect`)
- **Lombok** ile boilerplate azaltma
- **Strategy** ile polimorfik mil hesabı
- **Custom Exception** ile kontrollü hata yönetimi

> 💡 *Konsol uygulaması olmasının sebebi basit: HTTP, DI ve veritabanı karmaşası olmadan saf Java mantığına odaklanmak.*

---

## 📂 Paket Yapısı

```text
phase1-java-core/
└── src/main/java/com/thy/miles/
    ├── app/
    │   └── Main.java                      # Uygulama giriş noktası
    ├── model/
    │   └── Flight.java                    # Veri modeli (entity benzeri)
    ├── service/
    │   └── FlightManager.java             # List / Set / Map + Stream yönetimi
    ├── strategy/
    │   ├── MilesCalculatorStrategy.java   # Hesaplama sözleşmesi (interface)
    │   ├── ClassicMemberMilesCalculator.java
    │   └── EliteMemberMilesCalculator.java
    └── exception/
        └── InvalidFlightDataException.java
```

| Katman | Rol |
|--------|-----|
| `model` | Sadece veri taşır |
| `service` | İş akışı / koleksiyon yönetimi |
| `strategy` | Değişebilir hesaplama kuralları |
| `exception` | Domain hataları |
| `app` | `main` ile orkestrasyon |

> Bu ayrım, Faz 2’deki **Controller / Service / (ileride Repository)** düşüncesinin ön hazırlığıdır.

---

## 🧠 Öğrenilen Kavramlar (Detaylı)

### 1) Lombok — Neden kullandık?

Kurumsal Java projelerinde model sınıfları çoğu zaman şuna benzer:

- getter / setter
- `toString`
- `equals` / `hashCode`
- constructor’lar

Bunlar **iş kuralı değil**, tekrarlayan şablon koddur (boilerplate). Lombok, derleme anında bu kodu üretir.

`Flight` sınıfında kullandığımız başlıca anotasyonlar:

| Anotasyon | Arka planda ne yapar? |
|-----------|------------------------|
| `@Data` | Getter/setter, `toString`, `equals`, `hashCode` üretir |
| `@NoArgsConstructor` | `new Flight()` boş constructor |
| `@AllArgsConstructor` | Tüm alanları alan constructor |
| `@Builder` | `Flight.builder()...build()` fluent API’si |

**Builder örneği:**

```java
Flight flight = Flight.builder()
        .flightNumber("TK1903")
        .destination("New York")
        .distanceKm(3200)
        .businessClass(true)
        .build();
```

> ✅ *Okunabilirlik artar, alan sırasını ezberleme ihtiyacı azalır, kurumsal kod stiline yaklaşır.*

---

### 2) Koleksiyonlar ve Stream API

`FlightManager` içinde üç koleksiyon tipi bir arada kullanılır:

| Yapı | Tip | Ne için? |
|------|-----|----------|
| `List<Flight>` | sıralı liste | Tüm uçuşlar |
| `Set<String>` | tekrarsız küme | Benzersiz varış noktaları |
| `Map<String, Flight>` | anahtar → değer | Uçuş no ile O(1)’e yakın arama |

**Neden klasik `for` yerine Stream?**

```java
return flights.stream()
        .filter(flight -> flight.isBusinessClass()) // sadece business
        .collect(Collectors.toList());
```

```java
return flights.stream()
        .mapToInt(flight -> milesCalculator.calculateMiles(flight)) // Flight → int
        .sum(); // toplam
```

Stream’in avantajları:

- **Ne istediğini** (`filter`, `map`, `sum`) kodda daha net görürsün
- Zincirleme (pipeline) okunabilirliği yüksektir
- İleride paralel işlem (`parallelStream`) için kapı aralar
- Mikroservislerde DTO dönüşümleri / filtreleme için aynı zihinsel model kullanılır

> ⚠️ *Stream “sihir” değildir; altında yine döngü vardır. Asıl kazanç ifade gücü ve bakım kolaylığıdır.*

---

### 3) OOP ve Polimorfizm (Strategy)

Aynı uçuş için **Classic** ve **Elite** üyeye farklı mil vermek istiyoruz. Bunu `if (memberType == ...)` ile şişirmek yerine **Strategy** kullanırız:

```text
MilesCalculatorStrategy  (interface)
        ├── ClassicMemberMilesCalculator
        └── EliteMemberMilesCalculator
```

```java
public interface MilesCalculatorStrategy {
    int calculateMiles(Flight flight);
}
```

`FlightManager` stratejiyi **interface tipi** ile tutar:

```java
private MilesCalculatorStrategy milesCalculator;
```

Çalışma anında Classic veya Elite nesnesi verilebilir. Bu **polimorfizmdir**:

> *Aynı sözleşme (`calculateMiles`), farklı davranış.*

**Mikroservis hazırlığı açısından önemi:**

- Hesaplama kuralı değişince manager’ı parçalamazsın
- Yeni üye tipi = yeni sınıf (Open/Closed Principle’a yaklaşım)
- İleride “mil hesaplama” ayrı bir domain servisine dönüştürülebilir

---

### 4) Özel Hata Yönetimi — `InvalidFlightDataException`

```java
public class InvalidFlightDataException extends RuntimeException {
    public InvalidFlightDataException(String message) {
        super(message);
    }
}
```

**Neden `RuntimeException`?**

| Tip | Davranış |
|-----|----------|
| `Exception` (checked) | Metot imzasında `throws` zorunlu; çağıran her yer etkilenir |
| `RuntimeException` (unchecked) | Derleyici zorlamaz; domain kuralları için daha esnek |

Geçersiz uçuş numarası gibi durumlar **programlama hatası / iş kuralı ihlali**dir. Bunları checked exception ile her katmana yaymak gürültü yaratır.

`FlightManager.addFlight` içinde:

```java
if (flight == null || flight.getFlightNumber() == null || flight.getFlightNumber().isBlank()) {
    throw new InvalidFlightDataException("Uçuş numarası boş olamaz.");
}
```

> 🧭 *Faz 2’de benzer hataları `@RestControllerAdvice` ile HTTP 400 JSON cevabına bağlayacaksın.*

---

## 🚀 Nasıl Çalıştırılır? (IntelliJ + Maven)

### 1) Projeyi aç

1. IntelliJ IDEA → **File → Open**
2. `phase1-java-core` klasörünü seç (`pom.xml` görünmeli)
3. Maven olarak tanımasını onayla

### 2) JDK 17 ayarı

1. **File → Project Structure → Project**
2. **SDK = Java 17**
3. Language level = **17**

### 3) Maven bağımlılıklarını yükle

- Sağdaki **Maven** panelinden reload (🔄)
- Lombok plugin kurulu olsun (**Settings → Plugins → Lombok**)
- **Annotation Processing** açık olsun

### 4) Çalıştır

1. `src/main/java/com/thy/miles/app/Main.java` dosyasını aç
2. `main` yanındaki ▶️ → **Run**

### Terminal alternatifi

```bash
cd phase1-java-core
mvn -q compile
java -cp target/classes com.thy.miles.app.Main
```

---

## ✅ Bu Fazdan Çıkman Gereken Kazanımlar

- [ ] Paketleri sorumluluğa göre ayırabiliyorum
- [ ] Lombok ile model sınıfını sade tutabiliyorum
- [ ] `List` / `Set` / `Map` farkını örnekle açıklayabiliyorum
- [ ] Stream `filter` / `mapToInt` / `sum` zincirini okuyabiliyorum
- [ ] Strategy ile polimorfik hesaplama yazabiliyorum
- [ ] Domain exception’ı ne zaman fırlatacağımı biliyorum

> Sonraki durak: [`phase2-spring-boot`](./phase2-spring-boot.md) — aynı domain’i REST API’ye taşıyoruz.
