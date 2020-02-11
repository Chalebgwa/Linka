package com.nest.linka.Util;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by black on 3/7/17.
 */

public class UserRequest extends StringRequest {
    final static String URL="http://192.168.43.222/Linka/Users.php";

    public UserRequest(Response.Listener<String> listener,Response.ErrorListener errorListener ) {
        super(Method.POST, URL, listener,errorListener);
    }
}
