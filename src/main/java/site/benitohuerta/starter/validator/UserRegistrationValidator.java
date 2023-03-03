package site.benitohuerta.starter.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import site.benitohuerta.starter.entity.User;
import site.benitohuerta.starter.service.UserService;

import java.util.List;

@Component
public class UserRegistrationValidator implements Validator {
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty");

        if (user.getName().length() < 6) {
            errors.rejectValue("name", "Size");
        }

        if (user.getPassword().length() < 6) {
            errors.rejectValue("password", "Size");
        }

        if (user.getPasswordConfirm().length() < 6) {
            errors.rejectValue("passwordConfirm", "Size");
        }

        User alreadyRegisteredUserName = userService.getUserByName(user.getName());

        if (alreadyRegisteredUserName != null) {
            errors.rejectValue("name", "Duplicate");
        }

        User alreadyRegisteredUser = userService.getUserByEmail(user.getEmail());

        if (alreadyRegisteredUser != null) {
            errors.rejectValue("email", "Duplicate");
        }

        // if (user.getPasswordConfirm() != user.getPassword()){
        //     errors.rejectValue("passwordConfirm", "PasswordConfirm");
        // }

    }
}