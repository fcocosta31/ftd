/** BEGIN OF NEW JAVA SCRIPT FILE DATE: 2017-10-11 **/

/*! FUNCTION CREATED BY CHICOH 2017-10-12*/

$(document).ready(function(){			
	
	$(".datables-table").dataTable({
		"bPaginate": true,
        	"sDom":'fptip'
	});


	$(".datables-table-notas").dataTable({
		"bPaginate": true,
        	"sDom":'fptip',
		"footerCallback": function ( row, data, start, end, display ) {
		    var api = this.api(), data;
	 
		    // Remove the formatting to get integer data for summation
		    var intVal = function ( i ) {
		        return typeof i === 'string' ?
		            i.replace(/[\.,]/g, '')*1 :
		            typeof i === 'number' ?
		                i : 0;
		    };
	 
		    // Total over all pages
		    total = api
		        .column( 7, {filter: 'applied'} )
		        .data()
		        .reduce( function (a, b) {
		            return intVal(a) + intVal(b);
		        }, 0 );
	 
		    // Total over this page
		    pageTotal = api
		        .column( 7, { page: 'current'} )
		        .data()
		        .reduce( function (a, b) {
		            return intVal(a) + intVal(b);
		        }, 0 );
	 
		    // Update footer
		    $( api.column( 7 ).footer() ).html(	
		        'Total da página: '+(pageTotal/100).toLocaleString('pt-BR') +' ( Total geral: '+ (total/100).toLocaleString('pt-BR') +' )'
		    );
		}
	});	


	$(".download-loader").click(function(e){
		var form = $(this).closest('form');
		fileDownload(form);
	});

	updateTotalCart();
	
	$('select').material_select();
	
	$('#carousel-ftd').carousel({
		fullWidth: true
	});
	
	$('#carousel-lanctos').carousel({
		fullWidth: false,
		dist: 0,
		padding: 0,
		noWrap: true
	});

    $("#lanctos-carousel").owlCarousel({
		items: 7,
		autoPlay: true,
		autoPlayInterval: 1000,
		center: true,
	    navigation:false,
	    pagination: false
	});

    
    $('.datepicker').pickadate({
        monthsFull: ['Janeiro', 'Fevereiro', 'Marco', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
        monthsShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
        weekdaysFull: ['Domingo', 'Segunda', 'Terca', 'Quarta', 'Quinta', 'Sexta', 'Sabado'],
        weekdaysShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
        today: 'Hoje',
        clear: 'Limpar',
        close: 'Ok',
        labelMonthNext: 'Proximo mes',
        labelMonthPrev: 'Mes anterior',
        labelMonthSelect: 'Selecione um mes',
        labelYearSelect: 'Selecione um ano',
        selectMonths: true, 
        selectYears: 15,
        format: 'dd/mm/yyyy',
        onSet: function (ele) {
        	   if(ele.select){
        	          this.close();
           }
        }
    });
    
	$('.modal').modal();

	autoplayFTD();

	$('#btnUserLogin').click(function(e){
		var login = $("#txtlogin").val();
		var passwd = $("#txtsenha").val();
		if(login == ''){
			alert("Preencha o campo Usuario!");
		}else if(passwd == ''){
			alert("Preencha o campo Senha!");
		}else{
			var form = $(this).closest('form');
			preloadActive();
			$.ajax({
			       type: "POST",
			       url: "srl",
			       data: form.serialize(),
			       success: function(msg) {
			    	    preloadDeActive();
			    	    if(msg == 'erro'){
			    	    	document.getElementById("diverrorlogin").innerHTML = '<p  style="color: yellow; font-size: 10pt; padding: 0;">*nome de usuario ou senha incorreto!</p>';
			    	    }else{
			    	    	location.reload(true);
			    	    }	    	    
			       },
			       error: function(msg){
			    	   
			       }
			});				
		}
	});	
	
});

function downloadPriceTable(cargo){
	
	if(cargo == 1){
		
		$("#modalPriceTable").modal('open');
		
	}else{

	    $.fileDownload("srl?acao=tabeladeprecos", {
	        prepareCallback: function (responseHtml, url) {
	        	console.log("Download iniciado!....");
	        	preloadActive();
	            
	        },	    	
	        failCallback: function (responseHtml, url) {
	        	
	        	preloadDeActive();
	            alert("Erro o tentar processar o arquivo!");
	            
	        },
	        successCallback: function(url){
	        	
	        	preloadDeActive();
	        	
	        },
	        httpMethod: "GET"
	    });
		
		//$.get("srl",{acao:"tabeladeprecos"});		
	}
}

function updateTotalCart(){
	$('#qtdTotalCart').load("orcamentoTotal.jsp #divcontent");
}

$(document).click(function(event) {
	var elem = event.target;
	if ($(elem).hasClass('loadevent')){
			preloadActive();				
	}else if($(elem).attr('type') == 'submit'){
		preloadActive();
	}else if($(elem).attr('value') == 'Enviar'){
		preloadActive();
	}
});


$('#button-collapse-out').sideNav({
    menuWidth: 300, // Default is 240
    closeOnClick: true // Closes side-nav on <a> clicks, useful for Angular/Meteor
  }
);

$('.collapsible').collapsible();		



function autoplayFTD() {
    $('#carousel-ftd').carousel('next');
    setTimeout(autoplayFTD, 5000);
};	


$('#formsearchitem').on('submit', function(e){
	e.preventDefault();
    var item = $('#autocomplete-input').val();
    preloadActive();
    $.get("srl",{acao:"pesquisarprd", txtdescricao:item})			
	.done(function(dados){
		preloadDeActive();
		location.href = "tabela.jsp";
		$('#autocomplete-input').val(''); $('#autocomplete-input').focus();				
	});			
});


$('#autocomplete-input').materialize_autocomplete({
	
    limit: 20,
	   multiple: {
        enable: false
    },            
    dropdown: {
        el: '#singleDropdown',
        itemTemplate: '<li class="ac-item" data-id="{{=item.id}}" data-text=\'{{=item.text}}\'><a href="javascript:void(0)"><span style="font-size: 10pt; font-weight: bold">{{=item.text}}</span><br><span style="font-size: 7pt">{{=item.autor}}</span></a></li>'
    },
    getData: function (value, callback) {
    	
    		$(this).addClass('ajaxWorking');

    		$.ajax({
				dataType: 'json',
				type: 'Get',
				url: 'srl?acao=pesquisarprd&txtdescricao='+value,
				success: function(data){
					$(this).removeClass('ajaxWorking');
					callback(value, data);
				}
			});
    },
    onSelect: function(item){
        preloadActive();
        $.get("srl",{acao:"pesquisarprd", txtdescricao:item.id})			
    	.done(function(dados){
    		preloadDeActive();
    		location.href = "tabela.jsp";
    		$(this).val(''); $('#autocomplete-input').focus();				
    	});			    	
    }
});			



$('#txtdescricaoescola').materialize_autocomplete({
	
    limit: 20,
	   multiple: {
        enable: false
    },            
    dropdown: {
        el: '#singleDropdownSchool',
        itemTemplate: '<li class="ac-item" data-id="{{=item.id}}" data-text="{{=item.text}}"><a href="javascript:void(0)"><span style="font-size: 10pt; font-weight: bold">{{=item.text}}</span><br><span style="font-size: 7pt">{{=item.endereco}}</span></a></li>'
    },
    getData: function (value, callback) {
    	
    		$(this).addClass('ajaxWorking');

    		$.ajax({
				dataType: 'json',
				type: 'Get',
				url: 'srl?acao=pesquisarescola&txtdescricao='+value,
				success: function(data){
					$(this).removeClass('ajaxWorking');
					callback(value, data);
				}
			});
    },    
    onSelect: function(item){
    	
    	preloadActive();
    	
    	var pagina = getcurrentlocation();
    	
    	if(pagina == '/adocaoPesquisar.jsp'){
    		
	   		 $( "#txtdescricaoescola" ).val( item.text );
			 $( "#txtdescricaoescola-id" ).val( item.id );
			 $("#cmbSerie").empty().html(' ');		 
			 
			 loadComboAnosAdocao(item.id);
			 
			 preloadDeActive();
			 
    	}else if(pagina == '/adocaoSelecionar.jsp'){
    		
	   		 $( "#txtdescricaoescola" ).val( item.text );
			 $( "#txtdescricaoescola-id" ).val( item.id );
			 preloadDeActive();
    	}else if(pagina == '/escolaEditar.jsp' || pagina == '/escolaEditar1.jsp' || pagina == '/escolaEditar2.jsp'){    		    		 
    		
    		 $( "#idescola" ).val( item.id );    		 
    		 
    		 $("#formeditarescola").trigger("reset");
    		 
    		 $("#formeditarescola").removeClass('noDisplay');
    		 
    		 carregarDadosEscola(item.id);
    		 preloadDeActive();    		 
    	}else if(pagina == '/doacaoRegistrar.jsp'){
    		 $( "#idescola" ).val( item.id );
    		 loadComboProfessor(item.id);
    		 preloadDeActive();
    	}else{
    		preloadDeActive();
    		$( "#idescola" ).val( item.id );
    	}    	
    }
    
});			


$('#txtdescricaoprofessor').materialize_autocomplete({
	
    limit: 20,
	   multiple: {
        enable: false
    },            
    dropdown: {
        el: '#singleDropdownTeacher',
        itemTemplate: '<li class="ac-item" data-id="{{=item.id}}" data-text="{{=item.text}}"><a href="javascript:void(0)"><span style="font-size: 10pt; font-weight: bold">{{=item.text}}</span></a></li>'
    },
    getData: function (value, callback) {
    	
    		$(this).addClass('ajaxWorking');

    		$.ajax({
				dataType: 'json',
				type: 'Get',
				url: 'srl?acao=listartodosprofessores&txtnome='+value,
				success: function(data){
					$(this).removeClass('ajaxWorking');
					callback(value, data);
				}
			});
    },    
    onSelect: function(item){

    	$("#formeditarprofessor").trigger("reset");
		 
		$("#formeditarprofessor").removeClass('noDisplay');
    	
    	carregarDadosProfessor(item.id);
    }
    
});			




$('#txtdescricaoempresa').materialize_autocomplete({
	
    limit: 20,
	   multiple: {
        enable: false
    },            
    dropdown: {
        el: '#singleDropdownEmpresa',
        itemTemplate: '<li class="ac-item" data-id="{{=item.id}}" data-text=\'{{=item.text}}\'><a href="javascript:void(0)"><span style="font-size: 10pt; font-weight: bold">{{=item.text}}</span><br><span style="font-size: 7pt">{{=item.desc}}</span></a></li>'
    },
    getData: function (value, callback) {
    	
    		$(this).addClass('ajaxWorking');

    		$.ajax({
				dataType: 'json',
				type: 'Get',
				url: 'srl?acao=pesquisarempresa&txtdescricao='+value,
				success: function(data){
					$(this).removeClass('ajaxWorking');
					callback(value, data);
				}
			});
    },    
    onSelect: function(item){
    	
		 $( "#txtdescricaoempresa" ).val( item.text );
		 $( "#txtidempresa" ).val( item.id );
		 document.getElementById("diverrorname").innerHTML = "";
		 $( "#txtdescricaoempresa-description" ).html( "[Código do cliente: "+item.id+"]");
		 $("#btnDialogDownloadPedido").attr("href","srl?acao=downloadpedcliente&txtidempresa="+item.id+"&txtdescricaoempresa="+item.text);
		 $( "#txtdescricaoempresa" ).attr( "readonly" );
    }
    
});			


$('#txtnomecliente').materialize_autocomplete({
	
    limit: 20,
	   multiple: {
        enable: false
    },            
    dropdown: {
        el: '#singleDropdownNomeCliente',
        itemTemplate: '<li class="ac-item" data-id="{{=item.id}}" data-text=\'{{=item.text}}\'><a href="javascript:void(0)"><span style="font-size: 10pt; font-weight: bold">{{=item.text}}</span><br><span style="font-size: 7pt">{{=item.desc}}</span></a></li>'
    },
    getData: function (value, callback) {
    	
    		$(this).addClass('ajaxWorking');

    		$.ajax({
				dataType: 'json',
				type: 'Get',
				url: 'srl?acao=pesquisarempresa&txtdescricao='+value,
				success: function(data){
					$(this).removeClass('ajaxWorking');
					callback(value, data);
				}
			});
    },    
    onSelect: function(item){
    	
		 $( "#txtnomecliente" ).val( item.text );
		 $( "#txtidnomecliente" ).val( item.id );
		 document.getElementById("diverrorname").innerHTML = "";
		 $( "#txtnomecliente-description" ).html( "[Código do cliente: "+item.id+"]");
		 
    }
    
});			


function pesquisaProdutoAutocomplete(item){
    $.get("srl",{acao:"pesquisarprd", txtdescricao:item})			
	.done(function(dados){
		location.href = "tabela.jsp";
		
		$('#autocomplete-input').val(''); $('#autocomplete-input').focus();				
	});	
}


function preloadActive(){

    $("#modalajaxloader").modal({
	      dismissible: false, // Modal can be dismissed by clicking outside of the modal
	      opacity: .2, // Opacity of modal background
	      ending_top: '50%'
    });
    
	$("#modalajaxloader").modal('open');
	
};

function preloadDeActive(){

	$("#modalajaxloader").modal('close');
};


function pesquisarBonusClick(valor, event){
	if(event.which == 13){
		event.preventDefault();
	}
	var idescola = $("#idescola").val();
	var ano = valor.value;

	var $selectDropdown = $("#cmbBonus").empty().html(' ');
		
	$.get('srl',{acao:'bonuspesquisar', txtidescola:idescola,txtyear:ano},
			function(dados){
		$.each(dados, function(i, item){
			$selectDropdown.append("<option value="+item+">"+item+"</option>");
		});
		$selectDropdown.trigger('contentChanged');		
	});
};


function loadComboClientes(){
			
	var $selectDropdown = $("#cmbClientes").empty().html(' ');
	
	var options = '';

	$.get("srl",{acao:"listclientesdopedcliente"})
			.done(function(dados){
			
		$.each(dados, function(i, item){
			
			options += "<option value='"+item.codigoftd+"'>"+item.razaosocial+" ["+item.codigoftd+"]"+"</option>";
			
		});
		
		$selectDropdown.append($(options));	
		
		$selectDropdown.trigger('contentChanged');
				
	});	
};

$('select').on('contentChanged', function() {
	  // re-initialize (update)
	  $(this).material_select();
});

function sendAjaxListarPedCliente(formulario, xpedido){		
			
	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       success: function(msg) {    	       	   
    	    if(xpedido != ''){
    	    	location.href = 'pedClienteRegistrar.jsp';
    	    }else{
    	    	location.href = 'pedClienteListagem.jsp';
    	    }
       }       
    });
};







