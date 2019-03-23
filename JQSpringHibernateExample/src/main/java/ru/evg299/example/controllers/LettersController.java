package ru.evg299.example.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import ru.evg299.example.dao.LetterDao;
import ru.evg299.example.domain.entities.Letter;
import ru.evg299.example.forms.LetterForm;

@Controller
@RequestMapping(value = "/letters")
public class LettersController implements HandlerExceptionResolver {
	@Autowired
	private LetterDao letterDao;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Locale locale, Model model) {
		model.addAttribute("letterForm", new LetterForm());
		model.addAttribute("letters", letterDao.getAllLetters());
		return "letters";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String sendForm(
			@ModelAttribute("letterForm") @Valid LetterForm letterForm,
			BindingResult result, Locale locale, Model model) {

		if (null == letterForm || null == letterForm.getAttachedFile()
				|| 0 == letterForm.getAttachedFile().getSize()) {
			ObjectError objectError = new ObjectError("letterForm",
					messageSource.getMessage("NotNull.letterForm.file", null,
							locale));
			result.addError(objectError);
		}

		if (!result.hasErrors()) {
			Letter letter = new Letter();
			letter.setDocNumber(letterForm.getDocNumber());
			letter.setTitle(letterForm.getTitle());
			letter.setTextContent(letterForm.getTextContent());
			letter.setCreationDate(letterForm.getCreationDate());
			letter.setPublished(letterForm.isPublished());
			letter.setAttachedFile(letterForm.getAttachedFile().getBytes());

			letterDao.saveLetter(letter);

			return "redirect:list";
		} else {
			model.addAttribute("letters", letterDao.getAllLetters());
			return "letters";
		}
	}

	@RequestMapping(value = "publish/{id}", method = RequestMethod.GET)
	public String publish(@PathVariable(value = "id") Integer letterId,
			Locale locale, Model model) {
		Letter letter = letterDao.getLetterById(letterId);
		if (null != letter) {
			letter.setPublished(true);
			letterDao.updateLetter(letter);
		}

		return "redirect: /../../list";
	}

	@RequestMapping(value = "file/{id}", method = RequestMethod.GET)
	public String downloadFile(@PathVariable(value = "id") Integer letterId,
			Locale locale, Model model, HttpServletResponse response) {
		Letter letter = letterDao.getLetterById(letterId);
		if (null != letter) {
			response.setHeader("Content-Disposition", "inline; filename=\"file"
					+ letterId + "\"");
			String contentType = "application/octet-stream";
			try {
				OutputStream out = response.getOutputStream();
				response.setContentType(contentType);
				out.write(letter.getAttachedFile());
				out.flush();
				out.close();
			} catch (IOException e) {
				throw new RuntimeException(e.getLocalizedMessage());
			}

		}

		return null;

	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (exception instanceof MaxUploadSizeExceededException) {
			model.put("errors", messageSource.getMessage(
					"Error.page.letters.maxfilesize",
					new String[] { ""
							+ ((MaxUploadSizeExceededException) exception)
									.getMaxUploadSize() }, null));
		} else {
			model.put(
					"errors",
					messageSource.getMessage("Error.page.letters.unexpected",
							null, null) + exception.getMessage());
		}

		model.put("letterForm", new LetterForm());
		model.put("letters", letterDao.getAllLetters());
		return new ModelAndView("letters", model);

	}
}
