package starvationevasion.greninja.gui;

import starvationevasion.server.Server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagalasan on 12/3/15.
 */
public class ServerThread
{
  private ArrayList<String> commands;
  public ServerThread(String loginPath)
  {
    commands = new ArrayList<>();
    commands.add("java");
    commands.add("starvationevasion/server/Server");
    commands.add(loginPath);
    //commands.add("data/config/");
    ProcessBuilder processBuilder = new ProcessBuilder(commands);
    //processBuilder.directory(new File("/home/sagalasan/Documents/ideaProjects/StarvationEvasionGreninja/out/production/StarvationEvasionGreninja"));
    processBuilder.directory(new File("./out/production/StarvationEvasionGreninja"));
    List<String> result = processBuilder.command();
    for(int i = 0; i < result.size(); i++)
    {
      System.out.println(result.get(i));
    }

    processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
    processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
    Process start;
    try
    {
      start = processBuilder.start();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return;
    }
    try
    {
      start.waitFor();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    ServerThread serverThread = new ServerThread("config/easy_password_file.tsv");
  }
}
