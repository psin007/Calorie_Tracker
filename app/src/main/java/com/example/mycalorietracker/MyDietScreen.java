package com.example.mycalorietracker;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class MyDietScreen  extends Fragment {
    View vDietScreen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDietScreen = inflater.inflate(R.layout.mydietscreen, container, false);
        final EditText edittext = (EditText) vDietScreen.findViewById(R.id.searchfood);
        final Button btnSearch = (Button) vDietScreen.findViewById(R.id.btnFind);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     String foodToSearch = edittext.getText().toString();

                     SearchGoogleSnippet searchSnippet = new SearchGoogleSnippet();
//                     SearchImageLink searchImageLink = new SearchImageLink();
                     searchSnippet.execute(foodToSearch);
//                     searchImageLink.execute(foodToSearch);
                     DownloadImage dimage = new DownloadImage((ImageView)vDietScreen.findViewById(R.id.imageSearch));
                     dimage.execute(foodToSearch);
            }
        });
        return vDietScreen;
    }

    private class SearchGoogleSnippet extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.searchResult(params[0],new String[]{"num"},new String[]{"1"});
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textview = (TextView) vDietScreen.findViewById(R.id.tvResult);
            textview.setText(SearchGoogleAPI.getFormattedSnippet(result));
        }
    }
////to give credit to source of image
//    private class SearchImageLink extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            return SearchGoogleAPI.searchResult(params[0], new String[]{"num", "searchType"}, new String[]{"1", "image"});
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//            TextView tv = (TextView) vDietScreen.findViewById(R.id.sourceImage);
//            tv.setText(SearchGoogleAPI.getImageLink(result));
//        }
//    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImage(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getActivity(), "Please wait, it may take sometime...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... params) {
            String result = SearchGoogleAPI.searchResult(params[0], new String[]{"num", "searchType"}, new String[]{"1", "image"});
            String imageLink = SearchGoogleAPI.getImageLink(result);
            Bitmap image = null;
            try {
                InputStream in = new java.net.URL(imageLink).openStream();
                image = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return image;
        }

        protected void onPostExecute(Bitmap result) {

            imageView.setImageBitmap(result);
        }
    }

}
