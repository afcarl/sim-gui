
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Sandmann
 */
public class EditableGui extends javax.swing.JFrame {

    // variables from multigui
    /*
     if TabSettings is a well understood element that controls all the panes in 
     the first page of gui, then what does the Tab Plotting does?
     */
    TabSettings settingContainer;
    TabPlotting plottingContainer;
    ArrayList<String> agentList;

    private JPanel lcRightPanel;
    private JScrollPane listScrollAgentTable;
    private AgentTableModel tabAgentsModel;

    /**
     *
     */
    public static boolean variableRemoved;

    /**
     * Creates new form SimulationSettingsFrame
     */
    public EditableGui() {

        initComponents();
        
        testLabelPlotting.setVisible(false);
        simulationSettingsTestLabel.setVisible(false);
        
        //the window appearing on attempt to close the gui
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Object text = "Do you want to save the settings before quitting? \n";
                int choice = JOptionPane.showConfirmDialog(null, text, "Exit GUI", JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == 0) {
                    /*Save Settings*/
                    SaveSettings();
                    setVisible(false);
                    dispose();
                    System.exit(-1);
                } else if (choice == 1) {
                    /*Choice is no*/
                    setVisible(false);
                    dispose();
                }
            }
        });

        readSettingsFromFile();
        settingContainer = new TabSettings();
        plottingContainer = new TabPlotting();

        oldFillMenu_FromMultiGUIConstructor();
        runExperimentButton.setEnabled(SimulationSettings.experimentBuilt);
        //OldTabExperimentInitialization();
        //OldTabPlottingInitialization();

        //new simulation settings page:
        addOldSumulationPanelFunctionalityInNewSimulationTab();
        newDrawAgentTable();
        //drawAgentTable();

        //new plotting settings page
        addOldSubTabsInNewTabPlottingPage();
        addOldBottomPanelFunctionalityInNewTabPlottingPage();

        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jButton3 = new javax.swing.JButton();
        runExperimentButton = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        doNotRunSimulationsRadioButton = new javax.swing.JRadioButton();
        runSimulationsRadioButton = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        numberOfBatchRunsTextField = new javax.swing.JTextField();
        numberOfIterationsTextField = new javax.swing.JTextField();
        numberOfProcessorsComboBox = new javax.swing.JComboBox();
        changeParameterSetupButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        runOnlyOneBatchRadioButton = new javax.swing.JRadioButton();
        parameterVariationOneParameterRadioButton = new javax.swing.JRadioButton();
        editParameter1Button = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        simulationSettingsTestLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        notCompressRadioButton = new javax.swing.JRadioButton();
        compressAndRemoveOrigRadioButton = new javax.swing.JRadioButton();
        compressAndKeepOrigRadioButton = new javax.swing.JRadioButton();
        removeDecompressedRadioButton = new javax.swing.JRadioButton();
        decompressRadioButton = new javax.swing.JRadioButton();
        yesWriteAllDatabaseRadioButton = new javax.swing.JRadioButton();
        noWriteAllDatabaseRadioButton = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        singleRunAnalysisCheckboxNew = new javax.swing.JCheckBox();
        batchRunCheckBoxNew = new javax.swing.JCheckBox();
        parameterAnalysisCheckBoxNew = new javax.swing.JCheckBox();
        addLegendCheckboxNew = new javax.swing.JCheckBox();
        coloredPlotsCheckboxNew = new javax.swing.JCheckBox();
        fileTypePlotsComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        transitionPhaseTextFieldNew = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        testLabelPlotting = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton3.setText("Build Experiment");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        runExperimentButton.setText("Run Experiment");
        runExperimentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runExperimentButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonGroup1.add(doNotRunSimulationsRadioButton);
        doNotRunSimulationsRadioButton.setText("Do not run Simulations");
        doNotRunSimulationsRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doNotRunSimulationsRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(runSimulationsRadioButton);
        runSimulationsRadioButton.setSelected(true);
        runSimulationsRadioButton.setText("Run Simulations");
        runSimulationsRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runSimulationsRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(runSimulationsRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(doNotRunSimulationsRadioButton)
                .addGap(142, 142, 142))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doNotRunSimulationsRadioButton)
                    .addComponent(runSimulationsRadioButton))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Number of batch Runs");

        jLabel2.setText("Number of Iterations");

        jLabel3.setText("Number of Processes");

        numberOfBatchRunsTextField.setText("10");
        numberOfBatchRunsTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                numberOfBatchRunsTextFieldCaretUpdate(evt);
            }
        });

        numberOfIterationsTextField.setText("5000");
        numberOfIterationsTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                numberOfIterationsTextFieldCaretUpdate(evt);
            }
        });

        numberOfProcessorsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", " " }));
        numberOfProcessorsComboBox.setEnabled(false);

        changeParameterSetupButton.setText("Change Parameter Setup");
        changeParameterSetupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeParameterSetupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(numberOfBatchRunsTextField)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(numberOfIterationsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addComponent(numberOfProcessorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addComponent(changeParameterSetupButton)
                .addGap(93, 93, 93))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(numberOfBatchRunsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numberOfIterationsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(changeParameterSetupButton))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numberOfProcessorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addGap(0, 41, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonGroup2.add(runOnlyOneBatchRadioButton);
        runOnlyOneBatchRadioButton.setSelected(true);
        runOnlyOneBatchRadioButton.setText("Run only one Batch");
        runOnlyOneBatchRadioButton.setToolTipText("");
        runOnlyOneBatchRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runOnlyOneBatchRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup2.add(parameterVariationOneParameterRadioButton);
        parameterVariationOneParameterRadioButton.setText("Parameter Variation with one Parameter");
        parameterVariationOneParameterRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parameterVariationOneParameterRadioButtonActionPerformed(evt);
            }
        });

        editParameter1Button.setText("Edit Parameter ");
        editParameter1Button.setEnabled(false);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTable2);

        simulationSettingsTestLabel.setText("testLabel");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(parameterVariationOneParameterRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(runOnlyOneBatchRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(simulationSettingsTestLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(editParameter1Button, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(editParameter1Button)
                            .addComponent(runOnlyOneBatchRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(parameterVariationOneParameterRadioButton)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(88, 131, Short.MAX_VALUE)
                        .addComponent(simulationSettingsTestLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonGroup3.add(notCompressRadioButton);
        notCompressRadioButton.setText("Do not compress");
        notCompressRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notCompressRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup3.add(compressAndRemoveOrigRadioButton);
        compressAndRemoveOrigRadioButton.setText("Compress Databases and remove Original");
        compressAndRemoveOrigRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compressAndRemoveOrigRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup3.add(compressAndKeepOrigRadioButton);
        compressAndKeepOrigRadioButton.setText("Compress Databases and keep Original");
        compressAndKeepOrigRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compressAndKeepOrigRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup3.add(removeDecompressedRadioButton);
        removeDecompressedRadioButton.setText("Remove decompressed Database");
        removeDecompressedRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeDecompressedRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup3.add(decompressRadioButton);
        decompressRadioButton.setText("Decompress Database");
        decompressRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decompressRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup4.add(yesWriteAllDatabaseRadioButton);
        yesWriteAllDatabaseRadioButton.setText("Yes");
        yesWriteAllDatabaseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesWriteAllDatabaseRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup4.add(noWriteAllDatabaseRadioButton);
        noWriteAllDatabaseRadioButton.setText("No");
        noWriteAllDatabaseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noWriteAllDatabaseRadioButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Write all agent variables to Database");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(decompressRadioButton)
                    .addComponent(compressAndRemoveOrigRadioButton)
                    .addComponent(notCompressRadioButton)
                    .addComponent(compressAndKeepOrigRadioButton)
                    .addComponent(removeDecompressedRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(yesWriteAllDatabaseRadioButton)
                .addGap(18, 18, 18)
                .addComponent(noWriteAllDatabaseRadioButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(notCompressRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(compressAndRemoveOrigRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(compressAndKeepOrigRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(removeDecompressedRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(decompressRadioButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yesWriteAllDatabaseRadioButton)
                    .addComponent(noWriteAllDatabaseRadioButton)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane3.setViewportView(jPanel6);

        jTabbedPane2.addTab("Simulation Settings", jScrollPane3);

        singleRunAnalysisCheckboxNew.setText("Single Run Analysis");
        singleRunAnalysisCheckboxNew.setName("SingleRunAnalysisCheckboxNew\n"); // NOI18N
        singleRunAnalysisCheckboxNew.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                singleRunAnalysisCheckboxNewItemStateChanged(evt);
            }
        });
        singleRunAnalysisCheckboxNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                singleRunAnalysisCheckboxNewActionPerformed(evt);
            }
        });

        batchRunCheckBoxNew.setText("Batch Run Analysis");
        batchRunCheckBoxNew.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                batchRunCheckBoxNewItemStateChanged(evt);
            }
        });

        parameterAnalysisCheckBoxNew.setText("Parameter Analysis");
        parameterAnalysisCheckBoxNew.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                parameterAnalysisCheckBoxNewItemStateChanged(evt);
            }
        });

        addLegendCheckboxNew.setText("Add Legends");
        addLegendCheckboxNew.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                addLegendCheckboxNewItemStateChanged(evt);
            }
        });

        coloredPlotsCheckboxNew.setText("Colored Plots");
        coloredPlotsCheckboxNew.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                coloredPlotsCheckboxNewItemStateChanged(evt);
            }
        });

        fileTypePlotsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pdf", "eps", "png" }));
        fileTypePlotsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileTypePlotsComboBoxActionPerformed(evt);
            }
        });

        jLabel5.setText("Transition Phase");

        transitionPhaseTextFieldNew.setText("0");
        transitionPhaseTextFieldNew.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                transitionPhaseTextFieldNewCaretUpdate(evt);
            }
        });
        transitionPhaseTextFieldNew.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                transitionPhaseTextFieldNewCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                transitionPhaseTextFieldNewInputMethodTextChanged(evt);
            }
        });
        transitionPhaseTextFieldNew.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                transitionPhaseTextFieldNewKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                transitionPhaseTextFieldNewKeyTyped(evt);
            }
        });

        jLabel6.setText("File Type of Plots");

        testLabelPlotting.setText("testLabel");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(parameterAnalysisCheckBoxNew)
                        .addComponent(singleRunAnalysisCheckboxNew))
                    .addComponent(testLabelPlotting, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(batchRunCheckBoxNew))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addLegendCheckboxNew, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coloredPlotsCheckboxNew, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fileTypePlotsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(transitionPhaseTextFieldNew, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(singleRunAnalysisCheckboxNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(batchRunCheckBoxNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(parameterAnalysisCheckBoxNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(testLabelPlotting, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(transitionPhaseTextFieldNew, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addLegendCheckboxNew, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(coloredPlotsCheckboxNew, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fileTypePlotsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 251, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane4.setViewportView(jPanel7);

        jTabbedPane2.addTab("Plotting Settings", jScrollPane4);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
            .addGroup(layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(jButton3)
                .addGap(211, 211, 211)
                .addComponent(runExperimentButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(runExperimentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        buildExperimentButtonPressed();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void singleRunAnalysisCheckboxNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_singleRunAnalysisCheckboxNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_singleRunAnalysisCheckboxNewActionPerformed

    private void singleRunAnalysisCheckboxNewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_singleRunAnalysisCheckboxNewItemStateChanged
        // TODO add your handling code here:
        PlottingSettings.singleRunAnalyis = singleRunAnalysisCheckboxNew.isSelected();
        testLabelPlotting.setText(PlottingSettings.singleRunAnalyis + "");
    }//GEN-LAST:event_singleRunAnalysisCheckboxNewItemStateChanged

    private void batchRunCheckBoxNewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_batchRunCheckBoxNewItemStateChanged
        // TODO add your handling code here:
        PlottingSettings.batchRunAnalyis = batchRunCheckBoxNew.isSelected();
        testLabelPlotting.setText(PlottingSettings.batchRunAnalyis + "");

    }//GEN-LAST:event_batchRunCheckBoxNewItemStateChanged

    private void parameterAnalysisCheckBoxNewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_parameterAnalysisCheckBoxNewItemStateChanged
        // TODO add your handling code here:
        PlottingSettings.parameterAnalyis = parameterAnalysisCheckBoxNew.isSelected();
        testLabelPlotting.setText(PlottingSettings.parameterAnalyis + "");

    }//GEN-LAST:event_parameterAnalysisCheckBoxNewItemStateChanged

    private void transitionPhaseTextFieldNewKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_transitionPhaseTextFieldNewKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_transitionPhaseTextFieldNewKeyTyped

    private void transitionPhaseTextFieldNewInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_transitionPhaseTextFieldNewInputMethodTextChanged

    }//GEN-LAST:event_transitionPhaseTextFieldNewInputMethodTextChanged

    private void transitionPhaseTextFieldNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_transitionPhaseTextFieldNewKeyPressed
        // TODO add your handling code here:


    }//GEN-LAST:event_transitionPhaseTextFieldNewKeyPressed

    private void transitionPhaseTextFieldNewCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_transitionPhaseTextFieldNewCaretUpdate
        // TODO add your handling code here:
        String input = transitionPhaseTextFieldNew.getText();
        if (input.length() == 0) {
            input = "0";
        }

        try {

            PlottingSettings.transitionPhase = Integer.parseInt(input);
            testLabelPlotting.setText(PlottingSettings.transitionPhase + "");
            //transitionPhaseTextFieldNew.selectAll();

        } catch (NumberFormatException nFE) {
            JOptionPane.showMessageDialog(null, "Not an integer");
        }
    }//GEN-LAST:event_transitionPhaseTextFieldNewCaretUpdate

    private void transitionPhaseTextFieldNewCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_transitionPhaseTextFieldNewCaretPositionChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_transitionPhaseTextFieldNewCaretPositionChanged

    private void addLegendCheckboxNewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_addLegendCheckboxNewItemStateChanged
        // TODO add your handling code here:
        PlottingSettings.addLegend = addLegendCheckboxNew.isSelected();
        testLabelPlotting.setText(PlottingSettings.addLegend + "");

    }//GEN-LAST:event_addLegendCheckboxNewItemStateChanged

    private void coloredPlotsCheckboxNewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_coloredPlotsCheckboxNewItemStateChanged
        // TODO add your handling code here:
        PlottingSettings.coloured = coloredPlotsCheckboxNew.isSelected();
        testLabelPlotting.setText(PlottingSettings.coloured + "");

    }//GEN-LAST:event_coloredPlotsCheckboxNewItemStateChanged

    private void fileTypePlotsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileTypePlotsComboBoxActionPerformed
        // TODO add your handling code here:
        PlottingSettings.fileTypePlots = (String) fileTypePlotsComboBox.getSelectedItem();
        testLabelPlotting.setText(PlottingSettings.fileTypePlots + "");
    }//GEN-LAST:event_fileTypePlotsComboBoxActionPerformed

    private void decompressRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decompressRadioButtonActionPerformed
        compressionRadioButtonModified();
    }//GEN-LAST:event_decompressRadioButtonActionPerformed

    private void removeDecompressedRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDecompressedRadioButtonActionPerformed
        compressionRadioButtonModified();
    }//GEN-LAST:event_removeDecompressedRadioButtonActionPerformed

    private void compressAndKeepOrigRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compressAndKeepOrigRadioButtonActionPerformed
        compressionRadioButtonModified();
    }//GEN-LAST:event_compressAndKeepOrigRadioButtonActionPerformed

    private void compressAndRemoveOrigRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compressAndRemoveOrigRadioButtonActionPerformed
        compressionRadioButtonModified();
    }//GEN-LAST:event_compressAndRemoveOrigRadioButtonActionPerformed

    private void notCompressRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notCompressRadioButtonActionPerformed
        compressionRadioButtonModified();
    }//GEN-LAST:event_notCompressRadioButtonActionPerformed

    private void parameterVariationOneParameterRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parameterVariationOneParameterRadioButtonActionPerformed
        paneSettingMiddleAreaRadioButtonUpdated();
        // TODO add your handling code here:

        //        if (parameterVariationOneParameterRadioButton.isSelected()) {
        //            SimulationSettings.numParameters = 1;
        //            editParameter1Button.setEnabled(true);
        //        }
    }//GEN-LAST:event_parameterVariationOneParameterRadioButtonActionPerformed

    private void runOnlyOneBatchRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runOnlyOneBatchRadioButtonActionPerformed

        paneSettingMiddleAreaRadioButtonUpdated();

        //        if (runOnlyOneBatchRadioButton.isSelected()) {
        //            SimulationSettings.numParameters = 0;
        //            editParameter1Button.setEnabled(false);
        //        }
    }//GEN-LAST:event_runOnlyOneBatchRadioButtonActionPerformed

    private void changeParameterSetupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeParameterSetupButtonActionPerformed
        new JDialogParameterSetup(ModelParameterSettings.modelParameters);
    }//GEN-LAST:event_changeParameterSetupButtonActionPerformed

    private void numberOfIterationsTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_numberOfIterationsTextFieldCaretUpdate
        String input = numberOfIterationsTextField.getText();

        if (input.length() == 0) {
            input = "0";
        }

        //String input = fieldNumIterations.getText();
        try {

            SimulationSettings.numIterations = Integer.parseInt(input);
            //fieldNumIterations.selectAll();
            System.out.println("numIterations: " + SimulationSettings.numIterations);

            for (int i = 0; i < PlottingSettings.listOfSingleTimeSeries.size(); i++) {

                PlottingSettings.listOfSingleTimeSeries.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);

            }

            for (int i = 0; i < PlottingSettings.listOfMultipleTimeSeries.size(); i++) {

                PlottingSettings.listOfMultipleTimeSeries.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);

            }

            for (int i = 0; i < PlottingSettings.listOfHeatmaps.size(); i++) {

                PlottingSettings.listOfHeatmaps.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);

            }

            boolean above = false;

            for (int i = 0; i < PlottingSettings.defaultsBoxplots.iterations.size(); i++) {

                if (Integer.parseInt(PlottingSettings.defaultsBoxplots.iterations.get(i).iteration) > (int) Math.floor(SimulationSettings.numIterations)) {

                    if (!above) {
                        PlottingSettings.defaultsBoxplots.iterations.get(i).iteration = Integer.toString((int) Math.floor(SimulationSettings.numIterations));
                    } else {
                        PlottingSettings.defaultsBoxplots.iterations.remove(i);
                        i--;
                    }
                    above = true;

                }

            }

            boolean above2 = false;

            for (int i = 0; i < PlottingSettings.defaultsHistogram.iterations.size(); i++) {

                if (Integer.parseInt(PlottingSettings.defaultsHistogram.iterations.get(i).iteration) > (int) Math.floor(SimulationSettings.numIterations)) {

                    if (!above2) {
                        PlottingSettings.defaultsHistogram.iterations.get(i).iteration = Integer.toString((int) Math.floor(SimulationSettings.numIterations));
                    } else {
                        PlottingSettings.defaultsHistogram.iterations.remove(i);
                        i--;
                    }
                    above2 = true;

                }

            }

            for (int i = 0; i < PlottingSettings.listOfHeatmaps.size(); i++) {

                PlottingSettings.listOfHeatmaps.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);

            }

            PlottingSettings.defaultsHeatmaps.tmax = (int) Math.floor(SimulationSettings.numIterations);

            PlottingSettings.defaultsSingleTimeSeries.tmax = (int) Math.floor(SimulationSettings.numIterations);
            PlottingSettings.defaultsMultipleTimeSeries.tmax = (int) Math.floor(SimulationSettings.numIterations);

        } catch (NumberFormatException nFE) {
            JOptionPane.showMessageDialog(null, "Not an integer");
        }

        simulationSettingsTestLabel.setText(
                "" + SimulationSettings.numIterations);
    }//GEN-LAST:event_numberOfIterationsTextFieldCaretUpdate

    private void numberOfBatchRunsTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_numberOfBatchRunsTextFieldCaretUpdate
        // TODO add your handling code here:
        String input = numberOfBatchRunsTextField.getText();
        if (input.length() == 0) {
            input = "0";
        }

        try {
            SimulationSettings.numBatchRuns = Integer.parseInt(input);
            //System.out.println("NumBatch runs: " + SimulationSettings.numBatchRuns);
            //            numberOfBatchRunsTextField.selectAll();
        } catch (NumberFormatException nFE) {
            JOptionPane.showMessageDialog(null, "Not an integer");
        }
        simulationSettingsTestLabel.setText("" + SimulationSettings.numBatchRuns);
    }//GEN-LAST:event_numberOfBatchRunsTextFieldCaretUpdate

    private void runSimulationsRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runSimulationsRadioButtonActionPerformed
        if (runSimulationsRadioButton.isSelected()) {
            SimulationSettings.DO_RUN = 1;
        }
        simulationSettingsTestLabel.setText("" + SimulationSettings.DO_RUN);
    }//GEN-LAST:event_runSimulationsRadioButtonActionPerformed

    private void doNotRunSimulationsRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doNotRunSimulationsRadioButtonActionPerformed
        // TODO add your handling code here:
        if (doNotRunSimulationsRadioButton.isSelected()) {
            SimulationSettings.DO_RUN = 0;
        }
        simulationSettingsTestLabel.setText("" + SimulationSettings.DO_RUN);
    }//GEN-LAST:event_doNotRunSimulationsRadioButtonActionPerformed

    private void yesWriteAllDatabaseRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesWriteAllDatabaseRadioButtonActionPerformed
        if (yesWriteAllDatabaseRadioButton.isSelected()) {
            SimulationSettings.saveAllAgentVariables = true;
        } else {
            SimulationSettings.saveAllAgentVariables = false;
        }
        simulationSettingsTestLabel.setText("" + SimulationSettings.saveAllAgentVariables);

    }//GEN-LAST:event_yesWriteAllDatabaseRadioButtonActionPerformed

    private void noWriteAllDatabaseRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noWriteAllDatabaseRadioButtonActionPerformed
        if (yesWriteAllDatabaseRadioButton.isSelected()) {
            SimulationSettings.saveAllAgentVariables = true;
        } else {
            SimulationSettings.saveAllAgentVariables = false;
        }
        simulationSettingsTestLabel.setText("" + SimulationSettings.saveAllAgentVariables);
    }//GEN-LAST:event_noWriteAllDatabaseRadioButtonActionPerformed

    private void runExperimentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runExperimentButtonActionPerformed
