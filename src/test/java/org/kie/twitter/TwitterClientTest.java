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

package org.kie.twitter;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kie.io.Entry;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.api.TimelinesResources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TwitterClientTest {

    @Test
    public void testGetEntriesByHashTag() throws Exception {
        final Query expectedQuery = new Query( "#Drools" ).count( 100 );
        final TwitterClient client = newTwitterClient( twitterSearchMock( expectedQuery ) );

        List<Entry> entries = client.getEntriesByHashTag( "Drools" );

        assertEquals( 2, entries.size() );
    }

    @Test
    public void testGetEntriesByUser() throws Exception {
        final String expectedSearch = "g_carreiro";
        final Paging expectedPagination = new Paging().count( 200 );
        final TwitterClient client = newTwitterClient( twitterUserTimeLineMock( expectedSearch, expectedPagination ) );

        List<Entry> entries = client.getEntriesByUser( "g_carreiro" );

        assertEquals( 3, entries.size() );
    }

    private TwitterClient newTwitterClient( final Twitter twitterMock ) {
        return new TwitterClient() {
            @Override
            Twitter twitter() {
                return twitterMock;
            }
        };
    }

    private Twitter twitterUserTimeLineMock( final String screenName,
                                             final Paging resultsPerPage ) throws TwitterException {
        final Twitter twitter = mock( Twitter.class );
        final Status status = mock( Status.class );
        final ResponseList responseList = mock( ResponseList.class );
        final TimelinesResources timelinesResources = mock( TimelinesResources.class );

        when( status.getUser() ).thenReturn( mock( User.class ) );
        when( responseList.stream() ).thenReturn( Arrays.asList( status, status, status ).stream() );
        when( timelinesResources.getUserTimeline( screenName, resultsPerPage ) ).thenReturn( responseList );
        when( twitter.timelines() ).thenReturn( timelinesResources );

        return twitter;
    }

    private Twitter twitterSearchMock( final Query query ) throws Exception {
        final Twitter twitter = mock( Twitter.class );
        final QueryResult queryResult = mock( QueryResult.class );
        final Status status = mock( Status.class );

        when( status.getUser() ).thenReturn( mock( User.class ) );
        when( queryResult.getTweets() ).thenReturn( Arrays.asList( status, status ) );
        when( twitter.search( query ) ).thenReturn( queryResult );

        return twitter;
    }
}
