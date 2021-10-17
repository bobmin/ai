Experimente mit künstlicher Intelligenz auf der Grundlage von rundenbasierenden Brettspielen

https://help.github.com/articles/basic-writing-and-formatting-syntax/

[Was ist Machine Learning? Definition, Algorithmen und Beispiele von maschinellem Lernen](https://datadrivencompany.de/machine-learning-definition-algorithmen-und-beispiele/)

# gsrv

Ein Server mit REST-Schnittstellen zum Betrieb der KI im Netzwerk.

* https://jersey.java.net/documentation/1.19.1/getting-started.html
* http://www.tutorialspoint.com/restful/restful_quick_guide.htm
* http://www.codingpedia.org/ama/tutorial-rest-api-design-and-implementation-in-java-with-jersey-and-spring/
* https://addons.mozilla.org/de/firefox/addon/restclient/
* http://www.codingpedia.org/ama/how-to-test-a-rest-api-from-command-line-with-curl/

![GSRV Libs](https://github.com/bobmin/ai/blob/master/gsrv_libs.gif)

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

* https://de.wikipedia.org/wiki/Tic-Tac-Toe
* https://blog.codecentric.de/en/2018/01/gamma-tictactoe-neural-network-machine-learning-simple-game/

# nn

* http://cs231n.github.io/optimization-2/
* https://github.com/WilliamOnVoyage/NeuralNetwork
* https://github.com/andrey9594/NeuronNetwork
* [Convolutional Neural Networks with Deeplearning4j](https://aboullaite.me/cnn-dl4j/)

## Getting started

```shell
mkdir bobmin_ai_demo && cd bobmin_ai_demo
git clone https://github.com/bobmin/ai
cd ai/nn/
ant
java -cp dist/nn-*.jar bob.demo.XyzDemo
``` 

Der Befehl "git pull" im Verzeichnis "ai" aktualisiert die lokale Kopie und der Aufruf "ant" im Verzeichnis "nn" übersetzt die Quellen. 

Mit "ant clean" werden die Verzeichnisse "build" und "dist" gelöscht.

## SimpleDemo

## BackpropagationDemo

* [Neuronale Netze - Backpropagation - Forwardpass](https://youtu.be/YIqYBxpv53A)
* [Neuronale Netze - Backpropagation - Backwardpass](https://youtu.be/EAtQCut6Qno)
* [Create an artificial neural network using the Neuroph Java framework](https://blog.codecentric.de/en/2018/01/gamma-tictactoe-neural-network-machine-learning-simple-game/)
* [Implementing an Artificial Neural Network in Pure Java](https://medium.com/coinmonks/implementing-an-artificial-neural-network-in-pure-java-no-external-dependencies-975749a38114)

# fxg

* [Introduction to JavaFX for Game Development](https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835)
* [Processing Reference](https://processing.org/reference/)
* [JavaFX Game Development Framework](https://github.com/AlmasB/FXGL)
