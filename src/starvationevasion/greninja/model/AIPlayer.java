package starvationevasion.greninja.model;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;

import java.util.List;

/**
 *
 */
public class AIPlayer implements PlayerInterface
{
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
  public void setPlayerHand(List<EnumPolicy> hand)
  {

  }

  @Override
  public List<EnumPolicy> getPlayerHand()
  {
    return null;
  }

  @Override
  public boolean addCard(EnumPolicy card)
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
  public EnumPolicy getCard(int index)
  {
    return null;
  }

  @Override
  public EnumPolicy firstCard(List<EnumPolicy> cardPile)
  {
    return null;
  }

  @Override
  public EnumPolicy vote(int index)
  {
    return null;
  }

  @Override
  public void draft(int index)
  {

  }
}
