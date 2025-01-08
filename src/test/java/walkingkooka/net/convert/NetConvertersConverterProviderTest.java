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

import org.junit.jupiter.api.Test;
import walkingkooka.convert.provider.ConverterProviderTesting;
import walkingkooka.net.header.MediaType;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;

public final class NetConvertersConverterProviderTest implements ConverterProviderTesting<NetConvertersConverterProvider> {

    @Test
    public void testConverterSelectorHttpEntityContentType() {
        final MediaType contentType = MediaType.TEXT_PLAIN;

        this.converterAndCheck(
            NetConvertersConverterProvider.HTTP_ENTITY_CONTENT_TYPE_STRING + " (\"" + contentType + "\")",
            ProviderContexts.fake(),
            NetConverters.httpEntityContentType(contentType)
        );
    }

    @Override
    public NetConvertersConverterProvider createConverterProvider() {
        return NetConvertersConverterProvider.INSTANCE;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    @Override
    public Class<NetConvertersConverterProvider> type() {
        return NetConvertersConverterProvider.class;
    }
}
