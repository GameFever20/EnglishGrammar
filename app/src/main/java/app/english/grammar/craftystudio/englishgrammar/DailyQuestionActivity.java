package app.english.grammar.craftystudio.englishgrammar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.util.ArrayList;

import utils.FireBaseHandler;
import utils.Questions;
import utils.ZoomOutPageTransformer;

public class DailyQuestionActivity extends AppCompatActivity {

    FireBaseHandler fireBaseHandler;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    ArrayList<Questions> mQuestionsList = new ArrayList<>();

    ProgressDialog progressDialog;

    Questions questions;

    String dateName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_question);
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

        mPager = (ViewPager) findViewById(R.id.dailyQuestionActivity_viewpager);

        initializeViewPager();

        dateName = getIntent().getExtras().getString("Date");

        toolbar.setTitle(dateName);
        setSupportActionBar(toolbar);

        showDialog("Loading...Please Wait");
        downloadQuestionByDateName(dateName);

    }

    /* Download questions according to DATE name*/
    public void downloadQuestionByDateName(String dateName) {
        fireBaseHandler = new FireBaseHandler();

        // isMoreQuestionAvailable = false;
        fireBaseHandler.downloadQuestionList(30, dateName, new FireBaseHandler.OnQuestionlistener() {
            @Override
            public void onQuestionDownLoad(Questions questions, boolean isSuccessful) {

                hideDialog();
            }

            @Override
            public void onQuestionListDownLoad(ArrayList<Questions> questionList, boolean isSuccessful) {

                if (isSuccessful) {

                    mQuestionsList.clear();

                    for (Questions questions : questionList) {
                        mQuestionsList.add(questions);
                    }
                    initializeViewPager();

                    //Toast.makeText(DailyQuestionActivity.this, questionList.get(0).getQuestionTopicName(), Toast.LENGTH_SHORT).show();

                    //addNativeAds();

                    mPagerAdapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(DailyQuestionActivity.this, "No Data", Toast.LENGTH_SHORT).show();


                }

                hideDialog();

            }

            @Override
            public void onQuestionUpload(boolean isSuccessful) {


            }
        });


    }


    private void initializeViewPager() {

        // Instantiate a ViewPager and a PagerAdapter.

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //change to zoom
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //checkInterstitialAds();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public void showDialog(String message) {
        progressDialog = new ProgressDialog(DailyQuestionActivity.this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        try {
            progressDialog.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //  adsCount++;
            //getting more stories
            if (position == mQuestionsList.size() - 2) {

             /*   if (isMoreQuestionAvailable) {
                    downloadMoreQuestionList();

                }*/
            }

            return AptitudeFragment.newInstance(mQuestionsList.get(position), DailyQuestionActivity.this);
        }

        @Override
        public int getCount() {
            return mQuestionsList.size();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            onShareClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void onShareClick() {

        Questions question = mQuestionsList.get(mPager.getCurrentItem());

        showDialog("Creating Link...Please Wait");
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://goo.gl/Sxz7iY?questionID=" + question.getQuestionUID() + "&questionTopic=" + question.getQuestionTopicName()))
                .setDynamicLinkDomain("st5f4.app.goo.gl")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("app.english.grammar.craftystudio.englishgrammar")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle(question.getQuestionName())
                                .setDescription(question.getQuestionTopicName())
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

        Questions question = mQuestionsList.get(mPager.getCurrentItem());

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

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "\nCan you pick up the Correct answer? \n\n " + question.getQuestionName() + "\n\n" + "1. " + question.getOptionA()
                        + "\n2. " + question.getOptionB() + "\n3. " + question.getOptionC() + "\n4. " + question.getOptionD() + "\n\n Learn English Grammar Now\n " + shortUrl);
        startActivity(Intent.createChooser(sharingIntent, "Share English Grammar via"));
        hideDialog();

        try {
            //   Answers.getInstance().logCustom(new CustomEvent("Share question").putCustomAttribute("question", questions.getQuestionName()).putCustomAttribute("question topic", questions.getQuestionTopicName()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
