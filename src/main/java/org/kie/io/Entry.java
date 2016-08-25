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

import java.io.Serializable;

import com.google.api.client.util.DateTime;
import com.google.api.services.plus.model.Activity;
import com.sun.syndication.feed.synd.SyndEntry;
import twitter4j.Status;

public class Entry implements Comparable,
                              Serializable {

    private final String title;
    private final String link;
    private final EntryType type;
    private final DateTime createdAt;

    public Entry( SyndEntry entry ) {
        title = entry.getTitle().trim();
        link = entry.getLink().trim();
        createdAt = new DateTime( entry.getPublishedDate() );
        type = EntryType.RSS;
    }

    public Entry( final Status status ) {
        this.title = "@" + status.getUser().getScreenName() + ": " + status.getText();
        this.link = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
        this.createdAt = new DateTime( status.getCreatedAt() );
        this.type = EntryType.TWITTER;
    }

    public Entry( final Activity activity ) {
        this.title = getTitle( activity );
        this.link = activity.getUrl();
        this.createdAt = activity.getPublished();
        this.type = EntryType.GooglePlus;
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

    public DateTime getCreatedAt() {
        return createdAt;
    }

    private String getTitle( Activity activity ) {
        if ( activity.getTitle() != null && !activity.getTitle().isEmpty() ) {
            return removeTags( activity.getTitle() );
        }

        if ( activity.getAnnotation() != null && !activity.getAnnotation().isEmpty() ) {
            return removeTags( activity.getAnnotation() );
        }

        if ( activity.getObject().getContent() != null && !activity.getObject().getContent().isEmpty() ) {
            return removeTags( activity.getObject().getContent() );
        }

        return activity.getUrl();
    }

    private String removeTags( String s ) {
        return s.replaceAll( "\\<.*?>", "" ).trim();
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final Entry entry = (Entry) o;

        return title != null ? title.equals( entry.title ) : entry.title == null;

    }

    @Override
    public int compareTo( final Object o ) {
        final Entry entry = (Entry) o;

        final Long v1 = entry.getCreatedAt().getValue();
        final Long v2 = getCreatedAt().getValue();

        return v1.compareTo( v2 );
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : super.hashCode();
    }
}
