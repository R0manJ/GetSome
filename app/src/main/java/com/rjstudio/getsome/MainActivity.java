package com.rjstudio.getsome;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rjstudio.getsome.adapter.CnVPAdapter;
import com.rjstudio.getsome.fragment.ContentFragment;
import com.rjstudio.getsome.widget.CnToolbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager vp_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView()
    {
        CnToolbar toolbar = (CnToolbar) findViewById(R.id.toolbar);
        toolbar.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }
        });
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        List<ContentFragment> list = new ArrayList<ContentFragment>();
        for (int i = 0 ; i < 10 ; i++)
        {
            ContentFragment contentFragment = new ContentFragment();
            list.add(contentFragment);
        }

        CnVPAdapter cnVPAdapter = new CnVPAdapter(getSupportFragmentManager(),list);
        vp_content.setAdapter(cnVPAdapter);
    }
}
