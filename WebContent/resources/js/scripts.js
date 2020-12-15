function pesquisarprodutoclick(){
	$("#modalpesquisarproduto").modal('show');
	$(".modaldivpesquisarproduto").load('produtoPesquisar.jsp');
};

function verificaSenhaUsuario(){	
	var s1 = $("#txtsenha1").val();
	var s2 = $("#txtsenha2").val();
	if(s1 != s2){
		$("#errorsenha").text('*erro na confirmacao da senha!');
		$("#txtsenha2").focus();
		return false;
	}else if(s1.length < 4){
		$("#errorsenha").text('*senha deve ter no minimo 4 caracteres!');
		return false;
	}else{
		$("#errorsenha").text('');
		return true;
	}	
};

function setCharAt(str,index,chr) {
    if(index > str.length-1) return str;
    return str.substr(0,index) + chr + str.substr(index+1);
};

function sendAjaxCadastroUsuario(formulario) {
  	if(verificaSenhaUsuario()){
  		$.ajax({
  	       type: "POST",
  	       url: "srl",
  	       data: formulario,
  	       dataType:'json',
  	       success: function(msg) {    	       	   
  				bootbox.alert(msg, function() {
  					$("#formCadastroUsuario").trigger('reset');
  				});
  	    	    
  	       }       
  	    });  		
  	}
 };	


 function sendAjaxEditarUsuario(formulario) {
	$.ajax({
		   type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {
	    	   
				bootbox.alert(msg, function() {
					carregarDadosUsuario();
				});
	    	    
	       }       
	});	   
 };	


function sendAjaxDeletarUsuario(id) {
	  	
		$.ajax({
		   type: "GET",
	       url: "srl?acao=deletarusuario&txtid="+id,
	       dataType:'json',
	       success: function(msg) {
	    	   
				bootbox.alert(msg, function() {
					location.reload(true);
				});
	    	    
	       }       
	    });
};	


function sendAjaxAlterarSenhaUsuario(id, senha) {
		$.ajax({
			   type: "GET",
		       url: "srl?acao=alterarsenhausuario&txtid="+id+'&txtsenha='+senha,
		       dataType:'json',
		       success: function(msg) {
		    	   $("#modalAlterarSenha").modal('hide');
					bootbox.alert(msg, function() {
					});
		    	    
		       }       
		});	  
};	

function editarprodutoclick(codigo){
		
		location.href = "produtoEditar.jsp?codigoprd="+codigo;
}



function deleteescolaclick(valor, vendedor){

	$.get("srl",{acao:'deletarescola', idescola: valor},
			function (dados){
			bootbox.alert(dados, function() {
			location.href = 'escolas.jsp';
		});
	});
};


function excluiescolaroteiroclick(valor, vendedor){

	$.get("srl",{acao:'excluirescolaroteiro', idescola: valor},
			function (dados){
			bootbox.alert(dados, function() {
				location.reload(true);
		});
	});
};


function veritempedidoclick(codigo){
	$.get("srl",{acao:'veritempedido', txtcodigo:codigo},
			function(){
		$("#modaldetalhepedido").modal('hide');
		$("#modalitempedido").modal('show');
		$(".modaldivitempedido").load('adminEditarItemPed.jsp #divcontent');
	});
};


function obsitempedidoclick(idpedido, codigo){
	$.get("srl",{acao:'obsitempedido', txtidpedido:idpedido,txtcodigo:codigo},
			function(dados){
		$("#modalpedido").modal('hide');
		$("#modalitempedido").modal('show');
		$(".modaldivitempedido").load('adminEditarItemPed.jsp #divcontent');
	});	
};


function alteraritempedidoclick(){
	var formulario = $('#formedititemped').serialize();
	sendAjaxAlterarItemPedido(formulario);
};


function sendAjaxAlterarItemPedido(formulario){
	
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {    	       	   
				bootbox.alert(msg, function() {
					location.reload(true);
				});
	    	    
	       }       
	    });	
};


function sendAjaxAtualizaPrevisao(formulario){
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {    	       	   
				bootbox.alert(msg, function() {
					location.reload(true);
				});
	    	    
	       }       
	});		
};

function sendAjaxListarRoteiros(formulario){

	$("#modalajaxloader").modal({
  	  backdrop: 'static',
  	  keyboard: false
    });
    
    $("#modalajaxloader").modal("show");	        	
	
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'html',
	       success: function() {
	    	   $("#modalajaxloader").modal("hide");
	    	   location.href = 'roteiroListagem.jsp';
	       }       
	    });	
};



//PEGA OS DADOS DO PRODUTO NO SERVLET ATRAVÉS DO CÓDIGO E PREENCHE A POPUP MODALPRODUTO
function codigoclick(codigoprd){
				
	$.get("srl",{acao:"consultarprd", codigo:codigoprd},
			function(dados){				
		var target = '#'+codigoprd;
		
		//Carrega os dados dos elementos html
		$(target+"Codigo").text(dados.codigo);
		$(target+"ProdutoLabel").text(dados.descricao);
		$(target+"ProdutoAutor").text(dados.autor);
		$(target+"ProdutoFamilia").text(dados.familia);
		$(target+"ProdutoNivel").text(dados.nivel);
		$(target+"ProdutoColecao").text(dados.colecao);
		$(target+"ProdutoCodbar").text(dados.codbar);				
		$(target+"ProdutoObs").text(dados.obs);
	});
};

//FAZ UMA REQUISIÇÃO DOS DADOS DOS PEDIDOS DO PRODUTO NO SERVLET E CARREGA A MODALPEDIDO 
function enviarclick(codigoprd, descricaoprd){

	var acaoserver = $("#selpedidos"+codigoprd+" option:selected").val();
	var anoconsulta = $("#selanos"+codigoprd+" option:selected").val();	
	
	$("#modal"+codigoprd).modal("hide");
    $("#modalajaxloader").modal({
  	  backdrop: 'static',
  	  keyboard: false
    });
    
    $("#modalajaxloader").modal("show");	        	

	
	if(acaoserver == 'consultarpedidos'){
		
		$.get("srl",{acao:acaoserver, codigo:codigoprd, ano:anoconsulta},
			
			function(dados){
				$("#modalajaxloader").modal("hide");
				$("#modalpedido").modal('show');
				$("#modaldivpedidos").load('pedidosDoProduto.jsp #divcontent');
				$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);
												
				
				$("#modalpedido").on("click", "#detalhenotafiscal", function(event){
				    
					var nota = $(this).attr('alt');
					notafiscalclick(nota);
				});
		});
		
	}else if(acaoserver == 'consultaradocoes'){

		$.get("srl",{acao:acaoserver, txtcodigo:codigoprd, txtano:anoconsulta},
				
				function(dados){
					$("#modalajaxloader").modal("hide");
					$("#modalpedido").modal('show');
					$("#modaldivpedidos").load('adocoesDoProduto.jsp #divcontent');
					$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);
													
					
					$("#modalpedido").on("click", "#detalheadocao", function(event){
					    
						var s = $(this).attr('alt');
						var parametros = s.split(';');
						var idescola = parametros[0];
						var serie = parametros[1];
						var ano = parametros[2];
						
						detalheadocaoclick(idescola, serie, ano);
					});
			});

	}else if(acaoserver == 'consultardoacoes'){

		
		 $.get("srl",{acao:acaoserver, txtcodigo:codigoprd, txtano:anoconsulta},
				
				function(dados){
			 		$("#modalajaxloader").modal("hide");
					$("#modalpedido").modal('show');
					$("#modaldivpedidos").load('doacoesDoProduto.jsp #divcontent');
					$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);
													
					
					$("#modalpedido").on("click", "#detalhedoacao", function(event){
					    
						var s = $(this).attr('alt');
						var parametros = s.split(';');
						var iddoacao = parametros[0];
						
						detalhedoacaoclick(iddoacao);
					});
				 
			});
			
	}else if(acaoserver == 'consultarkardex'){

		
		 $.get("srl",{acao:acaoserver, txtcodigo:codigoprd, txtano:anoconsulta},
				
				function(dados){
			 		$("#modalajaxloader").modal("hide");
					$("#modalpedido").modal('show');
					$("#modaldivpedidos").load('kardexDoProduto.jsp #divcontent');
					$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);

					$("#modalpedido").on("click", "#detalhekardex", function(event){
					    
						var s = $(this).attr('alt');
						var parametros = s.split(';');
						var idorcam = parametros[0];
						var idcliente = parametros[1];
						var nomecliente = parametros[2];
						
						detalhekardexclick(idorcam,idcliente, nomecliente);
					});
			});
			
	}else if(acaoserver == 'consultarpendencias'){

		
		 $.get("srl",{acao:acaoserver, txtcodigo:codigoprd, txtano:anoconsulta},
				
				function(dados){
			 		$("#modalajaxloader").modal("hide");
					$("#modalpedido").modal('show');
					$("#modaldivpedidos").load('pendenciasDoProduto.jsp #divcontent');
					$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);																		
			});
			
	}
};

//FAZ UMA REQUISIÇÃO AO SERVLET PARA PEGAR O DETALHE DA ADOCAO E CARREGA A MODALDETALHENOTA
function detalheadocaoclick(idescola, serie, ano){
	$.get("srl",{acao:"detalharadocao", txtidescola:idescola, txtserie:serie, txtano:ano, txttabela:"atual"},
			function(dados){
				$("#modalpedido").modal('hide');
				//$("#modaldetalhenota").modal('show');
				//$(".modaldivdetalhenota").load('orcamento.jsp');
				location.href = "orcamentoImprimir.jsp";
		});		
};


//FAZ UMA REQUISIÇÃO AO SERVLET PARA PEGAR O DETALHE DO ORCAMENTO E CARREGA MODALDETALHENOTA
function detalhekardexclick(idorcam, idcliente, nomecli){
	$.get("srl",{acao:"detalharkardex", txtidorcam:idorcam, txtidcliente:idcliente, txtnome:nomecli},
			function(dados){
				$("#modalpedido").modal('hide');
				$("#modaldetalhenota").modal('show');
				$(".modaldivdetalhenota").load('orcamento.jsp #divcontent');				
		});		
};


//IMPRIME UM ELEMENTO SEPARADO DO SITE
function printElement(elem) {
    var domClone = elem.cloneNode(true);
    
    var $printSection = document.getElementById("printSection");
    
    if (!$printSection) {
        var $printSection = document.createElement("div");
        $printSection.id = "printSection";
        document.body.appendChild($printSection);
    }
    
    $printSection.innerHTML = "";
    $printSection.appendChild(domClone);
    window.print();
};

//FAZ UMA REQUISIÇÃO AO SERVLET PARA PEGAR O DETALHE DA NOTA FISCAL E CARREGA A MODALDETALHENOTA
function notafiscalclick(nota){

	$("#modalajaxloader").modal({
    	  backdrop: 'static',
    	  keyboard: false
      });
      
      $("#modalajaxloader").modal("show");	        	

	$.get("srl",{acao:"detalharnotafiscal", txtidnota:nota},
		function(dados){
		    $("#modalajaxloader").modal("hide");
			$("#modalpedido").modal('hide');
			$("#modaldetalhenota").modal();
			$(".modaldivdetalhenota").load('notafiscalDetalhar.jsp #divcontent');			
	});	
};


