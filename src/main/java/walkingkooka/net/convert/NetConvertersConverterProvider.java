/*
 * Copyright 2024 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.net.convert;

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.provider.ConverterInfo;
import walkingkooka.convert.provider.ConverterName;
import walkingkooka.convert.provider.ConverterProvider;
import walkingkooka.convert.provider.ConverterProviders;
import walkingkooka.net.UrlPath;
import walkingkooka.net.header.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link ConverterProvider} for {@link NetConverterProviders}.
 */
final class NetConvertersConverterProvider implements ConverterProvider {

    final static NetConvertersConverterProvider INSTANCE = new NetConvertersConverterProvider();

    private NetConvertersConverterProvider() {
        super();
    }

    @Override
    public <C extends ConverterContext> Optional<Converter<C>> converter(final ConverterName name,
                                                                         final List<?> values) {
        Converter<?> converter;

        final List<?> copy = Lists.immutable(values);
        final int count = copy.size();

        switch (name.value()) {
            case HTTP_ENTITY_CONTENT_TYPE_STRING:
                if (1 != count) {
                    throw new IllegalArgumentException("Expected 1 value got " + count + " " + values);
                }

                converter = NetConverters.httpEntityContentType(
                        MediaType.parse(
                                (String) values.get(0)
                        )
                );
                break;
            default:
                converter = null;
                break;
        }

        return Optional.ofNullable(
                Cast.to(converter)
        );
    }

    final static String HTTP_ENTITY_CONTENT_TYPE_STRING = "http-entity-content-type";

    final static ConverterName HTTP_ENTITY_CONTENT_TYPE = ConverterName.with(HTTP_ENTITY_CONTENT_TYPE_STRING);

    @Override
    public Set<ConverterInfo> converterInfos() {
        return INFOS;
    }

    private final static Set<ConverterInfo> INFOS = Sets.of(
            nameToConverterInfo(HTTP_ENTITY_CONTENT_TYPE)
    );

    private static ConverterInfo nameToConverterInfo(final ConverterName name) {
        return ConverterInfo.with(
                ConverterProviders.BASE_URL.appendPath(
                        UrlPath.parse(
                                name.value()
                        )
                ),
                name
        );
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}