//        MultiGUI.ExecuteSimulations exeSim;
//        exeSim = new MultiGUI.ExecuteSimulations();
//        exeSim.start();        // TODO add your handling code here:
    }//GEN-LAST:event_runExperimentButtonActionPerformed

    void readSettingsFromFile() {
        //new SimulationSettings();
        SimulationSettings.WORKING_DIRECTORY = WorkspaceLauncher.workspacePath;
        LoadSettings(SimulationSettings.WORKING_DIRECTORY + "/GlobalSettings.xml");

    }

    /**
     * took out to make possible referencing from inner class in one of the
     * listeners.
     */
    JScrollPane settingScrollPane, plottingScrollPane;

    void addOldSubTabsInNewTabPlottingPage() {
        final TabSingleTimeSeries tabSingleTimeSeries;
        TabMultipleTimeSeries tabMultipleTimeSeries;
        final TabDistributions tabDistributions;
        JScrollPane plottingScrollPane, plottingScrollPane2, plottingScrollPane3;

        tabSingleTimeSeries = new TabSingleTimeSeries();
        tabMultipleTimeSeries = new TabMultipleTimeSeries();
        tabDistributions = new TabDistributions();

        plottingScrollPane = new JScrollPane(tabSingleTimeSeries);
        plottingScrollPane.setPreferredSize(new Dimension(700, 600));

        plottingScrollPane2 = new JScrollPane(tabMultipleTimeSeries);
        plottingScrollPane2.setPreferredSize(new Dimension(700, 600));

        plottingScrollPane3 = new JScrollPane(tabDistributions);
        plottingScrollPane3.setPreferredSize(new Dimension(700, 600));

        jTabbedPane3.addTab("Time Series", plottingScrollPane);
        jTabbedPane3.addTab("Multiple Time Series", plottingScrollPane2);
        jTabbedPane3.addTab("Agent Distributions", plottingScrollPane3);

        jTabbedPane3.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {

                int selectedIndex = jTabbedPane3.getSelectedIndex();

                if (selectedIndex == 0) {

                    tabSingleTimeSeries.redrawAllTables();

                } else if (selectedIndex == 2) {

                    tabDistributions.redrawAllTables();

                }

            }
        });
        //add(plottingTabbedPane, BorderLayout.NORTH);

    }

    /**
     * this method duplicates the functionality of original method panel2 from
     * TabPlotting class. The purpose of rewriting it is to concentrate on the
     * functionality (e.g. updating PlottingSettings) and to separate design as
     * much as only possible.
     */
    void addOldBottomPanelFunctionalityInNewTabPlottingPage() {

        /*Default values:*/
        singleRunAnalysisCheckboxNew.setSelected(PlottingSettings.singleRunAnalyis);
        batchRunCheckBoxNew.setSelected(PlottingSettings.batchRunAnalyis);
        parameterAnalysisCheckBoxNew.setSelected(PlottingSettings.parameterAnalyis);

        transitionPhaseTextFieldNew.setText(Integer.toString(PlottingSettings.transitionPhase));
        addLegendCheckboxNew.setSelected(PlottingSettings.addLegend);
        coloredPlotsCheckboxNew.setSelected(PlottingSettings.coloured);

        if (PlottingSettings.fileTypePlots.equals("pdf")) {
                fileTypePlotsComboBox.setSelectedIndex(0);
        }else if (PlottingSettings.fileTypePlots.equals("eps")){
                fileTypePlotsComboBox.setSelectedIndex(1);
        }else fileTypePlotsComboBox.setSelectedIndex(2);


    }

    void addOldSumulationPanelFunctionalityInNewSimulationTab() {
        if (SimulationSettings.DO_RUN == 1) {
            runSimulationsRadioButton.setSelected(true);
        } else {
            doNotRunSimulationsRadioButton.setSelected(true);
        }
        numberOfBatchRunsTextField.setText(Integer.toString(SimulationSettings.numBatchRuns));
        numberOfIterationsTextField.setText(Integer.toString(SimulationSettings.numIterations));

        editParameter1Button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new JDialogEditParameter1();
                drawParameterTables();
            }
        });

        if (SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL == 1) {
            compressAndKeepOrigRadioButton.setSelected(true);
        } else if (SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL == 1) {
            compressAndRemoveOrigRadioButton.setSelected(true);
        } else if (SimulationSettings.DO_REMOVE_DB == 1) {
            removeDecompressedRadioButton.setSelected(true);
        } else if (SimulationSettings.DO_DECOMPRESS == 1) {
            decompressRadioButton.setSelected(true);
        } else {
            notCompressRadioButton.setSelected(true);
        }

        if (SimulationSettings.saveAllAgentVariables) {
            yesWriteAllDatabaseRadioButton.setSelected(true);
        } else {
            noWriteAllDatabaseRadioButton.setSelected(true);
        }

    }

    void oldFillMenu_FromMultiGUIConstructor() {
        //menuBar = new JMenuBar();
//        JMenu menuExperiment = new JMenu("Experiment");
        JMenu menu1 = new JMenu("Experiment");
        JMenuItem loadExperiment, newExperiment, saveExperiment, saveExperimentAs, exitGUI;
        JMenuItem runBatchExperiments;
        JMenu menuSettings, setPathes;
        final JMenuItem setPathModelXML, setExecutable, setZeroXMLFile, setPathRScripts, setPathXparser;
        JMenu menuImportExport, menuimportPlottingSections, menuimportParameterSections;
        JMenuItem importPlottingSettings, exportPlottingSettings, importParameterSettings, exportParameterSettings;
        final JTabbedPane mainTabPane = null;

        menuBar.add(menu1);

        //menuExperiment = new JMenu("Experiment");
        //  menuBar.add(jMenu1);
        // menuBar = new JMenuBar();
        //menuExperiment = new JMenu("Experiment");
        //JMenu menuExperiment = jMenu1;
        menuBar.add(menu1);

        newExperiment = new JMenuItem("New");
        loadExperiment = new JMenuItem("Load");
        saveExperiment = new JMenuItem("Save");
        saveExperimentAs = new JMenuItem("Save as...");

        exitGUI = new JMenuItem("Exit");

        menu1.add(newExperiment);

        newExperiment.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY, true, false, "", false);
                chooseFile.openFileChooser();
                SimulationSettings.WORKING_DIRECTORY = chooseFile.getDirectoryOrFile();

                /*Set up agent list*/
                //AgentSettings.agents = new ArrayList<Agent>();
                SimulationSettings.experimentBuilt = false;
                runExperimentButton.setEnabled(SimulationSettings.experimentBuilt);

            }

        });

        menu1.add(loadExperiment);

        loadExperiment.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY, true, false, "", false);
                chooseFile.openFileChooser();
                SimulationSettings.WORKING_DIRECTORY = chooseFile.getDirectoryOrFile();
                //menuExperiment.add(chooseFile);

                /*Read specific experiment settings*/
                File file = new File(SimulationSettings.WORKING_DIRECTORY + "/GlobalSettings.xml");

                FileReader fr;

                String testString;

                try {

                    fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);

                    testString = br.readLine();
                    System.out.println(testString);

                    boolean isEmpty = false;

                    if (testString == null) {
                        isEmpty = true;
                    }

                    System.out.println(isEmpty + testString);

                    br.close();

                    /*Read Settings*/
                    LoadSettings(SimulationSettings.WORKING_DIRECTORY + "/GlobalSettings.xml");

                    drawAgentTable();
                    settingContainer.drawTableParemters();

                    if (SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL == 1) {
                        settingContainer.doCompressKeepOriginal.setSelected(true);
                        settingContainer.doCompressRemoveOriginal.setSelected(false);
                        settingContainer.doNotCompress.setSelected(false);

                    } else if (SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL == 1) {

                        settingContainer.doCompressKeepOriginal.setSelected(false);
                        settingContainer.doCompressRemoveOriginal.setSelected(true);
                        settingContainer.doNotCompress.setSelected(false);

                    } else {

                        settingContainer.doCompressKeepOriginal.setSelected(false);
                        settingContainer.doCompressRemoveOriginal.setSelected(false);
                        settingContainer.doNotCompress.setSelected(true);
                    }

                    if (SimulationSettings.saveAllAgentVariables) {
                        settingContainer.rbNoStoreAll.setSelected(false);
                        settingContainer.rbYesStoreAll.setSelected(true);
                    } else {

                        settingContainer.rbNoStoreAll.setSelected(true);
                        settingContainer.rbYesStoreAll.setSelected(false);

                    }

                } catch (IOException e1) {

                    JOptionPane.showMessageDialog(null, "XML File with saved settings not found. Please enter the settings manually");
                }

            }

        });

        /*Save Experiment:*/
        menu1.add(saveExperiment);

        saveExperiment.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                /*Save Settings*/
                SaveSettings();
            }

        });

        /*Save as...Experiment:*/
        menu1.add(saveExperimentAs);

        saveExperimentAs.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY, true, false, "", false);
                chooseFile.openFileChooser();
                SimulationSettings.WORKING_DIRECTORY = chooseFile.getDirectoryOrFile();

                /*Save Settings*/
                SaveSettings();

            }

        });

        /*menu to import and export settings: plotting settings and initial parameters*/
        menuImportExport = new JMenu("Import/Export");

        menuimportPlottingSections = new JMenu("Plotting Settings");

        importPlottingSettings = new JMenuItem("Import Plotting Settings");
        exportPlottingSettings = new JMenuItem("Export Plotting Settings");

        importPlottingSettings.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY, false, true, "xml", false);
                    chooseFile.openFileChooser();

                    String file = chooseFile.getDirectoryOrFile();

                    File settingsFile = new File(chooseFile.getDirectoryOrFile());

                    if (!settingsFile.exists()) {

                        throw new FileNotFoundException();

                    }

                    ReadXMLFile defValues = new ReadXMLFile(file);
                    defValues.new ReadClassFromXML(new PlottingSettings(), "plottingSettings");

                    for (int i = 0; i < PlottingSettings.listOfAgentsVariableInstances.size(); i++) {

                        for (int j = 0; j < AgentSettings.agents.size(); j++) {

                            if (PlottingSettings.listOfAgentsVariableInstances.get(i).agentName.equals(AgentSettings.agents.get(j).agentName)) {

                                /*Check if variable is there:*/
                                for (int k = 0; k < PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size(); k++) {

                                    if (PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(k).isVariable) {

                                        boolean found = false;

                                        for (int l = 0; l < AgentSettings.agents.get(j).variableList.size(); l++) {

                                            AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = false;

                                            if (PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(k).variable.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)) {

                                                AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = true;
                                                found = true;
                                                break;
                                            }

                                        }

                                        if (!found) {

                                            PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.remove(k);
                                            k--;

                                        }

                                    } else {
                                        /*If agent ratio*/

                                        boolean numeratorFound = false;
                                        boolean denominatorFound = false;

                                        for (int l = 0; l < AgentSettings.agents.get(j).variableList.size(); l++) {

                                            if (PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(k).agentRatio.numerator.equals(AgentSettings.agents.get(j).variableList.get(l).name)) {
                                                numeratorFound = true;
                                            } else if (PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(k).agentRatio.denominator.equals(AgentSettings.agents.get(j).variableList.get(l).name)) {

                                                denominatorFound = true;

                                            }

                                        }

                                        /*If either numerator or denominator not found remove agent ratio*/
                                        if (!numeratorFound || !denominatorFound) {

                                            PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.remove(k);
                                            k--;

                                        }

                                    }

                                }

                            }

                        }

                    }

                    /*Check histograms*/
                    for (int i = 0; i < PlottingSettings.listOfHistograms.size(); i++) {

                        for (int j = 0; j < AgentSettings.agents.size(); j++) {

                            if (PlottingSettings.listOfHistograms.get(i).histBelongsTo.equals(AgentSettings.agents.get(j).agentName)) {

                                /*Check if variable is there:*/
                                if (PlottingSettings.listOfHistograms.get(i).isVariable) {

                                    boolean found = false;

                                    for (int l = 0; l < AgentSettings.agents.get(j).variableList.size(); l++) {

                                        AgentSettings.agents.get(j).variableList.get(l).isSelectedForHistograms = false;

                                        if (PlottingSettings.listOfHistograms.get(i).variable.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)) {

                                            AgentSettings.agents.get(j).variableList.get(l).isSelectedForHistograms = true;
                                            found = true;
                                            break;
                                        }

                                    }

                                    if (!found) {

                                        PlottingSettings.listOfHistograms.remove(i);
                                        i--;

                                    }

                                } else {
                                    /*If agent ratio*/

                                    boolean numeratorFound = false;
                                    boolean denominatorFound = false;

                                    for (int l = 0; l < AgentSettings.agents.get(j).variableList.size(); l++) {

                                        if (PlottingSettings.listOfHistograms.get(i).agentRatio.numerator.equals(AgentSettings.agents.get(j).variableList.get(l).name)) {
                                            numeratorFound = true;
                                        } else if (PlottingSettings.listOfHistograms.get(i).agentRatio.denominator.equals(AgentSettings.agents.get(j).variableList.get(l).name)) {

                                            denominatorFound = true;

                                        }

                                    }

                                    /*If either numerator or denominator not found remove agent ratio*/
                                    if (!numeratorFound || !denominatorFound) {

                                        PlottingSettings.listOfHistograms.remove(i);
                                        i--;

                                    }

                                }

                            }

                        }

                    }

                    /*Check Boxplots*/
                    for (int i = 0; i < PlottingSettings.listOfBoxplots.size(); i++) {

                        for (int j = 0; j < AgentSettings.agents.size(); j++) {

                            if (PlottingSettings.listOfBoxplots.get(i).histBelongsTo.equals(AgentSettings.agents.get(j).agentName)) {

                                /*Check if variable is there:*/
                                if (PlottingSettings.listOfBoxplots.get(i).isVariable) {

                                    boolean found = false;

                                    for (int l = 0; l < AgentSettings.agents.get(j).variableList.size(); l++) {

                                        AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = false;

                                        if (PlottingSettings.listOfBoxplots.get(i).variable.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)) {

                                            AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = true;
                                            found = true;
                                            break;
                                        }

                                    }

                                    if (!found) {

                                        PlottingSettings.listOfBoxplots.remove(i);
                                        i--;

                                    }

                                } else {
                                    /*If agent ratio*/

                                    boolean numeratorFound = false;
                                    boolean denominatorFound = false;

                                    for (int l = 0; l < AgentSettings.agents.get(j).variableList.size(); l++) {

                                        if (PlottingSettings.listOfBoxplots.get(i).agentRatio.numerator.equals(AgentSettings.agents.get(j).variableList.get(l).name)) {
                                            numeratorFound = true;
                                        } else if (PlottingSettings.listOfBoxplots.get(i).agentRatio.denominator.equals(AgentSettings.agents.get(j).variableList.get(l).name)) {

                                            denominatorFound = true;

                                        }

                                    }

                                    /*If either numerator or denominator not found remove agent ratio*/
                                    if (!numeratorFound || !denominatorFound) {

                                        PlottingSettings.listOfBoxplots.remove(i);
                                        i--;

                                    }

                                }

                            }

                        }

                    }

                    plottingContainer.removeAll();

                    plottingContainer = new TabPlotting();

                    plottingContainer.validate();

                    plottingScrollPane = new JScrollPane(plottingContainer);
                    plottingScrollPane.setPreferredSize(new Dimension(800, 1200));

                    /*Add Scroll pane to JFrame*/
                    //add(guiScrollPane);
                    mainTabPane.remove(1);

                    mainTabPane.add(plottingScrollPane, "Plotting Settings");

                    validate();

                } catch (Exception ex) {

                    System.out.println(ex);

                }

            }

        });

        exportPlottingSettings.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY, false, true, "xml", true);
                chooseFile.openFileChooser();

                WriteSettingsXMLFile exportPloSettings;

                if (chooseFile.getDirectoryOrFile().endsWith(".xml")) {
                    exportPloSettings = new WriteSettingsXMLFile("", chooseFile.getDirectoryOrFile(), "plottingSettings");
                } else {
                    exportPloSettings = new WriteSettingsXMLFile("", chooseFile.getDirectoryOrFile() + ".xml", "plottingSettings");
                }

                exportPloSettings.createXMLFile();

                exportPloSettings.new WriteClassFromXML(new PlottingSettings(), "plottingSettings", false);

            }

        });

        menuimportPlottingSections.add(importPlottingSettings);
        menuimportPlottingSections.add(exportPlottingSettings);

        menuImportExport.add(menuimportPlottingSections);

        menuimportParameterSections = new JMenu("Parameter Settings");

        importParameterSettings = new JMenuItem("Import Parameter Settings");
        exportParameterSettings = new JMenuItem("Export Parameter Settings");

        importParameterSettings.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY, false, true, "xml", false);
                    chooseFile.openFileChooser();

                    String file = chooseFile.getDirectoryOrFile();

                    File settingsFile = new File(chooseFile.getDirectoryOrFile());

                    if (!settingsFile.exists()) {

                        throw new FileNotFoundException();

                    }

                    ReadXMLFile defValues = new ReadXMLFile(file);

                    defValues.new ReadClassFromXML(new ModelParameterSettings(), "parameterSettings");

                    new JDialogParameterSetup(ModelParameterSettings.modelParameters);

                } catch (Exception ex) {

                    System.out.println(ex);

                }

            }

        });

        exportParameterSettings.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY, false, true, "xml", true);
                chooseFile.openFileChooser();

                WriteSettingsXMLFile exportPloSettings;

                if (chooseFile.getDirectoryOrFile().endsWith(".xml")) {
                    exportPloSettings = new WriteSettingsXMLFile("", chooseFile.getDirectoryOrFile(), "parameterSettings");
                } else {
                    exportPloSettings = new WriteSettingsXMLFile("", chooseFile.getDirectoryOrFile() + ".xml", "parameterSettings");
                }

                exportPloSettings.createXMLFile();

                exportPloSettings.new WriteClassFromXML(new ModelParameterSettings(), "parameterSettings", false);

            }

        });

        menuimportParameterSections.add(importParameterSettings);
        menuimportParameterSections.add(exportParameterSettings);

        menuImportExport.add(menuimportParameterSections);

        menu1.add(menuImportExport);

        runBatchExperiments = new JMenuItem("Run Batch");

        menu1.add(runBatchExperiments);

        runBatchExperiments.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                new JDialogBatchExperiments();

            }

        });

        menu1.add(runBatchExperiments);

        menu1.add(exitGUI);
        exitGUI.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                Object text = "Do you want to save the settings before quitting? \n";

                int choice = JOptionPane.showConfirmDialog(null, text, "Exit GUI", JOptionPane.YES_NO_CANCEL_OPTION);

                if (choice == 0) {

                    /*Choice is yes*/
                    String PathToFile = new String(SimulationSettings.WORKING_DIRECTORY + "/SimParameter.sh");

                    System.out.println(SimulationSettings.WORKING_DIRECTORY);
                    System.out.println(PathToFile);

                    /*Safe the settings to XML file*/
                    SaveSettings();

                    setVisible(false);
                    dispose();

                    System.exit(-1);

                } else if (choice == 1) {

                    /*Choice is no*/
                    setVisible(false);
                    dispose();

                    System.out.println("agents:   " + AgentSettings.agents.get(0).agentName);

                    System.exit(-1);

                }

            }
        });

        menuSettings = new JMenu("Settings");
        menuBar.add(menuSettings);

        setPathes = new JMenu("Set Pathes");

        setPathModelXML = new JMenuItem("Set Path to Model.xml file");
        setPathModelXML.setToolTipText("Current path: " + SimulationSettings.EURACE_MODEL_XML);
        setExecutable = new JMenuItem("Set Model Executable");
        setExecutable.setToolTipText("Current Executable: " + SimulationSettings.MAIN_EXECUTABLE);
        setZeroXMLFile = new JMenuItem("Set initial Data File (0.xml)");
        setZeroXMLFile.setToolTipText("Current initial Data File: " + SimulationSettings.ZERO_XML_FILE);
        setPathRScripts = new JMenuItem("Set path to R Scripts");
        setPathRScripts.setToolTipText("Current path to R Scripts: " + SimulationSettings.PATH_TO_RSCRIPTS);
        setPathXparser = new JMenuItem("Set path to xparser");
        setPathXparser.setToolTipText("Current path to xparser: " + SimulationSettings.PATH_TO_XPARSER);

        setPathModelXML.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                String pathBefore;

                FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML, false, true, "xml", false);
                chooseFile.openFileChooser();

                pathBefore = SimulationSettings.EURACE_MODEL_XML;

                SimulationSettings.EURACE_MODEL_XML = chooseFile.getDirectoryOrFile();
                setPathModelXML.setToolTipText("Current path: " + SimulationSettings.EURACE_MODEL_XML);

                if (!pathBefore.equals(SimulationSettings.EURACE_MODEL_XML)) {
                    /*Set agent list*/
                    agentList = ReadAgentListFromModelXML();
                    AgentSettings.agents = new ArrayList<Agent>();

                    PlottingSettings.listOfAgentsVariableInstances = new ArrayList<PlottingSettings.Agent>();

                    /*List of ratio Instances*/
                    PlottingSettings.listOfRatioInstances = new ArrayList<PlottingSettings.RatioInstance>();

                    /*List of time series*/
                    PlottingSettings.listOfSingleTimeSeries = new ArrayList<PlottingSettings.SingleTimeSeries>();
                    PlottingSettings.defaultsSingleTimeSeries = (new PlottingSettings()).new DefaulSingleTimeSeriesSettings();

                    /*List of multiple time series*/
                    PlottingSettings.listOfMultipleTimeSeries = new ArrayList<PlottingSettings.MultipleTimeSeries>();
                    PlottingSettings.defaultsMultipleTimeSeries = (new PlottingSettings()).new DefaulMultipleTimeSeriesSettings();

                    /*List of multiple time series*/
                    PlottingSettings.listOfHistograms = new ArrayList<PlottingSettings.Histogram>();
                    PlottingSettings.defaultsHistogram = (new PlottingSettings()).new DefaultSettingsHistogram();

                    /*List of multiple time series*/
                    PlottingSettings.listOfBoxplots = new ArrayList<PlottingSettings.Boxplots>();
                    PlottingSettings.defaultsBoxplots = (new PlottingSettings()).new DefaultSettingsBoxplots();


                    /*List of multiple time series*/
                    PlottingSettings.listOfHeatmaps = new ArrayList<PlottingSettings.Heatmaps>();
                    PlottingSettings.defaultsHeatmaps = (new PlottingSettings()).new DefaultSettingsHeatmaps();

                    /*List of multiple time series*/
                    PlottingSettings.listOfScatterPlots = new ArrayList<PlottingSettings.ScatterPlots>();
                    PlottingSettings.defaultsScatterPlots = (new PlottingSettings()).new DefaultSettingsScatterPlots();

                    for (int i = 0; i < agentList.size(); i++) {

                        AgentSettings.agents.add(new Agent(agentList.get(i)));
                        PlottingSettings.Agent temAgent = (new PlottingSettings()).new Agent(agentList.get(i));
                        PlottingSettings.listOfAgentsVariableInstances.add(temAgent);

                    }

                    /*Read Model parameters*/
                    ReadModelParameter modelXML = new ReadModelParameter();
                    modelXML.getFIleListDirectlyFromEuraceModelXML();
                    ModelParameterSettings.modelParameters = modelXML.GetModelParameterFromModelXMLFiles();

                    plottingContainer = new TabPlotting();
                    settingContainer = new TabSettings();

                    plottingContainer.validate();
                    settingContainer.validate();


                    /*Add GUI container to Scroll pane*/
                    settingScrollPane = new JScrollPane(settingContainer);
                    settingScrollPane.setPreferredSize(new Dimension(800, 1200));

                    plottingScrollPane = new JScrollPane(plottingContainer);
                    plottingScrollPane.setPreferredSize(new Dimension(800, 1200));

                    /*Add Scroll pane to JFrame*/
                    //add(guiScrollPane);
                    mainTabPane.remove(1);

                    mainTabPane.remove(0);

                    mainTabPane.add(settingScrollPane, "Simulation Settings");

                    mainTabPane.add(plottingScrollPane, "Plotting Settings");

                    validate();

                    validate();
                }

            }
        });

        setExecutable.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                FileChooserFromMenuList chooseFile;

                if (SimulationSettings.MAIN_EXECUTABLE.equals(".")) {
                    chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML, false, false, "", false);
                } else {
                    chooseFile = new FileChooserFromMenuList(SimulationSettings.MAIN_EXECUTABLE, false, false, "", false);
                }

                chooseFile.openFileChooser();
                SimulationSettings.MAIN_EXECUTABLE = chooseFile.getDirectoryOrFile();
                setExecutable.setToolTipText("Current Executable: " + SimulationSettings.MAIN_EXECUTABLE);

            }
        });

        setZeroXMLFile.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                FileChooserFromMenuList chooseFile;

                if (SimulationSettings.ZERO_XML_FILE.equals("0.xml")) {
                    chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML, false, true, "xml", false);
                } else {
                    chooseFile = new FileChooserFromMenuList(SimulationSettings.ZERO_XML_FILE, false, true, "xml", false);
                }

                chooseFile.openFileChooser();
                SimulationSettings.ZERO_XML_FILE = chooseFile.getDirectoryOrFile();
                setExecutable.setToolTipText("Current Executable: " + SimulationSettings.ZERO_XML_FILE);

            }
        });

        setPathRScripts.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.PATH_TO_RSCRIPTS, true, false, "", false);
                chooseFile.openFileChooser();
                SimulationSettings.PATH_TO_RSCRIPTS = chooseFile.getDirectoryOrFile();
                setPathRScripts.setToolTipText("Current pat: " + SimulationSettings.PATH_TO_RSCRIPTS);

            }
        });

        setPathXparser.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.PATH_TO_XPARSER, false, false, "", false);
                chooseFile.openFileChooser();
                SimulationSettings.PATH_TO_XPARSER = chooseFile.getDirectoryOrFile();
                setPathXparser.setToolTipText("Current path: " + SimulationSettings.PATH_TO_XPARSER);

            }
        });

        setPathes.add(setPathModelXML);
        setPathes.add(setExecutable);
        setPathes.add(setZeroXMLFile);
        setPathes.add(setPathRScripts);
        setPathes.add(setPathXparser);

        menuSettings.add(setPathes);
      
    }

    /**
     * this is an attempt to reuse the functionality of the TabPlotting class
     * without committing major changes to the structure of code.
     */
    void OldTabExperimentInitialization() {
        settingScrollPane = new JScrollPane(settingContainer);
        settingScrollPane.setPreferredSize(new Dimension(800, 780));
        jTabbedPane2.addTab("Old Experiment Settings", settingScrollPane);
    }

    void OldTabPlottingInitialization() {
//        TabSingleTimeSeries timeSeriesPanel=new TabSingleTimeSeries();
//        timeSeriesPanel.redrawAllTables();
        plottingScrollPane = new JScrollPane(plottingContainer);
        plottingScrollPane.setPreferredSize(new Dimension(800, 780));
        jTabbedPane2.addTab("Old Plotting Settings", plottingScrollPane);

    }

    /**
     * here some changes will be made to the structure of the method compared to
     * the one appearing in the multi gui. In original multi gui there is an
     * instance settingContainer of tabSettings class that corresponds to the
     * subpanels of gui. Here no such additional class is used, all the GUI
     * components are generated automatically. Therefore reference to
     * settingContainer will be replaced by references to interface elements.
     *
     *
     * another change is related to the fact that in the first screen
     * single\batch run is not included. therefore the place in the
     * buildExperiment where it checks if check boxes related to
     * single\batch\parameter will be commented out for the sake of presentation
     * of project at current stage.
     *
     */
    private void buildExperimentButtonPressed() {
        SimulationSettings.numProcessors = Integer.parseInt(numberOfProcessorsComboBox.getSelectedItem().toString());

        /*Write shadow model xml file for selected data storage*/
        if (!SimulationSettings.saveAllAgentVariables) {
            try {
                OverwriteFlameXMLCFile overwrite = new OverwriteFlameXMLCFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        ShadowModelXML shadowFile = new ShadowModelXML();

        shadowFile.setFilterAndWeights();

        ShadowModelXML.agents.clear();

        for (int i = 0; i < AgentSettings.agents.size(); i++) {

            boolean found = false;

            for (int j = 0; j < AgentSettings.agents.get(i).variableList.size(); j++) {

                if (AgentSettings.agents.get(i).variableList.get(j).isSelectedForPlotting) {

                    if (!found) {
                        ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
                        found = true;
                        System.out.println(ShadowModelXML.agents.size());
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable("id", "int"));
                    }

                    if (!AgentSettings.agents.get(i).variableList.get(j).name.equals("id")) {
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name, AgentSettings.agents.get(i).variableList.get(j).type));
                    }

                } else if (AgentSettings.agents.get(i).variableList.get(j).isSelectedForHistograms) {
                    if (!found) {
                        ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
                        found = true;
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable("id", "int"));
                    }
                    if (!AgentSettings.agents.get(i).variableList.get(j).name.equals("id")) {
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name, AgentSettings.agents.get(i).variableList.get(j).type));
                    }
                } else if (AgentSettings.agents.get(i).variableList.get(j).isSelectedForBoxplots) {
                    if (!found) {
                        ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
                        found = true;
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable("id", "int"));
                    }
                    if (!AgentSettings.agents.get(i).variableList.get(j).name.equals("id")) {
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name, AgentSettings.agents.get(i).variableList.get(j).type));
                    }
                } else if (AgentSettings.agents.get(i).variableList.get(j).isSelectedFilter) {
                    if (!found) {
                        ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
                        found = true;
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable("id", "int"));
                    }

                    if (!AgentSettings.agents.get(i).variableList.get(j).name.equals("id")) {
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name, AgentSettings.agents.get(i).variableList.get(j).type));
                    }

                } else if (AgentSettings.agents.get(i).variableList.get(j).isSelectedWeighted) {
                    if (!found) {
                        ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
                        found = true;
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable("id", "int"));
                    }
                    if (!AgentSettings.agents.get(i).variableList.get(j).name.equals("id")) {
                        ShadowModelXML.agents.get(ShadowModelXML.agents.size() - 1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name, AgentSettings.agents.get(i).variableList.get(j).type));
                    }

                }

                /*TODO add further isselected booleans*/
            }
        }

        new CountAgents();

        WriteSettingsXMLFile shadowXMLFile = new WriteSettingsXMLFile("", "shadow_model.xml", "xmodel");
        shadowXMLFile.createXMLFile();
        shadowXMLFile.new WriteClassFromXML(shadowFile, "xmodel", true);

        WriteSettingsXMLFile experiment = new WriteSettingsXMLFile("", "experiment.xml", "experiment");
        experiment.createXMLFile();

        BatchExperiments.Experiment tempExp = (new BatchExperiments()).new Experiment();

        tempExp.path = SimulationSettings.WORKING_DIRECTORY;

        if (SimulationSettings.numParameters == 1) {
            tempExp.parameter1 = SimulationSettings.PARAMETER_1;
        }

        if (SimulationSettings.numParameters == 2) {
            tempExp.parameter1 = SimulationSettings.PARAMETER_1;
            tempExp.parameter2 = SimulationSettings.PARAMETER_2;
        }

        experiment.new WriteClassFromXML(tempExp, "experiment", false);

        WriteRInterface rInterface = new WriteRInterface();
        rInterface.writeVariableTXTFile();
        rInterface.writeRatioTXTFile();
        rInterface.writeGrowthrateTXTFile();
        rInterface.writeSingleTimeSeriesTXTFile();
        rInterface.writeMultipleTimeSeriesTXTFile();
        rInterface.writeHistogramTXTFile();
        rInterface.writeBoxplotsTXTFile();
        rInterface.writeHeatmapsTXTFile();
        rInterface.writeScatterTXTFile();

        rInterface.writeConfigureFile();

        AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY + "/variables.txt", SimulationSettings.PATH_TO_RSCRIPTS + "/Data_Files/variables.txt");
        AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY + "/time_series_data.txt", SimulationSettings.PATH_TO_RSCRIPTS + "/Data_Files/time_series_data.txt");
        AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY + "/multiple_time_series_data.txt", SimulationSettings.PATH_TO_RSCRIPTS + "/Data_Files/multiple_time_series_data.txt");
        AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY + "/boxplot_data.txt", SimulationSettings.PATH_TO_RSCRIPTS + "/Data_Files/boxplot_data.txt");
        AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY + "/histogram_data.txt", SimulationSettings.PATH_TO_RSCRIPTS + "/Data_Files/histogram_data.txt");

        AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY + "/Configure.r", SimulationSettings.PATH_TO_RSCRIPTS + "/Configure.r");

        AuxFunctions.copyfile("Bash_scripts/exp_script_1_setup.sh", SimulationSettings.WORKING_DIRECTORY + "/exp_script_1_setup.sh");
        AuxFunctions.copyfile("Bash_scripts/exp_script_2_specific.sh", SimulationSettings.WORKING_DIRECTORY + "/exp_script_2_specific.sh");
        AuxFunctions.copyfile("Bash_scripts/create_job_list.sh", SimulationSettings.WORKING_DIRECTORY + "/create_job_list.sh");
        AuxFunctions.copyfile("Bash_scripts/launch_job_list.sh", SimulationSettings.WORKING_DIRECTORY + "/launch_job_list.sh");
        AuxFunctions.copyfile("Bash_scripts/run.sh", SimulationSettings.WORKING_DIRECTORY + "/run.sh");
        AuxFunctions.copyfile("Bash_scripts/compress_db.sh", SimulationSettings.WORKING_DIRECTORY + "/compress_db.sh");
        AuxFunctions.copyfile("Bash_scripts/create_job_list_compress.sh", SimulationSettings.WORKING_DIRECTORY + "/create_job_list_compress.sh");
        AuxFunctions.copyfile("Bash_scripts/r_serial.sh", SimulationSettings.WORKING_DIRECTORY + "/r_serial.sh");

        if (SimulationSettings.saveAllAgentVariables) {
            AuxFunctions.copyfile("./Bash_scripts/gendb.py", SimulationSettings.WORKING_DIRECTORY + "/gendb.py");
        } else {
            AuxFunctions.copyfile("./Bash_scripts/gendb_special.py", SimulationSettings.WORKING_DIRECTORY + "/gendb.py");
            AuxFunctions.copyfile("shadow_model.xml", SimulationSettings.WORKING_DIRECTORY + "/shadow_model.xml");
        }

        AuxFunctions.copyfile("experiment.xml", SimulationSettings.WORKING_DIRECTORY + "/experiment.xml");

        String PathToFile = new String("launch.sh");

        System.out.println("before updating init");
        HashMap<String, String> dict = new HashMap<String, String>();

