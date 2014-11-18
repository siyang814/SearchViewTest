package com.example.searchviewtest;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
/**
 * 			匹配首字母
 * @author hc360
 *
 */
public class MainActivity extends Activity implements
		SearchView.OnQueryTextListener {
	ListView listView;
	SearchView searchView;
	Object[] names;
	ArrayAdapter<String> adapter;
	ArrayList<String> mAllList = new ArrayList<String>();

	public void btn_click ( View v )
	{
		startActivity(new Intent(this, Activity_SearchView.class));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		initActionbar();
		names = loadData();
		searchView = (SearchView) findViewById(R.id.searchView);
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
				android.R.layout.simple_expandable_list_item_1, names));

		listView.setTextFilterEnabled(true);
		searchView.setOnQueryTextListener(this);
		searchView.setSubmitButtonEnabled(false);
	}

	public void initActionbar() {
		// 自定义标题栏
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mTitleView = mInflater.inflate(R.layout.activity_searchview, null);
		getActionBar().setCustomView(
				mTitleView,
				new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
		searchView = (SearchView) mTitleView.findViewById(R.id.searchView);
	}

	public Object[] loadData() {
		mAllList.add("aa");
		mAllList.add("ddfa");
		mAllList.add("qw");
		mAllList.add("sd");
		mAllList.add("fd");
		mAllList.add("cf");
		mAllList.add("re");
		return mAllList.toArray();
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		if (TextUtils.isEmpty(newText)) {
			// Clear the text filter.
			listView.clearTextFilter();
		} else {
			// Sets the initial value for the text filter.
			listView.setFilterText(newText.toString());
		}
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}
}