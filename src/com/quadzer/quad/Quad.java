package com.quadzer.quad;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Quad implements Serializable {
        
	    private String promoter;    
	    private String title;
	    private JSONArray performers;
	    //private String[] performer_images;
        private String venuename;
        private String address1;
        private String city;
        
        
        public Quad(String promoter,String title,JSONArray performers, String venuename, String address1, String city) 
          {
                super();
                this.promoter = promoter;
                this.title = title;
                this.performers = performers;
                this.venuename = venuename;
                this.address1 = address1;
                this.city = city;
        }

        
      


		public String getPromoter() {
            return promoter;
    }
        
        public void setPromoter(String promoter) {
            this.promoter =  promoter;
    }
        
        public String getTitle() {
                return title;
        }
        
        public void setTitle(String title) {
            this.title =  title;
    }  
     
//Performer names        
        public JSONArray getPerformers() {
            return performers;
    }
      
        
        public void setPerformers(JSONArray performers) {
            this.performers =  performers;
    }
        
      
        public String getVenuetitle() {
            return venuename;
    }
        
        
        public void setVenuetitle(String venuetitle) {
                this.venuename = venuetitle;
        }

        public String getAddress1() {
                return address1;
        }
        
        
        public void setAddress1(String address1) {
            this.address1= address1;
    }
        
        public String getCity() {
            return city;
    }
    
    
    public void setCity(String address1) {
        this.city= address1;
}
}