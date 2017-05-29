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
 */
public class LabelModel implements Serializable {

    private String label, project, year;
    private boolean checked;
    private CheckBox checkBox;
    private TextView textView;

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
}
