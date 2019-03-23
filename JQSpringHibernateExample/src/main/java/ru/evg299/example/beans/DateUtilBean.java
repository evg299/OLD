package ru.evg299.example.beans;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DateUtilBean {

	public String formatDate(Date date) {
		Locale locale = LocaleContextHolder.getLocale();
		DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		return f.format(date);
	}
}
