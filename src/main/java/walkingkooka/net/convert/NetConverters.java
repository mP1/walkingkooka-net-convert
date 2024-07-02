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

import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.net.header.MediaType;
import walkingkooka.reflect.PublicStaticHelper;

/**
 * A collection of {@link walkingkooka.convert.Converter} for walkingkooka.net
 */
public final class NetConverters implements PublicStaticHelper {

    /**
     * {@see HttpEntityWithContentTypeToHttpEntityConverter}
     */
    public static <C extends ConverterContext> Converter<C> httpEntityContentType(final MediaType contentType) {
        return HttpEntityWithContentTypeToHttpEntityConverter.with(contentType);
    }

    /**
     * {@see StringToUrlConverter}
     */
    public static <C extends ConverterContext> Converter<C> stringToUrl() {
        return StringToUrlConverter.instance();
    }

    /**
     * Stop creation
     */
    private NetConverters() {
        throw new UnsupportedOperationException();
    }
}
