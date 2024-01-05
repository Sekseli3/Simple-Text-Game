# Osmon
   ####          #####        ####       ###           ###      ####     ###        ##
 ##    ##     ##            ##    ##     ## ##       ## ##    ##    ##   ## ##      ##
##      ##      ###        ##      ##    ##  ##     ##  ##   ##      ##  ##   ##    ##
##      ##         ###     ##      ##    ##   ##   ##   ##   ##      ##  ##    ##   ##
##   ##              ##    ##    ##     ##    ## ##    ##    ##    ##   ##      ## ##
 ####           #####        ####       ##     ###     ##      ####     ##        ###




Simple text game made with scala. Ideas of 2 games (pokemon and swords in sandals) merged together. Turn based combat where fighting and  moving done trough text UI. 




	------------------Table of Contents:
	1.----------What is Osomon adventure
	2.----------------------How it works
	3.--------------------------Contents
	3.1---------------------adventure.ui
	3.2------------------------adventure
	3.2.1----------------Adventure.scala
	3.2.2-------------------Action.scala
	3.2.3-------------------Player.scala
	3.2.4-------------------Osomon.scala
	3.2.5---------------------Area.scala
	3.2.6-----------------Opponent.scala
	3.2.7-------------------Battle.scala



    1.What is Osomon adventure?
Osomon Adventure is a textgame, mimicking the hugely popular Pokemon series, just with less details and features.
The point in this game is the same as in the actual Pokemon games, to become the champion.

    2.How it works
So in Osomon, every action is done by using text commands that are written in a console style ui or just the actual console.
One can move around, catch Osomon, battle and collect items utilizing different commands. 

    3.Contents
    
    3.1 adventure.ui
The adventure.ui folder holds both the console text ui and the other, windowed ui.
It is separated from the actual background tasks that make the game function(as it should be)

    3.2 adventure
The actual adventure folder holds all the necessary components to make the program function properly. 
These include all the classes and traits that will be used. Next I will introduce the most important ones.

    3.2.1 Adventure.scala
  This class represents the actual adventure. It's responsibilities include creating the areas, player,items, winconditions,
  adding the Osomon to the area, as well as playing the turns. It utilizes the help of other classes to create these features.
    
    3.2.2 Action.scala
  Action class represents the actions taken by the player. It's main purpose is to decide the right action/method to be used
  when user writes a specific command. It leaves the implementation in either the hands of the player class, or the battle class.
  
    3.2.3 Player.scala
  This class creates the player and the methods it has. As it has the actual solid implementations for most of the actions in the game
  and therefore it is quite large. It features also a wide array of variables ranging from the team the player has to the players location.

    3.2.4 Osomon.scala
  Osomon class functions as a superclass for all the types of Osomon there are. All of the necessary methods and functions are created
  in this class to make creating new Osomon super easily.(One just has to set parameter base values for the new class, and that's it)
  This classes methods are mostly used by the Battle trait and it's subclasses.
  
    3.2.5 Area.scala
  Area classes main function is to hold information about it's neighbors, and any items or trainers that are in the area. It's one of
  the more simpler classes with less functionality.
  
    3.2.6 
  Opponent class is for the "human" opponents the player will face during their journey. It features some of the methods that will
  be used by the TrainerBattle class.
  
    3.2.7 Battle.scala
  And lastly, the Battle trait. It functions as a trait for 2 classes: Encounter.scala and TrainerBattle.scala They represent the battle
  player can be a part of. There is a wild Osomon in an encounter, and a trainer in TrainerBattle They hold information regarding the parties in the battle, 
  and play the turns out so that the battle advances. Also opponent "AI" is part of this class instead of the opponent class.
