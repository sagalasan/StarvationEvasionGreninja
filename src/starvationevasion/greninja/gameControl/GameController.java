package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumFood;
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.greninja.model.HumanPlayer;
import starvationevasion.greninja.model.State;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Main communication hub of client application.  This will be the go-between
 * class for the server message handlers, world state model, game state classes
 * and gui.
 */
public class GameController
{
  private GuiBase gui;
  private EnumRegion playerRegion;
  private State playerRegionInfo;
  private DraftingPhase draftingPhase;
  private VotingPhase votingPhase;
  private HumanPlayer humanPlayer;
  private ArrayList<PolicyCard> cardsForVote;
  //WorldModel
  //GameStateTracker

  /**
   * Constructor for ai game.
   * TODO wait to see what server does for ais
   */
  public GameController()
  {
    //start AI game
  }

  /**
   * Starter for human player game.
   * @param gui       gui to use.
   */
  public GameController(GuiBase gui)
  {
    this.gui = gui;
    cardsForVote = new ArrayList<>();
  }

  /**
   * Messages from server come in to this method.  Identifies message and sends
   * to the appropriate data structure or takes appropriate action
   * @param message       Serializable object.
   */
  public void handleMessageIn(Serializable message)
  {
    //identify message type.
    //send to appropriate location.
  }

  /**
   * Send a message to the message queue
   * @param message       Serializable object.
   */
  public void sendMessageOut(Serializable message)
  {
    //send message to message queue;
  }

  /*
  ============================Startup===========================================
  */

  /**
   * Begin single player game.
   * Instantiate local server.
   */
  public void startSinglePlayerGame()
  {
    System.out.println("Start single player game.");
    //for remaining slots, start AiGame (on new thread?).
    gui.swapToStagingPane();
  }

  /**
   * Start game for an ai player.
   */
  public void startAiGame()
  {
    //Start a game for ai player.
  }

  /**
   * Start multiplayer game and connect to server.
   */
  public void startMultiPlayerGame(String serverName)
  {
    //validate and attempt to connect to server.
    //if invalid go back.
    System.out.println("Start multiplayer game.");
    System.out.println("Prompt user for Server Name");
    System.out.println("Try To Connect");
    gui.swapToStagingPane();
  }

  /**
   * Perform region select actions.
   * @param region        region player chose.
   */
  public void regionSelected(EnumRegion region)
  {
    playerRegion = region;
    System.out.println("Inform server of choice.");
    System.out.println("Wait for other players.");
    System.out.println("Start Policy Phase.");
    beginGame();
  }

  /**
   * Once initial phases are over, setup rest of game and start policy drafting
   * phase.
   */
  private void beginGame()
  {
    //create initial hand
    ArrayList<EnumPolicy> initialHand = new ArrayList<>();
    //instantiate player
    humanPlayer = new HumanPlayer(playerRegion, initialHand);
    playerRegionInfo = new State(playerRegion);
    gui.initPlayerRegionInfo(playerRegionInfo, playerRegion);
    fillHand();
    //start policy drafting phase.
    startPolicyDraftingPhase();
  }
 /*
  ============================end startup=======================================
  ******************************************************************************
  ===========================GENERAL PHASE METHODS==============================
  */

  /**
   * When gui visualization pane is clicked, ask the visualization for the
   * detailed vis.
   * @return        nothing yet
   */
  public void getVisualization()
  {
    //return visualization to gui
  }

  /**
   * When region is clicked on the interactive map, eturn region details for
   * clicked region.
   * @param regionClicked       enum of region clicked.
   * @return                    nothing yet.
   */
  public void getRegionDetails(EnumRegion regionClicked)
  {
    //return region details
  }

  /**
   * When chat message is sent from player, inform server that message was sent.
   * @param messageSent
   */
  public void sendChatMessage(String messageSent)
  {
    //send message to server.
  }

  /**
   * When message is received from server, send to gui
   * @param messageReceived
   */
  public void receiveChatMessage(String messageReceived)
  {
    //send message to gui.
  }

  /**
   * Send commodity info to gui.
   * @param commodity
   */
  public void getCommodityInfo(EnumFood commodity)
  {
    //send commodity info to gui.
  }

  /**
   * Get card info for display.  Called when player clicks a card.
   * @param index       index of card clicked.
   * @return            EnumPolicy for corresponding card.
   */
  public EnumPolicy getCardInfo(int index)
  {
    return humanPlayer.getCard(index);
  }