/** BEGINO OF OLD JAVA SCRIPT FILE **/

function pesquisarprodutoclick(){
	$("#modalpesquisarproduto").modal('open');
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
  				mbox.alert(msg, function() {
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
	    	   
				mbox.alert(msg, function() {
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
	    	   
				mbox.alert(msg, function() {
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

					mbox.alert(msg, function() {
					});
		    	    
		       }       
		});	  
};	

function editarprodutoclick(codigo){
		
		location.href = "produtoEditar.jsp?codigoprd="+codigo;
}

function clonarprodutoclick(codigo){
	location.href = "produtoCopiar.jsp?codigoprd="+codigo;
}

function deletepedcliente(idpedido){

	mbox.confirm("Tem certeza?", function(result) {
		if(result){
			$.get("srl",{acao:'deletepedcliente', txtidpedido:idpedido},
					function(msg){
					mbox.alert(msg, function() {
						location.href = 'pedClienteListagem.jsp';
					});						
			});			
		}
		});
	
};


function deleteescolaclick(valor, vendedor){
	var page = getcurrentlocation();
	$.get("srl",{acao:'deletarescola', idescola: valor},
			function (dados){			
			mbox.alert(dados, function() {
				if(page == '/escolaEditar1.jsp' || page == '/escolaEditar2.jsp'){
					location.reload(true);
				}else{
					location.href = 'escolas.jsp';
				}			
		});
	});
};


function excluiescolaroteiroclick(valor, vendedor){

	$.get("srl",{acao:'excluirescolaroteiro', idescola: valor},
			function (dados){
			mbox.alert(dados, function() {
				location.reload(true);
		});
	});
};


function veritempedidoclick(codigo){
	$.get("srl",{acao:'veritempedido', txtcodigo:codigo},
			function(){
		$("#modaldetalhepedido").modal('close');
		$("#modalitempedido").modal('open');
		$(".modaldivitempedido").load('adminEditarItemPed.jsp #divcontent');
	});
};


function obsitempedidoclick(idpedido, codigo){
	preloadActive();
	$.get("srl",{acao:'obsitempedido', txtidpedido:idpedido,txtcodigo:codigo},
			function(dados){
		preloadDeActive();
		$("#modalpedido").modal('close');
		$("#modalitempedido").modal('open');	
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
				mbox.alert(msg, function() {
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
				mbox.alert(msg, function() {
					location.reload(true);
				});
	    	    
	       }       
	});		
};

function sendAjaxListarRoteiros(formulario){
	
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'html',
	       success: function() {
	    	   preloadDeActive();
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
	var dataini = $("#dataini"+codigoprd).val();
	var datafim = $("#datafim"+codigoprd).val();
	
	$("#modal"+codigoprd).modal("close");
	preloadActive();    
	
	if(acaoserver == 'consultarpedidos'){
		
		$.get("srl",{acao:acaoserver, codigo:codigoprd, ano:anoconsulta},
			
			function(dados){
				preloadDeActive();
				$("#modalpedido").modal('open');
				$("#modaldivpedidos").load('pedidosDoProduto.jsp #divcontent');
				$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);
												
				
				$("#modalpedido").on("click", "#detalhenotafiscal", function(event){
				    
					var nota = $(this).attr('alt');
					var items = nota.split(";");
					var numero = items[0];
					var emissao = items[1];
					notafiscalclick(numero, emissao);
				});
				
				$("#modalpedido").on("click", "#obsitempedido", function(event){
					var items = $(this).attr('alt').split(';');
					obsitempedidoclick(items[0], items[1]);
				});
		});
		
	}else if(acaoserver == 'consultaradocoes'){

		$.get("srl",{acao:acaoserver, txtcodigo:codigoprd, txtano:anoconsulta},
				
				function(dados){
					$("#modalajaxloader").modal("close");
					$("#modalpedido").modal('open');
					$("#modaldivpedidos").load('adocoesDoProduto.jsp #divcontent');
					$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);
																		
			});

	}else if(acaoserver == 'consultardoacoes'){

		
		 $.get("srl",{acao:acaoserver, txtcodigo:codigoprd, txtano:anoconsulta},
				
				function(dados){
			 		$("#modalajaxloader").modal("close");
					$("#modalpedido").modal('open');
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

		var filial = $("#cmbEmpresas"+codigoprd).val();
			
		 $.get("srl",{acao:acaoserver, txtcodigo:codigoprd, txtano:anoconsulta, txtfilial:filial},
				
				function(dados){
			 		$("#modalajaxloader").modal("close");
					$("#modalpedido").modal('open');
					$("#modaldivpedidos").load('kardexDoProduto.jsp #divcontent');
					$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);

			});
			
	}else if(acaoserver == 'consultarpendencias'){

		
		 $.get("srl",{acao:acaoserver, txtcodigo:codigoprd, txtinicio:dataini, txtfinal:datafim},
				
				function(dados){
			 		$("#modalajaxloader").modal("close");
					$("#modalpedido").modal('open');
					$("#modaldivpedidos").load('pendenciasDoProduto.jsp #divcontent');
					$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);																		
			});
			
	}else if(acaoserver == 'consultarnotas'){
		
		var filial = $("#cmbEmpresas"+codigoprd).val();
		
		$.get("srl",{acao:acaoserver, codigo:codigoprd, ano:anoconsulta, txtfilial:filial},
			
			function(dados){
				preloadDeActive();
				$("#modalpedido").modal('open');
				$("#modaldivpedidos").load('notasDoProduto.jsp #divcontent');
				$("#modalPedidoLabel").text(codigoprd+" - "+descricaoprd);

				$("#modalpedido").on("click", "#detalhenotafiscal", function(event){		    
					var nota = $(this).attr('alt');
					var items = nota.split(";");
					var numero = items[0];
					var emissao = items[1];
					notafiscalclick(numero, emissao);
				});

			});
		
	}
	
};


//FAZ UMA REQUISIÇÃO AO SERVLET PARA PEGAR O DETALHE DA ADOCAO E CARREGA A MODALDETALHENOTA
function detalheadocaoclick(idescola, serie, ano){	
	preloadActive();
	$.get("srl",{acao:"detalharadocao", txtidescola:idescola, txtserie:serie, txtano:ano, txttabela:"atual"},
			function(dados){
				preloadDeActive();				
				location.href = "orcamentoImprimir.jsp";
		});		
};


//FAZ UMA REQUISIÇÃO AO SERVLET PARA PEGAR O DETALHE DO ORCAMENTO E CARREGA MODALDETALHENOTA
function detalhekardexclick(filial, idorcam, idcliente, nomecli){
	preloadActive();
	$.get("srl",{acao:"detalharkardex", txtfilial:filial, txtidorcam:idorcam, txtidcliente:idcliente, txtnome:nomecli},
			function(dados){
				preloadDeActive();
				$("#modaldetalhenota").modal('open');
				$(".modaldivdetalhenota").load('orcamentoImprimir.jsp #divcontent');				
		});		
};

function detalhenotaclick(filial, idorcam, idcliente, nomecli){
	preloadActive();
	$.get("srl",{acao:"detalharkardex", txtfilial:filial, txtidorcam:idorcam, txtidcliente:idcliente, txtnome:nomecli},
			function(dados){
				preloadDeActive();
				$("#modaldetalhenota").modal('open');
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
function notafiscalclick(nota, emissao){

	preloadActive();
	
	$.get("srl",{acao:"detalharnotafiscal", txtidnota:nota, txtemissao:emissao},
		function(dados){
			preloadDeActive();
			$("#modalpedido").modal('close');
			$(".modaldivdetalhenota").load('notafiscalDetalhar.jsp #divcontent');
			$("#modaldetalhenota").modal('open');
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
		}else if(valor == 'systemparams'){
			location.href = "systemparams.jsp";
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

	var $selectDropdown = $("#cmbMunicipio").empty().html(' ');
	
	var options = "<option value='todos'>TODOS</option>";

	$.get("srl",{acao:"listarmunicipios", txtsetor: setor, txtdependencia: dependencia})
			.done(function(dados){
			
		$.each(dados, function(i, item){
			
			options += "<option value='"+item+"'>"+item+"</option>";
			
		});
		
		$selectDropdown.append($(options));	
		
		$selectDropdown.trigger('contentChanged');
				
	});
	
	listarBairros(setor, dependencia, $selectDropdown.val());
};

function listarBairros(setor, dependencia, municipio){

	var $selectDropdown = $("#cmbBairro").empty().html(' ');
	
	var options = "<option selected value='todos'>TODOS</option>";

	$.get("srl",{acao:"listarbairros", txtsetor:setor, txtdependencia: dependencia, txtmunicipio: municipio})
			.done(function(dados){
			
		$.each(dados, function(i, item){
			
			options += "<option value='"+item+"'>"+item+"</option>";
			
		});
		
		$selectDropdown.append($(options));	
		
		$selectDropdown.trigger('contentChanged');
				
	});	
};


function sendAjaxResumoRoteiro(formulario){
	$.ajax( {
	    url: 'srl',
	    type: 'POST',
	    data: formulario,
	    beforeSend: function(){
	    	preloadActive();
	    },
	    error: function(xhr, status, error){
	    	preloadDeActive(); 						    	
	    },
		success: function(){
			
			preloadDeActive();
			
			location.href = "roteiroResumo.jsp";
		}
	});			
};


function sendAjaxListarEscolas(formulario){
	$.ajax( {
	    url: 'srl',
	    type: 'POST',
	    data: formulario,
	    error: function(xhr, status, error){
	    	preloadDeActive();
	    },
		success: function(){
			location.href = "escolas.jsp";
		}
	});		
};


function listagemescolas(){

	$.ajax( {
	    url: 'srl?acao=listarescolas',
	    type: 'GET',
	    beforeSend: function(){
	    	preloadActive();
	    },
	    error: function(xhr, status, error){
	    	preloadDeActive(); 						    	
	    },
		success: function(){
			
			preloadDeActive();

	      $("#modalajaxloader").modal({
	    	  backdrop: 'static',
	    	  keyboard: false
	      });
	      
		  $("#modalajaxloader").modal('open');
	    },
	    error: function(xhr, status, error){
	    	$("#modalajaxloader").modal("close"); 						    	
	    },
		success: function(){
			
			$("#modalajaxloader").modal("close");

			location.href = "escolas.jsp";
		}
	});	
	
};


function enviarEmailPedidoOrcam(nome, idftd, formapgto, transportadora, observ, emailcli, guardapend){
	preloadActive();

	$.get('srl',{acao:"sendmailpedidoorcam", txtnome:nome, txtidempresa:idftd,
		txtfonecontato:formapgto, txttransportadora:transportadora, 
		txtobs: observ, emailcliente:emailcli, guardapend:guardapend}, function(msg){
		preloadDeActive();
 	   mbox.alert(msg, function(e) {
 		  sendAjaxDescartarOrcamento();
	   });    	   		
	});	
};

function enviarEmailOrcamento(nome, fone, email){
    
	preloadActive();
	
	$.get('srl',{acao:"sendmailorcam", txtemail:email, txtnome:nome, txtfone:fone}, function(msg){
	   preloadDeActive();
 	   mbox.alert(msg, function(e) {
 		  sendAjaxDescartarOrcamento();
	   });    	   		
	});
};


function sendMailPedCliente(){
	preloadActive();
	$.get('srl',{acao:"sendmailpedcliente"}, function(msg){
			preloadDeActive();
	});
};
	
function sendMailPedCliente(){
	$("#modalajaxloader").modal({
	  	  backdrop: 'static',
	  	  keyboard: false
	    });
	    
	$("#modalajaxloader").modal('open');
	
	$.get('srl',{acao:"sendmailpedcliente"}, function(msg){
		   $("#modalajaxloader").modal("close");

	 	   		mbox.alert(msg, function(e) {
		   });		
	});
};

function enviarEmailNotaFiscal(formulario){
	preloadActive();	
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function(msg){
	    	   preloadDeActive();
	     	   mbox.alert(msg, function(e) {
	     		  sendAjaxDescartarOrcamento();
	    	   });	    	   
	       }
	});	
		
};


function menupesquisaradocaoclick(){
	location.href = "adocaoPesquisar.jsp";
};


function loadComboProfessor(valor){
	var options = "";
	$.get("srl",{acao:"loadcomboprofessor", txtidescola:valor},
			function(dados){									
				$.each(dados, function(i, item){
					options += "<option value='"+item.id+"'>"+item.nome+"</option>";
				});
				
				$("#cmbProfessor").empty().html(' ');
				$("#cmbProfessor").append(options);
				$("#cmbProfessor").trigger("contentChanged");				
	});	
};



function sendAjaxEditarEscola(formulario, vendedor) {
  	
	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {

    	   mbox.alert(msg, function(e) {
    		   	var pagina = getcurrentlocation();
	 			if(pagina == '/escolaEditar2.jsp'){
	 				location.reload(true);
				}else if(pagina == '/escolaEditar1.jsp'){
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
	    	   
	    	   if(msg === 'Erro'){
	    		   
	    		   mbox.confirm('Produto ja cadastrado! Deseja ativar?', function(yes) {
	    			    if (yes) {
		    				  sendAjaxAtivarProduto($("#txtcodigo").val()); 		    				 
	    			    } else {
	    			    	  $('#formcadastrarproduto').trigger("reset");
	    			    }
	    			});
	    		   

	    	   }else if(msg === 'Ok'){
					mbox.alert('Produto salvo com sucesso!', function() {
						$('#formcadastrarproduto').trigger("reset");
					});	    		   
	    	   }else{
					mbox.alert(msg, function() {
						
					});	    		   	    		   
	    	   }
	    	    
	       }       
	    });
		
};	

function sendAjaxAtivarProduto(codigo){
	$.get("srl",{acao:"ativarprd", txtcodigo:codigo}, function(msg){
		mbox.alert(msg, function() {
			location.href = 'tabela.jsp';
		});	  
	});
};


function sendAjaxClonarProduto(formulario) {
  	
	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {    	       	   
				
		    	   if(msg === 'Erro'){
		    		   
		    		   mbox.confirm('Produto ja cadastrado! Deseja ativar?', function(yes) {
		    			    if (yes) {
			    				  sendAjaxAtivarProduto($("#txtcodigo1").val()); 		    				 
		    			    } else {
		    			    	  $('#formcadastrarproduto').trigger("reset");
		    			    }
		    			});
		    		   

		    	   }else{
						mbox.alert('Produto salvo com sucesso!', function() {
							$('#formcadastrarproduto').trigger("reset");
						});	    		   
		    	   }
    	    
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
				mbox.alert(msg, function() {
					preloadDeActive();	
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
				mbox.alert(msg, function() {
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
					mbox.alert("Cliente salvo!", function() {
						$('#formcadastrocliente').trigger("reset");
					});
	    	   }else{
					mbox.alert("Erro ao salvar!", function() {
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
			mbox.alert(msg, function() {
				preloadDeActive();
			});
    	    
       }       
    });
};	


function sendAjaxListarNota(formulario) {
  	
    preloadActive();
    
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function() {
	    	   
	    	   preloadDeActive();
	    	   
	    	   location.href = "notafiscalListagem.jsp";
	       }       
   });

};	


function roteiroaddescolaclick(id, element){								
		
		$.ajax({
	       type: "GET",
	       url: "srl?acao=roteiroaddescola&idescola="+id,
	       dataType:'json',
	       success: function(msg) {
	    	   	if(msg == -1){
	    	   		roteiroaddescolaclick(id, element);
	    	   	}else if(msg == 0){
	    	   		mbox.alert('ESCOLA JÁ EXISTE NESTE ROTEIRO! ADICIONE OUTRA!',
	    	   				function(){});
	    	   	}else{
			   		if($(element).hasClass("blue")){
				      	$(element).addClass("black").removeClass("blue");
					}
			   		
		    	   $("#totalroteiro").load('roteiroTotal.jsp #divcontent');	    	   		
	    	   	}	    	       	   
	       }       
	    });
		
};
	 
	 
	 
function detalheescolaclick(id, idusuario){
	var pagina = getcurrentlocation();
	if(pagina == '/roteiro.jsp'){
		location.href= 'escolaEditar1.jsp?idescola='+id+"&idusuario="+idusuario;
	}else{
		location.href= 'escolaEditar.jsp?idescola='+id+"&idusuario="+idusuario;
	}
	
	
};

function carregarDadosEscola(id){
	
	$.get("srl",{acao:'detalheescola', idescola:id},
			function(dados){	
			
			$("#idescola").val(dados.id);
			
			dependenciaSelect(dados.dependencia);
			
			classificacaoselect(dados.classificacao);
			
			vendedorselect(dados.setor);
			
			$('form').loadJSON(dados);
			$("#cep").val(dados.cep);
			$("#cep").mask("99.999-999");
			$("#cnpj").val(dados.cnpj);
			$("#cnpj").mask("99.999.999/9999-99");												
	});		

};

function dependenciaSelect(dependencia){
	
	var $selectDropdown = $("#cmbDependencia").empty().html(' ');
	
	var aDep = {"privada":"Privada","publica":"Pública"};

	$.each(aDep, function(key, value){
		if(dependencia == key){
			$selectDropdown.append("<option selected value='"+key+"'>"+value+"</option>");
		}else{
		    $selectDropdown.append("<option value='"+key+"'>"+value+"</option>");
		}		
	});
	$selectDropdown.trigger("contentChanged");
	
};

function numeroalunosclick(id){

	$.get("srl",{acao:'numeroalunosescola', txtidescola:id},
			function(dados){
		$("#modalAlunos").modal('open');
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
		$("#modalObservacao").modal('open');			
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
			mbox.alert(msg, function() {
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
			mbox.alert(msg, function() {
				var pagina = getcurrentlocation();
				if(pagina == "/escolas.jsp"){
					location.reload(true);
				}
			});
    	       	   
       }       
    });
	
};	

function updateItemValue(element){
	var item = $(element).attr('id');
	var value = parseInt($(element).val());
	var valuepedido = parseInt($("#"+item+"pdo").val());
	if(value > valuepedido){
		$('#trava-record').val('1');
		alert("Quantidade atendida maior que pedida!")
	}else{
		$('#trava-record').val('0');
		$.get("srl",{acao:"updateitemvalue", codigo:item, valor:value},
				function(msg){
				location.reload(true);
		});		
	}
}


function sendAjaxSalvarPedCliente(formulario) {
	var trava = $('#trava-record').val();
	if(trava == 1){
		mbox.alert("Quantidade atendida não pode ser maior que quantidade pedida!", function() {});		
	}else{
		preloadActive();
		
		$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {
	    	   
	    	     preloadDeActive();
	    	     
	    	     mbox.confirm('Deseja imprimir?', function(yes) {
	    	    	    if (yes) {
	    	    	    	imprimirPedCliente();    	    	    	
	    	    	    }else{
	    	    	    	sendAjaxDescartarPedCliente();
	    	    	    }
	    	    });
	    	     
	       }       
	    });		
	}
};	


function sendAjaxDownloadCsvPedCliente(formulario){
	fileDownload(formulario);
}


function sendAjaxAtualizarPedCliente(formulario) {
	var trava = $('#trava-record').val();
	if(trava == 1){
		mbox.alert("Quantidade atendida não pode ser maior que quantidade pedida!", function() {});		
	}else{
		preloadActive();
		
		$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {
	    	   
	    	    preloadDeActive();
	    	   
	    	   	var pagina = getcurrentlocation();
	    	   	if(pagina == '/pedClienteRegistrar.jsp'){
	    			location.href = 'pedClienteRegistrar.jsp';    	   		
	    	   	}else{
	    	   		location.href = 'pedClientePendente.jsp';
	    	   	}
				$("#totalpedido").load('pedClienteTotal.jsp #divcontent');
	       }       
	    });
	}
};	


function sendAjaxAlterarNomeCliente(idempresa){

	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: {_idempresa:idempresa,
	    	      acao:'alterarnomecliente'
	    	   	 },
	       dataType:'json',
	       success: function(msg) {
				mbox.alert(msg, function() {
					location.reload(true);
				});
	    	       	   
	       }       
	    });

};

function btnsalvarroteiroclick(){
	var formulario = $("#formsalvarroteiro").serialize();
	sendAjaxSalvarRoteiro(formulario);
};


function sendAjaxSalvarRoteiro(formulario) {

	$.ajax({
       type: "POST",
       url: "srl",
       data: formulario,
       dataType:'json',
       success: function(msg) {
			mbox.alert(msg, function() {
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
	$('#modalRoteiro').modal('open');
	$('#modalsalvarroteiro').load('roteiroSalvar.jsp #divcontent');
};

function refreshSelectMenu(item){
	$('#'+item).selectpicker('refresh');
};

function cancelarroteiroclick(){

	$.get("srl",{acao:'cancelarroteiro'},
			function(msg){
			mbox.alert(msg, function() {
				location.href = "index.jsp";
			});
	});	

};

function vendedorselect(){
    
	var $selectDropdown = $("#cmbVendedor").empty().html(' ');
	
	var options = '';

	$.get("srl",{acao:"loadvendedores"})
			.done(function(dados){
			
		$.each(dados, function(i, item){
			
			options += "<option value='"+item.setor+"'>"+item.nome+"</option>";
			
		});
		
		$selectDropdown.append($(options));	
		
		$selectDropdown.trigger('contentChanged');
				
	});	    
};



function vendedorselect(setor){
	
	var $selectDropdown = $("#cmbVendedor").empty().html(' ');
	
	var options = '';

	$.get("srl",{acao:"loadvendedores"})
			.done(function(dados){
			
		$.each(dados, function(i, item){
			if(setor == item.setor)
			     options += "<option selected value='"+item.setor+"'>"+item.nome+"</option>";
            else
			     options += "<option value='"+item.setor+"'>"+item.nome+"</option>";
		});
		
		$selectDropdown.append($(options));	
		
		$selectDropdown.trigger('contentChanged');
				
	});	
};


function classificacaoselect(classificacao){
	
	var $selectDropdown = $("#cmbClassificacao").empty().html(' ');
	
	$.get("srl",{acao:"loadclassificacao"},
			
			function(dados){

				$.each(dados, function(i, item){
					if(classificacao == item.idclassificacao)
						$selectDropdown.append("<option selected value='"+item.idclassificacao+"'>"+item.classificacaoescola+"</option>");
					else
						$selectDropdown.append("<option value='"+item.idclassificacao+"'>"+item.classificacaoescola+"</option>");
				});
				$selectDropdown.trigger("contentChanged");					
		
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

	preloadActive();
	
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function() {
	    	   preloadDeActive();

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
	mbox.confirm("Tem certeza? [remover doacao "+iddoacao+"]", function(result) {
		if(result){	
			$.get("srl",{acao:"deletardoacao", txtiddoacao:iddoacao},
					function(msg){
						mbox.alert(msg, function(e) {
							var pagina = getcurrentlocation();
							if(pagina == '/doacaoListagem.jsp')
								location.href = 'doacaoListagem.jsp';
						});	    	       	   
			});
		}
	});
};

function listarEscolasPorDependencia(dependencia){
	
	$.get("srl",{acao:"pesquisarescola", txtdescricao:"todos", txtdependencia: dependencia},
			function(dados){
			$("#cmbProfEscolas").empty().html(' ');
			$.each(dados, function(i, item){
				$("#cmbProfEscolas").append("<option value='"+item.id+"'>"+item.text+"</option>");
			});

			$("#cmbProfEscolas").trigger('contentChanged');

	});	
}

function loadCombosCadastroProfessor(){
		
	$.get("srl",{acao:"pesquisarescola", txtdescricao:"todos", txtdependencia: "privada"},
			function(dados){
			$("#cmbProfEscolas").empty().html(' ');
			$.each(dados, function(i, item){
				$("#cmbProfEscolas").append("<option value='"+item.id+"'>"+item.text+"</option>");
			});

			$("#cmbProfEscolas").trigger('contentChanged');

		});
	
	$.get("srl",{acao:"loadcombodisciplinas"},
			function(dados){
			$("#cmbProfDisciplinas").empty().html(' ');
			$.each(dados, function(i, item){			
				$("#cmbProfDisciplinas").append("<option value='"+item+"'>"+item+"</option>");
			});
			$("#cmbProfDisciplinas").trigger('contentChanged');
			
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
	    	   console.log(msg);
				mbox.alert(msg, function(e) {
					location.reload(true);
					$(window).scrollTop(0);
				});	    	       	   
	       }       
	    });	
};


function btnListarProfessoresClick(idescola){
	$.get("srl",{acao:"listarprofessores", txtidescola:idescola},
			function (dados){
			var pagina = getcurrentlocation();
			if(pagina == "/escolaEditar2.jsp"){
				location.href = "professorListagem.jsp?escola="+$("#nome").val();
			}else{
				$("#modalprofessores").modal('open');
				$("#modalProfessorLabel").text("Listagem de professores");
				$(".modaldivdetalheprofessores").load("professorListagem.jsp #divcontent");
			}
	});
};

function carregarDadosProfessor(idprofessor){
	
	$.get("srl",{acao:"dadosprofessor",txtidprofessor:idprofessor},
			function(dados){
			
				
				$("#modaleditarprofessor").modal('open');			
				
				$("#txtnome").val(dados.nome);
				$("#txtemail").val(dados.email);
				$("#txttelefone").val(dados.telefone);
				$("#txtidprofessor").val(dados.id);
				$("#txtendereco").val(dados.endereco);
				$("#txtbairro").val(dados.bairro);
				$("#txtmunicipio").val(dados.municipio);
				$("#txtuf").val(dados.uf);
				$("#txtdescricaoescola").val(dados.escola.nome);
				$("#idescola").val(dados.escola.id);
				
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

	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       dataType:'json',
	       success: function(msg) {
				mbox.alert(msg, function(e) {
					var pagina = getcurrentlocation();
					if(pagina == '/professorListagem.jsp'){
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
				mbox.alert(msg, function(e) {
					$("#formeditarprofessor").trigger("reset");
					location.reload(true);
				});	    	       	   
	       }       
	});		
};

function sendAjaxDeletarProfessor(idprofessor){
	
	$("#modaleditarprofessor").modal("close");
	
	
	
	$.get("srl",{acao:"deletarprofessor", txtidprofessor:idprofessor},
			function(msg){
			
			mbox.alert(msg, function(e) {
				var pagina = getcurrentlocation();
				if(pagina == "/professorEditar.jsp"){
					location.reload(true);					
				}else if(pagina == '/professorListagem.jsp'){
					location.reload(true);
				}

			});

	});
};

function loadCombosEditarProfessor(professor){
	
	$("#cmbProfDisciplinas").empty().html(' ');
	$("#cmbCargo").find('option').removeAttr("selected");
	$("#cmbProfNivel").find('option').removeAttr("selected");
	
	$("#cmbCargo option").each(function(i){
		  if ($(this).val() == professor.cargo){
		    $(this).attr("selected","selected");
		    return false;
		  }
	});
	
	$("#cmbCargo").trigger('contentChanged');
	
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
			$("#cmbProfDisciplinas").trigger("contentChanged");

	});
		
	$.each(professor.niveis, function (i, item){
		$("#cmbProfNivel option").each(function(k){
			if($(this).val() == item){
				$(this).attr("selected","selected");
				return false;
			}
		});
	});
		
	$("#cmbProfNivel").trigger("contentChanged");

};

function loadListProfessor(){
	
	$("#cmbListProfessores").empty().html(' ');
	
	$.get("srl",{acao:"listartodosprofessores"},
			function(dados){
			$.each(dados, function(i, item){
				$("#cmbListProfessores").append("<option value='"+item.id+"'>"+item.nome+"</option>");
			});
			$("#cmbListProfessores").trigger("contentChanged");			
	});
};


function loadComboAnosAdocao(valor){

	var $selectDropdown = $("#cmbAnoAdocao").empty().html(' ');
	
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
				
				$selectDropdown.append(options);				
				$("#cmbAnoAdocao").append($("#cmbAnoAdocao option").remove().sort(function(a, b) {
				    var at = $(a).text(), bt = $(b).text();
				    return (at > bt)?1:((at < bt)?-1:0);
				}));
				$selectDropdown.trigger('contentChanged');				
				loadComboSeries();								
			});	
};



function loadComboAnosAdocaoEscola(valor){
	
	var $selectDropdown = $("#cmbAnoAdocao"+valor).empty().html(' ');
	
	$selectDropdown.addClass('working');
	
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

				$selectDropdown.append(options);
				$selectDropdown.trigger('contentChanged');
				
				$selectDropdown.append($("#cmbAnoAdocao"+valor+" option").remove().sort(function(a, b) {
				    var at = $(a).text(), bt = $(b).text();
				    return (at < bt)?1:((at > bt)?-1:0);
				}));
				$selectDropdown.trigger('contentChanged');
				$selectDropdown.removeClass('working');
			});	
};


$("#formpesqadocao").on('submit', function(event){
	event.preventDefault();
	var formulario = $(this).serialize();
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function(dados) {
	    	    preloadDeActive();
				$("#formtabela").load('orcamentoImprimir.jsp #divcontent');			
				$("#formtabela").show();
				$('#qtdTotalCart').load("orcamentoTotal.jsp #divcontent");
				//$('#qtdTotalCart').html(dados);	
	       }       
	});    		
});


function enviarpesquisaadocaoclick(event, idescola){
	
	event.preventDefault();
	
	var formulario = $("#formpesqadocao"+idescola).serialize();
	
	preloadActive();
	$.ajax({
	       type: "POST",
	       url: "srl",
	       data: formulario,
	       success: function(dados) {
				preloadDeActive();
				location.href = "orcamentoImprimir.jsp";
	       }       
    });    	

};


function adicionarOrcamAoPedido(){
	$.get("srl",{acao:"adicionaraopedido"}, function(msg){		
		var pagina = getcurrentlocation();
		if(msg == '0'){
			mbox.alert("Ocorreu um erro ao tentar adicionar o(s) iten(s)!!", function() {});
		}else{
			if(pagina == '/orcamentoImprimir.jsp'){
				location.href = "pedClienteRegistrar.jsp";
			}else{
				$("#totalroteiro").load('pedClienteTotal.jsp #divcontent');
				$("#formtabela").empty();
				$('#qtdTotalCart').load("orcamentoTotal.jsp #divcontent");
			}			
		}				
		
	});	
};

function getcurrentlocation(){
	var pagina = window.location.pathname;
	var n = pagina.lastIndexOf("/");
	var p = pagina.length;
	return pagina.substring(n,p);
};

function imprimirPedCliente(){
	var left  = ($(window).width()/2)-(900/2),
    top   = ($(window).height()/2)-(600/2),
    popup = window.open ("pedidoImprimir.jsp", "Pedido de cliente", "width=900, height=600, top="+top+", left="+left);
	var jqwin = $(popup);
	$(jqwin).blur(function(){
		this.close();
		location.href = "pedClienteRegistrar.jsp";	
	});
	popup.focus();
	location.href = "pedClienteRegistrar.jsp";
};


function sendAjaxDescartarPedCliente(){
	$.get("srl",{acao:"descartarpedido"}, function(){
		location.href = 'index.jsp';
	});
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
			var pagina = getcurrentlocation();
			if(pagina == '/adocaoPesquisar.jsp'){
				$("#formtabela").load('orcamentoImprimir.jsp #divcontent');			
				$("#formtabela").show();
				$('#qtdTotalCart').load("orcamentoTotal.jsp #divcontent");
				$("#qtdTotalCart").show();
			}else{
				location.href = "orcamentoImprimir.jsp";
			}
	});	
};


function remitempedidoclick(codigoitem){
	mbox.confirm("Tem certeza? [remover item "+codigoitem+"]", function(result) {
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
		mbox.alert("Orcamento vazio!", function() {			
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
	mbox.confirm("Tem certeza?", function(result) {
		if(result){
			$.get("srl",{acao:"deletarpedidofornecedor", txtidpedido:idpedido},
					function(msg){
					mbox.alert(msg, function() {});
					location.href = "pedidoListagem.jsp";
			});
		}
	});		
};


function deletarNotaFiscalClick(idnota){
	mbox.confirm("Tem certeza?", function(result) {
		if(result){
			$.get("srl",{acao:"deletarnotafiscal", txtidnota:idnota},
					function(msg){
					mbox.alert(msg, function() {});
					location.reload(true);
			});
		}
	});		
};


function enviarveritempedclick(idped, idprod, dtprev, qtped, qtatend, qtpend){
			
	$.get("srl",{acao:'veritempedido', idpedido:idped, codigo:idprod, previsao:dtprev, qtdped:qtped, qtdatend:qtatend, qtdpend:qtpend},
			
			function(dados){
				
				$("#modaldetalhepedido").modal('close');
				$("#modalitempedido").modal('open');
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
};


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
};



function verificaData(formulario){
	
	var dataini =  document.getElementById("dataini");
	var datafim =  document.getElementById("datafim");
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
};


function loadComboSeries(){

	var idescola = $("#txtdescricaoescola-id").val();
	var anoadocao = $("#cmbAnoAdocao").val();
	var options = "";
	
	var $selectDropdown = $("#cmbSerie").empty().html(' ');
	
	$.get("srl",{acao:'loadseriesadocao', txtidescola:idescola, txtano:anoadocao}, 
			function(dados){
		if(dados != ''){
			var i = 0;
			$.each(dados, function(i, item){
				if(i == 0){
					options += "<option value='"+item+"' selected>"+item+"</option>";
				}else{
					options += "<option value='"+item+"'>"+item+"</option>";
				}
				i++;
			});
			
			$selectDropdown.append(options);
			$selectDropdown.trigger('contentChanged');
		};
	});	
};


function carregarComboSeries(idescola, ano){

	var options = "";
	
	var $selectDropdown = $("#cmbSerie"+idescola).empty().html(' ');
	
	$.get("srl",{acao:'loadseriesadocao', txtidescola:idescola, txtano:ano}, 
			function(dados){
		if(dados != ''){
			var i = 0;
			$.each(dados, function(i, item){
				if(i == 0){
					options += "<option value='"+item+"' selected>"+item+"</option>";
				}else{
					options += "<option value='"+item+"'>"+item+"</option>";
				}
				i++;
			});
			
			$selectDropdown.append(options);
			$selectDropdown.trigger('contentChanged');
		};
	});	
};


function loadComboEmpresas(){
	var options = "";
	
	var $selectDropdown = $("#cmbEmpresas").empty().html(' ');
	
	$.get("srl",{acao:'loadempresas'},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item.id+"'>"+item.razaosocial+"</option>";
		});
		
		$selectDropdown.append($(options));
		$selectDropdown.trigger('contentChanged');
		
	});
};

function loadComboFiliaisFTD(element){
	var options = "";
	
	var $selectDropdown = element.empty().html(' ');
	
	$.get("srl",{acao:'loadempresas'},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item.filialftd+"'>"+item.nomereduz+"</option>";
		});
		
		$selectDropdown.append($(options));
		$selectDropdown.trigger('contentChanged');
		
	});
};


function loadComboEmpresasFTD(){
	var options = "";
	
	var $selectDropdown = $("#cmbEmpresas").empty().html(' ');
	
	$.get("srl",{acao:'loadempresas'},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item.filialftd+"'>"+item.nomereduz+"</option>";
		});
		
		$selectDropdown.append($(options));
		$selectDropdown.trigger('contentChanged');
		
	});
};


