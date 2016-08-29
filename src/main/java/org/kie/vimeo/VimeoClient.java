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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoResponse;
import org.json.JSONArray;
import org.kie.io.Entry;

public class VimeoClient {

    public List<Entry> getEntriesByUser( final String userName ) {
        try {
            return entriesFromJson( vimeo().get( "/users/" + userName + "/videos" ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Entry> getEntriesByKeyWord( final String query ) {
        try {
            return entriesFromJson( vimeo().searchVideos( param( query ) ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO: Extract to a config File
    Vimeo vimeo() {
        return new Vimeo( "1e34c10c1522dbf2f315273bad305fd2" );
    }

    private List<Entry> entriesFromJson( final VimeoResponse vimeoResponse ) {
        return asEntries( vimeoResponse.getJson().getJSONArray( "data" ) );
    }

    private List<Entry> asEntries( JSONArray data ) {
        final List<Entry> entries = new ArrayList<>();

        for ( int i = 0; i < data.length(); i++ ) {
            entries.add( new Entry( data.getJSONObject( i ) ) );
        }

        return entries;
    }

    private String param( final String query ) {
        try {
            return URLEncoder.encode( query, "UTF-8" );
        } catch ( UnsupportedEncodingException e ) {
            e.printStackTrace();
        }
        return "";
    }
}
