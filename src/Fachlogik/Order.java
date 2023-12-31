package Fachlogik;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    //static int x;

    int id;
    //Customer customer;
   //List<CartItem> cartItems;
    Cart cart;
    String status;
    boolean isBouquet;

    public Order(int x,String s,Cart cart,boolean isBouquet){
        this.id = x;
        //this.cartItems = new ArrayList<CartItem>();
        this.status = s;
        this.cart=cart;
        this.isBouquet = isBouquet;
    }


    void putAsBouquet() {
        isBouquet=true;
    }

    void viewOrder() {
        System.out.println("Order details:");
        System.out.println("ID: " + getId());
        //System.out.println("Customer: " +customer.getName());
        System.out.println("Status: " + getStatus());
        System.out.println("Is bouquet: " + isBouquet);
        for (CartItem cartItem : cart.cartItems) {
            System.out.println(cartItem);
        }
    }
    
    public List<CartItem> getOrderItems(){
        return cart.cartItems;
    }
        

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

   /* public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
*/
    
    public String getBouqute(){
        if(isBouquet){
            return "Yes";
        }else{
            return "No";
        }
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isBouquet() {
        return isBouquet;
    }

    public void setBouquet(boolean bouquet) {
        isBouquet = bouquet;
    }
    
    public double getPrice(){
        double price = 0.0;
        for (CartItem cartItem : cart.cartItems) {
            price+= cartItem.getPrice()*cartItem.getQuantity();
        }
        return price;
    }
    public String toString(){
        return "The Order with Id "+getId()+" has been "+getStatus();
    }

}