function menuadminclick(valor, cargo){

		if(valor == 'importped'){
			if(cargo == 1){
				location.href = "adminImportRelPed.jsp";
			}else{
				alert("Usuário não autorizado!");
			}
		}else if(valor == 'importnf'){
			location.href = "adminImportNf.jsp";
		}else if(valor == 'exportnf'){
			location.href = "adminExportNf.jsp";
		}else if(valor == 'relnfped'){
			location.href = "adminNfToPedido.jsp";
		}else if(valor == 'relatped'){
			location.href = 'adminPedPorData.jsp';
		}else if(valor == 'relatpend'){
			location.href = 'adminRelatPend.jsp';
		}else if(valor == 'datarecnf'){
			location.href = 'adminDataRecNF.jsp';
		}else if(valor == 'relatdemanda'){
			location.href = 'adminRelatDemanda.jsp';
		}else if(valor == 'vendassintetico'){
			location.href = 'adminVendasSintetico.jsp';
		}else if(valor == 'vendasprodutos'){
			location.href = 'adminRelatVdProd.jsp';
		}else if(valor == 'importcadprod'){
			location.href = 'adminImportCadProd.jsp';
		}else if(valor == 'importtabpre'){
			location.href = 'adminImportTabPre.jsp';
		}else if(valor == 'exporttabpre'){
			location.href = 'adminExportTabPre.jsp';
		}else if(valor == 'cadescola'){
			location.href = "escolaCadastrar.jsp";
		}else if(valor == 'listescola'){
			location.href = 'escolaListar.jsp';
		}else if(valor == 'listroteiro'){
			location.href = "roteiroListar.jsp";			
		}else if(valor == 'cadastrarproduto'){
			location.href = "produtoCadastrar.jsp";
		}else if(valor == 'cadastrousuario'){
			location.href = "usuarioCadastrar.jsp";
		}else if(valor == 'editarusuario'){
			location.href = "usuarioEditar.jsp";
		}else if(valor == 'registraradocao'){
			loadSeriesProduto();
			location.href = "adocaoSelecionar.jsp";
		}else if(valor == 'exportaprodutos'){
			location.href = 'adminExportCadProd.jsp';		
		}else if(valor == 'listdoacao'){
			location.href = "doacaoListar.jsp";
		}else if(valor == 'registrodoacao'){
			prepararDoacao();
		}else if(valor == 'cadastroprofessor'){
			location.href = "professorCadastrar.jsp";
		}else if(valor == 'editarprofessor'){
			location.href = "professorEditar.jsp";
		}else if(valor == 'listarpedcliente'){
			location.href = 'pedClienteListar.jsp';
		}else if(valor == 'importorcam'){
			location.href = 'adminImportOrcam.jsp';
		}else if(valor == 'listarnf'){
			location.href = "notafiscalListar.jsp";
		}else if(valor == 'resumeroteiro'){
			location.href = "roteiroResumoListar.jsp";
		}else if(valor == 'acertodoacoes'){
			location.href = "doacaoAcertos.jsp";
		}else if(valor == 'termometroadocoes'){
			location.href = "adocaoTermometro.jsp";
		}else{
			location.href = "index.jsp";
		}		
};


function prepararDoacao(){
	$.get("srl",{acao:'preparedoacao'},
			function(){
		location.href = "doacaoRegistrar.jsp";
	});	
};

function detalharroteiroclick(valor){
	$.get("srl",{acao:'detalharroteiro', txtid:valor},
			function(){
		location.href = "roteiro.jsp";
	});	
};

function listarMunicipios(setor, dependencia){
	
	$("#cmbMunicipio").empty();
	$("#cmbMunicipio").append("<option value='todos'>TODOS</option>");
	$.get("srl",{acao:"listarmunicipios", txtsetor: setor, txtdependencia: dependencia},
			function(dados){
			$.each(dados, function(i, item){
				$("#cmbMunicipio").append("<option value='"+item+"'>"+item+"</option>");
			});
			$("#cmbMunicipio").selectpicker("refresh");			
	});
};

function listarBairros(setor, dependencia, municipio){
	
	$("#cmbBairro").empty();

	$("#cmbBairro").append("<option value='todos'>TODOS</option>");
	
	$.get("srl",{acao:"listarbairros", txtsetor:setor, txtdependencia: dependencia, txtmunicipio: municipio},
			function(dados){
			$.each(dados, function(i, item){
				$("#cmbBairro").append("<option value='"+item+"'>"+item+"</option>");
			});
			$("#cmbBairro").selectpicker("refresh");			
	});
};

function sendAjaxResumoRoteiro(formulario){
	$.ajax( {
	    url: 'srl',
	    type: 'POST',
	    data: formulario,
	    beforeSend: function(){
	      $("#modalajaxloader").modal({
	    	  backdrop: 'static',
	    	  keyboard: false
	      });
	      
		  $("#modalajaxloader").modal("show");
	    },
	    error: function(xhr, status, error){
	    	$("#modalajaxloader").modal("hide"); 						    	
	    },
		success: function(){
			
			$("#modalajaxloader").modal("hide");
			location.href = "roteiroResumo.jsp";
		}
	});			
};


function sendAjaxListarEscolas(formulario){
	$.ajax( {
	    url: 'srl',
	    type: 'POST',
	    data: formulario,
	    beforeSend: function(){
	      $("#modalajaxloader").modal({
	    	  backdrop: 'static',
	    	  keyboard: false
	      });
	      
		  $("#modalajaxloader").modal("show");
	    },
	    error: function(xhr, status, error){
	    	$("#modalajaxloader").modal("hide");
	    },
		success: function(){
			
			$("#modalajaxloader").modal("hide");
			location.href = "escolas.jsp";
		}
	});		
};

function listagemescolas(){

	$.ajax( {
	    url: 'srl?acao=listarescolas',
	    type: 'GET',
	    beforeSend: function(){
	      $("#modalajaxloader").modal({
	    	  backdrop: 'static',
	    	  keyboard: false
	      });
	      
		  $("#modalajaxloader").modal("show");
	    },
	    error: function(xhr, status, error){
	    	$("#modalajaxloader").modal("hide"); 						    	
	    },
		success: function(){
			
			$("#modalajaxloader").modal("hide");
			location.href = "escolas.jsp";
		}
	});	
	
};

function enviarEmailOrcamento(nome, fone, email){
    
	$("#modalajaxloader").modal({
  	  backdrop: 'static',
  	  keyboard: false
    });
    
	$("#modalajaxloader").modal("show");

	$.get('srl',{acao:"sendmailorcam", txtemail:email, txtnome:nome, txtfone:fone}, function(msg){
	   $("#modalajaxloader").modal("hide");
 	   bootbox.alert(msg, function(e) {
	   });    	   		
	});
};

function sendMailPedCliente(){
	$("#modalajaxloader").modal({
	  	  backdrop: 'static',
	  	  keyboard: false
	    });
	    
	$("#modalajaxloader").modal("show");
	
	$.get('srl',{acao:"sendmailpedcliente"}, function(msg){
		   $("#modalajaxloader").modal("hide");
	 	   		bootbox.alert(msg, function(e) {
		   });		
	});
};

function enviarEmailNotaFiscal(formulario){
	$("#modalajaxloader").modal({
	  	  backdrop: 'static',
	  	  keyboard: false
	    });
	    
	$("#modalajaxloader").modal("show");
	
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function(msg){
	    	   $("#modalajaxloader").modal("hide");
	     	   bootbox.alert(msg, function(e) {
	    	   });	    	   
	       }
	});	
		
};


function menupesquisaradocaoclick(){
	location.href = "adocaoPesquisar.jsp";
};


//ACIONA A PESQUISA DE UMA ADOCAO
function pesquisaradocaoclick(valor){
			
		var descricao = valor.value;
			
		$("input#txtdescricaoescola").autocomplete({
			source: function (request, response){
				$.ajax({
					dataType: 'json',
					type: 'Get',
					url: 'srl?acao=pesquisarescola&txtdescricao='+descricao,
					success: function(data){
						$('input#txtdescricaoescola').removeClass('ui-autocomplete-loading');
						var search = descricao.toUpperCase();
						response(data);
					},
					error: function(data){
						$('input#txtdescricaoescola').removeClass('ui-autocomplete-loading');
					}
				});
			},
			minLength: 1,
			maxShowItems: 5,
			 focus: function( event, ui ) {
				 $( "#txtdescricaoescola" ).val( ui.item.label);
				 return false;
				 },
				 select: function( event, ui ) {
					 $( "#txtdescricaoescola" ).val( ui.item.label );
					 $( "#txtdescricaoescola-id" ).val( ui.item.value );
					 $( "#txtdescricaoescola-description" ).html( ui.item.desc );							 
					 $("#cmbSerie").empty();
					 loadComboAnosAdocao(ui.item.value);							 
					 $("#divoptserieescola").show();
					 return false;
				}						
		}).autocomplete( "instance" )._renderItem = function( ul, item ) {
			 return $( "<li>" )
			 .append( "<a><span style='font-weight: bold; font-size:11pt;'>"
					 + item.label + "</span><br><span style='font-size: 8pt'>"
					 + item.desc + "["+item.value+"]</span></a>" )
			 .appendTo( ul );
		};

};



function pesquisarempresaclick(valor){
	
	var descricao = valor.value;
	descricao = descricao.toUpperCase();
	
			$("input#txtdescricaoempresa").autocomplete({
				source: function (request, response){
					$.ajax({
						dataType: 'json',
						type: 'Get',
						url: 'srl?acao=pesquisarempresa&txtdescricao='+descricao,
						success: function(data){
							$('input#txtdescricaoempresa').removeClass('ui-autocomplete-loading');
							response(data);
						},
						error: function(data){
							$('input#txtdescricaoempresa').removeClass('ui-autocomplete-loading');
						}
					});
				},
				minLength: 1,
				maxShowItems: 5,
				 focus: function( event, ui ) {						 
					 $( "#txtdescricaoempresa" ).val( ui.item.label );
					 return false;
					 },
					 select: function( event, ui ) {
						 $( "#txtdescricaoempresa" ).val( ui.item.label );
						 $( "#txtidempresa" ).val( ui.item.value );
						 $( "#txtdescricaoempresa-description" ).html( ui.item.desc + "("+ui.item.value+")");
						 $("#btnDialogDownloadPedido").attr("href","srl?acao=downloadpedcliente&txtidempresa="+ui.item.value+"&txtdescricaoempresa="+ui.item.label);
						 return false;
					}						
			})
			.autocomplete( "instance" )._renderItem = function( ul, item ) {
				 return $( "<li>" )
				 .append( "<a><span style='font-weight: bold; font-size:11pt;'>"
						 + item.label + "</span><br><span style='font-size: 8pt'>"
						 + item.desc + "</span><span style='font-size: 8pt'>("+ item.value + ")</span></a>" )
				 .appendTo( ul );
			};
	
};



//ACIONA A PESQUISA DE UMA ADOCAO
function pesquisarescoladoacaoclick(valor){
			
			var descricao = valor.value;
			
				$("input#txtdescricaoescola").autocomplete({
					source: function (request, response){
						$.ajax({
							dataType: 'json',
							type: 'Get',
							url: 'srl?acao=pesquisarescola&txtdescricao='+descricao,
							success: function(data){
								$('input#txtdescricaoescola').removeClass('ui-autocomplete-loading');
								response(data);
							},
							error: function(data){
								$('input#txtdescricaoescola').removeClass('ui-autocomplete-loading');
							}
						});
					},
					minLength: 1,
					maxShowItems: 5,
					 focus: function( event, ui ) {
						 $( "#txtdescricaoescola" ).val( ui.item.label );
						 return false;
						 },
						 select: function( event, ui ) {
							 $( "#txtdescricaoescola" ).val( ui.item.label );
							 $( "#txtdescricaoescola-id" ).val( ui.item.value );
							 $( "#txtdescricaoescola-description" ).html( ui.item.desc );
							 loadComboProfessor(ui.item.value);
							 $("#divoptprofessor").show();
							 
							 return false;
						}						
				})
				.autocomplete( "instance" )._renderItem = function( ul, item ) {
					 return $( "<li>" )
					 .append( "<a><span style='font-weight: bold; font-size:11pt;'>"
							 + item.label + "</span><br><span style='font-size: 8pt'>"
							 + item.desc + "</span></a>" )
					 .appendTo( ul );
				};
};



