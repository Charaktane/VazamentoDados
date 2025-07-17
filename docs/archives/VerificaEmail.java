package docs.archives;
import java.io.*;
import java.util.*;

public class VerificaEmail {

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
        HashSet<String> base = carregarBase("emails_vazados.txt");

        System.out.print("Digite o e-mail para verificar: ");
        String email = scanner.nextLine().trim();

        if (base.contains(email)) {
            System.out.println("E-mail encontrado na base vazada!");
        } else {
            System.out.println("E-mail não encontrado na base.");
        }

        scanner.close();
    }
}
