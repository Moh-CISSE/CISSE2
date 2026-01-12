========================================
        SNAKE GAME – README
========================================



----------------------------------------
1. DESCRIPTION DU PROJET
----------------------------------------

Ce projet est une implémentation du jeu Snake développée en Java
en utilisant le framework LibGDX.

Le jeu contient :
- Un menu principal
- Un menu de sélection de niveaux
- Plusieurs niveaux avec obstacles
- Un système de score
- Un système de déblocage de niveaux
- Une gestion du son (musique + effets)
- Un menu pause pendant le jeu

----------------------------------------
2. PRÉREQUIS
----------------------------------------

Avant d’exécuter le projet, assurez-vous d’avoir :

- Java JDK 8 ou plus installé
  Vérifier avec :
    java -version

- Gradle (optionnel, le projet inclut Gradle Wrapper)

- Système d’exploitation :
  - Windows (recommandé pour le son)
  - Linux / WSL (le son peut ne pas fonctionner)

----------------------------------------
3. STRUCTURE DU PROJET
----------------------------------------

- core/                 : logique principale du jeu
- assets/               : images, sons, cartes (.tmx)
- lwjgl3/               : version bureau (desktop)
- build.gradle          : configuration Gradle
- settings.gradle
- README.txt            : ce fichier

----------------------------------------
4. COMPILATION DU PROJET
----------------------------------------

Depuis la racine du projet :

Sous Windows :
    gradlew build

Sous Linux / Mac :
    ./gradlew build

----------------------------------------
5. EXÉCUTION DU JEU
----------------------------------------

IMPORTANT :
Pour avoir le son, exécutez le jeu sous Windows
(et non via WSL).

Commande d’exécution :

Sous Windows :
    gradlew lwjgl3:run

Sous Linux / Mac :
    ./gradlew lwjgl3:run

----------------------------------------
6. EXÉCUTION DU JEU A TRAVERS RUN
----------------------------------------


a. Exécution sous Linux / WSL

- Se placer à la racine du projet
- Donner les droits d’exécution (une seule fois) :
  chmod +x run.sh
- Lancer le jeu :
  ./run.sh

Remarque :
Sous WSL, le son peut ne pas fonctionner (limitation système).


b. Exécution sous Windows

- Copier le projet dans un dossier Windows (ex: Documents/SnakeGame)
- Double-cliquer sur run.bat
- Le jeu se compile et se lance automatiquement
- Le son fonctionne normalement sous Windows

----------------------------------------
7. COMMANDES DU JEU
----------------------------------------

Menu :
- Flèche Haut / Bas : naviguer
- Entrée            : valider

En jeu :
- Flèches directionnelles : déplacer le serpent
- P                 : pause / reprendre
- Échap             : retour au menu principal

Fin de partie :
- R : recommencer le niveau actuel
- N : niveau suivant (si débloqué)

----------------------------------------
8. SYSTÈME DE NIVEAUX
----------------------------------------

- Le joueur commence au niveau 1
- Les niveaux suivants se débloquent uniquement
  après avoir terminé le niveau précédent
- En cas de défaite, le joueur recommence
  le niveau en cours (pas de retour au niveau 0)

----------------------------------------
9. GESTION DU SON
----------------------------------------

- Musique de fond
- Son lorsqu’un fruit est mangé
- Son de victoire
- Son de défaite

Le son peut être activé/désactivé depuis :
- Le menu principal
- Le menu pause

----------------------------------------
10. REMARQUES IMPORTANTES
----------------------------------------

- Le jeu utilise des cartes Tiled (.tmx)
- Les collisions sont gérées par cases (tiles)
- Les ressources doivent rester dans le dossier assets/

----------------------------------------
11. PROBLÈMES CONNUS
----------------------------------------

- Le son ne fonctionne pas sous WSL
  Solution : exécuter le projet directement sous Windows
