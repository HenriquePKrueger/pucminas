package exercicio02;

import java.util.*;

public class Principal {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DAO dao = new DAO();
		
		dao.conectar();
		
		boolean sair = false;
		
		while(sair != true) {
			
			System.out.println(" ==== Menu (Digite o número da opção que deseja para prosseguir) === ");
			System.out.println("1 - Listar");
			System.out.println("2 - Inserir");
			System.out.println("3 - Excluir");
			System.out.println("4 - Atualizar");
			System.out.println("5 - Sair");
			
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu){
			case 1:
				System.out.println("Listas de pessoas no banco de dados:");		
				for(int i = 0; i < dao.getPessoas().length; i++) {
					System.out.println((i + 1) + " - "+ dao.getPessoas()[i].toString());
				}
				
				break;
				
			case 2: 
				System.out.print("Digite o nome que sera inserido:");
				String novoNome = sc.nextLine();
				
				Pessoa pessoa = new Pessoa(novoNome);
				if(dao.inserirPessoa(pessoa) == true) {
					System.out.println("'" + novoNome + "' Foi adicionado com sucesso ao banco de dados");
				}
				
				break;
			case 3:
				System.out.print("Digite o nome que sera excluido:");
				String excluirNome = sc.nextLine();
				System.out.print("Deseja mesmo excluir '" + excluirNome + "' do banco de dados (S/N)?");
				String confirmacao = sc.nextLine();
					
					switch(confirmacao.toUpperCase()) {
						case "S":
							if(dao.excluirPessoa(excluirNome) == true) {
								System.out.println("Nome excluido com sucesso");
							}
						break;
							
						case "N":
							
							System.out.print("Exclusão cancelada, voltando para o menu...");
							
						break;
						
						default:
							
							System.out.println("Opcao invalida, digite S/N");
					}
					
				break;
					
			case 4:
				System.out.print("Digite o nome que sera atualizado no banco de dados:");
				String nomeAntigo = sc.nextLine();
				
				System.out.print("Digite o novo nome:");
				String nomeAtual = sc.nextLine();
				
				Pessoa pessoaAtualizada = new Pessoa(nomeAtual);
				
				if(dao.atualizarPessoa(nomeAntigo, pessoaAtualizada) == true) {
					
					System.out.println("Nome atualizado com sucesso!");
					
				}
				else {
					
					System.out.println("Não foi possível encontrar'" + nomeAntigo + "'no banco de dados, tente novamente");
					
				}
				
				break;
				
			case 5:
				System.out.println("Deseja sair do programa(S/N)?");
				
				String confirm = sc.nextLine();
				
				
				
				switch(confirm.toUpperCase()){
					case "N":
						
					break;
						
					case "S":
						
						sair = true;
						
					break;
				}
			break;	
				
			default:
				
				System.out.println("O numero digitado nao eh valido");
			};
			
			System.out.println();
			
		};
			
		System.out.println("Programa finalizado!");
		dao.close();
		sc.close();
	}
}