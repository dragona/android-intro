package mg.x261.myexpandablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the expandable list view
        expandableListView = findViewById(R.id.expandable_list);

        // prepare list data
        prepareListData();

        // set up adapter
        adapter = new CustomExpandableListAdapter(listDataHeader, listDataChild);
        expandableListView.setAdapter(adapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // add headers
        listDataHeader.add("Header 1");
        listDataHeader.add("Header 2");
        listDataHeader.add("Header 3");

        // add child items
        List<String> child1 = new ArrayList<>();
        child1.add("Child 1 of Header 1");
        child1.add("Child 2 of Header 1");
        child1.add("Child 3 of Header 1");

        List<String> child2 = new ArrayList<>();
        child2.add("Child 1 of Header 2");
        child2.add("Child 2 of Header 2");
        child2.add("Child 3 of Header 2");

        List<String> child3 = new ArrayList<>();
        child3.add("Child 1 of Header 3");
        child3.add("Child 2 of Header 3");
        child3.add("Child 3 of Header 3");

        // add child items to headers
        listDataChild.put(listDataHeader.get(0), child1);
        listDataChild.put(listDataHeader.get(1), child2);
        listDataChild.put(listDataHeader.get(2), child3);
    }

    private static class CustomExpandableListAdapter extends BaseExpandableListAdapter {
        private List<String> listDataHeader;
        private HashMap<String, List<String>> listDataChild;

        public CustomExpandableListAdapter(List<String> listDataHeader, HashMap<String, List<String>> listDataChild) {
            this.listDataHeader = listDataHeader;
            this.listDataChild = listDataChild;
        }

        @Override
        public int getGroupCount() {
            return listDataHeader.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            String headerTitle = listDataHeader.get(groupPosition);
            List<String> childList = listDataChild.get(headerTitle);
            return childList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return listDataHeader.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String headerTitle = listDataHeader.get(groupPosition);
            List<String> childList = listDataChild.get(headerTitle);
            return childList.get(childPosition);
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
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            }
            TextView headerTextView = convertView.findViewById(android.R.id.text1);
            headerTextView.setText(headerTitle);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String childTitle = (String) getChild(groupPosition, childPosition);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            }
            TextView childTextView = convertView.findViewById(android.R.id.text1);
            childTextView.setText(childTitle);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
