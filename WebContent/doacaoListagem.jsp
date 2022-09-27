<script type="text/javascript">
		
	$(document).ready(function(){
			
		$("#tabledoacoes").dataTable({
			"bPaginate": true,
		    "sDom":'fptip',
		    "footerCallback": function ( row, data, start, end, display ) {
	            var api = this.api(), data;
	 
	            // Remove the formatting to get integer data for summation
	            var intVal = function ( i ) {
	                return typeof i === 'string' ?
	                    i.replace(/[\$,]/g, '')*1 :
	                    typeof i === 'number' ?
	                        i : 0;
	            };
	 			
	            // Total over this page
	            pageTotal = api
	                .column( 4, { page: 'current'} )
	                .data()
	                .reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
	 
		    }
		});
		
	});
	
	function formatReal( int )
	{
		var tmp = int+'';
        var neg = false;
        if(tmp.indexOf("-") == 0)
        {
            neg = true;
            tmp = tmp.replace("-","");
        }
        
        if(tmp.length == 1) tmp = "0"+tmp;
    
        tmp = tmp.replace(/([0-9]{2})$/g, ",$1");
        if( tmp.length > 6)
            tmp = tmp.replace(/([0-9]{3}),([0-9]{2}$)/g, ".$1,$2");
        
        if( tmp.length > 9)
            tmp = tmp.replace(/([0-9]{3}).([0-9]{3}),([0-9]{2}$)/g,".$1.$2,$3");
    
        if( tmp.length > 12)
            tmp = tmp.replace(/([0-9]{3}).([0-9]{3}).([0-9]{3}),([0-9]{2}$)/g,".$1.$2.$3,$4");
        
        if(tmp.indexOf(".") == 0) tmp = tmp.replace(".","");
        if(tmp.indexOf(",") == 0) tmp = tmp.replace(",","0,");
    
    	return (neg ? '-'+tmp : tmp);
   	}
	
</script>
<div class="center-align div-title-padrao col s12">
		Listagem de doações
</div>
<br>

<div class="row">
	
	<div class="col m10 s12 offset-m1">
	
		<a href="srl?acao=listardoacoestoexcel"
			onclick="exportaConsultaExcel(e)"><img
			src="resources/images/export_excel.png" title="Exportar para Excel"
			style="float: left; margin-right: 1%"></a>
	
		<table class="highlight reponsive-table"
			id="tabledoacoes">
			<thead>
				<tr class="rows">
					<th>Nº Contr.</th>
					<th>Emissão</th>
					<th>Professor</th>
					<th>Qtde</th>
					<th>Total</th>
					<th>Escola</th>
					<th>Vendedor</th>
					<th class="col l3 m3 s3">#</th>
				</tr>
			</thead>
	
			<c:set var="totalitens" value="0"></c:set>
			<c:set var="totalqtde" value="0"></c:set>
			<c:set var="totalvalor" value="0"></c:set>
	
			<tbody>
				<c:forEach var="doacao" items="${listadoacao}">
	
					<c:set var="totalitens" value="${totalitens + 1}"></c:set>
					<c:set var="totalqtde" value="${totalqtde + doacao.qtdtotal }"></c:set>
					<c:set var="totalvalor" value="${totalvalor + doacao.total}"></c:set>
					<tr>
						<td>${doacao.id}</td>
						<td><fmt:formatDate value="${doacao.emissao}"
								pattern="dd/MM/yyyy" /></td>
						<td><a href="javascript:void(0)"
							onclick="doacaoPrint(${doacao.id})">${doacao.professor.nome}</a></td>
						<td>${doacao.qtdtotal }</td>
						<td style="text-align: right"><fmt:formatNumber
								value="${doacao.total}" type="number" pattern="#,#00.00#" /></td>
						<td>${doacao.escola.nome},
							${doacao.escola.municipio}-${doacao.escola.uf}</td>
						<td>${doacao.usuario.nome}</td>
						<td><c:if test="${usuariologado.cargo eq 1}">
							<div class="row">
								<div class="col l6 m6 s12">
									<a type="button" class="btn-floating btn-small waves-effect waves-light red noPrint"
										onclick="alterardoacaoclick('${doacao.id}')">
										<i class="material-icons" title="Alterar Doação">edit</i>
									</a>
								</div>
								<div class="col l6 m6 s12">
									<a type="button" class="btn-floating btn-small waves-effect waves-light black noPrint"
										onclick="deletardoacaoclick('${doacao.id}')">
										<i class="material-icons" title="Deletar Doação">delete</i>
									</a>
								</div>
							</div>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	
			<tfoot>
				<tr style="font-size: 9pt; font-weight: bold">
					<td colspan="3">${totalitens }itens</td>
					<td>${totalqtde }</td>
					<td><fmt:formatNumber value="${totalvalor}" type="number"
							pattern="#,#00.00#" /></td>
					<td colspan="3"></td>
				</tr>
			</tfoot>
	
		</table>
		
	</div>
	
</div>