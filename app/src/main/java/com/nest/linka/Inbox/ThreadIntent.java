package com.nest.linka.Inbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nest.linka.R;

import java.util.ArrayList;
import java.util.Random;

public class ThreadIntent extends AppCompatActivity {

    private Thread thread;
    private final int INCOMING=0;
    private final int OUTGOING=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);
        thread = new Thread("2");
        loadHistory();


        ImageButton sendButton =(ImageButton) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
                loadHistory();
            }
        });

    }

    void toaster(int i){
        Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show();

    }

    public void sendMessage(){
        EditText editText = (EditText) findViewById(R.id.messageBodyField);
        String messageText = editText.getText().toString();
        thread.addMessage(messageText,OUTGOING);
        editText.setText("");
    }

    public void loadHistory(){

        ListView listView = (ListView) findViewById(R.id.listMessages);
        listView.setAdapter(new Adapter());

    }


    class Adapter extends BaseAdapter implements AdapterView.OnItemClickListener{
        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return thread.getMessages().size();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Thread.Message getItem(int position) {
            return thread.getMessages().get(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {

            return  this.getItem(position).getMessageID();

        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int direction = getItem(position).getDirection();

            if(direction==OUTGOING){
                convertView = getLayoutInflater().inflate(R.layout.message_right,parent,false);
            }
            else if(direction==INCOMING) {
                convertView = getLayoutInflater().inflate(R.layout.message_left,parent,false);
            }

            TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            TextView sender = (TextView) convertView.findViewById(R.id.txtSender);
            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);

            txtMessage.setText(getItem(position).getMessage());
            //txtDate.setText(getItem(position).getDirection());


            return convertView;
        }



        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            toaster(position);
        }
    }


}

/*
 *
 */
class Thread {
    private ArrayList<Message> messages;
    private String userId;
    private String username;



    Thread(String userId) {
        this.setUserId(userId);
        username="Thomas Wayne"+userId;
        messages = new ArrayList<>();
    }


    public void addMessage(String messageText,int direction){

        Message message = new Message(userId,messageText,direction);
        messages.add(message);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    class Message {
        private String message;
        private int messageID;
        private int direction;


        Message(String userID, String message,int direction) {
            this.message = message;
            this.setDirection(direction);

            //this.messageID = getId();
        }

        public int getMessageID() {
            return messageID;
        }

        public String getMessage() {
            return message;
        }

        public void setMessageID(int messageID) {
            this.messageID = messageID;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }
    }


}
