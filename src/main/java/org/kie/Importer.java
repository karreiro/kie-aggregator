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
import org.kie.config.People;
import org.kie.config.Person;
import org.kie.gplus.GooglePlusClient;
import org.kie.io.Entry;
import org.kie.io.EntryRecorder;
import org.kie.rss.RSSClient;
import org.kie.twitter.TwitterClient;

public class Importer {

    private static List<String> keywords = KeyWords.all();
    private static List<Person> people = People.all();

    public static void run() {
        people.forEach( person -> {

            final List<Entry> twitterEntries = new TwitterClient().getEntriesByUser( person.getTwitter() );
            final List<Entry> googlePlusEntries = new GooglePlusClient().getEntriesByUser( person.getGooglePlus() );
            final List<Entry> rssEntries = new RSSClient( person.getRss() ).getEntries();

            record( person.getUserId() + "-Twitter", filter( twitterEntries ) );
            record( person.getUserId() + "-GooglePlus", filter( googlePlusEntries ) );
            record( person.getUserId() + "-RSS", filter( rssEntries ) );
        } );

        keywords.forEach( ( hashTag ) -> {
            record( hashTag + "-Twitter", new TwitterClient().getEntriesByHashTag( hashTag ) );
        } );

        keywords.forEach( ( keyWord ) -> {
            record( keyWord + "-GooglePlus", new GooglePlusClient().getEntriesByKeyWord( keyWord ) );
        } );
    }

    private static void record( final String fileName,
                                final List<Entry> entries ) {

        new EntryRecorder( fileName ).record( entries );
    }

    // TODO: Remove this method
    private static List<Entry> filter( final List<Entry> entries ) {
        return entries
                .stream()
                .filter( entry -> keywords.stream().anyMatch( keyword -> entry.getTitle().contains( keyword ) ) )
                .collect( Collectors.toList() );
    }
}
