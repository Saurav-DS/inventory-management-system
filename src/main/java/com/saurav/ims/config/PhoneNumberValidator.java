package com.saurav.ims.config;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.saurav.ims.config.annotations.ValidPhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isEmpty()) return false;

        try {
            // null region allows numbers with country code
            var numberProto = phoneUtil.parse(phoneNumber, null);
            return phoneUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
