package utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.english.grammar.craftystudio.englishgrammar.R;

/**
 * Created by Aisha on 1/31/2018.
 */

public class TopicListAdapter extends ArrayAdapter<String> {

    ArrayList<String> mTopicList;
    Context mContext;

    int mResourceID;

    ClickListener clickListener;

    public TopicListAdapter(Context context, int resource, ArrayList<String> topicList) {
        super(context, resource);
        this.mTopicList = topicList;
        this.mContext = context;
        this.mResourceID = resource;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_textview, null, false);
        }
        //getting the view
        //  View view = layoutInflater.inflate(mResourceID, null, false);


        TextView topicNameTextview = (TextView) convertView.findViewById(R.id.custom_textview);

        topicNameTextview.setText((String)mTopicList.get(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemCLickListener(view,position);
                //  Toast.makeText(mContext, "Item clicked " + mTopicList.get(position), Toast.LENGTH_SHORT).show();
            }
        });


        // Toast.makeText(mContext, "Topic set", Toast.LENGTH_SHORT).show();
        return convertView;
    }

    @Override
    public int getCount() {
        return mTopicList.size();
    }

    public void setOnItemCLickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
