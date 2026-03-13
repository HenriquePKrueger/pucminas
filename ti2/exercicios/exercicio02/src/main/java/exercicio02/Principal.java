package exercicio02;

import java.util.*;

public class Principal {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DAO dao = new DAO();//Cria um objeto "dao" a partir da classe "DAO" e chama o método contrutor sem passar parametros
		
		dao.conectar();//Chama o metodo "conectar" do objeto "dao"
		
		boolean sair = false;
		
		while(sair != true) {
			
			System.out.println();
			System.out.println(" ==== Menu (Digite o número da opção que deseja para prosseguir) === ");
			System.out.println("1 - Listar");
			System.out.println("2 - Inserir");
			System.out.println("3 - Excluir");
			System.out.println("4 - Atualizar");
			System.out.println("5 - Sair");
			
			int menu = sc.nextInt();
			sc.nextLine();
			
			//Menu criado dentro de um laco de repeticao, garantindo que o usuario utilize a opcao(5) para sair do programa
			switch(menu){
			case 1: //O caso 1 lista todos os nomes presentes no BD
				System.out.println("Listas de pessoas no banco de dados:");		
				for(int i = 0; i < dao.getPessoas().length; i++) { //Esse laco garante que todos os objetos da classe "Pessoa" sejam listados
					System.out.println(dao.getPessoas()[i].toString());
				}
				
				break;
				
			case 2:  //O caso 2 adiciona um nomo nome ao BD
				System.out.print("Digite o nome que sera inserido:");
				String novoNome = sc.nextLine();
				
				Pessoa pessoa = new Pessoa(novoNome);
				if(dao.inserirPessoa(pessoa) == true) { //Programa retorna ao usuario uma mensagem dizendo que o novo nome foi adicionado ao BD
					System.out.println("'" + novoNome + "' Foi adicionado com sucesso ao banco de dados");
				}
				
				break;
			case 3: //O caso 3 exclui um nome do BD
				System.out.print("Digite o nome que sera excluido:");
				String excluirNome = sc.nextLine();
				System.out.print("Deseja mesmo excluir '" + excluirNome + "' do banco de dados (S/N)?");//Uma confirmacao de exclusao e solicitada ao usuario
				
				String confirme = sc.nextLine().toUpperCase();//Transforma qualquer entrada em letra maiuscula
				
				while(!confirme.equals("S") && !confirme.equals("N")){//Se o usuario digitar algo diferente de "S" e "N" e solicitada nova entrada do usuario
					
					System.out.println("Opcao invalida, digite S/N");
					confirme = sc.nextLine().toUpperCase();
					
				};
						
					if(confirme.equals("S")){
						
						if(dao.excluirPessoa(excluirNome) == true) {//Se o usuario digitar "S" e o nome for encontrado no BD, entao o programa retoirna uma mensagem de sucesso
							System.out.println("Nome excluido com sucesso");
						}
						else{//Se o nome nao for encontrado outra mensagem e exibida
						
							System.out.println("Nao foi possivel excluir '" + excluirNome + "', nome nao encontrado.");
							
						}
						
					}
					else {//Se o usuario digitar "N" o programa retorna ao menu inicial
						
						System.out.print("Exclusão cancelada, voltando para o menu...");
						
					};
					
			break;
					
			case 4://O caso 4 atualiza no BD um nome ja existente
				System.out.print("Digite o nome que sera atualizado no banco de dados:");
				String nomeAntigo = sc.nextLine();
				
				System.out.print("Digite o novo nome:");
				String nomeAtual = sc.nextLine();
				
				Pessoa pessoaAtualizada = new Pessoa(nomeAtual);
				
				if(dao.atualizarPessoa(nomeAntigo, pessoaAtualizada) == true) {//Se o nome "antigo" do usuario for encontrado no BD, ele sera atualizado e o programa exibira uma mensagem
					
					System.out.println("Nome atualizado com sucesso!");
					
				}
				else {//Se o usuario digitar o nome errado, uma mensagem de erro e exibida
					
					System.out.println("Não foi possível encontrar '" + nomeAntigo + "' no banco de dados, tente novamente");
					
				}
				
				break;
				
			case 5://A opcao 5 do menu fecha a aplicacao
				System.out.println("Deseja sair do programa(S/N)?");//Sistema pede uma confirmacao do usuario
				
				String confirm = sc.nextLine().toUpperCase();
				
				if(confirm.equals("N")) {//Se o usuario digitar "N"(nao quero sair), ele retorna para o menu inicial
					
					System.out.println("Retornando ao menu principal");
					
				}
				else {//Se digitar "S"(quero sair do programa) a aplicacao e fechada
					
					sair = true; //Define a variavel "sair" como true, o que faz o programa sair do loop e ser finalizado
					
				}

			break;	
				
			default://Se digitar um numero fora do menu, o sistema solicita um numero valido e retona para o menu
				
				System.out.println("O numero digitado nao eh valido");
				
			};
			
		};
			
		System.out.println("Programa finalizado!");
		dao.close();
		sc.close();
	}
}