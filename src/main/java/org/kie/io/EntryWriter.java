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
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

public class EntryWriter {

    private final String fileName;

    public EntryWriter( final String fileName ) {
        this.fileName = fileName;
    }

    public void record( final int index,
                        final List<Entry> entries ) throws IOException {
        final String content = toJson( entries );

        writeFile( filePath( index ), content );
    }

    private void writeFile( final String fileName,
                            final String fileContent ) throws IOException {
        final FileWriter file = new FileWriter( fileName );

        file.write( fileContent );
        file.flush();
        file.close();
    }

    private String toJson( final List<Entry> entries ) {
        return new Gson().toJson( entries );
    }

    private String filePath( final int pageNumber ) {
        return folderPath() + "/" + pageNumber + ".json";
    }

    private String folderPath() {
        final String folder = System.getProperty( "user.dir" ) + "/static-content/" + fileName;
        final File file = new File( folder );

        file.mkdir();

        return file.getPath();
    }
}
