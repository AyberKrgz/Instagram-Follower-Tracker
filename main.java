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
        int input = 99;

        Set<String> followersSet = new HashSet<>();
        Set<String> followingSet = new HashSet<>();

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
            JSONArray followersArray = (JSONArray) parser.parse(new FileReader(followersFile));
            //Access the array "relationships_following"
            JSONArray followingArray = (JSONArray) jsonFollowing.get("relationships_following");
            //Access the array "relationships_follow_requests_sent"
            JSONArray followRequestsArray = (JSONArray) jsonPending.get("relationships_follow_requests_sent");


            // Followers setini doldur

            for (Object followerObj : followersArray) {
                JSONObject follower = (JSONObject) followerObj;
                JSONArray stringListData = (JSONArray) follower.get("string_list_data");
                for (Object dataObj : stringListData) {
                    JSONObject data = (JSONObject) dataObj;
                    String value = (String) data.get("value");
                    followersSet.add(value);
                }
            }

            // Following setini doldur

            for (Object followingObj : followingArray) {
                JSONObject following = (JSONObject) followingObj;
                JSONArray stringListData = (JSONArray) following.get("string_list_data");
                for (Object dataObj : stringListData) {
                    JSONObject data = (JSONObject) dataObj;
                    String value = (String) data.get("value");
                    followingSet.add(value);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        while(true){
            System.out.println("\nChoose an operation:");
            System.out.println("1-List of followers ");
            System.out.println("2-List of following ");
            System.out.println("3-Still haven't accepted my follow request ");
            System.out.println("4-Followers who I don't follow back ");
            System.out.println("5-Followings who don't follow me back ");
            System.out.println("0-QUIT");
            input = scan.nextInt();
            scan.nextLine();

            if(input == 1){                             //"1-List of followers "
                getFollowers();
            }
            else if(input == 2){                        //"2-List of following "
                getFollowing();
            }
            else if(input == 3){                        //"3-Still haven't accepted my follow request "
                getPending();
            }
            else if(input == 4){                        //"4-Followers who I don't follow back "
                opt4(followersSet, followingSet);
            }
            else if(input == 5){                        //"5-Followings who don't follow me back "
                opt5(followingSet, followersSet);
            }
            else if(input == 0){                        //"0-QUIT"
                System.out.println("Quitting. BYE!");
                break;
            }
            else{continue;}

        }

    }

    public static void opt4(Set<String> followersSet, Set<String> followingSet){
        //Copying sets
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.addAll(followersSet);
        set2.addAll(followingSet);

        //Extracting following from followers
        set1.removeAll(set2);

        //Printing the result
        System.out.println("Followers who you don't follow back: ");
        for (String user : set1) {
            System.out.println(user);
        }

    }

    public static void opt5(Set<String> followingSet, Set<String> followersSet){

        //Copying sets
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.addAll(followingSet);
        set2.addAll(followersSet);

        //Extracting followers from following
        set1.removeAll(set2);

        //Printing the result
        System.out.println("Followings who don't follow you back:");
        for (String user : set1) {
            System.out.println(user);
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
