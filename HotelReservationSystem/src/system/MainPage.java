package system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;

public class MainPage extends JFrame {
    private Font loadLibreBaskerville(float size, int style) { // font loading
        try {
            InputStream is = getClass().getResourceAsStream("elements/LibreBaskerville-Regular.ttf");
            if (is == null) {
                java.io.File file = new java.io.File("elements/LibreBaskerville-Regular.ttf");
                if (file.exists()) {
                    is = new java.io.FileInputStream(file);
                } else {
                    throw new Exception("Font file not found at elements/LibreBaskerville-Regular.ttf");
                }
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(style, size);
            if (!"Libre Baskerville".equals(font.getFamily())) {
                throw new Exception("Font family mismatch, expected Libre Baskerville");
            }
            java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            System.out.println("Loaded Libre Baskerville font with size " + size + " and style " + style);
            return font;
        } catch (Exception e) {
            System.err.println("Error loading Libre Baskerville font: " + e.getMessage());
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }

    public MainPage() {
        setTitle("Covalent Hotel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // GIF [need idebug hindi naloload gif]
        JLabel backgroundLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                try {
                    if (!isVisible() || getWidth() <= 0 || getHeight() <= 0) {
                        System.out.println(
                                "Background label not visible or has invalid size: " + getWidth() + "x" + getHeight());
                        repaint();
                        return;
                    }
                    super.paintComponent(g);
                    Image image = null;
                    String primaryPath = "C:/Users/User/OneDrive/Desktop/oak/OOP_Finals/swingFinals/elements/covalent-gif.gif";
                    System.out.println("Attempting to load GIF from: " + primaryPath);
                    java.io.File file = new java.io.File(primaryPath);
                    if (file.exists() && file.canRead()) {
                        System.out.println("File found and readable at " + primaryPath);

                        try (java.io.InputStream is = new java.io.FileInputStream(file)) {
                            byte[] data = is.readAllBytes();
                            image = java.awt.Toolkit.getDefaultToolkit().createImage(data);
                            java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
                            tracker.addImage(image, 0);
                            tracker.waitForID(0);
                            if (tracker.isErrorID(0)) {
                                System.out.println("GIF image load error from " + primaryPath);
                                image = null;
                            }
                        } catch (java.io.IOException e) {
                            System.out.println("Failed to read file from " + primaryPath + " - " + e.getMessage());
                        } catch (InterruptedException e) {
                            System.out.println("Image load interrupted from " + primaryPath + " - " + e.getMessage());
                        }
                    } else {
                        System.out.println("File not found or not readable at " + primaryPath);
                    }
                    if (image != null) {
                        System.out.println("Successfully loaded GIF from: " + primaryPath);
                        Image scaledImg = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
                        g.drawImage(scaledImg, 0, 0, this);
                    } else {
                        System.out.println("No valid image to display from " + primaryPath);
                    }
                } catch (Exception e) {
                    System.err.println("Error in paintComponent: " + e.getMessage());
                }
            }
        };
        add(backgroundLabel, BorderLayout.CENTER);

        // header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setPreferredSize(new Dimension(1280, 60));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setOpaque(true);

        // logo
        try {
            ImageIcon headerIconImg = new ImageIcon("elements/logo.png");
            if (headerIconImg.getImageLoadStatus() == MediaTracker.COMPLETE) {
                JLabel headerIcon = new JLabel(
                        new ImageIcon(headerIconImg.getImage().getScaledInstance(65, 40, Image.SCALE_DEFAULT)));
                headerIcon.setPreferredSize(new Dimension(60, 60));
                headerPanel.add(headerIcon, BorderLayout.WEST);
            } else {
                System.err.println("Header logo not found!");
                JLabel placeholder = new JLabel("LOGO");
                placeholder.setForeground(Color.WHITE);
                placeholder.setFont(loadLibreBaskerville(16, Font.BOLD));
                headerPanel.add(placeholder, BorderLayout.WEST);
            }
        } catch (Exception e) {
            System.err.println("Error loading header logo: " + e.getMessage());
            JLabel placeholder = new JLabel("LOGO");
            placeholder.setForeground(Color.WHITE);
            placeholder.setFont(loadLibreBaskerville(16, Font.BOLD));
            headerPanel.add(placeholder, BorderLayout.WEST);
        }

        // search + menu
        JPanel searchMenuPanel = new JPanel();
        searchMenuPanel.setOpaque(false);
        searchMenuPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton searchButton = new JButton("🔍");
        searchButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(Color.BLACK);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);

        JTextField searchField = new JTextField(15);
        searchField.setVisible(false);
        searchMenuPanel.add(searchField);

        searchButton.addActionListener(e -> {
            if (searchField.isVisible()) {
                searchField.setVisible(false);
                searchButton.setVisible(true);
            } else {
                searchField.setVisible(true);
                searchField.requestFocus();
                searchButton.setVisible(false);
            }
        });

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                searchField.setVisible(false);
                searchButton.setVisible(true);
            }
        });

        searchMenuPanel.add(searchButton);

        JButton menuButton = new JButton("☰");
        menuButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        menuButton.setForeground(Color.WHITE);
        menuButton.setBackground(Color.BLACK);
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);

        JPopupMenu dropdownMenu = new JPopupMenu();
        JMenuItem registerItem = new JMenuItem("Register");
        JMenuItem faqsItem = new JMenuItem("FAQs");
        registerItem.setFont(loadLibreBaskerville(14, Font.PLAIN));
        faqsItem.setFont(loadLibreBaskerville(14, Font.PLAIN));
        dropdownMenu.add(registerItem);
        dropdownMenu.add(faqsItem);

        menuButton.addActionListener(e -> {
            dropdownMenu.show(menuButton, 0, menuButton.getHeight());
        });

        searchMenuPanel.add(menuButton);
        headerPanel.add(searchMenuPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // main
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(null);
        add(mainPanel, BorderLayout.CENTER);

        // overlay
        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
        overlayPanel.setOpaque(false);
        overlayPanel.setBounds(0, 55, 1280, 400);

        // logo sa gitna
        try {
            ImageIcon overlayHexagonImg = new ImageIcon("elements/logo.png");
            if (overlayHexagonImg.getImageLoadStatus() == MediaTracker.COMPLETE) {
                JLabel overlayHexagon = new JLabel(
                        new ImageIcon(overlayHexagonImg.getImage().getScaledInstance(150, 90, Image.SCALE_DEFAULT)));
                overlayHexagon.setAlignmentX(Component.CENTER_ALIGNMENT);
                overlayPanel.add(overlayHexagon);
                overlayPanel.add(Box.createVerticalStrut(15));
            }
        } catch (Exception e) {
            System.err.println("Error loading overlay logo: " + e.getMessage());
            JLabel placeholder = new JLabel("[LOGO]");
            placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
            placeholder.setFont(loadLibreBaskerville(24, Font.BOLD));
            overlayPanel.add(placeholder);
            overlayPanel.add(Box.createVerticalStrut(15));
        }

        // covalent with pink bg
        JPanel textOverlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon pinkBg = new ImageIcon("elements/pink-bg.png");
                    if (pinkBg.getImageLoadStatus() == MediaTracker.COMPLETE) {
                        g.drawImage(pinkBg.getImage(), 0, 0, getWidth(), getHeight(), this);
                    } else {
                        g.setColor(new Color(255, 182, 193));
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                } catch (Exception e) {
                    g.setColor(new Color(255, 182, 193));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        textOverlay.setPreferredSize(new Dimension(1280, 140));
        textOverlay.setLayout(new BorderLayout());
        textOverlay.setOpaque(true);

        JLabel titleLabel = new JLabel(
                "<html><center><span style='font-size: 60px; font-weight: 400;'>COVALENT</span><br><span style='font-size: 24px; font-weight: 420;'>HOTEL</span></center></html>");
        titleLabel.setFont(loadLibreBaskerville(14, Font.PLAIN));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        textOverlay.add(titleLabel, BorderLayout.CENTER);

        overlayPanel.add(textOverlay);
        overlayPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(overlayPanel);

        // reserve button
        JButton reserveButton = new JButton("RESERVE NOW!");
        reserveButton.setFont(loadLibreBaskerville(18, Font.PLAIN));
        reserveButton.setBackground(new Color(255, 230, 240));
        reserveButton.setForeground(Color.BLACK);
        reserveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        reserveButton.setBounds(540, 470, 200, 50);
        reserveButton.setFocusPainted(false);
        reserveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                reserveButton.setBackground(new Color(250, 200, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                reserveButton.setBackground(new Color(255, 230, 240));
            }
        });
        mainPanel.add(reserveButton);
    }
}