/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RSSClient {

    public static final int N_THREADS = 1;

    private final String feedUrl;

    public RSSClient( final String feedUrl ) {
        this.feedUrl = feedUrl;
    }

    public List getEntries() {
        InputStream inputStream = getInputStream();
        try {
            XmlReader xmlReader = new XmlReader( inputStream );

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build( xmlReader );

            return feed.getEntries();
        } catch ( IOException | FeedException e ) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream getInputStream() {
        ExecutorService executor = getExecutor();

        try {
            Future<Response> response = executor.submit( request() );

            return response.get().getBody();
        } catch ( InterruptedException | IOException | ExecutionException e ) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        return null;
    }

    private Request request() throws MalformedURLException {
        return new Request( new URL( this.feedUrl ) );
    }

    private ExecutorService getExecutor() {
        return Executors.newFixedThreadPool( N_THREADS );
    }
}
