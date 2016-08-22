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

package org.kie;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.sun.syndication.feed.synd.SyndEntry;
import org.json.simple.JSONObject;
import org.kie.io.EntriesRecorder;
import org.kie.io.Entry;
import org.kie.rss.RSSClient;

public class App {

    public static void main( String[] args ) {
        final String feedUrl = "http://tecnologia.uol.com.br/ultnot/index.xml";
        final String destinationPath = "/home/karreiro/entries.js";

        final RSSClient rssClient = new RSSClient( feedUrl );
        final EntriesRecorder recorder = new EntriesRecorder( destinationPath );

        List<Entry> entries = ( (List<SyndEntry>) rssClient.getEntries() )
                .stream()
                .map( Entry::new )
                .filter( entry -> entry.getTitle().contains( "Drools" ) )
                .collect( Collectors.toList() );

        recorder.record( new Gson().toJson( entries ) );
    }
}
