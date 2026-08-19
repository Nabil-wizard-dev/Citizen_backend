package Nabil.Simplice.app.utils;

public final class GeoUtils {

    private static final double RAYON_TERRE_M = 6_371_000;

    // nombre de metres couverts par un degre de latitude
    private static final double METRES_PAR_DEGRE = 111_320.0;

    private GeoUtils() {
    }

    /**
     * Distance orthodromique entre deux points, en metres (formule de Haversine).
     */
    public static double distanceMetres(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        return RAYON_TERRE_M * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    /**
     * Demi-cote en degres de latitude du rectangle englobant un rayon donne.
     */
    public static double deltaLatitude(double rayonMetres) {
        return rayonMetres / METRES_PAR_DEGRE;
    }

    /**
     * Demi-cote en degres de longitude du rectangle englobant un rayon donne.
     * Depend de la latitude : les meridiens se resserrent vers les poles.
     */
    public static double deltaLongitude(double rayonMetres, double latitude) {
        double cos = Math.cos(Math.toRadians(latitude));
        // securite aux poles, ou le cosinus tend vers 0
        if (Math.abs(cos) < 1e-6) {
            return 180.0;
        }
        return rayonMetres / (METRES_PAR_DEGRE * cos);
    }
}
