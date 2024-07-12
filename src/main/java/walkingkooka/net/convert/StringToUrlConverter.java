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
import walkingkooka.Either;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.net.Url;

/**
 * A {@link Converter} that supports converting a {@link String} to one of the {@link Url} sub-classes.
 */
final class StringToUrlConverter<C extends ConverterContext> implements Converter<C> {

    /**
     * Type safe getter.
     */
    static <C extends ConverterContext> StringToUrlConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static StringToUrlConverter INSTANCE = new StringToUrlConverter();

    private StringToUrlConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return value instanceof String && Url.isClass(type);
    }

    @Override
    public <T> Either<T, String> convert(final Object value,
                                         final Class<T> type,
                                         final C context) {
        return value instanceof String && Url.isClass(type) ?
                this.successfulConversion(
                        Url.parseAsUrl(
                                (String) value,
                                Cast.to(type)
                        ),
                        type
                ) :
                this.failConversion(
                        value,
                        type
                );
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
