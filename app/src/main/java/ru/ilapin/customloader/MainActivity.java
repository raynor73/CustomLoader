package ru.ilapin.customloader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment())
					.commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<DataEntry>> {

		private static final String TAG = "PlaceholderFragment";

		private Activity mActivity;
		private RecyclerView mRecyclerView;
		private DataAdapter mAdapter;

		public PlaceholderFragment() {
		}

		@Override
		public void onAttach(final Activity activity) {
			Log.d(TAG, "onAttach");
			super.onAttach(activity);

			mActivity = activity;
		}

		@Override
		public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
								 final Bundle savedInstanceState) {
			Log.d(TAG, "onCreateView");
			return inflater.inflate(R.layout.fragment_main, container, false);
		}

		@Override
		public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
			Log.d(TAG, "onViewCreated");
			super.onViewCreated(view, savedInstanceState);

			mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		}

		@Override
		public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
			Log.d(TAG, "onActivityCreated");
			super.onActivityCreated(savedInstanceState);

			mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
			mAdapter = new DataAdapter(mActivity);
			mRecyclerView.setAdapter(mAdapter);

			getLoaderManager().initLoader(0, null, this);
		}

		@Override
		public Loader<List<DataEntry>> onCreateLoader(final int id, final Bundle args) {
			Log.d(TAG, "onCreateLoader");
			return new CustomLoader(mActivity);
		}

		@Override
		public void onLoadFinished(final Loader<List<DataEntry>> loader, final List<DataEntry> data) {
			Log.d(TAG, "onLoadFinished");
			mAdapter.setData(data);
		}

		@Override
		public void onLoaderReset(final Loader<List<DataEntry>> loader) {
			Log.d(TAG, "onLoaderReset");
			mAdapter.setData(null);
		}

		private static class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

			private List<DataEntry> mDataEntries;
			private final Context mContext;

			private DataAdapter(final Context context) {
				this.mContext = context;
			}

			public void setData(final List<DataEntry> dataEntries) {
				mDataEntries = dataEntries;
				notifyDataSetChanged();
			}

			@Override
			public DataAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
				return new ViewHolder(LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false));
			}

			@Override
			public void onBindViewHolder(final DataAdapter.ViewHolder holder, final int position) {
				if (mDataEntries != null) {
					holder.textView.setText(mDataEntries.get(position).label);
				}
			}

			@Override
			public int getItemCount() {
				if (mDataEntries == null) {
					return 0;
				} else {
					return mDataEntries.size();
				}
			}

			public class ViewHolder extends RecyclerView.ViewHolder {

				public TextView textView;

				public ViewHolder(final View itemView) {
					super(itemView);

					textView = (TextView) itemView.findViewById(android.R.id.text1);
				}
			}
		}
	}
}
