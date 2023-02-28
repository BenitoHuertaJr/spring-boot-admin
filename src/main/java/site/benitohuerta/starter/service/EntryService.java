package site.benitohuerta.starter.service;


import site.benitohuerta.starter.entity.Entry;

import java.util.List;

public interface EntryService {
    void save(Entry entry);

    Entry getEntryBySlug(String entrySlug);

    List<Entry> findAll();

    Entry findById(Integer id);

    Entry update(Integer id, Entry entryDetails);

    void delete(Integer id);

}