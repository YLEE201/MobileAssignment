package org.yuhonglee.gcu.mobileassignment.Fragments;
//Name.Yu Hong Lee
//StD No.S1620580

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.yuhonglee.gcu.mobileassignment.ListItemAdapter;
import org.yuhonglee.gcu.mobileassignment.R;
import org.yuhonglee.gcu.mobileassignment.PullParser.XMLPullParserRoadworks;

import java.io.IOException;
import java.io.InputStream;

import java.lang.ref.WeakReference;
import java.net.URL;

import java.util.List;
import java.util.Objects;

//extends to the swipe refresh to allow refreshing the data with a upwards swipe
public class Frag3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
        //layout variables
        private EditText Search;
        private Button startButton;
        private ProgressBar progressBar1;
        private ImageView img;
        private ListView listView;
        private View rootView;
        private SwipeRefreshLayout swipeLayout;

        //programming variables
        private ArrayAdapter<ListItemAdapter> adapter;
        private List<ListItemAdapter> current;
        private Context context;

        // Traffic Scotland URL Planned Road Works
        private String urlSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                //sets the view for the fragment instead of the default Main Activity
                rootView = inflater.inflate(R.layout.frag3_layout, container, false);


                //Connection between program and the layout
                progressBar1 = rootView.findViewById(R.id.progressBar1);
                startButton = (Button) rootView.findViewById(R.id.startButton);
                listView = rootView.findViewById(R.id.listView1);
                swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
                Search = rootView.findViewById(R.id.Search);

                //progress bar initialising
                progressBar1.setIndeterminate(false);
                progressBar1.setProgress(0);
                progressBar1.setMax(10);

                //sets the swipe refresh to this view and color to green
                swipeLayout.setOnRefreshListener(this);
                swipeLayout.setColorSchemeColors(Color.GREEN);

                //executes the async task when the app opens
                new RetrieveFeedTask1(getActivity()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                //when button is pressed or clicked run the async task
                startButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                new RetrieveFeedTask1(getActivity()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                });
                return rootView;
        }
        //swipe refresh code that executes the async task
        @Override
        public void onRefresh() {
                new RetrieveFeedTask1(getActivity()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                                swipeLayout.setRefreshing(false);
                        }
                }, 2000);
        }

        //async task
        private class RetrieveFeedTask1 extends AsyncTask<Void, Integer, String> {
                //for passing context// needed for dark theme
                private final WeakReference<Context> contextReference;

                public RetrieveFeedTask1(Context context) {
                        this.contextReference = new WeakReference<Context>(context);
                }

                protected void onPreExecute() {
                        super.onPreExecute();

                        //Set the progress bar to appear and set progress bar
                        progressBar1.setVisibility(View.VISIBLE);
                        progressBar1.setMax(100);
                }

                protected void onProgressUpdate(Integer... values) {
                        super.onProgressUpdate(values);

                        //update progress bar
                        progressBar1.setProgress(values[0]);
                }

                protected String doInBackground(Void... urls) {
                        //log statement to check if this bit runs
                        Log.e("MyTag", "in run");
                        //passes empty string since passing input for the list view caused errors
                        String emptyString = "";

                        //initialize / sets variables
                        InputStream is = null;
                        String API_URL = urlSource;

                        //sets the value of the progress bar
                        for (int i =0;i < 100;i++)
                        {

                                try {
                                        Thread.sleep(10);
                                        publishProgress(i);

                                } catch (InterruptedException ie)
                                {
                                        ie.printStackTrace();
                                }
                        }

                        try {
                                //log statement to see if this bit runs
                                Log.e("MyTag", "in try");
                                //converts the string url to type URL
                                URL url = new URL(API_URL);

                                //calls the pull parser class
                                XMLPullParserRoadworks parser = new XMLPullParserRoadworks();

                                //Stores the API feed into String is
                                is = url.openStream();

                                //parses the API feed and stores it into List
                                current = parser.parse(is);

                                //closes the API stream
                                is.close();

                        } catch (IOException ae) {
                                Log.e("MyTag", "ioexception");
                        }
                        return emptyString;
                }


                protected void onPostExecute(String emptyString){
                        //hides the progress bar and display text
                        progressBar1.setVisibility(View.GONE);

                        //checks context and runs the code if there is
                        context = this.contextReference.get();
                        if(context != null) {
                                Toast.makeText(context, "Data Downloaded!!", Toast.LENGTH_SHORT).show();
                                //Stores the list of current into the textview in row layout
                                adapter = new ArrayAdapter<ListItemAdapter>
                                        (Objects.requireNonNull(context), R.layout.row, R.id.listtext, current) {
                                        @Override
                                        public View getView(int position, View convertView, ViewGroup parent) {
                                                //changes the colour of list item based on the Strings
                                                // (could've been integer but I had no time to turn this into int)
                                                View itemView = super.getView(position, convertView, parent);
                                                if (getItem(position).getDifference().equals("{DAYS=0, HOURS=0}")) {
                                                        itemView.setBackgroundColor(Color.GREEN);
                                                } else if (getItem(position).getDifference().equals("{DAYS=1, HOURS=0}")) {
                                                        itemView.setBackgroundColor(getResources().getColor(R.color.Orange));
                                                } else if (getItem(position).getDifference().equals("{DAYS=3, HOURS=0}")) {
                                                        itemView.setBackgroundColor(Color.RED);
                                                } else {
                                                        TypedArray a = context.getTheme().obtainStyledAttributes(R.styleable.textlist);

                                                        int color = a.getColor(R.styleable.textlist_bgColor, -1);

                                                        itemView.setBackgroundColor(color);
                                                }


                                                return itemView;

                                        }
                                };
                        }
                        //calls the list view and sets filled row layout to list view
                        listView.setAdapter(adapter);

                        final ListAdapter listAdapter = listView.getAdapter();
                        if (listAdapter == null) {
                                // pre-condition
                                return;
                        }

                        //other way to make expanded list view as the default way was too complicated
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                private SparseArray<Boolean> hasClicked = new SparseArray<Boolean>();

                                //if an list item is clicked it will expand or collapse, also changes images to shows this
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long title) {
                                        ImageView img= (ImageView) view.findViewById(R.id.icon);
                                        if(hasClicked.get(position, false))  {
                                                img.setImageResource(R.drawable.baseline_expand_more_black_24dp);
                                                current.get(position).setExpand(false);
                                                hasClicked.put(position, false);
                                                adapter.notifyDataSetChanged();

                                        }
                                        else {
                                                img.setImageResource(R.drawable.baseline_expand_less_black_24dp);
                                                current.get(position).setExpand(true);
                                                hasClicked.put(position, true);
                                                adapter.notifyDataSetChanged();
                                        }

                                        //dynamically change height
                                        int totalHeight = 0;
                                        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
                                        for (int i = 0; i < listAdapter.getCount(); i++) {
                                                View listItem = listAdapter.getView(i, null, listView);
                                                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                                                totalHeight += listItem.getMeasuredHeight();
                                        }

                                        ViewGroup.LayoutParams params = listView.getLayoutParams();
                                        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
                                        listView.setLayoutParams(params);
                                        listView.requestLayout();
                                }

                        });

                        //listens to the edit text for any changes
                        Search.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void onTextChanged(final CharSequence s, int start, int before, int count) {
                                        //once user hits enter or next on keyboard executes code
                                        ((EditText)rootView.findViewById(R.id.Search)).setOnEditorActionListener(
                                                new EditText.OnEditorActionListener() {
                                                        //boiler code for the enter key on soft keyboard
                                                        @Override
                                                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                                if (event != null) {
                                                                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                                                                actionId == EditorInfo.IME_ACTION_DONE ||
                                                                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                                                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                                                                if (!event.isShiftPressed()) {
                                                                                        return true; // consume.
                                                                                }
                                                                        }
                                                                        //dynamically change height
                                                                        int totalHeight = 0;
                                                                        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
                                                                        for (int i = 0; i < listAdapter.getCount(); i++) {
                                                                                View listItem = listAdapter.getView(i, null, listView);
                                                                                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                                                                                totalHeight += listItem.getMeasuredHeight();
                                                                        }

                                                                        ViewGroup.LayoutParams params = listView.getLayoutParams();
                                                                        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
                                                                        listView.setLayoutParams(params);
                                                                        listView.requestLayout();
                                                                        //-----------------------------------------------------------------------------------------------------
                                                                        return false; // pass on to other listeners.
                                                                }
                                                                //filters the input based on edit text
                                                                (Frag3.this).adapter.getFilter().filter(s);

                                                                //hides keyboard
                                                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                imm.hideSoftInputFromWindow(Search.getWindowToken(), 0);
                                                                return true;
                                                        }
                                                });
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }

                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }
                        });

                }
        }
}
