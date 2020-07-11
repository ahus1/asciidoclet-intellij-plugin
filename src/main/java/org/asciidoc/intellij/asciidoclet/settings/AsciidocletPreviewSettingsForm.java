package org.asciidoc.intellij.asciidoclet.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.EnumComboBoxModel;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;
import org.asciidoc.intellij.editor.AsciiDocHtmlPanel;
import org.asciidoc.intellij.editor.AsciiDocHtmlPanelProvider;
import org.asciidoc.intellij.settings.AttributeTable;
import org.asciidoc.intellij.ui.SplitFileEditor;
import org.asciidoctor.SafeMode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class AsciidocletPreviewSettingsForm implements AsciidocletPreviewSettings.Holder {
  private Object myLastItem;
  private ComboBox myPreviewProvider;
  private ComboBox myDefaultSplitLayout;
  private ComboBox myPreviewThemeLayout;
  private ComboBox mySafeModeSetting;
  private JPanel myMainPanel;
  private EnumComboBoxModel<SplitFileEditor.SplitEditorLayout> mySplitLayoutModel;
  private EnumComboBoxModel<AsciiDocHtmlPanel.PreviewTheme> myPreviewThemeModel;
  private EnumComboBoxModel<SafeMode> mySafeModeModel;
  private CollectionComboBoxModel<AsciiDocHtmlPanelProvider.ProviderInfo> myPreviewPanelModel;
  private AttributeTable attributeTable;
  private JPanel attributesPanel;
  private JBRadioButton myVerticalLayout;
  private JBRadioButton myHorizontalLayout;
  private JBRadioButton myEditorLeft;
  private JBRadioButton myEditorBottom;
  private JBRadioButton myEditorRight;
  private JBRadioButton myEditorTop;
  private JBCheckBox myEnableInjections;
  private JBTextField myDisabledInjectionsByLanguage;
  private JPanel myDisableLanguageInjection;
  private JBCheckBox myShowAsciiDocWarningsAndErrorsInEditor;
  private JBCheckBox myInplacePreviewRefresh;
  private JBCheckBox myEnableKroki;
  private JPanel myKrokiUrlPanel;
  private JBTextField myKrokiUrl;
  private JBTextField myLanguageForPassthrough;
  private JBCheckBox myEnabledAttributeFolding;
  private JFormattedTextField myZoom;
  private JPanel myZoomSettings;
  private JBCheckBox myHideErrorsInSourceBlocks;
  private JBTextField myHideErrorsByLanguage;
  private JBLabel myHideErrorsByLanguageLabel;
  private JBCheckBox myAsciidocletEnabled;

  public JComponent getComponent() {
    return myMainPanel;
  }

  private void createUIComponents() {
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
