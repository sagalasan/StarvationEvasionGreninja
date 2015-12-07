package starvationevasion.greninja.model;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.sim.CardDeck;

import java.util.List;

/**
 * Class that represents an AI player.
 * @author Erin Sosebee
 */
public class AIPlayer implements PlayerInterface
{
  private String playerName;
  EnumRegion region;
  CardDeck playerDeck;
  List<PolicyCard> cards, discardPile, voteRequiredPolicies;

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
  public void setPlayerRegion(EnumRegion region)
  {
    this.region = region;
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
  public void setPlayerHand(List<PolicyCard> cards)
  {
    this.cards = cards;
  }

  @Override
  public List<PolicyCard> getPlayerHand()
  {
    return cards;
  }

  @Override
  public boolean addCard(PolicyCard card)
  {
    if (cards.size() < 7)
    {
      cards.add(card);
      return true;
    }
    else return false;
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
    if (index < cards.size())
    {
      PolicyCard discarded = cards.remove(index);
      discardPile.add(discarded);
      return true;
    }
    else return false;
  }

  @Override
  public boolean discardThree(int[] indices)
  {
    if (cards.size() >= 3)
    {
      PolicyCard[] cardsToDiscard = new PolicyCard[3];
      for (int i = 0; i < 3; ++i)
      {
        cardsToDiscard[i] = getCard(indices[i]);
      }
      for (int i = 0; i < 3; ++i)
      {
        discardPile.add(cardsToDiscard[i]);
        cards.remove(cardsToDiscard[i]);
      }
      return true;
    }
    else return false;
  }

  @Override
  public PolicyCard getCard(int index)
  {
    if (index < cards.size())
    {
      return cards.get(index);
    }
    else return null;
  }

  @Override
  public PolicyCard firstCard(List<PolicyCard> cardPile)
  {
    return cardPile.get(0);
  }

  @Override
  public PolicyCard vote(int index)
  {
    return voteRequiredPolicies.get(index);
  }

  @Override
  public PolicyCard draft(int index)
  {
    PolicyCard selectedCard = cards.remove(index);
    return selectedCard;
  }
}
