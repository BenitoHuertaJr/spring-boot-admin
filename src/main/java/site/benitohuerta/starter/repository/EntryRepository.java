package site.benitohuerta.starter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.benitohuerta.starter.entity.Entry;

@Repository
public interface EntryRepository extends CrudRepository<Entry, Integer> {

    @Query(value = "SELECT * FROM entries WHERE slug = :entrySlug", nativeQuery = true)
    public Entry getEntryBySlug(@Param("entrySlug") String entrySlug);

}