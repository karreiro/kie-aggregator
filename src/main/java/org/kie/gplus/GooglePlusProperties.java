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

package org.kie.gplus;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GooglePlusProperties {

    static String APPLICATION_NAME = properties().getProperty( "application.name" );
    static String APPLICATION_SERVICE_ACCOUNT_ID = properties().getProperty( "application.serviceAccountId" );

    private static Properties properties() {
        final Properties prop = new Properties();

        try {
            prop.load( new FileReader( filePath() ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return prop;
    }

    private static String filePath() {
        return System.getProperty( "user.dir" ) + "/googleplus.properties";
    }
}
