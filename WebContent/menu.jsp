<nav class="black noPrint">	

<div class="nav-wrapper">
			<!-- LOGO FTDEDUCACAO - INDEX -->
			<a href="index.jsp" class="brand-logo" title="Página inicial"><img 
				src="resources/images/logofe_white.png" width="95" height="auto"></a>

	<ul class="right">			
			<!-- CARRINHO PEDIDO CLIENTES -->
			<li id="totalroteiro">
				<c:if test="${!empty pedcliente}">					
					<a href="pedClienteRegistrar.jsp" title="Detalhar"><img alt="Detalhar" src="resources/images/information.png"></a>
					<span>${fn:length(pedcliente.itens)} itens</span>
				</c:if>		
			</li>
			
			<!-- LOGIN DO USUARIO -->
			<li>
		       <c:choose>
		       	<c:when test="${empty usuariologado}">
			       	<a href="#modaluserlogin" class="waves-effect waves-light modal-trigger"><i class="material-icons">person</i></a>
			    </c:when>
			    <c:otherwise>
			    		<form role="form" action="srl" method="post" id="form_logout">
			    			${usuariologado.nome}
			    			<a href="javascript:void(0)" onclick="$('#form_logout').submit()"
			    				class="waves-effect waves-light"><i class="material-icons">exit_to_app</i></a>	    			
			    			<input type="hidden" name="acao" value="logout">
			    		</form>
			    </c:otherwise>
		       </c:choose>
		     </li>
	</ul>
</div>


<!-- MODAL LOGIN DO USUARIO -->
<div id="modaluserlogin" class="modal">
  <div class="modal-content gradientbackg3">
    <div class="row">
    	<div class="col l12 s12">
    	   <div class="row">
	    	   	<form action="srl" id="formuserlogin" method="post" class="navbar-form" role="form">
			 		<div class="col l12 s12"><h5>Login do Usuário</h5></div>
			 		<br>			 		
			 		<div class="input-field">
			 			<i class="material-icons prefix">account_circle</i>
			 			<input type="text" name="txtlogin" id="txtlogin" autocomplete="off" required>
			 		</div>
			 		<div class="input-field">
			 			<i class="material-icons prefix">lock</i>
			 			<input type="password" name="txtsenha" id="txtsenha" autocomplete="off" required>
			 		</div>
			 		<input type="hidden" name="acao" value="logon">
					<div class="input-group">
						<div>
							  <button class="btn waves-effect waves-light grey" type="button" id="btnUserLogin">
							  	Enviar
							  </button>
						</div>
						<div>
							<input type="radio" class="with-gap" id="radio1" name="usertype" value="usuario" checked>
							<label for="radio1">Usuário</label>
							<input type="radio" class="with-gap" id="radio2" name="usertype" value="livreiro">
							<label for="radio2">Livreiro</label>
						</div>
						<div id="diverrorlogin"></div>
					</div>			 		
		 		</form>
	 	   </div>
 		</div>   
    </div>
  </div>
</div>

<c:if test="${!empty usuariologado and usuariologado.cargo != 4}">
<!-- MENU SIDENAV -->	  	      		
<ul id="slide-out" class="side-nav">

<li><div class="user-view">
  <div class="background">
    <img src="resources/images/modern-dark-texture-background.jpg" width="auto" height="200px">
  </div>
  <article>
  <h5>${pageTitle}</h5>
  <h6>Menu Principal</h6>
  </article>
</div></li>

