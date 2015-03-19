package com.example.mariasyomina.appvk;

import android.app.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserInfoActivity extends ActionBarActivity implements View.OnClickListener {

    ArrayList<MyFriend> friendsList = new ArrayList<>();
    Adapter adapter;

    ImageButton bsearch;
    ImageView bmenu;
    ImageView avatar;
    TextView tview;
    TextView tname;
    TextView tcity;
    ListView lvMain;
    DrawerLayout drawerLayout;

    private Activity context;


    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = (ListView) findViewById(R.id.lvMain);

        context = this;

        bsearch = (ImageButton) findViewById(R.id.bsearch);
        avatar = (ImageView) findViewById(R.id.avatar);
        tview = (TextView) findViewById(R.id.tview);
        tname = (TextView) findViewById(R.id.tname);
        tcity = (TextView) findViewById(R.id.tcity);
        bmenu = (ImageButton) findViewById(R.id.bmenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final View.OnClickListener oclbsearch = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Нажата кнопка поиска", Toast.LENGTH_LONG).show();
            }
        };
        View.OnClickListener oclbmenu = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        };

        bsearch.setOnClickListener(oclbsearch);
        bmenu.setOnClickListener(oclbmenu);


        VKRequest request = new VKRequest("users.get", VKParameters.from(VKApiConst.FIELDS, "city,photo_100,"));
        VKRequest request1 = new VKRequest("friends.get", VKParameters.from(VKApiConst.FIELDS, "bdate,city, photo_100"));


        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                Log.v(TAG, response.responseString);
                JSONObject dataJsonObj = null;
                String firstName = "", lastName = "", title = "", photo = "";
                try {
                    dataJsonObj = new JSONObject(response.responseString);
                    JSONArray responce = dataJsonObj.getJSONArray("response");
                    JSONObject info = responce.getJSONObject(0);
                    firstName = info.getString("first_name");
                    Log.v(TAG, firstName);
                    lastName = info.getString("last_name");
                    Log.v(TAG, lastName);
                    photo = info.getString("photo_100");
                    Log.v(TAG, photo);


                    tname.setText(firstName + " " + lastName);


                    JSONObject city1 = info.getJSONObject("city");
                    title = city1.getString("title");
                    Log.v(TAG, String.valueOf(title));

                    tcity.setText(title);

                    Picasso.with(context).load(photo).into(avatar);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });


        request1.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.responseString);

                    JSONObject responce = jsonObject.getJSONObject("response");
                    Log.v(TAG, responce.toString());
                    JSONArray items = responce.getJSONArray("items");
                    Log.v(TAG, items.toString());

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject friend = items.getJSONObject(i);

                        String firstNameFriend = friend.getString("first_name");
                        String lastNameFriend = friend.getString("last_name");
                        String photoFriend = friend.getString("photo_100");

                        Log.v(TAG, firstNameFriend);
                        Log.v(TAG, lastNameFriend);
                        Log.v(TAG, photoFriend);

                        MyFriend duck = new MyFriend();
                        duck.setFirstName(firstNameFriend);
                        duck.setLastName(lastNameFriend);
                        duck.setPhoto(photoFriend);

                        friendsList.add(duck);
                    }

                    Adapter adapter = new Adapter(context, friendsList);
                    lvMain.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void attemptFailed(VKRequest request2, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request2, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });

    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "по id определяем кнопку");
        switch (v.getId()) {
            case R.id.bsearch:
                Log.d(TAG, "кнопка поиска");
                Toast.makeText(this, "Нажата кнопка поиска", Toast.LENGTH_LONG).show();
                break;

        }

    }

}