function pesquisarescolaeditarclick(valor){
			alert("DIGITOU!!!");
			var descricao = valor;
			
				$("input#txtdescricaoescola").autocomplete({
					source: function (request, response){
						$.ajax({
							dataType: 'json',
							type: 'Get',
							url: 'srl?acao=pesquisarescola&txtdescricao='+descricao,
							success: function(data){
								$('input#txtdescricaoescola').removeClass('ui-autocomplete-loading');
								response(data);
							},
							error: function(data){
								$('input#txtdescricaoescola').removeClass('ui-autocomplete-loading');
							}
						});
					},
					minLength: 1,
					maxShowItems: 5,
					 focus: function( event, ui ) {
						 $( "#txtdescricaoescola" ).val( ui.item.label );
						 return false;
						 },
						 select: function( event, ui ) {
							 $( "#txtdescricaoescola" ).val( ui.item.label );
							 $( "#idescola" ).val( ui.item.value );
							 $( "#txtdescricaoescola-description" ).html( ui.item.desc );
							 
							 $("#formeditarescola").trigger("reset");

							 carregarDadosEscola(ui.item.value, ui.item.idvend);
							 
							 return false;
						}						
				})
				.autocomplete( "instance" )._renderItem = function( ul, item ) {
					 return $( "<li>" )
					 .append( "<a><span style='font-weight: bold; font-size:11pt;'>"
							 + item.label + "</span><br><span style='font-size: 8pt'>"
							 + item.desc + "</span></a>" )
					 .appendTo( ul );
				};
};


function loadComboProfessor(valor){
	var options = "";
	$.get("srl",{acao:"loadcomboprofessor", txtidescola:valor},
			function(dados){									
				$.each(dados, function(i, item){
					options += "<option value='"+item.id+"'>"+item.nome+"</option>";
				});
				
				$("#cmbProfessor").empty();
				$("#cmbProfessor").html(options).show();
				$("#cmbProfessor").selectmenu("refresh");				
	});	
};



function sendAjaxEditarEscola(formulario, vendedor) {
  	
	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {

    	   bootbox.alert(msg, function(e) {
    		   	var pagina = window.location.pathname;
	 			if(pagina == '/escolaEditar2.jsp' || pagina == '/ftd/escolaEditar2.jsp'){
	 				location.reload(true);
				}else if(pagina == '/escolaEditar1.jsp' || pagina == '/ftd/escolaEditar1.jsp'){
					location.href = 'roteiro.jsp';
				}else{
					location.href = 'escolas.jsp';
				}			    		       		   
    	   });    	   
		}       
    });
	
 };	


function sendAjaxEditarMap(formulario) {
	  	
		$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario
	    });
		
};	


function sendAjaxCadastrarProduto(formulario) {
	  	
		$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {    	       	   
				bootbox.alert(msg, function() {
					$('#formcadastrarproduto').trigger("reset");
				});
	    	    
	       }       
	    });
};	



 function sendAjaxEditarProduto(formulario) {
	  	
		$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {    	       	   
				bootbox.alert(msg, function() {

					location.href = 'tabela.jsp';
					
				});
	    	    
	       }       
	    });
 };	


 function sendAjaxCadastroEscola(formulario) {

		$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {
				bootbox.alert(msg, function() {
					$('#formcadastroescola').trigger("reset");
				});
	    	       	   
	       }       
	    });
 };	
 
function loadCodCliente(){
	$.get("srl",{acao:"loadcodcliente"}, function(dados){
		$("#txtcodigo").val(dados);
	});
};
 

function sendAjaxCadastroCliente(formulario) {

		$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {
	    	   if(msg == 'ok'){
					bootbox.alert("Cliente salvo!", function() {
						$('#formcadastrocliente').trigger("reset");
					});
	    	   }else{
					bootbox.alert("Erro ao salvar!", function() {
					});	    		   
	    	   }
	       }       
	    });
};	


function sendAjaxNotaPedido(formulario) {
  	
	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {    	       	   
			bootbox.alert(msg, function() {
			});
    	    
       }       
    });
};	


function sendAjaxListarNota(formulario) {
  	
	$("#modalajaxloader").modal({
	  	  backdrop: 'static',
	  	  keyboard: false
	    });
	    
  $("#modalajaxloader").modal("show");	        	

	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function() {
	    	   $("#modalajaxloader").modal("hide");
	    	   location.href = "notafiscalListagem.jsp";
	       }       
   });

};	


function roteiroaddescolaclick(id, element){								

		if($(element).hasClass("btn-default")){
	      	$(element).addClass("btn-success").removeClass("btn-default");
		}
		
		$.ajax({
	       type: "GET",
	       url: "srl?acao=roteiroaddescola&idescola="+id,
	       dataType:'json',
	       success: function(msg) {
				bootbox.alert(msg, function() {
					$("#totalroteiro").load('roteiroTotal.jsp #divcontent');
				});
	    	       	   
	       }       
	    });
		
};
	 
	 
	 
function detalheescolaclick(id, idusuario){
	var pagina = window.location.pathname;
	if(pagina == '/roteiro.jsp' || pagina == '/ftd/roteiro.jsp'){
		location.href= 'escolaEditar1.jsp?idescola='+id+"&idusuario="+idusuario;
	}else{
		location.href= 'escolaEditar.jsp?idescola='+id+"&idusuario="+idusuario;
	}
	
	
};

function carregarDadosEscola(id, idusuario){
	
	$.get("srl",{acao:'detalheescola', idescola:id},
			function(dados){	
			
			$("#idescola").val(id);
			
			dependenciaSelect(dados.dependencia);
			
			classificacaoselect(dados.classificacao);
			
			vendedorselect(idusuario);
			
			$('form').loadJSON(dados);
			$("#cep").val(dados.cep);
			$("#cep").mask("99.999-999");
			$("#cnpj").val(dados.cnpj);
			$("#cnpj").mask("99.999.999/9999-99");												
	});		

};

function dependenciaSelect(dependencia){
	
	$("#cmbDependencia").empty();
	
	var aDep = {"privada":"Privada","publica":"Pública"};

	$.each(aDep, function(key, value){
		if(dependencia == key){
			$("#cmbDependencia").append("<option selected value='"+key+"'>"+value+"</option>");
		}else{
		    $("#cmbDependencia").append("<option value='"+key+"'>"+value+"</option>");
		}		
	});
	$("#cmbDependencia").selectpicker("refresh");
	
};

function numeroalunosclick(id){

	$.get("srl",{acao:'numeroalunosescola', idescola:id},
			function(dados){
		$("#modalAlunos").modal('show');
		$("#nome").text(dados.nome);
		$("#infantil0").val(dados.infantil0);
		$("#infantil1").val(dados.infantil1);
		$("#infantil2").val(dados.infantil2);
		$("#infantil3").val(dados.infantil3);
		$("#infantil4").val(dados.infantil4);
		$("#infantil5").val(dados.infantil5);
		$("#ano1").val(dados.ano1);
		$("#ano2").val(dados.ano2);
		$("#ano3").val(dados.ano3);
		$("#ano4").val(dados.ano4);
		$("#ano5").val(dados.ano5);
		$("#ano6").val(dados.ano6);
		$("#ano7").val(dados.ano7);
		$("#ano8").val(dados.ano8);
		$("#ano9").val(dados.ano9);
		$("#serie1").val(dados.serie1);
		$("#serie2").val(dados.serie2);
		$("#serie3").val(dados.serie3);
		$("#eja").val(dados.eja);
		$("#supletivo").val(dados.supletivo);
		$("#id").val(dados.id);
		$("#txttotalalunos").text(" [Total de alunos: "+dados.totalalunos+"]");
		$("#txtatualizacao").text(" [Última atualização - ano/período: "+dados.ano+"]");
	});	
};


function roteiroobservacaoclick(id){
		
	$.get("srl",{acao:'detalheescola', idescola:id},
			function(dados){
		$("#modalObservacao").modal('show');			
		$("#divmodalobservacao").load('roteiroObservacao.jsp #divcontent');
		
	});	
		
};

function btnsalvarobservacaoclick(){
	var formulario = $("#formobservacao").serialize();
	sendAjaxSalvarObservacao(formulario);
};

function tabeladeprecosclick(){
	$.get("srl",{acao:'tabeladeprecos'},
			function(){
	});		
};

function sendAjaxSalvarObservacao(formulario) {

	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {
			bootbox.alert(msg, function() {
				location.reload(true);
			});
    	       	   
       }       
    });
};	



function btnsalvaralunosclick(){
	
	var formulario = $("#formnumeroalunos").serialize();
	sendAjaxSalvarAlunos(formulario);
};


function sendAjaxSalvarAlunos(formulario) {

	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {
			bootbox.alert(msg, function() {
				var pagina = window.location.pathname;
				if(pagina == "/escolas.jsp" || pagina == "/ftd/escolas.jsp"){
					location.reload(true);
				}
			});
    	       	   
       }       
    });
	
};	



function sendAjaxSalvarPedCliente(formulario) {

	$("#modalajaxloader").modal({
	  	  backdrop: 'static',
	  	  keyboard: false
	    });
	    
   $("#modalajaxloader").modal("show");	        	

	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {
    	     $("#modalajaxloader").modal("hide");
			 bootbox.confirm("Deseja imprimir?", function(result) {
				 if(result){
						imprimirPedCliente(); 
				 }else{
						sendAjaxDescartarPedCliente();
				}
	   		});
       }       
    });
};	



function sendAjaxAtualizarPedCliente(formulario) {
	$("#modalajaxloader").modal({
	  	  backdrop: 'static',
	  	  keyboard: false
	    });
	    
    $("#modalajaxloader").modal("show");	        	

	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {
    	   $("#modalajaxloader").modal("hide");
    	   	var pagina = window.location.pathname;
    	   	if(pagina == '/pedClienteRegistrar.jsp' || pagina == '/ftd/pedClienteRegistrar.jsp'){
    			location.href = 'pedClienteRegistrar.jsp';    	   		
    	   	}else{
    	   		location.href = 'pedClientePendente.jsp';
    	   	}
			$("#totalpedido").load('pedClienteTotal.jsp #divcontent');
       }       
    });
};	



function btnsalvarroteiroclick(){
	var formulario = $("#formsalvarroteiro").serialize();
	sendAjaxSalvarRoteiro(formulario);
}


function sendAjaxSalvarRoteiro(formulario) {

	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {
			bootbox.alert(msg, function() {
				location.href = "roteiroListar.jsp";
				var left  = ($(window).width()/2)-(900/2),
			    top   = ($(window).height()/2)-(600/2),
			    popup = window.open ("roteiroImprimir.jsp", "Roteiro de Divulgação", "width=900, height=600, top="+top+", left="+left);
				//var jqwin = $(popup);
				//$(jqwin).blur(function(){
				//	this.close();
				//});
				popup.focus();
			});
    	       	   
       }       
    });
};	

function escolasPrint(){
	var left  = ($(window).width()/2)-(900/2),
    top   = ($(window).height()/2)-(600/2),
    popup = window.open ("escolasImprimir.jsp", "Impressão de Escolas", "width=900, height=600, top="+top+", left="+left);
	var jqwin = $(popup);
	$(jqwin).blur(function(){
		this.close();
	});
	popup.focus();
};

