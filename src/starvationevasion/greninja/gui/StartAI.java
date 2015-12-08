package starvationevasion.greninja.gui;

import starvationevasion.greninja.gameControl.AIThread;

import java.util.Map;

/**
 * Created by sagalasan on 12/7/15.
 */
public class StartAI
{
  public static void main(String[] args)
  {
    System.out.println("Trying to staaaaaaaaaaart ai");
    for(String a : args) System.out.println(a);

    Map<String, String> map = System.getenv();


    String localhost = map.get("SEHOSTNAME");
    String port = map.get("SEPORT");
    String username = map.get("SEUSERNAME");
    String password = map.get("SEPASSWORD");
    int portInt = Integer.parseInt(port);



    /*AIThread aiThread = new AIThread(username, password, portInt, localhost);
    Thread thread = new Thread(aiThread);
    thread.start();*/
  }
}
