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

package org.kie.social.twitter;

import java.util.List;
import java.util.stream.Collectors;

import org.kie.io.Entry;
import twitter4j.Query;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterClient {

    public List<Entry> getEntries( final String query ) {
        try {
            return twitterClient()
                    .search( new Query( query ) )
                    .getTweets()
                    .stream()
                    .map( Entry::new )
                    .collect( Collectors.toList() );

        } catch ( TwitterException e ) {
            e.printStackTrace();
        }

        return null;
    }

    private Twitter twitterClient() {
        return TwitterFactory.getSingleton();
    }
}
