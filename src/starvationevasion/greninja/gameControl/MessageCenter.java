package starvationevasion.greninja.gameControl;

import starvationevasion.common.messages.*;

import java.io.Serializable;

/**
 * This will be a component of GameController.  It will handle messages comming
 * in from the server and decide what to do.  Anything that needs to go to the gui
 * gets kicked back to control.
 */
public class MessageCenter
{
  GameController control;
  
  public MessageCenter(GameController control)
  {
    this.control = control;
  }

  /**
   * Messages from server come in to this method.  Identifies message and sends
   * to the appropriate data structure or takes appropriate action
   * @param message       Serializable object.
   */
  public void handleMessageIn(Serializable message)
  {
    //identify message type.
    if(message instanceof ActionResponse)
    {
      //relay action response to appropriate channels.
    }
    else if(message instanceof AvailableRegions)
    {
      control.availableRegionReceived((AvailableRegions) message);
    }
    else if(message instanceof BeginGame)
    {
      System.out.println("Begin Game Message.");
      BeginGame msg = (BeginGame) message;
      control.beginGame(msg);
    }
    else if(message instanceof ClientChatMessage)
    {
      //message received.
    }
    else if(message instanceof GameState)
    {
      System.out.println("Recieved world state msg.");
      control.updateWorldStateInfo((GameState)message);
    }
    else if(message instanceof Goodbye)
    {
      //Logoff?
    }
    else if(message instanceof Hello)
    {
      control.helloReceived((Hello) message);
    }
    else if(message instanceof LoginResponse)
    {
      //System.out.println("Login Response Received: " + ((LoginResponse) message).responseType);
      control.handleLoginResponse((LoginResponse)message);
    }
    else if(message instanceof PhaseStart)
    {
      handlePhaseStartMessage((PhaseStart) message);
    }
    else if(message instanceof ReadyToBegin)
    {
      //start countdown screen here?
      control.startBeginGameCountdown((ReadyToBegin) message);
      System.out.println("Ready to begin message.");
    }
    else if(message instanceof Response)
    {
      //handle confirmation or error.
    }
    else if(message instanceof ServerChatMessage)
    {
      //handle chat stuff.
      control.receiveChatMessage((ServerChatMessage) message);
    }
    else if(message instanceof VoteStatus)
    {
      control.updateVoteStatusInfo((VoteStatus) message);
    }
  }



  /**
   * Decide what phase is starting based on PhaseStart message and take appropriate
   * action.
   * @param msg       PhaseStart message received from serveer.
   */
  public void handlePhaseStartMessage(PhaseStart msg)
  {
    System.out.println("PhaseStartMessage" + msg.currentGameState);
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
        control.startPolicyDraftingPhase();
        break;
      case VOTING:
        control.startPolicyVotingPhase();
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
}
