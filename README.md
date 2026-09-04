# ✈️ MSB Microservices Prep

> **Uçuş ve Mil Hesaplama** temalı, Java 17 / Spring Boot mikroservis mimarisine hazırlık eğitim ve pratik deposu.

---

## 🎯 Proje Başlığı ve Amacı

**MSB Microservices Prep**, üretim (production) ortamında çalışan Java 17 ve Spring Boot tabanlı mikroservis mimarilerine (API Gateway, Config Server, domain servisleri vb.) geçiş öncesinde temel becerileri pekiştirmek için oluşturulmuş bir **öğrenme ve ısınma** projesidir.

Bu deponun temel amaçları:

- Nesne yönelimli programlama (**OOP**) prensiplerini konsol uygulaması üzerinden pratik etmek
- Sınıf, paket, Maven proje yapısı ve katmanlı düşünme alışkanlığı kazanmak
- İleride kurulacak **Spring Boot** ekosistemine (Controller → Service → Repository) zihinsel köprü kurmak
- **IntelliJ IDEA** ile profesyonel geliştirme akışını erken aşamada oturtmak

Temamız olan **Uçuş ve Mil Hesaplama**, ilerleyen fazlarda üye detayları, mil kazanımı ve domain servislerine dönüşecek gerçekçi bir senaryo sunar.

---

## 🛠️ Kullanılan Teknolojiler (Tech Stack)

| Teknoloji | Rol |
|-----------|-----|
| **Java 17** | Dil ve runtime (LTS) |
| **Maven** | Bağımlılık yönetimi ve derleme |
| **IntelliJ IDEA** | Birincil IDE |
| **Git / GitHub** | Versiyon kontrolü ve uzak depo |
| **Spring Boot** *(planlanan)* | Faz 2+ REST API ve mikroservisler |
| **Spring Cloud Gateway / Config** *(planlanan)* | Faz 3 yönlendirme ve merkezi konfigürasyon |
| **JPA / Hibernate** *(planlanan)* | Faz 4 kalıcı veri katmanı |
| **JUnit, Logging, JWT** *(planlanan)* | Faz 5 test, gözlemlenebilirlik ve güvenlik |

---

## 🗺️ Çalışma Planı (Yol Haritası)

| Faz | Konu | Odak |
|-----|------|------|
| **Faz 1** | Java Core ve OOP | Değişkenler, sınıflar, döngüler, koleksiyonlar, Stream API |
| **Faz 2** | Spring Boot Temelleri | Controller, Service, Repository |
| **Faz 3** | Konfigürasyon ve Gateway | `msb-config-server`, `msb-gateway` mantığı |
| **Faz 4** | Domain Servisleri ve Veritabanı | `w3-member-details`, JPA/Hibernate |
| **Faz 5** | Kalite ve Production | Test, loglama, JWT Security |

> **Şu an:** Faz 1 — `phase1-java-core` konsol uygulaması (Flight & Miles).

---

## 🚀 IntelliJ IDEA'da Projeyi Çalıştırma (How to Run)

Aşağıdaki adımlar, projeyi **ilk kez** IntelliJ IDEA'da açan bir geliştirici içindir.

### 1) Depoyu klonlayın

```bash
git clone https://github.com/OnurAkyuz61/msb-microservices-prep.git
cd msb-microservices-prep
```

### 2) Projeyi IntelliJ'de açın

1. **File → Open…**
2. `msb-microservices-prep` klasörünü seçin **veya** doğrudan `phase1-java-core/pom.xml` dosyasını seçin
3. Maven projesi olarak tanınmasını onaylayın (gerekirse sağdaki **Maven** panelinden 🔄 Reload)

### 3) Java 17 SDK ayarını yapın

1. **File → Project Structure…** (`⌘;` / `Ctrl+Alt+Shift+S`)
2. **Project → SDK** alanından **Java 17** seçin  
   - Kurulu değilse: **Add SDK → Download JDK…** → Vendor (ör. Eclipse Temurin) → Version **17**
3. **Project language level** değerinin **17** olduğundan emin olun
4. **Apply → OK**

> İpucu: `phase1-java-core/pom.xml` içinde `maven.compiler.source` / `target` zaten **17** olarak tanımlıdır.

### 4) Main sınıfını çalıştırın

1. Sol Project ağacından şu dosyaya gidin:

   `phase1-java-core/src/main/java/com/msb/flightmiles/Main.java`

2. `main` metodunun yanındaki yeşil ▶️ simgesine tıklayın  
   **veya** dosyaya sağ tıklayıp **Run 'Main.main()'** seçin

3. Alt panelde **Run** çıktısında uçuş bilgileri ve mil hesaplama sonuçlarını görmelisiniz.

### 5) (İsteğe bağlı) Terminal / Maven ile çalıştırma

IntelliJ Maven paneli veya terminal:

```bash
cd phase1-java-core
mvn -q compile
java -cp target/classes com.msb.flightmiles.Main
```

Maven yoksa (yalnızca `javac` / `java`):

```bash
cd phase1-java-core
javac -d target/classes $(find src/main/java -name "*.java")
java -cp target/classes com.msb.flightmiles.Main
```

---

## 📂 Proje Yapısı (Folder Structure)

```text
msb-microservices-prep/
├── README.md
├── LICENSE
├── .gitignore
└── phase1-java-core/                 # Faz 1: Java Core konsol uygulaması
    ├── pom.xml                       # Maven proje tanımı (Java 17)
    ├── .gitignore
    └── src/
        ├── main/
        │   ├── java/
        │   │   └── com/msb/flightmiles/
        │   │       ├── Main.java                 # Uygulama giriş noktası
        │   │       ├── model/
        │   │       │   └── Flight.java           # Uçuş bilgisi (entity)
        │   │       └── service/
        │   │           └── MilesCalculator.java  # Mil hesaplama (iş kuralı)
        │   └── resources/
        └── test/
            └── java/
```

### Faz 1 sınıfları (kısa özet)

| Sınıf | Paket | Görev |
|-------|--------|--------|
| `Main` | `com.msb.flightmiles` | Nesneleri oluşturur, uygulamayı çalıştırır |
| `Flight` | `com.msb.flightmiles.model` | Uçuş no, mesafe, business class bilgisi |
| `MilesCalculator` | `com.msb.flightmiles.service` | if/else ve döngülerle mil hesaplar |

---

## 📄 Lisans

Bu proje [MIT License](./LICENSE) kapsamında lisanslanmıştır. Eğitim ve pratik amaçlı özgürce kullanılabilir, değiştirilebilir ve dağıtılabilir.

---

## 👤 Geliştirici

**Onur Akyüz** · IntelliJ IDEA ile geliştirilmektedir.

Sorular, iyileştirme önerileri veya PR’lar için GitHub deposunu kullanabilirsiniz:  
https://github.com/OnurAkyuz61/msb-microservices-prep
