package com.quadzer.quad;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;

import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity {
        private QuadAdapter adpt;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        adpt  = new QuadAdapter(new ArrayList<Quad>(), this);
        ListView lView = (ListView) findViewById(android.R.id.list);
        
        lView.setAdapter(adpt);
        
        // Exec async load task
        (new AsyncListViewLoader()).execute("http://www.quadzer.com/quads/browse.json");
    }


    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Quad>> {
            private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            
                @Override
                protected void onPostExecute(List<Quad> result) {                        
                        super.onPostExecute(result);
                        dialog.dismiss();
                        adpt.setItemList(result);
                        adpt.notifyDataSetChanged();
                }

                @Override
                protected void onPreExecute() {                
                        super.onPreExecute();
                        dialog.setMessage("Downloading Events...");
                        dialog.show();                        
                }

                @Override
                protected List<Quad> doInBackground(String... params) {
                        List<Quad> result = new ArrayList<Quad>();
                        
                        try {
                                URL u = new URL(params[0]);
                                
                                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                                conn.setRequestMethod("GET");
                                
                                conn.connect();
                                InputStream is = conn.getInputStream();
                                
                                
                                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(is));
                                String line;
                                String JSONRes = "";
                                while((line = bufferedReader.readLine()) != null)
                                	JSONRes += line;
                         
                                is.close();
                         
                                Log.v("JSON RESPONSE:",JSONRes);
                                
                               JSONArray arr = new JSONArray(JSONRes);
                               
                               Log.v("JSON-ARRAY:",arr.toString());
                               
                                for (int i=0; i < arr.length(); i++) {
                                       result.add(convertQuad(arr.getJSONObject(i)));
                                       Log.v("JSON CONVERT:",result.toString());
                               }
                           
                                
                                return result;
                        }
                        catch(Throwable t) {
                                t.printStackTrace();
                        }
                        return null;
                }
                
                private Quad convertQuad(JSONObject obj) throws JSONException {
                	
               
                	    JSONObject quad = obj.getJSONObject("Quad");
                        String promoter = quad.getString("promoter");
                        String title = quad.getString("title");
                        
                        JSONArray performers = obj.getJSONArray("Performer");
                       
                        
                        
                        JSONArray venueArray = obj.getJSONArray("Venue");
                        JSONObject venue = venueArray.getJSONObject(0);
                        String venuename = venue.getString("name");
                        String address1 = venue.getString("address1");
                        String city = venue.getString("city");
                        
                        return new Quad(promoter, title,performers, venuename, address1, city);
                }
            
    }
    
    
    
}