//                                                String slash="\\";                                                
        dict.put("<SimulationSettings.WORKING_DIRECTORY>",
                SimulationSettings.WORKING_DIRECTORY.replaceAll("\\\\", "\\\\\\\\"));
//                                                
        System.out.println(SimulationSettings.WORKING_DIRECTORY);
        System.out.println(dict);

        ShellTemplateUpdater updater = new ShellTemplateUpdater(dict);
        updater.update("template_files/launch_template.sh", "launch.sh");

        dict.put("<SimulationSettings.ZERO_XML_FILE>",
                SimulationSettings.ZERO_XML_FILE.replaceAll("\\\\", "\\\\\\\\"));
        dict.put("<SimulationSettings.numIterations>",
                SimulationSettings.numIterations + "");
        dict.put("<SimulationSettings.numProcessors>",
                SimulationSettings.numProcessors + "");

        //If one parameters is selected:
        // here the reference to settingContainer is replaced with reference to jRadioButton4:
        // if (settingContainer.parameterVariationOnePars.isSelected()) {
        if (parameterVariationOneParameterRadioButton.isSelected()) {
            dict.put("<NUM_PARS>", "1");
        } else {
            dict.put("<NUM_PARS>", "0");
        }

        dict.put("<SimulationSettings.numBatchRuns>",
                SimulationSettings.numBatchRuns + "");
        dict.put("<SimulationSettings.MAIN_EXECUTABLE>",
                SimulationSettings.MAIN_EXECUTABLE.replaceAll("\\\\", "\\\\\\\\"));

        if (SimulationSettings.saveAllAgentVariables) {
            dict.put("<MODEL_XML_FILE>",
                    SimulationSettings.EURACE_MODEL_XML.replaceAll("\\\\", "\\\\\\\\"));
        } else {
            dict.put("<MODEL_XML_FILE>",
                    (SimulationSettings.WORKING_DIRECTORY + "/shadow_model.xml'").replaceAll("\\\\", "\\\\\\\\"));
        }

        dict.put("<SimulationSettings.PATH_TO_RSCRIPTS>",
                (SimulationSettings.PATH_TO_RSCRIPTS + "/").replaceAll("\\\\", "\\\\\\\\"));
        dict.put("<SimulationSettings.DO_RUN>",
                SimulationSettings.DO_RUN + "");
        dict.put("<SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL>",
                SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL + "");
        dict.put("<SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL>",
                SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL + "");
        dict.put("<SimulationSettings.DO_DECOMPRESS>",
                SimulationSettings.DO_DECOMPRESS + "");
        dict.put("<SimulationSettings.DO_REMOVE_DB>",
                SimulationSettings.DO_REMOVE_DB + "");

        //
        // this is the second modification; after the comment 
        // is applied, r scripts will always be run.
        //if (plottingContainer.singleRunAnalyisCheckBox.isSelected() || plottingContainer.batchRunAnalyisCheckBox.isSelected() || plottingContainer.parameterAnalyisCheckBox.isSelected()) {
        if (true) {
            dict.put("<RUN_R_SCRIPTS>", "bash ./r_serial.sh");
        } else {
            dict.put("<RUN_R_SCRIPTS>", "#Don't run r scripts\\n#bash ./r_serial.sh");
        }

        updater.update("template_files/run_exp_template.sh", "run_exp.sh");

        String experimentSettings = "";
        /*
         similarly, here also reference to settingContainer is replaced
         */

