package starvationevasion.greninja.gameControl;

import javafx.application.Platform;
import starvationevasion.common.*;
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
  private GuiBase guiView;
  private EnumRegion playerRegion;
  private State playerRegionInfo;
  private DraftingPhase draftingPhase;
  private VotingPhase votingPhase;
  private PlayerInterface player;
  private ArrayList<PolicyCard> cardsForVote;
  private ServerConnection serverLine;
  private WorldData worldState;
  //WorldModel
  //GameStateTracker

  /**
   * Constructor for ai game.
   * TODO wait to see what server does for ais
   */
  public GameController()
  {
    //start AI game
    player = new AIPlayer();
    player.setPlayerName("Compy 386");
    view = new AIView(this, player);
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
      System.out.println("AvailableRegions message received");
      view.updateAvailableRegions((AvailableRegions) message, player);
    }
    else if(message instanceof BeginGame)
    {
      BeginGame msg = (BeginGame) message;
      beginGame(msg);
    }
    else if(message instanceof ClientChatMessage)
    {
      //message recieved.
    }
    else if(message instanceof GameState)
    {
      //update game state.
      GameState gameState = (GameState) message;
      worldState = gameState.worldData;
      //set player region info.
    }
    else if(message instanceof Goodbye)
    {
      //Logoff?
    }
    else if(message instanceof Hello)
    {
      //Confirmed connection.  This contains a salt thing for the password?
      System.out.println("Hello received!");
      Hello hello = (Hello) message;
      String salt = hello.loginNonce;
      Platform.runLater(() ->
      {
        guiView = (GuiBase) view;
        guiView.swapToLoginPane(salt);
      });
    }
    else if(message instanceof LoginResponse)
    {
      System.out.println("Login Response Received: " + ((LoginResponse) message).responseType);
      handleLoginResponse((LoginResponse)message);
    }
    else if(message instanceof PhaseStart)
    {
      handlePhaseStartMessage((PhaseStart) message);
    }
    else if(message instanceof ReadyToBegin)
    {
      //start countdown screen here?
    }
    else if(message instanceof Response)
    {
      //handle confirmation or error.
    }
    else if(message instanceof ServerChatMessage)
    {
      //handle chat stuff.
      receiveChatMessage((ServerChatMessage) message);
    }
  }

  /**
   * Send a message to the message queue
   * @param message       Serializable object.
   */
  public void sendMessageOut(Serializable message)
  {
    //send message to message queue;
    serverLine.sendMessage(message);
  }

  /**
   * If message is LoginResponse perform appropriate action
   * @param response        LoginResponse from server
   */
  private void handleLoginResponse(LoginResponse response)
  {
    LoginResponse.ResponseType type = response.responseType;
    guiView = (GuiBase) view;
    switch(type)
    {
      case ACCESS_DENIED:
        //bad password.  Inform user, do again
        Platform.runLater(() -> guiView.sendLoginFailed(response));
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

  /**
   * Decide what phase is starting based on PhaseStart message and take appropriate
   * action.
   * @param msg       PhaseStart message received from serveer.
   */
  public void handlePhaseStartMessage(PhaseStart msg)
  {
    switch(msg.currentGameState)
    {
      case LOGIN:
        //swap to login screen
        break;
      case BEGINNING:
        //countdown to start
        break;
      case DRAWING:
        //draw cards
        break;
      case DRAFTING:
        startPolicyDraftingPhase();
        break;
      case VOTING:
        startPolicyVotingPhase();
        break;
      case WIN:
        System.out.println("A winner is you!");
        break;
      case LOSE:
        System.out.println("All your base are belong to us.");
        break;
      case END:
        //game over? disconnect?
        break;
      default:
        System.out.println("Unknown state message.");
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
    guiView = (GuiBase)view; //set reference to view as a gui.
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
    //TODO may need to create an aiView object to have reference to.

    view = new AIView(this, player);
  }

  public void multiPlayerSelected()
  {
    guiView = (GuiBase)view;
    player = new HumanPlayer();
    serverLine = new ServerConnection(this);
    //guiView.serverConnectForm();
    guiView.swapToServerConnectionPane();
  }

  public void serverConnectionPaneCancelled()
  {
    guiView = (GuiBase) view;
    guiView.swapToEntryPane();
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
    //Temporary code. Subject to change.
    playerRegionInfo = State.CALIFORNIA;
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

  public void attemptConnection(String hostname, int port)
  {
    serverLine.startConnection(hostname, port);
  }

  /**
   * Send login info to the message queue in the form of a Login message.
   * @param name          name entered by user
   * @param password      password entered by user.
   * @param salt          salt for password.
   */
  public void sendLoginInfo(String name, String password, String salt)
  {
    player.setPlayerName(name);
    sendMessageOut(new Login(name, salt, password));
  }

  /**
   * Getter for player object.
   * @return
   */
  public PlayerInterface getPlayer()
  {
    return player;
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
   * @return                    RegionData object for specified region.
   */
  public RegionData getRegionDetails(EnumRegion regionClicked)
  {
    //return region details
    if(worldState != null)
    {
      return worldState.regionData[regionClicked.ordinal()];
    }
    else
    {
      return null;
    }
  }

  /**
   * When message is received from server, send to gui
   * @param messageReceived
   */
  public void receiveChatMessage(ServerChatMessage messageReceived)
  {
    //send message to gui.
    if(messageReceived.message == null)
    {
      //send card message to gui.
    }
    else
    {
      //send string message to gui.
    }
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
    draftingPhase = new DraftingPhase(this, player);
  }

  /**
   * Performs end drafting phase actions, and swaps to policy voting phase.
   * //TODO this can be cut out once we switch over to server run games.
   */
  public void endPolicyDraftingPhase()
  {
    //start voting phase.
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


  //need a way to get all discarded cards
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
    votingPhase = new VotingPhase(this);
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
   * //TODO this can be cut out when we switch to server run games.
   */
  public void endPolicyVotingPhase()
  {
    //fillHand();
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
