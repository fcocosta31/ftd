<script type="text/javascript">	
		function deletaroteiroclick(idroteiro){
	
			mbox.confirm("Tem certeza?", function(result) {
				if(result){
					$.get("srl",{acao:'deletaroteiro', txtidroteiro:idroteiro},
							function(msg){
							mbox.alert(msg, function() {
								location.href = 'roteiroListagem.jsp';
							});						
					});			
				}
	   		});
			
		};

</script>

<div class="center-align div-title-padrao col s12">
		Listagem dos Roteiros de Divulgação
</div>
<br><br>

<div>

	<div class="container">

		<div class="row">


			<div class="col-md-10 col-md-offset-1">

				<div id="formtabela">


					<div class="modal">
						<!-- Place at bottom of page -->
					</div>


					<div class="row" id="divlistagemescolas">


						<table class="highlight responsive-table datables-table">
							<thead>
								<tr>
									<th>Nº Contr.</th>
									<th>Data do roteiro</th>
									<th>No.Escolas</th>
									<th>Vendedor</th>
									<th>#</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach var="t" items="${listarroteiros}">

									<tr style="font-size: 12pt; color: #000099;">
										<td>${t.id}</td>
										<td><a href="javascript:void(0)"
											onclick="detalharroteiroclick('${t.id}')"><fmt:formatDate
													value="${t.data}" pattern="dd/MM/yyyy" /></a></td>
										<td>${fn:length(t.escolas)}</td>
										<td>${t.vendedor.nome}</td>
										<td>																				
											<a href="javascript:void(0)"
												class="btn-floating btn-small waves-effect waves-light black noPrint"
												onclick="deletaroteiroclick('${t.id}')"
												title="Apagar roteiro"> 
												<i class="material-icons">delete</i>
											</a>
										</td>
									</tr>
								</c:forEach>

							</tbody>

							<tfoot>
								<tr style="color: gray;">
									<td colspan="5"></td>
								</tr>
							</tfoot>

						</table>
					</div>

				</div>
				<!-- Fim da div formtabela -->

			</div>

		</div>
	</div>
</div>
