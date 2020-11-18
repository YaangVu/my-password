package com.yaangvu.mypassword.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translation {
    private static ResourceBundleMessageSource messageSource;
    
    @Autowired
    public Translation(ResourceBundleMessageSource messageSource) {
        Translation.messageSource = messageSource;
    }
    
    public static String trans(String msg) {
        Locale locale = LocaleContextHolder.getLocale();
        
        return messageSource.getMessage(msg, null, locale);
    }
}
