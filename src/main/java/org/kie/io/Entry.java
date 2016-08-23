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

import java.util.Date;

import com.sun.syndication.feed.synd.SyndEntry;
import twitter4j.Status;

public class Entry {

    private final String title;
    private final String link;
    private final EntryType type;
    private final Date date;

    public Entry( SyndEntry entry ) {
        title = entry.getTitle().trim();
        link = entry.getLink().trim();
        date = entry.getPublishedDate();
        type = EntryType.RSS;
    }

    public Entry( final Status status ) {
        this.title = "@" + status.getUser().getScreenName() + ": " + status.getText();
        this.link = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
        this.date = status.getCreatedAt();
        this.type = EntryType.TWITTER;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public EntryType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }
}
