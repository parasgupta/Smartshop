/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcart;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;

/**
 *
 * @author parasgupta301
 */
public class SmartCart {
public static int check(List<String[]> s, String s1, String s2)
    {
        String[] good={"good","awesome","powerful","great","cool","fantastic","superb","fine","nice","high","better","brilliant","perfect","decent","rich","superb","smart","excellent","best"};
        String[] bad={"bad","faulty","worst","low","poor","slow","less","pathetic","weak","defective","average","unwanted","little","horrible"};
        for(int i=0;i<s.size();i++)
        {
            String[] y=s.get(i);
            if((y[0].equalsIgnoreCase(s1)||(asList(good).contains(s1.toLowerCase())&&asList(good).contains(y[0].toLowerCase()))||(asList(bad).contains(s1.toLowerCase())&&asList(bad).contains(y[0].toLowerCase())))&&y[1].equalsIgnoreCase(s2))
            {
                return i;
            } 
        }
        return -1;
    }
public static int check1(List<String[]> s, String s1, String s2,String s3)
    {
        String[] good={"good","awesome","powerful","great","cool","fantastic","superb","fine","nice","high","better","brilliant","perfect","decent","rich","superb","smart","excellent","best"};
        String[] bad={"bad","faulty","worst","low","poor","slow","less","pathetic","weak","defective","average","unwanted","little","horrible"};
        for(int i=0;i<s.size();i++)
        {    
            String[] y=s.get(i);
            //System.out.println(y[0]+y[1]+y[2]);
            if((y[0].equalsIgnoreCase(s1)||(asList(good).contains(s1.toLowerCase())&&asList(good).contains(y[0].toLowerCase()))||(asList(bad).contains(s1.toLowerCase())&&asList(bad).contains(y[0].toLowerCase())))&&y[1].equalsIgnoreCase(s2)&&(y[2]!=null&&y[2].equalsIgnoreCase(s3)))
            {
                return i;
            } 
        }
        return -1;
    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //String[] words;
        List<String> words = new ArrayList<>();
        String[] parts;
        //String[][] pairs=new String[1000000][3];
        //Map<String,String> pairs = new HashMap<String,String>();
        List<String[]> pairs = new ArrayList<>();
        int x=0;
        int ind=-1;
        boolean flag=true;
        List<Integer> count=new ArrayList<>();
       // parts = new String[2];
        MaxentTagger tagger = new MaxentTagger("C:\\Users\\parasgupta301\\Documents\\NetBeansProjects\\SmartShop\\src\\java\\taggers\\english-left3words-distsim.tagger");
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        //Stemmer stemmer1 = new Stemmer();
        //String sample=br.readLine();
        //System.out.println();
        
        BufferedReader br1 = null;
                 try {
			String sample;
			br1 = new BufferedReader(new FileReader("C:\\Users\\parasgupta301\\Documents\\NetBeansProjects\\SmartShop\\lib\\projectdata\\review5000less.txt"));
                        while ((sample = br1.readLine()) != null) {
                            //sample="Very good..... Good Phone.... Micromax phone awesome mobile I bought this phone for my father who needs to use the phone very regularly. Although the phone as such is very good, the battery is pathetic. It needs to be recharged every 4 hours and dips at a very rapid pace. Since this product is being sold online, the product service centers have refused to service the mobile or attend to the battery. Therefore it is not a very good idea to buy this phone, in spite of very good app quality. I tried to change the location of installation of softwares and its data but it still installing applications in internal storage. I added 8 GB external card, it is getting detected in system but could not use the same. Need assistance to make use of both internal and external storage at the same time proportionately. Why its so...no need 2 but it...on olx it will b available at5k";  
                            String tagged = tagger.tagString(sample);
                            words = Arrays.asList(tagged.split(" "));
                           // System.out.println(tagged+"##");
                            for (int i=0;i<words.size();i++) 
                            {
                                String word=words.get(i);
                               // System.out.println(word);
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
                                                     if(((ind=check(pairs,adj,noun))==-1))
                                                    {
                                                        pairs.add(new String[]{adj,noun,null});
                                                        count.add(1);
                                                   //System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1]+" "+ x+" "+count.get(x));    
                                                    
                                                    x++;
                                                    }
                                                    else
                                                    {
                                                        int c=count.get(ind);
                                                        count.set(ind, (c+1));
                                                      // System.out.println(pairs.get(ind)[0]+" "+pairs.get(ind)[1]+" "+ x+" "+count.get(ind));
                                                    }
                                                    flag=false;
                                                }
                                            }
                                        }
                                        if(flag)
                                        {
                                            if(i!=words.size()-1)
                                            {
                                                if(words.get(i+1).contains("NN")||words.get(i+1).contains("JJ"))
                                                {
                                           
                                        //pairs[x][0]=words[i];
                                        //pairs[x][1]=words[i+1];
                                                if(words.get(i+1).contains("JJ"))
                                                {
                                                    if(i!=words.size()-2)
                                                    {
                                                        if(words.get(i+2).contains("NN"))
                                                        {
                                                            String adj=words.get(i).substring(0,words.get(i).indexOf('_')).toLowerCase();
                                                            String adj2=words.get(i+1).substring(0,words.get(i+1).indexOf('_')).toLowerCase();     
                                                            String noun=words.get(i+2).substring(0,words.get(i+2).indexOf('_')).toLowerCase();     
                                                            //System.out.println(adj+adj2+noun);
                                                            if(((ind=check1(pairs,adj,adj2,noun))==-1))
                                                            {
                                                                count.add(1);   
                                                                pairs.add(new String[]{adj, adj2, noun});
                                                                i=i+2;
                                                                //System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1]+" "+pairs.get(x)[2]+" "+ x+" "+count.get(x));
                                                                x++;
                                                            }
                                                             else
                                                            {
                                                                 int c=count.get(ind);
                                                                 count.set(ind, (c+1));
                                                                // System.out.println(pairs.get(ind)[0]+" "+pairs.get(ind)[1]+" "+pairs.get(ind)[2]+" "+ x+" "+count.get(ind));
                                                                
                                                            }
                                                        }
                                                    }
                                            
                                                }
                                                else
                                                {
                                                    String adj=words.get(i).substring(0,words.get(i).indexOf('_')).toLowerCase();
                                                    String noun=words.get(i+1).substring(0,words.get(i+1).indexOf('_')).toLowerCase();     
                                                    if(((ind=check(pairs,adj,noun))==-1))
                                                    {
                                                        pairs.add(new String[]{adj, noun,null});
                                                        count.add(1);
                                                        i=i+1;
                                                        //System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1]+" "+ x+" "+count.get(x));
                                                        x++;
                           
                                                    }
                                                    else
                                                    {
                                                       int c=count.get(ind);
                                                       count.set(ind, (c+1)); 
                                                       //System.out.println(pairs.get(ind)[0]+" "+pairs.get(ind)[1]+" "+ x+" "+count.get(ind));
                                                    }
                                                }
                                            /*if(pairs.get(x)[2]!=null)
                                            {
                                                System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1]+" "+pairs.get(x)[2]+ x);
                                                x++;
                                            }  
                                            else
                                            {
                                                System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1] + x);
                                                x++;
                                            }*/
                                        //x++;
                                                }
                                            }
                                        }
                                    }      
                                }
                            }
                        }
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br1 != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
    }
                          try {
			String sample;
			br1 = new BufferedReader(new FileReader("C:\\Users\\parasgupta301\\Documents\\NetBeansProjects\\SmartShop\\lib\\projectdata\\AmazonCrawl\\review5000less.txt"));
                        while ((sample = br1.readLine()) != null) {
                            //sample="Very good..... Good Phone.... Micromax phone awesome mobile I bought this phone for my father who needs to use the phone very regularly. Although the phone as such is very good, the battery is pathetic. It needs to be recharged every 4 hours and dips at a very rapid pace. Since this product is being sold online, the product service centers have refused to service the mobile or attend to the battery. Therefore it is not a very good idea to buy this phone, in spite of very good app quality. I tried to change the location of installation of softwares and its data but it still installing applications in internal storage. I added 8 GB external card, it is getting detected in system but could not use the same. Need assistance to make use of both internal and external storage at the same time proportionately. Why its so...no need 2 but it...on olx it will b available at5k";  
                            String tagged = tagger.tagString(sample);
                            words = Arrays.asList(tagged.split(" "));
                           // System.out.println(tagged+"##");
                            for (int i=0;i<words.size();i++) 
                            {
                                String word=words.get(i);
                               // System.out.println(word);
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
                                                     if(((ind=check(pairs,adj,noun))==-1))
                                                    {
                                                        pairs.add(new String[]{adj,noun,null});
                                                        count.add(1);
                                                   //System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1]+" "+ x+" "+count.get(x));    
                                                    
                                                    x++;
                                                    }
                                                    else
                                                    {
                                                        int c=count.get(ind);
                                                        count.set(ind, (c+1));
                                                      // System.out.println(pairs.get(ind)[0]+" "+pairs.get(ind)[1]+" "+ x+" "+count.get(ind));
                                                    }
                                                    flag=false;
                                                }
                                            }
                                        }
                                        if(flag)
                                        {
                                            if(i!=words.size()-1)
                                            {
                                                if(words.get(i+1).contains("NN")||words.get(i+1).contains("JJ"))
                                                {
                                           
                                        //pairs[x][0]=words[i];
                                        //pairs[x][1]=words[i+1];
                                                if(words.get(i+1).contains("JJ"))
                                                {
                                                    if(i!=words.size()-2)
                                                    {
                                                        if(words.get(i+2).contains("NN"))
                                                        {
                                                            String adj=words.get(i).substring(0,words.get(i).indexOf('_')).toLowerCase();
                                                            String adj2=words.get(i+1).substring(0,words.get(i+1).indexOf('_')).toLowerCase();     
                                                            String noun=words.get(i+2).substring(0,words.get(i+2).indexOf('_')).toLowerCase();     
                                                            //System.out.println(adj+adj2+noun);
                                                            if(((ind=check1(pairs,adj,adj2,noun))==-1))
                                                            {
                                                                count.add(1);   
                                                                pairs.add(new String[]{adj, adj2, noun});
                                                                i=i+2;
                                                                //System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1]+" "+pairs.get(x)[2]+" "+ x+" "+count.get(x));
                                                                x++;
                                                            }
                                                             else
                                                            {
                                                                 int c=count.get(ind);
                                                                 count.set(ind, (c+1));
                                                                // System.out.println(pairs.get(ind)[0]+" "+pairs.get(ind)[1]+" "+pairs.get(ind)[2]+" "+ x+" "+count.get(ind));
                                                                
                                                            }
                                                        }
                                                    }
                                            
                                                }
                                                else
                                                {
                                                    String adj=words.get(i).substring(0,words.get(i).indexOf('_')).toLowerCase();
                                                    String noun=words.get(i+1).substring(0,words.get(i+1).indexOf('_')).toLowerCase();     
                                                    if(((ind=check(pairs,adj,noun))==-1))
                                                    {
                                                        pairs.add(new String[]{adj, noun,null});
                                                        count.add(1);
                                                        i=i+1;
                                                        //System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1]+" "+ x+" "+count.get(x));
                                                        x++;
                           
                                                    }
                                                    else
                                                    {
                                                       int c=count.get(ind);
                                                       count.set(ind, (c+1)); 
                                                       //System.out.println(pairs.get(ind)[0]+" "+pairs.get(ind)[1]+" "+ x+" "+count.get(ind));
                                                    }
                                                }
                                            /*if(pairs.get(x)[2]!=null)
                                            {
                                                System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1]+" "+pairs.get(x)[2]+ x);
                                                x++;
                                            }  
                                            else
                                            {
                                                System.out.println(pairs.get(x)[0]+" "+pairs.get(x)[1] + x);
                                                x++;
                                            }*/
                                        //x++;
                                                }
                                            }
                                        }
                                    }      
                                }
                            }
                        }
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br1 != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
    }

                 String[] ignore={"phone","work","tint","deal","memory","users","day","days","month","packing","life","people","problem","apple","others","iphones","force","value","budget","android","apps","cover","smart","storage","months","smartphone","iphone","reviews","phones","job","end","part","device","product","buy","thing","bit","brands","brand","time","choice","time","use","everything","windows","condition","conditions"};
                 List<String> ques=new ArrayList<>();
                 List<String> qual=new ArrayList<>();
                 for(int j=0;j<count.size();j++) 
                 {
                     if(count.get(j)>100)
                     {
                         if(!asList(ignore).contains(pairs.get(j)[1]))
                         {
                             System.out.println(pairs.get(j)[0]+" "+pairs.get(j)[1]+" "+count.get(j));
                             if(!qual.contains(pairs.get(j)[1]))
                             {
                                qual.add(pairs.get(j)[1]);
                                ques.add("How would you prioritize "+Character.toUpperCase(pairs.get(j)[0].charAt(0))+pairs.get(j)[0].substring(1)+" "+Character.toUpperCase(pairs.get(j)[1].charAt(0))+pairs.get(j)[1].substring(1));
                             }  
                         }
                     }
                 }
                 for(int j=0;j<ques.size();j++)
                 {
                     System.out.println(ques.get(j));
                 }
                 try {
                     
                  
                     File file = new File("C:\\Users\\parasgupta301\\Documents\\NetBeansProjects\\SmartShop\\QuestionnaireFiles\\Bel5000.txt");
                     FileWriter fw = new FileWriter(file.getAbsoluteFile());
		     BufferedWriter bw = new BufferedWriter(fw);
                     fw.flush();
                     String content;
                     for(int i=0; i<ques.size();i++)
                     {
                         content=ques.get(i);
                         bw.write(content+System.lineSeparator());
		     }
                     bw.close();
                    } 
                catch (IOException e) {
			e.printStackTrace();
		}
	
    }
}