function loadComboFornecedor(){
	
	var options = "";
	
	var $selectDropdown = $("#cmbFornecedor").empty().html(' ');
	
	$.get("srl",{acao:'loadcombofornecedor'},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item.cnpj+"'>"+item.nome+" ["+item.uf+"] ["+item.cnpj+"]</option>";
		});
		
		$selectDropdown.append($(options));
		$selectDropdown.trigger('contentChanged');
		
	});	
};



function loadCombosCadastroEscola(){
	loadComboVendedor();
	loadComboUf();
	loadComboMunicipio("todos");	
};

function loadCombosUfMun(){
	loadComboUf();
	loadComboMunicipio("todos");
};

function loadComboClassficacao(){
	var $selectDropdown = $("#cmbClassificacao").empty().html(' ');
	var options = "";
	$.get("srl",{acao:"loadclassificacao"},
			function(dados){
			$.each(dados, function(i, item){
				options += "<option value='"+item.idclassificacao+"'>"+item.classificacaoescola+"</option>";
			});
			
			$selectDropdown.append($(options));
			$selectDropdown.trigger('contentChanged');
	});	
};

function loadComboVendedor(){
	
	var $selectDropdown = $("#cmbVendedor").empty().html(' ');
	var options = '';
	$.get("srl",{acao:"loadvendedores"},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item.setor+"'>"+item.nome+"</option>";
		});
		
		$selectDropdown.append($(options));
		$selectDropdown.trigger('contentChanged');		
	});		
};

