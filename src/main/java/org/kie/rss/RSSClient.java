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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.kie.io.Entry;

public class RSSClient {

    private final String feedUrl;

    public RSSClient( final String feedUrl ) {
        this.feedUrl = feedUrl;
    }

    public List<Entry> getEntries() {
        try {
            return getFeedEntries()
                    .stream()
                    .map( Entry::new )
                    .collect( Collectors.toList() );

        } catch ( FeedException | IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    protected List<SyndEntry> getFeedEntries() throws FeedException, IOException {
        final SyndFeedInput input = new SyndFeedInput();

        return input.build( xmlReader() ).getEntries();
    }

    private XmlReader xmlReader() throws IOException {
        return new XmlReader( feedUrl() );
    }

    private URL feedUrl() throws MalformedURLException {
        return new URL( this.feedUrl );
    }
}
