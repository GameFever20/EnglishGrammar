package app.english.grammar.craftystudio.englishgrammar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.util.ArrayList;

import utils.AppRater;
import utils.FireBaseHandler;

public class DisplayTextActivity extends AppCompatActivity {

    String mSubTopic, mMainTopic;
    WebView webView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mMainTopic = getIntent().getExtras().getString("MainTopic");
        mSubTopic = getIntent().getExtras().getString("SubTopic");


        toolbar.setTitle(mSubTopic);
        setSupportActionBar(toolbar);

        showDialog();
        //download data from database

        if (mSubTopic == null) {
            toolbar.setTitle(mMainTopic);
            setSupportActionBar(toolbar);

            downloadFullData(mMainTopic);
        } else {
            downloadFullData(mMainTopic, mSubTopic);

        }

        //App rater when 3 topics opened

        try {
            AppRater.app_launched(DisplayTextActivity.this);

            Answers.getInstance().logCustom(new CustomEvent("Topic Reading").putCustomAttribute("topic", mMainTopic).putCustomAttribute("Sub topic",mSubTopic));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void downloadFullData(String mainTopic, String subTopic) {
        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadItemData(mainTopic, subTopic, new FireBaseHandler.OnDatalistener() {
            @Override
            public void onDataDownLoad(String itemData, boolean isSuccessful) {


                if (isSuccessful) {
                    if (itemData != null) {
                        webView = (WebView) findViewById(R.id.displayText_Main_Webview);
                        webView.loadDataWithBaseURL("", itemData, "text/html", "UTF-8", "");

                    } else {
                        webView.loadDataWithBaseURL("", "Data Cannot be loaded", "text/html", "UTF-8", "");

                    }
                }
                hideDialog();
            }


            @Override
            public void onDataUpload(boolean isSuccessful) {

            }
        });
    }

    void downloadFullData(String mainTopic) {
        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadItemData(mainTopic, new FireBaseHandler.OnDatalistener() {
            @Override
            public void onDataDownLoad(String itemData, boolean isSuccessful) {


                if (isSuccessful) {
                    if (itemData != null) {
                        webView = (WebView) findViewById(R.id.displayText_Main_Webview);
                        webView.loadDataWithBaseURL("", itemData, "text/html", "UTF-8", "");

                    } else {
                        webView.loadDataWithBaseURL("", "Data Cannot be loaded", "text/html", "UTF-8", "");

                    }
                }
                hideDialog();
            }


            @Override
            public void onDataUpload(boolean isSuccessful) {

            }
        });
    }


    private void onShareClick() {
        showDialog();
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://play.google.com/store/apps/details?id=app.computer.basic.quiz.craftystudio.computerbasic"))
                .setDynamicLinkDomain("f5kn7.app.goo.gl")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("app.computer.basic.quiz.craftystudio.computerbasic")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Learn Computer Quickly")
                                .setDescription("Download the App Now")
                                .build())
                .setGoogleAnalyticsParameters(
                        new DynamicLink.GoogleAnalyticsParameters.Builder()
                                .setSource("share")
                                .setMedium("social")
                                .setCampaign("example-promo")
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            Uri shortLink = task.getResult().getShortLink();

                            openShareDialog(shortLink);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }


    private void openShareDialog(Uri shortUrl) {

        try {
            /*
            Answers.getInstance().logCustom(new CustomEvent("Share link created").putCustomAttribute("Content Id", questions.getQuestionUID())
                    .putCustomAttribute("Shares", questions.getQuestionTopicName()));
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        //sharingIntent.putExtra(Intent.EXTRA_STREAM, newsMetaInfo.getNewsImageLocalPath());

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Learn Computer on the Go! " + shortUrl);
        startActivity(Intent.createChooser(sharingIntent, "Share Basic Computer App via"));
        hideDialog();

        try {
            //  Answers.getInstance().logCustom(new CustomEvent("Share question").putCustomAttribute("question",questions.getQuestionName()).putCustomAttribute("question topic",questions.getQuestionTopicName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    void showDialog() {
        progressDialog = new ProgressDialog(DisplayTextActivity.this);
        progressDialog.setMessage("Loading Data..Please Wait..");
        progressDialog.show();
    }

    void hideDialog() {
        progressDialog.hide();
    }

}
