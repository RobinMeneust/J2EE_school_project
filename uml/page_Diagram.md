# Pages Diagram

```mermaid
flowchart
    A([Accueil])
    B([sign in/up])
    C([mdp oublié])
    D([Catalogue])
    E([Produit])
    F([Panier])
    G([Validation commande])
    H([Confirmation d'achat])
    I([Profil])
    A <--> B
    B <--> C
    A <--> D
    A <--> E
    D <--> E
    E --> F
    D <--> F
    F --> G
    G --> H
    H --> A
```

```mermaid
flowchart
    A([Banière])
    B([Accueil])
    F([Catalogue])
    C([Sign in/up])
    K([dashboard])
    D([Profil])
    E([Panier])
    
    G([mdp oublié])
    H([Produit])
    I([Validation commande])
    J([Confirmation d'achat])
    
    L([Pages produits])
    M([Pages clients])
    N([Pages catégories])
    O([Promotions])
    P([Modifier client])

    A --> B
    A --> F
    A --> C
    A ----> K
    A --> D
    A --> E
    
    B --> H
    E --> I
    I --> J
    F --> H
    C --> G
    
    K --> L
    K --> M
    K --> N
    K --> O
    M --> P
```

## Admin

Liste des parties
- Clients
  - lister
  - gérer les droits / avantages
  - ajouter / supprimer
- catalogue
  - lister
  - ajouter
  - supprimer
  - modifier
- catégories
  - ajouter
  - supprimer
  - modifier

Programme par palier
