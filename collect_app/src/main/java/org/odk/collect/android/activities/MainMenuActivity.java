/*
 * Copyright (C) 2009 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.odk.collect.android.activities;
import org.javarosa.core.io.BufferedInputStream;
import org.odk.collect.android.utilities.WebUtils;
import org.odk.collect.android.utilities.ZipUtils;
import android.content.DialogInterface;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.preferences.AdminPreferencesActivity;
import org.odk.collect.android.preferences.PreferencesActivity;
import org.odk.collect.android.provider.FormsProviderAPI;
import org.odk.collect.android.provider.InstanceProviderAPI;
import org.odk.collect.android.provider.InstanceProviderAPI.InstanceColumns;
import org.odk.collect.android.utilities.CompatibilityUtils;
import org.opendatakit.httpclientandroidlib.Header;
import org.opendatakit.httpclientandroidlib.HttpEntity;
import org.opendatakit.httpclientandroidlib.client.methods.HttpGet;
import org.opendatakit.httpclientandroidlib.impl.client.SystemDefaultHttpClient;
import org.opendatakit.httpclientandroidlib.protocol.HttpContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Responsible for displaying buttons to launch the major activities. Launches
 * some activities based on returns of others.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
public class MainMenuActivity extends Activity {
	private static final String t = "MainMenuActivity";
 static ProgressDialog dialog;
	static String msg="Down";
	private static final int PASSWORD_DIALOG = 1;
final String Password="pd";
	// menu options
	private static final int MENU_PREFERENCES = Menu.FIRST;
	private static final int MENU_ADMIN = Menu.FIRST + 1;
static int i=1;

	// buttons
	private Button mEnterDataButton;
	private Button mManageFilesButton;
	private Button mSendDataButton;
	private Button mReviewDataButton;
	private Button mGetFormsButton;

	private View mReviewSpacer;
	private View mGetFormsSpacer;

	private AlertDialog mAlertDialog;
	private SharedPreferences mAdminPreferences;

	private int mCompletedCount;
	private int mSavedCount;

	private Cursor mFinalizedCursor;
	private Cursor mSavedCursor;

	private IncomingHandler mHandler = new IncomingHandler(this);
	private MyContentObserver mContentObserver = new MyContentObserver();

	private static boolean EXIT = true;





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// must be at the beginning of any activity that can be called from an
		// external intent
		Log.i(t, "Starting up, creating directories");
		try {
			Collect.createODKDirs();
		} catch (RuntimeException e) {
			createErrorDialog(e.getMessage(), EXIT);
			return;
		}

		setContentView(R.layout.main_menu);

		{
			// dynamically construct the "ODK Collect vA.B" string
			TextView mainMenuMessageLabel = (TextView) findViewById(R.id.main_menu_header);
			mainMenuMessageLabel.setText(Collect.getInstance()
					.getVersionedAppName());
		}

		setTitle(getString(R.string.app_name) + " > "
				+ getString(R.string.main_menu));

		File f = new File(Collect.ODK_ROOT + "/collect.settings");
		if (f.exists()) {

			boolean success = loadSharedPreferencesFromFile(f);
			if (success) {
				Toast.makeText(this,
						"Settings successfully loaded from file",
						Toast.LENGTH_LONG).show();
				f.delete();
			} else {
				Toast.makeText(
						this,
						"Sorry, settings file is corrupt and should be deleted or replaced",
						Toast.LENGTH_LONG).show();
			}
		}

		mReviewSpacer = findViewById(R.id.review_spacer);
		mGetFormsSpacer = findViewById(R.id.get_forms_spacer);

		mAdminPreferences = this.getSharedPreferences(
				AdminPreferencesActivity.ADMIN_PREFERENCES, 0);

		// enter data button. expects a result.
		mEnterDataButton = (Button) findViewById(R.id.enter_data);
		mEnterDataButton.setText(getString(R.string.enter_data_button));
		mEnterDataButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Collect.getInstance().getActivityLogger()
						.logAction(this, "fillBlankForm", "click");
				Intent i = new Intent(getApplicationContext(),
						FormChooserList.class);
				startActivity(i);
			}
		});

		// review data button. expects a result.
		mReviewDataButton = (Button) findViewById(R.id.review_data);
		mReviewDataButton.setText(getString(R.string.review_data_button));
		mReviewDataButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Collect.getInstance().getActivityLogger()
						.logAction(this, "editSavedForm", "click");
				Intent i = new Intent(getApplicationContext(),
						InstanceChooserList.class);
				startActivity(i);
			}
		});

		// send data button. expects a result.
		mSendDataButton = (Button) findViewById(R.id.send_data);
		mSendDataButton.setText(getString(R.string.send_data_button));
		mSendDataButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Collect.getInstance().getActivityLogger()
						.logAction(this, "uploadForms", "click");
				Intent i = new Intent(getApplicationContext(),
						InstanceUploaderList.class);
				startActivity(i);
			}
		});

		// manage forms button. no result expected.
		mGetFormsButton = (Button) findViewById(R.id.get_forms);
		mGetFormsButton.setText(getString(R.string.get_forms));
		mGetFormsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Collect.getInstance().getActivityLogger()
						.logAction(this, "downloadBlankForms", "click");
				SharedPreferences sharedPreferences = PreferenceManager
						.getDefaultSharedPreferences(MainMenuActivity.this);
				String protocol = sharedPreferences.getString(
						PreferencesActivity.KEY_PROTOCOL, getString(R.string.protocol_odk_default));
				Intent i = null;
				if (protocol.equalsIgnoreCase(getString(R.string.protocol_google_sheets))) {
					i = new Intent(getApplicationContext(),
							GoogleDriveActivity.class);
				} else {
					i = new Intent(getApplicationContext(),
							FormDownloadList.class);
				}
				startActivity(i);

			}
		});

		// manage forms button. no result expected.
		mManageFilesButton = (Button) findViewById(R.id.manage_forms);
		mManageFilesButton.setText(getString(R.string.manage_files));
		mManageFilesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Collect.getInstance().getActivityLogger()
						.logAction(this, "deleteSavedForms", "click");
				Intent i = new Intent(getApplicationContext(),
						FileManagerTabs.class);
				startActivity(i);
			}
		});
// my new code baby for synchronized buuton
		/*mManageFilesButton = (Button) findViewById(R.id.sycButt);
		mManageFilesButton.setText(getString(R.string.pull_instance));*/

		// count for finalized instances
		String selection = InstanceColumns.STATUS + "=? or "
				+ InstanceColumns.STATUS + "=?";
		String selectionArgs[] = {InstanceProviderAPI.STATUS_COMPLETE,
				InstanceProviderAPI.STATUS_SUBMISSION_FAILED};

		try {
			mFinalizedCursor = managedQuery(InstanceColumns.CONTENT_URI, null,
					selection, selectionArgs, null);
		} catch (Exception e) {
			createErrorDialog(e.getMessage(), EXIT);
			return;
		}

		if (mFinalizedCursor != null) {
			startManagingCursor(mFinalizedCursor);
		}
		mCompletedCount = mFinalizedCursor != null ? mFinalizedCursor.getCount() : 0;
		getContentResolver().registerContentObserver(InstanceColumns.CONTENT_URI, true, mContentObserver);
