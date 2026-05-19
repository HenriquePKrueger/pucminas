<#import "/layout/main.ftl" as layout>
<style>

	.request-card {
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
            border: 1px solid #eaeaea;
            transition: all 0.3s ease;
            margin-bottom: 20px;
            overflow: hidden;
        }
        
        .request-card:hover {
            box-shadow: 0 6px 16px rgba(0, 0, 0, 0.08);
            transform: translateY(-2px);
            border-color: #cce5ff;
        }

        .request-card-header {
            background-color: #fcfcfc;
            border-bottom: 1px solid #f0f0f0;
            padding: 16px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .request-card-body {
            padding: 20px;
        }

        .request-card-footer {
            padding: 16px 20px;
            background-color: #fff;
            border-top: 1px solid #f0f0f0;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }

        /* Status Badges */
        .status-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 600;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }
        .status-aberto {
            background-color: #e3f2fd;
            color: #0d6efd;
        }
        .status-andamento {
            background-color: #fff3cd;
            color: #856404;
        }
        .status-concluido {
            background-color: #d1e7dd;
            color: #0f5132;
        }

        /* Títulos e Textos */
        .service-category {
            font-size: 1.1rem;
            font-weight: 700;
            color: #1a1a1a;
            margin-bottom: 4px;
        }
        
        .service-category i {
            color: #007bff;
            margin-right: 8px;
            width: 20px;
            text-align: center;
        }

        .service-date {
            font-size: 0.85rem;
            color: #6c757d;
        }

        .service-desc {
            color: #495057;
            font-size: 0.95rem;
            line-height: 1.5;
            display: -webkit-box;
            -webkit-line-clamp: 2; /* Limita a 2 linhas */
            -webkit-box-orient: vertical;
            overflow: hidden;
            margin-bottom: 15px;
        }

        .service-location {
            font-size: 0.85rem;
            color: #6c757d;
            display: flex;
            align-items: center;
            gap: 6px;
        }

        

   
</style>
<@layout.padrao title="MatchService - Minhas Solicitações" scripts=meusScripts>
<div class="container mt-5 mb-5">
        
        <div class="page-header">
            <div>
                <h2 class="fw-bold mb-1">Minhas Solicitações</h2>
                <p class="text-muted mb-0">Gerencie os serviços que você solicitou na plataforma.</p>
            </div>
            <a href="/criar-solicitacao" class="btn btn-primary shadow-sm" style="font-weight: 600; border-radius: 8px;">
                <i class="fas fa-plus me-2"></i> Nova Solicitação
            </a>
        </div>

        <div class="row mt-5">
            
            <div class="col-lg-6">
                <div class="request-card">
                    <div class="request-card-header">
                        <div class="service-date">
                            <i class="far fa-calendar-alt me-1"></i> Criado em 24 de Out, 2023
                        </div>
                        <span class="status-badge status-aberto">
                            <i class="fas fa-search"></i> Aguardando Propostas
                        </span>
                    </div>
                    <div class="request-card-body">
                        <h4 class="service-category"><i class="fas fa-key"></i> Chaveiro</h4>
                        <p class="service-desc">
                            Minha chave quebrou dentro do portão de casa e preciso de alguém para retirar e fazer uma cópia nova. Tenho disponibilidade no fim de semana...
                        </p>
                        <div class="service-location">
                            <i class="fas fa-map-marker-alt"></i> Praça da Sé, 123 - São Paulo, SP
                        </div>
                    </div>
                    <div class="request-card-footer">
                        <!-- Aciona o Modal de Exclusão -->
                        <button class="btn btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#modalExcluir">
                            <i class="fas fa-trash-alt me-1"></i> Excluir
                        </button>
                        
                        <a href="solicitacao.html" class="btn btn-primary">
                            <i class="fas fa-edit me-1"></i> Ver / Editar
                        </a>
                    </div>
                </div>
            </div>

         
            <div class="col-lg-6">
                <div class="request-card">
                    <div class="request-card-header">
                        <div class="service-date">
                            <i class="far fa-calendar-alt me-1"></i> Criado em 20 de Out, 2023
                        </div>
                        <span class="status-badge status-andamento">
                            <i class="fas fa-handshake"></i> Serviço em Andamento
                        </span>
                    </div>
                    <div class="request-card-body">
                        <h4 class="service-category"><i class="fas fa-bolt"></i> Eletricista</h4>
                        <p class="service-desc">
                            Preciso instalar um chuveiro 220v no banheiro suíte. A fiação já está passada, falta apenas fazer as conexões elétricas e instalar o disjuntor correto.
                        </p>
                        <div class="service-location">
                            <i class="fas fa-map-marker-alt"></i> Praça da Sé, 123 - São Paulo, SP
                        </div>
                    </div>
                    <div class="request-card-footer">
                        
                        <button class="btn btn-excluir" disabled title="Não é possível excluir um serviço em andamento">
                            <i class="fas fa-trash-alt me-1"></i> Excluir
                        </button>
                        <a href="solicitacao.html" class="btn btn-editar">
                            <i class="fas fa-eye me-1"></i> Ver Detalhes
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="request-card" style="opacity: 0.85;">
                    <div class="request-card-header">
                        <div class="service-date">
                            <i class="far fa-calendar-alt me-1"></i> Criado em 05 de Out, 2023
                        </div>
                        <span class="status-badge status-concluido">
                            <i class="fas fa-check-circle"></i> Concluído
                        </span>
                    </div>
                    <div class="request-card-body">
                        <h4 class="service-category"><i class="fas fa-faucet"></i> Encanador</h4>
                        <p class="service-desc">
                            Pia da cozinha entupida e com vazamento no sifão. Já tentei usar produtos comuns e não resolveu.
                        </p>
                        <div class="service-location">
                            <i class="fas fa-map-marker-alt"></i> Praça da Sé, 123 - São Paulo, SP
                        </div>
                    </div>
                    <div class="request-card-footer">
                       
                        <button class="btn btn-light" style="font-weight: 500; border: 1px solid #ced4da;">
                            <i class="fas fa-history me-1"></i> Ver Histórico
                        </button>
                    </div>
                </div>
            </div>

        </div>
    </div>
</@layout.padrao>