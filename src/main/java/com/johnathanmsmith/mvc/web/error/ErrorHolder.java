package com.johnathanmsmith.mvc.web.error;

/**
 * Date:   6/12/13 / 8:48 AM
 * Author: Johnathan Mark Smith
 * Email:  johnathansmith1969@gmail.com
 * <p/>
 * Comments:
 * <p/>
 * This is used to return a error to the client
 */

public class ErrorHolder
{
    public String errorMessage;

    public ErrorHolder(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
