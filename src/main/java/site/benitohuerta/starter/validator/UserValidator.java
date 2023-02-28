package site.benitohuerta.starter.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import site.benitohuerta.starter.entity.User;
import site.benitohuerta.starter.service.UserService;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "active", "NotEmpty");

        if (user.getRoles().size() < 1) {
            errors.rejectValue("roles", "ArrayMin");
        }

        if (user.getName().length() < 6) {
            errors.rejectValue("name", "Size");
        }

        User alreadyRegisteredUser = userService.getUserByEmail(user.getEmail());

        if (alreadyRegisteredUser != null) {
            if (user.getId() == null || alreadyRegisteredUser.getId() != user.getId()) {
                errors.rejectValue("email", "Duplicate");
            }
        }

    }
}