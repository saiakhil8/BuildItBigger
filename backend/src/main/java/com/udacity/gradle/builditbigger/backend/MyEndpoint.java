package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.saiakhil.jokes.JokesProvider;


/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com"
        )
)
public class MyEndpoint {

    private static JokesProvider jokesProvider;

    public MyBean getJoke(){
        if (jokesProvider == null) jokesProvider = new JokesProvider();

        MyBean response = new MyBean();
        response.setData(jokesProvider.retrieveJoke());
        return response;
    }


}