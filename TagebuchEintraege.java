package de.bbq.tagebuch;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class TagebuchEintraege {

    private List<TagebuchEintrag> entries = new ArrayList<>();

    public TagebuchEintraege() {}

    public TagebuchEintraege(List<TagebuchEintrag> entries) {
        this.entries = entries;
    }

    public List<TagebuchEintrag> getEntries() {
        return entries;
    }
    public void setEntries(List<TagebuchEintrag> entries) {
        this.entries = entries;
    }
}
