package br.com.facio.rules.model;

/**
 *
 * @author fabiano
 */
public class Header {
    
    private String country;
    private String state;
    private String city;

    public Header(String country, String state, String city) {
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Header{" + "country=" + country + ", state=" + state + ", city=" + city + '}';
    }
    
}