  /*
  *===================DECK MANAGEMENT==========================================
  */
  /**
   * Deals cards to player hand from deck up to the max hand size.  Initially
   * called with the hand itself.  Should be called with player.getHand() during
   * the game.
   */
  public void fillHand()
  {
    while(humanPlayer.addCard(drawCard()))
    {
      System.out.println("Added card");
    }
  }

  /**
   * Draw a card from deck and add to hand.
   * TODO fix when we've figured out how deck will work.
   * @return        Card drawn.
   */
  public EnumPolicy drawCard()
  {
    return EnumPolicy.International_Food_Releif_Program;
  }
  //=================end deck management========================================

  /*
  =========end general phase methods============================================
  ******************************************************************************
  ============================DRAFTING PHASE====================================
  */

  /**
   * Instantiate new policyDrafting phase.  Inform gui that it's policy drafting
   * time.
   */
  public void startPolicyDraftingPhase()
  {
    //get HDI's, Populations, Money from server.
    gui.swapToPolicyPane();
    draftingPhase = new DraftingPhase(this, gui.getTimerPane(EnumPhase.DRAFTING),
        humanPlayer);
  }

  /**
   * Performs end drafting phase actions, and swaps to policy voting phase.
   */
  public void endPolicyDraftingPhase()
  {
    //start voting phase.
    startPolicyVotingPhase();
    draftingPhase = null;
  }

  /**
   * Draft a policy.  Called from gui.  Drafting phase attempts to get card
   * and this method adds it to the appropriate policy pending list.
   * @param cardIndex       index of card to draft.
   */
  public void draftPolicy(int cardIndex)
  {
    EnumPolicy cardDrafted = draftingPhase.draftPolicy(cardIndex);
    if(cardDrafted != null)
    {
      //if card needs voting goes into cardsForVote, else goes to cardsToPlay
      if(cardDrafted.votesRequired() == 0)
      {
        //inform server that card was played;
      }
      else
      {
        //inform server that card was played;
        cardsForVote.add(new PolicyCard(playerRegion, cardDrafted));
      }
    }
    else
    {
      //inform gui that drafting action failed.
      System.out.println("Policy Draft Failed.");
    }
  }

  /**
   * When player discards a card on the gui, call this.
   * @param cardIndex           index of card player selected.
   */
  public void discardOne(int cardIndex)
  {
    if(draftingPhase.discardOne(cardIndex))
    {
      //inform server of discard
      //send success message to gui.
      System.out.println("Discard Success.");
    }
    else
    {
      //send failure message to gui.
      System.out.println("Discard failed.");
    }
  }

  /**
   * When gui's discard3 action is selected prompt user to select 3 cards.
   */
  public void discardThree()
  {
    //check if 3 cards in hand.
    //prompt user to select 3 cards to discard
    int[] cardsSelected = new int[3];
    //gui.selectThree(cardsSelected)

    if(draftingPhase.discardThree(cardsSelected))
    {
      //inform server of discards
      //send success message to gui
      System.out.println("Discard Success.");
    }
    else
    {
      //send failure message to gui
      System.out.println("Discard failed.");
    }
  }

  /*
  ==================drafting phase end==========================================
  ******************************************************************************
  ==============================VOTING PHASE====================================
  */

  /**
   * Start new voting phase.
   */
  public void startPolicyVotingPhase()
  {
    gui.swapToVotingPane();
    getCardsForVote();
    votingPhase = new VotingPhase(this, gui.getTimerPane(EnumPhase.VOTING));
    //do stuff
  }

  /**
   * Cast a vote.  Called from the gui, informs server of card and vote cast
   * for it.
   * @param vote        true if "yes" vote, false if "no" vote.
   */
  public void voteCast(boolean vote)
  {
    if(vote)
    {
      //inform server that vote yes.
    }
    else
    {
      //inform server of no vote.
    }
  }


  /**
   * end voting phase.
   */
  public void endPolicyVotingPhase()
  {
    fillHand();
    startPolicyDraftingPhase();
    votingPhase = null;
  }

  private void getCardsForVote()
  {
    //get the cards up for vote from the server.
    //for each card for vote
      //add to cardsForVote
    //inform gui of cards by region.
  }


  /*
  ===========voting phase end===================================================
   */
}
