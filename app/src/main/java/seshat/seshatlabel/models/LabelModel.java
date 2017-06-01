package seshat.seshatlabel.models;

import android.widget.CheckBox;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

/**
 * Modèle représentant une étiquette
 * avec ses données
 * ainsi qu'un item de la ListView
 */
public class LabelModel implements Serializable {

    /**
     * informations de l'échantillon
     * nom de l'échantillon
     * projet de l'échantillon
     * année
     */
    private String label, project, year;

    /**
     * selectionné ou non
     */
    private boolean checked;

    /**
     * Checkbox associée
     */
    private CheckBox checkBox;

    /**
     * TextView associée
     */
    private TextView textView;

    /**
     * Nombre d'impressions selectionné
     */
    private int nbImpressions;

    /**
     * identifiants format d'impressions de l'etiquette
     */
    public final static int FORMAT_STANDARD = 1;
    public final static int FORMAT_CRYOTUBE = 2;

    /**
     * format actuel d'impression de l'etiquette
     */
    private int currentFormat;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public LabelModel(String label, String project, String date) {
        super();
        this.label = label;
        this.project = project;
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime time = null;
        try {
            time = format.parseDateTime(date);
        } catch (IllegalArgumentException e) {
            format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
            time = format.parseDateTime(date);
        } finally {
            this.year = "" + time.getYear();
        }
        this.nbImpressions = 0;
        this.checked = false;
        this.currentFormat = LabelModel.FORMAT_STANDARD;
    }

    public String getProject() {
        return this.project;
    }

    public String getYear() {
        return this.year;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLabel() {
        return this.label;
    }

    public void incrementImpressions() {
        if(this.nbImpressions < 5) {
            this.nbImpressions++;
        }
    }

    public void decrementImpressions() {
        if(this.nbImpressions > 1) {
            this.nbImpressions--;
        }
    }

    public int getNbImpressions() {
        return this.nbImpressions;
    }

    public void setNbImpressions(int nb) {
        this.nbImpressions = nb;
    }

    public int getCurrentFormat() {
        return this.currentFormat;
    }

    public void setCurrentFormat(int newFormat) {
        this.currentFormat = newFormat;
    }

    public void reset() {
        this.nbImpressions = 0;
        this.checked = false;
        if(this.checkBox != null) this.checkBox.setChecked(false);
        this.setCurrentFormat(LabelModel.FORMAT_STANDARD);
    }

}
