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
import walkingkooka.convert.provider.ConverterInfoSet;
import walkingkooka.convert.provider.ConverterName;
import walkingkooka.convert.provider.ConverterProvider;
import walkingkooka.convert.provider.ConverterSelector;
import walkingkooka.net.UrlPath;
import walkingkooka.net.header.MediaType;
import walkingkooka.plugin.ProviderContext;

import java.util.List;
import java.util.Objects;
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
    public <C extends ConverterContext> Converter<C> converter(final ConverterSelector selector,
                                                               final ProviderContext context) {
        Objects.requireNonNull(selector, "selector");
        Objects.requireNonNull(context, "context");

        return selector.evaluateValueText(
                this,
                context
        );
    }

    @Override
    public <C extends ConverterContext> Converter<C> converter(final ConverterName name,
                                                               final List<?> values,
                                                               final ProviderContext context) {
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
            case STRING_TO_URL_STRING:
                if (0 != count) {
                    throw new IllegalArgumentException("Expected 0 value(s) got " + count + " " + values);
                }

                converter = NetConverters.stringToUrl();
                break;
            default:
                throw new IllegalArgumentException("Unknown converter " + name);
        }

        return Cast.to(converter);
    }

    final static String HTTP_ENTITY_CONTENT_TYPE_STRING = "http-entity-content-type";

    final static ConverterName HTTP_ENTITY_CONTENT_TYPE = ConverterName.with(HTTP_ENTITY_CONTENT_TYPE_STRING);

    final static String STRING_TO_URL_STRING = "string-to-url";

    final static ConverterName STRING_TO_URL = ConverterName.with(STRING_TO_URL_STRING);

    @Override
    public ConverterInfoSet converterInfos() {
        return INFOS;
    }

    private final static ConverterInfoSet INFOS = ConverterInfoSet.with(
            Sets.of(
                    nameToConverterInfo(HTTP_ENTITY_CONTENT_TYPE),
                    nameToConverterInfo(STRING_TO_URL)
            )
    );

    private static ConverterInfo nameToConverterInfo(final ConverterName name) {
        return ConverterInfo.with(
                NetConverterProviders.BASE_URL.appendPath(
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
