
# **System Design Document – Affordmed URL Shortener**

## **1. Overview**

Affordmed URL Shortener is a full-stack web application designed to generate short, user-friendly URLs from long URLs. Users can optionally set a custom shortcode and specify link validity. The system redirects users from short URLs to the original URLs, with support for link expiration.

---

## **2. Architecture**

### **2.1. Frontend (Next.js)**

* **Framework:** Next.js (React-based)
  **Justification:** Supports server-side rendering (SSR) for faster page loads, dynamic routes for short URL redirection, and integration with API endpoints.
* **State Management:** `useState` and `useEffect` for managing URLs, custom shortcodes, and API results.
* **API Communication:** Axios for HTTP requests to backend endpoints.
* **UI:** Vanilla HTML + CSS (no third-party UI library) for simplicity and maintainability.
* **Routing:** Dynamic routing for short URLs using `[shortcode].tsx`.

### **2.2. Backend (Spring Boot)**

* **Framework:** Spring Boot with REST API architecture.
  **Justification:** Robust, production-ready, widely used for scalable backend services.
* **Controller-Service-Model Pattern:**

  * **Controller:** Handles incoming requests, response formatting, and validation.
  * **Service:** Encapsulates business logic (shortcode generation, URL expiry handling).
  * **Model:** Represents `ShortUrl` objects with attributes: `shortcode`, `originalUrl`, `expiryDate`.
* **Persistence:** Initially in-memory storage (HashMap) for prototyping; easily replaceable with a database for production.
* **CORS Configuration:** Enabled for local frontend-backend communication.

---

## **3. Data Modeling**

**ShortUrl Entity**

| Attribute   | Type   | Description                      |
| ----------- | ------ | -------------------------------- |
| shortcode   | String | Unique identifier for short URL  |
| originalUrl | String | Original long URL                |
| createdAt   | Date   | Timestamp when URL was shortened |
| expiryDate  | Date   | Optional expiry date             |

**Assumptions:**

* Shortcodes are unique; collisions are handled via regeneration.
* Expired URLs return a 404 error.
* Validity is optional; defaults to 30 minutes if not specified.

---

## **4. Key Design Decisions**

| Decision                           | Justification                                    |
| ---------------------------------- | ------------------------------------------------ |
| **Frontend framework: Next.js**    | SSR + API integration + dynamic routing          |
| **Backend framework: Spring Boot** | Robust REST API + easy dependency injection      |
| **Axios for HTTP requests**        | Simple promise-based HTTP client                 |
| **No external UI libraries**       | Lightweight, minimal dependencies                |
| **Custom shortcodes**              | Allows user personalization                      |
| **In-memory storage initially**    | Fast prototyping; easily switchable to DB        |
| **Dynamic routing for redirect**   | Supports `/shortcode` path mapping for redirects |

---

## **5. Scalability & Maintainability**

* **Frontend:** Component-based architecture, reusable input fields, dynamic state handling.
* **Backend:** Separation of concerns (Controller, Service, Model), future-proof for database integration and caching (Redis for frequently accessed short URLs).
* **API Design:** RESTful principles allow easy integration with other clients or mobile apps.
* **Error Handling:** Both client and server validate input; backend returns meaningful HTTP status codes.

---

## **6. Technology Stack**

| Layer        | Technology/Library | Reason                                         |
| ------------ | ------------------ | ---------------------------------------------- |
| Frontend     | Next.js + React    | SSR, dynamic routing, modern React features    |
| HTTP Client  | Axios              | Promise-based API calls                        |
| Backend      | Spring Boot        | REST API, scalability, dependency injection    |
| Build Tools  | Maven / Node.js    | Dependency management for backend and frontend |
| Version Ctrl | Git + GitHub       | Source control, combined frontend-backend repo |

---

## **7. Assumptions**

* All URLs are valid HTTP/HTTPS links.
* Shortcodes are alphanumeric and case-insensitive.
* Single instance prototype; no horizontal scaling implemented yet.
* In-memory data store is sufficient for initial MVP.

---

## **8. Future Enhancements**

* Add persistent database (MySQL/PostgreSQL) for durability.
* Use Redis caching for high-traffic URLs.
* Implement analytics (click count, geolocation).
* User authentication for private URL management.
* Rate limiting and anti-abuse mechanisms.

---

