# E-Commerce Backend API

Bu proje, temel bir E-Ticaret uygulamasının backend servislerini sağlamak üzere tasarlanmış bir RESTful API'dir. Ürün, sipariş, sepet ve müşteri yönetimi gibi temel e-ticaret operasyonlarını destekler.

## ✨ Özellikler

* **Ürün Yönetimi:** Ürünleri ekleme, güncelleme, silme ve listeleme.
* **Müşteri Yönetimi:** Müşteri bilgilerini yönetme.
* **Sepet Yönetimi:** Müşterilerin sepetlerine ürün ekleme, çıkarma ve miktar güncelleme.
* **Sipariş Yönetimi:** Sipariş oluşturma ve sipariş durumlarını güncelleme.
* **Veritabanı Entegrasyonu:** Spring Data JPA ile kalıcı veri depolama.
* **Bellek İçi Veritabanı:** Geliştirme kolaylığı için H2 veritabanı kullanımı.
* **API Dokümantasyonu:** SpringDoc OpenAPI (Swagger UI) ile otomatik API belgeleme.
* **Geliştirici Dostu:** Lombok ile boilerplate kod azaltma.
* **Validasyon:** Veri girişlerini doğrulamak için Spring Boot Starter Validation.

## 🚀 Teknolojiler

* **Java 17**
* **Spring Boot 3.3.1**
* **Maven**
* **Spring Data JPA**
* **H2 Database** (Bellek İçi)
* **Spring Web** (RESTful API oluşturma)
* **SpringDoc OpenAPI (Swagger UI)**
* **Lombok**
* **Hibernate**
* **Jakarta Validation**

## ⚙️ Kurulum ve Çalıştırma

Projeyi yerel makinenizde çalıştırmak için aşağıdaki adımları takip edin:

1.  **Projeyi Klonlayın:**
    ```bash
    git clone [Bu repository'nin URL'si]
    cd ecommerceapp
    ```
2.  **Bağımlılıkları Yükleyin ve Projeyi Derleyin:**
    Maven kullanarak projenin bağımlılıklarını indirin ve `target` dizinine bir JAR dosyası oluşturun:
    ```bash
    mvn clean install
    ```
3.  **Uygulamayı Çalıştırın:**
    IntelliJ IDEA gibi bir IDE kullanarak `src/main/java/com/example/ecommerceapp/EcommerceappApplication.java` dosyasını açın ve `main` metodunu çalıştırın.

    Alternatif olarak, komut satırından JAR dosyasını çalıştırabilirsiniz:
    ```bash
    java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar
    ```

    Uygulama başarıyla başladığında konsolda aşağıdaki gibi bir çıktı görmelisiniz:
    ```
    ...
    :: Spring Boot ::        (v3.3.1)
    ...
    Tomcat started on port 8080 (http) with context path '/'
    ...
    Started EcommerceappApplication in X.XXX seconds
    ```

## 🌐 API Dokümantasyonu (Swagger UI)

Uygulama çalıştığında, API endpoint'lerine ve detaylı dokümantasyonuna Swagger UI üzerinden erişebilirsiniz:

* **Swagger UI URL:** `http://localhost:8080/swagger-ui.html`

Bu arayüz üzerinden tüm API'leri görüntüleyebilir, detaylarını inceleyebilir ve "Try it out" özelliğini kullanarak doğrudan test edebilirsiniz.
Sayfanın görünümünün birkaç örnek görseli şu şekildedir:
![api_ekran](https://github.com/user-attachments/assets/30acd1c0-ca5d-448f-a736-560a351eac6b)

![api_ekran2](https://github.com/user-attachments/assets/1cf897b2-73fc-4796-8bb2-5e43b2082cdf)


## 🗄️ H2 Veritabanı Konsolu

Geliştirme amacıyla kullanılan bellek içi H2 veritabanına web konsolu üzerinden erişebilirsiniz:

* **H2 Console URL:** `http://localhost:8080/h2-console`
Sayfanın görünümünün birkaç örnek görseli şu şekildedir:
![db_ekran](https://github.com/user-attachments/assets/a26964be-3387-4bf7-bc09-02516bf3b241)
![db_ekran2](https://github.com/user-attachments/assets/63085418-5d71-4e26-b691-d9e353feae46)

Bağlantı Bilgileri:
* **JDBC URL:** `jdbc:h2:mem:ecommerce_db`
* **User Name:** `sa`
* **Password:** (Boş bırakın)

## 📂 Proje Yapısı
ecommerceapp/

├── src/

│   ├── main/

│   │   ├── java/com/example/ecommerceapp/

│   │   │   ├── EcommerceappApplication.java      # Ana uygulama sınıfı

│   │   │   ├── controller/                     # REST API endpoint'leri

│   │   │   ├── dto/                            # Veri Transfer Nesneleri

│   │   │   ├── entity/                         # Veritabanı varlıkları (JPA)

│   │   │   ├── exception/                      # Özel istisna sınıfları

│   │   │   ├── repository/                     # Veritabanı erişim arayüzleri

│   │   │   ├── service/                        # İş mantığı servisleri

│   │   │   └── util/                           # Yardımcı sınıflar

│   │   └── resources/

│   │       └── application.properties          # Uygulama yapılandırmaları

│   └── test/

│       └── java/com/example/ecommerceapp/

│           └── EcommerceappApplicationTests.java # Test sınıfları

├── pom.xml                                     # Maven proje yapılandırma dosyası

└── README.md # Bu dosya

