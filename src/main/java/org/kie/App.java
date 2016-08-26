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

import java.util.List;

import org.kie.config.KeyWords;
import org.kie.config.People;

public class App {

    public static void main( String[] args ) {
        final List<String> keywords = KeyWords.all();

        People.all().forEach( person -> {
            Importer.runTwitterUserImporter( person.getUserId(), person.getTwitter() );
            Importer.runGooglePlusUserImporter( person.getUserId(), person.getGooglePlus() );
            Importer.runRssImporter( person.getUserId(), person.getRss() );
        } );

        keywords.forEach( Importer::runTwitterHashTagImporter );
        keywords.forEach( Importer::runGooglePlusSearch );
    }
}
