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

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;

public class BaseClient {

    protected GoogleCredential credential( final HttpTransport httpTransport,
                                           final JsonFactory jsonFactory ) throws GeneralSecurityException, IOException {
        return new GoogleCredential.Builder()
                .setServiceAccountId( GooglePlusProperties.APPLICATION_SERVICE_ACCOUNT_ID )
                .setTransport( httpTransport )
                .setJsonFactory( jsonFactory )
                .setServiceAccountPrivateKeyFromP12File( configFile() )
                .setServiceAccountScopes( Arrays.asList( PlusScopes.PLUS_ME, YouTubeScopes.YOUTUBE ) )
                .build();
    }

    protected File configFile() {
        return new File( "googleplus.key.p12" );
    }
}
