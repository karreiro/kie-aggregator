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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class EntryRecorder {

    private final String fileName;
    private final EntryPagination pagination;

    public EntryRecorder( final String fileName ) {
        this.pagination = new EntryPagination();
        this.fileName = fileName;
    }

    public void record( List<Entry> newEntries ) {
        final List<Entry> existingEntries = loadExistingEntries();
        final List<Entry> entries = merge( newEntries, existingEntries );

        overrideExistingEntries( entries );
    }

    private List<Entry> merge( final List<Entry> newEntries,
                               final List<Entry> existingEntries ) {
        final ArrayList<Entry> entries = new ArrayList<Entry>() {{
            addAll( newEntries );
            addAll( existingEntries );
        }};

        return entries
                .stream()
                .distinct()
                .sorted( Entry::compareTo )
                .collect( Collectors.toList() );
    }

    List<Entry> loadExistingEntries() {
        return new EntryReader( fileName ).getEntries();
    }

    void overrideExistingEntries( final List<Entry> entryList ) {
        final List<Entry> entries = Lists.reverse( entryList );

        pagination.paginate( entries, ( index, page ) -> {
            try {
                new EntryWriter( fileName + "-" + index ).record( page );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        } );

    }

}
