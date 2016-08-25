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

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class EntryPagination {

    private static int PAGE_SIZE = 100;

    public void paginate( final List<Entry> entries,
                          final BiConsumer<Integer, List<Entry>> consumer ) {
        int i = 0;
        List<Entry> page;

        do {
            int index = i * PAGE_SIZE;
            page = entries.stream().skip( index ).limit( PAGE_SIZE ).collect( Collectors.toList() );

            if ( !page.isEmpty() ) {
                consumer.accept( i, page );
                i++;
            }

        } while ( !page.isEmpty() );
    }
}