function roteiroPrint(){
	var left  = ($(window).width()/2)-(900/2),
    top   = ($(window).height()/2)-(600/2),
    popup = window.open ("roteiroImprimir.jsp", "Roteiro de Divulgação", "width=900, height=600, top="+top+", left="+left);
	var jqwin = $(popup);
	$(jqwin).blur(function(){
		this.close();
	});
	popup.focus();
};


function doacaoPrint(iddoacao){
	$.get("srl",{acao:"doacaoimprimir", txtiddoacao:iddoacao},
			function(){
			var left  = ($(window).width()/2)-(900/2),
		    top   = ($(window).height()/2)-(600/2),
		    popup = window.open ("doacaoImprimir.jsp", "Doação a Professor", "width=900, height=600, top="+top+", left="+left);
			var jqwin = $(popup);
			$(jqwin).blur(function(){
				this.close();
			});
			popup.focus();							
	});
};

function detalhesalvarroteiroclick(){
	//$('#modalRoteiro').modal('show');
	//$('#modalsalvarroteiro').load('roteiroSalvar.jsp #divcontent');
	location.href = 'roteiroSalvar.jsp';
	//$('#modalRoteiro').modal('show');
	//vendedorselect();
	//$('#modalRoteiro').modal('show');
	//$("#cmbVendedor").selectpicker("refresh");
}

function refreshSelectMenu(item){
	$('#'+item).selectpicker('refresh');
};

function cancelarroteiroclick(){

	$.get("srl",{acao:'cancelarroteiro'},
			function(msg){
			bootbox.alert(msg, function() {
				location.href = "index.jsp";
			});
	});	

}

function vendedorselect(){
	var setores = "";

	$("#cmbVendedor").empty();
	
	$.get("srl",{acao:"loadvendedores"},
			
		function(dados){
			
			$.each(dados, function(i, item){
				$("#cmbVendedor").append("<option value='"+item.setor+"'>"+item.nome+"</option>");
				setores += item.setor + ", ";
			});

			$("#cmbVendedor").selectpicker("refresh");
	});

};


function vendedorselect(idvendedor){
	
	$("#cmbVendedor").empty();
		
	$.get("srl",{acao:"loadvendedores"},
			
		function(dados){
			$.each(dados, function(i, item){
				if(idvendedor == item.id)
					$("#cmbVendedor").append("<option selected value='"+item.setor+"'>"+item.nome+"</option>");
				else
					$("#cmbVendedor").append("<option value='"+item.setor+"'>"+item.nome+"</option>");
			});
			$("#cmbVendedor").selectpicker("refresh");					
	});

};

function classificacaoselect(classificacao){
	
	$("#cmbClassificacao").empty();
	
	$.get("srl",{acao:"loadclassificacao"},
			
			function(dados){

				$.each(dados, function(i, item){
					if(classificacao == item.idclassificacao)
						$("#cmbClassificacao").append("<option selected value='"+item.idclassificacao+"'>"+item.classificacaoescola+"</option>");
					else
						$("#cmbClassificacao").append("<option value='"+item.idclassificacao+"'>"+item.classificacaoescola+"</option>");
				});
				$("#cmbClassificacao").selectpicker("refresh");					
		
	});
};


function sendAjaxSalvarDoacao(formulario){
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function() {
	    	   location.href = 'index.jsp';
	    	   
				var left  = ($(window).width()/2)-(900/2),
			    top   = ($(window).height()/2)-(600/2),
			    popup = window.open ("doacaoImprimir.jsp", "Doação a Professor", "width=900, height=600, top="+top+", left="+left);
				var jqwin = $(popup);
				$(jqwin).blur(function(){
					this.close();
				});
				popup.focus();	    	   
	       }       
	    });	
};


function sendAjaxDescartarOrcamento(){
	$.get("srl",{acao:"descartar"}, function(){
		location.href = 'index.jsp';
	});
};


function sendAjaxListarDoacoes(formulario){

	$("#modalajaxloader").modal({
	  	  backdrop: 'static',
	  	  keyboard: false
	    });
	    
    $("#modalajaxloader").modal("show");	        	

	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function() {
	    	   $("#modalajaxloader").modal("hide");
	    	   //$("#listagemdoacao").load('doacaoListagem.jsp #divcontent');
	    	   location.href = "doacaoListagem.jsp";
	       }       
	    });
	
};


function alterardoacaoclick(iddoacao){
	$.get("srl",{acao:"alterardoacao", txtiddoacao:iddoacao},
		function(){
		location.href = 'doacaoRegistrar.jsp';
	});
};

function deletardoacaoclick(iddoacao){
	bootbox.confirm("Tem certeza? [remover doacao "+iddoacao+"]", function(result) {
		if(result){	
			$.get("srl",{acao:"deletardoacao", txtiddoacao:iddoacao},
					function(msg){
						bootbox.alert(msg, function(e) {
							var pagina = window.location.pathname;
							if(pagina == '/doacaoListagem.jsp' || pagina == '/ftd/doacaoListagem.jsp')
								location.href = 'doacaoListagem.jsp';
						});	    	       	   
			});
		}
	});
};


function loadCombosCadastroProfessor(){
		
	$.get("srl",{acao:"pesquisarescola", txtdescricao:"todos"},
			function(dados){
			$("#cmbProfEscolas").empty();
			$.each(dados, function(i, item){
				$("#cmbProfEscolas").append("<option value='"+item.value+"'>"+item.label+"</option>");
			});

			$("#cmbProfEscolas").selectpicker('refresh');

		});
	
	$.get("srl",{acao:"loadcombodisciplinas"},
			function(dados){
			$.each(dados, function(i, item){
				$("#cmbProfDisciplinas").append("<option value='"+item+"'>"+item+"</option>");
			});
			$("#cmbProfDisciplinas").selectpicker('refresh');
			
		});
	
	loadCombosUfMun();
};


function sendAjaxCadastroProfessor(formulario){
	
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {
				bootbox.alert(msg, function(e) {
					location.reload(true);
					$(window).scrollTop(0);
				});	    	       	   
	       }       
	    });	
};


function btnListarProfessoresClick(idescola){
	$.get("srl",{acao:"listarprofessores", txtidescola:idescola},
			function (dados){
			var pagina = window.location.pathname;
			if(pagina == "/escolaEditar2.jsp" || pagina == "/ftd/escolaEditar2.jsp"){
				location.href = "professorListagem.jsp?escola="+$("#nome").val();
			}else{
				$("#modalprofessores").modal("show");
				$("#modalProfessorLabel").text("Listagem de professores");
				$(".modaldivdetalheprofessores").load("professorListagem.jsp #divcontent");
			}
	});
};

function carregarDadosProfessor(idprofessor){
	$.get("srl",{acao:"dadosprofessor",txtidprofessor:idprofessor},
			function(dados){
			
			var pagina = window.location.pathname;
			
			if(pagina == "/professorListagem.jsp" || "/ftd/professorListagem.jsp"){
				
			}
			$("#modalprofessores").modal("hide");
			$("#modaleditarprofessor").modal("show");			
			
			$("#txtnome").val(dados.nome);
			$("#txtemail").val(dados.email);
			$("#txttelefone").val(dados.telefone);
			$("#txtidprofessor").val(dados.id);
			$("#txtendereco").val(dados.endereco);
			$("#txtbairro").val(dados.bairro);
			$("#txtmunicipio").val(dados.municipio);
			$("#txtuf").val(dados.uf);
			
			loadCombosEditarProfessor(dados);
			
	});
		
};


function loadDadosProfessor(idprofessor){
	$.get("srl",{acao:"dadosprofessor",txtidprofessor:idprofessor},
			function(dados){
			
			$("#txtnome").val(dados.nome);
			$("#txtemail").val(dados.email);
			$("#txttelefone").val(dados.telefone);
			$("#txtidprofessor").val(dados.id);
			$("#txtaniversario").val(dados.aniversario);			
			$("#txtendereco").val(dados.endereco);
			$("#txtbairro").val(dados.bairro);
			$("#txtmunicipio").val(dados.municipio);
			$("#txtuf").val(dados.uf);
			loadCombosEditarProfessor(dados);			
			
	});
		
};

function sendAjaxEditarProfessor(formulario){

	$("#modaleditarprofessor").modal("hide");
	
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {
				bootbox.alert(msg, function(e) {
					var pagina = window.location.pathname;
					if(pagina == '/professorListagem.jsp' || pagina == '/ftd/professorListagem.jsp'){
						location.reload(true);
					}else{
						$("#formeditarprofessor").trigger("reset");					
					}
				});	    	       	   
	       }       
	});		
};

function enviarAjaxEditarProfessor(formulario){

	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {
				bootbox.alert(msg, function(e) {
					$("#formeditarprofessor").trigger("reset");
					location.reload(true);
				});	    	       	   
	       }       
	});		
};

function sendAjaxDeletarProfessor(idprofessor){
	
	$("#modaleditarprofessor").modal("hide");
	
	
	
	$.get("srl",{acao:"deletarprofessor", txtidprofessor:idprofessor},
			function(msg){
			
			bootbox.alert(msg, function(e) {
				var pagina = window.location.pathname;
				if((pagina == "/professorEditar.jsp") || (pagina == "/ftd/professorEditar.jsp")){
					location.reload(true);					
				}else if(pagina == '/professorListagem.jsp' || pagina == '/ftd/professorListagem.jsp'){
					location.reload(true);
				}

			});

	});
};

function loadCombosEditarProfessor(professor){
	
	$("#cmbProfEscolas").empty();
	$("#cmbProfDisciplinas").empty();
	$("#cmbCargo").find('option').removeAttr("selected");
	$("#cmbProfNivel").find('option').removeAttr("selected");
	
	$("#cmbCargo option").each(function(i){
		  if ($(this).val() == professor.cargo){
		    $(this).attr("selected","selected");
		    return false;
		  }
	});
	
	$("#cmbCargo").selectpicker('refresh');
	
	$.get("srl",{acao:"pesquisarescola", txtdescricao:"todos"},
			function(dados){			
			var flag;
			
			$.each(dados, function(i, item){
				flag = true;
				$.each(professor.escolas, function(k, it){					
					  if (it.id == item.value){
						  $("#cmbProfEscolas").append("<option selected='selected' value='"+item.value+"'>"+item.label+"</option>");
						  flag = false;
						  return false;
					  }					
				});
				if(flag){
					$("#cmbProfEscolas").append("<option value='"+item.value+"'>"+item.label+"</option>");					
				}	

			});

			$("#cmbProfEscolas").selectpicker('refresh');

	});

	$.get("srl",{acao:"loadcombodisciplinas"},
			function(dados){
			var flag;

			$.each(dados, function(i, item){
				flag = true;
				$.each(professor.disciplinas, function(k, it){
					if(it == item){
						$("#cmbProfDisciplinas").append("<option selected='selected' value='"+item+"'>"+item+"</option>");
						flag = false;
						return false;
					}
				});
				if(flag){
					$("#cmbProfDisciplinas").append("<option value='"+item+"'>"+item+"</option>");
				}
			});
			$("#cmbProfDisciplinas").selectpicker("refresh");

	});
		
	$.each(professor.niveis, function (i, item){
		$("#cmbProfNivel option").each(function(k){
			if($(this).val() == item){
				$(this).attr("selected","selected");
				return false;
			}
		});
	});
		
	$("#cmbProfNivel").selectpicker("refresh");

};

function loadListProfessor(){
	
	$("#cmbListProfessores").empty();
	
	$.get("srl",{acao:"listartodosprofessores"},
			function(dados){
			$.each(dados, function(i, item){
				$("#cmbListProfessores").append("<option value='"+item.id+"'>"+item.nome+"</option>");
			});
			$("#cmbListProfessores").selectpicker("refresh");			
	});
}


