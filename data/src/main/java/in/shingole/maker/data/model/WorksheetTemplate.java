package in.shingole.maker.data.model;

/**
 * Template for creating worksheet
 */
public class WorksheetTemplate {
  private final String templateName;
  private final String templateDescription;
  private final String templateIcon;

  public WorksheetTemplate(String templateName, String templateDescription, String templateIcon) {
    this.templateName = templateName;
    this.templateDescription = templateDescription;
    this.templateIcon = templateIcon;
  }

  public String getTemplateIcon() {
    return templateIcon;
  }

  public String getTemplateName() {
    return templateName;
  }

  public String getTemplateDescription() {
    return templateDescription;
  }

  @Override
  public String toString() {
    return templateName + "\n" + templateDescription;
  }
}
