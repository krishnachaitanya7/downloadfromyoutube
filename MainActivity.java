package com.example.krishna.downloadfromyoutube;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 public class MainActivity extends AppCompatActivity {
    public static List<String> extractUrls(String text){
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));        }

        return containedUrls;
    }
    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        return vId;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        // Get the action of the intent
        String action = intent.getAction();
        // Get the type of intent (Text or Image)
        String type = intent.getType();
        // When Intent's action is 'ACTION+SEND' and Tyoe is not null
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            // When tyoe is 'text/plain'
            if ("text/plain".equals(type)) {
                handleSendText(this,intent); // Handle text being sent
            }
        }



    }
    private void handleSendText(Context context, Intent intent) {
        List<String> extractedUrls = extractUrls(String.valueOf(intent.getClipData()));
        for (String url : extractedUrls)
        {
            String youtube_id=extractYTId(url);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://convert2mp3.net/en/index.php?p=tags&id=youtube_"+youtube_id)));
            finish();
        }


        }

 }




