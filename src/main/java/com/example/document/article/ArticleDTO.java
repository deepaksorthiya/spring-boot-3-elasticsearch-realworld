/*
 * Licensed to Elasticsearch B.V. under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch B.V. licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.example.document.article;

import com.example.document.user.Author;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.Instant;
import java.util.List;

@JsonTypeName("article")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
public record ArticleDTO(
        String slug,
        String title,
        String description,
        String body,
        List<String> tagList,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
        Instant createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
        Instant updatedAt,
        boolean favorited,
        int favoritesCount,
        Author author) {


    public ArticleDTO(Article article) {
        this(article.slug(), article.title(), article.description(), article.body(), article.tagList(),
                article.createdAt(), article.updatedAt(),
                article.favorited(), article.favoritesCount(), article.author());
    }
}
