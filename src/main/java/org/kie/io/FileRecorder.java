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

package org.kie.io;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class FileRecorder {

    private final String fileName;

    public FileRecorder( final String fileName ) {
        this.fileName = fileName;
    }

    public void record( List<Entry> newEntries ) {
        final FileReader fileReader = new FileReader( filePath() );

        List<Entry> entries = new ArrayList<>();

        try {
            entries = fileReader.getEntries();
        } catch ( FileNotFoundException e ) {

        }

        newEntries.addAll( entries );

        final List<Entry> entryList = newEntries
                .stream()
                .distinct( )
                .sorted( ( e1, e2 ) -> e1.compareTo( e1 ) )
                .collect( Collectors.toList() );

        final String fileContent = toJson( entryList );

        writeFile( fileContent );
    }

    private void writeFile( final String fileContent ) {
        try {
            final FileWriter file = new FileWriter( filePath() );

            file.write( fileContent );
            file.flush();
            file.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private String toJson( final List<Entry> entries ) {
        return new Gson().toJson( entries );
    }

    private String filePath() {
        return System.getProperty( "user.dir" ) + "/static-content/" + fileName + ".json";
    }
}
