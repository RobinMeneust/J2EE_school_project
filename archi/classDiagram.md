# Class Diagram

```mermaid
classDiagram
    User <|-- Client
    User <|-- Moderator
    Moderator <|-- Administrator
    Client --> Address
    Client --> LoyaltyAccount
    %%LoyaltyAccount "*" --> "1" Discount : -discounts%%
    Product --> Category : -category
    Category --> Discount : -discount
    LoyaltyAccount o--> "*" LoyaltyProgram : loyaltyProgram
    LoyaltyProgram o--> "*" LoyaltyLevel : loyaltyLevel




class User{
        <<abstract>>
        -int id
        -String firstName
        -String lastName
        -String email
        -String phone_number
    }
    
    class Client{
        -Address address
        -FidelityAccount fidelityAccount
    }
    
    class Administrator{
        +getClients() List~Client~
        +cancelSubscription(Client client)
        +addModerator(...)
        +addDiscount(...)
    }
    
    class Moderator{
        commentaire : suppression, mise en avance
        fidélité ?
        promo
        ajouter client
    }
    
    class AuthService{
        +logIn(String email, String password) User | boolean
        +logOut()
        +registerClient()
        +registerAdmin()
        +registerModo()
    }
    
    class Address{
        -String street_address
        -String postal_code
        -String city
        -String country
    }
    
    class Order{
        
    }
    
    class LoyaltyAccount{
        -int fidelityPoints
        -Date startDate
        -Set~LoyaltyLevel~ levelsUsed
        +getReductionFromPoints()
        +getDuration()
        +isLevelUsed(LoyaltyLevel level) boolean
        +claimLevel(LoyaltyLevel level)
        +levelAvailable() List~LoyaltyLevel~
    }
    
    class LoyaltyLevel{
        -int id
        -int requiredPoints
    }
    
    class loyaltyProgram{
        -Duration duration    
    }  
    
    class Discount{
        -String name
        -Date startDate
        -Date endDate
        -int fixedValue
        -int percentValue
        -Category category | null
        +getRemainingTime() Duration
    }
    
    class Product{
        -String name
        -int stock
        -float price
        -String description
        -String image
        +addStock(int quantity)
        +decrementStock(int quantity)
    }
    
    class Category{
        String name
        String description
    }
    
    
```

A voir pour les modérateurs plus tard si on a le temps.