<c:if test="${usuariologado.cargo eq 1 or usuariologado.cargo eq 2}">	    	      
<li class="no-padding">
<ul class="collapsible collapsible-accordion">			
<!-- MENU LOGISTICA -->
<li>
<a class="collapsible-header waves-effect waves-teal">LOGÍSTICA<i class="mdi-navigation-arrow-drop-down"></i></a>
<!-- Dropdwon logistica -->
<div class="collapsible-body">
<ul>
	<li><a class="subheader">Pedidos ao Fornecedor</a></li>
	<li>
			<a href="adminImportRelPed.jsp"
				onclick="menuadminclick('importped', ${usuariologado.cargo})">Importar Relatório de Pedido</a>
			<a href="javascript:void(0)"
				onclick="menuadminclick('relatped', ${usuariologado.cargo})">Relatório de Pedidos</a>
			<a href="javascript:void(0)"
				onclick="menuadminclick('relatpend', ${usuariologado.cargo})">Relatóro de Pedidos Pendentes(xls)</a>
			<a	href="adminUpdatePedSP.jsp">Update Previsões Itens Pendentes SP</a>				
			<a href="pedidoItensTransito.jsp" >Relatório de Itens em Trânsito (xls)</a>
	</li>
	
	<li class="divider"></li>
	<li><a class="subheader">Notas Fiscais</a></li>
	<li>
				<a href="javascript:void(0)"
					onclick="menuadminclick('importnf', ${usuariologado.cargo})">Importar
					Nota Fiscal(xml)</a>
				<a href="javascript:void(0)"
					onclick="menuadminclick('exportnf', ${usuariologado.cargo})">Exportar
					Nota Fiscal(csv)</a>
				<a href="javascript:void(0)"
					onclick="menuadminclick('listarnf', ${usuariologado.cargo})">Relatório de
					Notas Fiscais</a>
				<a href="javascript:void(0)"
					onclick="menuadminclick('relnfped', ${usuariologado.cargo})">Amarração Pedido X
					Nota Fiscal</a>					
	</li>

	<li class="divider"></li>
	<li><a class="subheader">Pedidos de Clientes</a></li>
	<li>
				<a href="javascript:void(0)"
					onclick="menuadminclick('importorcam', ${usuariologado.cargo})">Importar Orçamento (csv)</a>
				<a href="javascript:void(0)"
					onclick="menuadminclick('listarpedcliente', ${usuariologado.cargo})">Relatório de pedidos de clientes</a>
				<a href="pedClientePendenteListar.jsp" >Relatório pendências clientes (xls)</a>
				<a href="pedClienteGuardaPendencia.jsp" >Alterar Pendência do Pedido do Cliente</a>
				<a href="pedClienteCancelaPendencia.jsp" >Cancelar Pendências dos Pedidos de Clientes</a>					
		
	</li>
	
</ul>
</div>
</li>
</ul>
</li>
</c:if>

<li class="no-padding">
<ul class="collapsible collapsible-accordion">
<!-- MENU CADASTROS -->										
<li>
<a class="collapsible-header waves-effect waves-teal">CADASTROS<i class="mdi-navigation-arrow-drop-down"></i></a>
<!-- Dropdwon logistica -->
<div class="collapsible-body">
<ul>
													
<c:if test="${usuariologado.cargo eq 1 or usuariologado.cargo eq 2}">
	<li><a class="subheader">Produtos</a></li>
	<li>
				<a href="javascript:void(0)"
					onclick="menuadminclick('cadastrarproduto', ${usuariologado.cargo})"">Cadastrar</a>
				<a href="javascript:void(0)"
					onclick="menuadminclick('exportaprodutos', ${usuariologado.cargo})"">Exportar Cadastro (Totvs)</a>
				<a 
					href="adminImportCadProd.jsp">Importar do Arquivo (csv)</a>
		
	</li>
	<li class="divider"></li>
	<li><a class="subheader">Escolas</a></li>
	<li>
				<a href="adminImportCadEscola.jsp">Importar Cadastro de Escolas</a>
		
	</li>
	<li class="divider"></li>
	<li><a class="subheader">Tabelas</a></li>
	<li>
						<a href="javascript:void(0)"
							onclick="menuadminclick('importtabpre', ${usuariologado.cargo})">Importar
							Tabela de Preços</a>
						<a 
							href="adminExportTabPre.jsp">Exportar Tabela de Preços</a>
			
	</li>
	<li class="divider"></li>
