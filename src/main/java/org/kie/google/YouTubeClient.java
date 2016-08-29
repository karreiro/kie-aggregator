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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import org.kie.io.Entry;

public class YouTubeClient extends BaseClient {

    public List<Entry> getEntriesByKeyWord( final String keyWord ) {
        try {
            return youTubeSearch()
                    .setQ( keyWord )
                    .setOrder( "relevance" )
                    .execute()
                    .getItems()
                    .stream()
                    .map( Entry::new )
                    .collect( Collectors.toList() );

        } catch ( GeneralSecurityException | IOException e ) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<Entry> getEntriesByUser( final String userName ) {
        try {
            return youTubeSearch()
                    .setChannelId( channeId( userName ) )
                    .setOrder( "date" )
                    .execute()
                    .getItems()
                    .stream()
                    .map( Entry::new )
                    .collect( Collectors.toList() );

        } catch ( IndexOutOfBoundsException | GeneralSecurityException | IOException e ) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    YouTube youTube() throws GeneralSecurityException, IOException {
        final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        return new YouTube
                .Builder( httpTransport, jsonFactory, credential( httpTransport, jsonFactory ) )
                .setApplicationName( GooglePlusProperties.APPLICATION_NAME )
                .build();
    }

    private YouTube.Search.List youTubeSearch() throws IOException, GeneralSecurityException {
        return youTube()
                .search()
                .list( "id,snippet" )
                .setMaxResults( 50L );
    }

    private String channeId( final String userName ) throws IOException, GeneralSecurityException {
        final List<Channel> channels = youTube()
                .channels()
                .list( "snippet" )
                .setForUsername( userName )
                .execute()
                .getItems();

        return channels.get( 0 ).getId();
    }

}
