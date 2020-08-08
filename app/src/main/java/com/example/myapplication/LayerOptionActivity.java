package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

public class LayerOptionActivity extends AppCompatActivity {

    String[] activation_func_list;
    int layer_num_get;
    int node_num_send;
    int activationfunction_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layeroption);

        Intent intent=new Intent(this.getIntent());

        layer_num_get=intent.getIntExtra("layernum", 0);
        final int node_num_get=intent.getIntExtra("nodenum", 0);
        node_num_send=node_num_get;
        final int activationfunction_get=intent.getIntExtra("activationfunction", 0);
        activationfunction_send=activationfunction_get;

        TextView layer_num=(TextView)findViewById(R.id.layer_num);
        layer_num.setText("레이어 번호 : "+layer_num_get);

        final EditText node_num=(EditText)findViewById(R.id.node_num);
        node_num.setText(""+node_num_get);

        activation_func_list=getResources().getStringArray(R.array.activation_func_list);
        ArrayAdapter<String> act_func_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, activation_func_list);
        final Spinner activation_func=(Spinner)findViewById(R.id.activation_func);
        activation_func.setAdapter(act_func_adapter);
        activation_func.setSelection(activationfunction_get);


        Button setting_complete=(Button)findViewById(R.id.setting_complete);
        setting_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                node_num_send=Integer.parseInt(node_num.getText().toString());
                String activationfunction_send_string=activation_func.getSelectedItem().toString();
                activationfunction_send=Arrays.asList(activation_func_list).indexOf(activationfunction_send_string);
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent setting_complete_intent=new Intent();
        setting_complete_intent.putExtra("layernum",layer_num_get);
        setting_complete_intent.putExtra("nodenum",node_num_send);
        setting_complete_intent.putExtra("activationfunction",activationfunction_send);
        setResult(RESULT_OK, setting_complete_intent);
        finish();
        super.onBackPressed();
    }
}