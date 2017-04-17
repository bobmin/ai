Experimente mit künstlicher Intelligenz auf der Grundlage von rundenbasierenden Brettspielen

https://help.github.com/articles/basic-writing-and-formatting-syntax/

# gsrv

Ein Server mit REST-Schnittstellen zum Betrieb der KI im Netzwerk.

* https://jersey.java.net/documentation/1.19.1/getting-started.html
* http://www.tutorialspoint.com/restful/restful_quick_guide.htm
* http://www.codingpedia.org/ama/tutorial-rest-api-design-and-implementation-in-java-with-jersey-and-spring/
* https://addons.mozilla.org/de/firefox/addon/restclient/
* http://www.codingpedia.org/ama/how-to-test-a-rest-api-from-command-line-with-curl/

## API

```
GET  /player?name=xxx .. Spielernamen suchen
PUT  /player ........... Spieler hinzufügen
GET  /player/1 ......... Spieler anzeigen
POST /player/1 ......... Spieler aktualisieren
/game ............. Spieleübersicht
/game/1 ........... Spiel 1
/game/1/field ..... Übersicht vom Spielbrett des ersten Spiels
/game/1/field/1 ... das erste Spielfeld beim ersten Spiel
```

Bei Übersichten erfolgt mit **PUT** eine Neuanlage.

Mit **POST** werden die Einträge geändert.

Die Standardrepräsentation werden mit JSON beschrieben.

# Tic-Tac-Toe

https://de.wikipedia.org/wiki/Tic-Tac-Toe

# nn

* http://cs231n.github.io/optimization-2/
* https://github.com/WilliamOnVoyage/NeuralNetwork
* https://github.com/andrey9594/NeuronNetwork

## SimpleDemo

```shell
mkdir bobmin_ai_demo && cd bobmin_ai_demo
git clone https://github.com/bobmin/ai
cd ai/nn/
ant
java -cp dist/nn-*.jar bob.demo.SimpleDemo
``` 

Mit dem Befehl "git pull" das Verzeichnis ggf. aktualisieren und erneut "ant" aufrufen. 

Der Befehl "ant clean" löscht die Verzeichnisse "build" und "dist".