//		mFinalizedCursor.registerContentObserver(mContentObserver);

		// count for finalized instances
		String selectionSaved = InstanceColumns.STATUS + "=?";
		String selectionArgsSaved[] = {InstanceProviderAPI.STATUS_INCOMPLETE};

		try {
			mSavedCursor = managedQuery(InstanceColumns.CONTENT_URI, null,
					selectionSaved, selectionArgsSaved, null);
		} catch (Exception e) {
			createErrorDialog(e.getMessage(), EXIT);
			return;
		}

		if (mSavedCursor != null) {
			startManagingCursor(mSavedCursor);
		}
		mSavedCount = mSavedCursor != null ? mSavedCursor.getCount() : 0;
		// don't need to set a content observer because it can't change in the
		// background

		updateButtons();
	}

	public void pull(View v) {

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected())

		{
			showDialog(3);
			//new HttpAsyncTask1()
					//.execute("http://beta.fieldata.in/instance.zip");
		} else {

			Toast.makeText(
					MainMenuActivity.this,
					getString(R.string.net),
					Toast.LENGTH_SHORT).show();


		}





	}
	public  void st(){

		new HttpAsyncTask1()
				.execute("http://beta.fieldata.in/instance.zip");
		dialog = new ProgressDialog(this);
		dialog.setMessage("Downloading...");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
										int which) {
						dialog.dismiss();

					}
				});
		dialog.show();
	}
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				AdminPreferencesActivity.ADMIN_PREFERENCES, 0);

		boolean edit = sharedPreferences.getBoolean(
				AdminPreferencesActivity.KEY_EDIT_SAVED, true);
		if (!edit) {
			mReviewDataButton.setVisibility(View.GONE);
			mReviewSpacer.setVisibility(View.GONE);
		} else {
			mReviewDataButton.setVisibility(View.VISIBLE);
			mReviewSpacer.setVisibility(View.VISIBLE);
		}

		boolean send = sharedPreferences.getBoolean(
				AdminPreferencesActivity.KEY_SEND_FINALIZED, true);
		if (!send) {
			mSendDataButton.setVisibility(View.GONE);
		} else {
			mSendDataButton.setVisibility(View.VISIBLE);
		}

		boolean get_blank = sharedPreferences.getBoolean(
				AdminPreferencesActivity.KEY_GET_BLANK, true);
		if (!get_blank) {
			mGetFormsButton.setVisibility(View.GONE);
			mGetFormsSpacer.setVisibility(View.GONE);
		} else {
			mGetFormsButton.setVisibility(View.VISIBLE);
			mGetFormsSpacer.setVisibility(View.VISIBLE);
		}

		boolean delete_saved = sharedPreferences.getBoolean(
				AdminPreferencesActivity.KEY_DELETE_SAVED, true);
		if (!delete_saved) {
			mManageFilesButton.setVisibility(View.GONE);
		} else {
			mManageFilesButton.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mAlertDialog != null && mAlertDialog.isShowing()) {
			mAlertDialog.dismiss();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Collect.getInstance().getActivityLogger().logOnStart(this);
	}

	@Override
	protected void onStop() {
		Collect.getInstance().getActivityLogger().logOnStop(this);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Collect.getInstance().getActivityLogger()
				.logAction(this, "onCreateOptionsMenu", "show");
		super.onCreateOptionsMenu(menu);

		CompatibilityUtils.setShowAsAction(
				menu.add(0, MENU_PREFERENCES, 0, R.string.general_preferences)
						.setIcon(R.drawable.ic_menu_preferences),
				MenuItem.SHOW_AS_ACTION_NEVER);
		CompatibilityUtils.setShowAsAction(
				menu.add(0, MENU_ADMIN, 0, R.string.admin_preferences)
						.setIcon(R.drawable.ic_menu_login),
				MenuItem.SHOW_AS_ACTION_NEVER);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_PREFERENCES:
				Collect.getInstance()
						.getActivityLogger()
						.logAction(this, "onOptionsItemSelected",
								"MENU_PREFERENCES");
				Intent ig = new Intent(this, PreferencesActivity.class);
				startActivity(ig);
				return true;
			case MENU_ADMIN:
				Collect.getInstance().getActivityLogger()
						.logAction(this, "onOptionsItemSelected", "MENU_ADMIN");
				String pw = mAdminPreferences.getString(
						AdminPreferencesActivity.KEY_ADMIN_PW, "");
				if ("".equalsIgnoreCase(pw)) {
					Intent i = new Intent(getApplicationContext(),
							AdminPreferencesActivity.class);
					startActivity(i);
				} else {
					showDialog(PASSWORD_DIALOG);
					Collect.getInstance().getActivityLogger()
							.logAction(this, "createAdminPasswordDialog", "show");
				}
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void createErrorDialog(String errorMsg, final boolean shouldExit) {
		Collect.getInstance().getActivityLogger()
				.logAction(this, "createErrorDialog", "show");
		mAlertDialog = new AlertDialog.Builder(this).create();
		mAlertDialog.setIcon(android.R.drawable.ic_dialog_info);
		mAlertDialog.setMessage(errorMsg);
		DialogInterface.OnClickListener errorListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int i) {
				switch (i) {
					case DialogInterface.BUTTON_POSITIVE:
						Collect.getInstance()
								.getActivityLogger()
								.logAction(this, "createErrorDialog",
										shouldExit ? "exitApplication" : "OK");
						if (shouldExit) {
							finish();
						}
						break;
				}
			}
		};
		mAlertDialog.setCancelable(false);
		mAlertDialog.setButton(getString(R.string.ok), errorListener);
		mAlertDialog.show();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		final ProgressDialog Dialog = new ProgressDialog(this);
		switch (id) {
			case PASSWORD_DIALOG:

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				final AlertDialog passwordDialog = builder.create();

				passwordDialog.setTitle(getString(R.string.enter_admin_password));
				final EditText input = new EditText(this);
				input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				input.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
				passwordDialog.setView(input, 20, 10, 20, 10);

				passwordDialog.setButton(AlertDialog.BUTTON_POSITIVE,
						getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int whichButton) {
								String value = input.getText().toString();
								String pw = mAdminPreferences.getString(
										AdminPreferencesActivity.KEY_ADMIN_PW, "");




								if((pw.compareTo(value) == 0)){
									Intent i = new Intent(getApplicationContext(),
											AdminPreferencesActivity.class);
									input.setText("");
									passwordDialog.dismiss();

									startActivity(i);



								} else {
									Toast.makeText(
											MainMenuActivity.this,
											getString(R.string.admin_password_incorrect),
											Toast.LENGTH_SHORT).show();
									Collect.getInstance()
											.getActivityLogger()
											.logAction(this, "adminPasswordDialog",
													"PASSWORD_INCORRECT");
								}
							}
						});

				passwordDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
						getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								Collect.getInstance()
										.getActivityLogger()
										.logAction(this, "adminPasswordDialog",
												"cancel");
								input.setText("");
								return;
							}
						});

				passwordDialog.getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				return passwordDialog;
			case 3:{


				AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
				final AlertDialog passwordDialog1 = builder1.create();

				passwordDialog1.setTitle(getString(R.string.enter_admin_password));
				final EditText input1 = new EditText(this);
				input1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				input1.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
				passwordDialog1.setView(input1, 20, 10, 20, 10);

				passwordDialog1.setButton(AlertDialog.BUTTON_POSITIVE,
						getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int whichButton) {
								String value1 = input1.getText().toString();

if(value1.equals(Password)){
	passwordDialog1.dismiss();
	st();

}

										else{
	input1.setText("");
										Toast.makeText(
												MainMenuActivity.this,
												getString(R.string.admin_password_incorrect),
												Toast.LENGTH_SHORT).show();
										Collect.getInstance()
												.getActivityLogger()
												.logAction(this, "adminPasswordDialog",
														"PASSWORD_INCORRECT");


								}









								}
						});

				passwordDialog1.setButton(AlertDialog.BUTTON_NEGATIVE,
						getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								Collect.getInstance()
										.getActivityLogger()
										.logAction(this, "adminPasswordDialog",
												"cancel");
								input1.setText("");
								return;
							}
						});

				passwordDialog1.getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				return passwordDialog1;






			}


		}
		return null;
	}

	private void updateButtons() {
		if (mFinalizedCursor != null && !mFinalizedCursor.isClosed()) {
			mFinalizedCursor.requery();
			mCompletedCount = mFinalizedCursor.getCount();
			if (mCompletedCount > 0) {
				mSendDataButton.setText(getString(R.string.send_data_button, mCompletedCount));
			} else {
				mSendDataButton.setText(getString(R.string.send_data));
			}
		} else {
			mSendDataButton.setText(getString(R.string.send_data));
			Log.w(t, "Cannot update \"Send Finalized\" button label since the database is closed. Perhaps the app is running in the background?");
		}

		if (mSavedCursor != null && !mSavedCursor.isClosed()) {
			mSavedCursor.requery();
			mSavedCount = mSavedCursor.getCount();
			if (mSavedCount > 0) {
				mReviewDataButton.setText(getString(R.string.review_data_button,
						mSavedCount));
			} else {
				mReviewDataButton.setText(getString(R.string.review_data));
			}
		} else {
			mReviewDataButton.setText(getString(R.string.review_data));
			Log.w(t, "Cannot update \"Edit Form\" button label since the database is closed. Perhaps the app is running in the background?");
		}
	}

	/**
	 * notifies us that something changed
	 */
	private class MyContentObserver extends ContentObserver {

		public MyContentObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			mHandler.sendEmptyMessage(0);
		}
	}

	/*
	 * Used to prevent memory leaks
	 */
	static class IncomingHandler extends Handler {
		private final WeakReference<MainMenuActivity> mTarget;

		IncomingHandler(MainMenuActivity target) {
			mTarget = new WeakReference<MainMenuActivity>(target);
		}

		@Override
		public void handleMessage(Message msg) {
			MainMenuActivity target = mTarget.get();
			if (target != null) {
				target.updateButtons();
			}
		}
	}

	private boolean loadSharedPreferencesFromFile(File src) {
		// this should probably be in a thread if it ever gets big
		boolean res = false;
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(new FileInputStream(src));
			Editor prefEdit = PreferenceManager.getDefaultSharedPreferences(
					this).edit();
			prefEdit.clear();
			// first object is preferences
			Map<String, ?> entries = (Map<String, ?>) input.readObject();
			for (Entry<String, ?> entry : entries.entrySet()) {
				Object v = entry.getValue();
				String key = entry.getKey();

				if (v instanceof Boolean)
					prefEdit.putBoolean(key, ((Boolean) v).booleanValue());
				else if (v instanceof Float)
					prefEdit.putFloat(key, ((Float) v).floatValue());
				else if (v instanceof Integer)
					prefEdit.putInt(key, ((Integer) v).intValue());
				else if (v instanceof Long)
					prefEdit.putLong(key, ((Long) v).longValue());
				else if (v instanceof String)
					prefEdit.putString(key, ((String) v));
			}
			prefEdit.commit();

			// second object is admin options
			Editor adminEdit = getSharedPreferences(AdminPreferencesActivity.ADMIN_PREFERENCES, 0).edit();
			adminEdit.clear();
			// first object is preferences
			Map<String, ?> adminEntries = (Map<String, ?>) input.readObject();
			for (Entry<String, ?> entry : adminEntries.entrySet()) {
				Object v = entry.getValue();
				String key = entry.getKey();

				if (v instanceof Boolean)
					adminEdit.putBoolean(key, ((Boolean) v).booleanValue());
				else if (v instanceof Float)
					adminEdit.putFloat(key, ((Float) v).floatValue());
				else if (v instanceof Integer)
					adminEdit.putInt(key, ((Integer) v).intValue());
				else if (v instanceof Long)
					adminEdit.putLong(key, ((Long) v).longValue());
				else if (v instanceof String)
					adminEdit.putString(key, ((String) v));
			}
			adminEdit.commit();

			res = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return res;
	}

	private class HttpAsyncTask1 extends AsyncTask<String,String, String> {
		//private final ProgressDialog dialog = new ProgressDialog(MainMenuActivity.this);

		//return GET1(urls[0]);


		//}


		@Override
		protected void onProgressUpdate(final String... values) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					dialog.setMessage(values[0]);
				}
			});
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
		}

		@Override
		protected String doInBackground(String... urls) {
			publishProgress(msg, Integer.valueOf(i).toString(), Integer.valueOf(6)
					.toString());

			//public  static String GET1(String url) {
			HttpContext localContext = Collect.getInstance().getHttpContext();

			org.opendatakit.httpclientandroidlib.client.HttpClient httpclient = WebUtils.createHttpClient(WebUtils.CONNECTION_TIMEOUT);


			//Collect.getInstance().getContentResolver().delete(InstanceColumns.CONTENT_URI,InstanceColumns._ID+">23",null);
			org.opendatakit.httpclientandroidlib.HttpResponse response;
			try {
				HttpGet req = WebUtils.createOpenRosaHttpGet(new URL(urls[0]).toURI());

				req.setHeader("Accept", "text/zip");
				req.setHeader("charset", "utf-8");
				response = httpclient.execute(req, localContext);

				InputStream is = null;
				OutputStream os = null;

int j=0;
				try {
					HttpEntity entity = response.getEntity();
					is = entity.getContent();


					ZipInputStream zipIn = new ZipInputStream(is);

					ZipEntry entry = zipIn.getNextEntry();
					ZipEntry en=zipIn.getNextEntry();
/*while(en!=null){
	j++;
	en= zipIn.getNextEntry();

}*/
					System.out.println("sk");
					String filePath = "";

					String o = null;
					while (entry != null) {

						if (!entry.isDirectory()) {
							ContentValues cv = new ContentValues();

							int start = entry.getName().lastIndexOf('/');
							int end = entry.getName().lastIndexOf('.');
							int name = entry.getName().indexOf('_');
							String dname = entry.getName().substring(start + 1, name);

							String dis = entry.getName().substring(name + 1, end);
							System.out.println(dname + "  god" + dis + "  darling");

							filePath = Collect.INSTANCES_PATH + File.separator + entry.getName().substring(start + 1, end);

							File temp = new File(filePath);
							if (!temp.exists())
								temp.mkdirs();
							os = new FileOutputStream(new File(temp.getPath() + File.separator + entry.getName().substring(start + 1, end + 1) + "xml"));
							byte buf[] = new byte[4096];
							int len;
							while ((len = zipIn.read(buf)) > 0) {
								os.write(buf, 0, len);
								//publishProgress((int) (0));
								System.out.println("janu");
							}
							os.flush();

							String path = Collect.INSTANCES_PATH + File.separator + entry.getName().substring(start + 1, end) + File.separator + entry.getName().substring(start + 1, end + 1) + "xml";
							cv.put(InstanceColumns.DISPLAY_NAME, dname);
							cv.put(InstanceColumns.SUBMISSION_URI, "");
							cv.put(InstanceColumns.CAN_EDIT_WHEN_COMPLETE, "true");
							cv.put(InstanceColumns.INSTANCE_FILE_PATH, path);
							System.out.println(path + "  pathhh");

							cv.put(InstanceColumns.JR_FORM_ID, dname);
							cv.put(InstanceColumns.JR_VERSION, o);

							cv.put(InstanceColumns.STATUS, "incomplete");
							cv.put(InstanceColumns.LAST_STATUS_CHANGE_DATE, dis);
							cv.put(InstanceColumns.DISPLAY_SUBTEXT, "completed on" + dis);

							Collect.getInstance().getContentResolver().insert(InstanceColumns.CONTENT_URI, cv);
							msg = "Extracting file";
							publishProgress(msg, Integer.valueOf(i).toString(), Integer.valueOf(6)
									.toString());
							i++;
						} else {

							File dir = new File(filePath);
							dir.mkdir();

						}

						zipIn.closeEntry();
						System.out.println(entry.getName());
						entry = zipIn.getNextEntry();

					}
//dialog.dismiss();
					zipIn.close();
				} catch (Exception e) {
					System.out.println("parveen");
				}
			} catch (Exception e) {
				System.out.println("sethi");
			}
			return "";

		}

	}
}