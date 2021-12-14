package nl.krijnschelvis.place2beserver;

import javax.persistence.*;

@Table(name= "gathering")
@Entity
public class Gathering {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="latitude")
    private double latitude;

    @Column(name="longitude")
    private double longitude;

    @Column(name="city")
    private String city;

    public Gathering() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
