package by.javateam.model;

/**
 * Created by valera.
 */
public class Report {

    private String description;
    private String type;
    private String label;

    public Report() {
    }

    public Report(final String description, final String type, final String label) {
        this.description = description;
        this.type = type;
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    @Override
    public final String toString() {
        return "Report{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

}
