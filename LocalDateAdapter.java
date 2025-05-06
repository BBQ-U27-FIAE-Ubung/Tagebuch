package de.bbq.tagebuch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return (v == null || v.isEmpty()) ? null : LocalDate.parse(v, FMT);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return (v == null) ? "" : v.format(FMT);
    }
}
