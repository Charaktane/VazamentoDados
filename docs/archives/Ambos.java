package docs.archives;
import java.io.*;
import java.util.*;

public class Ambos {

    public static HashSet<String> carregarBase(String nomeArquivo) {
        HashSet<String> base = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                base.add(linha.trim());
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            System.exit(1);
        }
        return base;
    }

    public static boolean verificarEmailSenha(String email, String senha, HashSet<String> base) {
        String combinado = email + ":" + senha;
        return base.contains(combinado);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashSet<String> base = carregarBase("vazados.txt");

        System.out.print("Digite o e-mail para verificar: ");
        String email = scanner.nextLine().trim();

        System.out.print("Digite a senha para verificar: ");
        String senha = scanner.nextLine().trim();

        if (verificarEmailSenha(email, senha, base)) {
            System.out.println("⚠ E-mail e senha encontrados na base vazada!");
        } else {
            System.out.println("✅ Par e-mail + senha não encontrado na base.");
        }

        scanner.close();
    }
}
