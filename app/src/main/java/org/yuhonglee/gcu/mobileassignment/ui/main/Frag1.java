package org.yuhonglee.gcu.mobileassignment.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.yuhonglee.gcu.mobileassignment.MainActivity;
import org.yuhonglee.gcu.mobileassignment.R;
import org.yuhonglee.gcu.mobileassignment.XMLPullParserHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PrivateKey;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Frag1 extends Fragment {
private Button startButton;
private ProgressBar progressBar1;
private ListView listView;
private ArrayAdapter<currentincidents> adapter;
private List<currentincidents> current;
private View rootView;
private ImageView img;

// Traffic Scotland URLs
//private String urlSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
//private String urlSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
private String urlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

}
@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.frag1_layout, container, false);

        progressBar1 = rootView.findViewById(R.id.progressBar1);
        startButton = (Button) rootView.findViewById(R.id.startButton);
        listView = rootView.findViewById(R.id.listView1);


        startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        new RetrieveFeedTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }
        });
        return rootView;
}
        private class RetrieveFeedTask1 extends AsyncTask<Void, Void, String> {
                private Exception exception;
                private String url;

                protected void onPreExecute() {
                        progressBar1.setVisibility(View.VISIBLE);

                }

                protected String doInBackground(Void... urls) {
                        String emptyString = "";
                        InputStream is = null;
                        String API_URL = urlSource;

                        Log.e("MyTag", "in run");

                        try {
                                Log.e("MyTag", "in try");
                                URL url = new URL(API_URL);
                                XMLPullParserHandler parser = new XMLPullParserHandler();
                                is = url.openStream();
                                current = parser.parse(is);


                                adapter = new ArrayAdapter<currentincidents>
                                        (getActivity(), R.layout.row, R.id.listtext, current);


                                is.close();

                        } catch (IOException ae) {
                                Log.e("MyTag", "ioexception");
                        }
                        return emptyString;
                }


                protected void onPostExecute(String emptyString){
                        progressBar1.setVisibility(View.GONE);

                        listView.setAdapter(adapter);



                        final ListAdapter listAdapter = listView.getAdapter();
                        if (listAdapter == null) {
                                // pre-condition
                                return;
                        }

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                private SparseArray<Boolean> hasClicked = new SparseArray<Boolean>();

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long title) {
                                         ImageView img= (ImageView) view.findViewById(R.id.icon);

                                        if(hasClicked.get(position, false))  {
                                                img.setImageResource(R.drawable.baseline_expand_more_black_48dp);
                                                current.get(position).setExpand(false);
                                                hasClicked.put(position, false);
                                                adapter.notifyDataSetChanged();

                                        }
                                        else {
                                                img.setImageResource(R.drawable.baseline_expand_less_black_48dp);
                                                current.get(position).setExpand(true);
                                                hasClicked.put(position, true);
                                                adapter.notifyDataSetChanged();
                                        }

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
                }
        }
}
