package starvationevasion.greninja.gui;

import java.io.IOException;

/**
 * Created by sagalasan on 12/7/15.
 */
public class ProcessTest
{
  public static void main(String[] args)
  {
    String[] command = new String[2];
    command[0] = "java";
    command[1] = "starvationevasion.greninja.gui.StartAI";
    ProcessBuilder processBuilder = new ProcessBuilder(command);
    processBuilder.inheritIO();
    System.out.println(processBuilder.directory());
    System.out.println(System.getProperty("user.dir"));
    try
    {
      processBuilder.start();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
