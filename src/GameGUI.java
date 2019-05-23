/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import game.arena.*;
import game.competition.*;
import game.entities.sportsman.*;
import game.enums.*;

public class GameGUI implements Observer {
    private JFrame gameFrame;
    private Competition competition = null;
    private IArena arena = null;
    private String arenaType = null;
    private JPanel racePanel, racePanelBack, optionPanelNew;
    private ImageIcon compImg = null, backgroundImg = null;
    Observer beholder = this;
    private gameInfoFrame infoFrame;
    private ArenaFactory arenaFactory = new ArenaFactory();

    //Information frame extended class
    private class gameInfoFrame extends JFrame {
        private JScrollPane pane;
        private JTable infoTable;
        private String[] tableColumns = {"Position","Name", "Speed", "Max speed", "Location", "Finished", "Status", "Time of injury\n(1/10 seconds)"};
        private DefaultTableModel tableModel;

        /**
         * Constructor
         */
        public gameInfoFrame() {
            super("Race information");
            tableModel = new DefaultTableModel(getInfo(), tableColumns){
                @Override
                public boolean isCellEditable(int row, int column) { return false; }
            };
            infoTable = new JTable();
            infoTable.setModel(tableModel);
            infoTable.setGridColor(Color.blue);
            pane = new JScrollPane(infoTable);
            this.add(pane);
        }
    }

    //Game frame initialization
    private void initGameFrame(){
        //gameFrame
        gameFrame = new JFrame("Need for Speed: Finding Snowbunny");
        gameFrame.setSize(new Dimension(1000, 760));
        gameFrame.setPreferredSize(new Dimension(1000, 760));
        gameFrame.setMaximumSize(new Dimension(1000, 760));
        gameFrame.setResizable(false);
        gameFrame.setLayout(new BorderLayout(3,3));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }

    //Race panel initialization
    private void initRacePanel(){
        //racePanel
        racePanel = new JPanel() {
            @Override
            public synchronized void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImg != null) {
                    Image i = backgroundImg.getImage();
                    g.drawImage(i, 0, 0, this.getWidth(), this.getHeight(), null);
                }
            }

