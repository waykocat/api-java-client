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
package com.abiquo.apiclient;

import com.abiquo.apiclient.cloud.CloudApi;
import com.abiquo.apiclient.enterprise.EnterpriseApi;
import com.abiquo.apiclient.infrastructure.InfrastructureApi;
import com.abiquo.apiclient.templates.TemplatesApi;
import com.abiquo.model.transport.SingleResourceTransportDto;

public class ApiClient
{
    private final RestClient client;

    private final EnterpriseApi enterpriseApi;

    private final InfrastructureApi infrastructureApi;

    private final CloudApi cloudApi;

    private final TemplatesApi templatesApi;

    public ApiClient(final String baseURL, final String username, final String password)
    {
        this(baseURL, username, password, SingleResourceTransportDto.API_VERSION);
    }

    public ApiClient(final String baseURL, final String username, final String password,
        final String version)
    {
        this(baseURL, username, password, version, null);
    }

    public ApiClient(final String baseURL, final String username, final String password,
        final String version, final SSLConfiguration sslConfiguration)
    {
        client = new RestClient(username, password, baseURL, version, sslConfiguration);
        enterpriseApi = new EnterpriseApi(client);
        infrastructureApi = new InfrastructureApi(client);
        cloudApi = new CloudApi(client);
        templatesApi = new TemplatesApi(client);
    }

    public RestClient getClient()
    {
        return client;
    }

    public EnterpriseApi getEnterpriseApi()
    {
        return enterpriseApi;
    }

    public InfrastructureApi getInfrastructureApi()
    {
        return infrastructureApi;
    }

    public CloudApi getCloudApi()
    {
        return cloudApi;
    }

    public TemplatesApi getTemplatesApi()
    {
        return templatesApi;
    }

}