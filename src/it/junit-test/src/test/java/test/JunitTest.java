package test;


import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Assert;
import org.junit.Test;

import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.net.convert.NetConverters;
import walkingkooka.net.header.HttpHeaderName;
import walkingkooka.net.header.MediaType;
import walkingkooka.net.http.HttpEntity;

@J2clTestInput(JunitTest.class)
public class JunitTest {

    // TODO verify Class.cast is emulated
    @Test
    public void testAssertEquals() {
        Assert.assertEquals(
            1,
            1
        );
    }

    @Test
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
        Assert.assertEquals(
            expected,
            actual
        );
    }
}
