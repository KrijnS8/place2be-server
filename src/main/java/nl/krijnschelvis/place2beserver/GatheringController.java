package nl.krijnschelvis.place2beserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/gathering")
public class GatheringController {
    @Autowired
    private GatheringRepository gatheringRepository;

    @GetMapping(path="/get-distance")
    public @ResponseBody double getDistance() {
        Gathering g1 = gatheringRepository.findGatheringById(1);
        Gathering g2 = gatheringRepository.findGatheringById(2);

        double d = CalculateDistance.getDistance(g1.getLatitude(), g1.getLongitude(), g2.getLatitude(), g2.getLongitude());
        return d;
    }
}

class CalculateDistance {

    // Radius of the earth in meters
    private static final int r = 6371000;

    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert degrees to radians
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // calculate the result
        return(c * r);
    }
}
