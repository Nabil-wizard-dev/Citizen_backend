package Nabil.Simplice.app.dto.request;

public class GeolocalisationRequest {
    private String longitude;
    private String latitude;

    // Getters et setters
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