function loadComboMunicipio(uf){
	
	var $selectDropdown = $("#cmbMunicipio").empty().html(' ');
	var options = "";
	
	$.get("srl",{acao:"loadmunicipios", txtuf:uf},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item+"'>"+item+"</option>";
		});
		
		$selectDropdown.append($(options));
		$selectDropdown.trigger('contentChanged');		
		
	});			
};

function loadComboUf(){
	
	var $selectDropdown = $("#cmbUF").empty().html(' ');
	var options = "";
	
	$.get("srl",{acao:"loaduf"},
			function(dados){
		$.each(dados, function(i, item){
			options += "<option value='"+item+"'>"+item+"</option>";
		});
		
		$selectDropdown.append($(options));
		$selectDropdown.trigger('contentChanged');		
		
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
			$("#cmbNivel").empty().html(' ');
			$("#cmbFamilia").empty().html(' ');
			$("#cmbSerie").empty().html(' ');
			$("#cmbEditora").empty().html(' ');
			$("#cmbDisciplina").empty().html(' ');
			
			$("#cmbNivel").append(optionNivel);
			$("#cmbNivel").trigger("contentChanged");
			$("#cmbFamilia").append(optionFamilia);	
			$("#cmbFamilia").trigger("contentChanged");
			$("#cmbSerie").append(optionSerie);
			$("#cmbSerie").trigger("contentChanged");
			$("#cmbEditora").append(optionEditora);
			$("#cmbEditora").trigger("contentChanged");
			$("#cmbDisciplina").append(optionDisciplina);
			$("#cmbDisciplina").trigger("contentChanged");
};


function loadDadosPesquisa(){
	$.get("srl",{acao:'loadcombosproduto'},
			function(dados){
			loadCombosPesquisa(dados);					
	});
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

	$("#comboFamilia").empty().html(' ');			
	$("#comboFamilia").append(optionFamilia);
	$("#comboFamilia").trigger("contentChanged");

	$("#comboNivel").empty().html(' ');
	$("#comboNivel").append(optionNivel);
	$("#comboNivel").trigger("contentChanged");

	$("#comboDisciplina").empty().html(' ');
	$("#comboDisciplina").append(optionDisciplina);
	$("#comboDisciplina").trigger("contentChanged");
	
	$("#comboSerie").empty().html(' ');
	$("#comboSerie").append(optionSerie);
	$("#comboSerie").trigger("contentChanged");
	
	
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
			
			
		$("#cmbNivel").empty().html(' ');
		$("#cmbFamilia").empty().html(' ');			
		$("#cmbSerie").empty().html(' ');
		$("#cmbEditora").empty().html(' ');
		$("#cmbDisciplina").empty().html(' ');

		$("#cmbNivel").append(optionNivel);
		$("#cmbNivel").trigger('contentChanged');
		$("#cmbFamilia").append(optionFamilia);
		$("#cmbFamilia").trigger('contentChanged');
		$("#cmbSerie").append(optionSerie);
		$("#cmbSerie").trigger('contentChanged');
		$("#cmbEditora").append(optionEditora);
		$("#cmbEditora").trigger('contentChanged');
		$("#cmbDisciplina").append(optionDisciplina);
		$("#cmbDisciplina").trigger('contentChanged');
		
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


function sendAjaxUploadNotaFiscal(formulario) {
  	
	$.ajax({
       type: "POST",
       url: "UploadNotaFiscal",
       data: formulario,
       dataType:'json',
       success: function(msg) {    	       	   
			mbox.alert(msg, function() {
				$("#formimportnotafiscal").trigger('reset');
			});
    	    
       }       
    });
 };	


function removerAcentos(newString){
	var string = newString.value;
	return string.toUpperCase();
} 

function removerAcentos2( newStringComAcento ) {

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
};



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
};

function fileUpload(formData, myform){			  

	$.ajax( {
		    url: 'srl',
		    type: 'POST',
		    data: formData,
		    processData: false,
		    contentType: false,
		    dataType: 'json',
		    beforeSend: function(){
		    	preloadActive();		    },
		    error: function(xhr, status, error){
		    	preloadDeActive();
				mbox.alert(xhr.responseText, function() {}); 						    	
		    },
			success: function(data){
				
				preloadDeActive();
				mbox.alert(data, function() {
					myform.trigger('reset');
				});
			}
		});	
};

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
        	console.log("Download iniciado!....");
        	preloadActive();
        },	    	
        failCallback: function (responseHtml, url) {
        	
        	preloadDeActive();
            console.log("Erro o tentar processar o arquivo!");
            
        },
        successCallback: function(url){

            console.log("Arquivo processado!....");
            preloadDeActive();

        },
        httpMethod: "POST",
        data: myform.serialize()
    });
  
    return false;	
};

