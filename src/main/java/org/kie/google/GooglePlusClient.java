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
import com.google.api.services.plus.Plus;
import org.kie.io.Entry;

public class GooglePlusClient extends BaseClient {

    public List<Entry> getEntriesByKeyWord( final String keyWord ) {
        try {
            return gPlus()
                    .activities()
                    .search( keyWord )
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
            return gPlus()
                    .activities()
                    .list( userName, "public" )
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

    Plus gPlus() throws GeneralSecurityException, IOException {
        final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        return new Plus
                .Builder( httpTransport, jsonFactory, credential( httpTransport, jsonFactory ) )
                .setApplicationName( GooglePlusProperties.APPLICATION_NAME )
                .build();
    }
}
