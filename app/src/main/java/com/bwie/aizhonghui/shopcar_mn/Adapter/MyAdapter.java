package com.bwie.aizhonghui.shopcar_mn.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bwie.aizhonghui.shopcar_mn.Bean.MyBean;
import com.bwie.aizhonghui.shopcar_mn.R;

import java.util.List;

/**
 * Created by DANGEROUS_HUI on 2017/10/24.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements MyAdaptertwo.ShopCheckbox{

    private Context context;
    private List<MyBean.DataBean> list;
    private List<Integer> listint;
    private SparseBooleanArray mCheckStates=new SparseBooleanArray();
    private Shopjiaxuan spjx;
    private int wk=0;

    public MyAdapter(Context context, List<MyBean.DataBean> list,List<Integer> listint) {
        this.context = context;
        this.list = list;
        this.listint=listint;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_one,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        System.out.println("===llll=="+listint.get(position));
        if(listint.get(position)==1){
            holder.ckbshop.setChecked(true);
        }else if(listint.get(position)==0){
            holder.ckbshop.setChecked(false);
        }
        holder.shopjia.setText(list.get(position).getSellerName());
        holder.ckbshop.setTag(position);
        List<MyBean.DataBean.ListBean> listBeen = this.list.get(position).getList();
        final MyAdaptertwo maw=new MyAdaptertwo(context,listBeen);
        maw.setShopCheckbox(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        holder.ry.setLayoutManager(linearLayoutManager);
        holder.ry.setAdapter(maw);
        //wk=0;
//        holder.ckbshop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                int pos= (int) compoundButton.getTag();
//                if(b){
//                  //mCheckStates.put(pos,true);
////                    spjx.shopjiadxuan(position);
//                }else {
//                    //mCheckStates.delete(pos);
////                    spjx.shopjiadbxuan(position);
//                }
//            }
//
//        });
        holder.ckbshop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    spjx.shopjiadxuan(position);
                }else {
                    spjx.shopjiadbxuan(position);
                }
            }
        });

//        holder.ckbshop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                wk++;
//                if(wk%2==1){
//                    spjx.shopjiadxuan(position);
//                }else if(wk%2==0){
//                    spjx.shopjiadbxuan(position);
//                }
//            }
//        });
           //holder.ckbshop.setChecked(mCheckStates.get(position,false));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void Shoptrue(int poss) {
        spjx.shopdanxuan(poss);
    }

    @Override
    public void Shopfalse(int poss) {
         spjx.shopdanbxuan(poss);
    }

    @Override
    public void Shopjiajian(int poss,int num) {
        spjx.shopUpdate(poss,num);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private   TextView shopjia;
        private   RecyclerView ry;
        private   CheckBox ckbshop;

        public MyViewHolder(View itemView) {
            super(itemView);
            shopjia = itemView.findViewById(R.id.tv_shopja);
            ry = itemView.findViewById(R.id.ry_shop);
            ckbshop = itemView.findViewById(R.id.ckb_shop);
        }
    }

    public void setshopjiaxuan(Shopjiaxuan spxj){
        this.spjx=spxj;
    }

    public interface  Shopjiaxuan{
        void shopjiadxuan(int poss);
        void shopjiadbxuan(int poss);
        void shopdanxuan(int poss);
        void shopdanbxuan(int poss);
        void shopUpdate(int poss,int num);
    }
}
