import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String input = "";

        while(true){
            System.out.println("\nChoose an operation:");
            System.out.println("1-Followers");
            System.out.println("2-Following");
            System.out.println("3-Pending follow requests");
            System.out.println("q for QUIT");
            input = scan.next();

            if(input.equalsIgnoreCase("1")){
                System.out.println("Selamun ALeyküm");
            }
            else if(input.equalsIgnoreCase("2")){
                System.out.println("Selamun ALeyküm2");
            }
            else if(input.equalsIgnoreCase("3")){
                getPending();
            }
            else if(input.equalsIgnoreCase("q")){
                System.out.println("Quitting. BYE!");
                break;
            }



        }




    }

    public static void getPending(){

        //Creating a JSON parser object
        JSONParser parser = new JSONParser();

        try {

            //Reading files
            File pending = new File("pending_follow_requests.json");

            //Parsing the JSON file
            JSONObject jsonPending =  (JSONObject) parser.parse(new FileReader(pending));

            //Access the array "relationships_follow_requests_sent"
            JSONArray followRequests = (JSONArray) jsonPending.get("relationships_follow_requests_sent");

            //Going through all requested people
            System.out.println("\nPeople that you sent request:");
            for(Object requestObject : followRequests){
                JSONObject request = (JSONObject) requestObject;

                //Access the "string_list_data" array within each request object
                JSONArray stringListData = (JSONArray) request.get("string_list_data");

                //Retrieve the "value" field from each element in "string_list_data"
                for (Object dataObject : stringListData) {
                    JSONObject data = (JSONObject) dataObject;
                    String value = (String) data.get("value");
                    String link = (String) data.get("href");
                    System.out.println("Name: " + value + "\nLink: " + link);
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
