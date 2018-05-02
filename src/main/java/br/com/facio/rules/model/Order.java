package br.com.facio.rules.model;

/**
 *
 * @author fabiano
 */
public class Order {
    private Header header;

    private boolean automaticDebit;
    private boolean modemOwner;
    private int velocity;
    private double price;
    private double discount;

    public Order(Header header, boolean automaticDebit, boolean modemOwner, int velocity) {
        this.header = header;
        this.automaticDebit = automaticDebit;
        this.modemOwner = modemOwner;
        this.velocity = velocity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isAutomaticDebit() {
        return automaticDebit;
    }

    public void setAutomaticDebit(boolean automaticDebit) {
        this.automaticDebit = automaticDebit;
    }

    public boolean isModemOwner() {
        return modemOwner;
    }

    public void setModemOwner(boolean modemOwner) {
        this.modemOwner = modemOwner;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
    
    public void calculateAndSetPrice(double basePrice) {
        price = (basePrice + velocity) * (1-discount);
        System.out.println("Price for base price.: " + basePrice + " and discount " + discount + ", is " + price);
    }

    @Override
    public String toString() {
        return "Order{" + "header=" + header + ", automaticDebit=" + automaticDebit + ", modemOwner=" + modemOwner + ", velocity=" + velocity + ", price=" + price + ", discount=" + discount + '}';
    }
    
}
