import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.Timer;

public class Verificador extends JFrame {

    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextArea resultadoArea;
    private boolean modoEscuro = true;

    private JPanel painelPrincipal;
    private JButton botaoTema;

    // Paletas de cores
    private Color fundoClaro = Color.WHITE;
    private Color textoClaro = Color.BLACK;
    private Color campoClaro = Color.WHITE;

    private Color fundoEscuro = new Color(30, 30, 30);
    private Color textoEscuro = new Color(230, 230, 230);
    private Color campoEscuro = new Color(45, 45, 45);

    private JLabel titulo;
    public Verificador() {
        setTitle("Verificador de Vazamento");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(new EmptyBorder(10, 40, 20, 40));

        titulo = new JLabel("Verifique se foi alvo de Vazamentos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailField = new JTextField();
        senhaField = new JPasswordField();
        resultadoArea = new JTextArea(4, 4);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        botaoTema = new JButton("🌙 Tema: Escuro");
        botaoTema.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoTema.addActionListener(e -> animarTransicaoTema());

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnEmail = new JButton("Verificar E-mail");
        JButton btnSenha = new JButton("Verificar Senha");

        botoes.add(btnEmail);
        botoes.add(btnSenha);

        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(botaoTema);
        painelPrincipal.add(Box.createVerticalStrut(20));
        painelPrincipal.add(emailField);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(senhaField);
        painelPrincipal.add(Box.createVerticalStrut(15));
        painelPrincipal.add(botoes);
        painelPrincipal.add(Box.createVerticalStrut(15));
        painelPrincipal.add(new JScrollPane(resultadoArea));
        JButton botaoCreditos = new JButton("Desenvolvido por Charaktane");
        botaoCreditos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        botaoCreditos.setBorder(null);
        botaoCreditos.setOpaque(false);
        botaoCreditos.setContentAreaFilled(false);
        botaoCreditos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoCreditos.setForeground(modoEscuro ? new Color(180, 180, 255) : new Color(0, 0, 160));
        botaoCreditos.setAlignmentX(Component.CENTER_ALIGNMENT);

        botaoCreditos.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://github.com/Charaktane/VazamentoDados"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível abrir o link.");
            }
        });

        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(botaoCreditos);
    painelPrincipal.add(Box.createVerticalStrut(10));

        add(painelPrincipal);

        btnEmail.addActionListener(e -> verificarEmail());
        btnSenha.addActionListener(e -> verificarSenha());

        aplicarTema(modoEscuro);
    }

    // 🌀 Transição suave de tema
    private void animarTransicaoTema() {
        boolean proEscuro = !modoEscuro;
        int passos = 30;
        int delay = 10;

        Color corInicialFundo = painelPrincipal.getBackground();
        Color corFinalFundo = proEscuro ? fundoEscuro : fundoClaro;

        Timer timer = new Timer();
        modoEscuro = proEscuro;

        for (int i = 0; i <= passos; i++) {
            int passo = i;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    float ratio = passo / (float) passos;
                    Color transicao = interpolar(corInicialFundo, corFinalFundo, ratio);
                    painelPrincipal.setBackground(transicao);
                    resultadoArea.setBackground(
                            interpolar(resultadoArea.getBackground(), proEscuro ? campoEscuro : campoClaro, ratio));
                    emailField.setBackground(
                            interpolar(emailField.getBackground(), proEscuro ? campoEscuro : campoClaro, ratio));
                    senhaField.setBackground(
                            interpolar(senhaField.getBackground(), proEscuro ? campoEscuro : campoClaro, ratio));

                    Color textoFinal = proEscuro ? textoEscuro : textoClaro;
                    resultadoArea.setForeground(textoFinal);
                    emailField.setForeground(textoFinal);
                    senhaField.setForeground(textoFinal);
                    emailField.setCaretColor(textoFinal);
                    senhaField.setCaretColor(textoFinal);
                }
            }, i * delay);
        }

        botaoTema.setText(proEscuro ? "🌙 Tema: Escuro" : "☀ Tema: Claro");
    }

    private Color interpolar(Color c1, Color c2, float t) {
        int r = (int) (c1.getRed() + t * (c2.getRed() - c1.getRed()));
        int g = (int) (c1.getGreen() + t * (c2.getGreen() - c1.getGreen()));
        int b = (int) (c1.getBlue() + t * (c2.getBlue() - c1.getBlue()));
        return new Color(r, g, b);
    }

    private void aplicarTema(boolean escuro) {
        Color fundo = escuro ? fundoEscuro : fundoClaro;
        Color texto = escuro ? textoEscuro : textoClaro;
        Color campo = escuro ? campoEscuro : campoClaro;
        Color borda = escuro ? Color.GRAY : Color.LIGHT_GRAY;

        titulo.setForeground(escuro ? textoEscuro : textoClaro);

        painelPrincipal.setBackground(fundo);
        resultadoArea.setBackground(campo);
        resultadoArea.setForeground(texto);
        resultadoArea.setBorder(new CompoundBorder(new LineBorder(borda), new EmptyBorder(10, 10, 10, 10)));

        emailField.setBackground(campo);
        emailField.setForeground(texto);
        emailField.setCaretColor(texto);
        emailField.setBorder(new CompoundBorder(new LineBorder(borda), new EmptyBorder(5, 10, 5, 10)));

        senhaField.setBackground(campo);
        senhaField.setForeground(texto);
        senhaField.setCaretColor(texto);
        senhaField.setBorder(new CompoundBorder(new LineBorder(borda), new EmptyBorder(5, 10, 5, 10)));

        botaoTema.setText(escuro ? "🌙 Tema: Escuro" : "☀ Tema: Claro");
    }

    private HashSet<String> carregarBase(String nomeArquivo) {
        HashSet<String> base = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                base.add(linha.trim());
            }
        } catch (IOException e) {
            resultadoArea.setText("Erro ao ler: " + nomeArquivo);
        }
        return base;
    }

    private boolean emailValido(String email) {
        String regex = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$";
        return Pattern.matches(regex, email);
    }

    private void verificarEmail() {
        String email = emailField.getText().trim();
        if (!emailValido(email)) {
            resultadoArea.setText("❌ E-mail inválido!");
            resultadoArea.setBackground(new Color(30, 30, 30));
            return;
        }

        HashSet<String> base = carregarBase("docs/emails_vazados.txt");

        if (base.contains(email)) {
            resultadoArea.setText("⚠ E-mail encontrado na base!");
            resultadoArea.setBackground(new Color(80, 30, 30));
        } else {
            resultadoArea.setText("✅ E-mail não encontrado.");
            resultadoArea.setBackground(new Color(30, 60, 30));
        }
    }

    private void verificarSenha() {
        String senha = new String(senhaField.getPassword()).trim();
        HashSet<String> base = carregarBase("docs/senhas_vazadas.txt");

        if (base.contains(senha)) {
            resultadoArea.setText("⚠ Senha encontrada na base!");
            resultadoArea.setBackground(new Color(80, 30, 30));
        } else {
            resultadoArea.setText("✅ Senha não encontrada.");
            resultadoArea.setBackground(new Color(30, 60, 30));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Verificador().setVisible(true);
        });
    }
}