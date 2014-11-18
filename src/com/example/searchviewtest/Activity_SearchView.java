package com.example.searchviewtest;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
/**
 * 			匹配所有有此字母字段
 * @author hc360
 *
 */
public class Activity_SearchView extends Activity implements
		SearchView.OnQueryTextListener {
	ListView listView;
	SearchView searchView;
	Object[] names;
	ArrayAdapter<String> adapter;
	ArrayList<String> mAllList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		initActionbar();
		searchView = (SearchView) findViewById(R.id.searchView);
		names = loadData();
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
				android.R.layout.simple_expandable_list_item_1, names));

		// listView.setTextFilterEnabled(true);
		searchView.setOnQueryTextListener(this);
		searchView.setSubmitButtonEnabled(false);

		// SearchView去掉（修改）搜索框的背景 修改光标
		 setSearchViewBackground(searchView);
	}

//	public void initActionbar() {
//		// 自定义标题栏
//		getActionBar().setDisplayShowHomeEnabled(false);
//		getActionBar().setDisplayShowTitleEnabled(false);
//		getActionBar().setDisplayShowCustomEnabled(true);
//		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View mTitleView = mInflater.inflate(R.layout.custom_action_bar_layout,
//				null);
//		getActionBar().setCustomView(
//				mTitleView,
//				new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
//						LayoutParams.WRAP_CONTENT));
//		searchView = (SearchView) mTitleView.findViewById(R.id.search_view);
//	}

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
		Object[] obj = searchItem(newText);
		updateLayout(obj);
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object[] searchItem(String name) {
		ArrayList<String> mSearchList = new ArrayList<String>();
		for (int i = 0; i < mAllList.size(); i++) {
			int index = mAllList.get(i).indexOf(name);
			// 存在匹配的数据
			if (index != -1) {
				mSearchList.add(mAllList.get(i));
			}
		}
		return mSearchList.toArray();
	}

	public void updateLayout(Object[] obj) {
		listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
				android.R.layout.simple_expandable_list_item_1, obj));
	}

	// android4.0 SearchView去掉（修改）搜索框的背景 修改光标
	public void setSearchViewBackground(SearchView searchView) {
		try {
			Class<?> argClass = searchView.getClass();
			// 指定某个私有属性
			Field ownField = argClass.getDeclaredField("mSearchPlate"); // 注意mSearchPlate的背景是stateListDrawable(不同状态不同的图片)
			// 所以不能用BitmapDrawable
			// setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
			ownField.setAccessible(true);
			View mView = (View) ownField.get(searchView);
			mView.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));

			// 指定某个私有属性
			Field mQueryTextView = argClass.getDeclaredField("mQueryTextView");
			mQueryTextView.setAccessible(true);
			Class<?> mTextViewClass = mQueryTextView.get(searchView).getClass()
					.getSuperclass().getSuperclass().getSuperclass();

			// mCursorDrawableRes光标图片Id的属性
			// 这个属性是TextView的属性，所以要用mQueryTextView（SearchAutoComplete）的父类（AutoCompleteTextView）的父
			// 类( EditText）的父类(TextView)
			Field mCursorDrawableRes = mTextViewClass
					.getDeclaredField("mCursorDrawableRes");

			// setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
			mCursorDrawableRes.setAccessible(true);
			mCursorDrawableRes.set(mQueryTextView.get(searchView),
					R.drawable.icon);// 注意第一个参数持有这个属性(mQueryTextView)的对象(mSearchView)
			// 光标必须是一张图片不能是颜色，因为光标有两张图片，一张是第一次获得焦点的时候的闪烁的图片，一张是后边有内容时候的图片，如果用颜色填充的话，就会失去闪烁的那张图片，颜色填充的会缩短文字和光标的距离（某些字母会背光标覆盖一部分）。
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}