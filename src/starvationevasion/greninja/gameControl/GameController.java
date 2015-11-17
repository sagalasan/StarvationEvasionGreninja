package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.clientCommon.EnumPhase;

import java.util.ArrayList;
import java.util.List;


/**
 * Main communication hub of client application.  This will be the go-between
 * class for the server message handlers, world state model, game state classes
 * and gui.
 */
public class GameController
{
  private GuiBase gui;
  private EnumRegion playerRegion;
  private DraftingPhase draftingPhase;
  private VotingPhase votingPhase;
  private Player player;
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
  public void beginGame()
  {
    //create initial hand
    ArrayList<EnumPolicy> initialHand = new ArrayList<>();
    fillHand(initialHand);
    //instantiate player
    player = new Player(playerRegion, initialHand);
    //start policy drafting phase.
    startPolicyDraftingPhase();
  }

  /**
   * Deals cards to player hand from deck up to the max hand size.  Initially
   * called with the hand itself.  Should be called with player.getHand() during
   * the game.
   * @param hand        The player's hand of enum policies.
   */
  public void fillHand(List<EnumPolicy> hand)
  {
    //TODO make correct hand when we know how the deck will work.
    while(hand.size() < 7)
    {
      hand.add(EnumPolicy.International_Food_Releif_Program);
    }
  }
  /*
  ============================end startup=======================================
  ******************************************************************************
  ===========================GENERAL PHASE METHODS==============================
  */

  /*
  =========end general phase methods============================================
  ******************************************************************************
  ============================DRAFTING PHASE====================================
  */

  /**
   * Instantiate new policyDrafting phase
   */
  public void startPolicyDraftingPhase()
  {
    gui.swapToPolicyPane();
    draftingPhase = new DraftingPhase(this, gui.getTimerPane(EnumPhase.DRAFTING),
                                      player);
  }

  /**
   * Performs end drafting phase actions.
   */
  public void endPolicyDraftingPhase()
  {
    //start voting phase.
    //TODO put in startPolicyVotingPhase()
    startPolicyVotingPhase();
    draftingPhase = null;
  }

  /**
   * When player discards a card on the gui, call this.
   * @param cardIndex           index of card player selected.
   */
  public void discardOne(int cardIndex)
  {
    if(draftingPhase.discardOne(cardIndex))
    {
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
    votingPhase = new VotingPhase(this, gui.getTimerPane(EnumPhase.VOTING));
    //do stuff
  }

  /**
   * end voting phase.
   */
  public void endPolicyVotingPhase()
  {
    //add cards to hand.
    startPolicyDraftingPhase();
    votingPhase = null;
  }

  /*
  ===========voting phase end===================================================
   */
}
