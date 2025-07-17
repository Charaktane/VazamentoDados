package docs.archives;
import java.io.*;
import java.util.*;

public class VerificaSenha {

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashSet<String> base = carregarBase("senhas_vazadas.txt");

        System.out.print("Digite a senha para verificar: ");
        String senha = scanner.nextLine().trim();

        if (base.contains(senha)) {
            System.out.println(" senha encontrada na base vazada!");
        } else {
            System.out.println(" senha não encontrado na base.");
        }

        scanner.close();
    }
}
