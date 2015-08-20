package JSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pranav
 */
public class WriteProductJSON {
    
    JSONObject products;
    JSONArray details;  
    public WriteProductJSON()
    {
        products = new JSONObject();  
        details= new JSONArray();
    }
    
    public void buildJSON(String imageURL, String name, String price, String rating, String productURL) {
     
        JSONObject product1 = new JSONObject();  
        product1.put("image", imageURL);  
        product1.put("name", name);  
        product1.put("cost", price);
        product1.put("good", rating);
        product1.put("buy", productURL);
     
        details.add(product1);
    }
    public int write()
    {
        products.put("products", details);
        try {  
              
            // Writing to a file  
            File file=new File("Path to products.json");  
            file.createNewFile();  
            FileWriter fileWriter = new FileWriter(file);  
            System.out.println("Writing JSON object to file");  
            System.out.println("-----------------------");  
            System.out.print(products);  
  
            fileWriter.write(products.toJSONString());  
            fileWriter.flush();  
            fileWriter.close();  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return 0;
    }
}
