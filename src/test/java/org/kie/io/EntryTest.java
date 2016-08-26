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

package org.kie.io;

import java.util.Date;

import com.google.api.client.util.DateTime;
import com.google.api.services.plus.model.Activity;
import com.sun.syndication.feed.synd.SyndEntry;
import org.junit.Test;
import twitter4j.Status;
import twitter4j.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EntryTest {

    @Test
    public void testRssEntry() {
        final Date date = new Date();
        final SyndEntry entryMock = mock( SyndEntry.class );

        when( entryMock.getTitle() ).thenReturn( "\n\n\n RSS Title   " );
        when( entryMock.getLink() ).thenReturn( "\n\n\n   https://www.redhat.com/en/rss/press-releases   " );
        when( entryMock.getPublishedDate() ).thenReturn( date );

        final Entry entry = new Entry( entryMock );

        assertEquals( "RSS Title", entry.getTitle() );
        assertEquals( "https://www.redhat.com/en/rss/press-releases", entry.getLink() );
        assertEquals( new DateTime( date ), entry.getCreatedAt() );
        assertEquals( EntryType.RSS, entry.getType() );
    }

    @Test
    public void testTwitterEntry() {
        final Date date = new Date();
        final Status status = mock( Status.class );
        final User user = mock( User.class );

        when( user.getScreenName() ).thenReturn( "g_carreiro" );
        when( status.getUser() ).thenReturn( user );
        when( status.getId() ).thenReturn( 1L );
        when( status.getText() ).thenReturn( "Test" );
        when( status.getCreatedAt() ).thenReturn( date );

        final Entry entry = new Entry( status );

        assertEquals( "@g_carreiro: Test", entry.getTitle() );
        assertEquals( "https://twitter.com/g_carreiro/status/1", entry.getLink() );
        assertEquals( new DateTime( date ), entry.getCreatedAt() );
        assertEquals( EntryType.Twitter, entry.getType() );
    }

    @Test
    public void testEquals() {
        final Entry e1 = new Entry( new Activity().setTitle( "Test" ).setUrl( "http://redhat.com/en" ) );
        final Entry e2 = new Entry( new Activity().setTitle( "Test" ).setUrl( "http://redhat.com/br" ) );

        assertEquals( e1, e2 );
    }
}