</c:if>
	<li><a class="subheader">Usuarios</a></li>	
	<li>

		<c:if test="${usuariologado.cargo eq 1}">
				<a href="javascript:void(0)"
					onclick="menuadminclick('cadastrousuario', ${usuariologado.cargo})">Cadastrar</a>
		</c:if>
			<a href="javascript:void(0)"
				onclick="menuadminclick('editarusuario', ${usuariologado.cargo})">Editar</a>
	
	</li>

</ul>
</div>
</li>			
</ul>
</li>	      
	      
<li class="no-padding">
<ul class="collapsible collapsible-accordion">		
<!-- DIVULGAÇÃO -->
<li>
<a class="collapsible-header waves-effect waves-teal">DIVULGAÇÃO<i class="mdi-navigation-arrow-drop-down"></i></a>
<!-- Dropdwon logistica -->
<div class="collapsible-body">

<ul>

<li><a class="subheader">Listas</a></li>
<li>
			<a href="javascript:void(0)"
				onclick="menupesquisaradocaoclick()">Consultar Lista</a>
			<a href="javascript:void(0)"
				onclick="menuadminclick('registraradocao', ${usuariologado.cargo})">Registrar Lista</a>
	
</li>
<li class="divider"></li>
<li><a class="subheader">Escolas</a></li>
<li>
			<a href="escolaCadastrar.jsp">Cadastrar Escola</a>
			<a href="escolaEditar2.jsp">Editar Escola</a>
			<a href="escolaListar.jsp">Listar Escolas</a>
</li>
<li class="divider"></li>
<li><a class="subheader">Bônus</a></li>
<li>

		<c:if test="${usuariologado.id eq 1}">
			<a href="bonusRegistrar.jsp">Registrar</a>
		</c:if>
			<a href="bonusPesquisar.jsp">Imprimir</a>
	
</li>
<li class="divider"></li>
<li><a class="subheader">Roteiros</a></li>
<li>
			<a href="roteiroListar.jsp">Listar Roteiros</a>
</li>
<li class="divider"></li>
<li><a class="subheader">Doações</a></li>
<li>
			<a href="doacaoListar.jsp">Listar Doações</a>
		<c:if
		test="${usuariologado.cargo eq 1 or usuariologado.cargo eq 3}">
			<a href="javascript:void(0)"
				onclick="menuadminclick('registrodoacao', ${usuariologado.cargo})">Registrar Doação</a>
	</c:if>
</li>
<li class="divider"></li>
<li><a class="subheader">Professores</a></li>
<li>

			<a href="javascript:void(0)"
				onclick="menuadminclick('cadastroprofessor', ${usuariologado.cargo})">Cadastrar</a>
			<a href="javascript:void(0)"
				onclick="menuadminclick('editarprofessor', ${usuariologado.cargo})">Editar</a>
</li>

</ul>
</div>
</li>	
</ul>
</li>	      



<li class="no-padding">
<ul class="collapsible collapsible-accordion">
<!-- MENU RELATÓRIOS -->
<li>
<a class="collapsible-header waves-effect waves-teal">RELATÓRIOS<i class="mdi-navigation-arrow-drop-down"></i></a>
<!-- Dropdwon logistica -->
<div class="collapsible-body">

<ul>
<li><a class="subheader">Qualidade</a></li>
<li>

			<a href="glassListar.jsp">
			Posição consolidada do consultor</a>
			<a href="adocaoTermometro.jsp">
				Termômetro das adoções do consultor</a>
			<a href="roteiroResumoListar.jsp">
				Relatório de visitas do consultor</a>
		<c:if test="${usuariologado.cargo eq 1 or usuariologado.cargo eq 2}">
			<a href="javascript:void(0)"
				onclick="menuadminclick('relatdemanda', ${usuariologado.cargo})">Previsão de Vendas(xls)</a>
		</c:if>																	
</li>

