package site.benitohuerta.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import site.benitohuerta.starter.entity.Entry;
import site.benitohuerta.starter.entity.Role;
import site.benitohuerta.starter.repository.EntryRepository;
import site.benitohuerta.starter.repository.RoleRepository;
import site.benitohuerta.starter.utils.Slug;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public void save(Entry entry) {

        entry.setSlug(Slug.makeSlug(entry.getName()));
        entry.setShortDescription(StringUtils.abbreviate(entry.getDescription(), 20));

        entryRepository.save(entry);
    }

    @Override
    public Entry getEntryBySlug(String entrySlug) {
        return entryRepository.getEntryBySlug(entrySlug);
    }

    public List<Entry> findAll() {
        return (List<Entry>) entryRepository.findAll();
    }

    public Entry findById(Integer id) {
        Entry entry = entryRepository.findById(id).orElse(null);
        return entry;
    }

    public Entry update(Integer id, Entry entryDetails) {
        Entry entry = entryRepository.findById(id).get();

        entry.setName(entryDetails.getName());
        entry.setSlug(Slug.makeSlug(entry.getName()));
        entry.setDescription(entryDetails.getDescription());
        entry.setShortDescription(StringUtils.abbreviate(entry.getDescription(), 100));

        return entryRepository.save(entry);
    }

    public void delete(Integer id) {
        entryRepository.deleteById(id);
    }

}