function loadComboAnosAdocao(valor){
	
	$("#cmbAnoAdocao").addClass('working');
	
	$.get("srl",{acao:"adocaoescolaselect", txtidescola:valor},
			
			function(dados){									
					
				var anoant = "", anopos = "";
				
				var options = "";								
				
				$.each(dados, function(i, item){
					anopos = anoant;
					anoant = item.ano;
					if(anopos != anoant){
						options += "<option value='"+item.ano+"'>"+item.ano+"</option>";
					}
				});

				$("#cmbAnoAdocao").empty();
				$("#cmbAnoAdocao").append(options);
				
				$("#cmbAnoAdocao").append($("#cmbAnoAdocao option").remove().sort(function(a, b) {
				    var at = $(a).text(), bt = $(b).text();
				    return (at < bt)?1:((at > bt)?-1:0);
				}));
				
				$("#cmbAnoAdocao").selectpicker("refresh");					
				$("#cmbAnoAdocao").removeClass('working');
			});	
};



function loadComboAnosAdocaoEscola(valor){
	
	$("#cmbAnoAdocao"+valor).addClass('working');
	
	$.get("srl",{acao:"adocaoescolaselect", txtidescola:valor},
			
			function(dados){									
					
				var anoant = "", anopos = "";
				
				var options = "";								
				
				$.each(dados, function(i, item){
					anopos = anoant;
					anoant = item.ano;
					if(anopos != anoant){
						options += "<option value='"+item.ano+"'>"+item.ano+"</option>";
					}
				});

				$("#cmbAnoAdocao"+valor).empty();
				$("#cmbAnoAdocao"+valor).append(options);
				
				$("#cmbAnoAdocao"+valor).append($("#cmbAnoAdocao"+valor+" option").remove().sort(function(a, b) {
				    var at = $(a).text(), bt = $(b).text();
				    return (at < bt)?1:((at > bt)?-1:0);
				}));
				
				$("#cmbAnoAdocao"+valor).selectpicker("refresh");					
				$("#cmbAnoAdocao"+valor).removeClass('working');
			});	
};

function confirmarpesquisaadocaoclick(event){
	
	event.preventDefault();
	
	var formulario = $("#formpesqadocao").serialize();
	
	$("#modalajaxloader").modal({
  	  backdrop: 'static',
  	  keyboard: false
    });
    
	$("#modalajaxloader").modal("show");

	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function(dados) {
				$("#modalajaxloader").modal("hide");
				$("#formtabela").load('orcamento.jsp #divcontent');			
				$("#formtabela").show();
				$("#imgCart").load("orcamentoTotal.jsp #divcontent");
				$("#imgCart").show();
	       }       
    });    	

};


function enviarpesquisaadocaoclick(event, idescola){
	
	event.preventDefault();
	
	var formulario = $("#formpesqadocao"+idescola).serialize();
	
	$("#modalajaxloader").modal({
  	  backdrop: 'static',
  	  keyboard: false
    });
    
	$("#modalajaxloader").modal("show");

	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function(dados) {
				$("#modalajaxloader").modal("hide");
				location.href = "orcamentoImprimir.jsp";
	       }       
    });    	

};


function adicionarOrcamAoPedido(){
	$.get("srl",{acao:"adicionaraopedido"}, function(){
		
		$("#qtdTotalCart").text('0');		
		//location.href = "pedClienteRegistrar.jsp";
		var pagina = window.location.pathname;
		if(pagina == '/orcamentoImprimir.jsp' || pagina == '/ftd/orcamentoImprimir.jsp'){
			location.href = "pedClienteRegistrar.jsp";
		}else{
			$("#formtabela").empty();
			$("#totalroteiro").load('pedClienteTotal.jsp #divcontent');
		}				
	});	
};


function imprimirPedCliente(){
	var left  = ($(window).width()/2)-(900/2),
    top   = ($(window).height()/2)-(600/2),
    popup = window.open ("pedidoImprimir.jsp", "Pedido de cliente", "width=900, height=600, top="+top+", left="+left);
	var jqwin = $(popup);
	$(jqwin).blur(function(){
		this.close();			
	});
	popup.focus();
	//popup.onbeforeunload = function(){
		//sendAjaxDescartarPedCliente();
	//};	
};

function sendAjaxDescartarPedCliente(){
	$.get("srl",{acao:"descartarpedido"}, function(){
		location.href = 'index.jsp';
	});
};


//BUSCA PRODUTO - TEXTE REGEX
function descricaoclick2(e){
	
	var accentMap = { 
			"Á": "A",
			"À": "A",
			"Ẵ": "A",
			"Â": "A",
			"Ä": "A",
			"Ç": "C",
			"É": "E",
			"È": "E",
			"Ê": "E",
			"Ế": "E",
			"Ë": "E",
			"Í": "I",
			"Ì": "I",
			"Ó": "O",
			"Ò": "O",
			"Ô": "O",
			"Ố": "O",
			"Ú": "U",
			"Ù": "U",
			"Û": "U",
			"Ü": "U",
			"Ũ": "U"
	};
	
	var normalize = function( term ) {
      var ret = "";
      for ( var i = 0; i < term.length; i++ ) {
        ret += accentMap[ term.charAt(i) ] || term.charAt(i);
      }
      return ret;
    };
 
	var descricao = $("#txtdescricao").val();	
	
	$.get("srl",{acao:"pesquisarprd", txtdescricao:descricao})			
		.done(function(dados){
			var names = dados;
			if(e == 'clicou' || e == 'CLICOU'){				
				location.href = "tabela.jsp";
				$('#txtdescricao').val(''); $('#txtdescricao').focus();
			}else if(e.which == 13){
				descricaoclick('clicou');
			}else{
			$("input#txtdescricao").autocomplete({
			source: function( request, response ) {
		        var matcher = new RegExp( $.ui.autocomplete.escapeRegex( request.term ), "i" );
		        response( $.grep( names, function( value ) {
		          value = value.label || value.value || value;
		          return matcher.test( value ) || matcher.test( normalize( value ) );
		        }) );
		    },
			minLength: 1,
			maxShowItems: 5,
			focus: function( event, ui ) {
			 $( "#txtdescricao" ).val( ui.item.label );
			 return false;
			 },
			 
			 select: function( event, ui ) {
				 $( "#txtdescricao" ).val( ui.item.label );
				 $( "#txtdescricao-id" ).val( ui.item.value );
				 $( "#txtdescricao-description" ).html( ui.item.desc );
				 
				 $.get("srl",{acao:"pesquisarprd", txtdescricao:ui.item.value})
						.done(function(dados){
							location.href = "tabela.jsp";
							$('#txtdescricao').val(''); $('#txtdescricao').focus();
				});
				 return false;
			 	},
			 	search  : function(){$(this).addClass('working');},
			 	open    : function(){$(this).removeClass('working');}
			 }).keyup(function (e) {
			        if(e.which === 13) {
			            $(".ui-menu-item").hide();
			        }            
			 }).autocomplete( "instance" )._renderItem = function( ul, item ) {
				 return $( "<li>" )
				 .append( "<a><span style='font-weight: bold; font-size:9pt;'>"
						 + item.label + "</span><br><span style='font-size: 7pt'> Autor: "
						 + item.desc + "</span></a>" )
				 .appendTo( ul );
			};
		};
	});
	
};


//BUSCA PRODUTO - AUTOCOMPLETE
function descricaoclick(element, e){					
	
	
	var descricao = $(element).val();	
	
	if(e.which == 13){
		e.preventDefault();
		if(descricao != ''){
			$.get("srl",{acao:"pesquisarprd", txtdescricao:descricao})			
			.done(function(dados){
				location.href = "tabela.jsp";
				$(element).val(''); $(element).focus();				
			});			
		}
	}
	
	$(element).autocomplete({
		source: function (request, response){
			$.ajax({
				dataType: 'json',
				type: 'Get',
				url: 'srl?acao=pesquisarprd&txtdescricao='+descricao,
				success: function(data){
					$(element).removeClass('ui-autocomplete-loading');
					var search = descricao.toUpperCase();
					response(data);
				},
				error: function(data){
					$(element).removeClass('ui-autocomplete-loading');
				}
			});
		},
		minLength: 3,
		maxShowItems: 5,
		focus: function( event, ui ) {
		 $(element).val( ui.item.label );
		 return false;
		 },
		 
		 select: function( event, ui ) {
			 $(element).val( ui.item.label );
			 
			 $.get("srl",{acao:"pesquisarprd", txtdescricao:ui.item.value})
					.done(function(dados){
						location.href = "tabela.jsp";
						$(element).val(''); $(element).focus();
			});
			 return false;
		 	},
		 	search  : function(){$(this).addClass('working');},
		 	open    : function(){$(this).removeClass('working');}
		 }).keyup(function (e) {
		        if(e.which === 13) {
		            $(".ui-menu-item").hide();
		        }            
	 }).autocomplete( "instance" )._renderItem = function( ul, item ) {
		 return $( "<li>" )
		 .append( "<a><span style='font-weight: bold; font-size:9pt;'>"
				 + item.label + "</span><br><span style='font-size: 7pt'> Autor: "
				 + item.desc + "</span></a>" )
		 .appendTo( ul );
	};
};



//ADICIONA ITENS AO ORCAMENTO E DÁ UM REFRESH NA DIV orcamentoTotal.jsp
function additemclick(codigoitem, element){	
	
	if($(element).hasClass("blue")){
      	$(element).addClass("red").removeClass("blue");
	}

	var qtde = $('#'+codigoitem+'Qtde').val();
	
	$.get("srl",{acao:"additem", codigo:codigoitem, quantidade:qtde},
			function(dados){
			document.getElementById("qtdTotalCart").innerHTML = dados;			
	});
	 		
};


function additemclickbox(codigoitem, e){
	
	var qtde = $('#'+codigoitem+'Qtde').val();
	if(e.which == 13){
		
		if($("#btncart"+codigoitem).hasClass("blue")){
	      	$("#btncart"+codigoitem).addClass("red").removeClass("blue");
		}

		$.get("srl",{acao:"additem", codigo:codigoitem, quantidade:qtde},
				function(dados){
				document.getElementById("qtdTotalCart").innerHTML = dados;		});
	};
};


function remitemclick(codigoitem){
	$.get("srl",{acao:"remitem", codigo:codigoitem},
			function(dados){
			var pagina = window.location.pathname;
			if(pagina == '/adocaoPesquisar.jsp' || pagina == '/ftd/adocaoPesquisar.jsp'){
				$("#formtabela").load('orcamento.jsp #divcontent');			
				$("#formtabela").show();
				$("#imgCart").load("orcamentoTotal.jsp #divcontent");
				$("#imgCart").show();
			}else{
				location.href = "orcamentoImprimir.jsp";
			}
	});	
};


function remitempedidoclick(codigoitem){
	bootbox.confirm("Tem certeza? [remover item "+codigoitem+"]", function(result) {
		if(result){
			$.get("srl", {acao:"remitempedcliente", txtcodigo:codigoitem}, function(){
				location.href = 'pedClienteRegistrar.jsp';
				$("#totalpedido").load('pedClienteTotal.jsp #divcontent');
			});
		}
	});
};


//MOSTRA O ORCAMENTO FEITO
function mostrarorcamentoclick(){
	var qtde = $("#qtdTotalCart").val();
	if(qtde == 0){
		bootbox.alert("Orcamento vazio!", function() {			
		});
	}else{
		location.href = "orcamentoImprimir.jsp";
	}	
};

//MOSTRA O ROTEIRO FEITO
function mostrarroteiroclick(){
	location.href = "roteiro.jsp";	
};


function btnrelpedclick(valor){
	$.get("srl",{acao:valor, dataini:$("#dataini").val(), datafim:$("#datafim").val()},
			function(dados){
			location.href = "pedidoListagem.jsp";
	});
};


