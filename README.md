# 💊 Pharmacy Management System

A desktop-based Pharmacy Management application built with **Java Swing** using OOP principles.

---

## 📁 Project Structure

```
src/
├── com/example/pharmacy/
│   └── Main.java                     ← Entry point
├── Gui/
│   ├── AdminLoginFrame.java
│   ├── DashboardFrame.java
│   ├── MainFrame.java
│   ├── BillingFrame.java
│   ├── MedicineFormDialog.java
│   ├── SearchFilterPanel.java
│   └── TableModelMedicine.java
├── Model/
│   ├── User.java
│   ├── Admin.java
│   ├── Customer.java
│   ├── Medicine.java
│   ├── PharmacyInventory.java
│   ├── SaleItem.java
│   └── Bill.java
└── Utilities/
    ├── FileManager.java
    ├── SalesManager.java
    ├── ReportGenerator.java
    ├── Utils.java
    ├── MedicineIDComparator.java
    ├── MedicineNameComparator.java
    ├── MedicinePriceComparator.java
    └── MedicineExpiryComparator.java
```

---

## ✨ Features

- 🔐 Admin Login
- 📦 Inventory Management (Add, Edit, Delete)
- 🔍 Search & Filter by name, category, price range
- 📊 Sort by ID, Name, Price, Expiry
- 🛒 Billing & Cart-based Sales
- ⚠️ Low Stock & Expiry Alerts
- 📄 Text Report Export
- 💾 Data saved via Java Serialization

---

## ▶️ How to Run

### IntelliJ IDEA
1. Open IntelliJ → **File → Open** → select project folder
2. **File → Project Structure → Modules → Sources** → mark `src` as Sources Root
3. Set JDK 17+ under **Project Structure → SDK**
4. Open `src/com/example/pharmacy/Main.java`
5. Right-click → **Run 'Main'**

### Default Login
```
Username: Waqar
Password: admin
```

---

## 🧠 OOP Concepts Used

| Concept | Where |
|---|---|
| Inheritance | `Admin` extends `User` |
| Encapsulation | Private fields + getters/setters |
| Polymorphism | 4 custom `Comparator` implementations |
| Serialization | `Medicine`, `Bill`, `PharmacyInventory` |
| Deep Copy | `Medicine.deepCopy()` |
| Shallow Copy | `Medicine.shallowCopy()` via `Cloneable` |

---

## 👤 Author
**Waqar** — OOP Project, Semester 3
COMSATS University Islamabad, Lahore Campus
