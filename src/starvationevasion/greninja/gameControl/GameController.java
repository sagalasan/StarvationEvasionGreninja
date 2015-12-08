package starvationevasion.greninja.gameControl;

import javafx.application.Platform;
import starvationevasion.common.*;
import starvationevasion.common.messages.*;
import starvationevasion.greninja.clientCommon.ClientConstant;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.greninja.model.AIPlayer;
import starvationevasion.greninja.model.HumanPlayer;
import starvationevasion.greninja.model.PlayerInterface;
import starvationevasion.greninja.model.State;
import starvationevasion.greninja.serverCom.ServerConnection;
import starvationevasion.sim.CardDeck;

import java.io.Serializable;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;


/**
 * Main communication hub of client application.  This will be the go-between
 * class for the server message handlers, world state model, game state classes
 * and gui. Incoming messages from the server are sent to MessageCenter
 * which calls back to control to perform the appropriate action.
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
  private ArrayList<PolicyCard> draftedCards;

  private ServerConnection serverLine;
  private WorldData worldState;
  private MessageCenter messageCenter;
  private boolean isHuman;
  private boolean isTesting = false;
  //WorldModel
  //GameStateTracker
  private CardDeck tempDeck; //For testing mode

  private boolean singlePlayerMode;

  private boolean DEBUG = false;

  /**
   * Constructor for ai game.  Sets up an AI view and ai player.
   */
  public GameController(String playerName)
  {
    //start AI game
    isHuman = false;
    player = new AIPlayer();
    player.setPlayerName(playerName);
    view = new AIView(this, player);
    serverLine = new ServerConnection(this);
    messageCenter = new MessageCenter(this);
    ((AIView)view).setDecisionObject(new AIDecisions((AIPlayer)player, 0));
    draftedCards = new ArrayList<>();
  }

  /**
   * Starter for human player game.
   * @param view       gui to use.
   */
  public GameController(GuiBase view)
  {
    isHuman = true;
    this.view = view;
    guiView = (GuiBase) view;
    cardsForVote = new ArrayList<>();
    messageCenter = new MessageCenter(this);
    draftedCards = new ArrayList<>();
  }

  /**
   * Returns a reference to the server line
   * @return        ServerLine
   */
  public ServerConnection getServerLine()
  {
    return serverLine;
  }

  /**
   * Is this controller for a human player?
   * @return      true if is a human player
   */
  public boolean isHuman()
  {
    return isHuman;
  }

  /**
   * Messages from server come in to this method.  Identifies message and sends
   * to the appropriate data structure or takes appropriate action
   * @param message       Serializable object.
   */
  public void handleMessageIn(Serializable message)
  {
    messageCenter.handleMessageIn(message);
  }

  /**
   * If message center gets a hello from server, it kicks the message back to control
   * to do the control stuff.
   * @param message
   */
  public void helloReceived(Hello message)
  {
    //Confirmed connection.  This contains a salt thing for the password?
    print("Hello received!");
    Hello hello = (Hello) message;
    String salt = hello.loginNonce;
    Platform.runLater(() ->
    {
      //guiView = (GuiBase) view;
      if(singlePlayerMode) guiView.swapToLoginPane(salt, true);
      guiView.swapToLoginPane(salt);
    });
  }

  /**
   * If availableRegion message is received, message center kicks it back to
   * control to do gui stuff.
   * @param message
   */
  public void availableRegionReceived(AvailableRegions message)
  {
    //print("AvailableRegions message received");
    if(isHuman)
    {
      Platform.runLater(() -> guiView.updateAvailableRegions(message, player));
    }
    else
    {
      view.updateAvailableRegions(message, player);
    }
  }

  /**
   * If message is LoginResponse perform appropriate action
   * @param response        LoginResponse from server
   */
  public void handleLoginResponse(LoginResponse response)
  {
    print("Login Response Received: " + response.responseType);
    LoginResponse.ResponseType type = response.responseType;
    //guiView = (GuiBase) view;
    switch(type)
    {
      case ACCESS_DENIED:
        //bad password.  Inform user, do again
        if(isHuman)
        {
          Platform.runLater(() -> guiView.sendLoginFailed(response));
        }//if ai?
        break;
      case ASSIGNED_REGION:
        EnumRegion region = response.assignedRegion;
        regionSelected(region);
        playerRegion = region;
        player.setPlayerRegion(region);
        if(isHuman)
        {
          Platform.runLater(() ->
          {
            view.swapToStagingPane();
            ((GuiBase) view).lockStagingPane(playerRegion);
            System.out.println(playerRegion);
          });
        }
        sendMessageOut(new RegionChoice(playerRegion));
        //skip staging pane?
        break;
      case REJOIN:
        //??
        break;
      case CHOOSE_REGION:
        //go to staging like normal.
        Platform.runLater(() -> view.swapToStagingPane());
        break;
      default:
        break;
    }
  }

  /**
   * Send a message to the message  out queue.
   * @param message       Serializable object.
   */
  public void sendMessageOut(Serializable message)
  {
    //send message to message queue;
    serverLine.sendMessage(message);
  }

  /*
  ============================Startup===========================================
  */

  /**
   * Starts no AIs or servers.  For testing.
   */
  public void startTestingGame()
  {
    isTesting = true;
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
   * Sees if the game is in testing mode.
   * @return return true if in testing mode.
   */
  public boolean isTesting()
  {
    return isTesting;
  }

  /**
   * Begins a single player game and instantiates a local server.
   * @param name  the player's login name.
   * @param pass  the player's password.
   */
  public void startSinglePlayerGame(String name, String pass)
  {
    singlePlayerMode = true;
    System.out.println("Start single player game.");
    guiView = (GuiBase)view; //set reference to view as a gui.
    //for remaining slots, start AiGame (on new thread?).
    serverLine = new ServerConnection(this);
    player = new HumanPlayer();
    player.setPlayerName("Player");
    //login
    //serverLine.startConnection(ClientConstant.LOCAL_HOST, ClientConstant.TEST_PORT);
    guiView.swapToServerConnectionPane(true);
  }

  /**
   * Setup for a multiplayer game.
   */
  public void multiPlayerSelected()
  {
    singlePlayerMode = false;
    guiView = (GuiBase)view;
    player = new HumanPlayer();
    serverLine = new ServerConnection(this);
    //guiView.serverConnectForm();
    guiView.swapToServerConnectionPane();
  }

  /**
   * Called when player backs out of server connection pane.0
   */
  public void serverConnectionPaneCancelled()
  {
    guiView = (GuiBase) view;
    guiView.swapToEntryPane();
  }

  /**
   * Perform region select actions.  Sets player region and informs server.
   * @param region region player chose.
   */
  public void regionSelected(EnumRegion region)
  {
    playerRegion = region;
    player.setPlayerRegion(region);
    if(isTesting) beginGame();
    //serverLine.sendMessage(new RegionChoice(region));
  }

  /**
   * If message received is a game state message, message center calls this.
   * @param state the current state of the game.
   */
  public void updateWorldStateInfo(GameState state)
  {
    worldState = state.worldData;
    if(isHuman)//Workaround to keep turn counter from updating
    {
      State.updateAllData(worldState);
    }
    updatePlayerHand(state.hand);
    view.gameStateUpdate();
  }

  /**
   * Update the player's hand with the new hand from the server.
   * @param hand        EnumPolicy[] from GameState Message.
   */
  public void updatePlayerHand(EnumPolicy[] hand)
  {
    ArrayList<PolicyCard> newHand = new ArrayList<>();
    for(EnumPolicy policy : hand)
    {
      newHand.add(PolicyCard.create(player.getPlayerRegion(), policy));
    }
    player.setPlayerHand(newHand);
    if(isHuman)
    {
      Platform.runLater(() -> guiView.setPlayerHand(newHand));
    }
  }

  /**
   * Once initial phases are over, setup rest of game and start policy drafting
   * phase.
   */
  private void beginGame()
  {
    //create initial hand
    //Temporary code. Subject to change.
    //playerRegionInfo = State.CALIFORNIA;
    view.initPlayerRegionInfo(playerRegionInfo, playerRegion);
    tempDeck = new CardDeck(playerRegion);
    //start policy drafting phase.
    if(isHuman) ((GuiBase) view).beginGameMessageReceived();
    if(isTesting)
    {
      startPolicyDraftingPhase();
    }
  }

  /**
   * Begin game.  Finalize player region with method from server and pass to
   * default begin game message.
   * @param msg
   */
  public void beginGame(BeginGame msg)
  {
    //confirm player region.
    for(EnumRegion region : msg.finalRegionChoices.keySet())
    {
      if(msg.finalRegionChoices.get(region).equals(player.getPlayerName()))
      {
        player.setPlayerRegion(region);
        player.setPlayerDeck(tempDeck);
        playerRegion = region;
      }
    }
    Platform.runLater(() -> beginGame());
  }

  /**
   * Starts the countdown for until the game begins.
   * @param message the message to be shown in the countdown.
   */
  public void startBeginGameCountdown(ReadyToBegin message)
  {
    if(isHuman) guiView.startBeginGameCountdown(message);
  }

  /**
   * Tell server line to attempt to connect with the supplied IP Address and port.
   * @param hostname        IP Address to connect to
   * @param port            Port to connect to.
   */
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
  /*
  public void getVisualization()
  {
    //return visualization to gui

  }*/

  /**
   * Get the current worldState WorldData object.
   * @return        WorldData currently held by server.
   */
  public WorldData getWorldData()
  {
    return worldState;
  }

  /**
   * When region is clicked on the interactive map, return region details for
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
    if (player.equals(null))
    {
      System.out.println("helloWorld");
    }
    System.out.println("helloWorld");
    System.out.println(player.getPlayerHand().get(0).getTitle());
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
    if(isTesting)
    {
    //  fillHand();
      guiView.setPlayerHand((ArrayList<PolicyCard>)player.getPlayerHand());
    }
    Platform.runLater(() ->
    {
      view.swapToPolicyPane();
      draftingPhase = new DraftingPhase(this, player);
    });
  }

  /**
   * Performs end drafting phase actions, and swaps to policy voting phase.
   * //TODO this can be cut out once we switch over to server run games.
   */
  public void endPolicyDraftingPhase()
  {
    //start voting phase.
    if(isTesting)
    {
      startPolicyVotingPhase();
    }
    //draftingPhase = null;
  }

  /**
   * Draft a policy.  Called from gui.  Drafting phase attempts to get card
   * and this method adds it to the appropriate policy pending list.
   * @param cardDrafted       card to draft.
   */
  //perhaps just take by the index in the player hand
  public void draftPolicy(PolicyCard cardDrafted)
  {
    //PolicyCard cardDrafted = draftingPhase.draftPolicy(cardIndex);
    if(cardDrafted != null)
    {
      //if card needs voting goes into cardsForVote, else goes to cardsToPlay
      if(cardDrafted.votesRequired() == 0)
      {
        //inform server that card was played;
        sendMessageOut(new DraftCard(cardDrafted));
      }
      else
      {
        sendMessageOut(new DraftCard(cardDrafted));
        //inform server that card was played;
        //cardsForVote.add(new PolicyCard(playerRegion, cardDrafted));
      }
      //TODO Temporary, remove
      if(isTesting)
      {
        tempDeck.discard(cardDrafted.getCardType());
      }
    }
    else
    {
      //inform gui that drafting action failed.
      System.out.println("Policy Draft Failed.");
    }
  }

  /**public void setDraftedCards()
  {
    draftedCards = new ArrayList<>();
  }**/
  //=====================Temporary deck management.
  //TODO Remove when done

  /**
   * @deprecated
   * Fill player hand up to 7 cards. quick and dirty
   */
  public void fillHand()
  {
    //System.out.println("filling hand");
    ArrayList<PolicyCard> newHand = new ArrayList<>();
    newHand.add(PolicyCard.create(player.getPlayerRegion(), EnumPolicy.Clean_River_Incentive));
    newHand.add(PolicyCard.create(player.getPlayerRegion(), EnumPolicy.Fertilizer_Subsidy));
    newHand.add(PolicyCard.create(player.getPlayerRegion(), EnumPolicy.GMO_Seed_Insect_Resistance_Research));
    newHand.add(PolicyCard.create(player.getPlayerRegion(), EnumPolicy.Efficient_Irrigation_Incentive));
    newHand.add(PolicyCard.create(player.getPlayerRegion(), EnumPolicy.Ethanol_Tax_Credit_Change));
    newHand.add(PolicyCard.create(player.getPlayerRegion(), EnumPolicy.Covert_Intelligence));
    newHand.add(PolicyCard.create(player.getPlayerRegion(), EnumPolicy.Foreign_Aid_for_Farm_Infrastructure));

    player.setPlayerHand(newHand);
  }
 // private void removeCardFromPlayerHand(int index)
 // {
  //  player.removeCard(index);
 // }

  /**
   * Sets the card from the player's hand to be drafted.
   * @param cardToDraft the card to be drafted.
   * @param index       the index of the card in the player's hand.
   * @return true if drafting is successful, else false.
   */
  public boolean setDraftCard(PolicyCard cardToDraft, int index)
  {
    if (draftedCards.size()<2)
    {
      draftedCards.add(cardToDraft);
      player.removeCard(index);
      //removeCardFromPlayerHand(index);
      draftPolicy(cardToDraft); //TODO put somewhere more appropriate.
      return true;
    }
    return false;
  }

  /**
   * Player can undo one or both cards that they drafted and return them back to their hand.
   * @param cardToUndo  the card to put back in the player's hand.
   * @return true if successful else false.
   */
  public boolean undoDraftCard(PolicyCard cardToUndo)
  {
    if (draftedCards.size()==0) return false;

    for (PolicyCard policy: draftedCards)
    {
      if (policy.equals(cardToUndo))
      {
        draftedCards.remove(policy);
        player.addCard(policy);
        return true;
      }
    }
    return false;
  }

  /**
   * Get the player's drafted cards.
   * @return an ArrayList of the cards drafted
   */
  public ArrayList<PolicyCard> getDraftedCards()
  {
    return draftedCards;
  }

  /**
   *
   */
  public void setDraftedCards()
  {
    draftedCards = new ArrayList<>();
  }

  //need a way to get all discarded cards
  /**
   * When player discards a card on the gui, call this.
   * @param cardIndex           index of card player selected.
   */
  public void discardOne(int cardIndex)
  {
    tempDeck.discard(player.getCard(cardIndex).getCardType());
    /*TODO will use again when server is set up for drafting
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
    }*/
  }

  /**
   * When gui's discard3 action is selected prompt user to select 3 cards.
   */
  public void discardThree()
  {
    //check if 3 cards in hand.
    //prompt user to select 3 cards to discard
    int[] cardsSelected = new int[3];
    for(int i = 0; i < 3; ++i)
    {
      tempDeck.discard(player.getCard(cardsSelected[i]).getCardType());
      player.discardCard(cardsSelected[i]);
    }
    //gui.selectThree(cardsSelected)

    /*TODO will use again when server is set up for drafting phase
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
    }*/
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
   * @param card        PolicyCard chosen by player.
   * @param vote    Vote Type enum chosen by player.
   */
  public void voteCast(PolicyCard card, VoteType vote)
  {
    serverLine.sendMessage(new Vote(playerRegion, vote));
  }


  /**
   * end voting phase.
   * //TODO this can be cut out when we switch to server run games.
   */
  public void endPolicyVotingPhase()
  {
    //fillHand();
    if(isTesting)
    {
      startPolicyDraftingPhase();
    }
    //votingPhase = null;
  }

  /**
   * Get Info about vote tallies on voting cards.
   * @param msg       VoteStatus message object.
   */
  public void updateVoteStatusInfo(VoteStatus msg)
  {
    //Send vote status to gui.
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

  /**
   * Prints the type of login response the player received.
   * @param print the login response message.
   */
  public void print(String print)
  {
    System.out.println(player.getPlayerName() + ": " + print);
  }
}
