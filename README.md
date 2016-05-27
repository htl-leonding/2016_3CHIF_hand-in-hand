
<h1 align="center">
Hand-In-Hand!
</h1>

<p align="center"><img src="http://i.imgur.com/j1xdPfO.jpg?1"></p>


Die Bedienung eines Computers ist neben Lesen, Schreiben und Rechnen längst zu einer alltäglichen Sache geworden. Menschen wie Stephen Hawking oder Prof. Sang-Mook Lee zeigen, wie wichtig es ist, allen Menschen mit Behinderung einen barrierefreien Weg in die Welt und Nutzung von Computer und Internet zu gewährleisten. Menschen mit Behinderung sind in der Nutzung des Computers mit Barrieren konfrontiert. Speziell die Bedienung von Tastatur und Maus macht ihnen große Schwierigkeiten aufgrund fehlender Präzision und oder Konzentration. Dies macht ihnen die Verwendung von Computer schwer bis zu unmöglich.

***

##Einführung

* Hand in Hand ist ein unterstützendes System für beeinträchtigte Personen.
* Unsere Software ist dafür gedacht Menschen, die nicht mit der Tastatur zurecht kommen, zu helfen.
* Dies machen wir möglich indem wir die Tastatur viel größer und robuster nachbauen.
* Unser Gerät kann man ohne größere Probleme überall mitnehmen wo es zum Einsatz kommen soll.
* Die größten Vorteile unserer Software sind die hohe Flexibilität, leichte Verwendung und vielseitige Nutzbarkeit.

##Grundlagen

* Hand in Hand – Derzeitige [Version 3.1](https://github.com/htl-leonding/2016_3CHIF_hand-in-hand/wiki/Versionen#version-31)
* Unsere Software ermöglicht es schnell und einfach Tasten von einer herkömmlichen Tastatur auf unserem EazyKeyboard zu konfigurieren
* 3-Funktionen: Linke Pfeiltaste – Leertaste – Rechte Pfeiltaste
* Ein 7 zoll Display erleichtert die Handlichkeit und die Mitnahme des Gerätes.
* RXTX-Bibliothek ermöglicht uns über den Seriellen Port zu kommunizieren

##Unsere Ziele

Den Menschen soll es durch unser Projekt viel leichter fallen zwischen Fotos oder Videos zu wechseln. Ziel ist es das reibungslos laufende Programm auf vielen Systemen verwenden zu können. Der Benutzer soll mittels den Tasten auf dem Arduino in einem Video-, Bild- oder Musikwiedergabeprogramm verschiedene Funktionen ausführen können. Diese Funktionen kann der Benutzer selbständig einer Taste zuweisen.

<p align="center">
   <img src ="http://i.imgur.com/1X0g6uJ.png">
</p>

***

#Aktuell (Version 3.1)

* Holzbox mit Buttons und Display
* EazyKeyboard leicht konfigurierbar
* 3-Funktionen: Linke Pfeiltaste – Leertaste – Rechte Pfeiltaste
* Neue [Feautures](https://github.com/htl-leonding/2016_3CHIF_hand-in-hand/wiki/Feautures) für den Benutzer
* RXTX-Bibliothek für die serielle Kommunikation
* 7 Zoll Display mit Touch
* Mehrere Sprachen
  * Deutsch
  * Englisch

Rohentwurf der Box und Button:                

![Holzbox](http://i.imgur.com/XoshqZr.jpg?1 =460x280)
![Button](http://i.imgur.com/1cuVZUD.jpg =460x280)

##Vorgänger
###Version 2.2:
* Esp8266 mit 3 Tasten
* RXTX-Bibliothek für die serielle Kommunikation

![Version 2.2](http://i.imgur.com/WXepJFD.png?1)

###Version 1.1:
* Box mit selbstgebauten Tasten
* MakeyMakey

![Version 1.1](http://i.imgur.com/5S9Tcvp.png)

***

Ab der Version 3.1 stellen wir ein paar Feautures zur Verfügung

#PictureViewer
Dadurch kann sich der Benutzer einzelne Fotos anschauen und zwischen denen wechseln. Das Wechseln erfolgt mittels den Eingebauten Buttons.

#VideoPlayer
Mit Hilfe der Buttons kann man mit diesen Programm Videos abspielen und genießen.

#MusikPlayer
Mit diesen Programm kann der Benutzer ein Musikordner auswählen um dann die darin enthaltenen Lieder abzuspielen. Dabei kann man mittels den Buttons zum nächsten Lied vorspringen, zum vorherigen Lied wechseln oder das Lied pausieren.

#Asteroids-Game
Dabei handelt sich um ein Spiel bei dem der Benutzer einen Raumschiff steuert. Natürlich haben wir es dem Benutzer nicht leicht gemacht, man muss um ein Level abzuschließen alle Asteroide die auf einen zukommen abschießen. Mit jedem neuen Level wird das spiel schwerer und anspruchsvoller.

***

#Kurzbeschreibung
Unser ganzes Projekt wird in Java Entwickelt, dabei handelt es sich um ein Maven-Projekt.

#Struktur
<p allign="center">
    <img src="http://i.imgur.com/TW2wX0S.png">
</p>

#Klassen
##PortListener
Diese Klasse stellt die Verbindung zwischen dem Arduino und dem PC auf. Ist auch dafür da um die Eingabe der Benutzer, mit dem Buttons, zu bearbeiten und an die Controller Klasse weiter zu geben.

Verbindungsaufbau:
<p allign="center">
    <img src="http://i.imgur.com/jSB278J.png">
</p>

Vom Port lesen:
<p allign="center">
    <img src="http://i.imgur.com/iJcw0QZ.png">
</p>

##Controller
Der Controller bearbeitet die Informationen die er von PortListener entgegennimmt. Nicht zugewiesene Buttons werden vom Controller einer Funktion zugewiesen und in einer Liste gespeichert. Falls ein Button gedrückt wird der schon in der Liste gespeichert ist wird die jeweilige Funktion ausgeführt.

Tasteneingabe verarbeiten:
<p allign="center">
    <img src="http://i.imgur.com/TrKQRvk.png">
</p>

Neue Tasten eintragen
<p allign="center">
    <img src="http://i.imgur.com/m02sirj.png">
</p>

##Key
In der Klasse Key wird ein Button mit der jeweiligen Funktion gespeichert und im Controller in einer Liste geführt.

#Programmablauf
<p allign="center">
    <img src="http://i.imgur.com/FPD4HOS.png">
</p>

#Bibliotheken
##RXTX
Ermöglicht die serielle und parallele Kommunikation mit der JDK. RXTX wurde Anfang 1997 Entwickelt und wurde seitdem immer weiter bearbeitet. Die neuste Verion ist rxtx-2.1-7, die Anfang 2006 veröffentlicht wurde. Mehr Informationen über diese Bibliothek finden man [hier](http://users.frii.com/jarvi/rxtx/index.html).

***
* [Home](https://github.com/htl-leonding/2016_3CHIF_hand-in-hand/wiki/Home)
* [Über das Projekt](https://github.com/htl-leonding/2016_3CHIF_hand-in-hand/wiki/%C3%9Cber-das-Projekt)
* [Versionen](https://github.com/htl-leonding/2016_3CHIF_hand-in-hand/wiki/Versionen)
* [Feautures](https://github.com/htl-leonding/2016_3CHIF_hand-in-hand/wiki/Feautures)

