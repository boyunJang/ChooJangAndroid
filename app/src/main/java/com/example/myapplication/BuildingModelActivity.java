package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Vector;

public class BuildingModelActivity extends AppCompatActivity {

    final String[] this_data = {"MNIST"};
    private LinearLayout layout;
    int numLayer=0;
    private final int layer_id=0;
    Vector<Integer> node_num=new Vector<Integer>();
    Vector<Integer> activation_func=new Vector<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildingmodel);

        // "데이터 선택" 버튼을 눌렀을 때
        // 현재 데이터명(this_data[0])을 들고 DataSelectingActivity.class로 이동
        // DataSelectingActivity.class에서 받은 값을 this_data[0]에 저장 (onActivityResult() 참고)
        Button data_selection=(Button) findViewById(R.id.data_selection);
        data_selection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), DataSelectingActivity.class);
                intent.putExtra("dataname",this_data[0]);
                startActivityForResult(intent, 201);
            }
        });

        // "레이어 추가" 버튼을 눌렀을 때
        // addLayer() 실행
        Button add_layer=(Button)findViewById(R.id.add_layer);
        add_layer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                layout=(LinearLayout)findViewById(R.id.layout);
                addLayer();
            }
        });

        // "레이어 삭제" 버튼을 눌렀을 때
        // deleteLayer() 실행
        Button delete_layer=(Button)findViewById(R.id.delete_layer);
        delete_layer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                layout=(LinearLayout)findViewById(R.id.layout);
                deleteLayer();
            }
        });

        // "모델실행" 버튼을 눌렀을 때
        // 지금은 전체 데이터를 하나의 String으로 저장한 후 ResultActivity.class에서 보여지도록 했음
        // 추후 서버에 데이터를 보내는 코드로 변경해야 함
        Button run_model=(Button)findViewById(R.id.run_model);
        run_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ResultActivity.class);
                String[] activation_func_list=getResources().getStringArray(R.array.activation_func_list);
                String message="현재 사용 중인 데이터 : "+this_data[0]+"\n\n\n";
                for(int i=0;i<numLayer;i++){
                    message=message+"레이어 번호 "+Integer.toString(i+1)+"\n노드 수 : "+Integer.toString(node_num.get(i))+"\n활성함수 : "+activation_func_list[activation_func.get(i)]+"\n\n";
                }
                intent.putExtra("result", message);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // "데이터 선택" 버튼을 눌렀을 때 현재 class가 받은 데이터는 선택된 데이터임
        // 따라서 this_data[0]를 다른 class에서 받은 데이터명으로 변경
        if(requestCode==201){
            this_data[0]=data.getStringExtra("dataname");
            super.onActivityResult(requestCode, resultCode, data);
        }
        // 추가된 레이어 버튼을 눌렀을 때
        else if(requestCode==202){
            int this_layer_num=data.getIntExtra("layernum",0);
            node_num.set(this_layer_num-1,data.getIntExtra("nodenum", 1));
            activation_func.set(this_layer_num-1,data.getIntExtra("activationfunction",1));
        }
    }

    private void addLayer(){
        numLayer++;

        final int button_num=numLayer;
        node_num.add(1);
        activation_func.add(1);

        Button layer=new Button(this);
        layer.setId(button_num);
        layer.setText("레이어 "+button_num);

        layout.addView(layer, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        layer.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), LayerOptionActivity.class);
                intent.putExtra("layernum",button_num);
                intent.putExtra("nodenum", node_num.get(button_num-1));
                intent.putExtra("activationfunction", activation_func.get(button_num-1));
                startActivityForResult(intent, 202);
            }
        });
    }

    private void deleteLayer(){
        if(numLayer<=0){
            return;
        }
        Button removing_layer=(Button)findViewById(numLayer);
        layout.removeView(removing_layer);
        numLayer--;
        node_num.remove(numLayer);
        activation_func.remove(numLayer);
    }
}