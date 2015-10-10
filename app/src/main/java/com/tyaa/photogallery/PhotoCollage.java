package com.tyaa.photogallery;

        import org.json.JSONObject;

        /**
 + * Created by Павел on 27.09.2015.
 + */
        public class PhotoCollage extends Collage{
        public PhotoCollage(){
            }
        public PhotoCollage(int id, String pagetitle, String alias, String caption) {
                this.id = id;
                this.pagetitle = pagetitle;
                this.alias = alias;
                this.caption = caption;
            }
        public static PhotoCollage fromJson(JSONObject object) {
                int id = object.optInt("id", 0);
                String pagetitle= object.optString("pagetitle","");
                String alias= object.optString("alias", "");
                String caption= object.optString("caption", "");
                return new PhotoCollage(id,pagetitle,alias,caption);
            }
    }