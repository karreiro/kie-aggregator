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

package org.kie;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main( String[] args ) {

        List<String> keywords = new ArrayList<String>() {{
            add( "Drools" );
            add( "jBPM" );
            add( "KIE" );
            add( "JBoss" );
        }};

        Importer.runRssImporter( "RedHat", "https://www.redhat.com/en/rss/press-releases", keywords );

        Importer.runTwitterUserImporter( "GuilhermeCarreiro", "g_carreiro", keywords );
        Importer.runTwitterUserImporter( "MarkProctor", "markproctor", keywords );

        Importer.runGooglePlusUserImporter( "GuilhermeCarreiro", "107736479244951758517", keywords );
        Importer.runGooglePlusUserImporter( "MarkProctor", "+MarkProctor", keywords );

        keywords.forEach( Importer::runTwitterHashTagImporter );
        keywords.forEach( Importer::runGooglePlusSearch );
    }
}