            @Override
            public synchronized void paint(Graphics g) {
                super.paint(g);
                try {
                    if (getCompetition() != null) {
                        g.drawLine(10, super.getHeight() - 55, super.getWidth() - 10, super.getHeight() - 55);
                        g.drawString("FINISH LINE", super.getWidth() - 80, super.getHeight() - 60);
                        for (int i = 0; i < getCompetition().getActiveCompetitors().size(); i++) {
                            double currPos = (((Sportsman) getCompetition().getActiveCompetitors().get(i)).getLocation().getX()
                                    / ((WinterArena) getCompetition().getArena()).getLength()) * super.getHeight();
                            g.setColor(Color.BLACK);
                            g.drawImage(getCompImg().getImage(),
                                    (int) (((Sportsman) getCompetition().getActiveCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 8),
                                    (int) (currPos * 0.90), 50, 50, null);
                            g.drawImage((new ImageIcon("src/icons/states/" + ((Sportsman) getCompetition().getActiveCompetitors().get(i)).getCurrentState() + ".png")).getImage(),
                                    (int) (((Sportsman) getCompetition().getActiveCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 8),
                                    (int) (currPos * 0.90), 23, 23, null);
                            g.setColor(((Sportsman) getCompetition().getActiveCompetitors().get(i)).getColor().getColor());
                            g.drawOval((int) (((Sportsman) getCompetition().getActiveCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 10),
                                    (int) (currPos * 0.90 + 30), 10, 10);
                            g.fillOval((int) (((Sportsman) getCompetition().getActiveCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 10),
                                    (int) (currPos * 0.90 + 30), 10, 10);
                        }
                        for (int i = 0; i < getCompetition().getFinishedCompetitors().size(); i++) {
                            g.setColor(Color.BLACK);
                            g.drawImage(getCompImg().getImage(), (int) (((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 8),
                                    (int) (super.getHeight() * 0.90), 50, 50, null);
                            g.drawImage((new ImageIcon("src/icons/states/" + ((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getCurrentState() + ".png")).getImage(),
                                    (int) (((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 8),
                                    (int) (super.getHeight() * 0.90), 23, 23, null);
                            g.setColor(((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getColor().getColor());
                            g.drawOval((int) (((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 10),
                                    (int) (super.getHeight() * 0.90 + 30), 10, 10);
                            g.fillOval((int) (((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 10),
                                    (int) (super.getHeight() * 0.90 + 30), 10, 10);
                        }
                        for (int i = 0; i < getCompetition().getDisqualifiedCompetitors().size(); i++) {
                            g.setColor(Color.BLACK);
                            g.drawImage(getCompImg().getImage(), (int) (((Sportsman) getCompetition().getDisqualifiedCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 8),
                                    (int) (super.getHeight() * 0.90), 50, 50, null);
                            g.drawImage((new ImageIcon("src/icons/states/" + ((Sportsman) getCompetition().getDisqualifiedCompetitors().get(i)).getCurrentState() + ".png")).getImage(),
                                    (int) (((Sportsman) getCompetition().getDisqualifiedCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 8),
                                    (int) (super.getHeight() * 0.90), 23, 23, null);
                            g.setColor(((Sportsman) getCompetition().getDisqualifiedCompetitors().get(i)).getColor().getColor());
                            g.drawOval((int) (((Sportsman) getCompetition().getDisqualifiedCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 10),
                                    (int) (super.getHeight() * 0.90 + 30), 10, 10);
                            g.fillOval((int) (((Sportsman) getCompetition().getDisqualifiedCompetitors().get(i)).getPositionInTheCompetition() * 50 * 1.1 + 10),
                                    (int) (super.getHeight() * 0.90 + 30), 10, 10);
                        }
                    }
                }
                catch (Exception er){}
            }
        };
        racePanel.setSize(new Dimension(820,700));
        racePanel.setMinimumSize(new Dimension(820,700));
        racePanel.setPreferredSize(new Dimension(820,700));
        racePanel.setMaximumSize(new Dimension(820,780));
        racePanelBack = new JPanel();
        racePanelBack.setSize(new Dimension(820, 780));
        racePanelBack.setMinimumSize(new Dimension(820,780));
        racePanelBack.setMaximumSize(new Dimension(820,780));
        racePanelBack.setPreferredSize(new Dimension(820,780));
        racePanelBack.add(racePanel);

        /* Mouse option*/
        /////////////////
        racePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = mouseSelection(new Point(e.getX(),e.getY()));
                if (i != -1){
                    JButton decorateButton = new JButton("Decorate");
                    decorateButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JLabel changeColorLabel = new JLabel ("Change color: ");
                            changeColorLabel.setSize(60,20);
                            JComboBox changeColorComboBox = new JComboBox(CompetitorColor.values());
                            changeColorComboBox.setSize(50,20);
                            JButton changeColorButton = new JButton("Change");
                            changeColorButton.setSize(40,20);
                            JLabel addAccelerationLabel = new JLabel("Add acceleration: ");
                            addAccelerationLabel.setSize(80,20);
                            JTextField addAccelerationTextField = new JTextField("0.1");
                            addAccelerationTextField.setSize(20,20);
                            JButton addAccelerationButton = new JButton("Add");
                            addAccelerationButton.setSize(40,20);
                            addAccelerationButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    getCompetition().addAcceleration(i,Double.parseDouble(addAccelerationTextField.getText()));
                                }
                            });
                            changeColorButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    getCompetition().decorate(i,(CompetitorColor)changeColorComboBox.getSelectedItem());
                                    racePanel.repaint();
                                    racePanel.revalidate();
                                }
                            });
                            Object[] temp = {changeColorLabel,changeColorComboBox,changeColorButton,
                                    addAccelerationLabel,addAccelerationTextField,addAccelerationButton};
                            JOptionPane.showOptionDialog(null,"Please enter the changes you wish to make",
                                    "Decorate competitor",1,1,null,temp,0);
                        }
                    });

                    //prototypeButton
                    JButton prototypeButton = new JButton("Use as a prototype");
                    prototypeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JComboBox prototypeColorComboBox = new JComboBox(CompetitorColor.values());
                            prototypeColorComboBox.setSize(50,20);
                            JButton prototypeColorButton = new JButton("Create new competitor");
                            prototypeColorButton.setSize(40,20);
                            prototypeColorButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        getCompetition().prototypeCompetitor(i, (CompetitorColor) prototypeColorComboBox.getSelectedItem());
                                        racePanel.repaint();
                                        racePanel.revalidate();
                                    }
                                    catch (Exception er) {
                                        JOptionPane.showMessageDialog(null,er.getMessage(),null,JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            });
                            Object[] temp = {prototypeColorComboBox,prototypeColorButton};
                            JOptionPane.showOptionDialog(null,"Please choose color of a new competitor",
                                    "Prototype",JOptionPane.NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,temp,0);
                        }
                    });
                    Object[] temp = {decorateButton, prototypeButton};
                    JOptionPane.showOptionDialog(null,"ID " + (int)
                                    ((Sportsman)getCompetition().getActiveCompetitors().get(i)).getSportsmanID() + "\nColor: "
                                    + ((Sportsman)getCompetition().getActiveCompetitors().get(i)).getColor() + "\nMax Speed: "
                                    + ((Sportsman)getCompetition().getActiveCompetitors().get(i)).getMaxSpeed() + "\nAcceleration: "
                                    + ((Sportsman)getCompetition().getActiveCompetitors().get(i)).getAcceleration(),
                            "Competitor: " + ((Sportsman)getCompetition().getActiveCompetitors().get(i)).getName(),
                            JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null,temp,0);
                }
            }
            @Override
            public void mousePressed(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e){}
        });
    }

    //Options panel initialization
    private void initOptionPanel(){
        optionPanelNew = new JPanel();
        optionPanelNew.setLayout(new GridBagLayout());
        optionPanelNew.setSize(new Dimension(170,700));
        optionPanelNew.setPreferredSize(new Dimension(170, 700));
        optionPanelNew.setMaximumSize(new Dimension(170, 700));
        optionPanelNew.setBackground(Color.orange);
    }

    //Constructor
    public GameGUI() {
        initGameFrame();
        initRacePanel();
        initOptionPanel();
        this.init();
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        try {
            if (!competition.isFinished()) {
                racePanel.repaint();
                racePanel.revalidate();
            }
        }
        catch (Exception er){}
    }

    //Info table method
    private void showInfoTable(){
        infoFrame = new gameInfoFrame();
        infoFrame.setVisible(true);
        infoFrame.setSize(new Dimension(800, 300));
        infoFrame.setPreferredSize(new Dimension(400, 300));
        infoFrame.repaint();
        infoFrame.revalidate();
    }

    //Mouse selection coordinator
    private int mouseSelection(Point p){
        if (getCompetition() != null) {
            if (getCompetition().isFinished() || getCompetition().getActiveCompetitors().size() == 0 || p.getY() > 45) return -1;
            else {
                for (int i = 0; i < getCompetition().getActiveCompetitors().size(); i++){
                    if (p.getX() < i * 50 * 1.1 + 55 && p.getX() > i* 50 * 1.1) return i;
                }
            }
        }
        return -1;
    }

    /**
     * Competition information extortion (real-time)
     *
     * @return Object[][]
     */
    private Object[][] getInfo() {
        Object ret[][] = null;
        if (getCompetition() != null) {
            ret = new Object[getCompetition().getFinishedCompetitors().size()
                    + getCompetition().getActiveCompetitors().size()
                    + getCompetition().getDisqualifiedCompetitors().size()][5];
            int k = 0;
            String injuryTime;
            for (int i = 0; i < getCompetition().getFinishedCompetitors().size(); i++) {
                if (((Sportsman)getCompetition().getFinishedCompetitors().get(i)).getCurrentState().equals("Injured")){
                    injuryTime = Integer.toString(((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getPhysicalBreakdownTime());
                }
                else injuryTime = "";
                ret[k] = new Object[]{Integer.toString(k + 1),((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getName(),
                        ((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getSpeed(),
                        ((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getMaxSpeed(),
                        getCompetition().getFinishedCompetitors().get(i).getLocation().getX(),
                        /*finished:*/"Yes",((Sportsman) getCompetition().getFinishedCompetitors().get(i)).getCurrentState(),
                        injuryTime
                };
                k++;
            }
            for (int j = 0; j < getCompetition().getActiveCompetitors().size(); j++) {
                if (((Sportsman)getCompetition().getActiveCompetitors().get(j)).getCurrentState().equals("Injured")){
                    injuryTime = Integer.toString(((Sportsman) getCompetition().getActiveCompetitors().get(j)).getPhysicalBreakdownTime());
                }
                else injuryTime = "";
                ret[k] = new Object[]{Integer.toString(k + 1),((Sportsman) getCompetition().getActiveCompetitors().get(j)).getName(),
                        ((Sportsman) getCompetition().getActiveCompetitors().get(j)).getSpeed(),
                        ((Sportsman) getCompetition().getActiveCompetitors().get(j)).getMaxSpeed(),
                        getCompetition().getActiveCompetitors().get(j).getLocation().getX(),
                        /*finished:*/"No",((Sportsman) getCompetition().getActiveCompetitors().get(j)).getCurrentState(),
                        injuryTime
                };
                k++;
            }
            for (int j = 0; j < getCompetition().getDisqualifiedCompetitors().size(); j++) {
                injuryTime = Integer.toString(((Sportsman) getCompetition().getDisqualifiedCompetitors().get(j)).getPhysicalBreakdownTime());
                ret[k] = new Object[]{Integer.toString(k + 1),((Sportsman) getCompetition().getDisqualifiedCompetitors().get(j)).getName(),
                        ((Sportsman) getCompetition().getDisqualifiedCompetitors().get(j)).getSpeed(),
                        ((Sportsman) getCompetition().getDisqualifiedCompetitors().get(j)).getMaxSpeed(),
                        getCompetition().getDisqualifiedCompetitors().get(j).getLocation().getX(),
                        /*finished:*/"No",((Sportsman) getCompetition().getDisqualifiedCompetitors().get(j)).getCurrentState(),
                        injuryTime
                };
                k++;
            }
        }
        return ret;
    }

    /**
     * Set competitor image
     *
     * @param gender
     * @param comp
     */
    private void setCompetitorImage(Gender gender, String comp) {
        if (gender == Gender.MALE && comp.equals("Ski")) compImg = new ImageIcon("src/icons/SkiMale.png");
        else if (gender == Gender.MALE && comp.equals("Snowboard"))
            compImg = new ImageIcon("src/icons/SnowboardMale.png");
        else if (gender == Gender.FEMALE && comp.equals("Ski")) compImg = new ImageIcon("src/icons/SkiFemale.png");
        else if (gender == Gender.FEMALE && comp.equals("Snowboard"))
            compImg = new ImageIcon("src/icons/SnowBoardFemale.png");
    }

    /**
     * Set background image (race panel)
     *
     * @param weatherCondition
     */
    private void setBackgroundImg(WeatherCondition weatherCondition) {
        if (weatherCondition != null) {
            if (weatherCondition == WeatherCondition.CLOUDY) backgroundImg = new ImageIcon("src/icons/Cloudy.jpg");
            else if (weatherCondition == WeatherCondition.STORMY) backgroundImg = new ImageIcon("src/icons/Stormy.jpg");
            else if (weatherCondition == WeatherCondition.SUNNY) backgroundImg = new ImageIcon("src/icons/Sunny.jpg");
        }
    }

    /**
     * getCompetition
     *
     * @return competition
     */
    public synchronized Competition getCompetition() { return competition; }

    /**
     * getCompImg
     * @return compImg
     */
    private ImageIcon getCompImg() { return compImg; }

    /**
     * @return ArenaFactory
     */
    private ArenaFactory getArenaFactory() { return arenaFactory; }

    //Build arena method
    private void buildArena(double arenaLength, String arenaType, SnowSurface surface, WeatherCondition condition) throws Exception{
        try {
            if (competition != null && competition.isActive()) throw new Exception("Current competition must be finished first!");
            else {
                int temp = (int)((arenaLength / 900) * gameFrame.getHeight() * 0.95);
                arena = getArenaFactory().getNewArena(arenaType, arenaLength, surface, condition);
                setBackgroundImg(condition);
                racePanel.setSize(racePanel.getWidth(), temp);
                racePanel.setPreferredSize(new Dimension(racePanel.getWidth(),temp));
                racePanel.setBounds(0,0,racePanel.getWidth(),temp);
                competition = null;
                racePanel.repaint();
                racePanel.revalidate();
            }
        } catch (Exception er) {
            throw er;
        }
    }

    //Create competition method
    private void createCompetition(String type, int maxCompetitors,Discipline discipline, League league, Gender gender) throws Exception{
        try {
            arenaType = type;
            Class tempClass = Class.forName("game.competition." + type + "Competition");
            Constructor tempConstructor = tempClass.getConstructor(new Class[]{Arena.class,
                    int.class, Discipline.class, League.class, Gender.class});
            competition = (WinterCompetition) tempConstructor.newInstance((Arena) arena, maxCompetitors, discipline, league, gender);
            setCompetitorImage(gender, type);
            racePanel.repaint();
            racePanel.revalidate();
        }
        catch (Exception er) {
            er.printStackTrace(System.out);
            throw er;
        }
    }

    //Quick build method
    private void quickBuildCompetition(int numberOfCompetitors) throws Exception{
        try {
            WinterCompetitionBuilder bobby = new SkiCompetitionBuilder(numberOfCompetitors);
            DefaultCompetitionBuilder bob = new DefaultCompetitionBuilder(bobby);
            bob.buildCompetition();
            competition = bob.getCompetition();
            arena = competition.getArena();
            arenaType = "Ski";
            setBackgroundImg(((Arena) arena).getCondition());
            setCompetitorImage(Gender.FEMALE, "Ski");
            int temp = (int)((((Arena) arena).getLength() / 900) * gameFrame.getHeight() * 0.95);
            racePanel.setSize(racePanel.getWidth(), temp);
            racePanel.setPreferredSize(new Dimension(racePanel.getWidth(),temp));
            racePanel.setBounds(0,0,racePanel.getWidth(),temp);
            racePanel.repaint();
            racePanel.revalidate();
        }
        catch (Exception er){
            throw er;
        }
    }

    //Add competitor method
    private void addCompetitor(String name, double age,Gender gender,double acceleration, double maxSpeed, CompetitorColor color ) throws Exception{
        try {
            Competitor tempComp;
            String tempString = null;
            if (arenaType.equals("Ski")) tempString = "Skier";
            if (arenaType.equals("Snowboard")) tempString = "Snowboarder";
            Class tempClass = Class.forName(("game.entities.sportsman." + tempString));
            Constructor tempConstructor = tempClass.getConstructor(new Class[]{String.class,
                    double.class, Gender.class, double.class, double.class, double.class, double.class, Discipline.class, CompetitorColor.class});
            tempComp = (Competitor) tempConstructor.newInstance(name, age, gender, acceleration, maxSpeed, arena.getFriction(),
                    ((Arena) arena).getLength(), ((WinterCompetition) competition).getDiscipline(),color);
            competition.addCompetitor(tempComp);
            racePanel.repaint();
            racePanel.revalidate();
        } catch (Exception er) {
            throw er;
        }
    }

    //Buttons and labels initialization
    public void init() {
        //buildArenaLabel
        JLabel buildArenaLabel = new JLabel("BUILD ARENA");
        //arenaTypeLabel
        JLabel arenaTypeLabel = new JLabel("Arena Type");
        //arenaTypeComboBox
        JComboBox arenaTypeComboBox = new JComboBox();
        arenaTypeComboBox.addItem("Winter");
        arenaTypeComboBox.addItem("Summer");
        //arenaLengthLabel
        JLabel arenaLengthLabel = new JLabel("Arena length");
        //snowSurfaceLabel
        JLabel snowSurfaceLabel = new JLabel("Snow surface");
        //snowSurfaceComboBox
        JComboBox snowSurfaceComboBox = new JComboBox();
        snowSurfaceComboBox.setPreferredSize(new Dimension(140, 20));
        snowSurfaceComboBox.setMaximumSize(new Dimension(140, 20));
        snowSurfaceComboBox.setModel(new DefaultComboBoxModel(SnowSurface.values()));
        //weatherConditionLabel
        JLabel weatherConditionLabel = new JLabel("Weather condition");
        //weatherConditionComboBox
        JComboBox weatherConditionComboBox = new JComboBox();
        weatherConditionComboBox.setPreferredSize(new Dimension(140, 20));
        weatherConditionComboBox.setMaximumSize(new Dimension(140, 20));
        weatherConditionComboBox.setModel(new DefaultComboBoxModel(WeatherCondition.values()));
        //Blue Underlined Font
        Font blueUnderlined = buildArenaLabel.getFont();
        Map blueUnderlinedAtrr = blueUnderlined.getAttributes();
        blueUnderlinedAtrr.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        buildArenaLabel.setFont(blueUnderlined.deriveFont(blueUnderlinedAtrr));
        buildArenaLabel.setForeground(Color.BLUE);
        //arenaLengthTextField
        JTextField arenaLengthTextField = new JTextField("700");
        arenaLengthTextField.setPreferredSize(new Dimension(100, 20));
        arenaLengthTextField.setMaximumSize(new Dimension(100, 20));
        //buildArenaButton
        JButton buildArenaButton = new JButton("Build arena");
        buildArenaButton.setPreferredSize(new Dimension(80,20));
        buildArenaButton.setBounds(0,0,80,20);
        buildArenaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (competition != null && competition.isActive())
                        throw new Exception("Current competition must be finished first!");
                    else {
                        if (arenaLengthTextField.getText().isEmpty()) arenaLengthTextField.setText("700");
                        buildArena(Double.parseDouble(arenaLengthTextField.getText()), (String) arenaTypeComboBox.getSelectedItem(),
                                (SnowSurface) snowSurfaceComboBox.getSelectedItem(),(WeatherCondition) weatherConditionComboBox.getSelectedItem());
                    }
                } catch (Exception er) {
                    JOptionPane.showMessageDialog(null, ("Error! " + er.getMessage()), "Error", 1);
                    er.printStackTrace(System.out);
                }
            }
        });
        //createCompetitionLabel
        JLabel createCompetitionLabel = new JLabel("CREATE COMPETITION");
        createCompetitionLabel.setFont(blueUnderlined.deriveFont(blueUnderlinedAtrr));
        createCompetitionLabel.setForeground(Color.BLUE);
        //chooseCompetitionLabel
        JLabel chooseCompetitionLabel = new JLabel("Choose competition");
        //chooseCompetitionComboBox
        JComboBox chooseCompetitionComboBox = new JComboBox();
        chooseCompetitionComboBox.addItem("Ski");
        chooseCompetitionComboBox.addItem("Snowboard");
        //maxCompetitorsNumberLabel
        JLabel maxCompetitorsNumberLabel = new JLabel("Max competitors number");
        //maxCompetitorsNumberTextField
        JTextField maxCompetitorsNumberTextField = new JTextField("10");
        //disciplineLabel
        JLabel disciplineLabel = new JLabel("Discipline");
        //disciplineComboBox
        JComboBox disciplineComboBox = new JComboBox();
        disciplineComboBox.setModel(new DefaultComboBoxModel(Discipline.values()));
        //leagueLabel
        JLabel leagueLabel = new JLabel("League");
        //leagueComboBox
        JComboBox leagueComboBox = new JComboBox();
        leagueComboBox.setModel(new DefaultComboBoxModel(League.values()));
        //genderLabel
        JLabel genderLabel = new JLabel("Gender");
        //genderComboBox
        JComboBox genderComboBox = new JComboBox();
        genderComboBox.setModel(new DefaultComboBoxModel(Gender.values()));
        //createCompetitionButton
        JButton createCompetitionButton = new JButton("Create competition");
        createCompetitionButton.setPreferredSize(new Dimension(80,20));
        createCompetitionButton.setBounds(0,0,80,20);
        createCompetitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (competition != null && competition.isActive()) throw new Exception("Current competition must be finished first!");
                    else {
                        if (maxCompetitorsNumberTextField.getText().isEmpty()) maxCompetitorsNumberTextField.setText("10");
                        createCompetition((String) chooseCompetitionComboBox.getSelectedItem(),
                                Integer.parseInt(maxCompetitorsNumberTextField.getText()),(Discipline) disciplineComboBox.getSelectedItem(),
                                (League) leagueComboBox.getSelectedItem(), (Gender) genderComboBox.getSelectedItem());
                    }
                } catch (Exception er) {
                    if (er.toString().equals("java.lang.reflect.InvocationTargetException")) {
                        JOptionPane.showMessageDialog(null, ("Error! " + er.getCause().getMessage()), "Error", 1);
                    } else {
                        JOptionPane.showMessageDialog(null, ("Error! " + er.getMessage()), "Error", 1);
                    }
                    er.printStackTrace(System.out);
                }
            }
        });
        //quickBuildLabel
        JLabel quickBuildLabel = new JLabel("Number of competitors: ");
        //quickBuildTextField
        JTextField quickBuildTextField = new JTextField("10");
        quickBuildTextField.setPreferredSize(new Dimension(30,20));
        //quickBuildButton
        JButton quickBuildButton = new JButton("Create competition");
        quickBuildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Integer.parseInt(quickBuildTextField.getText()) > 14 || Integer.parseInt(quickBuildTextField.getText()) < 1)
                        JOptionPane.showMessageDialog(null, "Number of competitors must be 1-14");
                    else quickBuildCompetition(Integer.parseInt(quickBuildTextField.getText()));
                } catch (Exception er){
                    er.printStackTrace(System.out);
                }
            }
        });
        //quickBuildCompetitionButton
        JButton quickBuildCompetitionButton = new JButton("Quick Build");
        quickBuildCompetitionButton.setPreferredSize(new Dimension(80,20));
        quickBuildCompetitionButton.setBounds(0,0,80,20);
        quickBuildCompetitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] temp = {quickBuildLabel,quickBuildTextField,quickBuildButton};
                JOptionPane.showOptionDialog(null,"Quick build of Ski competition!","Quick build",2,JOptionPane.PLAIN_MESSAGE,null,temp,0);
            }
        });
        //addCompetitorLabel
        JLabel addCompetitorLabel = new JLabel("ADD COMPETITOR");
        addCompetitorLabel.setFont(blueUnderlined.deriveFont(blueUnderlinedAtrr));
        addCompetitorLabel.setForeground(Color.BLUE);
        //nameLabel
        JLabel nameLabel = new JLabel("Name");
        //nameTextField
        JTextField nameTextField = new JTextField();
        //competitorColorLabel
        JLabel competitorColorLabel = new JLabel("Competitor color");
        //competitorColoroComboBox
        JComboBox competitorColorComboBox = new JComboBox();
        competitorColorComboBox.setModel(new DefaultComboBoxModel(CompetitorColor.values()));
        //ageLabel
        JLabel ageLabel = new JLabel("Age");
        //ageTextField
        JTextField ageTextField = new JTextField();
        //maxSpeedLabel
        JLabel maxSpeedLabel = new JLabel("Max speed");
        //maxSpeedTextField
        JTextField maxSpeedTextField = new JTextField();
        //accelerationLabel
        JLabel accelerationLabel = new JLabel("Acceleration");
        //accelerationTextField
        JTextField accelerationTextField = new JTextField();
        //addCompetitorButton
        JButton addCompetitorButton = new JButton("Add competitor");
        addCompetitorButton.setPreferredSize(new Dimension(80,20));
        addCompetitorButton.setBounds(0,0,80,20);
        addCompetitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (competition == null) {
                    JOptionPane.showMessageDialog(null, "You must create competition first!", "Error", 1);
                } else if (competition.isFinished()) JOptionPane.showMessageDialog(null,
                        "Current competition is finished. You must create new competition", "Error", 1);
                else if (competition.isActive()) JOptionPane.showMessageDialog(null, "The competition already started!",
                        "Error", 1);
                else if (nameTextField.getText().isEmpty() || ageTextField.getText().isEmpty() || maxSpeedTextField.getText().isEmpty()
                        || accelerationTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill al the required fields", "Error", 1);
                }
                else {
                    try {
                        addCompetitor(nameTextField.getText(),Double.parseDouble(ageTextField.getText()),
                                ((WinterCompetition) competition).getGender(),Double.parseDouble(accelerationTextField.getText()),
                                Double.parseDouble(maxSpeedTextField.getText()),(CompetitorColor) competitorColorComboBox.getSelectedItem());
                        nameTextField.setText(""); ageTextField.setText(""); maxSpeedTextField.setText(""); accelerationTextField.setText("");
                    } catch (Exception er) {
                        if (er.toString().equals("java.lang.reflect.InvocationTargetException")) {
                            JOptionPane.showMessageDialog(null, ("Error! " + er.getCause().getMessage()), "Error", 1);
                        } else JOptionPane.showMessageDialog(null, ("Error! " + er.getMessage()), "Error", 1);
                        er.printStackTrace(System.out);
                    }
                }
            }
        });
        //startCompetitionButton
        JButton startCompetitionButton = new JButton("Start competition");
        startCompetitionButton.setPreferredSize(new Dimension(80,20));
        startCompetitionButton.setBounds(0,0,80,20);
        startCompetitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (competition == null) JOptionPane.showMessageDialog(null, "You must create competition to start!",
                        "Error", 1);
                else if (competition.isFinished()) JOptionPane.showMessageDialog(null,
                        "Current competition is finished. You must create new competition", "Error", 1);
                else if (competition.isActive()) JOptionPane.showMessageDialog(null,
                        "The competition already started!", "Error", 1);
                else if (competition.hasActiveCompetitors()) {
                    try {
                        for (int i = 0; i < getCompetition().getActiveCompetitors().size(); i++){
                            ((Sportsman)getCompetition().getActiveCompetitors().get(i)).addObserver(beholder);
                        }
                        getCompetition().startRace();
                    } catch (Exception er) {
                        JOptionPane.showMessageDialog(null, "Error! " + er.getMessage(), "Error", 1);
                    }
                } else JOptionPane.showMessageDialog(null, "Competition has no competitors! Add at least one.",
                        "Error", 1);
            }
        });
        //gameInfoButton
        JButton gameInfoButton = new JButton("Game info");
        gameInfoButton.setPreferredSize(new Dimension(80,20));
        gameInfoButton.setBounds(0,0,80,20);
        gameInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getCompetition() == null) JOptionPane.showMessageDialog(null,
                        "No competition to observe. Please create new competition", "Error", 1);
                else {
                    showInfoTable();
                }
            }
        });

        /////////////////////////////////
        ///OPTION PANEL GRAPHICAL INIT///
        /////////////////////////////////
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        optionPanelNew.add(buildArenaLabel,c);
        c.gridy = 1;
        optionPanelNew.add(arenaTypeLabel,c);
        c.gridy = 2;
        optionPanelNew.add(arenaTypeComboBox,c);
        c.gridy = 3;
        optionPanelNew.add(arenaLengthLabel,c);
        c.gridy = 4;
        optionPanelNew.add(arenaLengthTextField,c);
        c.gridy = 5;
        optionPanelNew.add(snowSurfaceLabel,c);
        c.gridy = 6;
        optionPanelNew.add(snowSurfaceComboBox,c);
        c.gridy = 7;
        optionPanelNew.add(weatherConditionLabel,c);
        c.gridy = 8;
        optionPanelNew.add(weatherConditionComboBox,c);
        c.gridy = 9;
        optionPanelNew.add(buildArenaButton,c);
        c.gridy = 12;
        optionPanelNew.add(new JSeparator(JSeparator.HORIZONTAL),c);
        c.gridy = 13;
        optionPanelNew.add(createCompetitionLabel,c);
        c.gridy = 14;
        optionPanelNew.add(chooseCompetitionLabel,c);
        c.gridy = 15;
        optionPanelNew.add(chooseCompetitionComboBox,c);
        c.gridy = 16;
        optionPanelNew.add(maxCompetitorsNumberLabel,c);
        c.gridy = 17;
        optionPanelNew.add(maxCompetitorsNumberTextField,c);
        c.gridy = 18;
        optionPanelNew.add(disciplineLabel,c);
        c.gridy = 19;
        optionPanelNew.add(disciplineComboBox,c);
        c.gridy = 20;
        optionPanelNew.add(leagueLabel,c);
        c.gridy = 21;
        optionPanelNew.add(leagueComboBox,c);
        c.gridy = 22;
        optionPanelNew.add(genderLabel,c);
        c.gridy = 23;
        optionPanelNew.add(genderComboBox,c);
        c.gridy = 24;
        optionPanelNew.add(createCompetitionButton,c);
        c.gridy = 25;
        optionPanelNew.add(quickBuildCompetitionButton,c);
        c.gridy = 26;
        optionPanelNew.add(new JSeparator(JSeparator.HORIZONTAL),c);
        c.gridy = 27;
        optionPanelNew.add(addCompetitorLabel,c);
        c.gridy = 28;
        optionPanelNew.add(nameLabel,c);
        c.gridy = 29;
        optionPanelNew.add(nameTextField,c);
        c.gridy = 30;
        optionPanelNew.add(competitorColorLabel,c);
        c.gridy = 31;
        optionPanelNew.add(competitorColorComboBox,c);
        c.gridy = 32;
        optionPanelNew.add(ageLabel,c);
        c.gridy = 33;
        optionPanelNew.add(ageTextField,c);
        c.gridy = 34;
        optionPanelNew.add(maxSpeedLabel,c);
        c.gridy = 35;
        optionPanelNew.add(maxSpeedTextField,c);
        c.gridy = 36;
        optionPanelNew.add(accelerationLabel,c);
        c.gridy = 37;
        optionPanelNew.add(accelerationTextField,c);
        c.gridy = 38;
        optionPanelNew.add(addCompetitorButton,c);
        c.gridy = 39;
        optionPanelNew.add(new JSeparator(JSeparator.HORIZONTAL),c);
        c.gridy = 40;
        optionPanelNew.add(startCompetitionButton,c);
        c.gridy = 41;
        optionPanelNew.add(gameInfoButton,c);
        gameFrame.add(optionPanelNew,BorderLayout.EAST);
        gameFrame.add(racePanelBack);
        gameFrame.revalidate();
    }
}
