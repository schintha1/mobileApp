package com.example.copdatdrawer;

/**
 * Created by weiyu on 3/24/2018.
 */

public class Products {
    private int id;
    private String name;
    private int num;
    private String pic;
    private String vendor;
    private int  size;
    private String color;
    private int price;
    public Products(){
        id = 0;
        name ="";
        num = 0;
        pic ="";
        vendor = "";
        size = 0;
        color="";
        price = 0;
    }
    public  int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getNum(){
        return num;
    }
    public String getPic(){
        return pic;
    }
    public String getVendor(){
        return vendor;
    }
    public int getSize(){
        return size;
    }
    public String getColor(){
        return color;
    }
    public void setId(int id){
        this.id =id;
    }
    public void setName(String name){
        this.name =name;
    }
    public void setNum(int num){
        this.num =num;
    }
    public void setPic(String pic){
        this.pic =pic;
    }
    public void setVendor(String vendor){
        this.vendor = vendor;
    }
    public void setSize(int size){
        this.size =size;
    }
    public void setColor(String color){
        this.color = color;
    }


}
