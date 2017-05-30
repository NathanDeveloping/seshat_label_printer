# Seshat Label Printer

Application mobile Android (Java) permettant l'impression des étiquettes via imprimante Zebra.
L'impression se fait grâce au langage de mise en forme ZPL.

## Fonctionnalités

* Impression multiple d'etiquette (liste)
* Choix du format (cryotube, flacon normal)
* Choix du nombre d'étiquettes à imprimer

## Configuration

Pour configurer l'application, un fichier est mis à disposition dans
**/app/src/main/res/values/config.xml**.
On peut y spécifier l'adresse locale du Raspberry (spécifiée pendant l'installation
de Seshat, défaut *10.10.10.10*) ainsi que le port (défaut *8080*) et l'adresse
MAC de l'imprimante.

## Compilation et signature de l'application

Avant de pouvoir installer l'application, il faut pouvoir générer le .apk
correspondant et le signer.

https://developer.android.com/studio/run/index.html
https://developer.android.com/studio/publish/app-signing.html

## Installation sur tablette
