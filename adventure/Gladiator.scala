package o1.adventure

import scala.collection.mutable
import scala.util.Random

class Gladiator(val baseHp: Int, val baseAtk: Int, val baseDef: Int, val baseSpd: Int, val name: String, val items: mutable.Buffer[Item]) {



  /** Initialize stats */
  private var hp                = baseHp
  private var atk               = baseAtk
  private var defence           = baseDef
  private var speed             = baseSpd
  /** taunted keeps trrack of which options a gladiator can choose from */
  private var taunted: Boolean  = false
  /** evades checks whether the gladiator will dodge the next attack/action */
  private var evades: Boolean   = false


  /** Apply item modifiers to stats
    * only used by the player character */
  for (item <- items) atk     = (atk      * item.atkModifier).toInt
  for (item <- items) defence = (defence  * item.defModifier).toInt
  for (item <- items) speed   = (speed    * item.spdModifier).toInt

  /** Define functions getter functions for other classes */
  def isDead = hp <= 0
  def getHp = hp
  def getSpd = speed
  def bounty = (baseHp + baseAtk + baseDef + baseSpd) * 2


  /** Heals the gladiator */
  def heal(heal: Int) = {
    if(!this.isDead && !taunted){
      val added = math.min(heal, baseHp - hp)
      hp += added
      s"$name healed for $added. Now has $hp left."
    }
    else name + " is dead."
  }

  /** Heals to full */
  def heal(): String = {
    heal(math.max(0, baseHp - hp))
  }

  /** Attacks the target */
  def attack(target: Gladiator) = {
    if(!this.isDead && !target.isDead) {
      if (target.evades) {
        target.evades = false
        s"${this.name}'s attack missed."
      }
      else {
        target.hp = math.max(target.hp - (10 * this.atk / target.defence), 0)
        this.taunted = false
        s"${target.name} was attacked by ${this.name}."
      }
    }
    else {
      this.taunted = false
      ""
    }
  }

  /** Attempts to dodge next attack */
  def dodge(target: Gladiator) = {
    if (Random.nextInt(this.speed) > target.speed / 2) {
      this.evades = true
      this.name + " will dodge next attack."
    }
    else this.name + "'s dodging failed."
  }

  /** Attempts to raise atk stat */
  def focus = {
    if(Random.nextInt(hp) >= baseHp / 3 && !taunted) {
      atk = atk * 1.4 .toInt
      this.name + "'s focus succeeded."
    }
    else this.name + "'s focus failed"
  }

  /** Sets a flag that prevents the target from doing anything else other than attacking */
  def taunt(target: Gladiator) = {
    if(target.evades) {
      target.evades = false
      target.name + "evaded the taunt"
    }
    else if(this.taunted) this.name + " is taunted."
    else {
      target.taunted = true
      target.name + " was taunted."
    }
  }

  override def toString = s"$name: hp $hp atk $atk def $defence spd $speed"
}
