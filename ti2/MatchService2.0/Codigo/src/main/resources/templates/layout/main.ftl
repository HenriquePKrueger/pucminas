<#macro padrao title="MatchService" scripts="">
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>

    <!-- Google Fonts - Inter -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">

    <!-- Bootstrap 5.3 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    
    <!-- AdminLTE CSS (Opcional, se for usar componentes específicos) -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/admin-lte@3.2/dist/css/adminlte.min.css">
	<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <style>
        body { 
		    font-family: 'Inter', sans-serif; 
		    /* Cria um layout vertical que ocupa 100% da altura da tela */
		    display: flex;
		    flex-direction: column;
		    min-height: 100vh;
		    background-color: #f4f6f9;
		}
		
		main {
		    /* Faz com que o <main> ocupe todo o espaço disponível, 
		       empurrando o footer para baixo */
		    flex: 1 0 auto;
		    display: flex;
		    align-items: center; /* Centraliza verticalmente (útil para login) */
		}
		
		.footer {
		    /* Garante que o footer não encolha */
		    flex-shrink: 0;
		}
 
    </style>
</head>
<body class="hold-transition">

    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
        <div class="container">
            <a class="navbar-brand d-flex align-items-center" href="/">
                <img src="/images/logo.png" width="40" alt="Logo"> 
                <span>MatchService</span>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMain">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarMain">
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-center">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/prestadores">Prestadores</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/pesquisa">Pesquisa Avançadaa</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/about">Sobre nós</a>
                    </li>
                    
                    <!-- Dropdown Usuário -->
                    <#if user_login??>
	                    <li class="nav-item dropdown ms-lg-3">
	                        <a class="nav-link dropdown-toggle text-white fw-bold" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
	                            <i class="fas fa-user-circle me-1"></i> Olá, ${user_login}
	                        </a>
	                        
	                        <ul class="dropdown-menu dropdown-menu-end">
		                   		<#if tipo_usuario == 0>
		                        	<li><a class="dropdown-item" href="/criar-solicitacao"><i class="fa-solid fa-plus me-2"></i></i>Criar Solicitação</a></li>
		                        	<li><a class="dropdown-item" href="/minhas-solicitacoes"><i class="fa-solid fa-handshake-angle"></i></i>Minhas solicitaçções</a></li>
		                        </#if>
	                            <li><a class="dropdown-item" href="/perfil"><i class="fas fa-user-edit me-2"></i>Editar Perfil</a></li>
	                            <li><hr class="dropdown-divider"></li>
	                            <li><a class="dropdown-item text-danger" href="/logout"><i class="fas fa-sign-out-alt me-2"></i>Sair</a></li>
	                        </ul>
	                    </li>
                    <#else>
	                    <li class="nav-item">
	                        <a class="nav-link text-white" href="/cadastro">Cadastrar</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link text-white" href="/login">Login</a>
	                    </li>
                    </#if>
                   
                </ul>
            </div>
        </div>
    </nav>

    <main class="flex-fill d-flex align-items-center">
	    <div class="container py-5 p-0">
	        <#nested>
	    </div>
	</main>

    <footer class="text-center mt-5 py-4 border-top">
        <p class="text-muted small">&copy; 2026 MatchService - Todos os direitos reservados</p>
    </footer>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/admin-lte@3.2/dist/js/adminlte.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    ${scripts}
</body>
</html>
</#macro>