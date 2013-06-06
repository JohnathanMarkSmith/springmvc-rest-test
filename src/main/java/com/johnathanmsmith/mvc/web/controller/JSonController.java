package com.johnathanmsmith.mvc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Date:   6/5/13 / 7:58 AM
 * Author: Johnathan Mark Smith
 * Email:  johnathansmith1969@gmail.com
 * <p/>
 * Comments:
 * <p/>
 * This is my basic controller for my web app.
 */


@Controller
@RequestMapping("/json")
class JSonController
{

    private static final Logger logger = LoggerFactory.getLogger(JSonController.class);

    private class User {
        private String user;
        private String name;

        private User(String user, String name)
        {
            this.user = user;
            this.name = name;
        }

        private String getUser()
        {
            return user;
        }

        private void setUser(String user)
        {
            this.user = user;
        }
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public @ResponseBody User getName(@PathVariable String name, ModelMap model)
    {

        logger.debug("I am in the controller and got user name: " + name);

        /*

            Simulate a successful lookup for 2 users:

         */


        if ("JohnathanMarkSmith".equals(name))
        {
            return new User("Johnathan Mark Smith", name);
        }

        if ("Regan".equals(name))
        {
            return new User("Regan Smith", name);
        }
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody User getDisplayDefault(ModelMap model) {

        /*

            you did not enter a name so the default is going to run

         */


        return new User("Johnathan Mark Smith", "JohnathanMarkSmith");

    }
}
