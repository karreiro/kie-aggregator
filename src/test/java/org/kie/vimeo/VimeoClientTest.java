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

package org.kie.vimeo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.io.Entry;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VimeoClientTest {

    @Mock
    private Vimeo vimeo;

    @Mock
    private VimeoResponse vimeoResponse;

    @Mock
    private JSONObject json;

    private VimeoClient client;

    @Before
    public void setup() throws Exception {
        client = clientMock();
    }

    @Test
    public void testGetVideosByUser() throws Exception {
        when( vimeo.get( "/users/karreiro/videos" ) ).thenReturn( vimeoResponse );

        final List<Entry> entries = client.getEntriesByUser( "karreiro" );

        assertEquals( 1, entries.size() );
    }

    @Test
    public void testGetEntriesByKeyWord() throws Exception {
        when( vimeo.searchVideos( "Drools" ) ).thenReturn( vimeoResponse );

        final List<Entry> entries = client.getEntriesByKeyWord( "Drools" );

        assertEquals( 1, entries.size() );
    }

    private VimeoClient clientMock() throws IOException {
        final VimeoClient client = spy( new VimeoClient() );

        when( client.vimeo() ).thenReturn( vimeo );
        when( vimeoResponse.getJson() ).thenReturn( json );
        when( json.getJSONArray( "data" ) ).thenReturn( getJsonArray() );

        return client;
    }

    private JSONArray getJsonArray() {
        final JSONArray jsonArray = new JSONArray();

        jsonArray.put( new org.json.simple.JSONObject( new HashMap<String, String>() {{
            put( "name", "Test" );
            put( "link", "https://vimeo.com/147173661" );
            put( "release_time", "2000-01-01T00:00:00.000Z" );
        }} ) );

        return jsonArray;
    }
}
