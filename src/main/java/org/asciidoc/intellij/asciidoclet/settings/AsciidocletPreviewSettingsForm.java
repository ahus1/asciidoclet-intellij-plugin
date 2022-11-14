package org.asciidoc.intellij.asciidoclet.settings;

import com.intellij.ui.components.JBCheckBox;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class AsciidocletPreviewSettingsForm implements AsciidocletPreviewSettings.Holder {
  private JPanel myMainPanel;
  private JBCheckBox myAsciidocletEnabled;

  public JComponent getComponent() {
    return myMainPanel;
  }

  @Override
  public void setAsciidocletPreviewSettings(@NotNull AsciidocletPreviewSettings settings) {
    myAsciidocletEnabled.setSelected(settings.isAsciidocletEnabled());
  }

  @NotNull
  @Override
  public AsciidocletPreviewSettings getAsciidocletPreviewSettings() {
    return new AsciidocletPreviewSettings(myAsciidocletEnabled.isSelected());
  }
}
