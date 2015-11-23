package starvationevasion.greninja.serverCom;

/**
 * Created by sagalasan on 11/22/15.
 */
public class ServerState
{
  private static volatile ServerState myInstance = null;
  private ServerStateEnum state;

  public enum ServerStateEnum
  {
    IDLE,
    WAITING_FOR_LOGIN_RESPONSE,
    WAITING_FOR_BEGIN_GAME_RESPONSE,
    WAITING_FOR_RESPONSE
  }

  public synchronized static ServerState getInstance()
  {
    if(myInstance == null)
    {
      myInstance = new ServerState();
    }
    return myInstance;
  }

  private ServerState()
  {
    state = ServerStateEnum.IDLE;
  }
}
