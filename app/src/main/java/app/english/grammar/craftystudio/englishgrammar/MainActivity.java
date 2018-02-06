package app.english.grammar.craftystudio.englishgrammar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;

import utils.ClickListener;
import utils.TopicListAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //  FireBaseHandler fireBaseHandler;

    ArrayList<String> mArraylist = new ArrayList<>();
    ListView topicAndTestListview;
    TopicListAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    }

    private void downloadTopicList() {
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
                TextView textview = (TextView) view;


                Toast.makeText(MainActivity.this, " Selected " + textview.getText().toString(), Toast.LENGTH_SHORT).show();

                try {
                    // Answers.getInstance().logCustom(new CustomEvent("Topic open").putCustomAttribute("topic", textview.getText().toString()));
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
                    //downloadTestList();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
