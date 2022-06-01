package com.example.news.fragments.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.news.Model.News;
import com.example.news.R;
import com.example.news.activities.DetailActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {


    String URL="http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";

    SearchView searchView;
    ListView listView;
    List<News> newsList;
    List<News> newsListOriginal;
    ArrayAdapter<News> arrayAdapter;

    EditText editTextSearch;
    Button btnSearch;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       return  inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intializeViews(view);
        setQueryListener();
        setAdapter();


        loadData();
    }

    private void setAdapter() {

        newsList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<News>(requireContext(),R.layout.lyt_item,R.id.txt,newsList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news=newsList.get(i);
                Intent n=new Intent(requireContext(), DetailActivity.class);
                n.putExtra("News",news);
                startActivity(n);
            }
        });

    }

    private void intializeViews(@NonNull View view) {
        editTextSearch=view.findViewById(R.id.editTextSearch);
        btnSearch=view.findViewById(R.id.btnSearch);
        listView=view.findViewById(R.id.listView);
    }

    private void setQueryListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsList.clear();
                String query=editTextSearch.getText().toString();

                if(query.isEmpty())
                {

                    newsList.addAll(newsListOriginal);
                }
                else
                {

                    for(News news:newsListOriginal)
                    {
                        if(news.getTitle().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)))
                        {
                            newsList.add(news);
                        }

                    }

                }
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Method to load data from api
     * @param
     */
    private void loadData()
    {

        new AsyncTask<String,String,String>()
        {

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                progressDialog=new ProgressDialog(requireContext());
                progressDialog.show();
                showSnackBar("Loading Data");
            }

            @Override
            protected String doInBackground(String... voids) {


                String response="";
                try
                {


                    //response variable to store response in raw string
                    String inputLine = "";
                    java.net.URL url=new URL(URL);
                    URLConnection urlConnection=url.openConnection();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    //while there is data read the data
                    while ((inputLine = bufferedReader.readLine()) != null)
                    {
                        //Add new line in previous data
                        response = response + inputLine;
                    }

                    bufferedReader.close();
                }
                catch (IOException ae)
                {
                    ae.printStackTrace();
                }
                return response;
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(String response)
            {
                super.onPostExecute(response);
                progressDialog.dismiss();
                newsList.clear();
                newsListOriginal=parseNews(response);
                if(newsListOriginal!=null)
                {
                    newsList.addAll(newsListOriginal);
                }
                arrayAdapter.notifyDataSetChanged();

            }
        }.execute();

    }


    /**
     * Method to parse results
     * @param response
     */
    public List<News> parseNews(String response)
    {

        if(response.isEmpty())
        {
            showSnackBar("Error  : Check ur internet connection");
            return null;
        }

        //creating list to store roadworks
        List<News> newsList=new ArrayList<>();

        try
        {

            News news=null;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( response ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG )
                {

                    if(xpp.getName().equals("item") )
                    {
                        news=new News();
                    }

                    if(news ==null)
                    {
                        eventType = xpp.next();
                        continue;
                    }

                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("title"))
                    {
                        // Now just get the associated text

                        news.setTitle(xpp.nextText());
                    }
                    else
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("description"))
                        {

                            String description = xpp.nextText();
                            news.setDescription(description);

                        }
                        else //checking if tag is georss
                            if (xpp.getName().equalsIgnoreCase("link"))
                            {
                                //putting value in model class of link
                                news.setUrl(xpp.nextText());
                            }
                            else
                                // check with tga name
                                if (xpp.getName().equalsIgnoreCase("pubDate"))
                                {
                                    news.setPubDate(xpp.nextText());

                                }
                }


                //checking if end tag and tag name is item
                // it means our model class has all data it needs title,description etc
                // now we can add that into the list
                if(eventType == XmlPullParser.END_TAG  && xpp.getName().equals("item"))
                {

                    newsList.add(news);
                    news=null;
                }
                // Get the next event
                eventType = xpp.next();

            } // End of while
        }
        catch (XmlPullParserException | IOException ae1)
        {
            Log.e("ERROR",ae1.getMessage());
        }


        return newsList;
    }
    private void showToast(String msg) {

        Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show();
    }

    private void showSnackBar(String message) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),message, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

}