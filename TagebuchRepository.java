package de.bbq.tagebuch;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class TagebuchRepository {

    private static final File FILE = new File("tagebuch.xml");

    public static TagebuchEintraege load() {
        try {
            if (!FILE.exists()) return new TagebuchEintraege();
            JAXBContext ctx = JAXBContext.newInstance(TagebuchEintraege.class);
            Unmarshaller um = ctx.createUnmarshaller();
            return (TagebuchEintraege) um.unmarshal(FILE);
        } catch (Exception e) {
            e.printStackTrace();
            return new TagebuchEintraege();
        }
    }

    public static void save(TagebuchEintraege wrap) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(TagebuchEintraege.class);
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(wrap, FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
