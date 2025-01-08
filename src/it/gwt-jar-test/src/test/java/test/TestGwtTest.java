package test;

import com.google.gwt.junit.client.GWTTestCase;

import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.net.convert.NetConverters;
import walkingkooka.net.header.HttpHeaderName;
import walkingkooka.net.header.MediaType;
import walkingkooka.net.http.HttpEntity;

@walkingkooka.j2cl.locale.LocaleAware
public class TestGwtTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "test.Test";
    }

    public void testAssertEquals() {
        assertEquals(
            1,
            1
        );
    }

    public void testConverter() {
        final MediaType contentType = MediaType.ANY_TEXT;
        final Converter<ConverterContext> converter = NetConverters.httpEntityContentType(contentType);
        final String text = "BodyText123";

        checkEquals(
            text,
            converter.to(
                String.class,
                Converters.hasTextToString()
            ).convertOrFail(
                HttpEntity.EMPTY.addHeader(
                    HttpHeaderName.CONTENT_TYPE,
                    MediaType.TEXT_PLAIN
                ).setBodyText(text),
                String.class,
                ConverterContexts.fake()
            )
        );
    }

    private void checkEquals(final Object expected,
                             final Object actual) {
        assertEquals(
            expected,
            actual
        );
    }
}