function exportaConsultaExcel(e){
	
	e.preventDefault();
	
    $.fileDownload($(this).prop('href'), {
        prepareCallback: function (responseHtml, url) {
        	console.log("Download iniciado!....");
        	preloadActive();
            
        },	    	
        failCallback: function (responseHtml, url) {
        	
        	preloadActive();
            alert("Erro o tentar processar o arquivo!");
            
        },
        successCallback: function(url){
        	
        	preloadDeActive();
        	
        },
        httpMethod: "GET"
    });
    
    return false;		
};


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

	$("#modaldetalheadocao").modal('open');
	$(".modaldivdetalheadocao").load('adocaoSalvarItens.jsp #divcontent');
	$("#modalAdocaoLabel").text($("#txtdescricaoescola").val());
	//location.href = 'adocaoSalvarItens.jsp;'
	
};

function gravaRegistroAdocao(){
	$.get("srl", {acao:"registraradocao"},
			function(dados){
			mbox.alert(dados, function() {
				location.href = "index.jsp";
			});    			
	});    			
};

function loadSeriesProduto(){
	$.get("srl",{acao:"loadseriesproduto"},
			function(dados){
	});
};

function refreshCombosSeries(){	
	for(var i=0; i < $("#countcmb").val(); i++){
		$("#"+i).trigger('contentChanged');		
	}
	
	mbox.alert("ALTERE AS SERIES DE ACORDO COM A ADOCAO!!!", function() {
		//$(".select").selectpicker('refresh');
	});    						
};

