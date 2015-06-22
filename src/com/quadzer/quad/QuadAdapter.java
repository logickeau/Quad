package com.quadzer.quad;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.quadzer.quad.R.drawable;


import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class QuadAdapter extends ArrayAdapter<Quad> {
	LinearLayout imagecontainer;
        private List<Quad> itemList;
        private Context context;
                
        public QuadAdapter(List<Quad> itemList, Context ctx) {
                super(ctx, R.layout.quad, itemList);
                this.itemList = itemList;
                this.context = ctx;                
        }
        
        public int getCount() {
                if (itemList != null)
                        return itemList.size();
                return 0;
        }

        public Quad getItem(int position) {
                if (itemList != null)
                        return itemList.get(position);
                return null;
        }

        public long getItemId(int position) {
                if (itemList != null)
                        return itemList.get(position).hashCode();
                return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
             convertView = null;  
               
        	if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.quad, null);
               }
                
                
                Quad c = itemList.get(position);
                TextView promoter_title = (TextView) convertView.findViewById(R.id.promoter_title);
                promoter_title.setText(android.text.Html.fromHtml(c.getPromoter()).toString());

                TextView quad_title = (TextView) convertView.findViewById(R.id.quad_title);
                quad_title.setText(android.text.Html.fromHtml(c.getTitle()).toString());
                
                 
                
                imagecontainer = (LinearLayout) convertView.findViewById(R.id.imageContainer);

                //get the performer arrays
                JSONArray performers = c.getPerformers();
                
                 for (int i=0; i < performers.length(); i++) {
                 	try {
						JSONObject performer = performers.getJSONObject(i);
						
						//ImageView image = new ImageView(context);
			               
			               String url_image_string = "http://www.quadzer.com/img/performers/"+ performer.getString("photoname");
			               new DownloadTask(url_image_string).execute();
			               //image.setImageDrawable(LoadImageFromWebOperations(url_image_string));
			               
			               //image.setImageResource(R.drawable.tvice);
			               //image.setScaleType(ImageView.ScaleType.FIT_CENTER);
			               //LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
			                  ///     LayoutParams.WRAP_CONTENT,
			                   //    LayoutParams.WRAP_CONTENT, 0.5f);
			               //image.setLayoutParams(param);
			                //imagecontainer.addView(image,param);

						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                
                 }
                
                
                
                
                
                
                

                TextView venuetitle = (TextView) convertView.findViewById(R.id.venueTitle);
                
                venuetitle.setText(android.text.Html.fromHtml(c.getVenuetitle()).toString());

                TextView address1 = (TextView) convertView.findViewById(R.id.address1);
                address1.setText(c.getAddress1());

                return convertView;
                
        }

        public List<Quad> getItemList() {
                return itemList;
        }

        public void setItemList(List<Quad> itemList) {
                this.itemList = itemList;
        }

        public static Drawable LoadImageFromWebOperations(String url) {
        	
        	Log.v("IMAGE:",url);
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e) {
                return null;
            }
        }
        
        private class DownloadTask extends AsyncTask<Void, Void, Drawable>{

            private final String mUrl;
            public DownloadTask(String... url){
                mUrl = url[0];
            }

            protected Drawable doInBackground(Void... params) {
                try {
                    return Drawable.createFromStream((InputStream)new URL(mUrl).getContent(), "src");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Drawable result){
                //((ImageView)findViewById(R.id.imageView1)).setImageDrawable(result);
            	ImageView image = new ImageView(context);
            	Random r = new Random();
            	int tsLong = (int) ((System.currentTimeMillis()/1000) + r.nextInt(99));
            	Log.v("IMAGEVIEW ID:",Integer.toString(tsLong));
            	image.setId(tsLong);
            	image.setImageDrawable(result);
            	//image.setScaleType(ImageView.ScaleType.FIT_CENTER);
	               LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
	                       LayoutParams.WRAP_CONTENT,
	                       LayoutParams.WRAP_CONTENT, 0.5f);
	            image.setLayoutParams(param);
	            imagecontainer.addView(image,param);
            }
        
        }   
}