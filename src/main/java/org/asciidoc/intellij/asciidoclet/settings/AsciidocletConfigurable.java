package org.asciidoc.intellij.asciidoclet.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.asciidoc.intellij.AsciiDocBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AsciidocletConfigurable implements SearchableConfigurable {
  @Nullable
  private AsciidocletPreviewSettingsForm myForm = null;

  @NotNull
  @Override
  public String getId() {
    return "Settings.Asciidoclet";
  }

  @Nullable
  @Override
  public Runnable enableSearch(String option) {
    return null;
  }

  @Nls
  @Override
  public String getDisplayName() {
    return AsciiDocBundle.message("settings.asciidoc.preview.name");
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return null;
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return getForm().getComponent();
  }

  @Override
  public boolean isModified() {
    AsciidocletApplicationSettings settings = AsciidocletApplicationSettings.getInstance();
    return !getForm().getAsciidocletPreviewSettings().equals(settings.getAsciidocletPreviewSettings());
  }

  @Override
  public void apply() throws ConfigurationException {
    AsciidocletApplicationSettings settings = AsciidocletApplicationSettings.getInstance();
    settings.setAsciidocletPreviewSettings(getForm().getAsciidocletPreviewSettings());
  }

  @Override
  public void reset() {
    AsciidocletApplicationSettings settings = AsciidocletApplicationSettings.getInstance();
    getForm().setAsciidocletPreviewSettings(settings.getAsciidocletPreviewSettings());
  }

  @Override
  public void disposeUIResources() {
    myForm = null;
  }

  @NotNull
  public AsciidocletPreviewSettingsForm getForm() {
    if (myForm == null) {
      myForm = new AsciidocletPreviewSettingsForm();
    }
    return myForm;
  }
}