function sendAjaxDeletarSerieAdocao(event){
	event.preventDefault();
	var valor = $("#txtdescricaoescola-id").val();
	
	mbox.confirm("Tem certeza?", function(result) {
			if(result){
				$.get("srl",{acao:"deletarserieadocao"},
						function(msg){
						$("#cmbAnoAdocao").empty().html(' ');
						$("#formtabela").empty().html(' ');
						$('#qtdTotalCart').load("orcamentoTotal.jsp #divcontent");
						mbox.alert(msg, function() {
							loadComboAnosAdocao(valor);
							$("#cmbSerie").empty().html(' ');
							$("#cmbSerie").trigger('contentChanged');
						});
				});
			}
   		});	
};

function carregarUsuarios(){
	
	var $selectDropdown = $("#cmbListUsuarios").empty().html(' ');
	$selectDropdown.append('<option value="" disabled selected>Escolha um usuário</option>');
	$.get("srl",{acao:'carregartodosusuarios'},
			function(dados){		
			$.each(dados, function(i, item){				
				if($("#txtcargousuariologado").val() != '1'){
					$selectDropdown.addClass('noDisplay');
					$("#formEditarUsuario").removeClass('noDisplay');
					carregarDadosUsuario(item.id);
					return false;
				}else{					
					$selectDropdown.append('<option value='+item.id+'>'+item.nome+'</option>');
				}
			});
			$selectDropdown.trigger('contentChanged');
	});			
};


