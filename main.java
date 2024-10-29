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
        int input = 99;

        while(true){
            System.out.println("\nChoose an operation:");
            System.out.println("1-List of followers  ");
            System.out.println("2-List of following ");
            System.out.println("3-Still haven't accepted my follow request ");
            System.out.println("4-Followers who I don't follow back ");
            System.out.println("5-Followers who don't follow me back ");
            System.out.println("0-QUIT");
            input = scan.nextInt();
            scan.nextLine();

            if(input == 1){
                getFollowers();
            }
            else if(input == 2){
                getFollowing();
            }
            else if(input == 3){
                getPending();
            }
            else if(input == 4){
                System.out.println("Still working on it! :/");
            }
            else if(input == 5){
                System.out.println("Still working on it! :/");
            }
            else if(input == 0){
                System.out.println("Quitting. BYE!");
                break;
            }
            else{continue;}



        }




    }

    public static void getFollowers(){

        //Creating a JSON parser object
        JSONParser parser = new JSONParser();

        try {

            //Reading the file
            File followersFile = new File("followers_1.json");

            //Parsing the JSON file and creating the array
            JSONArray followers = (JSONArray) parser.parse(new FileReader(followersFile));

            //Going through all the people that we follow
            System.out.println("\nYour followers: ");
            for(Object followerObject : followers){
                JSONObject follower = (JSONObject) followerObject;

                //Access the "string_list_data" array within each request object
                JSONArray stringListData = (JSONArray) follower.get("string_list_data");

                //Retrieve the "value" and "href" fields from each element in "string_list_data"
                for (Object dataObject : stringListData) {
                    JSONObject data = (JSONObject) dataObject;
                    String value = (String) data.get("value");
                    String link = (String) data.get("href");
                    System.out.println(value + "\t\t" + link);
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

    public static void getFollowing(){

        //Creating a JSON parser object
        JSONParser parser = new JSONParser();

        try {

            //Reading the file
            File followingFile = new File("following.json");

            //Parsing the JSON file
            JSONObject jsonFollowing =  (JSONObject) parser.parse(new FileReader(followingFile));

            //Access the array "relationships_following"
            JSONArray following = (JSONArray) jsonFollowing.get("relationships_following");

            //Going through all the people that we follow
            System.out.println("\nPeople that you follow:");
            for(Object followingObject : following){
                JSONObject followings = (JSONObject) followingObject;

                //Access the "string_list_data" array within each request object
                JSONArray stringListData = (JSONArray) followings.get("string_list_data");

                //Retrieve the "value" and "href" fields from each element in "string_list_data"
                for (Object dataObject : stringListData) {
                    JSONObject data = (JSONObject) dataObject;
                    String value = (String) data.get("value");
                    String link = (String) data.get("href");
                    System.out.println(value + "\t\t" + link);
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

    public static void getPending(){

        //Creating a JSON parser object
        JSONParser parser = new JSONParser();

        try {

            //Reading the file
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

                //Retrieve the "value" and "href" fields from each element in "string_list_data"
                for (Object dataObject : stringListData) {
                    JSONObject data = (JSONObject) dataObject;
                    String value = (String) data.get("value");
                    String link = (String) data.get("href");
                    System.out.println(value + "\t\t" + link);
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
