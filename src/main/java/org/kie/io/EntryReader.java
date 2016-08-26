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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class EntryReader {

    private final String fileName;

    public EntryReader( final String fileName ) {
        this.fileName = fileName;
    }

    public List<Entry> getEntries() {
        int pageNumber = 0;

        List<Entry> entries = new ArrayList<>();

        while ( exists( filePath( pageNumber ) ) ) {
            entries.addAll( Arrays.asList( gson().fromJson( readFile( filePath( pageNumber ) ), Entry[].class ) ) );
            pageNumber += 1;
        }

        return entries;
    }

    private boolean exists( final String filePath ) {
        return new File( filePath ).exists();
    }

    private JsonReader readFile( final String filePath ) {
        try {
            return new JsonReader( new FileReader( filePath ) );
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }

        return null;
    }

    private Gson gson() {
        return new Gson();
    }

    private String filePath( final int n ) {
        return System.getProperty( "user.dir" ) + "/static-content/" + fileName + "/" + n + ".json";
    }
}
