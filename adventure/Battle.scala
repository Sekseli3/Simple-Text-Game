package o1.adventure

import scala.util.Random

class Battle(player: Player, opponent: Gladiator) {


  player.currentBattle = Some(this)

  /** Returns the status of the battle. */
  def status = s"You have ${player.currentPlayer.getHp} hp, ${opponent.name} has ${opponent.getHp} hp"

  /** Checks whether the battle is over. Stops the battle if necessary. */
  def checkWin = {
    if (player.currentPlayer.isDead){
      player.hasLost = true
      player.currentBattle = None
      "You died"
    }
    else if (opponent.isDead) {
      player.currentBattle = None
      this.player.location.removeFighter(opponent)
      if (player.location.fighters.isEmpty) player.isChampion = true
      else{
        player.money += opponent.bounty
        player.strengthenFighter
        "You won!"
      }
    }
    else ""
  }

  /** Responsible for opponent's AI
    * chooses randomly between all choices */
  def opponentTurn = {
    if (!this.opponent.isDead) {
      val choice = Random.nextInt(5)
      if      (choice == 0) opponent.attack(player.currentPlayer)
      else if (choice == 1) opponent.heal(opponent.baseHp / 3)
      else if (choice == 2) opponent.dodge(player.currentPlayer)
      else if (choice == 3) opponent.taunt(player.currentPlayer)
      else                  opponent.focus
    }
    else s"${opponent.name} lost."
  }

  /** Turn methods */

  /** Executes turn with a player attack */
  def attack = {
    if(player.currentPlayer.getSpd >= opponent.getSpd) player.attack(opponent) + "\n" + checkWin + "\n" + opponentTurn + "\n" + status
    else                                               opponentTurn + "\n" + player.attack(opponent) + "\n" + checkWin + "\n" + status
  }

  /** Executes turn with a player heal */
  def heal = {
    if(player.currentPlayer.getSpd >= opponent.getSpd) player.heal(player.currentPlayer.baseHp / 3) + "\n" + checkWin + "\n" + opponentTurn + "\n" + status
    else                                               opponentTurn + "\n" + player.heal(player.currentPlayer.baseHp / 3) + "\n" + checkWin + "\n" + status
  }

  /** Executes turn with a player dodge */
  def dodge = {
    if(player.currentPlayer.getSpd >= opponent.getSpd) player.dodge(opponent) + "\n" + checkWin + "\n" + opponentTurn + "\n" + status
    else                                               opponentTurn + "\n" + player.dodge(opponent) + "\n" + checkWin + "\n" + status
  }

  /** Executes turn with a player focus */
  def focus = {
    if(player.currentPlayer.getSpd >= opponent.getSpd) player.focus + "\n" + checkWin + "\n" + opponentTurn + "\n" + status
    else                                               opponentTurn + "\n" + player.focus + "\n" + checkWin + "\n" + status
  }

  /** Executes turn with a player taunt */
  def taunt = {
    if(player.currentPlayer.getSpd >= opponent.getSpd) player.taunt(opponent) + "\n" + checkWin + "\n" + opponentTurn + "\n" + status
    else                                               opponentTurn + "\n" + player.taunt(opponent) + "\n" + checkWin + "\n" + status
  }


  def help = {
    "attack: deals damage to opponent \nheal: heals player by 1/3 of max hp \ndodge: attempts to dodge next attack based on a speed check " +
    "\nfocus: attempts to increase attack based on a hp check \ntaunt: attempts to taunt the opponent to force them to only attack"
  }
}
