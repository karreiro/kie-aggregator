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

package org.kie.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.ActivityFeed;
import org.junit.Test;
import org.kie.io.Entry;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GooglePlusClientTest {

    @Test
    public void testGetEntriesByKeyWord() throws Exception {
        final GooglePlusClient googlePlus = googlePlusMock();

        List<Entry> entries = googlePlus.getEntriesByKeyWord( "Drools" );

        assertEquals( 3, entries.size() );
    }

    @Test
    public void testGetEntriesByUser() throws Exception {
        final GooglePlusClient googlePlus = googlePlusMock();

        List<Entry> entries = googlePlus.getEntriesByUser( "+MarkProctor" );

        assertEquals( 3, entries.size() );
    }

    private ActivityFeed activityFeed() {
        final ActivityFeed activityFeed = new ActivityFeed();
        final List<Activity> items = Arrays.asList( activity(), activity(), activity() );

        activityFeed.setItems( items );

        return activityFeed;
    }

    private Activity activity() {
        Activity activity = new Activity();
        activity.setTitle( "Test" );
        return activity;
    }

    private GooglePlusClient googlePlusMock() throws GeneralSecurityException, IOException {
        final GooglePlusClient googlePlus = spy( new GooglePlusClient() );
        final Plus plus = mock( Plus.class );
        final Plus.Activities activities = mock( Plus.Activities.class );
        final Plus.Activities.Search search = mock( Plus.Activities.Search.class );
        final Plus.Activities.List list = mock( Plus.Activities.List.class );

        when( googlePlus.gPlus() ).thenReturn( plus );
        when( plus.activities() ).thenReturn( activities );
        when( activities.search( "Drools" ) ).thenReturn( search );
        when( activities.list( "+MarkProctor", "public" ) ).thenReturn( list );
        when( search.execute() ).thenReturn( activityFeed() );
        when( list.execute() ).thenReturn( activityFeed() );

        return googlePlus;
    }

}