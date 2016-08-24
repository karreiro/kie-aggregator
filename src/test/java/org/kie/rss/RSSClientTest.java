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

import java.util.Arrays;
import java.util.Date;

import com.sun.syndication.feed.synd.SyndEntry;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RSSClientTest {

    @Test
    public void testGetEntries() throws Exception {
        final RSSClient rssClient = spy( new RSSClient( "https://www.redhat.com/en/rss/press-releases" ) );
        final SyndEntry syndEntry = mock( SyndEntry.class );

        when( syndEntry.getTitle() ).thenReturn( "" );
        when( syndEntry.getLink() ).thenReturn( "" );
        when( syndEntry.getPublishedDate() ).thenReturn( new Date() );
        when( rssClient.getFeedEntries() ).thenReturn( Arrays.asList( syndEntry, syndEntry ) );

        assertEquals( 2, rssClient.getEntries().size() );
    }
}