function btnrelpedcliclick(){
	$.get("srl",{acao:"listarpedcliente", dataini:$("#dataini").val(), datafim:$("#datafim").val()},
			function(dados){
		location.href = 'pedClienteListagem.jsp';
	});
};


function enviardetalhepedidoclick(valor){
		
	$.get("srl",{acao:'detalharped', idpedido:valor},
			
			function(dados){
				
				location.href = "pedidoDetalhado.jsp";		
	});
	
};


function deletarPedidoClick(idpedido){
	bootbox.confirm("Tem certeza?", function(result) {
		if(result){
			$.get("srl",{acao:"deletarpedidofornecedor", txtidpedido:idpedido},
					function(msg){
					bootbox.alert(msg, function() {});
					location.href = "pedidoListagem.jsp";
			});
		}
	});		
};


function deletarNotaFiscalClick(idnota){
	bootbox.confirm("Tem certeza?", function(result) {
		if(result){
			$.get("srl",{acao:"deletarnotafiscal", txtidnota:idnota},
					function(msg){
					bootbox.alert(msg, function() {});
					location.reload(true);
			});
		}
	});		
};


function enviarveritempedclick(idped, idprod, dtprev, qtped, qtatend, qtpend){
			
	$.get("srl",{acao:'veritempedido', idpedido:idped, codigo:idprod, previsao:dtprev, qtdped:qtped, qtdatend:qtatend, qtdpend:qtpend},
			
			function(dados){
				
				$("#modaldetalhepedido").modal('hide');
				$("#modalitempedido").modal('show');
				$(".modaldivitempedido").load('adminEditarItemPed.jsp #divcontent');				
	});	
};


function fechaForm(){
	window.close();
};


function dateMask(inputData, e)
{
	var tecla;
	
	if(document.all) // Internet Explorer
		tecla = event.keyCode;
	else //Outros Browsers
		tecla = e.which;
	if(tecla >= 47 && tecla < 58)
	{
		var data = inputData.value;
		if ((data.length == 2 || data.length == 5) && tecla != 47 )
		{
			data += '/';
			inputData.value = data;
		};
	}
	else
		if(tecla == 8 || tecla == 0) return true;
	else return false;
};

function cnpjMask (inputData, e){
	
	var tecla;
	
	if(document.all) // Internet Explorer
		tecla = event.keyCode;
	else //Outros Browsers
		tecla = e.which;
	if(tecla >= 47 && tecla < 58)
	{
		var data = inputData.value;
		if ((data.length == 2 || data.length == 6) && tecla != 47 )
		{
			data += '.';
			inputData.value = data;
		}else if((data.length == 10) && tecla != 47){
			data += '/';
			inputData.value = data;
		}else if((data.length == 15) && tecla != 47){
			data += '-';
			inputData.value = data;
		};
	}
	else
		if(tecla == 8 || tecla == 0) return true;
	else return false;	
}


function cepMask (inputData, e){
	
	var tecla;
	
	if(document.all) // Internet Explorer
		tecla = event.keyCode;
	else //Outros Browsers
		tecla = e.which;
	if(tecla >= 47 && tecla < 58)
	{
		var data = inputData.value;
		if ((data.length == 2) && tecla != 47 )
		{
			data += '.';
			inputData.value = data;
		}else if((data.length == 6) && tecla != 47){
			data += '-';
			inputData.value = data;
		};
	}
	else
		if(tecla == 8 || tecla == 0) return true;
	else return false;	
}



function verificaData(formulario){
	
	var dataini =  document.getElementById("datai");
	var datafim =  document.getElementById("dataf");
	var di, df;
	var dts = new Array;
	dts = dataini.value.split("/");
	di = dts[2]+"-"+dts[1]+"-"+dts[0];
	var dti = new Date(di);
	dts = datafim.value.split("/");
	df = dts[2]+"-"+dts[1]+"-"+dts[0];
	var dtf = new Date(df);
	
	if(dataini.value=="" || datafim.value==""){
		
		alert("Favor, preencher o campo data!!!");		
		return false;
		
	}else{
		if(dti > dtf){
			alert("Faixa de datas invalida!!!");		
			return false;			
		}else{
			formulario.submit();
		};
	}
};


function validaDat(campo,valor) {
	var date=valor;
	var ardt=new Array;
	var ExpReg=new RegExp("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/[12][0-9]{3}");
	ardt=date.split("/");
	erro=false;
	if ( date.search(ExpReg)==-1){
		erro = true;
		}
	else if (((ardt[1]==4)||(ardt[1]==6)||(ardt[1]==9)||(ardt[1]==11))&&(ardt[0]>30))
		erro = true;
	else if ( ardt[1]==2) {
		if ((ardt[0]>28)&&((ardt[2]%4)!=0))
			erro = true;
		if ((ardt[0]>29)&&((ardt[2]%4)==0))
			erro = true;
	}
	if (erro) {
		alert("" + valor + " nao e uma data valida!!!");
		campo.focus();
		campo.value = "";
		return false;
	}
	return true;
};

//Concede o desconto no orçamento
function setDesconto(t, e){
	var tecla = e.which;
	if(tecla == 13){
		var taxa = parseFloat(t.value);
		taxa /= 100;
		$.get("srl",{acao:"aplicardesconto", txttaxa:taxa},function(msg){
			if(msg == 'pedcliente'){
				location.href = 'pedClienteRegistrar.jsp';
				$("#totalpedido").load('pedClienteTotal.jsp #divcontent');
			}else{
				location.href = 'orcamentoImprimir.jsp';
			}
		});
	}
};



function clearInputField(){
	$("#txtdescricao").val('');
}


function loadComboSeries(){
	var idescola = $("#txtdescricaoescola-id").val();
	var anoadocao = $("#cmbAnoAdocao").val();
	var options = "";
	$("#cmbSerie").addClass('working');
	$.get("srl",{acao:'loadseriesadocao', txtidescola:idescola, txtano:anoadocao}, 
			function(dados){
		if(dados != ''){
			$.each(dados, function(i, item){
				options += "<option value='"+item+"'>"+item+"</option>";
			});
			
			$("#cmbSerie").empty();
			$("#cmbSerie").html(options).show();
			$("#cmbSerie").selectpicker('refresh');
			$("#cmbSerie").removeClass('working');
		};
	});	
};


function carregarComboSeries(idescola, ano){

	var options = "";

	$.get("srl",{acao:'loadseriesadocao', txtidescola:idescola, txtano:ano}, 
			function(dados){
		if(dados != ''){
			$.each(dados, function(i, item){
				options += "<option value='"+item+"'>"+item+"</option>";
			});
			
			$('#cmbSerie'+idescola).empty();
			$('#cmbSerie'+idescola).html(options).show();
			$('#cmbSerie'+idescola).selectpicker('refresh');
		};
	});	
};


function loadComboEmpresas(){
	var options = "";
	$.get("srl",{acao:'loadempresas'},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item.id+"'>"+item.razaosocial+"</option>";
		});
		
		$("#cmbEmpresas").html(options).show();
		$("#cmbEmpresas").selectmenu('refresh');
		
	});
};


function loadCombosCadastroEscola(){
	//loadComboClassficacao();
	loadComboVendedor();
	loadComboUf();
	loadComboMunicipio("todos");
	
	$("#cmbVendedor #cmbUF #cmbMunicipio").selectmenu('refresh');	
	
};

function loadCombosUfMun(){
	loadComboUf();
	loadComboMunicipio("todos");
	
	$("#cmbUF #cmbMunicipio").selectmenu('refresh');		
}

function loadComboClassficacao(){
	var options = "";
	$.get("srl",{acao:"loadclassificacao"},
			function(dados){
			$.each(dados, function(i, item){
				options += "<option value='"+item.idclassificacao+"'>"+item.classificacaoescola+"</option>";
			});
			
			$("#cmbClassificacao").html(options).show();
		
	});	
};

function loadComboVendedor(){
	
	$("#cmbVendedor").empty();
	
	$.get("srl",{acao:"loadvendedores"},
			function(dados){
		$.each(dados, function(i, item){
			$("#cmbVendedor").append("<option value='"+item.setor+"'>"+item.nome+"</option>");
		});
		
		$("#cmbVendedor").selectpicker("refresh");
		
	});		
};

function loadComboMunicipio(uf){
	
	var options = "";
	
	$.get("srl",{acao:"loadmunicipios", txtuf:uf},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item+"'>"+item+"</option>";
		});
		
		$("#cmbMunicipio").html(options).show();		
	});			
};

function loadComboUf(){
	
	var options = "";
	
	$.get("srl",{acao:"loaduf"},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item+"'>"+item+"</option>";
		});
		
		$("#cmbUF").html(options).show();
		
	});				
};


function loadCodMunicipio(uf, mun){
	$.get("srl",{acao:"loadcodmun", txtuf:uf, txtmun: mun},
			function(dados){
			$("#txtcodmun").val(dados);
	});					
};



function carregarDadosProduto(){
	
	$.get("srl",{acao:'dadosprd', txtcodigo:$("#txtcodigo").val()},
			function(produto){

			$.get("srl",{acao:'loadcombosproduto'},
					function(dados){
					loadCombosEditarProduto(produto, dados);					
			});			
						
			$('form').loadJSON(produto);
			
			var preco = number_format(produto.preco, 2, ',', '.');
			
			$("#txtpreco").val(preco);

			$("#cmbNivel #cmbFamilia #cmbSerie #cmbEditora #cmbDisciplina #cmbAtivo").selectmenu("refresh");
	});
};


function loadCombosEditarProduto(produto, dados){
			
			var optionFamilia = "";
			var optionNivel = "";
			var optionSerie = "";
			var optionEditora = "";
			var optionDisciplina = "";

			$("#cmbAtivo option").each(function(i){
				  if ($(this).val() == dados.ativo){
				    $(this).attr("selected","selected");
				  }
			});

			
			if(produto.familia == ''){
				optionFamilia += "<option value='Nenhum' selected>Nenhum</option>";
			}

				$.each(dados.familia, function(i, item){				
					if(item == produto.familia){
						optionFamilia += "<option value='"+item+"' selected>"+item+"</option>";
					}else{
						optionFamilia += "<option value='"+item+"'>"+item+"</option>";	
					}
				});

				if(produto.nivel == ''){
					optionNivel += "<option value='Nenhum' selected>Nenhum</option>";
				}
				
				$.each(dados.nivel, function(i, item){				
					if(item == produto.nivel){
						optionNivel += "<option value='"+item+"' selected>"+item+"</option>";
					}else{
						optionNivel += "<option value='"+item+"'>"+item+"</option>";	
					}
				});

				if(produto.serie == ''){
					optionSerie += "<option value='Nenhum' selected>Nenhum</option>";
				}

				$.each(dados.serie, function(i, item){				
					if(item == produto.serie){
						optionSerie += "<option value='"+item+"' selected>"+item+"</option>";
					}else{
						optionSerie += "<option value='"+item+"'>"+item+"</option>";	
					}
				});

				if(produto.editora == ''){
					optionEditora += "<option value='Nenhum' selected>Nenhum</option>";
				}
				
				$.each(dados.editora, function(i, item){				
					if(item == produto.editora){
						optionEditora += "<option value='"+item+"' selected>"+item+"</option>";
					}else{
						optionEditora += "<option value='"+item+"'>"+item+"</option>";	
					}
				});

				if(produto.disciplina == ''){
					optionDisciplina += "<option value='Nenhum' selected>Nenhum</option>";
				}
				
				$.each(dados.disciplina, function(i, item){				
					if(item == produto.disciplina){
						optionDisciplina += "<option value='"+item+"' selected>"+item+"</option>";
					}else{
						optionDisciplina += "<option value='"+item+"'>"+item+"</option>";	
					}
				});
												
			$("#cmbNivel").html(optionNivel).show();
			$("#cmbFamilia").html(optionFamilia).show();			
			$("#cmbSerie").html(optionSerie).show();
			$("#cmbEditora").html(optionEditora).show();
			$("#cmbDisciplina").html(optionDisciplina).show();				
			
	};


