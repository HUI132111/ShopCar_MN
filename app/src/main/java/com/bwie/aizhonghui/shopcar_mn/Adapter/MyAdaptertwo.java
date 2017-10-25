package com.bwie.aizhonghui.shopcar_mn.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.aizhonghui.shopcar_mn.Bean.MyBean;
import com.bwie.aizhonghui.shopcar_mn.R;
import com.bwie.aizhonghui.shopnum.AmountView;

import java.util.List;

/**
 * Created by DANGEROUS_HUI on 2017/10/24.
 */

public class MyAdaptertwo extends RecyclerView.Adapter<MyAdaptertwo.MyViewHoldertwo>{
    private Context context;
    private List<MyBean.DataBean.ListBean> list;
    private ShopCheckbox shopCheckbox;
    private int zkk=0;

    public MyAdaptertwo(Context context, List<MyBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHoldertwo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_two,null);
        MyViewHoldertwo myViewHoldertwo=new MyViewHoldertwo(view);
        return myViewHoldertwo;
    }

    @Override
    public void onBindViewHolder(final MyViewHoldertwo holder, final int position) {
               if(list.get(position).getSelected()==1){
                   holder.ckbdan.setChecked(true);
               }else if(list.get(position).getSelected()==0){
                   holder.ckbdan.setChecked(false);
               }
               holder.title.setText(list.get(position).getTitle());
               holder.price.setText(list.get(position).getBargainPrice()+"");
               holder.amountView.setGoods_storage(50);
               holder.amountView.setAmount(list.get(position).getNum());
               holder.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                   @Override
                   public void onAmountChange(View view, int amount) {
                       Toast.makeText(context,"===kkk==="+amount,Toast.LENGTH_SHORT).show();
                       shopCheckbox.Shopjiajian(list.get(position).getPid(),amount);
                   }
               });
              String images = list.get(position).getImages();
              String[] split = images.split("\\|");
        System.out.println("==="+split[0]);
        Glide.with(context)
                .load(split[0])
                .into(holder.img);
               holder.ckbdan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                       if(b){
                           shopCheckbox.Shoptrue(list.get(position).getPid());
                       }else {
                           shopCheckbox.Shopfalse(list.get(position).getPid());
                       }
                   }
               });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHoldertwo extends RecyclerView.ViewHolder{

        private   TextView title;
        private   TextView price;
        private   ImageView img;
        private   CheckBox ckbdan;
        private   AmountView amountView;

        public MyViewHoldertwo(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            price = itemView.findViewById(R.id.tv_price);
            img = itemView.findViewById(R.id.iv_img);
            ckbdan = itemView.findViewById(R.id.ckb_dan);
            amountView = itemView.findViewById(R.id.amount_view);
        }
    }

    public void setShopCheckbox(ShopCheckbox shopCheckbox){
        this.shopCheckbox=shopCheckbox;
    }

    public interface ShopCheckbox{
        void Shoptrue(int poss);
        void Shopfalse(int poss);
        void Shopjiajian(int poss,int num);
    }
}
