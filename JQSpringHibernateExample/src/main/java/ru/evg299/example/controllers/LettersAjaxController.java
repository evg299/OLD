package ru.evg299.example.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.evg299.example.dao.LetterDao;
import ru.evg299.example.domain.entities.Letter;
import ru.evg299.example.forms.LetterForm;
import ru.evg299.example.json.ErrorJSON;
import ru.evg299.example.json.FormResponseJSON;
import ru.evg299.example.json.LetterJSON;

@Controller
@RequestMapping(value = "/lettersAjax")
public class LettersAjaxController {
	@Autowired
	private LetterDao letterDao;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		model.addAttribute("letterForm", new LetterForm());
		return "letters-ajax";
	}

	@RequestMapping(/* value = "list", */method = RequestMethod.POST)
	@ResponseBody
	public FormResponseJSON sendForm(
			@ModelAttribute("letterForm") @Valid LetterForm letterForm,
			BindingResult result, Locale locale, Model model) {
		FormResponseJSON formResponseJSON = new FormResponseJSON();

		if (!result.hasErrors()) {
			Letter letter = new Letter();
			letter.setDocNumber(letterForm.getDocNumber());
			letter.setTitle(letterForm.getTitle());
			letter.setTextContent(letterForm.getTextContent());
			letter.setCreationDate(letterForm.getCreationDate());
			letter.setPublished(letterForm.isPublished());
			letter.setAttachedFile(letterForm.getAttachedFile().getBytes());

			letterDao.saveLetter(letter);
		} else {
			formResponseJSON.setStatus("error");
			formResponseJSON.setErrors(new ArrayList<ErrorJSON>());
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				List<String> types = Arrays.asList(error.getCodes());
				if (0 < types.size()) {
					Object[] args = error.getArguments();
					formResponseJSON.getErrors().add(
							new ErrorJSON(error.getField(), messageSource
									.getMessage(types.get(0), args, locale)));
				}
			}
		}

		return formResponseJSON;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public List<LetterJSON> list() {
		List<Letter> letters = letterDao.getAllLetters();
		List<LetterJSON> letterJSONs = new ArrayList<LetterJSON>();
		for (Letter letter : letters) {
			letterJSONs.add(new LetterJSON(letter));
		}
		return letterJSONs;
	}

	@RequestMapping(value = "publish", method = RequestMethod.GET)
	@ResponseBody
	public Boolean publish(HttpServletRequest request,
			HttpServletResponse response) {
		String lid = request.getParameter("lid");
		if (null != lid) {
			Integer letterId = Integer.parseInt(lid);
			Letter letter = letterDao.getLetterById(letterId);
			if (null != letter) {
				letter.setPublished(true);
				letterDao.updateLetter(letter);
				return true;
			}
		}

		return false;
	}
}
