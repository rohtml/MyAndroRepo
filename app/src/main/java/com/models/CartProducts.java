package com.models;

import java.util.ArrayList;

public class CartProducts {
    private ArrayList<AllProductsResponse> cartItems = new ArrayList<AllProductsResponse>();

    public AllProductsResponse getProductsList(AllProductsResponse cartItems){

        return cartItems;


    }

    public AllProductsResponse getProducts(int position){

        return cartItems.get(position);


    }

    public void setProducts(AllProductsResponse Products){

        cartItems.add(Products);

    }

    public int getCartsize(){

        return cartItems.size();

    }

    public boolean CheckProductInCart(AllProductsResponse aproduct){


        return cartItems.contains(aproduct);


    }

    public void removeProduct(AllProductsResponse prodcut){
        cartItems.remove(prodcut);
    }
    public int getTotalCartPrice() {
        return totalCartPrice;
    }

    int totalCartPrice;

    public int addCartPrice(int price){
        return totalCartPrice + price;
    }
}
