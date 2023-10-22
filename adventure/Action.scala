package o1.adventure


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as "go east" or "rest" */
class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim


  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as "You go west."). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player) = this.verb match {
    case "go"         => Some(actor.go(this.modifiers))
    case "quit"       => Some(actor.quit())
    case "inventory"  => Some(actor.inventory)
    case "rest"       => Some(actor.rest())
    case "drop"       => Some(actor.drop(this.modifiers))
    case "challenge"  => Some(actor.challenge)
    case "cheat"      => Some(actor.cheat)
    case "buy"        => Some(actor.buy(this.modifiers))
    case "help"       => Some(actor.help)
    case "equip"      => Some(actor.equip(this.modifiers))
    case "unequip"    => Some(actor.unequip(this.modifiers))
    case "equipped"   => Some(actor.equippedItems)
    case "money"      => Some(actor.money.toString)
    case "description"=> Some(actor.description(this.modifiers))
    case other        => None
  }

  /** Responsible for battle commands */
  def battle(player: Player) = {
    val fight = player.currentBattle.get
    this.verb match {
      case "attack" => Some(fight.attack)
      case "dodge"  => Some(fight.dodge)
      case "taunt"  => Some(fight.taunt)
      case "heal"   => Some(fight.heal)
      case "focus"  => Some(fight.focus)
      case "help"   => Some(fight.help)
      case other    => None
    }
  }


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"


}

