package starvationevasion.greninja.model;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a generic player (human player and AI player).
 */
public class HumanPlayer implements PlayerInterface
{
  EnumRegion region;
  List<EnumPolicy> cards, discardPile, voteRequiredPolicies;
  int votingPolicyCount = 0;

  public HumanPlayer() {}

  public HumanPlayer(EnumRegion region, List<EnumPolicy> cards)
  {
    discardPile = new ArrayList<EnumPolicy>();
    voteRequiredPolicies = new ArrayList<EnumPolicy>();

    this.region = region;
    this.cards = cards;
  }

  @Override
  public EnumRegion getPlayerRegion()
  {
    return this.region;
  }

  @Override
  public void setPlayerRegion(EnumRegion region)
  {
    this.region = region;
  }

  @Override
  public List<EnumPolicy> getPlayerHand()
  {
    return this.cards;
  }

  @Override
  public void setPlayerHand(List<EnumPolicy> hand)
  {
     this.cards = hand;
  }

  @Override
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

  @Override
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

  @Override
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

  @Override
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

  @Override
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


  @Override
  public EnumPolicy vote(int index)
  {
    return voteRequiredPolicies.get(index);
  }


  @Override
  public void draft(int index)
  {
    EnumPolicy selectedCard = getCard(index);
    // TODO: add this to a policy pile?
  }

  // TODO: communication with other players
}
