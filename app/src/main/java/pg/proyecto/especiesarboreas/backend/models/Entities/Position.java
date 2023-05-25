package pg.proyecto.especiesarboreas.backend.models.Entities;

import java.io.Serializable;

public class Position implements Serializable {
    private float lat;
    private float lng;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