<c:if test="${usuariologado.cargo eq 1 or usuariologado.cargo eq 2}">	
<li class="divider"></li>
<li><a class="subheader">Relatórios Gerenciais</a></li>
	<li>
					<a href="reportnotaslistar.jsp" >Orçamentos / Notas (Totvs)</a>	
					<a href="javascript:void(0)"
						onclick="menuadminclick('vendassintetico', ${usuariologado.cargo})">Vendas
						Sintético (xls)</a>
					<c:if test="${usuariologado.cargo eq 1}">	
					<a href="javascript:void(0)"
						onclick="menuadminclick('vendasprodutos', ${usuariologado.cargo})">Vendas
						Produtos (xls)</a>
					<a href="adminImportPrevSP.jsp">Importar
						Previsão Informada (SP)</a>
					<a 
						href="srl?acao=relatorioestoque">Estoques(xls)</a>						
					</c:if>	
	</li>	
</c:if>

</ul>
</div>
</li>			
</ul>
</li>	      

<c:if test="${usuariologado.cargo eq 1 and usuariologado.id eq 1}">
<li class="no-padding">
<ul class="collapsible collapsible-accordion">
<!-- MENU MISCELANEA -->
<li>
<a class="collapsible-header waves-effect waves-teal">MISCELÂNEA<i class="mdi-navigation-arrow-drop-down"></i></a>
<!-- Dropdwon logistica -->
<div class="collapsible-body">

<ul>
<li><a class="subheader">Acertos FTD</a></li>
<li>

		<a href="javascript:void(0)"
			onclick="menuadminclick('acertodoacoes', ${usuariologado.cargo})">Acertos das Doações (SP)</a>
</li>
</ul>
</div>
</li>			
</ul>
</li>	
</c:if>
	      
</ul>
</c:if>
<!-- END OF SIDENAV -->
	
</nav>

<nav class="transparent z-depth-0 noPrint">
	
	<div class="nav-wrapper row">
		<!-- BUTTON TO SIDENAV's ACTIVATES -->		
		<div class="col l5 m2 s1 divnavbutton">
			<c:if test="${!empty usuariologado and usuariologado.cargo != 4}">			
				<a href="#" data-activates="slide-out" title="Menu Principal" id="button-collapse-out">
				<img alt="Menu" src="resources/images/menu_icon_black.png" class="waves-effect waves-light"></a>
			</c:if>
		</div>		
		
		<!-- INICIO DIV SEARCH -->		
		<div class="col l6 m8 s9">
		
			<div class="row">
			    <form action="srl" method="post" id="formsearchitem">
             		<div class="input-field col s12">
             			<div class="ac-input" style="margin-top: 10px">
		                    <i onclick="$(this).closest('form').submit()" class="material-icons prefix" id="searchAutocomplete-icon">search</i>
		                    <input type="text" id="autocomplete-input" placeholder="Digite sua busca..." name="txtdescricao"
		                     required data-activates="singleDropdown" data-beloworigin="true" autocomplete="off">		                     
		                     <label for="autocomplete-input">Pesquisar</label>		                     
	                     </div>	                     
                    </div>
                    <input name="acao" value="pesquisarprd" type="hidden">
                </form>
            </div>
            <ul id="singleDropdown" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>                                            
            
		</div>
		<!-- FIM DIV SEARCH -->

		
		<!-- INICIO DIV CARRINHO -->
		<div class="col l1 m2 s2">
				<!-- carrinho de livros -->
					<div id="imgCart2">
						<a href="orcamentoImprimir.jsp" title="Detalhar orçamento">
							<img src="resources/images/shopping-cart-icon.png">
							<c:choose>
								<c:when test="${!empty orcamento}">
									<span id="qtdTotalCart">${orcamento.totalitens}</span>
								</c:when>
								<c:otherwise>
									<span id="qtdTotalCart">0</span>
								</c:otherwise>
							</c:choose>							
						</a>
					</div>
		</div>
		<!-- FIM DIV CARRINHO -->
						
	</div>
</nav>