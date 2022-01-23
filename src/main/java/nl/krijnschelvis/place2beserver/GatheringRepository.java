package nl.krijnschelvis.place2beserver;

import org.springframework.data.repository.CrudRepository;

public interface GatheringRepository extends CrudRepository<Gathering, Integer>{
    Gathering findGatheringById(int id);
    Iterable<Gathering> findGatheringsByCity(String city);
    Iterable<Gathering> getGatheringsByIdIsNotNull();
}
