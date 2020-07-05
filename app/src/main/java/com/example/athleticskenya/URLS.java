package com.example.athleticskenya;

public class URLS {

    private static final String URL_ROOT = "http://192.168.43.192/athleticskenya/muriithi/Operations.php?action=";//136.243.75.214:2082 76-kevin 212-erick 114-me
    static final String URL_REGISTER = URL_ROOT + "signUp";//for sign up used in sign up activity
    static final String URL_LOGIN = URL_ROOT + "login";//for log in used in log in activity
    static final String URL_UPDATE = URL_ROOT + "update";//for updating user profile used in EditProfile activity
    static final String URL_CONTACT = URL_ROOT + "contact";//used by a coach to update athlete contact id indicating they have accepted their request used in CoachAdapter
    public static final String URL_EVENTS = URL_ROOT + "events";//for fetching the events used in all main activities
    static final String URL_ALL_COACHES = URL_ROOT + "all_coaches";//for fetching all coaches used in CoachListActivity
    static final String URL_COACH = URL_ROOT + "coach";//for fetching available coaches used in CoachListActivity
    static final String URL_ACCEPT = URL_ROOT + "accept";//for a coach to accept athlete request used in RequestAdapter
    static final String URL_REJECT = URL_ROOT + "reject";//for a coach to reject athlete request used in RequestAdapter
    static final String URL_DIET = URL_ROOT + "diet";//for a coach to upload their diets for coaching athletes used in RecommendDiet activity
    static final String URL_FEEDBACK = URL_ROOT + "feedback";// for everyone to give feedback to the admin used in every main activity
    static final String URL_CHAT = URL_ROOT + "chat";//for inserting messages to the database used in ChatRoom Activity
    static final String URL_TRAINING_GROUND = URL_ROOT + "training_grounds";//for fetching training grounds used in TrainingGroundsActivity
    public static final String URL_FEMALE_AWARDS = URL_ROOT + "female_awards";//for fetching female awards used in FemaleFragment
    public static final String URL_MALE_AWARDS = URL_ROOT + "male_awards";//for fetching female awards used in MaleFragment

    /**
     *fetching accepted athletes by coaches using contact id used in AcceptedAthletes Activity
     */
    static final String URL_ACCEPTED = "http://192.168.43.192/athleticskenya/muriithi/Coach_athlete.php?contact=";
    /**
     *fetching all chats by receiver id used in ChatRoom Activity
     */
    static final String URL_CHATS = "http://192.168.43.192/athleticskenya/muriithi/Chats.php?id=";
    /**
     *fetching all athlete requests to coaches by coach id used in Coach_Athlete_Request Activity
     */
    static final String URL_REQUEST = "http://192.168.43.192/athleticskenya/muriithi/Request.php?id=";
    /**
     *fetching athlete diet using their ids and also fetching specific diets of athletes in coach side using athlete id used in DietActivity and RecommendDiet
     * activity
     */
    static final String URL_BREAKFAST = "http://192.168.43.192/athleticskenya/muriithi/Breakfast.php?id=";
    static final String URL_LUNCH = "http://192.168.43.192/athleticskenya/muriithi/Lunch.php?id=";
    static final String URL_DINNER = "http://192.168.43.192/athleticskenya/muriithi/Dinner.php?id=";
    /**
     *fetching a coach to athlete using contact id used in MyCoach Activity
     */
    static final String URL_MY_COACH = "http://192.168.43.192/athleticskenya/muriithi/Coach.php?contact=";

}
