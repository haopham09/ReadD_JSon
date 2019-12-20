package com.thohao.readjson;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    String mURL = "https://khoapham.vn/KhoaPhamTraining/json/tien/demo1.json";
    ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImg=findViewById(R.id.imageview);

        //Inner class nen ta khai bao la new ReadJSon();
        new ReadJSon().execute(mURL);
    }

    //ve nha làm với RxJava
    //Dung Async để làm giao dien dep, nhung rất chậm, dc Thay thế bằng Retrofit
    class ReadJSon extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("CCC", "bat dau doc du lieu");
        }

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doc file json
            //convert sang Json object
            try {
                JSONObject jsonObject=new JSONObject(s);
//                String monhoc = jsonObject.getString("monhoc");
                //Dùng optString để bỏ qua dữ liệu kg có key or key kg đúng.
                String monhoc = jsonObject.optString("monhoc");
                String noihoc = jsonObject.optString("noihoc");
                String website = jsonObject.optString("website");
                String logo = jsonObject.optString("logo");
                /*Log.d("CCC", website);
                Log.d("CCC", monhoc);
                Log.d("CCC", noihoc);
                Log.d("CCC", logo);*/

                Glide
                        .with(MainActivity.this)
                        .load(logo)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.ic_launcher_background)// neu load hình bị lỗi
                        .into(mImg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("CCC", s);
        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();//de biet thong tin truyen qua la gi
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }

    //Làm các demo https://khoapham.vn/KhoaPhamTraining/json/tien/demo1.json từ demo1-5
}
