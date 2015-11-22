package co.bluepass.web.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

/**
 * The type Date time editor.
 */
public class DateTimeEditor extends PropertyEditorSupport {

    private final DateTimeFormatter formatter;

    private final boolean allowEmpty;

    /**
     * Instantiates a new Date time editor.
     *
     * @param dateFormat the date format
     * @param allowEmpty the allow empty
     */
    public DateTimeEditor(String dateFormat, boolean allowEmpty) {
        this.formatter = DateTimeFormat.forPattern(dateFormat).withLocale(Locale.KOREA);
        this.allowEmpty = allowEmpty;
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return value != null ? new DateTime(value).toString(formatter) : "";
    }

    @Override
    public void setAsText( String text ) throws IllegalArgumentException {
        if ( allowEmpty && !StringUtils.hasText(text) ) {
            // Treat empty String as null value.
            setValue(null);
        } else {
            setValue(new DateTime(formatter.parseDateTime(text)));
        }
    }
}
