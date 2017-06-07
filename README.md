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

## Compilation de l'application (Générer .apk)

Avant de pouvoir installer l'application, il faut pouvoir générer le .apk
correspondant. Pour simplifier l'installation, on va installer la version debug
de l'application (sinon il faut signer le .apk et c'est plus complexe...)

Dans Android Studio, ouvrir le projet puis :
* Build > Select Build Variant > choisir le module app avec le Build Variant debug
* Build > Build apk
La compilation se lance, à la fin le .apk devrait être généré dans app/build/outputs/apk

https://developer.android.com/studio/run/ind

## Installation sur tablette

* Brancher la tablette sur votre ordinateur et copier le .apk dans un de ses dossier (Download par exemple)
* Ouvrir l'application File Commander ou tout autre explorateur de fichier sur la tablette...
* Chercher le .apk, l'ouvrir et suivre les consignes d'installation... après ça, l'application devrait être installée sans problème

## Accès menu caché

Dans l'application, pour parametrer l'adresse MAC de l'imprimante, les adresses IP et PORT du Raspberry ou du serveur de backup :
* Appuyer 10 fois très rapidement sur le bouton de rafraîchissement

Une fois dessus, parametrez et enregistrer... les changements sont sauvegardés même après fermeture de l'app.