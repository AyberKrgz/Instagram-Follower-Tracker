import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class main {
    public static void main(String[] args) {

        //Creating a JSON parser object
        JSONParser parser = new JSONParser();

        try {

            //Reading files
            File followers_json = new File("followers_1.json");
            File following_json = new File("following.json");
            File pending = new File("pending_follow_requests.json");


            //Parsing the JSON file
            Object obj = parser.parse(new FileReader(pending));
            JSONObject j =  (JSONObject) obj;


            //Access the array "relationships_follow_requests_sent"
            JSONArray followRequests = (JSONArray) j.get("relationships_follow_requests_sent");

            //Going through all requested people
            for(Object requestObject : followRequests){
                JSONObject request = (JSONObject) requestObject;

                //Access the "string_list_data" array within each request object
                JSONArray stringListData = (JSONArray) request.get("string_list_data");

                //Retrieve the "value" field from each element in "string_list_data"
                for (Object dataObject : stringListData) {
                    JSONObject data = (JSONObject) dataObject;
                    String value = (String) data.get("value");
                    System.out.println("Value: " + value);
                }
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
