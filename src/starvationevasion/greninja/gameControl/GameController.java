package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.clientCommon.EnumPhase;


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
  //WorldModel
  //GameStateTracker

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
    gui.swapToStagingPane();
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
    startPolicyDraftingPhase();//
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
    draftingPhase = new DraftingPhase(this, gui.getTimerPane(EnumPhase.DRAFTING));
  }

  /**
   * Performs end drafting phase actions.
   */
  public void endPolicyDraftingPhase()
  {
    //add cards to hand.
    //start voting phase.
    //TODO put in startPolicyVotingPhase()
    gui.swapToVotingPane();
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

  /**
   * Pass message to change color.
   * @param time            Phase time remaining in minutes.
   */
  public void timerColorChange(int time)
  {
    //gui.timerColorChange(time);
  }
  /*
  ==================drafting phase end==========================================
  ******************************************************************************
  ==============================VOTING PHASE====================================
  */

  public void startVotingPhase()
  {
    gui.swapToVotingPane();
    //do stuff
  }

  /*
  ===========voting phase end===================================================
   */
}
