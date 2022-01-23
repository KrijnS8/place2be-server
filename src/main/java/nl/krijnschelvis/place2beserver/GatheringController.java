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
    public @ResponseBody Gathering addGathering(@RequestParam double latitude, @RequestParam double longitude,
                                             @RequestParam String street, @RequestParam String postalCode,
                                             @RequestParam String city, @RequestParam String state,
                                             @RequestParam String country) {

        // Checks if any gatherings nearby exist
        Iterable<Gathering> gatheringIterable = gatheringRepository.findGatheringsByCity(city);
        for (Gathering g: gatheringIterable) {
            if (CalculateDistance.getDistance(latitude, longitude, g.getLatitude(), g.getLongitude()) < 200.0) {
                return new Gathering();
            }
        }

        // Create gathering bean
        Gathering gathering = new Gathering();
        gathering.setLatitude(latitude);
        gathering.setLongitude(longitude);
        gathering.setStreet(street);
        gathering.setPostalCode(postalCode);
        gathering.setCity(city);
        gathering.setState(state);
        gathering.setCountry(country);

        // Try to add gathering to database
        try {
            gatheringRepository.save(gathering);
        } catch (Exception e) {
            return new Gathering();
        }
        return gathering;
    }

    @GetMapping(path="/get-all-gatherings")
    public @ResponseBody Iterable<Gathering> getAllGatheringsInCity() {
        // Return Iterable of all gatherings
        return gatheringRepository.getGatheringsByIdIsNotNull();
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
