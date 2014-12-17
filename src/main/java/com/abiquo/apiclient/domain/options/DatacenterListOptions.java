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
package com.abiquo.apiclient.domain.options;

import java.util.Map;

import com.abiquo.apiclient.domain.options.ListOptions.BaseOptionsBuilder;

public class DatacenterListOptions extends BaseOptions
{
    protected DatacenterListOptions(final Map<String, Object> queryParams)
    {
        super(queryParams);
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder extends BaseOptionsBuilder<Builder>
    {
        private Integer pricing;

        public Builder pricing(final int pricing)
        {
            this.pricing = pricing;
            return self();
        }

        @Override
        protected Map<String, Object> buildParameters()
        {
            Map<String, Object> params = super.buildParameters();
            putIfPresent("pricing", pricing, params);
            return params;
        }

        public DatacenterListOptions build()
        {
            return new DatacenterListOptions(buildParameters());
        }

        @Override
        protected Builder self()
        {
            return this;
        }
    }

}
