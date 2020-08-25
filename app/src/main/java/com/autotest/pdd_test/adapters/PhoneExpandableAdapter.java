package com.autotest.pdd_test.adapters;


import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.autotest.pdd_test.R;
import java.util.ArrayList;


public class PhoneExpandableAdapter extends BaseExpandableListAdapter {

    private Fragment fragment;
    private ArrayList<Object> childtems;
    private ArrayList<Object> childtemsLower;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child, childLower;

    public PhoneExpandableAdapter(ArrayList<String> parents, ArrayList<Object> children, ArrayList<Object> children2) {
        this.parentItems = parents;
        this.childtems = children;
        this.childtemsLower = children2;
    }

    public void setInflater(LayoutInflater inflater, Fragment fragment) {
        this.inflater = inflater;
        this.fragment = fragment;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return ((ArrayList<String>) childtems.get(groupPosition)).size();

    }

    @Override
    public Object getGroup(int groupPosition) {

        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {

        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return 0;
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_phone_group, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childtems.get(groupPosition);
        childLower = (ArrayList<String>) childtemsLower.get(groupPosition);

        TextView textView = null;
        TextView textView2 = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_phone_item, null);
        }

        textView = (TextView) convertView.findViewById(R.id.text1);
        textView.setText(child.get(childPosition));
        textView2 = (TextView) convertView.findViewById(R.id.text2);
        textView2.setText(childLower.get(childPosition));
        TextView textView3 = (TextView) convertView.findViewById(R.id.text2);
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String phone_no = textView3.getText().toString().replaceAll("-", "");
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone_no));
                parent.getContext().startActivity(callIntent);

            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String phone_no = textView3.getText().toString().replaceAll("-", "");
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone_no));
                parent.getContext().startActivity(callIntent);

            }
        });
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }
}