function carregarDadosUsuario(id){	

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
	
	$("#txtcargo").trigger('contentChanged');
	
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
	var $selectDropdown = $('#selanos'+codigo).empty().html(' ');
	$.get('srl',{acao:'loadyearselection',txtaction:actionselection},
		function(dados){
		$.each(dados, function(i, item){
			$selectDropdown.append("<option value="+item+">"+item+"</option>");
		});
		$selectDropdown.trigger('contentChanged');remove
	});
	
	if(actionselection == 'consultarkardex'){
		$('#div-combo-filiais'+codigo).removeClass('noDisplay');
		$('#div-date-between'+codigo).removeClass('noDisplay');
		$('#div-date-between'+codigo).addClass('noDisplay');
		loadComboFiliaisFTD($("#cmbEmpresas"+codigo));
	}else if(actionselection == 'consultarpendencias'){
		$('#div-combo-filiais'+codigo).removeClass('noDisplay');
		$('#div-combo-filiais'+codigo).addClass('noDisplay');
		$('#selanos'+codigo).removeClass('noDisplay');
		$('#selanos'+codigo).addClass('noDisplay');
		$('#div-date-between'+codigo).removeClass('noDisplay');
	}else{
		$('#selanos'+codigo).addClass('noDisplay');
		$('#selanos'+codigo).removeClass('noDisplay');
		$('#div-date-between'+codigo).removeClass('noDisplay');
		$('#div-date-between'+codigo).addClass('noDisplay');
		$('#div-combo-filiais'+codigo).removeClass('noDisplay');
		$('#div-combo-filiais'+codigo).addClass('noDisplay');
	}
	
};

