
package manish_zappos_application;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


 class Products
{
    float price;                        //Product class which has the given attributes
    String productName;
    String brandName;
    String styleId;
    
}


public class Manish_Zappos_Application {

    ArrayList<Products> Product_List = new ArrayList<Products>();       //an arraylist to store Product objects
    
    private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;                                                              //function to return string
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);                                              //read iteratively till the end
    }
    return sb.toString();
  }
    
    public static JSONObject readJsonFromUrl(String url) throws IOException, ParseException {
    InputStream is = new URL(url).openStream();                         
    
    String jsonText = "";
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      jsonText = readAll(rd);
      JSONParser parser = new JSONParser();                               //JsonParser to parse the returned string
      Object obj = parser.parse(jsonText);
      JSONObject jsonObject = (JSONObject) obj;                           //return a JsonObject to parse through the file
      return jsonObject;
 
    } 
   
    finally {
      is.close();                                                         //gaurantees closing the input stream in case of an error
    }
  }
    

    private ArrayList getProductList(JSONObject jsonObject) {           //function to parse through the produtct list using jsonObject
        
         
         JSONArray jarray = (JSONArray) jsonObject.get("results");      //jsonArray to get product attributes
                
         for(int i = 0; i < jarray.size(); i++)
                {
                JSONObject obj2 = (JSONObject)jarray.get(i);
                Products p = new Products();
                p.productName = (String)obj2.get("productName");
                p.brandName = (String)obj2.get("brandName");
                p.styleId = (String)obj2.get("styleId");
                String x = (String)obj2.get("price");
                x = x.replace("$", "");                                 //replace $ and , present in the price attribute
                x = x.replace(",", "");
                p.price = Float.parseFloat(x);
                Product_List.add(p);
                
                }
               
        return Product_List;
    }
    
    public static void main(String[] args) throws IOException, ParseException {
        
         Scanner sc = new Scanner(System.in); 
        
        // ArrayList<Products> Product_List = new ArrayList<Products>();
         ArrayList<Products> Product_List = null;
         Manish_Zappos_Application m = new Manish_Zappos_Application();
         String jsonText = "";
         String frontUrl = "http://api.zappos.com/Search?sort={%22price%22:%22asc%22}&limit=100&page=";     //split URL in order to accomodate change in page number
         String endUrl = "&key=a73121520492f88dc3d33daf2103d7574f1a3166";
         JSONObject jsonObject = null;
         for(int i=60; i<65; i++) {
         jsonObject = readJsonFromUrl(frontUrl + i + endUrl);       //There are 1372 pages of products. In order to pass each page, a for loop is used
         Product_List = m.getProductList(jsonObject);               //the values of for loop can be changed to iterate the required pages
         }
         
        /* String name[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
         float price[] = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
         String bn[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
         String id[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
         
         for(int i=0; i<name.length; i++)
         {
             Products pp = new Products();
             pp.productName = name[i];
             pp.price = price[i];
             pp.brandName = bn[i];
             pp.styleId = id[i];
             Product_List.add(pp);
         }*/
         
         ProductCombos combo = new ProductCombos(Product_List);
         System.out.println("Please enter the required number of products");        //takes user input for sum and number of products
         int no_of_prodcuts = sc.nextInt();
         System.out.println("Please enter the estimated sum total of the product combination");
         int sum = sc.nextInt();
         
	 int ans = combo.subArraySum(sum, no_of_prodcuts);      //if there there are products less than the user entered sum, it will search the product combinations for the new sum
	 if (ans != sum) {
             ProductCombos p = new ProductCombos(Product_List);
             p.subArraySum(ans, no_of_prodcuts);
         }
	 //System.out.println("No product combination available for your requested input");
       
        
    }
}
