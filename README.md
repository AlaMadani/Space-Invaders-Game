# Space_Invaders_Patterns_Edition

## Description
Ce projet est une réimplémentation avancée du célèbre jeu d'arcade *Space Invaders*, développée en Java avec JavaFX. L'objectif principal est la mise en œuvre stricte d'une architecture logicielle basée sur les **Design Patterns** (GoF), tout en offrant une expérience de jeu moderne et dynamique.

Contrairement aux versions classiques, cette édition propose une liberté de mouvement totale (verticale et horizontale), des graphismes procéduraux (dessinés par le code sans images externes), et des mécaniques de collision physiques.

## Membres du Groupe
* Benothmen Youssef
* Madani Ala Eddine

## Technologies Utilisées
* **Langage :** Java 17
* **Framework GUI :** JavaFX (OpenJFX 21)
* **Logging :** Log4j2 (Format strict [TIMESTAMP] [TYPE] Message)
* **Build & Gestion de dépendances :** Maven
* **Architecture :** MVC simplifié (GameEngine / States)

## Design Patterns Implémentés
Ce projet intègre 6 patrons de conception majeurs, organisés dans des packages dédiés :

1.  **State Pattern** (`patterns.state`) :
    * Gère les phases du jeu : `MenuState` (Animation d'intro), `PlayingState` (Jeu en cours), `PausedState` (Pause/Reprise), et `GameOverState`.
2.  **Composite Pattern** (`patterns.composite`) :
    * `AlienSquad` gère une flotte entière d'aliens (`RedAlien`, `GreenAlien`). Permet un déplacement synchronisé et une gestion de groupe complexe.
3.  **Decorator Pattern** (`patterns.decorator`) :
    * `ShieldDecorator` enveloppe le vaisseau du joueur (`SpaceShip`) pour lui ajouter une invincibilité temporaire et un visuel unique, sans modifier la classe de base.
4.  **Factory Method** (`patterns.factory`) :
    * `AlienFactory` centralise la création des ennemis, permettant d'étendre facilement les types d'aliens.
5.  **Singleton Pattern** (`patterns.singleton`) :
    * `GameEngine` (Moteur unique) et `ScoreManager` (Gestion unique du score).
6.  **Observer Pattern** (`patterns.observer`) :
    * Le `ScoreManager` notifie le `PlayingState` (Observer) des changements de score pour mettre à jour l'interface et débloquer des bonus.

## Nouveautés Techniques & Graphiques
* **Graphismes Procéduraux :** Aucun fichier image externe n'est utilisé. Tout (vaisseau, aliens, flammes, étoiles) est dessiné en temps réel via l'API JavaFX Canvas (`GraphicsContext`).
* **StarField Dynamique :** Un système de fond étoilé animé avec effet de parallaxe.
* **Mouvement Fluide :** Gestion des entrées clavier via un `HashSet` permettant le déplacement diagonal et le tir simultané.

## Installation et Exécution

### Prérequis
* JDK 17 ou supérieur
* Maven 3.6+

### Étapes
1.  Cloner le dépôt :
    ```bash
    git clone [URL_DE_VOTRE_DEPOT]
    ```
2.  Compiler et lancer le jeu via Maven :
    ```bash
    mvn clean javafx:run
    ```

## Utilisation (Contrôles)

### En Jeu
* **Flèches DIRECTIONNELLES (Haut/Bas/Gauche/Droite)** : Déplacement libre du vaisseau.
* **ESPACE** : Tirer (Laser plasma).
* **Touche B** : Activer le Bouclier.
    * *Condition :* Nécessite une charge de bouclier (gagnée tous les 1000 points).
    * *Effet :* Invincibilité pendant 10 secondes.
* **Touche P** : Mettre le jeu en Pause.

### Menu Pause
* **Touche P** : Reprendre la partie.
* **Touche R** : Redémarrer le niveau (Reset du score).
* **Touche M** : Retourner au Menu Principal.

### Menu Principal / Game Over
* **ENTRÉE** : Démarrer ou Rejouer.

## Fonctionnalités du Jeu
* **Dogfight Spatial :** Le joueur peut monter et descendre pour esquiver ou attaquer, mais attention aux collisions physiques !
* **Système de Crash :** Le jeu se termine si le joueur percute physiquement un Alien ou un Bunker.
* **Intelligence de Groupe (Composite) :** Les ennemis accélèrent et changent de comportement de manière aléatoire (jitter) tout en restant en formation.
* **Système de Score & Bonus :** Cumuler des points permet de stocker des charges de bouclier stratégiques.
* **Vagues Infinies :** La difficulté augmente (plus d'ennemis) à chaque niveau complété.