package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.common.messages.ClientChatMessage;
import starvationevasion.common.messages.DraftCard;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.model.AIPlayer;
import starvationevasion.greninja.model.PlayerInterface;
import starvationevasion.greninja.model.State;
import starvationevasion.sim.CardDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * The AIView class handles AI communications with the game controller. This is parallel to GuiBase, and it is a
 * "GUI" for the robot AI players.
 * @author Justin Thomas
 * @author Erin Sosebee
 */
public class AIView implements ControlListener
{
  private GameController control;
  private AvailableRegions availableRegions;
  private AIDecisions decisions;
  private State currentState;

  private PlayerInterface player;
  EnumRegion pRegion;
  private CardDeck playerDeck;
  private boolean hasDrafted;

  private boolean DEBUG = false;

  /**
   * Instantiates an AIView for the player.
   * @param control a reference to the GameController
   * @param player  the AI player to see the AIView.
   */
  public AIView(GameController control, PlayerInterface player)
  {
    this.control = control;
    this.player = player;
//    this.playerDeck = player.getPlayerDeck();
    hasDrafted = false;
  }

  /**
   * Set the decision-making object.
   * @param decisions the AIDecisions object to be referenced.
   */
  public void setDecisionObject(AIDecisions decisions)
  {
    this.decisions = decisions;
  }

  @Override
  public TimerPane getTimerPane(EnumPhase phase)
  {
    return null;
  }

  @Override
  public void initPlayerRegionInfo(State state, EnumRegion pRegion)
  {
    this.currentState = state;
     this.pRegion = pRegion;
  }

  @Override
  public void swapToStagingPane()
  {
    if(availableRegions != null)
    {
      if(availableRegions.availableRegions != null)
      {
        decisions.selectRegion(availableRegions.availableRegions);
      }
    }
  }

  @Override
  public void swapToPolicyPane()
  {
    if (DEBUG) System.out.println("I'm a robot and I'm drafting policies!");
    hasDrafted = false;
  }

  @Override
  public void gameStateUpdate()
  {
    if(!hasDrafted)
    {
      if (player.getPlayerHand() != null)
      {
        int draftCardIndex = decisions.analyzeCards(player.getPlayerHand(), "mandatory");
        //player.draft(draftCardIndex);
        control.draftPolicy(player.draft(draftCardIndex));
        int draftVoteCardIndex = decisions.analyzeCards(player.getPlayerHand(), "votes");
        control.draftPolicy(player.draft(draftVoteCardIndex));
        //control.sendMessageOut(new DraftCard(player.draft(draftVoteCardIndex)));
        hasDrafted = true;
      }
    }
  }

  @Override
  public void swapToVotingPane()
  {
    if (DEBUG) System.out.println("I'm a robot and I'm voting on policies!");

    List<PolicyCard> voteCards = new ArrayList<PolicyCard>();
    // TODO: Get cards from voting pane

    // Cards to vote for
    int voteCardIndices[] = decisions.voteCard(voteCards);
    for (int i = 0; i < voteCardIndices.length; i++)
    {
      player.vote(voteCardIndices[i]);
    }

    // Cards to oppose
    for (int i = 0; i < voteCardIndices.length; i++)
    {
      if (i != voteCardIndices[i])
      {
        player.oppose(i);
      }
    }
  }

  @Override
  public void updateAvailableRegions(AvailableRegions availableRegions, PlayerInterface playerInterface)
  {
    this.availableRegions = availableRegions;
  }

  @Override
  public void sendChatMessage(String message, EnumRegion[] destination)
  {
    ClientChatMessage messageOut = new ClientChatMessage(message, destination);
    control.sendMessageOut(messageOut);
  }

  @Override
  public void sendChatMessage(EnumPolicy card, EnumRegion[] destination)
  {
    ClientChatMessage messageOut = new ClientChatMessage(card, destination);
    control.sendMessageOut(messageOut);
  }
}
