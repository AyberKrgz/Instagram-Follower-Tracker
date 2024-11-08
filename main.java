import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.util.Scanner;
import java.util.Set;

public class main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int input = 99;                                 //To control the operations

        //Initializing sets and arrays
        Set<String> followersSet = new HashSet<>();
        Set<String> followingSet = new HashSet<>();
        Set<String> opt4 = new HashSet<>();
        Set<String> opt5 = new HashSet<>();
        JSONArray followersArray = null;
        JSONArray followingArray = null;
        JSONArray followRequestsArray = null;

        //Creating a JSON parser object
        JSONParser parser = new JSONParser();

        try {

            //Reading files
            File followersFile = new File("followers_1.json");
            File followingFile = new File("following.json");
            File pending = new File("pending_follow_requests.json");

            //Parsing the JSON files
            JSONObject jsonFollowing =  (JSONObject) parser.parse(new FileReader(followingFile));
            JSONObject jsonPending =  (JSONObject) parser.parse(new FileReader(pending));

            //Parsing the JSON file and creating the array
            //Accessing the arrays "relationships_following" , "relationships_follow_requests_sent"
            followersArray = (JSONArray) parser.parse(new FileReader(followersFile));
            followingArray = (JSONArray) jsonFollowing.get("relationships_following");
            followRequestsArray = (JSONArray) jsonPending.get("relationships_follow_requests_sent");


            //Filling the followers set
            for (Object followerObj : followersArray) {
                JSONObject follower = (JSONObject) followerObj;
                JSONArray stringListData = (JSONArray) follower.get("string_list_data");
                for (Object dataObj : stringListData) {
                    JSONObject data = (JSONObject) dataObj;
                    String value = (String) data.get("value");
                    followersSet.add(value);
                }
            }

            //Filling the following set
            for (Object followingObj : followingArray) {
                JSONObject following = (JSONObject) followingObj;
                JSONArray stringListData = (JSONArray) following.get("string_list_data");
                for (Object dataObj : stringListData) {
                    JSONObject data = (JSONObject) dataObj;
                    String value = (String) data.get("value");
                    followingSet.add(value);
                }
            }

            //Copying and extracting operations for option 4 and 5.
            opt4.addAll(followingSet);
            opt4.removeAll(followersSet);
            opt5.addAll(followersSet);
            opt5.removeAll(followingSet);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        while(true){

            //MENU
            System.out.println("\nChoose an operation:");
            System.out.println("1-List of followers (" + followersSet.stream().count() + ") ");
            System.out.println("2-List of following (" + followingSet.stream().count() + ") ");
            System.out.println("3-Still haven't accepted my follow request (" + followRequestsArray.stream().count() + ") ");
            System.out.println("4-Followings who don't follow me back (" + opt4.stream().count() + ")");
            System.out.println("5-Followers who I don't follow back (" + opt5.stream().count() + ")");
            System.out.println("0-QUIT");
            input = scan.nextInt();
            scan.nextLine();

            if(input == 1){                             //"1-List of followers "
                if (followersArray != null) {
                    getFollowers(followersArray);
                }
            }
            else if(input == 2){                        //"2-List of following "
                if (followingArray != null) {
                    getFollowing(followingArray);
                }
            }
            else if(input == 3){                        //"3-Still haven't accepted my follow request "
                if (followRequestsArray != null) {
                    getPending(followRequestsArray);
                }
            }
            else if(input == 4){                        //"4-Followings who don't follow me back "
                getOpt4(opt4);
            }
            else if(input == 5){                        //"5-Followers who I don't follow back "
                getOpt5(opt5);
            }
            else if(input == 0){                        //"0-QUIT"
                System.out.println("Quitting. BYE!");
                break;
            }
            else{continue;}

        }

    }

    public static void getFollowers(JSONArray followers){

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

    }

    public static void getFollowing(JSONArray following){

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

    }

    public static void getPending(JSONArray followRequests){

        //Going through all requested people
        System.out.println("\nPeople that haven't accepted your request:");
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

    }

    public static void getOpt4(Set<String> opt4){

        //Printing the result
        System.out.println("Followings who don't follow you back:");
        for (String user : opt4) {
            System.out.println(user);
        }

    }

    public static void getOpt5(Set<String> opt5){

        //Printing the result
        System.out.println("Followers who you don't follow back: ");
        for (String user : opt5) {
            System.out.println(user);
        }

    }

}
