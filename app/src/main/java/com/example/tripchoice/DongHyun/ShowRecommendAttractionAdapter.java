package com.example.tripchoice.DongHyun;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripchoice.R;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ShowRecommendAttractionAdapter extends RecyclerView.Adapter<ShowRecommendAttractionAdapter.ItemViewHolder> {
    private static Context context;
    private ArrayList<attraction> mdata = new ArrayList<>();
    private InputStream is;
    private ArrayList<detail> asynclist = new ArrayList<detail>();

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_list_item, parent, false);
        this.context = parent.getContext();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShowRecommendAttractionAdapter.ItemViewHolder holder, int position) {
        holder.onBind(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public void addItem(attraction data){
        mdata.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name, address;
        private ImageView imageView;
        private attraction data;
        private ArrayList<detail> list = new ArrayList<>();

        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.attraction_name);
            address = itemView.findViewById(R.id.attraction_address);
            imageView = itemView.findViewById(R.id.attraction_imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailedInform detailedInform = new DetailedInform();
                    try {
                        list.clear();
                        list = (ArrayList<detail>)detailedInform.execute(mdata.get(getAdapterPosition()).contentid).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ViewGroup viewGroup = (ViewGroup) v.getParent();
                    viewGroup.removeView(v);

                    View layoutInflater = LayoutInflater.from(context).inflate(R.layout.detailattraciondialog, viewGroup, false);

                    AlertDialog.Builder builder = new AlertDialog.Builder(layoutInflater.getContext());
                    builder.setView(layoutInflater);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    TextView title = layoutInflater.findViewById(R.id.detailattractiontitle);
                    TextView overview = layoutInflater.findViewById(R.id.detailattractiondetail);
                    ImageView imageView = layoutInflater.findViewById(R.id.detailattractionimageview);
                    Button ok = layoutInflater.findViewById(R.id.detailattractionbutton);

                    title.setText(list.get(getAdapterPosition()).title);
                    overview.setText(list.get(getAdapterPosition()).overview);
                    imageView.setImageBitmap(list.get(getAdapterPosition()).bitmap);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
            });
        }

        private void onBind(attraction data){
            this.data = data;

            name.setText(data.title);
            address.setText(data.address);
            imageView.setImageBitmap(data.bitmap);
        }
    }

    private class DetailedInform extends AsyncTask<Integer, Integer, ArrayList> {

        @Override
        protected ArrayList doInBackground(Integer... integers) {
            int id = integers[0];
            StringBuffer buffer = new StringBuffer();
            ArrayList<detail> detailArrayList = new ArrayList<>();

            String mykey = "TFen3YAZ3qSYyKQmw689xVagwAxX/d+rbZXBO09a2orzWmKd4qh7cnNxgpkVRmDtkHANsVzCNfNVl90x5ES/kg==";
            StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon"); /*URL*/
            try {
                urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + mykey); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode(mykey, "UTF-8")); /*공공데이터포털에서 발급받은 인증키*/
                urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
                urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재 페이지 번호*/
                urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰), ETC*/
                urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
                urlBuilder.append("&" + URLEncoder.encode("contentId","UTF-8") + "=" + URLEncoder.encode(String.valueOf(id), "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("defaultYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("firstImageYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("areacodeYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("catcodeYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("addrinfoYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("mapinfoYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("overviewYN", "UTF-8") + "=" + URLEncoder.encode("Y","UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            URL url = null;
            try {
                url = new URL(urlBuilder.toString());
                is = url.openStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Content-type", "application/json");
            try {
                Log.d("tag", "Response code: " + conn.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader rd = null;
            try {
                if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            String line = "";
            while (true) {
                try {
                    if ((line = rd.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append(line);
            }
            try {
                rd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            line = sb.toString();
            Log.d("tag", line);
            conn.disconnect();

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;
                String title = "", overview = "";
                Bitmap bitmap = null;

                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    switch(eventType){
                        case XmlPullParser.START_TAG:

                            if(xpp.getName().equals("title")){
                                buffer.append("제목: ");
                                xpp.next();
                                title = xpp.getText();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if(xpp.getName().equals("firstimage")){
                                buffer.append("사진1: ");
                                xpp.next();

                                try{
                                    URL image  = new URL(xpp.getText());
                                    URLConnection connection = image.openConnection();
                                    connection.connect();

                                    int size = connection.getContentLength();
                                    BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream(), size);
                                    bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if(xpp.getName().equals("overview")){
                                buffer.append("설명: ");
                                xpp.next();
                                overview = xpp.getText();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }

                            break;
                        case XmlPullParser.TEXT:

                            break;

                        case XmlPullParser.END_TAG:
                            tag = xpp.getName();

                            if(title != "" && overview != "" && bitmap != null){
                                asynclist.add(new detail(title, bitmap, overview));
                                title = "";
                                bitmap = null;
                                overview = "";
                            }

                            if(tag.equals("item")) buffer.append("\n");
                            break;
                    }

                    eventType = xpp.next();
                }

            } catch (XmlPullParserException | UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("detail", String.valueOf(buffer));
            // 리스트에 제목,주소,사진,위도,경도 저장했으므로 recyclerview에 표시하면 끝
            return asynclist;
        }
    }

    private class detail{
        String title;
        Bitmap bitmap;
        String overview;

        public detail(String t, Bitmap b, String overview){
            this.title = t;
            this.bitmap = b;
            this.overview = overview;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }
    }
}
