package moviesapp.model;

import moviesapp.model.api.RequestBuilder;
import moviesapp.model.exceptions.NotAPositiveIntegerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestBuilderTest {

    @Test
    public void testConvertAsPositiveInt() throws NotAPositiveIntegerException {
        String valueToConvert1 = "42";
        int expectedValue = 42;
        int actualValue = RequestBuilder.convertAsPositiveInt(valueToConvert1);
        assertEquals(expectedValue, actualValue);

        String valueToConvert2 = "-10";
        assertThrows(NotAPositiveIntegerException.class, () -> RequestBuilder.convertAsPositiveInt(valueToConvert2));

        String valueToConvert3 = "0";
        expectedValue = 0;
        actualValue = RequestBuilder.convertAsPositiveInt(valueToConvert3);
        assertEquals(expectedValue, actualValue);
    }
}
