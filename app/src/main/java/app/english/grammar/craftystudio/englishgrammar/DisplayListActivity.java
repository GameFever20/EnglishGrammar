package app.english.grammar.craftystudio.englishgrammar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import utils.ClickListener;
import utils.TopicListAdapter;

public class DisplayListActivity extends AppCompatActivity {


    ListView displayItemListview;
    TopicListAdapter mAdapter;
    ArrayList<String> mArrayList = new ArrayList<>();

    String mainTopicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
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

        mArrayList = getIntent().getExtras().getStringArrayList("ArrayList");
        mainTopicName = getIntent().getExtras().getString("Name");

        toolbar.setTitle(mainTopicName);
        setSupportActionBar(toolbar);

        mAdapter = new TopicListAdapter(getApplicationContext(), R.layout.custom_textview, mArrayList);

        displayItemListview = (ListView) findViewById(R.id.display_item_listview);
        displayItemListview.setAdapter(mAdapter);


        mAdapter.setOnItemCLickListener(new ClickListener() {
            @Override
            public void onItemCLickListener(View view, int position) {

                String subTopicName = "";

                subTopicName = mArrayList.get(position);

                Intent intent = new Intent(getApplicationContext(), DisplayTextActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SubTopic", subTopicName);
                bundle.putString("MainTopic", mainTopicName);
                intent.putExtras(bundle);
                startActivity(intent);

                //  Toast.makeText(DiplayListActivity.this, mArrayList.get(position) + " position " + position + subTopicName, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
