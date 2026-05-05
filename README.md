# Employee Application

Application de gestion des employés construite avec Spring Boot,
suivant les bonnes pratiques de développement professionnel.

## Architecture

Monolithique en couches avec séparation stricte des responsabilités :

Controller → Service → Repository
DTO (Request/Response) → Mapper → Entity

## Fonctionnalités
- CRUD complet : créer, lire, modifier, supprimer un employé
- Validation et gestion d'erreurs via exceptions métier personnalisées
- Séparation Request/Response DTO avec Mapper dédié
- Tests unitaires complets (JUnit 5 + Mockito + AssertJ)

## Tests
Couverture des cas nominaux et cas d'erreur :
- getAllEmployees : liste complète et liste vide
- getEmployeeById : trouvé et NotFoundException
- createEmployee : sauvegarde et retour DTO
- updateEmployee : mise à jour et NotFoundException
- deleteEmployee : suppression et NotFoundException
