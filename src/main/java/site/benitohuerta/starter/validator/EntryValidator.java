package site.benitohuerta.starter.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import site.benitohuerta.starter.entity.Entry;
import site.benitohuerta.starter.service.EntryService;
import site.benitohuerta.starter.utils.Slug;

@Component
public class EntryValidator implements Validator {
    @Autowired
    private EntryService entryService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Entry.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Entry entry = (Entry) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");

        Entry alreadyRegisteredEntry = entryService.getEntryBySlug(Slug.makeSlug(entry.getName()));

        if (alreadyRegisteredEntry != null) {
            if (entry.getId() == null || alreadyRegisteredEntry.getId() != entry.getId()) {
                errors.rejectValue("name", "Duplicate");
            }
        }

    }

    public void validateFile(MultipartFile file, Errors errors) {

        if(file.isEmpty() || file.getSize() == 0) {
            errors.rejectValue("file", "NotEmpty");
        }

        String type = file.getContentType().toLowerCase();

        if(type.equals("image/jpg") || type.equals("image/jpeg") || type.equals("image/png")) {
            // TODO
        } else {
            errors.rejectValue("file", "NoImage");
        }
    }
}