//        if (settingContainer.parameterVariationOnePars.isSelected()) {
        if (parameterVariationOneParameterRadioButton.isSelected()) {
            //If one parameters is selected:	
            //Check if settings are correct, otherwise return warning message/
            try {
                SimulationSettings.PARAMETER_1.name.equals(null);

                SimulationSettings.PARAMETER_1.values.get(0);

                experimentSettings += "\nexport PARAMETER_1='" + SimulationSettings.PARAMETER_1.name + "'";

                String values1 = "";

                for (int i = 0; i < SimulationSettings.PARAMETER_1.values.size(); i++) {
                    values1 = values1 + " " + SimulationSettings.PARAMETER_1.values.get(i).value;
                }
                experimentSettings += "\nexport F1_values='" + values1 + "'"
                        + "\n"
                        + "export F1_values_b=(" + values1 + ")"
                        + "\n"
                        + "for i in ${!F1_values_b[*]};do export F1_values_b_$i='${F1_values_b[$i]}';done";
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Parameter 1 is not selected!");
            }

        } else {
            //If no parameters are selected:
            experimentSettings = "# No parameters selected";
        }
        dict.put("<ExperimentSettings>",
                (experimentSettings).replaceAll("\\\\", "\\\\\\\\"));

        updater.update("template_files/set_exp_template.sh", SimulationSettings.WORKING_DIRECTORY + "/set_exp.sh");

        WriteOutputXMLFile outputXML = new WriteOutputXMLFile(SimulationSettings.WORKING_DIRECTORY);
        outputXML.writeOutputToFile(AgentSettings.agents);
        outputXML.writeImportsToFile(SimulationSettings.ZERO_XML_FILE);

        WriteEnvironmentXMLFile environmentXMLFILE = new WriteEnvironmentXMLFile(SimulationSettings.WORKING_DIRECTORY);
        environmentXMLFILE.writeParametersToFile(ModelParameterSettings.modelParameters);
        SimulationSettings.experimentBuilt = true;
        runExperimentButton.setEnabled(SimulationSettings.experimentBuilt);
    }

    void LoadSettings(String file) {

        /*Return a warning message if the eurace model xml file is not there*/
        try {

            File settingsFile = new File(file);

            if (!settingsFile.exists()) {

                throw new FileNotFoundException();

            }

            ReadXMLFile defValues = new ReadXMLFile(file);

            System.out.println("BP0\n");

            defValues.new ReadClassFromXML(new SimulationSettings(), "SimulationSettings");

            System.out.println("BP1\n");

            File xmlFile = new File(SimulationSettings.EURACE_MODEL_XML);
            if (!xmlFile.exists()) {
                JOptionPane.showMessageDialog(null, "Set the correct path to the model.xml file!");
                FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML, false, true, "xml", false);
                chooseFile.openFileChooser();
                SimulationSettings.EURACE_MODEL_XML = chooseFile.getDirectoryOrFile();

            }

            /*Load list of model parameters */
            ReadModelParameter modelXML = new ReadModelParameter();
            modelXML.getFIleListDirectlyFromEuraceModelXML();


            /*Set the parameter values with those from saving file*/
            ArrayList<ModelParameter> tempModelParameter = new ArrayList<ModelParameter>();
            // tempModelParameter = defValues.readModelParameters();

            tempModelParameter = modelXML.GetModelParameterFromModelXMLFiles();

            defValues.new ReadClassFromXML(new ModelParameterSettings(), "modelParameters");

            for (int i = 0; i < ModelParameterSettings.modelParameters.size(); i++) {

                for (int j = 0; j < tempModelParameter.size(); j++) {

                    if (ModelParameterSettings.modelParameters.get(i).name.equals(tempModelParameter.get(j).name)) {

                        if (ModelParameterSettings.modelParameters.get(i).value != null) {

                            tempModelParameter.get(j).value = ModelParameterSettings.modelParameters.get(i).value;

                        }
                    }

                }

            }

            ModelParameterSettings.modelParameters = tempModelParameter;

            /*Set agentList by calling ReadAgentListFromModelXML*/
            agentList = ReadAgentListFromModelXML();

            defValues.new ReadClassFromXML(new AgentSettings(), "agentsSettings");

            /*Search the model.xml files: add variables if there are some new variables which are not contained in the settings; delete those
             *  which are not contained in the model.xml file*/
            /*First: Retrieve the current agent list from the model.xml file*/
            ReadAgentListFromModelXML agentList = new ReadAgentListFromModelXML(SimulationSettings.EURACE_MODEL_XML);
            agentList.GetAgentListModelXMLFiles();

            variableRemoved = false;

            /*Go through the agent list*/
            for (int i = 0; i < agentList.agentList.size(); i++) {

                boolean agentFound = false;

                for (int j = 0; j < AgentSettings.agents.size(); j++) {

                    if (agentList.agentList.get(i).equals(AgentSettings.agents.get(j).agentName)) {

                        agentFound = true;

                        Agent tempAgent = new Agent(agentList.agentList.get(i));

                        for (int k = 0; k < AgentSettings.agents.get(j).variableList.size(); k++) {

                            boolean varFound = false;

                            for (int l = 0; l < tempAgent.variableList.size(); l++) {

                                /*if a variable which is contained in the settings has been found in the var list of the temp agent -> remove it from the temp */
                                if (AgentSettings.agents.get(j).variableList.get(k).name.equals(tempAgent.variableList.get(l).name)) {

                                    varFound = true;
                                    tempAgent.variableList.remove(l);
                                    break;
                                }

                            }

                            /*if variable that is in the settings list has not been found in the current var list o the temp agent -> delete it from the settings list*/
                            if (!varFound) {

                                AgentSettings.agents.get(j).variableList.remove(k);
                                k--;

                                variableRemoved = true;

                            }

                        }

                        /*Add the remaining variables which have not been found in the settings to it*/
                        for (int l = 0; l < tempAgent.variableList.size(); l++) {

                            AgentSettings.agents.get(j).variableList.add(tempAgent.variableList.get(l));

                        }

                    }

                }

                /*if this agent is not found in the agent settings add this agent as a new item to the agent list*/
                if (!agentFound) {

                    AgentSettings.agents.add(new Agent(agentList.agentList.get(i)));

                }

            }

            defValues.new ReadClassFromXML(new PlottingSettings(), "PlottingSettings");

            if (variableRemoved) {

                PlottingSettings.removeDeletedVarsFromVariableInstanceList();

            }

        } catch (Exception e1) {

            JOptionPane.showMessageDialog(null, "No settings found. Select a model.xml File!");

            FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML, false, true, "xml", false);
            chooseFile.openFileChooser();
            SimulationSettings.EURACE_MODEL_XML = chooseFile.getDirectoryOrFile();

            JOptionPane.showMessageDialog(null, "Select executable!");

            FileChooserFromMenuList chooseFile2;

            if (SimulationSettings.MAIN_EXECUTABLE.equals(".")) {
                chooseFile2 = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML, false, false, "", false);
            } else {
                chooseFile2 = new FileChooserFromMenuList(SimulationSettings.MAIN_EXECUTABLE, false, false, "", false);
            }

            chooseFile2.openFileChooser();
            SimulationSettings.MAIN_EXECUTABLE = chooseFile2.getDirectoryOrFile();

            JOptionPane.showMessageDialog(null, "Select 0.xml file!");

            FileChooserFromMenuList chooseFile3;

            if (SimulationSettings.ZERO_XML_FILE.equals("0.xml")) {
                chooseFile3 = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML, false, true, "xml", false);
            } else {
                chooseFile3 = new FileChooserFromMenuList(SimulationSettings.ZERO_XML_FILE, false, true, "xml", false);
            }

            chooseFile3.openFileChooser();
            SimulationSettings.ZERO_XML_FILE = chooseFile3.getDirectoryOrFile();

            JOptionPane.showMessageDialog(null, "Select path to R scripts!");

            FileChooserFromMenuList chooseFile4 = new FileChooserFromMenuList(SimulationSettings.PATH_TO_RSCRIPTS, true, false, "", false);
            chooseFile4.openFileChooser();
            SimulationSettings.PATH_TO_RSCRIPTS = chooseFile4.getDirectoryOrFile();

            /*Set agent list*/
            agentList = ReadAgentListFromModelXML();
            AgentSettings.agents = new ArrayList<Agent>();
            for (int i = 0; i < agentList.size(); i++) {
                AgentSettings.agents.add(new Agent(agentList.get(i)));
                PlottingSettings.Agent temAgent = (new PlottingSettings()).new Agent(agentList.get(i));
                PlottingSettings.listOfAgentsVariableInstances.add(temAgent);

            }

            /*Read Model parameters*/
            ReadModelParameter modelXML = new ReadModelParameter();
            modelXML.getFIleListDirectlyFromEuraceModelXML();
            ModelParameterSettings.modelParameters = modelXML.GetModelParameterFromModelXMLFiles();

        }

    }

    ArrayList<String> ReadAgentListFromModelXML() {

        ArrayList<String> listOfAgents = new ArrayList<String>();

        try {

            /*Parse the eurace_model.xml*/
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            docFactory.setAttribute("http://xml.org/sax/features/namespaces", true);
            docFactory.setAttribute("http://xml.org/sax/features/validation", false);
            docFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            docFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            docFactory.setNamespaceAware(true);
            docFactory.setIgnoringElementContentWhitespace(false);
            docFactory.setIgnoringComments(false);
            docFactory.setValidating(false);

            docFactory.setValidating(false);
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(new File(SimulationSettings.EURACE_MODEL_XML));

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("xagent");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    listOfAgents.add(getTagValue("name", eElement));

                    //System.out.println("read agent witht name:"+getTagValue("name",eElement));
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return listOfAgents;

    }

    private String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = (Node) nlList.item(0);

        //    System.out.println("Check:   "+sTag+"   "+nValue);
        //nValue.equals(null);
        if (nValue == null) {
            return "No description avaiable";
        } else {
            return nValue.getNodeValue();
        }
    }

    void newDrawAgentTable() {

        String[] colHeaders = {"Agent", "Record", "Period", "Phase"};

        //AgentTableModel tabAgentsModel;
        tabAgentsModel = new AgentTableModel(colHeaders, AgentSettings.agents);

        wrapDrawStoreOptionTable(jTable1, tabAgentsModel, colHeaders);

//        DrawStoreOptionTable tabAgents = new DrawStoreOptionTable(tabAgentsModel, colHeaders);
        // jTable1=new DrawStoreOptionTable(tabAgentsModel, colHeaders);
//        jTable1.setModel(tabAgentsModel);
        //      jScrollPane1.add(tabAgents);
    }

    void drawAgentTable() {

//        try {
//
//            lcRightPanel.remove(listScrollAgentTable);
//            lcRightPanel.validate();
//        } catch (Exception e) {
//
//            System.out.println();
//
//        }
        String[] colHeaders = {"Agent", "Record", "Period", "Phase"};
        tabAgentsModel = new AgentTableModel(colHeaders, AgentSettings.agents);
        DrawStoreOptionTable tabAgents = new DrawStoreOptionTable(tabAgentsModel, colHeaders);

        listScrollAgentTable = new JScrollPane(tabAgents);
        listScrollAgentTable.setPreferredSize(new Dimension(280, 179));
        settingContainer.g.gridx = 0;
        settingContainer.g.gridy = 0;
        settingContainer.lcRightPanel.add(listScrollAgentTable, settingContainer.g);
        settingContainer.lcRightPanel.validate();

    }

    void SaveSettings() {

        /*Safe the settings to XML file*/
        System.out.println("Print WORKING_DIRECTORY" + SimulationSettings.WORKING_DIRECTORY);
        writeGeneralSettingsToFile wrSet = new writeGeneralSettingsToFile("PathToWorkspace.txt", false);

        wrSet.writeToFile(SimulationSettings.WORKING_DIRECTORY);

        WriteSettingsXMLFile xmlFileWD = new WriteSettingsXMLFile(SimulationSettings.WORKING_DIRECTORY, "GlobalSettings.xml", "StoredSettings");
        xmlFileWD.createXMLFile();

        //xmlFileWD.AddRootElement("PlottingSettings");
        //xmlFileWD.new WriteClassFromXML(new PlottingSettings(),"PlottingSettings");
        xmlFileWD.AddRootElement("PlottingSettings");
        xmlFileWD.new WriteClassFromXML(new PlottingSettings(), "PlottingSettings", false);

        xmlFileWD.AddRootElement("SimulationSettings");
        xmlFileWD.new WriteClassFromXML(new SimulationSettings(), "SimulationSettings", false);

        xmlFileWD.AddRootElement("bashExperiments");
        xmlFileWD.new WriteClassFromXML(new BatchExperiments(), "agentsSettings", false);

        xmlFileWD.AddRootElement("agentsSettings");
        xmlFileWD.new WriteClassFromXML(new AgentSettings(), "agentsSettings", false);

        /*Save the parameters only in the working directory*/
        xmlFileWD.AddRootElement("modelParameters");
        xmlFileWD.new WriteClassFromXML(new ModelParameterSettings(), "modelParameters", false);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditableGui.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditableGui.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditableGui.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditableGui.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditableGui().setVisible(true);
            }
        });
    }

    void paneSettingMiddleAreaRadioButtonUpdated() {
        ArrayList<SimulationSettings.Value> emptyArrayList = new ArrayList<SimulationSettings.Value>();
        final ParameterTableModel tabModel;
        final TableColumn col;

        if (runOnlyOneBatchRadioButton.isSelected()) {

            SimulationSettings.numParameters = 0;

            editParameter1Button.setEnabled(false);

//            rightPanel.remove(listScroll);
            //          rightPanel.validate();
            tabModel = new ParameterTableModel("", emptyArrayList, false);
            jTable2.setModel(tabModel);// = new JTable(tabModel);
            jTable2.getColumnModel().getColumn(0).setHeaderValue("");
            //listScroll = new JScrollPane(table);
            //listScroll.setPreferredSize(new Dimension(130, 150));
//            rp.gridx = 0;
//            rp.gridy = 1;
            // rightPanel.add(listScroll, rp);

            System.out.println("justBatchRuns.isSelected()");

//            rightPanel.validate();
        } else if (parameterVariationOneParameterRadioButton.isSelected()) {

            SimulationSettings.numParameters = 1;

//            rightPanel.remove(listScroll);
//
//            rightPanel.validate();
            tabModel = new ParameterTableModel(SimulationSettings.PARAMETER_1.name, SimulationSettings.PARAMETER_1.values, true);
            jTable2.setModel(tabModel);
            //table = new JTable(tabModel);
            jTable2.getColumnModel().getColumn(0).setHeaderValue(SimulationSettings.PARAMETER_1.name);
//            listScroll = new JScrollPane(table);
//            listScroll.setPreferredSize(new Dimension(130, 150));
//            rp.gridx = 0;
//            rp.gridy = 1;
//            rightPanel.add(listScroll, rp);

            col = jTable2.getColumnModel().getColumn(0);
            col.setCellEditor(new CellEditor());
            col.getCellEditor().addCellEditorListener(new EditorListener() {

                public void editingStopped(ChangeEvent e) {

                    /*If Textfield is empty remove this row!*/
                    if (col.getCellEditor().getCellEditorValue().toString().equals("") && tabModel.getRowCount() > 1) {
                        tabModel.delRow(((CellEditor) col.getCellEditor()).getRow());

                    } else if (col.getCellEditor().getCellEditorValue().toString().equals("") && tabModel.getRowCount() <= 1) {
                        //Do noting
                    } else {
                        if (SimulationSettings.PARAMETER_1.type.equals("int")) {
                            try {

                                Integer.parseInt(col.getCellEditor().getCellEditorValue().toString());
                                tabModel.setValueAt(col.getCellEditor().getCellEditorValue().toString(), ((CellEditor) col.getCellEditor()).getRow(), 0);
                                tabModel.updatetable();

                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Parameter must be an integer!");

                            }

                        } else {
                            try {
                                Double.parseDouble(col.getCellEditor().getCellEditorValue().toString());
                                tabModel.setValueAt(col.getCellEditor().getCellEditorValue().toString(), ((CellEditor) col.getCellEditor()).getRow(), 0);
                                tabModel.updatetable();

                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Parameter must be a numeric expression!");

                            }

                        }

                    }

                }

                public void editingCanceled(ChangeEvent e) {
                    System.out.println("Editing of a cell has been canceled.");
                }

            });

            System.out.println("parameterVariationOnePars.isSelected()");
//            rightPanel.validate();

            editParameter1Button.setEnabled(true);

        }

    }

    void drawParameterTables() {
        ParameterTableModel tabModel;
        TableColumn col;
        try {
//    	rightPanel.remove(listScroll);
//    	
//    	rightPanel.validate();
        } catch (NullPointerException ex) {

            System.out.println("Start drawing parameter table on Simulation settings pane");

        }

        if (SimulationSettings.numParameters == 1) {

            parameterVariationOneParameterRadioButton.setSelected(true);

            runOnlyOneBatchRadioButton.setSelected(false);

        } else {

            runOnlyOneBatchRadioButton.setSelected(true);

            parameterVariationOneParameterRadioButton.setSelected(false);

        }

        if (parameterVariationOneParameterRadioButton.isSelected()) {
            tabModel = new ParameterTableModel(SimulationSettings.PARAMETER_1.name, SimulationSettings.PARAMETER_1.values, true);
            jTable2.setModel(tabModel);
            //table = new JTable(tabModel);
            jTable2.getColumnModel().getColumn(0).setHeaderValue(SimulationSettings.PARAMETER_1.name);

            System.out.println("SimulationSettings.PARAMETER_1.name   " + SimulationSettings.PARAMETER_1.name);

            col = jTable2.getColumnModel().getColumn(0);

            col.setCellEditor(new CellEditor());

            editParameter1Button.setEnabled(true);

        } else {

            editParameter1Button.setEnabled(false);

        }

        //listScroll = new JScrollPane(table);
//        listScroll.setPreferredSize(new Dimension(130, 150));
//        rp.gridx = 0;
//        rp.gridy = 1;
//        rightPanel.add(listScroll, rp);
//
//        rightPanel.validate();
//
//        /*Add to panel upperCenterArea*/
//        d.gridx = 1;
//        d.gridy = 0;
//        add(rightPanel, d);
    }

    void compressionRadioButtonModified() {

        if (compressAndKeepOrigRadioButton.isSelected()) {
            SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL = 1;
            SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL = 0;
            SimulationSettings.DO_REMOVE_DB = 0;
            SimulationSettings.DO_DECOMPRESS = 0;

        } else if (compressAndRemoveOrigRadioButton.isSelected()) {

            SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL = 0;
            SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL = 1;
            SimulationSettings.DO_REMOVE_DB = 0;
            SimulationSettings.DO_DECOMPRESS = 0;

        } else if (notCompressRadioButton.isSelected()) {

            SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL = 0;
            SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL = 0;
            SimulationSettings.DO_REMOVE_DB = 0;
            SimulationSettings.DO_DECOMPRESS = 0;
        } else if (compressAndRemoveOrigRadioButton.isSelected()) {

            SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL = 0;
            SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL = 0;
            SimulationSettings.DO_REMOVE_DB = 1;
            SimulationSettings.DO_DECOMPRESS = 0;

        } else if (decompressRadioButton.isSelected()) {

            SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL = 0;
            SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL = 0;
            SimulationSettings.DO_REMOVE_DB = 0;
            SimulationSettings.DO_DECOMPRESS = 1;

        }
        simulationSettingsTestLabel.setText("compression changed");

    }

    void wrapDrawStoreOptionTable(JTable myTable, AgentTableModel tableMod, String[] colHeaders) {
        final AgentTableModel tableModel = tableMod;
        myTable.setModel(tableModel);
        final TableColumn col_2, col_3, col_4;

        try {

            for (int i = 0; i < 4; i++) {

                System.out.println(colHeaders[i]);

                myTable.getColumnModel().getColumn(i).setHeaderValue(colHeaders[i]);
                System.out.println(colHeaders[i]);
            }

            /*Set Column No 2: JCheckbox for setting whether the agent should be recorded*/
            col_2 = myTable.getColumnModel().getColumn(1);

            final JCheckBox check = new JCheckBox();

            col_2.setCellEditor(new JCheckBoxCellEditor(check));
            col_2.getCellEditor().getClass();

            col_2.getCellEditor().addCellEditorListener(new EditorListener() {

                public void editingStopped(ChangeEvent e) {

                    if (col_2.getCellEditor().getCellEditorValue().equals(true)) {
                        /*Set the check box*/
                        tableModel.changeValueAt("true", ((JCheckBoxCellEditor) col_2.getCellEditor()).getRow(), 1);

                        if (((JCheckBoxCellEditor) col_2.getCellEditor()).getRow() < AgentSettings.agents.size()) {

                            AgentSettings.agents.get(((JCheckBoxCellEditor) col_2.getCellEditor()).getRow()).dataStorageSettings.setSelected(true);

                        } else {
                            SimulationSettings.SNAPSHOTS = true;
                        }

                        tableModel.updatetable();

                    } else {
                        /*Set the check box*/
                        tableModel.changeValueAt("false", ((JCheckBoxCellEditor) col_2.getCellEditor()).getRow(), 1);
                        if (((JCheckBoxCellEditor) col_2.getCellEditor()).getRow() < AgentSettings.agents.size()) {

                            AgentSettings.agents.get(((JCheckBoxCellEditor) col_2.getCellEditor()).getRow()).dataStorageSettings.setSelected(false);

                        } else {

                            SimulationSettings.SNAPSHOTS = false;
                        }
                        tableModel.updatetable();

                    }

                }

            });

            col_2.setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table,
                        Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    check.setSelected(((Boolean) value).booleanValue());
                    return check;
                }

            });

            /*Here we set the column no 3:*/
            col_3 = myTable.getColumnModel().getColumn(2);
            col_3.setCellEditor(new CellEditor());

            col_3.getCellEditor().addCellEditorListener(new EditorListener() {

                public void editingStopped(ChangeEvent e) {

                    System.out.println("A cell has been edited.");

                    try {
                        System.out.println("Out: " + col_3.getCellEditor().getCellEditorValue());
                        /*Set the table cell*/
                        Integer.parseInt(col_3.getCellEditor().getCellEditorValue().toString());
                        tableModel.changeValueAt(col_3.getCellEditor().getCellEditorValue().toString(), ((CellEditor) col_3.getCellEditor()).getRow(), 2);
                        tableModel.updatetable();

                        /*Update the memory variable*/
                        if (((CellEditor) col_3.getCellEditor()).getRow() < AgentSettings.agents.size()) {
                            AgentSettings.agents.get(((CellEditor) col_3.getCellEditor()).getRow()).dataStorageSettings.period = col_3.getCellEditor().getCellEditorValue().toString();
                        } else {
                            SimulationSettings.SNAPSHOTS_FREQUENCY = Integer.parseInt(col_3.getCellEditor().getCellEditorValue().toString());
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Parameter must be an integer!");

                    }

                }

            });

            /*Here we set the column no 3:*/
            col_4 = myTable.getColumnModel().getColumn(3);
            col_4.setCellEditor(new CellEditor());

            col_4.getCellEditor().addCellEditorListener(new EditorListener() {

                public void editingStopped(ChangeEvent e) {

                    System.out.println("A cell has been edited.");

                    try {
                        System.out.println("Out: " + col_4.getCellEditor().getCellEditorValue());
                        /*Set the table cell*/
                        Integer.parseInt(col_4.getCellEditor().getCellEditorValue().toString());
                        tableModel.changeValueAt(col_4.getCellEditor().getCellEditorValue().toString(), ((CellEditor) col_4.getCellEditor()).getRow(), 3);
                        tableModel.updatetable();

                        /*Update the memory variable*/
                        AgentSettings.agents.get(((CellEditor) col_4.getCellEditor()).getRow()).dataStorageSettings.phase = col_4.getCellEditor().getCellEditorValue().toString();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Parameter must be an integer!");

                    }

                }

            });

            tableModel.updatetable();

        } catch (Exception ex) {

            System.out.println("read storage option settings: " + ex);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox addLegendCheckboxNew;
    private javax.swing.JCheckBox batchRunCheckBoxNew;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JButton changeParameterSetupButton;
    private javax.swing.JCheckBox coloredPlotsCheckboxNew;
    private javax.swing.JRadioButton compressAndKeepOrigRadioButton;
    private javax.swing.JRadioButton compressAndRemoveOrigRadioButton;
    private javax.swing.JRadioButton decompressRadioButton;
    private javax.swing.JRadioButton doNotRunSimulationsRadioButton;
    private javax.swing.JButton editParameter1Button;
    private javax.swing.JComboBox fileTypePlotsComboBox;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JRadioButton noWriteAllDatabaseRadioButton;
    private javax.swing.JRadioButton notCompressRadioButton;
    private javax.swing.JTextField numberOfBatchRunsTextField;
    private javax.swing.JTextField numberOfIterationsTextField;
    private javax.swing.JComboBox numberOfProcessorsComboBox;
    private javax.swing.JCheckBox parameterAnalysisCheckBoxNew;
    private javax.swing.JRadioButton parameterVariationOneParameterRadioButton;
    private javax.swing.JRadioButton removeDecompressedRadioButton;
    private javax.swing.JButton runExperimentButton;
    private javax.swing.JRadioButton runOnlyOneBatchRadioButton;
    private javax.swing.JRadioButton runSimulationsRadioButton;
    private javax.swing.JLabel simulationSettingsTestLabel;
    private javax.swing.JCheckBox singleRunAnalysisCheckboxNew;
    private javax.swing.JLabel testLabelPlotting;
    private javax.swing.JTextField transitionPhaseTextFieldNew;
    private javax.swing.JRadioButton yesWriteAllDatabaseRadioButton;
    // End of variables declaration//GEN-END:variables
}
