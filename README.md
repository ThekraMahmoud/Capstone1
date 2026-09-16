## Additional Features I Added

In addition to the main requirements of the E-Commerce System, I added several extra features to improve the user experience and make the system more interactive.

### 1. Family System

I added a **Family System** that allows users to connect with other users as family members.

The system supports:

* Sending a family request from one user to another.
* Accepting or rejecting a pending family request.
* Viewing the user's accepted family relationships.
* Checking family relationships and their current status.
* Preventing duplicate family requests and preventing a user from sending a request to themselves.

I implemented the family system using the existing `User` model without adding a new class.

---

### 2. Family Purchase Notification

I also extended the existing **Buy Product** requirement by adding a family-related notification.

When a user purchases a product, the system checks whether any of their **accepted family members** have already purchased the same product from the same merchant.

If a match is found, the purchase response informs the user that a family member has already purchased that product.

For example:

```text
true - Family members: [U003]
```

This feature was added as an extension to the original purchase requirement.

---

### 3. Family Product Check

I added a separate endpoint that takes:

* `User ID`
* `Product ID`
* `Merchant ID`

The system then checks the user's accepted family members and compares their previous purchases.

It returns the family members who purchased the **same product from the same merchant**.

This allows the system to provide family-based product information without adding an `Order` class.

---

### 4. Trending Categories

I added a **Trending Categories** feature based on the purchase history of the system.

The endpoint does not require any input from the user. Instead, it automatically checks the previous orders, connects each purchased product to its category, and counts how many orders belong to each category.

If a category has **more than four orders**, it is added to the Trending list.

The logic is:

```text
Purchase History
      ↓
Find Product
      ↓
Find Product Category
      ↓
Count Orders
      ↓
Count > 4
      ↓
Add Category to Trending
```

This makes the Trending result dynamic and based on actual purchases in the system.

---

### 5. Prime Membership

I added a **Prime Membership** feature for users.

A user can purchase Prime membership for **100 SAR**. After becoming a Prime member, the user receives a discount on products they purchase.

The system checks whether the user is a Prime member during the purchase process and applies the Prime discount automatically.

Prime membership is stored separately from the `User` model, so I did not need to add another field to the existing `User` class.

---

### 6. Product and Merchant Relationships

I also worked with the relationship between **Products and Merchants** through `MerchantStock`.

A product can be associated with a specific merchant, and the `MerchantStock` keeps track of:

* Product ID
* Merchant ID
* Available stock

When a user purchases a product, the system verifies that the selected merchant actually has that product and then decreases the available stock according to the purchased quantity.

---

### Summary

The extra features I added focus mainly on **family-based shopping, purchase history, product relationships, trending categories, and Prime membership**. These additions extend the original E-Commerce requirements while keeping the existing models and structure of the project simple.
