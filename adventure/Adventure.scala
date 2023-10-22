package o1.adventure

import scala.collection.mutable

class Adventure {

  val title = "Seikkailu Sandaaleissa"

  /** Initialize areas */
  private val Bar  =  new Area("Bar", " Rotting old house witch smells like beer. Here you got robbed.")
  private val Town =  new Area("Town", " A moderate sized town known for its gladiator arena.")
  private val Arena = new Area("Arena of Evil" , " A Gladiator arena. Designed by Alvar Aalto. Fierce battles are held here.")
  private val Shop =  new Area("Shop of Ollurs12", " A shop where you can buy goodies from the shopkeeper Ollurs12")

  private val destination = Bar

  /** Connect areas */
  Bar.setNeighbors  (Vector("south" -> Town))
  Town.setNeighbors (Vector("east" -> Arena, "south" -> Shop, "north" -> Bar))
  Arena.setNeighbors(Vector("west" -> Town))
  Shop.setNeighbors (Vector("north" -> Town))

  /** Creates player */
  val player = new Player(Bar)

  /** Initialize items */
  val saber =          new Item("saber", "Splendid saber of the ethernal ghost: A saber forged by the 12 ancient ghosts",                            1.3, 1.0, 1.5, 130)
  val axe =            new Item("axe"," Axe of Gimli the dwarf.",                                                                                    1.5, 1.1, 1.0, 110)
  val sword =          new Item("sword"," Evil banes' two-handed sword: It is said that eaven a glimpse of this sword is enough to cut you in half", 1.4, 1.0, 1.2, 120)
  val talisman =       new Item("talisman"," Mystic talisman of the ancient human: Crafted by the ancinet human. Said to give godly powers",         1.5, 1.5, 1.5, 300, 2)
  val shield =         new Item("shield"," Barbaric ruler's tower shield: A shield as long as the shopkeeper",                                       1.0, 2.0, 1.0, 200, 3)
  val abyssimalArmor = new Item("mail", " Abyssimal lords' banded mail of ice circles: A pitch black set of armor.",                                 1.0, 1.7, 1.2, 500, 4)
  val hellishArmor =   new Item("armor"," Hellish artist's quilter armor of force zone: Blazing armor bright enought to blind your foe",             1.2, 1.2, 1.1, 200, 4)
  val hauntedArmor =   new Item("bladearmor", " Haunted bladearmor: Armor with blades all over",                                                     1.3, 1.1, 1.0 ,300, 4)

  /**Add items to shop*/
  Shop.addItem(saber)
  Shop.addItem(axe)
  Shop.addItem(sword)
  Shop.addItem(talisman)
  Shop.addItem(shield)
  Shop.addItem(abyssimalArmor)
  Shop.addItem(hellishArmor)
  Shop.addItem(hauntedArmor)

  /** Initialize enemies */
  val opponent1 = new Gladiator(10, 25, 25, 25, "Tyler from highschool", mutable.Buffer[Item]())
  val opponent2 = new Gladiator(13, 30, 25, 30, "Samittaja69"          , mutable.Buffer[Item]())
  val opponent3 = new Gladiator(20, 20, 25, 60, "Ronnie Coleman"       , mutable.Buffer[Item]())
  val opponent4 = new Gladiator(25, 40, 40, 50, "Masi Pallopää"        , mutable.Buffer[Item]())
  val opponent5 = new Gladiator(35, 50, 50, 60, "Admirall Bulldog"     , mutable.Buffer[Item]())
  val opponent6 = new Gladiator(69, 70, 70, 61, "The BigHefe"          , mutable.Buffer[Item]())


  /** Add fighters to Arena */
  Arena.addFighter(opponent1)
  Arena.addFighter(opponent2)
  Arena.addFighter(opponent3)
  Arena.addFighter(opponent4)
  Arena.addFighter(opponent5)
  Arena.addFighter(opponent6)

  /** Sets a turn limit for the game */
  var turnCount = 0
  val timeLimit = 100

  /** Plays a turn */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) {
      this.turnCount += 1
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }

  /** Plays a battle turn */
  def playBattleTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.battle(this.player)
    outcomeReport.getOrElse("You can't do that in a battle!")
  }


  /** Checks whether the game is over */
  def isOver = this.player.isChampion || this.player.hasQuit || this.player.hasLost || this.turnCount == this.timeLimit

  /** Displays a message at game start */
  def welcomeMessage = " Welcome to Seikkailu Sandaaleissa! \nAre you capable of becoming the strongest fighter?  \nGo and test your luck in the arena! \nhelp for a list of commands"

  /** Displays a goodbye message after game is over */
  def goodbyeMessage = {
    if (this.player.isChampion) "Wow you did it!"
    else if (this.turnCount == this.timeLimit) "Oh no! Time's up\nGame over!"
    else "You lost. Game over!"
  }
}
