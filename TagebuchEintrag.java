package de.bbq.tagebuch;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Repräsentiert einen einzelnen Tagebucheintrag mit Datum, Überschrift,
 * Text, Seitenfarbe und optionalem Bildpfad.
 */
@XmlRootElement(name = "entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class TagebuchEintrag {

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate datum;

    /** Überschrift des Eintrags */
    private String ueberschrift;

    /** Haupttext des Eintrags */
    private String text;

    /**
     * Seitenfarbe im Hex-Format, z.B. "#FFEEAA".
     * Wird als Hintergrundfarbe des jeweiligen Eintrags-Pane verwendet.
     */
    private String seitenFarbe;

    /**
     * Optionaler Pfad zu einem Bild, das im Eintrag angezeigt werden soll.
     * Beispiel: "C:/Users/.../bilder/foto.jpg"
     */
    private String imagePath;

    /** JAXB benötigt einen No-Arg-Konstruktor */
    public TagebuchEintrag() {}

    /**
     * Vollständiger Konstruktor.
     *
     * @param datum         Datum des Eintrags
     * @param ueberschrift  Überschrift
     * @param text          Textinhalt
     * @param seitenFarbe   Hintergrundfarbe als Hex-String
     * @param imagePath     Dateipfad zum Bild (kann null sein)
     */
    public TagebuchEintrag(LocalDate datum,
                           String ueberschrift,
                           String text,
                           String seitenFarbe,
                           String imagePath) {
        this.datum         = datum;
        this.ueberschrift  = ueberschrift;
        this.text          = text;
        this.seitenFarbe   = seitenFarbe;
        this.imagePath     = imagePath;
    }

    // --- Getter & Setter ---

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSeitenFarbe() {
        return seitenFarbe;
    }

    public void setSeitenFarbe(String seitenFarbe) {
        this.seitenFarbe = seitenFarbe;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
