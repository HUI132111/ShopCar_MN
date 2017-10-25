package com.bwie.aizhonghui.shopcar_mn;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bick.com.shoppingmodel.adapter.ShoppingCartActivity;
import com.bwie.aizhonghui.shopcar_mn.Adapter.MyAdapter;
import com.bwie.aizhonghui.shopcar_mn.Bean.MyBean;
import com.bwie.aizhonghui.shopcar_mn.Utils.OkUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,MyAdapter.Shopjiaxuan {
  private OkUtils okUtils;
  private String url="http://120.27.23.105/product/getCarts";
  private Map<String,String> map;
    private RecyclerView ry;
    private List<MyBean.DataBean> data;
    private MyAdapter ma;
    private Button money;
    private CheckBox cb;
    private TextView tvmoney;
    private double kk=0;
    private int shopid=0;
    private int shopzong=0;
    private List<Integer> listint;
    private  String format=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initOk();
    }

    private void initOk() {
        okUtils=new OkUtils();
        map=new HashMap<>();
        map.put("uid","97");
        okUtils.MyOk(url, map, new OkUtils.Callbacks() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String result = response.body().string();
                    Gson gson=new Gson();
                    MyBean myBean = gson.fromJson(result, MyBean.class);
                    data = myBean.getData();
                    for (MyBean.DataBean dataBean : data) {
                        shopzong=0;
                        shopid=0;
                        List<MyBean.DataBean.ListBean> list = dataBean.getList();
                        for (MyBean.DataBean.ListBean listBean : list) {
                            if(listBean.getSelected()==1){
                                shopid++;
                            }
                        }
                        shopzong+=list.size();
                        if(shopzong==shopid){
                            listint.add(1);
                        }else {
                            listint.add(0);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                         initSetData();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initSetData() {
        ma=new MyAdapter(MainActivity.this,data,listint);
        ma.setshopjiaxuan(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        ry.setLayoutManager(linearLayoutManager);
        ry.setAdapter(ma);
    }

    private void initView() {
        listint=new ArrayList<>();
        ry = (RecyclerView) findViewById(R.id.recycler_ry);
        cb = (CheckBox) findViewById(R.id.ckb_quan);
        money = (Button) findViewById(R.id.btn_money);
        tvmoney = (TextView) findViewById(R.id.tv_money);
        money.setOnClickListener(this);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kk=0;
                   AnySelect(data);
                }else {
                    kk=0;
                    AnyNormal(data);
                }
                listint.clear();
                OnlyShopHome(data);
                ma.notifyDataSetChanged();
                format = String.format("%.2f",kk);
                tvmoney.setText(format);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_money:
               startActivity(new Intent(MainActivity.this, ShoppingCartActivity.class));
                break;
        }
    }

    @Override
    public void shopjiadxuan(int poss) {
         kk=0;
        for (int i = 0; i <data.size() ; i++) {
            List<MyBean.DataBean.ListBean> list = data.get(poss).getList();
            for (MyBean.DataBean.ListBean listBean : list) {
                listBean.setSelected(1);
            }
        }
        //=======
        listint.clear();
        OnlyShopHome(data);
        Onlymoney(data);
        Handler handler=new Handler();
        Runnable r=new Runnable() {
            @Override
            public void run() {
                ma.notifyDataSetChanged();
            }
        };
        handler.post(r);

        format = String.format("%.2f",kk);
        tvmoney.setText(format);
        ///=========
    }

    @Override
    public void shopjiadbxuan(int poss) {
        kk=0;
        for (int i = 0; i <data.size() ; i++) {
            List<MyBean.DataBean.ListBean> list = data.get(poss).getList();
            for (MyBean.DataBean.ListBean listBean : list) {
                listBean.setSelected(0);
            }

        }
        //=========
        listint.clear();
        OnlyShopHome(data);
        Onlymoney(data);
        Handler handlere=new Handler();
        Runnable re=new Runnable() {
            @Override
            public void run() {
                ma.notifyDataSetChanged();
            }
        };
        handlere.post(re);
        format = String.format("%.2f",kk);
        tvmoney.setText(format);
        //=========
    }

    @Override
    public void shopdanxuan(int poss) {
        kk=0;
        for (MyBean.DataBean dataBean : data) {
            List<MyBean.DataBean.ListBean> list = dataBean.getList();
            for (MyBean.DataBean.ListBean listBean : list) {
                if(listBean.getPid()==poss){
                    listBean.setSelected(1);
                }
            }
        }
        listint.clear();
        OnlyShopHome(data);
        Onlymoney(data);
        ma.notifyDataSetChanged();
        format = String.format("%.2f",kk);
        tvmoney.setText(format);
    }

    @Override
    public void shopdanbxuan(int poss) {
        kk=0;
        for (MyBean.DataBean dataBean : data) {
            List<MyBean.DataBean.ListBean> list = dataBean.getList();
            for (MyBean.DataBean.ListBean listBean : list) {
                if(listBean.getPid()==poss){
                    listBean.setSelected(0);
                }
            }
        }
        listint.clear();
        OnlyShopHome(data);
        Onlymoney(data);
        ma.notifyDataSetChanged();
        format = String.format("%.2f",kk);
        tvmoney.setText(format);
    }

    @Override
    public void shopUpdate(int poss,int num) {
        for (MyBean.DataBean dataBean : data) {
            List<MyBean.DataBean.ListBean> list = dataBean.getList();
            for (MyBean.DataBean.ListBean listBean : list) {
                if(listBean.getPid()==poss){
                   listBean.setNum(num);
                }
            }
        }
        kk=0;
        Onlymoney(data);
        ma.notifyDataSetChanged();
        format = String.format("%.2f",kk);
        tvmoney.setText(format);
    }

    public void AnySelect(List<MyBean.DataBean> anylist){
        for (MyBean.DataBean dataBean : anylist) {
            List<MyBean.DataBean.ListBean> list = dataBean.getList();
            for (MyBean.DataBean.ListBean listBean : list) {
                kk+=listBean.getBargainPrice()*listBean.getNum();
                listBean.setSelected(1);
            }
        }
    }
    public void AnyNormal(List<MyBean.DataBean> anylist){
        for (MyBean.DataBean dataBean : anylist) {
            List<MyBean.DataBean.ListBean> list = dataBean.getList();
            for (MyBean.DataBean.ListBean listBean : list) {
                listBean.setSelected(0);
            }
        }
    }
    public void OnlyShopHome(List<MyBean.DataBean> Onlylist){
        for (MyBean.DataBean dataBean : Onlylist) {
            shopzong=0;
            shopid=0;
            List<MyBean.DataBean.ListBean> list = dataBean.getList();
            for (MyBean.DataBean.ListBean listBean : list) {
                if(listBean.getSelected()==1){
                    shopid++;
                }
            }
            shopzong=list.size();
            System.out.println(shopzong+"======"+shopid);
            if(shopzong==shopid){
                listint.add(1);
            }else {
                listint.add(0);
            }
        }
    }
    public void Onlymoney(List<MyBean.DataBean> Oylist){
        for (MyBean.DataBean dataBean : Oylist) {
            List<MyBean.DataBean.ListBean> list = dataBean.getList();
            for (MyBean.DataBean.ListBean listBean : list) {
                if(listBean.getSelected()==1){
                    kk+=listBean.getBargainPrice()*listBean.getNum();
                }
            }
        }
    }
}
