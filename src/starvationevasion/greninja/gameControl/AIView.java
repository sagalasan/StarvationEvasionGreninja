package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.common.messages.ClientChatMessage;
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
   *
   * @param control
   * @param player
   */
  public AIView(GameController control, PlayerInterface player)
  {
    this.control = control;
    this.player = player;
//    this.playerDeck = player.getPlayerDeck();
    hasDrafted = false;
  }

  /**
   * set the decision making object.
   * @param decisions
   */
  public void setDecisionObject(AIDecisions decisions)
  {
    this.decisions = decisions;
  }
  /**
   * Get timerpane for ai returns null as of now.  Will be cleaned out.
   * @param phase
   * @return
   */
  @Override
  public TimerPane getTimerPane(EnumPhase phase)
  {
    return null;
  }

  /**
   * Inform view of player region, not entirely necessary.
   * @param state
   * @param pRegion
   */
  @Override
  public void initPlayerRegionInfo(State state, EnumRegion pRegion)
  {
    this.currentState = state;
     this.pRegion = pRegion;
  }

  /**
   * Inform ai that staging phase has started.  Does not need to do anything,
   * server will inform ai of region assignment.
   */
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

  /**
   * Inform AI that it is policy drafting phase.  When control calls the AI's
   * "GUI" it will prompt AIDecisions to do stuff.
   */
  @Override
  public void swapToPolicyPane()
  {
    if (DEBUG) System.out.println("I'm a robot and I'm drafting policies!");
    if(!hasDrafted)
    {
      if (player.getPlayerHand() != null)
      {
        int draftCardIndex = decisions.analyzeCards(player.getPlayerHand(), "mandatory");
        player.draft(draftCardIndex);
        int draftVoteCardIndex = decisions.analyzeCards(player.getPlayerHand(), "votes");
        player.draft(draftVoteCardIndex);
        hasDrafted = true;
      }
    }
    hasDrafted = false;
  }

  /**
   * Inform view that there has been a game state update.  If it is drafting phase
   * and cards have not been drafted, do the drafting.
   */
  @Override
  public void gameStateUpdate()
  {
    /*
    if(!hasDrafted)
    {
      if (player.getPlayerHand() != null)
      {
        int draftCardIndex = decisions.analyzeCards(player.getPlayerHand(), "mandatory");
        player.draft(draftCardIndex);
        int draftVoteCardIndex = decisions.analyzeCards(player.getPlayerHand(), "votes");
        player.draft(draftVoteCardIndex);
        hasDrafted = true;
      }
    }*/
  }

  /**
   * Inform AI that it is voting phase.  When control calls the AI's "GUI" it
   * will prompt AIDecisions to do stuff.
   */
  @Override
  public void swapToVotingPane()
  {
    if (DEBUG) System.out.println("I'm a robot and I'm voting on policies!");

    List<PolicyCard> voteCards = new ArrayList<PolicyCard>();
    // TODO: Get cards from voting pane
    int voteCardIndices[] = decisions.voteCard(voteCards);
    for (int i = 0; i < voteCardIndices.length; i++)
    {
      player.vote(voteCardIndices[i]);
    }
  }

  /**
   * Update availableRegions message.
   * @param availableRegions        AvailableRegions method.
   */
  @Override
  public void updateAvailableRegions(AvailableRegions availableRegions, PlayerInterface playerInterface)
  {
    this.availableRegions = availableRegions;
  }


  /**
   * Send string chat message to control to pass on to server coms.
   * @param message       String message.
   * @param destination   destination regions
   */
  public void sendChatMessage(String message, EnumRegion[] destination)
  {
    ClientChatMessage messageOut = new ClientChatMessage(message, destination);
    control.sendMessageOut(messageOut);
  }

  /**
   * Send card info chat message to control to pass on to server coms.
   * @param card       card message.
   * @param destination   destination regions
   */
  public void sendChatMessage(EnumPolicy card, EnumRegion[] destination)
  {
    ClientChatMessage messageOut = new ClientChatMessage(card, destination);
    control.sendMessageOut(messageOut);
  }
}
