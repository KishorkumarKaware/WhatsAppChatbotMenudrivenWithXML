package com.vyomChatBot;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadXMLFile {
	
	public static  Document doc ;
	public static  Map<String,String> NextCall = new HashMap<String,String>();
	public static String position = "",menuOption = "", ChildId = "";
	public static String MenuLevel = "1_1";
	public static String ParentId = null, userInput = "";
	public static Scanner reader = new Scanner(System.in); 
	public static NodeList menuList;
	
	public static void main(String argv[]) 
	{

	    try {
	    	
	    	//AEChatBotMenu aeChatBotMenu = new AEChatBotMenu();
	    	//System.out.println(aeChatBotMenu.toString());

		
		
		System.out.println("----------------------------");
			
		
		boolean result = true;
		
		/*do
		{				
			NextCall.clear();						
			MenuLevel = ReadXMLFile2.MenuOptions(MenuLevel);				
			result = !MenuLevel.equals("0");
		}while(result);*/
		
		//String Msg = ReadXMLFile2.MenuOptions("1");
		
		
		System.out.println("Thanx for using chat services.");
		reader.close();
		
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	  }
	
	public String MenuOptions( String userInput) throws Exception
	{
		System.out.println("NextCall : " + NextCall);
		String Msg = "";
		File fXmlFile = new File("staff2.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(fXmlFile);
		
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		menuList = doc.getElementsByTagName("MenuNode");
		System.out.println("userInput : " + userInput);
		if(userInput == null)
			return "Invalid input please restart session with Hi";
		if(userInput.equals(""))
				MenuLevel = "1_1";
		else
			if(userInput.equals("0"))
				return "Thanx for using chat services.";
			else 
				MenuLevel = NextCall.get(userInput);
		System.out.println("MenuLevel : " + MenuLevel);
		if(MenuLevel == null)
		{
			System.out.println("Invalid option..!");
			return "Invalid option..!";
		}
	
		NextCall.clear();
		for (int  i = 0 ; i < menuList.getLength(); i++) 
		{			
			Element menuElement = (Element) menuList.item(i);			
			if(menuElement.getAttribute("MenuLevel").equals(MenuLevel))
			{
				
				
				NodeList containerList = menuElement.getElementsByTagName("ContainerDisplayNode");
				ParentId = menuElement.getAttribute("ParentLevel");
				
				for (int  index = 0 ; index < containerList.getLength(); index++) 
				{
					Element element = (Element) containerList.item(index);		
					
					position = element.getElementsByTagName("Position").item(0).getTextContent();					
					menuOption = element.getElementsByTagName("DisplayText").item(0).getTextContent();
					ChildId = element.getElementsByTagName("ChildId").item(0).getTextContent();
					/*if(ChildId == null || ChildId == "")
					{
						Node ActionNode = element.getElementsByTagName("ActionNodeData").item(0);
						
						String ActionMsg = ActionNode.getElementsByTagName("ActionClass").item(0).getTextContent();
						return ActionMsg;
					}*/
					System.out.println(position + ". " + menuOption);
					Msg = Msg + "\n" + position + ". " + menuOption;
					NextCall.put(position,ChildId);					
				}
				System.out.println("*. Go to previous menu");
				NextCall.put("*",ParentId );	
				Msg = Msg + "\n" +"*. Go to previous menu";
				System.out.println("#. Go to Main menu");
				NextCall.put("#","1_1");
				Msg = Msg + "\n" +"#. Go to Main menu";
				System.out.println("0. To Exit");
				NextCall.put("0","0");
				Msg = Msg + "\n" +"0. To Exit";
			}
			
		}
			
		//do 
		//{
			Msg = Msg + "\n\nEnter a sequence number which you want to select : ";
			System.out.println("\nEnter a sequence number which you want to select : ");
			//userInput = reader.next();
			//}while(MenuLevel == null);
		
		
		return Msg;
	}
	

	}