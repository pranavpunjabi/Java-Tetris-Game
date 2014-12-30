TETRIS GAME:


RULES OF GAME:
 
1) Spheres come down from the top of the game board in pairs.
   A player can move(left & right & down) and rotate them on their way down. 

2) Spheres come in  4 different colors: red, blue, green, and yellow 

3) Linking four or more spheres of the same color (horizontally, vertically or both(as in tetris)) removes them from the game board,
   allowing any sphere remaining to drop and fill the vacated space. 
   This may lead to several possible "chain combos" if additional colors match. 

4) The scoring is depends on the number of same color spheres forming tetris or chain combo and numberof chain combos formed at a single time.
   More the number more the score.

5) The level of the game automatically increased in the game progress depending on the number of spheres removed till.



//All the 12 images in the folder Image are required to launch the game

COMPILING THE SOURCE CODE AND LAUNCHING GAME:

1) The source code file Puyo_Puyo.java placed in the same directory in which this file exists.

2) The code is developed in Java and compiled and interpreted using jdk1.5 edition of java.

3) JDK must be installed to compile and run this Game.

4) It is a java code so the code compiled from command prompt.
   Open the command prompt and change directory to the directory where the source code file exists.

5) set the path to the bin directory of jdk ex: path=c:\jdk1.5\bin here jdk is instlled to the c: drive.

6) Then enter the command "javac Puyo_Puyo.java"  (ie. javac SOURCE_CODE_FILE_NAME)
   This command compiles the total code. if compilation successfull then it gives no information and
   generates corresponding class files.

7) To run the game enter the command "java Puyo_Puyo" (ie. javac CLASS_NAME)
   A window opens to launch the game.

There is also other method to compile and run the souce code.
8) Place path and javac commands(which are given in 5 and 6 steps) in a file and name it as compile.bat
   A batch file will be created, by clicking the batch file the code will be compiled.

9) To launch the game place java command(given in 7th step) in a file and name it execute.bat
   By clicking the batch file the game will be launched.

There is no need to compile every time before launching the game. Once code compiled then one can directly execute the game
everytime to launch the game.

The second method will be preferable for this game if Java runtime environment is installed in the computer.

////////////////
Rename bakup file named execute.bat.bak to execute.bat and then click the file to directly lauch the game if JRE is already installed on ur computer(because class files are already generated)
////////////////


PLAYER CONTROLS:

1) Initially use <up> and <down> arrow key to change the level of the game.

2) To start the game one must press <Enter> key.

3) Once the game is started use the <left>,<right> and <down> arrow keys to move the piece of puyos in the relative direction. 

4) pressing the <up> arrow key rotates the piece in clock wise direction by 90 degrees. 

5) press <p> to pause the game and resume the game.

6) press <escape> to exit the game.

NOTE: The window must be on focus when playing game other wise the controls can not work.

GAME IMPROVEMENTS & FURTHER ENHANCEMENT:

// These features are not added but The game will be more interesting to play by adding this features.

1) Complexity in the generating puyos depending on the level can make the game interesting.

2) Adding menus to the game will make the game looking good and provides easiness for the gamer.

I can't find any bug in my code, if any one find the bug please tell me about the bug.



