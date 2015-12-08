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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import starvationevasion.common.EnumFood;
import starvationevasion.io.CropCSVLoader;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Jalen on 12/7/2015.
 */
public class ToolbarButtonPane extends BorderPane
{
  public ToolbarButtonPane(EnumFood type, Image bigImage)
  {
    setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 0;");
    ImageView image = new ImageView(bigImage);
    HBox top = new HBox();


    VBox titleInfo = new VBox();

    Label title = new Label(type.toString());
    title.setTextFill(Color.WHITE);
    title.setAlignment(Pos.CENTER);
    title.setStyle( "-fx-font-size:26;"+"-fx-border-color: white;");

    Text text = new Text(type.toLongString());
    text.setFill(Color.WHITE);
    text.setStyle("-fx-font-size:16;");

    titleInfo.getChildren().addAll(title, text);

    top.getChildren().addAll(image, titleInfo);
    setTop(top);



    try{
      File file = new File("data/trivia.xml");
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
          .newInstance();

      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse(file);




      Random rand = new Random();
      //6 questions
      //only get the category
      /**
      for (int i = 0; i < 6; i ++)
      {

      }
       **/
    //loop through each question, see if correct category, then use said question
      int numberOfQuestions = document.getDocumentElement().getElementsByTagName("question").getLength();

      for (int i = 0; i < numberOfQuestions; i++)
      {
        /**for(Element element: document.getElementsByTagName("question").item(1).getChildNodes().item(5))
        {

        }**/
      }

      //System.out.println("document tag name length is " +document.getElementsByTagName("question").item(1).getChildNodes().item(2).getTextContent());//.getNodeName());
      String question = document.getElementsByTagName("question").item(rand.nextInt(numberOfQuestions)).getTextContent();//.getTextContent();
      //document.getElementsByTagName("category");
      //System.out.println(question);

      VBox questionBlock = new VBox();
      Label trivia = new Label("Trivia Question!");
      trivia.setTextFill(Color.WHITE);
      title.setStyle( "-fx-font-size:26;");
      Text quest = new Text(question);
      quest.setFill(Color.WHITE);

      questionBlock.getChildren().addAll(trivia, quest);
      setCenter(questionBlock);

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



    //getChildren().add(image);
  }

}
