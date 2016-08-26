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

import java.util.List;
import java.util.stream.Collectors;

import org.kie.config.KeyWords;
import org.kie.gplus.GooglePlusClient;
import org.kie.io.Entry;
import org.kie.io.EntryRecorder;
import org.kie.rss.RSSClient;
import org.kie.twitter.TwitterClient;

public class Importer {

    private static List<String> keywords = KeyWords.all();

    public static void runRssImporter( final String userName,
                                       final String feedUrl ) {
        final List<Entry> entries = filterByKeywords( new RSSClient( feedUrl ).getEntries() );

        record( userName + "-RSS", entries );
    }

    public static void runTwitterHashTagImporter( final String hashTag ) {
        final List<Entry> entries = new TwitterClient().getEntriesByHashTag( hashTag );

        record( hashTag + "-Twitter", entries );
    }

    public static void runTwitterUserImporter( final String userName,
                                               final String twitterScreenName ) {
        final List<Entry> entries = filterByKeywords( new TwitterClient().getEntriesByUser( twitterScreenName ) );

        record( userName + "-Twitter", entries );
    }

    public static void runGooglePlusSearch( String keyWord ) {
        final List<Entry> entries = new GooglePlusClient().getEntriesByKeyWord( keyWord );

        record( keyWord + "-GooglePlus", entries );
    }

    public static void runGooglePlusUserImporter( final String userName,
                                                  final String googlePlusUserName ) {
        final List<Entry> entries = filterByKeywords( new GooglePlusClient().getEntriesByUser( googlePlusUserName ) );

        record( userName + "-GooglePlus", entries );
    }

    private static void record( final String fileName,
                                final List<Entry> entries ) {

        new EntryRecorder( fileName ).record( entries );
    }

    // TODO: remove this method
    private static List<Entry> filterByKeywords( final List<Entry> entries ) {
        return entries
                .stream()
                .filter( entry -> keywords.stream().anyMatch( keyword -> entry.getTitle().contains( keyword ) ) )
                .collect( Collectors.toList() );
    }
}