function loadComboYear(){
	$('#cmbano').empty().html(' ');
	$.get('srl',{acao:'loadyearselection',txtaction:"consultaradocoes"},
		function(dados){
		$.each(dados, function(i, item){
			$('#cmbano').append("<option value="+item+">"+item+"</option>");
		});
		$('#cmbano').trigger('contentChanged');		
	});
};

function loadYearTermometro(){
	$('#cmbAnoAdocao').empty().html(' ');
	$.get('srl',{acao:'loadyearselection',txtaction:"consultaradocoes"},
		function(dados){
		$.each(dados, function(i, item){
			$('#cmbAnoAdocao').append("<option value="+item+">"+item+"</option>");
		});
		$('#cmbAnoAdocao').trigger('contentChanged');
	});	
};

function sendAjaxAdocaoTermometro(formulario){
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
    		mbox.alert(msg, function(){});
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
		     
		 $("#modalajaxloader").modal('open');
		  location.href = aurl;

		 $("#modalajaxloader").modal("close");

		 $("#modalajaxloader").modal("close");

		  
     }else{
    	 event.run();
     }
    return false;	
};


function listItensAtendidos(codigo, idpedido){
	
	$("#col"+codigo).empty();
	
	$.get('srl',{acao:"listaritensatendidos", txtcodigo:codigo, txtidpedido:idpedido},
			function(dados){			
			var tabela = "" +
					"<table><tr width='100%'><th>Data de atendimento" +
					"_________________Quantidade_________________....</th></tr>" +
					"<tbody>";
			var count = 0;
			$.each(dados, function(i, item){
				count++;
				tabela += "<tr><td width='100%'><form action='srl' method='post'><table>" +
						"<tr><td width='40%'><input type='text' value='"+item.sdate+"' name='"+codigo+"date' readonly></td>" +
								"<td width='30%'>"
				+"<input type='number' min='0' max='"+item.qtdatendida+"' value='"+item.qtdatendida+"'" +
						" name='"+item.id+"' style='width: 60px' readonly></td>" +
				"<td width='30%'><input type='hidden' name='codigo' value='"+codigo+"'>" +
				"<input type='hidden' name='acao' value='alteraritematendido'>"+
				"<input type='hidden' name='item' value='"+item.id+"'>" +
				"<input type='submit' value='Delete'></td></tr></form></table></td></tr>";
			});
			tabela += "</tbody></table>";
			$("#divmodalitemalterar").empty();
			$("#divmodalitemalterar").append(tabela);
			$("#modalItemPedCliAlterar").modal('open');
	});
	
};


function alterarQtdeItemPedido(codigo,idpedido, quantidade){
	var tabela = "<form action='srl' method='post' id='formAlterarQtdeItemPedido'>" +
	"<table><tr>" +
	"<th width='200x'><strong>Quantidade</strong></th><th width='200x'>....</th></tr>";
	tabela += "<tr><td>"
	+"<input type='number' min='1' value='"+quantidade+"' name='"+codigo+"' style='width: 60px'></td>" +
	"<td><input type='hidden' name='codigo' value='"+codigo+"'><input type='hidden' name='acao' value='alterarqtdeitempedido'>"+
	"<input type='submit' value='Enviar'></td></tr>";
	tabela += "</table></form>";
	$("#divmodalitemalterar").empty();
	$("#divmodalitemalterar").append(tabela);	
	$("#modalItemPedCliAlterar").modal('open');
};

function sendGlassListar(formulario){
	 preloadActive();
	 $.ajax({
 	       type: "POST",
 	       url: "srl",
 	       data: formulario,
 	       dataType: "html",
 	       success: function(page) {
 	    	 preloadDeActive();
 	    	 location.href = "glassview.jsp"
 	       }
	 });
};
