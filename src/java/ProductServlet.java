/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import JSON.WriteProductJSON;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import smartcart.SmartCart2;

/**
 *
 * @author HEMANT
 */
public class ProductServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InterruptedException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductServlet at " + request.getContextPath() + "</h1>");
            
            Enumeration paramNames = request.getParameterNames();
            BufferedWriter writer = new BufferedWriter(new FileWriter("Path to file to write your answers"));
            while(paramNames.hasMoreElements()) {
               String paramName = (String)paramNames.nextElement();
               String paramValue =request.getParameter(paramName);
                   writer.write(paramValue);
                   writer.newLine();
            }
            writer.close();
                      
            
            HttpSession session= request.getSession();
            String price_range=session.getAttribute("price_range").toString();
           
            System.out.println(price_range);
            Map<String, List<String>> ProductList = new HashMap<String, List<String>>();           
            
            BufferedReader br1= new BufferedReader(new FileReader("Path to file containing list of all products for amazon"));
            WriteProductJSON obj=new WriteProductJSON();
            try{
                String line;
                
                while((line=br1.readLine())!=null)
                {
                    String[] words;
                    line=line.replace("|","\t");
                    words=line.split("\t");
            
                    List<String> valSet = new ArrayList<String>();
                    valSet.add(words[1]);    //Product Url
                    valSet.add(words[2]);    //Image Url
                    valSet.add(words[3]);    //Title
                    valSet.add(words[4]);    //Price
                    ProductList.put(words[0],valSet);
                }
                
                BufferedReader br2= new BufferedReader(new FileReader("Path to file containing list of all products for flipkart"));
           
                while((line=br2.readLine())!=null)
                {   
                    String[] words;
                    line=line.replace("|","\t");
                    words=line.split("\t");
                    List<String> valSet = new ArrayList<String>();
                    valSet.add(words[4]);    //Product Url
                    valSet.add(words[2]);    //Image Url
                    valSet.add(words[1]);    //Title
                    valSet.add(words[3]);    //Price
                    ProductList.put(words[0],valSet);
                }
                
                List<Map.Entry<String, Integer>> list=SmartCart2.getProductList(price_range);
                               
                for (Map.Entry<String, Integer> entry:list)
                {
                   String id= entry.getKey();
                   System.out.println("Return id:"+id);
                   for (Map.Entry<String, List<String>> entry1 : ProductList.entrySet())
                   {
                       String key = entry1.getKey();
                       System.out.println("My id:"+key);
                       if(key.equals(id))
                       {
                           List<String> values = entry1.getValue();
                           String rating=entry.getValue().toString();
                           System.out.println("My url:"+values.get(1));
                           obj.buildJSON(values.get(1),values.get(2),values.get(3),rating,values.get(0));
                           break;
                       }
                   } 
                }

            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            
            int x= obj.write();
            Thread.sleep(30000);
            response.sendRedirect("Products.html");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
