/*
 * Copyright 2013 Netflix, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package pre

import com.netflix.config.DynamicBooleanProperty
import com.netflix.config.DynamicPropertyFactory
import com.netflix.config.DynamicStringProperty
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.constants.ZuulConstants
import com.netflix.zuul.context.HttpRequestMessage
import com.netflix.zuul.context.SessionContext

class Debug extends ZuulFilter {

    static final DynamicBooleanProperty routingDebug = DynamicPropertyFactory.getInstance().getBooleanProperty(ZuulConstants.ZUUL_DEBUG_REQUEST, false)
    static final DynamicStringProperty debugParameter = DynamicPropertyFactory.getInstance().getStringProperty(ZuulConstants.ZUUL_DEBUG_PARAMETER, "debugParameter")

    @Override
    String filterType() {
        return 'pre'
    }

    @Override
    int filterOrder() {
        return 1
    }

    @Override
    boolean shouldFilter(SessionContext ctx) {
        HttpRequestMessage request = ctx.getRequest()
        if ("true".equals(request.getQueryParams().getFirst(debugParameter.get()))) return true;
        return routingDebug.get();

    }

    @Override
    SessionContext apply(SessionContext ctx) {
        ctx.getAttributes().setDebugRequest(true)
        ctx.getAttributes().setDebugRouting(true)
        return null;
    }

}
