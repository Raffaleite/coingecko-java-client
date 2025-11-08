package coingecko;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.net.URL;

public class CryptoViewer extends JFrame {

    private final CoinGeckoClient client = new CoinGeckoClient();

    private final JButton btnLoad = new JButton("Carregar Moedas");
    private final JButton btnDetails = new JButton("Ver Detalhes");

    private final DefaultListModel<Crypto> listModel = new DefaultListModel<>();
    private final JList<Crypto> list = new JList<>(listModel);

    private final JTextArea detailsArea = new JTextArea();
    private final JLabel imageLabel = new JLabel("Imagem da criptomoeda aparecerá aqui", SwingConstants.CENTER);

    public CryptoViewer() {
        super("Cliente CoinGecko (REST API)");

        setLayout(new BorderLayout(10, 10));
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel top = new JPanel();
        top.add(btnLoad);
        top.add(btnDetails);

        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);

        JScrollPane detailsScroll = new JScrollPane(detailsArea);

        // Painel com imagem 
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.add(detailsScroll, BorderLayout.CENTER);
        rightPanel.add(imageLabel, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(list),
                rightPanel
        );
        split.setResizeWeight(0.3);

        add(top, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);

        btnLoad.addActionListener(e -> loadCryptos());
        btnDetails.addActionListener(e -> showDetails());
    }

    private void loadCryptos() {
        listModel.clear();
        detailsArea.setText("Carregando moedas...");
        imageLabel.setIcon(null);
        imageLabel.setText("Imagem da criptomoeda aparecerá aqui");

        new SwingWorker<List<Crypto>, Void>() {

            @Override
            protected List<Crypto> doInBackground() throws Exception {
                return client.getMarketList();
            }

            @Override
            protected void done() {
                try {
                    List<Crypto> cryptos = get();
                    cryptos.forEach(listModel::addElement);
                    detailsArea.setText("Selecione uma moeda e clique em VER DETALHES.");
                } catch (Exception ex) {
                    detailsArea.setText("Erro: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private void showDetails() {
        Crypto selected = list.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma criptomoeda!");
            return;
        }

        detailsArea.setText("Carregando detalhes...");
        imageLabel.setIcon(null);
        imageLabel.setText("Carregando imagem...");

        new SwingWorker<Crypto, Void>() {

            @Override
            protected Crypto doInBackground() throws Exception {
                return client.getCryptoDetails(selected.getId());
            }

            @Override
            protected void done() {
                try {
                    Crypto c = get();

                    // Mostrar detalhes textuais
                    detailsArea.setText(
                            "Nome: " + c.getName() + "\n"
                            + "Símbolo: " + c.getSymbol() + "\n"
                            + "Preço Atual: $" + selected.getCurrentPrice() + "\n"
                            + "Market Cap: $" + selected.getMarketCap() + "\n"
                    );

                    // Mostrar imagem da moeda
                    if (selected.getImageUrl() != null && !selected.getImageUrl().isEmpty()) {
                        try {
                            ImageIcon icon = new ImageIcon(new URL(selected.getImageUrl()));
                            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                            imageLabel.setIcon(new ImageIcon(img));
                            imageLabel.setText("");
                        } catch (Exception ex) {
                            imageLabel.setText("Erro ao carregar imagem.");
                        }
                    } else {
                        imageLabel.setText("Sem imagem disponível.");
                    }

                } catch (Exception ex) {
                    detailsArea.setText("Erro: " + ex.getMessage());
                    imageLabel.setIcon(null);
                    imageLabel.setText("Falha ao carregar imagem.");
                }
            }
        }.execute();
    }
}
