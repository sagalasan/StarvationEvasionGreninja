package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumFood;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.common.messages.*;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.greninja.model.AIPlayer;
import starvationevasion.greninja.model.HumanPlayer;
import starvationevasion.greninja.model.PlayerInterface;
import starvationevasion.greninja.model.State;
import starvationevasion.greninja.serverCom.ServerConnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Main communication hub of client application.  This will be the go-between
 * class for the server message handlers, world state model, game state classes
 * and gui.
 */
public class GameController
{
  private ControlListener view;
  private EnumRegion playerRegion;
  private State playerRegionInfo;
  private DraftingPhase draftingPhase;
  private VotingPhase votingPhase;
  private PlayerInterface player;
  private ArrayList<PolicyCard> cardsForVote;
  private ServerConnection serverLine;
  //WorldModel
  //GameStateTracker

  /**
   * Constructor for ai game.
   * TODO wait to see what server does for ais
   */
  public GameController()
  {
    //start AI game
    view = new AIView(this);
    serverLine = new ServerConnection(this);
    player = new AIPlayer();
    player.setPlayerName("AI");
  }

  /**
   * Starter for human player game.
   * @param view       gui to use.
   */
  public GameController(GuiBase view)
  {
    this.view = view;
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
    if(message instanceof AvailableRegions)
    {
      view.updateAvailableRegions((AvailableRegions) message, player);
    }
    else if(message instanceof BeginGame)
    {
      BeginGame msg = (BeginGame) message;
      beginGame(msg);
    }
    else if(message instanceof LoginResponse)
    {
      handleLoginResponse((LoginResponse)message);
    }
  }

  /**
   * Send a message to the message queue
   * @param message       Serializable object.
   */
  public void sendMessageOut(Serializable message)
  {
    //send message to message queue;
  }

  /**
   * If message is LoginResponse perform appropriate action
   * @param response        LoginResponse from server
   */
  private void handleLoginResponse(LoginResponse response)
  {
    LoginResponse.ResponseType type = response.responseType;
    switch(type)
    {
      case ACCESS_DENIED:
        //bad password.  Inform user, do again.
        break;
      case ASSIGNED_REGION:
        EnumRegion region = response.assignedRegion;
        playerRegion = region;
        player.setPlayerRegion(region);
        //assign this region.
        //skip staging pane?
        break;
      case REJOIN:
        //??
        break;
      case CHOOSE_REGION:
        //go to staging like normal.
        view.swapToStagingPane();
        break;
      default:
        break;
    }

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
    serverLine = new ServerConnection(this);
    //serverLine.startConnection("localhost");
    player = new HumanPlayer();
    player.setPlayerName("Player");
    //login
    //TODO server will swap to staging pane.
    view.swapToStagingPane();
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
    player = new HumanPlayer();
    //validate and attempt to connect to server.
    //if invalid go back.
    System.out.println("Start multiplayer game.");
    serverLine = new ServerConnection(this);
    //serverLine.startConnection(serverName);
    //serverLine.sendMessage(name, password);
    System.out.println("Try To Connect");
    //if connection successful get login information. and send to server.
    //TODO server will swap to staging pane.
    view.swapToStagingPane();
  }

  /**
   * Perform region select actions.  Sets player region and informs server.
   * @param region        region player chose.
   */
  public void regionSelected(EnumRegion region)
  {
    playerRegion = region;
    player.setPlayerRegion(region);
    System.out.println("Inform server of choice.");
    System.out.println("Wait for other players.");
    System.out.println("Start Policy Phase.");
    //serverLine.sendMessage(new RegionChoice(region));
    //TODO will be called from server's begin game message.
    beginGame();
  }

  /**
   * Once initial phases are over, setup rest of game and start policy drafting
   * phase.
   */
  private void beginGame()
  {
    //create initial hand
    ArrayList<PolicyCard> initialHand = new ArrayList<>();
    //instantiate player
    playerRegionInfo = new State(playerRegion);
    view.initPlayerRegionInfo(playerRegionInfo, playerRegion);
    //fillHand();
    //start policy drafting phase.
    startPolicyDraftingPhase();
  }

  /**
   * Begin game.  Finalize player region with method from server and pass to
   * default begin game message.
   * @param msg
   */
  private void beginGame(BeginGame msg)
  {
    //confirm player region.
    for(EnumRegion region : msg.finalRegionChoices.keySet())
    {
      if(msg.finalRegionChoices.get(region).equals(player.getPlayerName()))
      {
        player.setPlayerRegion(region);
        playerRegion = region;
      }
    }
    beginGame();
  }

  /**
   * Send login info to the message queue in the form of a Login message.
   * @param name          name entered by user
   * @param password      password entered by user.
   */
  public void sendLoginInfo(String name, String password)
  {
    player.setPlayerName(name);
    sendMessageOut(new Login(name, "SaltySaltSalt?", password));
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
  public PolicyCard getCardInfo(int index)
  {
    return player.getCard(index);
  }

  /**
   * Get the player hand list.
   * @return        List of cards in player hand.
   */
  public List<PolicyCard> getPlayerHand()
  {
    return player.getPlayerHand();
  }

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
    view.swapToPolicyPane();
    draftingPhase = new DraftingPhase(this, view.getTimerPane(EnumPhase.DRAFTING),
        player);
  }

  /**
   * Performs end drafting phase actions, and swaps to policy voting phase.
   */
  public void endPolicyDraftingPhase()
  {
    //start voting phase.
    draftingPhase.stopTimer();
    startPolicyVotingPhase();
    //draftingPhase = null;
  }

  /**
   * Draft a policy.  Called from gui.  Drafting phase attempts to get card
   * and this method adds it to the appropriate policy pending list.
   * @param cardIndex       index of card to draft.
   */
  public void draftPolicy(int cardIndex)
  {
    PolicyCard cardDrafted = draftingPhase.draftPolicy(cardIndex);
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
        //cardsForVote.add(new PolicyCard(playerRegion, cardDrafted));
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
    view.swapToVotingPane();
    getCardsForVote();
    votingPhase = new VotingPhase(this, view.getTimerPane(EnumPhase.VOTING));
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
    //fillHand();
    votingPhase.stopTimer();
    startPolicyDraftingPhase();
    //votingPhase = null;
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
