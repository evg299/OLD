package evg299.sample.web.dojo.controllers.rest;

import evg299.sample.web.dojo.domain.entities.User;
import evg299.sample.web.dojo.domain.services.UserService;
import evg299.sample.web.dojo.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by evgeny on 27.12.14.
 */
@Controller
@RequestMapping(value = "rest/users")
public class UsersRest
{
    @Autowired
    private UserService userService;

    @RequestMapping(value = "generate", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void generate()
    {
        Random random = new Random();
        for (int i = 0; i < 123; i++)
        {
            User user = new User();
            int ii = random.nextInt();
            user.setFname("fn#" + ii);
            user.setSname("sn#" + ii);
            user.setLname("fn#" + ii);
            user.setEmail(String.format("eml%s@qaz.ee", random.nextInt()));
            user.setGender(random.nextBoolean());
            user.setBirthDate(new Date(random.nextInt()));
            userService.save(user);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> get(@RequestHeader(value = "Range", required = false) String range,
                             HttpServletRequest request, HttpServletResponse response)
    {
        RestUtil.Range parsedRange = RestUtil.parseRange(range);
        System.err.println("Range: " + parsedRange);

        RestUtil.FilterAndSortContainer filterAndSortContainer = RestUtil.parseFilterAndSort(request);
        System.err.println("Filter: " + filterAndSortContainer.getFilter());
        System.err.println("Sort: " + filterAndSortContainer.getSort());

        List<User> users = userService.get(filterAndSortContainer.getFilter(), filterAndSortContainer.getSort(), parsedRange);

        // Content-Range: items 0-24/66
        if(null != parsedRange)
            response.addHeader("Content-Range", String.format("items %s-%s/%s", parsedRange.getFirst(), parsedRange.getLast(), userService.count(filterAndSortContainer.getFilter(), filterAndSortContainer.getSort())));

        return users;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getById(@PathVariable int id)
    {
        return userService.getById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable int id, @RequestBody User user)
    {
        System.err.println(id);
        System.err.println(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void save(@PathVariable int id, @RequestBody User user)
    {
        System.err.println(id);
        System.err.println(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable int id)
    {
        System.err.println(id);
    }
}
