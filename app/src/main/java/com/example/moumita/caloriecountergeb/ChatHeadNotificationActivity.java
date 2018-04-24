package com.example.moumita.caloriecountergeb;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nex3z.notificationbadge.NotificationBadge;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;
import android.renderscript.*;

public class ChatHeadNotificationActivity extends AppCompatActivity {

    private BubblesManager bubblesManager;
    private NotificationBadge mBadge;
    private static int MY_PERMISSION = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_head_notification);

        bubblesManager = new BubblesManager.Builder(this)
                .build();
        bubblesManager.initialize();
        BubbleLayout bubbleView = (BubbleLayout) View.inflate(getApplicationContext(),R.layout.bubble_layout, null);
        bubblesManager.addBubble(bubbleView, 60, 20);
        bubblesManager = new BubblesManager.Builder(this)
                .setTrashLayout(R.layout.bubble_remove)
                .build();

        /*initBubble();

        final Button addBubbleBtn = findViewById(R.id.add_bubble_btn);
        addBubbleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBubble();
            }
        });

        if(Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(ChatHeadNotificationActivity.this)) {

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:"+getPackageName()));
                startActivityForResult(intent,MY_PERMISSION);
            }
        }
        else {
            Intent intent = new Intent(ChatHeadNotificationActivity.this, Service.class);
            startService(intent);
        }
        */
    }

    private void initBubble() {
        bubblesManager = new BubblesManager.Builder(this)
                .setTrashLayout(R.layout.bubble_remove)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
                        addNewBubble();
                    }
                }).build();
        bubblesManager.initialize();
    }

    private void addNewBubble() {
        BubbleLayout bubbleLayout = (BubbleLayout)LayoutInflater
                .from(ChatHeadNotificationActivity.this).inflate(R.layout.bubble_layout, null);
      //  mBadge = (NotificationBadge) bubbleLayout.findViewById(R.id.notification_badge);

      //  mBadge.setNumber(88);

        bubbleLayout.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) {
                Toast.makeText(ChatHeadNotificationActivity.this, "Removed", Toast.LENGTH_LONG).show();
            }
        });

        bubbleLayout.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {
            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                Toast.makeText(ChatHeadNotificationActivity.this, "Clicked", Toast.LENGTH_LONG).show();

            }
        });

        bubbleLayout.setShouldStickToWall(true);
        bubblesManager.addBubble(bubbleLayout,60,20);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        bubblesManager.recycle();
    }
}
