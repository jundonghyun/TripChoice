package com.example.tripchoice.DongHyun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripchoice.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.poi_item.TMapPOIItem;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AttractionMainActivity extends AppCompatActivity {
    private EditText input_position;
    private Button search;
    private RecommendAttractionAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<RecommendAttractionData> mdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("관광지추천");
        actionBar.setDisplayHomeAsUpEnabled(true);



        input_position = findViewById(R.id.input_position);
        input_position.setFilters(new InputFilter[]{filterKoEnNum});
        search = findViewById(R.id.search_button);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mdata.clear();
                imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                String t = input_position.getText().toString().trim();

                TMapData tMapData = new TMapData();
                TMapTapi tmaptapi = new TMapTapi(AttractionMainActivity.this);
                tmaptapi.setSKTMapAuthentication ("l7xxbbb12b800ea54d19bf35aa65f0c1980c");
                tMapData.findAllPOI(t, new TMapData.FindAllPOIListenerCallback() {
                    @Override
                    public void onFindAllPOI(ArrayList<TMapPOIItem> arrayList) {
                        for (int i = 0; i < arrayList.size(); i++) {
                            mdata.add(new RecommendAttractionData(arrayList.get(i).getPOIName(), arrayList.get(i).getPOIAddress(),
                                    arrayList.get(i).getPOIPoint().getLatitude(),
                                    arrayList.get(i).getPOIPoint().getLongitude()));
                            Log.d("로그", arrayList.get(i).getPOIName());
                        }
                    }
                });
                try {
                    Thread.sleep(1000);
                    init();
                    addItem(mdata);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    protected InputFilter filterKoEnNum = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$");

            if (!ps.matcher(source).matches()) {
                Toast.makeText(AttractionMainActivity.this, "특수문자는 허용되지않습니다", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;
        }
    };

    private void init() {
        recyclerView = findViewById(R.id.view_recommend_attraction);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(AttractionMainActivity.this,  DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        adapter = new RecommendAttractionAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void addItem(ArrayList<RecommendAttractionData> data){
        for(RecommendAttractionData d : data){
            adapter.addItem(d);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}