// Flight sınıfı: Bir uçuşa ait bilgileri tutmak için kullanılır
public class Flight { // Flight adında bir sınıf (class) tanımlıyoruz

    // --- DEĞİŞKENLER (sınıfın alanları / fields) ---

    // Uçuş numarasını metin (String) olarak saklar, örn: "TK123"
    private String flightNumber; // private: sadece bu sınıf içinden erişilebilir

    // Uçuş mesafesini kilometre cinsinden tam sayı (int) olarak saklar
    private int distanceKm; // mesafe değeri, örneğin 1500

    // Uçuşun business class olup olmadığını true/false (boolean) olarak saklar
    private boolean businessClass; // true = business, false = ekonomi

    // --- CONSTRUCTOR (yapıcı metot): Nesne oluşturulurken çalışır ---

    // Yeni bir Flight nesnesi oluştururken uçuş bilgilerini parametre olarak alır
    public Flight(String flightNumber, int distanceKm, boolean businessClass) { // parametreler: uçuş no, mesafe, business mi?
        this.flightNumber = flightNumber; // gelen uçuş numarasını sınıf değişkenine atar
        this.distanceKm = distanceKm; // gelen mesafe bilgisini sınıf değişkenine atar
        this.businessClass = businessClass; // gelen business bilgisini sınıf değişkenine atar
    } // constructor metodunun kapanış süslü parantezi

    // --- GETTER METOTLARI: Değişkenleri dışarıdan güvenli şekilde okumak için ---

    // Uçuş numarasını dışarıya döndüren metot
    public String getFlightNumber() { // dönüş tipi String olan bir metot
        return flightNumber; // flightNumber değişkeninin değerini geri verir
    } // getFlightNumber metodunun kapanış süslü parantezi

    // Mesafe bilgisini dışarıya döndüren metot
    public int getDistanceKm() { // dönüş tipi int olan bir metot
        return distanceKm; // distanceKm değişkeninin değerini geri verir
    } // getDistanceKm metodunun kapanış süslü parantezi

    // Business class durumunu dışarıya döndüren metot
    public boolean isBusinessClass() { // boolean için genelde "is" ile başlayan isim kullanılır
        return businessClass; // businessClass değişkeninin değerini geri verir
    } // isBusinessClass metodunun kapanış süslü parantezi

    // --- SETTER METOTLARI: Değişkenleri dışarıdan güncellemek için ---

    // Uçuş numarasını değiştirmek için kullanılan metot
    public void setFlightNumber(String flightNumber) { // void: bir şey döndürmez, sadece atama yapar
        this.flightNumber = flightNumber; // yeni uçuş numarasını sınıf değişkenine yazar
    } // setFlightNumber metodunun kapanış süslü parantezi

    // Mesafe bilgisini değiştirmek için kullanılan metot
    public void setDistanceKm(int distanceKm) { // int tipinde yeni mesafe alır
        this.distanceKm = distanceKm; // yeni mesafe değerini sınıf değişkenine yazar
    } // setDistanceKm metodunun kapanış süslü parantezi

    // Business class bilgisini değiştirmek için kullanılan metot
    public void setBusinessClass(boolean businessClass) { // boolean tipinde yeni değer alır
        this.businessClass = businessClass; // yeni business bilgisini sınıf değişkenine yazar
    } // setBusinessClass metodunun kapanış süslü parantezi

    // --- BİLGİ YAZDIRMA METODU ---

    // Uçuş bilgilerini ekrana (konsola) yazdıran basit bir metot
    public void printFlightInfo() { // void: ekrana yazar, değer döndürmez
        System.out.println("Uçuş No   : " + flightNumber); // uçuş numarasını yazdırır
        System.out.println("Mesafe    : " + distanceKm + " km"); // mesafeyi km ile yazdırır
        System.out.println("Business  : " + businessClass); // business durumunu yazdırır
    } // printFlightInfo metodunun kapanış süslü parantezi

} // Flight sınıfının kapanış süslü parantezi
