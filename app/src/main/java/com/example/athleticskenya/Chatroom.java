package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.athleticskenya.Utils.Cipher;
import com.example.athleticskenya.adapters.MessageListAdapter;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.Message;
import com.example.athleticskenya.getterClasses.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.athleticskenya.AcceptedProfile.EXTRA_CONTACT_EXTRA;
import static com.example.athleticskenya.AcceptedProfile.EXTRA_ID_EXTRA;

public class Chatroom extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText edit_message;
    Button send;
    String receiver_id;
    MessageListAdapter messageListAdapter;
    ArrayList<Message> messageList;

    int id = PrefManager.getInstance(this).userId();

    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        User user = db.getUser(id);

        edit_message = findViewById(R.id.message);
        messageList = new ArrayList<>();
        int id = user.getId();
        messageListAdapter = new MessageListAdapter(Chatroom.this, messageList, id);
        recyclerView = findViewById(R.id.chatRoom);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(messageListAdapter);



        fetchMessages();

        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.chatRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    messageList.clear();
                    fetchMessages();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void fetchMessages() {
        User user = db.getUser(id);
        Intent intent = getIntent();
        final String receiver_id = intent.getStringExtra(EXTRA_ID_EXTRA);
        String id = String.valueOf(user.getId());
        String contact = user.getContact();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_CHATS + receiver_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray thread = res.getJSONArray("messages");
                            for (int i = 0; i < thread.length(); i++) {
                                JSONObject obj = thread.getJSONObject(i);
                                int userId = obj.getInt("user_id");
                                String receiver_id = obj.getString("receiver_id");
                                String message = obj.getString("message");
                                String name = obj.getString("receiver_id");
                                String contact = obj.getString("contact");
                                String sentAt = obj.getString("sentAt");
                                Message messageObject = new Message(userId, receiver_id, name, message, contact, sentAt);
                                messageList.add(messageObject);
                            }

                           /* messageListAdapter = new MessageListAdapter(Chatroom.this, messageList, user.getId());
                            recyclerView.setAdapter(messageListAdapter);*/
                            messageListAdapter.notifyDataSetChanged();
                            scrollToBottom();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void sendMessage(){
        User user = db.getUser(id);
        Intent intent = getIntent();
        final String receiver_id = intent.getStringExtra(EXTRA_ID_EXTRA);
        final String contact = intent.getStringExtra(EXTRA_CONTACT_EXTRA);
        final String user_id = String.valueOf(user.getId());
        final String message = edit_message.getText().toString().trim();
        final String sentAt = getTimeStamp();
        final String user_name = user.getFirstname();
        int id = user.getId();

        if (TextUtils.isEmpty(message)) {
            edit_message.setError("Please enter your message!");
            edit_message.requestFocus();
            return;
        }

        //Toast.makeText(getApplicationContext(), "gotten id "+ receiver_id, Toast.LENGTH_SHORT).show();
        Message m = new Message(id, receiver_id, user_name, message, contact, sentAt);
        messageList.add(m);
        messageListAdapter.notifyDataSetChanged();

        scrollToBottom();

        edit_message.setText("");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_CHAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("message", message);
                params.put("receiver_id", receiver_id);
                params.put("user_id", user_id);
                params.put("contact", contact);
                params.put("sentAt", sentAt);
                return params;
            }
        };

        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


    private void scrollToBottom(){
        messageListAdapter.notifyDataSetChanged();
        if (messageListAdapter.getItemCount() > 1){
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, messageListAdapter.getItemCount() - 1);
        }
    }

    public static String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

    public void playBeep() {

        try {
            Uri notification = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                    notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
