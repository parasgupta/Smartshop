/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcart;

/**
 *
 * @author Paras
 */
 
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
 
public class SmartCart2{
    public static int checkgood(List<String[]> s, String s1, String s2)
    {
        String[] good={"good","awesome","powerful","great","cool","fantastic","superb","fine","nice","high","better","brilliant","perfect","decent","rich","superb","smart","excellent","best"};
        for(int i=0;i<s.size();i++)
        {
            String[] y=s.get(i);
            if((asList(good).contains(s1.toLowerCase())&&asList(good).contains(y[0].toLowerCase()))&&y[1].equalsIgnoreCase(s2))
            {
                return i;
            } 
            if(y[1].equalsIgnoreCase(s2)&&asList(good).contains(s1.toLowerCase()))
            {
                return i;
            }
        }
        return -1;
    }
    public static int checkbad(List<String[]> s, String s1, String s2)
    {
        String[] bad={"bad","faulty","worst","low","poor","slow","less","pathetic","weak","defective","average","unwanted","little","horrible"};
        for(int i=0;i<s.size();i++)
        {
            String[] y=s.get(i);
            if((asList(bad).contains(s1.toLowerCase())&&asList(bad).contains(y[0].toLowerCase()))&&y[1].equalsIgnoreCase(s2))
            {
                return i;
            } 
            if(y[1].equalsIgnoreCase(s2)&&asList(bad).contains(s1.toLowerCase()))
            {
                return i;
            }
        }
        return -1;
    }
    
    
	public static List<Entry<String, Integer>> getProductList(String price_range) throws IOException {
		
            String[] partial,parts;
            BufferedReader br1 = null;
            String s =price_range,sLine,x = null;//How would you prioritize...
            List<String[]> ques=new ArrayList<>();  //   category_arraylist     
            s="C:\\Users\\parasgupta301\\Documents\\NetBeansProjects\\SmartShop\\QuestionnaireFiles\\"+s+".txt";
            br1 = new BufferedReader(new FileReader(s));
            MaxentTagger tagger = new MaxentTagger("C:\\Users\\parasgupta301\\Documents\\NetBeansProjects\\SmartShop\\src\\java\\taggers\\english-left3words-distsim.tagger");
            boolean flag;
            int ind;
	    while ((sLine = br1.readLine()) != null) 
            {
                if(sLine!="")
                {
                    x=sLine.substring(25);
                    x=x.toLowerCase();
                    partial=x.split(" ");
                    ques.add(partial);
                }
            } 
            
            BufferedReader br3 = null;
            List<String> ans=new ArrayList<>();          
             br3 = new BufferedReader(new FileReader("C:\\Users\\parasgupta301\\Documents\\NetBeansProjects\\SmartShop\\Answer.txt"));
            String read;
	    while ((read=br3.readLine()) != null) 
            {
                ans.add(read);
            } 
            String range=price_range;
            final File folder = new File("C:\\Users\\parasgupta301\\Documents\\NetBeansProjects\\SmartShop\\lib\\projectdata\\Product instock 500\\"+range);
            HashMap<String,Integer> points=new HashMap<String,Integer>(); 
            String sCurrentLine;
            BufferedReader br2 = null;
            for (final File fileEntry : folder.listFiles()) 
            {
                br2 = new BufferedReader(new FileReader(fileEntry));
                int pts=0;
                String sa=fileEntry.getName().substring(fileEntry.getName().indexOf('M'),fileEntry.getName().indexOf(".txt"));
                String np;
                List<String> words=new ArrayList<>();
                while((np=br2.readLine())!=null)
                {
                    String tagged = tagger.tagString(np);
                    words = Arrays.asList(tagged.split(" "));
                    for (int i=0;i<words.size();i++) 
                            {
                                String word=words.get(i);
                                if(!"".equals(word))
                                {
                                    parts=word.split("_");
                                    if(parts[1].contains("JJ"))
                                    {  
                                        flag=true;
                                        if(i>=2)
                                        {
                                            if(words.get(i-1).contains("is"))
                                            {
                                                if(words.get(i-2).contains("NN"))
                                                {
                                                    String adj=words.get(i).substring(0,words.get(i).indexOf('_')).toLowerCase();
                                                    String noun=words.get(i-2).substring(0,words.get(i-2).indexOf('_')).toLowerCase();     
                                                     if(((ind=checkgood(ques,adj,noun))!=-1))
                                                    {
                                                        String rat=ans.get(ind);
                                                        switch (rat) {
                                                            case "High":
                                                                pts+=10;
                                                                break;
                                                            case "Medium":
                                                                pts+=5;
                                                                break;
                                                            case "Low":
                                                                pts+=2;
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                     else if(((ind=checkbad(ques,adj,noun))!=-1))
                                                     {
                                                         String rat=ans.get(ind);
                                                        switch (rat) {
                                                            case "High":
                                                                pts-=10;
                                                                break;
                                                            case "Medium":
                                                                pts-=5;
                                                                break;
                                                            case "Low":
                                                                pts-=2;
                                                                        break;
                                                            default:
                                                                break;
                                                        }
                                                     }
                                                    flag=false;
                                                }
                                            }
                                        }
                                        if(flag)
                                        {
                                            if(i!=words.size()-1)
                                            {
                                                if(words.get(i+1).contains("NN"))
                                                {
                                                    String adj=words.get(i).substring(0,words.get(i).indexOf('_')).toLowerCase();
                                                    String noun=words.get(i+1).substring(0,words.get(i+1).indexOf('_')).toLowerCase();     
                                                     if(((ind=checkgood(ques,adj,noun))!=-1))
                                                    {
                                                        String rat=ans.get(ind);
                                                        switch (rat) {
                                                            case "High":
                                                                pts+=10;
                                                                break;
                                                            case "Medium":
                                                                pts+=5;
                                                                break;
                                                            case "Low":
                                                                pts+=2;
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                     else if(((ind=checkbad(ques,adj,noun))!=-1))
                                                     {
                                                         String rat=ans.get(ind);
                                                        switch (rat) {
                                                            case "High":
                                                                pts-=10;
                                                                break;
                                                            case "Medium":
                                                                pts-=5;
                                                                break;
                                                            case "Low":
                                                                pts-=2;
                                                                        break;
                                                            default:
                                                                break;
                                                        }
                                                     }   
                                                i+=1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                }
            points.put(sa,pts);
            }
            final File folder1 = new File("C:\\Users\\parasgupta301\\Documents\\NetBeansProjects\\SmartShop\\lib\\projectdata\\AmazonCrawl\\"+range);
            for (final File fileEntry : folder1.listFiles()) 
            {
                br2 = new BufferedReader(new FileReader(fileEntry));
                int pts=0;
                String sa=fileEntry.getName().substring(0,fileEntry.getName().indexOf(".txt"));
                String np;
                List<String> words=new ArrayList<>();
                while((np=br2.readLine())!=null)
                {
                    String tagged = tagger.tagString(np);
                    words = Arrays.asList(tagged.split(" "));
                    for (int i=0;i<words.size();i++) 
                            {
                                String word=words.get(i);
                                if(!"".equals(word))
                                {
                                    parts=word.split("_");
                                    if(parts[1].contains("JJ"))
                                    {  
                                        flag=true;
                                        if(i>=2)
                                        {
                                            if(words.get(i-1).contains("is"))
                                            {
                                                if(words.get(i-2).contains("NN"))
                                                {
                                                    String adj=words.get(i).substring(0,words.get(i).indexOf('_')).toLowerCase();
                                                    String noun=words.get(i-2).substring(0,words.get(i-2).indexOf('_')).toLowerCase();     
                                                     if(((ind=checkgood(ques,adj,noun))!=-1))
                                                    {
                                                        String rat=ans.get(ind);
                                                        switch (rat) {
                                                            case "High":
                                                                pts+=10;
                                                                break;
                                                            case "Medium":
                                                                pts+=5;
                                                                break;
                                                            case "Low":
                                                                pts+=2;
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                     else if(((ind=checkbad(ques,adj,noun))!=-1))
                                                     {
                                                         String rat=ans.get(ind);
                                                        switch (rat) {
                                                            case "High":
                                                                pts-=10;
                                                                break;
                                                            case "Medium":
                                                                pts-=5;
                                                                break;
                                                            case "Low":
                                                                pts-=2;
                                                                        break;
                                                            default:
                                                                break;
                                                        }
                                                     }
                                                    flag=false;
                                                }
                                            }
                                        }
                                        if(flag)
                                        {
                                            if(i!=words.size()-1)
                                            {
                                                if(words.get(i+1).contains("NN"))
                                                {
                                                    String adj=words.get(i).substring(0,words.get(i).indexOf('_')).toLowerCase();
                                                    String noun=words.get(i+1).substring(0,words.get(i+1).indexOf('_')).toLowerCase();     
                                                     if(((ind=checkgood(ques,adj,noun))!=-1))
                                                    {
                                                        String rat=ans.get(ind);
                                                        switch (rat) {
                                                            case "High":
                                                                pts+=10;
                                                                break;
                                                            case "Medium":
                                                                pts+=5;
                                                                break;
                                                            case "Low":
                                                                pts+=2;
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                     else if(((ind=checkbad(ques,adj,noun))!=-1))
                                                     {
                                                         String rat=ans.get(ind);
                                                        switch (rat) {
                                                            case "High":
                                                                pts-=10;
                                                                break;
                                                            case "Medium":
                                                                pts-=5;
                                                                break;
                                                            case "Low":
                                                                pts-=2;
                                                                        break;
                                                            default:
                                                                break;
                                                        }
                                                     }   
                                                i+=1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                }
            points.put(sa,pts);
            }
            
             Set<Entry<String,Integer>> hashSet=points.entrySet();
             List<Entry<String, Integer>> list = new ArrayList<>(hashSet);
             Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
             {
                 @Override
                 public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
                 {
                     return (o2.getValue()).compareTo( o1.getValue() );
                 }

             });
             
            
             return list;
             
        }

}