package com.bobgranata.linkservice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.Models.DocumentModel;
import com.bobgranata.linkservice.Models.InfComModel;
import com.bobgranata.linkservice.R;

import java.util.List;

/**
 * Created by BobGranata on 03.08.2016.
 */
public class DocCirculListAdapter  extends BaseExpandableListAdapter {
    private static final int MAX_LENGTH_TITLE = 65;
    private final Context mContext;
    private final List<DocCirculModel> mGroups;

    public DocCirculListAdapter(Context context, List<DocCirculModel> values) {
        this.mContext = context;
        this.mGroups = values;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).countDocuments();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).getDocument(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        LinearLayout llDocCirculItem = (LinearLayout) convertView.findViewById(R.id.llDocCirculItem);

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        String sDcTitle = mGroups.get(groupPosition).getName();
        String sShortDcTitle = sDcTitle;
        if (sDcTitle.length() > MAX_LENGTH_TITLE) {
            sShortDcTitle = sShortDcTitle.substring(0, MAX_LENGTH_TITLE);
            sShortDcTitle += "...";
            textGroup.setText(sShortDcTitle);
        } else {
            textGroup.setText(sDcTitle);
        }

        ImageView ivStatusColor = (ImageView) convertView.findViewById(R.id.ivStatusColor);

        String sStatus = mGroups.get(groupPosition).getStatus();

        if (sStatus.toLowerCase().contains("correct")) {
            ivStatusColor.setBackgroundResource(R.drawable.green_status);
        } else if (sStatus.toLowerCase().contains("error")) {
            ivStatusColor.setBackgroundResource(R.drawable.red_status);
        } else {
            ivStatusColor.setBackgroundResource(R.drawable.grey_status);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
            if (sDcTitle.length() > MAX_LENGTH_TITLE) {
                textGroup.setText(sDcTitle);
                ViewGroup.LayoutParams params = llDocCirculItem.getLayoutParams();
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT; // или в пикселях
                llDocCirculItem.setLayoutParams(params);

                ViewGroup.LayoutParams paramsText = textGroup.getLayoutParams();
                paramsText.height = LinearLayout.LayoutParams.WRAP_CONTENT; // или в пикселях
                textGroup.setLayoutParams(paramsText);
            }
        }
        else{
            textGroup.setText(sShortDcTitle);
            //Изменяем что-нибудь, если текущая Group скрыта
//            llDocCirculItem.getLayoutParams().height = 60;
//            ivStatusColor.getLayoutParams().width = 10;
//            textGroup.setHeight(50);
        }


        TextView tvValueCreateDate = (TextView) convertView.findViewById(R.id.tvValueCreateDate);
        tvValueCreateDate.setText(mGroups.get(groupPosition).getCreateDate());

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        textChild.setText(mGroups.get(groupPosition).getDocument(childPosition).getName());

        TextView tvCreateDate = (TextView)convertView.findViewById(R.id.tvValueDocCreateDate);
        String sCreateDate = mGroups.get(groupPosition).getDocument(childPosition).getCreateDate();
        tvCreateDate.setText(sCreateDate);

        ImageView ivTreeNode = (ImageView)convertView.findViewById(R.id.ivTreeNode);

        if (childPosition != mGroups.get(groupPosition).countDocuments() - 1) {
           ivTreeNode.setImageResource(R.drawable.node_tree); //
        } else {
            ivTreeNode.setImageResource(R.drawable.end_node_tree);
        }
//        Button button = (Button)convertView.findViewById(R.id.buttonChild);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext,"button is pressed", Toast.LENGTH_SHORT).show();
//            }
//        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}