function loadDadosPesquisa(){
	$.get("srl",{acao:'loadcombosproduto'},
			function(dados){
			loadCombosPesquisa(dados);					
	});
	
	$("#comboFamilia #comboNivel #comboDisciplina #comboSerie").selectmenu("refresh");
};	
	
function loadCombosPesquisa(dados){

	var optionFamilia = "'<option value='familia'>Familia</option>'";
	var optionNivel = "'<option value='nivel'>Nivel</option>'";
	var optionSerie = "'<option value='serie'>Serie</option>'";
	var optionDisciplina = "'<option value='disciplina'>Disciplina</option>'";

	$.each(dados.familia, function(i, item){				
		optionFamilia += "<option value='"+item+"'>"+item+"</option>";	
	});
	
	$.each(dados.nivel, function(i, item){				
		optionNivel += "<option value='"+item+"'>"+item+"</option>";	
	});
	
	$.each(dados.disciplina, function(i, item){				
		optionDisciplina += "<option value='"+item+"'>"+item+"</option>";	
	});

	$.each(dados.serie, function(i, item){				
		optionSerie += "<option value='"+item+"'>"+item+"</option>";	
	});

	$("#comboFamilia").html(optionFamilia).show();			
	$("#comboNivel").html(optionNivel).show();
	$("#comboDisciplina").html(optionDisciplina).show();				
	$("#comboSerie").html(optionSerie).show();
	
};


function loadCombosCadastrarProduto(){
	
	$.get("srl",{acao:'loadcombosproduto'},
			function(dados){										

		var optionFamilia = "";
		var optionNivel = "";
		var optionSerie = "";
		var optionEditora = "";
		var optionDisciplina = "";
	
			optionFamilia += "<option value='Nenhum' selected>Nenhum</option>";
	
			$.each(dados.familia, function(i, item){				
					optionFamilia += "<option value='"+item+"'>"+item+"</option>";	
			});
	
			optionNivel += "<option value='Nenhum' selected>Nenhum</option>";
			
			$.each(dados.nivel, function(i, item){				
					optionNivel += "<option value='"+item+"'>"+item+"</option>";	
			});
	
			optionSerie += "<option value='Nenhum' selected>Nenhum</option>";
	
			$.each(dados.serie, function(i, item){				
					optionSerie += "<option value='"+item+"'>"+item+"</option>";	
			});
	
			optionEditora += "<option value='Nenhum' selected>Nenhum</option>";
			
			$.each(dados.editora, function(i, item){				
					optionEditora += "<option value='"+item+"'>"+item+"</option>";	
			});
	
			optionDisciplina += "<option value='Nenhum' selected>Nenhum</option>";
			
			$.each(dados.disciplina, function(i, item){				
					optionDisciplina += "<option value='"+item+"'>"+item+"</option>";	
			});
			
			
		$("#cmbNivel").html(optionNivel).show();
		$("#cmbFamilia").html(optionFamilia).show();			
		$("#cmbSerie").html(optionSerie).show();
		$("#cmbEditora").html(optionEditora).show();
		$("#cmbDisciplina").html(optionDisciplina).show();
	});
};


function loadCombosRegistrarAdocao(){
	$.get("srl",{acao:'loadcombosproduto'},
			function(dados){										

		var optionFamilia = "";
		var optionNivel = "";
		var optionEditora = "";
		//var optionDisciplina = "";
	
			$.each(dados.familia, function(i, item){				
					optionFamilia += "<option value='"+item+"'>"+item+"</option>";	
			});
	
			optionNivel += "<option value='todos' selected>Todos</option>";
			
			$.each(dados.nivel, function(i, item){				
					optionNivel += "<option value='"+item+"'>"+item+"</option>";	
			});
		
			$.each(dados.editora, function(i, item){
					if(item == 'FTD'){
						optionEditora += "<option value='"+item+"' selected='selected'>"+item+"</option>";
					}else{
						optionEditora += "<option value='"+item+"'>"+item+"</option>";	
					}
						
			});		
			
			
		$("#cmbNivel").html(optionNivel).show();
		$("#cmbFamilia").html(optionFamilia).show();			
		$("#cmbEditora").html(optionEditora).show();

	});	
};



//Exemplo de ajax
function sendAjax() {
	 
    // get inputs
    var codigos = new Object();
    codigos.title = $('#title').val();
    article.url = $('#url').val();
    article.categories = $('#categories').val().split(";");
    article.tags = $('#tags').val().split(";");
 
    $.ajax({
        url: "jsonservlet",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(article),
        contentType: 'application/json',
        mimeType: 'application/json',
 
        success: function (data) {
            $("tr:has(td)").remove();
 
            $.each(data, function (index, article) {
 
                var td_categories = $("<td/>");
                $.each(article.categories, function (i, tag) {
                    var span = $("<span class='label label-info' style='margin:4px;padding:4px' />");
                    span.text(tag);
                    td_categories.append(span);
                });
 
                var td_tags = $("<td/>");
                $.each(article.tags, function (i, tag) {
                    var span = $("<span class='label' style='margin:4px;padding:4px' />");
                    span.text(tag);
                    td_tags.append(span);
                });
 
                $("#added-articles").append($('<tr/>')
                        .append($('<td/>').html("<a href='"+article.url+"'>"+article.title+"</a>"))
                        .append(td_categories)
                        .append(td_tags)
                );
 
            }); 
        },
        error:function(data,status,er) {
            alert("error: "+data+" status: "+status+" er:"+er);
        }
    });
}


function sendAjaxUploadNotaFiscal(formulario) {
  	
	$.ajax({
       type: "POST",
       url: "UploadNotaFiscal",
       data: formulario,
       dataType:'json',
       success: function(msg) {    	       	   
			bootbox.alert(msg, function() {
				$("#formimportnotafiscal").trigger('reset');
			});
    	    
       }       
    });
 };	



function removerAcentos( newStringComAcento ) {

		var string = newStringComAcento.value;
		
		var mapaAcentosHex = {
				a : /[\xE0-\xE6]/g,
				A : /[\xC0-\xC6]/g,
				e : /[\xE8-\xEB]/g,
				E : /[\xC8-\xCB]/g,
				i : /[\xEC-\xEF]/g,
				I : /[\xCC-\xCF]/g,
				o : /[\xF2-\xF6]/g,
				O : /[\xD2-\xD6]/g,
				u : /[\xF9-\xFC]/g,
				U : /[\xD9-\xDC]/g,
				c : /\xE7/g,
				C : /\xC7/g,
				n : /\xF1/g,
				N : /\xD1/g,
		};	 
		for ( var letra in mapaAcentosHex ) {
			var expressaoRegular = mapaAcentosHex[letra];
			string = string.replace( expressaoRegular, letra );
		}
	 
		string = string.toUpperCase();
		
		return string;
}



function testar_string(stringTeste){  
	palavra = new RegExp('\^((?:[.]|[?]|[=]|[&]|[@]|[_]|[ ]|[-])|([0-9|a-zA-Z|.|_| |.|=|&|@|?|-]{0,100}))\$');  
	(!palavra.test(stringTeste.value))?stringTeste.value = substStr(stringTeste,palavra):"";  

};

function substStr(teste,Lregex){  
	var str ="";  
	var i = 0;  
	Arr = new Array();  
	result = teste.value;  
	while(result.charAt(i)){  
	    if(Lregex.test(result.charAt(i)))  
	    str+=result.charAt(i);  
	    i++;  
	};  
	return str;  
}; 


function number_format(number, decimals, dec_point, thousands_sep) {
    // http://kevin.vanzonneveld.net
    // +   original by: Jonas Raoni Soares Silva (http://www.jsfromhell.com)
    // +   improved by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
    // +     bugfix by: Michael White (http://getsprink.com)
    // +     bugfix by: Benjamin Lupton
    // +     bugfix by: Allan Jensen (http://www.winternet.no)
    // +    revised by: Jonas Raoni Soares Silva (http://www.jsfromhell.com)
    // +     bugfix by: Howard Yeend
    // +    revised by: Luke Smith (http://lucassmith.name)
    // +     bugfix by: Diogo Resende
    // +     bugfix by: Rival
    // +      input by: Kheang Hok Chin (http://www.distantia.ca/)
    // +   improved by: davook
    // +   improved by: Brett Zamir (http://brett-zamir.me)
    // +      input by: Jay Klehr
    // +   improved by: Brett Zamir (http://brett-zamir.me)
    // +      input by: Amir Habibi (http://www.residence-mixte.com/)
    // +     bugfix by: Brett Zamir (http://brett-zamir.me)
    // +   improved by: Theriault
    // +   improved by: Drew Noakes
    // *     example 1: number_format(1234.56);
    // *     returns 1: '1,235'
    // *     example 2: number_format(1234.56, 2, ',', ' ');
    // *     returns 2: '1 234,56'
    // *     example 3: number_format(1234.5678, 2, '.', '');
    // *     returns 3: '1234.57'
    // *     example 4: number_format(67, 2, ',', '.');
    // *     returns 4: '67,00'
    // *     example 5: number_format(1000);
    // *     returns 5: '1,000'
    // *     example 6: number_format(67.311, 2);
    // *     returns 6: '67.31'
    // *     example 7: number_format(1000.55, 1);
    // *     returns 7: '1,000.6'
    // *     example 8: number_format(67000, 5, ',', '.');
    // *     returns 8: '67.000,00000'
    // *     example 9: number_format(0.9, 0);
    // *     returns 9: '1'
    // *    example 10: number_format('1.20', 2);
    // *    returns 10: '1.20'
    // *    example 11: number_format('1.20', 4);
    // *    returns 11: '1.2000'
    // *    example 12: number_format('1.2000', 3);
    // *    returns 12: '1.200'
    var n = !isFinite(+number) ? 0 : +number, 
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        toFixedFix = function (n, prec) {
            // Fix for IE parseFloat(0.55).toFixed(0) = 0;
            var k = Math.pow(10, prec);
            return Math.round(n * k) / k;
        },
        s = (prec ? toFixedFix(n, prec) : Math.round(n)).toString().split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}

function fileUpload(formData, myform){			  

	$.ajax( {
		    url: 'srl',
		    type: 'POST',
		    data: formData,
		    processData: false,
		    contentType: false,
		    dataType: 'json',
		    beforeSend: function(){
		      $("#modalajaxloader").modal({
		    	  backdrop: 'static',
		    	  keyboard: false
		      });
		      
			  $("#modalajaxloader").modal("show");
		    },
		    error: function(xhr, status, error){
		    	$("#modalajaxloader").modal("hide");
				bootbox.alert(xhr.responseText, function() {}); 						    	
		    },
			success: function(data){
				
				$("#modalajaxloader").modal("hide");
				bootbox.alert(data, function() {
					myform.trigger('reset');
				});
			}
		});	
}

function loadLancamentos(ano){
		
		 $.get("srl",{acao:"loadlanctos", txtdescricao:ano});	
};

function viewProductDetail(img){
	 $.get("srl",{acao:"pesquisarprd", txtdescricao:img.alt})
		.done(function(dados){
			location.href = "tabela.jsp";
		});
};


