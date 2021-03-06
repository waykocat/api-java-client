/**
 * Copyright (C) 2008 Abiquo Holdings S.L.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abiquo.apiclient.interceptors;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import com.abiquo.apiclient.auth.Authentication;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Authenticates the requests using the configured {@link Authentication} strategy.
 * 
 * @author Ignasi Barrera
 */
public class AuthenticationInterceptor implements Interceptor
{
    private final Authentication authentication;

    public AuthenticationInterceptor(final Authentication authentication)
    {
        this.authentication = checkNotNull(authentication, "authentication cannot be null");
    }

    @Override
    public Response intercept(final Chain chain) throws IOException
    {
        Request authenticated = authentication.authenticate(chain.request());
        return chain.proceed(authenticated);
    }

}
