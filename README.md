# 🛍️ Alten Shop

**Alten Shop** est une application e-commerce full-stack développée avec :
- 🎨 **Angular 17** pour le front-end (SPA)
- ⚙️ **Spring Boot 3 + Java 17** pour le back-end
- 🔐 Authentification via **JWT**
- 🛒 Gestion du **panier** et de la **wishlist**
- 🌐 API REST sécurisée et compatible avec CORS

---

## 📁 Structure du projet

```
AltenShop/
│
├── backend/                 → API Spring Boot
│   ├── src/main/java/com/alten/ecommerce/product
│   │   ├── controller/      → AuthController, ProductController, CartWishlistController
│   │   ├── service/         → AuthService, ProductService, UserCollectionsService...
│   │   ├── config/          → SecurityConfig, WebConfig (CORS)
│   │   ├── dto/, mapper/, enums/...
│   │   └── EcommerceApplication.java
│   └── pom.xml
│
└── frontend/                → Application Angular 17
    ├── src/app/
    │   ├── components/
    │   │   ├── product-list/   → Liste et filtrage des produits
    │   │   ├── cart/           → Panier utilisateur
    │   │   ├── contact/        → Formulaire de contact
    │   │   └── login, register → Authentification JWT
    │   ├── services/           → AuthService, ProductService, CartService...
    │   └── models/             → Interfaces TypeScript (Product, LoginRequest, etc.)
    ├── angular.json
    ├── package.json
    └── tsconfig.json
```

---

## 🚀 Lancer le projet

### 1️⃣ Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

Par défaut, le serveur démarre sur :
👉 **http://localhost:8080**

Endpoints principaux :
- `POST /account` → création de compte
- `POST /token` → login, renvoie un JWT
- `GET /api/products` → liste des produits (protégée)
- `POST /api/cart` → gestion du panier
- `POST /api/wishlist` → gestion de la liste d’envie

---

### 2️⃣ Frontend (Angular)

```bash
cd frontend
npm install
ng serve
```

👉 Application disponible sur :  
**http://localhost:4200**

---

## 🔑 Authentification JWT

Le backend génère un **token JWT** lors du login (`/token`).  
Le front stocke ce token dans le `localStorage` et l’envoie automatiquement via un **HttpInterceptor**.

### Exemple d’utilisateur admin
```json
{
  "email": "admin@admin.com",
  "password": "admin"
}
```
Seul cet utilisateur peut ajouter, modifier ou supprimer un produit.

---

## 💡 Fonctionnalités principales

### 🏪 Partie boutique
- Affichage de tous les produits
- Filtrage par nom
- Ajout/suppression du panier
- Affichage du total et du badge panier
- Liste d’envies (wishlist)

### 👤 Partie utilisateur
- Création de compte
- Connexion avec JWT
- Déconnexion / persistance de session
- Restriction d’accès aux routes sécurisées

### 💬 Contact
- Formulaire avec validation :
  - Email obligatoire
  - Message obligatoire et ≤ 300 caractères
- Message de confirmation : *"Demande de contact envoyée avec succès"*

---

## 🧱 Configuration CORS

Le backend autorise les requêtes du front :
```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:4200")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
}
```

---

## 🧪 Tests et documentation API

- Documentation Swagger : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- Tests Postman disponibles dans `/backend/tests/`

---

## 🧰 Technologies utilisées

### Backend
- Spring Boot 3.3
- Spring Security / JWT
- Spring Data JPA / Hibernate
- Lombok
- MySQL ou H2 (selon profil)
- Maven

### Frontend
- Angular 17
- RxJS / HttpClient
- TypeScript
- SCSS
- Bootstrap 5 (ou Angular Material)

---

## 👨‍💻 Auteur

**Edwin Arias**  
Projet pédagogique — Alten 2025  
🔗 [github.com/edwinariasb](https://github.com/edwinariasb)

---

## 📝 Licence

Ce projet est distribué sous licence MIT — utilisation libre pour usage éducatif ou personnel.
