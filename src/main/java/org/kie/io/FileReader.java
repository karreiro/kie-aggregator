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
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class FileReader {

    private final String fileName;

    public FileReader( final String fileName ) {
        this.fileName = fileName;
    }

    public List<Entry> getEntries() throws FileNotFoundException {
        final JsonReader reader = readFile();
        final Entry[] entries = gson().fromJson( reader, Entry[].class );

        return Arrays.asList( entries );
    }

    private JsonReader readFile() throws FileNotFoundException {
        return new JsonReader( new java.io.FileReader( fileName ) );
    }

    private Gson gson() {
        return new Gson();
    }
}
