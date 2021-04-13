/*
 * Copyright 2018 Netflix, Inc.
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

package com.netflix.zuul.netty.connectionpool;

import javax.annotation.Nullable;

import com.netflix.loadbalancer.Server;
import com.netflix.zuul.context.SessionContext;
import com.netflix.zuul.exception.ErrorType;

public interface RequestStat {

    String SESSION_CONTEXT_KEY = "niwsRequestStat";

    static RequestStat putInSessionContext(RequestStat stat, SessionContext context)
    {
        context.put(SESSION_CONTEXT_KEY, stat);
        return stat;
    }

    @Nullable
    static RequestStat getFromSessionContext(SessionContext context)
    {
        return (RequestStat) context.get(SESSION_CONTEXT_KEY);
    }

    RequestStat server(Server server);

    boolean isFinished();

    long duration();

    void serviceUnavailable();

    void generalError();

    void failAndSetErrorCode(ErrorType errorType);

    void updateWithHttpStatusCode(int httpStatusCode);

    void finalAttempt(boolean finalAttempt);

    boolean finishIfNotAlready();
}
