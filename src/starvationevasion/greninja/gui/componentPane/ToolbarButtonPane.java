package starvationevasion.greninja.gui.componentPane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import starvationevasion.common.EnumFood;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is the overlay for when a toolbar icon is clicked on
 */
public class ToolbarButtonPane extends BorderPane
{
  /**
   *
   * @param type the type of overlay to create
   * @param bigImage the image of the bigger icon
   */
  public ToolbarButtonPane(EnumFood type, Image bigImage)
  {
    setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 0;");
    ImageView image = new ImageView(bigImage);
    HBox top = new HBox();

    VBox titleInfo = new VBox();
    //the title of the category
    Label title = new Label(type.toString());
    title.setTextFill(Color.WHITE);
    title.setAlignment(Pos.CENTER);
    title.setStyle( "-fx-font-size:26;"+"-fx-border-color: white;");

    //the text of what types of fruits in this category
    Text text = new Text(type.toLongString());
    text.setFill(Color.WHITE);
    text.setStyle("-fx-font-size:16;");

    titleInfo.getChildren().addAll(title, text);

    top.getChildren().addAll(image, titleInfo);
    setTop(top);

    try{
      /**
       * makes an xml reader that looks for the correct category and then gets the question
       */
      File file = new File("data/trivia.xml");
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
          .newInstance();

      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse(file);



      //loop through each question, see if correct category, then use said question
      NodeList nodes = document.getElementsByTagName("category");
      goodQuestionsAndAnswers = new ArrayList<>();

      //finds good questions that apply to the category
      for (int j = 0; j < nodes.getLength(); j++)
      {
        System.out.println(nodes.item(j).getTextContent());
        if (nodes.item(j).getTextContent().equals(type.toString()) || nodes.item(j).getTextContent().equals("All"))
        {
          //store in list of good questions
          goodQuestionsAndAnswers.add(nodes.item(j).getParentNode().getTextContent());
        }
      }
      setRandomQuestion();
      //makes the question display


    }
    catch (ParserConfigurationException pce)
    {
      System.out.println(pce.getMessage());
    }
    catch (SAXException se)
    {
      System.out.println(se.getMessage());
    }
    catch (IOException ioe)
    {
      System.err.println(ioe.getMessage());
    }

  }
  private  ArrayList<String> goodQuestionsAndAnswers;
  public void setRandomQuestion()
  {
    VBox questionBlock = new VBox();
    Label trivia = new Label("Trivia Question!");
    trivia.setTextFill(Color.WHITE);
    trivia.setStyle("-fx-font-size:26;");
    Random rand = new Random();
    Text quest = new Text(goodQuestionsAndAnswers.get(rand.nextInt(goodQuestionsAndAnswers.size())));
    quest.setFill(Color.WHITE);

    questionBlock.getChildren().addAll(trivia, quest);
    setCenter(questionBlock);
  }

}
