package starvationevasion.greninja.model;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a generic player (human player and AI player).
 */
public class Player implements PlayerInterface
{
  EnumRegion region;
  List<EnumPolicy> cards, discardPile, voteRequiredPolicies;
  int votingPolicyCount = 0;

  public Player() {}

  public Player(EnumRegion region, List<EnumPolicy> cards)
  {
    discardPile = new ArrayList<EnumPolicy>();
    voteRequiredPolicies = new ArrayList<EnumPolicy>();

    this.region = region;
    this.cards = cards;
  }


  public EnumRegion getPlayerRegion()
  {
    return this.region;
  }


  public void setPlayerRegion(EnumRegion region)
  {
    this.region = region;
  }


  public List<EnumPolicy> getPlayerHand()
  {
    return this.cards;
  }


  public void setPlayerHand(List<EnumPolicy> hand)
  {
     this.cards = hand;
  }


  public boolean addCard(EnumPolicy card)
  {
    if (cards.size() < 7)
    {
      cards.add(card);
      return true;
    }
    else
    {
      return false;
    }
  }


  public boolean discardCard(int index)
  {
    if(index < cards.size())
    {
      EnumPolicy discarded = cards.remove(index);
      discardPile.add(discarded);
      return true;
    }
    else
    {
      return false;
    }
  }


  public boolean discardThree(int[] indices)
  {
    if(cards.size() >= 3)
    {
      EnumPolicy[] cardsToDiscard = new EnumPolicy[3];
      for (int i = 0; i < 3; ++i)
      {
        cardsToDiscard[i] = getCard(indices[i]);
      }
      for(int i = 0; i < 3; ++i)
      {
        discardPile.add(cardsToDiscard[i]);
        cards.remove(cardsToDiscard[i]);
      }
      return true;
    }
    else
    {
      return false;
    }
  }


  public EnumPolicy getCard(int index)
  {
    if(index < cards.size())
    {
      return cards.get(index);
    }
    else
    {
      return null;
    }
  }


  public EnumPolicy firstCard(List<EnumPolicy> cardPile)
  {
    return cardPile.get(0);
  }

  // TODO: I'm not entirely sure if we want to/how we will handle the voting and drafting in this class
  /**
   * Gets all the policies drafted by other players that need a vote.
   * @return
   */
  public List<EnumPolicy> getDraftedPolicies(List<EnumPolicy> draftedPolicies)
  {
    voteRequiredPolicies = draftedPolicies;
    return voteRequiredPolicies;
  }


  public EnumPolicy vote(int index)
  {
    return voteRequiredPolicies.get(index);
  }


  public void draft(int index)
  {
    EnumPolicy selectedCard = getCard(index);
    // TODO: add this to a policy pile?
  }

  // TODO: communication with other players
}
