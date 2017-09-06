package com.lenovohit.actionsheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnPopupActionSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPopupActionSheet = (Button) findViewById(R.id.btnPopupActionSheet);

        initEvent();
    }

    private void initEvent() {
        btnPopupActionSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionSheet.makeActionSheet(btnPopupActionSheet,R.layout.custom_action_sheet,"添加图片").show()
                        .registerItemClick(new ActionSheet.IItemClick() {
                    @Override
                    public void onItemClick(int index) {
                        Toast.makeText(MainActivity.this,"第"+index+"个按钮",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
