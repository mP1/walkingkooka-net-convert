/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.ConverterTesting2;
import walkingkooka.convert.FakeConverterContext;
import walkingkooka.net.header.HttpHeaderName;
import walkingkooka.net.header.MediaType;
import walkingkooka.net.http.HttpEntity;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class HttpEntityWithContentTypeToHttpEntityConverterTest implements ConverterTesting2<HttpEntityWithContentTypeToHttpEntityConverter<FakeConverterContext>, FakeConverterContext>,
        HashCodeEqualsDefinedTesting2<HttpEntityWithContentTypeToHttpEntityConverter<FakeConverterContext>> {

    @Test
    public void testWithNullFails() {
        assertThrows(
                NullPointerException.class,
                () -> HttpEntityWithContentTypeToHttpEntityConverter.with(null)
        );
    }

    private final static MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;

    @Test
    public void testConvertHttpEntityMissingContentTypeFails() {
        this.convertFails(
                HttpEntity.EMPTY,
                HttpEntity.class
        );
    }

    @Test
    public void testConvertHttpEntityIncompatibleContentTypeFails() {
        this.convertFails(
                HttpEntity.EMPTY.addHeader(
                        HttpHeaderName.CONTENT_TYPE,
                        MediaType.TEXT_PLAIN
                ),
                HttpEntity.class
        );
    }

    @Test
    public void testConvertHttpEntityIncompatibleContentTypeFails2() {
        this.convertFails(
                HttpEntityWithContentTypeToHttpEntityConverter.with(
                        MediaType.ANY_TEXT
                ),
                HttpEntity.EMPTY.addHeader(
                        HttpHeaderName.CONTENT_TYPE,
                        MediaType.IMAGE_PNG
                ),
                HttpEntity.class
        );
    }

    @Test
    public void testConvertHttpEntityExactContentType() {
        this.convertAndCheck(
                HttpEntity.EMPTY.addHeader(
                        HttpHeaderName.CONTENT_TYPE,
                        CONTENT_TYPE
                ),
                HttpEntity.class
        );
    }

    @Test
    public void testConvertHttpEntityCompatibleContentType() {
        this.convertAndCheck(
                HttpEntityWithContentTypeToHttpEntityConverter.with(MediaType.ANY_TEXT),
                HttpEntity.EMPTY.addHeader(
                        HttpHeaderName.CONTENT_TYPE,
                        MediaType.TEXT_PLAIN
                ),
                HttpEntity.class
        );
    }

    @Override
    public HttpEntityWithContentTypeToHttpEntityConverter createConverter() {
        return HttpEntityWithContentTypeToHttpEntityConverter.with(CONTENT_TYPE);
    }

    @Override
    public FakeConverterContext createContext() {
        return new FakeConverterContext();
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentContentType() {
        this.checkNotEquals(
                HttpEntityWithContentTypeToHttpEntityConverter.with(MediaType.ALL)
        );
    }

    @Override
    public HttpEntityWithContentTypeToHttpEntityConverter<FakeConverterContext> createObject() {
        return this.createConverter();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
                HttpEntityWithContentTypeToHttpEntityConverter.with(CONTENT_TYPE),
                CONTENT_TYPE.toString()
        );
    }

    // class............................................................................................................
    @Override
    public Class<HttpEntityWithContentTypeToHttpEntityConverter<FakeConverterContext>> type() {
        return Cast.to(HttpEntityWithContentTypeToHttpEntityConverter.class);
    }
}
