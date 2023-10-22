package o1.adventure

import scala.collection.mutable.Buffer


/** A `Player` object represents a player character controlled by the real-life user of the program.
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area) {

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val inventoryList = Buffer[Item]()

  /** Initializes mutable values */
  var money = 0
  var hasLost = false
  var currentBattle: Option[Battle] = None
  val equipped = Buffer[Item]()
  var isChampion = false
  var currentPlayer = new Gladiator(10, 20, 17, 20, "MC", equipped.toBuffer)

  /** Checks whether there is a battle going on */
  def isInBattle = currentBattle.isDefined

  /** Increases the player characters stats by 5 */
  def strengthenFighter = {
    currentPlayer = new Gladiator(currentPlayer.baseHp + 5, currentPlayer.baseAtk + 5, currentPlayer.baseDef + 5, currentPlayer.baseSpd + 5, "MC", equipped)
  }

  /** Returns a list of commands */
  def help = {
    "equip: equips item if it exists in the inventory \nunequip: unequips item \ninventory: returns a listing of posessions \ndrop: discards item from inventory \nbuy: attempts to purchase given item " +
    "\ngo: attempts to move toward the given direction (uses cardinals) \nchallenge: challenges an opponent to a battle \nrest: heals to full \ncheat: makes player character invincible " +
    "\nmoney: displays money \ndescription: returns description of item \nquit: quits the game"
  }

  /** Returns the description of an item in the player's iventory */
  def description(itemName: String) = {
    val item = inventoryList.find(_.name == itemName)
    if (item.isDefined) item.get.description
    else "Failed to retrieve description of " + itemName
  }

  /** Returns equipped items */
  def equippedItems = {
    var result = ""
    for (item <- equipped) result += item.name + "\n"
    result
  }


  /** Equips an item if it exists in player's inventory */
  def equip(itemName: String): String = {
    val item = inventoryList.find(_.name == itemName)
    if (item.isDefined) {
      if (equipped.forall(_.itemType != item.get.itemType)) {
        equipped += item.get
        currentPlayer = new Gladiator(currentPlayer.baseHp, currentPlayer.baseAtk, currentPlayer.baseDef, currentPlayer.baseSpd, "MC", equipped)
        s"Equipped $itemName."
      }
      else "Can't equip multiple items of same type."
    }
    else "Equipping failed."
  }

  /** Unequips item */
  def unequip(itemName: String): String = {
    val item = equipped.find(_.name == itemName)
    if (item.isDefined) {
      equipped -= item.get
      currentPlayer = new Gladiator(currentPlayer.baseHp, currentPlayer.baseAtk, currentPlayer.baseDef, currentPlayer.baseSpd, "MC", equipped)
      "Unequipped " + itemName
    }
    else "Failed to unequip " + itemName
  }

  /** Causes the player to list what they are carrying.
    * Returns a listing of the player's possessions or a statement indicating that the player is carrying nothing.
    * The return value has the form "You are carrying:\nITEMS ON SEPARATE LINES" or "You are empty-handed."
    * The items are listed in an arbitrary order. */
  def inventory: String = {
    if (inventoryList.isEmpty) "You are empty-handed."
    else {
      var returnString = "You are carrying:"
      for (item <- inventoryList) (returnString += s"\n${item.name}")
      returnString
    }
  }

  /** Tries to drop an item of the given name. This is successful if such an item is currently in the player's possession.
    * If so, the item is removed from the player's inventory and placed in the area.
    * Returns a description of the result of the attempt: "You drop the ITEM." or "You don't have that!". */
  def drop(itemName: String): String = {
    inventoryList.find(_.name == itemName) match {
      case Some(item) => {
        inventoryList -= item
        this.location.addItem(item)
        s"You drop the ${item.name}"
      }
      case None => "You don't have that!"
    }
  }



  /** Responsible for buying an item */
  def buy(itemName: String): String = {
    if (!this.location.items.exists(_.name == itemName)) s"Shopkeeper doesn't have $itemName"
    else if (this.money >= this.location.items.find(_.name == itemName).get.price) {
      val item = this.location.items.find(_.name == itemName).get
      inventoryList += item
      this.money -= item.price
      this.location.items -= item
      s"You buy the ${itemName}."
    }
    else s"Purchase failed due to lack of funds."
  }


  /** Determines whether the player is carrying an item of the given name. */
  def has(itemName: String): Boolean = inventoryList.map(_.name).toVector.contains(itemName)

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven


  /** Returns the current location of the player. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + "."
  }

  /** Responsible for initiating a battle */
   def challenge = {
     if (location.hasFighter){
      val enemy = location.fighters.head
      this.currentBattle = Some(new Battle(this, enemy))
      "You challenged " + enemy.name + "\n" + currentBattle.get.status
     }
     else "There is no fighter to challenge here."
  }


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() = {
    this.currentPlayer.heal()
    "You rest for a while. You feel rejuvenated."
  }


  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  /** Methods for Battle.scala */
  def attack(target: Gladiator) = currentPlayer.attack(target)
  def dodge(target: Gladiator)  = currentPlayer.dodge(target)
  def taunt(target: Gladiator)  = currentPlayer.taunt(target)
  def focus                     = currentPlayer.focus
  def heal(amount: Int)         = currentPlayer.heal(amount)

  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

  /** Makes the player character unstoppable */
  def cheat = {
    currentPlayer = new Gladiator(1000000, 100000, 10000, 100000, "cheater", Buffer[Item]())
    "cheater mode activated"
  }

}

