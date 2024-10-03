# DinoGame
This repository contains a Java implementation of the classic Google Dinosaur game. The game is based on the original design, with a dinosaur running through a randomly generated landscape, avoiding obstacles and collecting points.

The game is developed using Java and JavaFX.

# Getting Started

### Features:

For further reference, please consider the following sections:

- **Faithful implementation**: The game resembles the original in terms of gameplay and aesthetics.
- **Clean and well-documented code**: The code is written in Java and is well documented for easy understanding and maintenance.

### How to play

* Use arrow keys or WASD scheme to control the dinosaur.
* Avoid the obstacles to get points.
* The game ends when the dinosaur loses all his lives when it hits an obstacle.

### How to run
* first, you need to build the game.
* You can run the game by executing the `DinoGame.jar` file in the `out/artifacts/DinoGame_jar` directory.
* You can also run the game using the command `java -jar out/artifacts/DinoGame_jar/DinoGame.jar` in the terminal.

### How to compile
* You can compile the game by executing the command `javac -d out -sourcepath src -cp out;lib/*.jar src/*.java` in the terminal.
* You can also compile the game using an IDE like IntelliJ IDEA.

### How to create a jar file
* You can create a jar file by executing the command `jar cvf out/artifacts/DinoGame_jar/DinoGame.jar -C out/ .` in the terminal.
* You can also create a jar file using an IDE like IntelliJ IDEA.
