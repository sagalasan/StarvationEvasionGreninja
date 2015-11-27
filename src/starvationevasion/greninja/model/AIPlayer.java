package starvationevasion.greninja.model;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;

import java.util.List;

/**
 *
 */
public class AIPlayer implements PlayerInterface
{
  private String playerName;

  /**
   * Set the string name of the player.
   * @param name        String name of player.
   */
  @Override
  public void setPlayerName(String name)
  {
    this.playerName = name;
  }

  /**
   * Get the string name of the player.
   * @return        String name of player.
   */
  @Override
  public String getPlayerName()
  {
    return playerName;
  }

  @Override
  public void setPlayerRegion(EnumRegion region)
  {

  }

  @Override
  public EnumRegion getPlayerRegion()
  {
    return null;
  }

  @Override
  public void setPlayerHand(List<PolicyCard> hand)
  {

  }

  @Override
  public List<PolicyCard> getPlayerHand()
  {
    return null;
  }

  @Override
  public boolean addCard(PolicyCard card)
  {
    return false;
  }

  @Override
  public boolean discardCard(int index)
  {
    return false;
  }

  @Override
  public boolean discardThree(int[] indices)
  {
    return false;
  }

  @Override
  public PolicyCard getCard(int index)
  {
    return null;
  }

  @Override
  public PolicyCard firstCard(List<PolicyCard> cardPile)
  {
    return null;
  }

  @Override
  public PolicyCard vote(int index)
  {
    return null;
  }

  @Override
  public void draft(int index)
  {

  }
}
