/*
 * Copyright 2016 sasha.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.alexfalappa.nbspringboot.projects.initializr;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;

import com.fasterxml.jackson.databind.JsonNode;

import static com.github.alexfalappa.nbspringboot.projects.initializr.InitializrProjectProps.WIZ_BOOT_VERSION;
import static com.github.alexfalappa.nbspringboot.projects.initializr.InitializrProjectProps.WIZ_DEPENDENCIES;
import static com.github.alexfalappa.nbspringboot.projects.initializr.InitializrProjectProps.WIZ_METADATA;

public class InitializrProjectPanelVisual2 extends JPanel {

    private final InitializrProjectWizardPanel2 panel;
    private final DefaultComboBoxModel<NamedItem> dcbmBootVersion = new DefaultComboBoxModel<>();
    private boolean initialized = false;

    public InitializrProjectPanelVisual2(InitializrProjectWizardPanel2 panel) {
        initComponents();
        this.panel = panel;
    }

    /** This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroller = new javax.swing.JScrollPane();
        pBootDependencies = new com.github.alexfalappa.nbspringboot.projects.initializr.BootDependenciesPanel();
        lBootVersion = new javax.swing.JLabel();
        cbBootVersion = new javax.swing.JComboBox<>();

        scroller.setMinimumSize(new java.awt.Dimension(200, 100));
        scroller.setViewportView(pBootDependencies);

        org.openide.awt.Mnemonics.setLocalizedText(lBootVersion, org.openide.util.NbBundle.getMessage(InitializrProjectPanelVisual2.class, "InitializrProjectPanelVisual2.lBootVersion.text")); // NOI18N

        cbBootVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBootVersionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroller, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lBootVersion)
                        .addGap(6, 6, 6)
                        .addComponent(cbBootVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lBootVersion)
                    .addComponent(cbBootVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroller, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbBootVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBootVersionActionPerformed
        final NamedItem bootVersionItem = (NamedItem) cbBootVersion.getSelectedItem();
        pBootDependencies.adaptToBootVersion(bootVersionItem.getId());
    }//GEN-LAST:event_cbBootVersionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbBootVersion;
    private javax.swing.JLabel lBootVersion;
    private com.github.alexfalappa.nbspringboot.projects.initializr.BootDependenciesPanel pBootDependencies;
    private javax.swing.JScrollPane scroller;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addNotify() {
        super.addNotify();
        pBootDependencies.requestFocus();
    }

    boolean valid(WizardDescriptor wizardDescriptor) {
        return true;
    }

    void store(WizardDescriptor wd) {
        wd.putProperty(WIZ_BOOT_VERSION, cbBootVersion.getSelectedItem());
        wd.putProperty(WIZ_DEPENDENCIES, pBootDependencies.getSelectedDependenciesString());
    }

    void read(WizardDescriptor wd) {
        if (!initialized) {
            final JsonNode meta = (JsonNode) wd.getProperty(WIZ_METADATA);
            pBootDependencies.init(meta);
            // the following will also trigger adaptation of dependencies to default boot version
            fillCombo(meta.path("bootVersion"), dcbmBootVersion, cbBootVersion);
            initialized = true;
        } else {
            pBootDependencies.setSelectedDependenciesString((String) wd.getProperty(WIZ_DEPENDENCIES));
            cbBootVersion.setSelectedItem(wd.getProperty(WIZ_BOOT_VERSION));
        }
//        final NamedItem bootVersionItem = (NamedItem) wd.getProperty(WIZ_BOOT_VERSION);
//        if (bootVersionItem != null) {
//            pBootDependencies.adaptToBootVersion(bootVersionItem.getId());
//        }
    }

    void validate(WizardDescriptor d) throws WizardValidationException {
        // nothing to validate
    }

    private void fillCombo(JsonNode attrNode, DefaultComboBoxModel<NamedItem> comboModel, JComboBox combo) {
        JsonNode valArray = attrNode.path("values");
        comboModel.removeAllElements();
        for (JsonNode val : valArray) {
            comboModel.addElement(new NamedItem(val.get("id").asText(), val.get("name").asText()));
        }
        combo.setModel(comboModel);
        combo.setSelectedItem(new NamedItem(attrNode.path("default").asText(), ""));
    }

}