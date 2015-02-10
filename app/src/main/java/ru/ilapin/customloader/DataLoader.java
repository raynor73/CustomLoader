package ru.ilapin.customloader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by Raynor on 10.02.2015.
 */
public class DataLoader extends AsyncTaskLoader<List<DataEntry>> {

	private static final String TAG = "DataLoader";

	private List<DataEntry> mDataEntries;

	public DataLoader(final Context context) {
		super(context);
	}

	@Override
	public List<DataEntry> loadInBackground() {
		Log.d(TAG, "loadInBackground");

		try {
			Thread.sleep(10000);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}

		mDataEntries = DataProvider.sDataEntries;

		return mDataEntries;
	}

	@Override
	protected void onStartLoading() {
		Log.d(TAG, "onStartLoading");

		if (mDataEntries != null) {
			deliverResult(mDataEntries);
		} else {
			forceLoad();
		}
	}

	@Override
	protected void onReset() {
		Log.d(TAG, "onReset");

		mDataEntries = null;
	}
}
