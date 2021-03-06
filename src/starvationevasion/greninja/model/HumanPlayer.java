package starvationevasion.greninja.model;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.sim.CardDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a human player.
 * @author Erin Sosebee
 */
public class HumanPlayer implements PlayerInterface
{
  EnumRegion region;
  List<PolicyCard> cards , discardPile, voteRequiredPolicies;
  CardDeck playerDeck;
  private String playerName;

  int votingPolicyCount = 0;

  public HumanPlayer()
  {
    cards = new ArrayList<PolicyCard>();
    discardPile = new ArrayList<PolicyCard>();
    voteRequiredPolicies = new ArrayList<PolicyCard>();

  }

  public HumanPlayer(EnumRegion region, List<PolicyCard> cards)
  {
    cards = new ArrayList<PolicyCard>();
    discardPile = new ArrayList<PolicyCard>();
    voteRequiredPolicies = new ArrayList<PolicyCard>();

    this.region = region;
    this.cards = cards;
  }


  /**
   * Set the string name of the player.
   * @param name        String name of player.
   */
  @Override
  public void setPlayerName(String name)
  {
    this.playerName = name;
  }

  @Override
  public String getPlayerName()
  {
    return playerName;
  }

  @Override
  public EnumRegion getPlayerRegion()
  {
    return region;
  }

  @Override
  public void setPlayerDeck(CardDeck deck)
  {
    this.playerDeck = deck;
  }

  @Override
  public CardDeck getPlayerDeck()
  {
    return playerDeck;
  }

  @Override
  public void setPlayerRegion(EnumRegion region)
  {
    this.region = region;
  }

  @Override
  public List<PolicyCard> getPlayerHand()
  {
    return cards;
  }

  @Override
  public void setPlayerHand(List<PolicyCard> cards)
  {
     this.cards = cards;
  }

  @Override
  public boolean addCard(PolicyCard card)
  {
    //System.out.println("adding " +card.getTitle());
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
  public boolean removeCard(int index)
  {
    if (cards.size() > 0)
    {
      cards.remove(index);
      return true;
    }
    else return false;
  }

  @Override
  public boolean discardCard(int index)
  {
    if(index < cards.size())
    {
      PolicyCard discarded = cards.remove(index);
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
      PolicyCard[] cardsToDiscard = new PolicyCard[3];
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
  public PolicyCard getCard(int index)
  {
    //at most there can only be 7 cards
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
  public PolicyCard firstCard(List<PolicyCard> cardPile)
  {
    return cardPile.get(0);
  }

  // TODO: I'm not entirely sure if we want to/how we will handle the voting and drafting in this class
  /**
   * Gets all the policies drafted by other players that need a vote.
   * @return
   */
  public List<PolicyCard> getDraftedPolicies(List<PolicyCard> draftedPolicies)
  {
    voteRequiredPolicies = draftedPolicies;
    return voteRequiredPolicies;
  }


  @Override
  public PolicyCard vote(int index)
  {
    return voteRequiredPolicies.get(index);
  }

  @Override
  public PolicyCard oppose(int index)
  {
    return voteRequiredPolicies.get(index);
  }

  @Override
  public PolicyCard draft(int index)
  {
    // TODO: add card to a policy pile?
    PolicyCard selectedCard = cards.remove(index);
    return selectedCard;
  }

  // TODO: communication with other players
}
