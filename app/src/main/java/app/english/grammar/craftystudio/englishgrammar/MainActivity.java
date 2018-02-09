package app.english.grammar.craftystudio.englishgrammar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;

import utils.AppRater;
import utils.ClickListener;
import utils.FireBaseHandler;
import utils.Questions;
import utils.TopicListAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //  FireBaseHandler fireBaseHandler;

    ArrayList<String> mArraylist = new ArrayList<>();
    ListView topicAndTestListview;
    TopicListAdapter adapter;
    ProgressDialog progressDialog;

    FireBaseHandler fireBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                //uploadQuestion();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mArraylist = new ArrayList<>();
        topicAndTestListview = (ListView) findViewById(R.id.topicActivity_topic_listview);

        //download list of Topics
        showDialog("Loading...Please wait");
        downloadTopicList();


        fireBaseHandler = new FireBaseHandler();

        setListViewFooter();

    }

    private void uploadQuestion() {

        Questions questions = new Questions();
        questions.setQuestionName("Who is the Boss ");
        questions.setOptionD("Mona");
        questions.setOptionA("Priyank");
        questions.setOptionB("Dolly");
        questions.setOptionC("No One");
        questions.setCorrectAnswer("Mona");
        questions.setQuestionDateName("Thu, 6 Feb 2018");
        questions.setQuestionTopicName("Noun");

        fireBaseHandler.uploadQuestion(questions, new FireBaseHandler.OnQuestionlistener() {
            @Override
            public void onQuestionDownLoad(Questions questions, boolean isSuccessful) {

            }

            @Override
            public void onQuestionListDownLoad(ArrayList<Questions> questionList, boolean isSuccessful) {

            }

            @Override
            public void onQuestionUpload(boolean isSuccessful) {

                if (isSuccessful) {
                    Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fireBaseHandler.uploadDateName("Thu, 6 Feb 2018", new FireBaseHandler.OnDatalistener() {
            @Override
            public void onDataDownLoad(String itemData, boolean isSuccessful) {

            }

            @Override
            public void onDataUpload(boolean isSuccessful) {

                if (isSuccessful) {
                  //  Toast.makeText(MainActivity.this, "Date Uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void downloadTopicList() {

        mArraylist.clear();

        mArraylist.add("Noun");
        mArraylist.add("Pronoun");
        mArraylist.add("Adjectives");
        mArraylist.add("Adverbs");
        mArraylist.add("Prepositions");
        mArraylist.add("Punctuation");
        mArraylist.add("Relative Clauses");
        mArraylist.add("Speech");
        mArraylist.add("Conjuction");
        mArraylist.add("DETERMINERS");


        adapter = new TopicListAdapter(MainActivity.this, R.layout.custom_textview, mArraylist);

        adapter.setOnItemCLickListener(new ClickListener() {
            @Override
            public void onItemCLickListener(View view, int position) {


                //Toast.makeText(MainActivity.this, " Selected " + textview.getText().toString(), Toast.LENGTH_SHORT).show();

                try {
                    Answers.getInstance().logCustom(new CustomEvent("Topic open").putCustomAttribute("topic", mArraylist.get(position)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayList<String> mTempArraylist = new ArrayList<>();

                Intent intent = new Intent(getApplicationContext(), DisplayListActivity.class);
                Bundle bundle = new Bundle();

                switch (position) {


                    case 0:
                        mTempArraylist.clear();
                        mTempArraylist.add("Collective Nouns");
                        mTempArraylist.add("Types of Noun");
                        mTempArraylist.add("Countable and Uncountable Nouns");
                        mTempArraylist.add("Possessive form of nouns");
                        mTempArraylist.add("Singular and plural nouns");

                        bundle.putStringArrayList("ArrayList", mTempArraylist);
                        bundle.putString("Name", mArraylist.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;

                    case 1:
                        String subTopicName = null;
                        Intent intent1 = new Intent(getApplicationContext(), DisplayTextActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("SubTopic", subTopicName);
                        bundle1.putString("MainTopic", mArraylist.get(position));
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        break;

                    case 2:
                        mTempArraylist.clear();
                        mTempArraylist.add("Introduction");
                        mTempArraylist.add("Types of Adjectives");
                        mTempArraylist.add("ATTRIBUTIVE AND PREDICATIVE ADJECTIVES");
                        mTempArraylist.add("PARTICIPAL ADJECTIVES");
                        mTempArraylist.add("THE COMPARATIVE AND THE SUPERLATIVE");

                        bundle.putStringArrayList("ArrayList", mTempArraylist);
                        bundle.putString("Name", mArraylist.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;

                    case 3:
                        mTempArraylist.clear();
                        mTempArraylist.add("INTRODUCTION");
                        mTempArraylist.add("TYPES OF ADVERBS");
                        mTempArraylist.add("POSITION OF ADVERBS");
                        mTempArraylist.add(" FORMING ADVERBS");

                        bundle.putStringArrayList("ArrayList", mTempArraylist);
                        bundle.putString("Name", mArraylist.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;

                    case 4:
                        mTempArraylist.clear();
                        mTempArraylist.add("INTRODUCTION");
                        mTempArraylist.add("POSITION OF PREPOSITION");
                        mTempArraylist.add("SIMPLE AND COMPOUND PREPOSITION");

                        bundle.putStringArrayList("ArrayList", mTempArraylist);
                        bundle.putString("Name", mArraylist.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;

                    case 5:
                        mTempArraylist.clear();
                        mTempArraylist.add("THE PERIOD, FULL STOP OR POINT");
                        mTempArraylist.add("THE COMMA");
                        mTempArraylist.add("THE APOSTROPHE");
                        mTempArraylist.add("THE QUESTION MARK");
                        mTempArraylist.add("THE COLON & SEMICOLON ");
                        mTempArraylist.add("HYPHENS AND DASHES");

                        bundle.putStringArrayList("ArrayList", mTempArraylist);
                        bundle.putString("Name", mArraylist.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;


                    case 6:
                        String subTopicName1 = null;
                        Intent intent2 = new Intent(getApplicationContext(), DisplayTextActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("SubTopic", subTopicName1);
                        bundle2.putString("MainTopic", mArraylist.get(position));
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case 7:
                        mTempArraylist.clear();
                        mTempArraylist.add("DIRECT SPEECH");
                        mTempArraylist.add("INDIRECT SPEECH");
                        mTempArraylist.add("Changing Direct Speech to Indirect Speech");
                        mTempArraylist.add("REPORTING QUESTIONS");

                        bundle.putStringArrayList("ArrayList", mTempArraylist);
                        bundle.putString("Name", mArraylist.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;

                    case 8:
                        mTempArraylist.clear();
                        mTempArraylist.add("CONNECTING CONJUCTIONS");
                        mTempArraylist.add("CORRELATIVE CONJUCTIONS");
                        mTempArraylist.add("COORDINATING CONJUCTIONS");
                        mTempArraylist.add("SUBORDINATING CONJUCTIONS ");

                        bundle.putStringArrayList("ArrayList", mTempArraylist);
                        bundle.putString("Name", mArraylist.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;

                    case 9:
                        mTempArraylist.clear();
                        mTempArraylist.add("THE DEFINITE ARTICLE ");
                        mTempArraylist.add("INDEFINITE ARTICLE");
                        mTempArraylist.add("ZERO ARTICLE");
                        mTempArraylist.add("PRE-DETERMINERS");
                        mTempArraylist.add(" Articles before Countable and Uncountable Nouns");

                        bundle.putStringArrayList("ArrayList", mTempArraylist);
                        bundle.putString("Name", mArraylist.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }


            }
        });

        topicAndTestListview.setAdapter(adapter);

        hideDialog();

    }

    private void setListViewFooter() {
        View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_textview, null, false);

        TextView topicNameTextview = (TextView) footerView.findViewById(R.id.custom_textview);
        topicNameTextview.setText("Enjoying the app Rate us now");
        topicNameTextview.setTextColor(Color.parseColor("#FFFFFF"));

        CardView cardView = (CardView) footerView.findViewById(R.id.custom_background_cardView);
        cardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRateUs();
            }
        });
        topicAndTestListview.addFooterView(footerView);

    }

    public void downloadDateList() {
        fireBaseHandler.downloadDateList(15, new FireBaseHandler.OnDateListlistener() {
            @Override
            public void onDateDownLoad(String test, boolean isSuccessful) {

            }

            @Override
            public void onDateListDownLoad(ArrayList<String> testList, boolean isSuccessful) {

                if (isSuccessful) {

                    mArraylist.clear();

                    for (String name : testList) {
                        mArraylist.add(name);
                    }

                    adapter = new TopicListAdapter(MainActivity.this, R.layout.custom_textview, mArraylist);

                    adapter.setOnItemCLickListener(new ClickListener() {
                        @Override
                        public void onItemCLickListener(View view, int position) {
                            //TextView textview = (TextView) view;

                            openMainActivity(mArraylist.get(position));
                            //  Toast.makeText(TopicActivity.this, "In Test " + " Selected " + textview.getText().toString() + " Postion is " + position, Toast.LENGTH_SHORT).show();

                            try {
                                Answers.getInstance().logCustom(new CustomEvent("Daily Quiz open").putCustomAttribute("Date Name", mArraylist.get(position)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    });


                    topicAndTestListview.post(new Runnable() {
                        public void run() {
                            topicAndTestListview.setAdapter(adapter);
                        }
                    });


                    hideDialog();

                }
                hideDialog();
            }

            @Override
            public void onDateUpload(boolean isSuccessful) {

            }


        });
    }

    public void openMainActivity(String Text) {

        Intent intent = new Intent(MainActivity.this, DailyQuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Date", Text);
        intent.putExtras(bundle);
        //Toast.makeText(this, Text, Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    public void showDialog(String message) {
        progressDialog = new ProgressDialog(MainActivity.this);
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


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_topic:
                    showDialog("Loading...Please wait");
                    downloadTopicList();
                    return true;

                case R.id.navigation_test_Series:
                    downloadDateList();
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_daily_quiz) {

            downloadDateList();
        } else if (id == R.id.nav_suggest) {

            giveSuggestion();
        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            //sharingIntent.putExtra(Intent.EXTRA_STREAM, newsMetaInfo.getNewsImageLocalPath());

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    " " + "\n\n https://goo.gl/Sxz7iY" + "\n English Grammar app \n Download App Now");
            startActivity(Intent.createChooser(sharingIntent, "Share English Grammar App via"));


        } else if (id == R.id.nav_rate) {
            onRateUs();
        } else if (id == R.id.nav_logical_reasoning) {
            onLogicalReasoningClick();
        } else if (id == R.id.nav_aptitude) {
            onAptitudeMasterClick();
        } else if (id == R.id.nav_personality_development) {
            onPersonalityDevelopment();
        } else if (id == R.id.nav_daily_editorial) {
            onDailyEditorialClick();
        } else if ((id == R.id.nav_pib)) {
            onPIBClick();
        } else if ((id == R.id.nav_basic_Computer)) {
            onBasicComputerClick();
        } else if ((id == R.id.nav_short_key)) {
            onShortKeyClick();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onPIBClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.crafty.studio.current.affairs.pib";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("PIB CLick"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onBasicComputerClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.computer.basic.quiz.craftystudio.computerbasic";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //  Answers.getInstance().logCustom(new CustomEvent("Basic Computer CLick"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onShortKeyClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.key.ashort.craftystudio.shortkeysapp";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            // Answers.getInstance().logCustom(new CustomEvent("ShortKey CLick"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onDailyEditorialClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.craftystudio.vocabulary.dailyeditorial";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("Daily Editorial"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPersonalityDevelopment() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.story.craftystudio.shortstory";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("Personality Development"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onLogicalReasoningClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.reasoning.logical.quiz.craftystudio.logicalreasoning";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("Logical Reasoning Click"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onAptitudeMasterClick() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.aptitude.quiz.craftystudio.aptitudequiz";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            //Answers.getInstance().logCustom(new CustomEvent("Aptitude Master Click"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void giveSuggestion() {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"acraftystudio@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion For " + getResources().getString(R.string.app_name));
        emailIntent.setType("text/plain");

        startActivity(Intent.createChooser(emailIntent, "Send mail From..."));

    }

    private void onRateUs() {
        try {
            String link = "https://play.google.com/store/apps/details?id=app.english.grammar.craftystudio.englishgrammar";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        } catch (Exception e) {

        }
    }


}
