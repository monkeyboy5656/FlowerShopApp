package Fachlogik;

import java.io.Serializable;
import java.util.Comparator;

public class Owner extends User implements Serializable {

    private Warehouse wh;

    public Owner(String name, String email, String password, Warehouse wh) {
        super(name, email, password);
        this.wh = wh;
    }

    /* public Shop getShop() {
        return shop;
    }



    public void setShop(Shop shop) {
        this.shop = shop;
    }

     */
    public void addProduct(Product product) {
        wh.addProduct(product);
    }

    public void removeProduct(Product product) {
        if (product != null) {
            wh.removeProduct(product);
        } else {
            System.out.println("There is no such Product to remove");
        }
    }

    public void updateProduct(Product product, int quan) {
        if (product != null) {
            wh.updateProduct(product, quan);
        } else {
            System.out.println("There is no such Product to update");
        }

    }

    public void viewWarehouse() {
        if (wh.products.isEmpty()) {
            System.out.println("There are no products yet");
        } else {
            for (Product a : wh.products) {
                System.out.println(a);
            }
        }
    }

    public Warehouse getWh() {
        return wh;
    }

    public void sortByType() {
        wh.sortByType();
    }

    public void sortByID() {
        wh.sortByID();

    }
}
