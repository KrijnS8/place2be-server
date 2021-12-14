package nl.krijnschelvis.place2beserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/gathering")
public class GatheringController {
    @Autowired
    private GatheringRepository gatheringRepository;

    @PostMapping(path="/add-gathering")
    public @ResponseBody String addGathering(@RequestParam double latitude, @RequestParam double longitude, @RequestParam String city) {

        // Checks if any gatherings nearby exist
        Iterable<Gathering> gatheringIterable = gatheringRepository.findGatheringsByCity(city);
        for (Gathering g: gatheringIterable) {
            if (CalculateDistance.getDistance(latitude, longitude, g.getLatitude(), g.getLongitude()) < 200.0) {
                return "Failed: Gathering too close to another gathering";
            }
        }

        // Create gathering bean
        Gathering gathering = new Gathering();
        gathering.setLatitude(latitude);
        gathering.setLongitude(longitude);
        gathering.setCity(city);

        // Try to add gathering to database
        try {
            gatheringRepository.save(gathering);
        } catch (Exception e) {
            return "Failed: Can't add gathering to database";
        }
        return "Success: Gathering has been saved to the database";
    }

    @GetMapping(path="/get-all-gatherings")
    public @ResponseBody Iterable<Gathering> getAllGatherings(@RequestParam String city) {
        return gatheringRepository.findGatheringsByCity(city);
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
