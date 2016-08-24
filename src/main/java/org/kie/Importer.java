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

import org.kie.gplus.GooglePlusClient;
import org.kie.io.Entry;
import org.kie.io.FileRecorder;
import org.kie.rss.RSSClient;
import org.kie.twitter.TwitterClient;

public class Importer {

    public static void runRssImporter( String userName,
                                       String feedUrl,
                                       final List<String> keywords ) {
        final RSSClient rss = new RSSClient( feedUrl );
        final List<Entry> entries = filterByKeywords( rss.getEntries(), keywords );

        fileRecorder( userName + "-RSS" ).record( entries );
    }

    public static void runTwitterHashTagImporter( String hashTag ) {
        final TwitterClient twitter = new TwitterClient();

        fileRecorder( hashTag + "-Twitter" ).record( twitter.getEntriesByHashTag( hashTag ) );
    }

    public static void runTwitterUserImporter( final String userName,
                                               final String twitterScreenName,
                                               final List<String> keywords ) {

        final TwitterClient twitter = new TwitterClient();
        final List<Entry> entries = filterByKeywords( twitter.getEntriesByUser( twitterScreenName ), keywords );

        fileRecorder( userName + "-Twitter" ).record( entries );
    }

    public static void runGooglePlusSearch( String keyWord ) {
        final GooglePlusClient googlePlus = new GooglePlusClient();

        fileRecorder( keyWord + "-GooglePlus" ).record( googlePlus.getEntriesByKeyWord( keyWord ) );
    }

    public static void runGooglePlusUserImporter( final String userName,
                                                  final String googlePlusUserName,
                                                  final List<String> keywords ) {

        final GooglePlusClient googlePlus = new GooglePlusClient();
        final List<Entry> entries = filterByKeywords( googlePlus.getEntriesByUser( googlePlusUserName ), keywords );

        fileRecorder( userName + "-GooglePlus" ).record( entries );
    }

    private static FileRecorder fileRecorder( final String userName ) {
        return new FileRecorder( userName );
    }

    // kill this method
    public static List<Entry> filterByKeywords( final List<Entry> entries,
                                                final List<String> keywords ) {
        return entries
                .stream()
                .filter( entry -> keywords.stream().anyMatch( keyword -> entry.getTitle().contains( keyword ) ) )
                .collect( Collectors.toList() );
    }
}