function fileDownload(myform){

    $.fileDownload(myform.prop('action'), {
        prepareCallback: function (responseHtml, url) {
        	
            $("#modalajaxloader").modal({
            	  backdrop: 'static',
            	  keyboard: false
              });
              
              $("#modalajaxloader").modal("show");	        	
            
        },	    	
        failCallback: function (responseHtml, url) {
        	
        	$("#modalajaxloader").modal("hide");
            alert("Erro o tentar processar o arquivo!");
            
        },
        successCallback: function(url){
        	
        	$("#modalajaxloader").modal("hide");

        },
        httpMethod: "POST",
        data: myform.serialize()
    });
    
    return false;	
}

function exportaConsultaExcel(e){
	
	e.preventDefault();
	
    $.fileDownload($(this).prop('href'), {
        prepareCallback: function (responseHtml, url) {
        	
            $("#modalajaxloader").modal({
            	  backdrop: 'static',
            	  keyboard: false
              });
              
              $("#modalajaxloader").modal("show");	        	
            
        },	    	
        failCallback: function (responseHtml, url) {
        	
        	$("#modalajaxloader").modal("hide");
            alert("Erro o tentar processar o arquivo!");
            
        },
        successCallback: function(url){
        	
        	$("#modalajaxloader").modal("hide");
        	
        },
        httpMethod: "GET"
    });
    
    return false;		
}

function btnpesqavancadaclick(){
	
	var descricao = "";
	
	var fam = $("#comboFamilia option:selected").val();
	var nv = $("#comboNivel option:selected").val();
	var dsc = $("#comboDisciplina option:selected").val();
	var sr = $("#comboSerie option:selected").val();
	
	var familia = "familia="+fam;
	var nivel = "nivel="+nv;
	var disciplina = "disciplina="+dsc;
	var serie = "serie="+sr;
	
	if(fam != "familia")
		descricao += familia+';';
	
	if(nv != "nivel")
		descricao += nivel+';';
	
	if(dsc != "disciplina")
		descricao += disciplina+';';
	
	if(sr != "serie")
		descricao += serie+';';
	
	 $.get("srl",{acao:"pesquisarprd", txtdescricao:descricao})
		.done(function(dados){
			location.href = "tabela.jsp";
			$('#txtdescricao').val(''); $('#txtdescricao').focus();	
	 });

};




function abandonaRegistroAdocao(){
	$.get("srl", {acao:"abandonarregistroadocao"},
			function(){
			location.href = 'index.jsp';
	});				
};

function finalizaRegistroAdocao(formulario){

	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function() {  	       	   
	       }       
    });    	

	$("#modaldetalheadocao").modal('show');
	$(".modaldivdetalheadocao").load('adocaoSalvarItens.jsp #divcontent');
	$("#modalAdocaoLabel").text($("#txtdescricaoescola").val());
	//location.href = 'adocaoSalvarItens.jsp;'
	
};

function gravaRegistroAdocao(){
	$.get("srl", {acao:"registraradocao"},
			function(dados){
		$("#modaldetalheadocao").modal('hide');
		bootbox.alert(dados, function() {
			location.href = "index.jsp";
		});    			
	});    			
};

function loadSeriesProduto(){
	$.get("srl",{acao:"loadseriesproduto"},
			function(dados){
/*			var k = 0;
			$.each(dados, function (i, item){
				k++;
				$("#"+k+" option").each(function(j){
					if($(this).val() == item.serie){
						$(this).attr('selected', 'selected');
						$(this).selectpicker('refresh');
						return false;
					}
				});
				
			});*/			
	});
};

function refreshCombosSeries(){	
	for(var i=0; i < $("#countcmb").val(); i++){
		$("#"+i).selectpicker('refresh');		
	}
	
	bootbox.alert("ALTERE AS SERIES DE ACORDO COM A ADOCAO!!!", function() {
		$(".select").selectpicker('refresh');
	});    						
};

function sendAjaxDeletarSerieAdocao(event){
	event.preventDefault();
	var valor = $("#txtdescricaoescola-id").val();
	
	bootbox.confirm("Tem certeza?", function(result) {
			if(result){
				$.get("srl",{acao:"deletarserieadocao"},
						function(msg){
						$("#cmbAnoAdocao").empty();
						$("#formtabela").empty();
						$("#totalCart").load("orcamentoTotal.jsp");
						bootbox.alert(msg, function() {
							loadComboAnosAdocao(valor);
							$("#cmbSerie").empty();
							$("#cmbSerie").selectpicker('refresh');
						});
				});
			}
   		});	
};

function carregarUsuarios(){
	
	$("#cmbListUsuarios").empty();
	
	$.get("srl",{acao:'carregartodosusuarios'},
			function(dados){		
			$.each(dados, function(i, item){				
				if($("#txtcargousuariologado").val() != '1'){
					$("#cmbListUsuarios").append("<option 'selected' value="+item.id+">"+item.nome+"</option>");
					carregarDadosUsuario();
					return false;
				}else{
					$("#cmbListUsuarios").append('<option value='+item.id+'>'+item.nome+'</option>');
				}
			});
			$("#cmbListUsuarios").selectpicker('refresh');
	});			
};


function carregarDadosUsuario(){
	var id = $("#cmbListUsuarios").val();
	$.get("srl",{acao:'carregarusuario', txtid:id},
			function(dados){
		
		carregarCampos(dados);			
	});		
};

function carregarCampos(dados){
	
	$("#txtcargo option").each(function(){
		  if ($(this).val() == dados.cargo){
		    $(this).attr("selected","selected");
		  }
	});

	$('form').loadJSON(dados);
		
	if($("#txtcargousuariologado").val() != '1'){
		$('#txtcargo option:not(:selected)').attr('disabled', true);
		$('#setor').attr('readonly','readonly');
	}
	
	$("#txtcargo").selectpicker('refresh');
};


function myPagination(nitens){
	
	var maxItens = 10;
	
	var npage = parseInt(nitens / maxItens);
	
	if((nitens%maxItens)>0){
		npage += 1;
	}
	
	if(npage > 1){
		for(var i = maxItens+1; i < nitens+1; i++){
			$('#'+i).css('display', 'none');
		}
	}

	$('#page-selection').bootpag({
        total: npage,
        page: 1,
        maxVisible: maxItens
    }).on("page", function(event, num){
    	
    	var i = (num*maxItens);
    	if(num > 1){
			for(var j = i-maxItens; j > 0; j--){
				$('#'+j).css('display', 'none');
			}
	
	    	for(var j = i-(maxItens-1); j < (i+1); j++){
				$('#'+j).css('display', 'block');
			}
	
	    	for(var j = (i+1); j < nitens+1; j++){
				$('#'+j).css('display', 'none');
			}
    	}else{
	    	for(var j = 1; j < (maxItens+1); j++){
				$('#'+j).css('display', 'block');
			}
	    	for(var j = (maxItens+1); j < nitens+1; j++){
				$('#'+j).css('display', 'none');
			}	    	
    	}
    });
	
};


function loadYearSelection(codigo, mycmb){
	var actionselection = $(mycmb).val();
	$('#selanos'+codigo).empty();
	$.get('srl',{acao:'loadyearselection',txtaction:actionselection},
		function(dados){
		$.each(dados, function(i, item){
			$('#selanos'+codigo).append("<option value="+item+">"+item+"</option>");
		});
		$('#selanos'+codigo).selectpicker('refresh');
	});
};

function loadComboYear(){
	$.get('srl',{acao:'loadyearselection',txtaction:"consultaradocoes"},
		function(dados){
		$.each(dados, function(i, item){
			$('#cmbano').append("<option value="+item+">"+item+"</option>");
		});
		$('#cmbano').selectpicker('refresh');		
	});
};

function loadYearTermometro(){
	$('#cmbAnoAdocao').empty();
	$.get('srl',{acao:'loadyearselection',txtaction:"consultaradocoes"},
		function(dados){
		$.each(dados, function(i, item){
			$('#cmbAnoAdocao').append("<option value="+item+">"+item+"</option>");
		});
		$('#cmbAnoAdocao').selectpicker('refresh');
	});	
};

function sendAjaxAdocaoTermometro(formulario){
	//location.href = "srl?acao=loadadocaotermometro&txtano="+ano+"&txtsetor="+setor+"&txttipo="+tipo;
	fileDownload(formulario);
};

function sendAjaxGeraRelDoacaoExcel(vendedores){
	var vendors = "";
	$.each(vendedores, function(i, item){
		vendors += item + ";";
	});
	
    location.href = "srl?acao=geradoacoestoexcel&txtvendedor="+vendors;	
};

function sendAjaxMarcaItensAcertados(vendedores){
	var vendors = "";
	$.each(vendedores, function(i, item){
		vendors += item + ";";
	});
	
    $.get('srl',{acao:'marcaritensacertados',txtvendedor:vendors},
    		function(msg){
    		bootbox.alert(msg, function(){});
    });
};

function trataLinkEvent(a, event){
    event.preventDefault();

    var aurl = a.attr('href');
    
     if(aurl != 'javascript:void(0)'){
	    $("#modalajaxloader").modal({
		   	  backdrop: 'static',
		   	  keyboard: false
		     });
		     
		 $("#modalajaxloader").modal("show");
		  location.href = aurl;
		 $("#modalajaxloader").modal("hide");
		  
     }else{
    	 event.run();
     }
    return false;	
};


function listItensAtendidos(codigo, idpedido){
	
	$("#col"+codigo).empty();
	
	$.get('srl',{acao:"listaritensatendidos", txtcodigo:codigo, txtidpedido:idpedido},
			function(dados){			
			var tabela = "<form action='srl' method='post'>" +
					"<table><tr><th width='300x'><strong>Data atendimento</strong></th>" +
					"<th width='200x'><strong>Quantidade</strong></th><th width='200x'>....</th></tr>";
			var count = 0;
			$.each(dados, function(i, item){
				count++;
				tabela += "<tr><td><input type='text' value='"+item.sdate+"' name='"+codigo+"date' readonly></td><td>"
				+"<input type='number' min='0' value='"+item.qtdatendida+"' name='"+codigo+"item"+count+"' style='width: 60px'></td>" +
				"<td><input type='hidden' name='codigo' value='"+codigo+"'><input type='hidden' name='acao' value='alteraritematendido'>"+
				"<input type='hidden' name='item' value='"+count+"'>" +
				"<input type='submit' value='Enviar'></td></tr>";
			});
			tabela += "</table></form>";
			$("#divmodalitemalterar").empty();
			$("#divmodalitemalterar").append(tabela);
			$("#modalItemPedCliAlterar").modal('show');
	});
	
};


function alterarQtdeItemPedido(codigo,idpedido, quantidade){
	var tabela = "<form action='srl' method='post'>" +
	"<table><tr>" +
	"<th width='200x'><strong>Quantidade</strong></th><th width='200x'>....</th></tr>";
	tabela += "<tr><td>"
	+"<input type='number' min='1' value='"+quantidade+"' name='"+codigo+"' style='width: 60px'></td>" +
	"<td><input type='hidden' name='codigo' value='"+codigo+"'><input type='hidden' name='acao' value='alterarqtdeitempedido'>"+
	"<input type='submit' value='Enviar'></td></tr>";
	tabela += "</table></form>";
	$("#divmodalitemalterar").empty();
	$("#divmodalitemalterar").append(tabela);	
	$("#modalItemPedCliAlterar").modal('show');
}



function sendGlassListar(formulario){
    $("#modalajaxloader").modal({
	   	  backdrop: 'static',
	   	  keyboard: false
	     });
    
	 $("#modalajaxloader").modal("show");
	 
	 $.ajax({
  	       type: "POST",
  	       url: "srl",
  	       data: formulario,
  	       dataType: "html",
  	       success: function(page) {
  	    	 $("#modalajaxloader").modal("hide");
  	    	 location.href = "glassview.jsp"
  	       }       
  	 });  		
	 
};