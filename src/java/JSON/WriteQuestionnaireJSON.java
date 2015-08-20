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
public class WriteQuestionnaireJSON {
    
    JSONObject questionnaire;
    JSONArray questions;  
    public WriteQuestionnaireJSON()
    {
        questionnaire = new JSONObject();  
        questions= new JSONArray();
    }
    
     public void buildJSON(String quesString,String quesid)
    {
        JSONObject ques = new JSONObject();
        ques.put("name",quesid);
        ques.put("question", quesString);  
        JSONArray options = new JSONArray();
        options.add("High");
        options.add("Medium");
        options.add("Low");
        ques.put("options",options);
        
        questions.add(ques);  
    }

    public JSONObject write() {
        questionnaire.put("questionnaire", questions);
        return questionnaire;
    }
    
}
