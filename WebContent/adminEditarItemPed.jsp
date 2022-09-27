<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row" id="divcontent">
	
	<div class="row">
	
		<form id="formedititemped" action="srl" method="post">
		
			<div class="col l3 m3 s12">
				<label>Número do pedido: </label>
					<input type="text" class="form-control" id="txtidpedido"
						name="txtidpedido" readonly size="15" maxlength="15"
						value="${itempedido.idpedido}">
			</div>
	
			
			<div class="input-group col l3 m3 s12">
				<label>Código</label>				
				<input type="text" class="form-control" id="txtcodigo"
					name="txtcodigo" readonly size="15"
					value="${itempedido.item.codigo}">
			</div>
			
			<div class="input-group col l6 m6 s12">
				<label>Descrição</label>
				<input type="text" class="form-control" id="txtdescricao" readonly
					size="54" value="${itempedido.item.descricao}">			
			</div>
	
			<div class="col l3 m3 s12">
				<label>Previsão:</label> <input type="date" class="form-control"
					name="txtprevisao" id="txtprevisao" size="8"
					value="${itempedido.previsao}">
			</div>
			<div class="col l3 m3 s4">
				<label>Qt.Pedida:</label> <input type="text" class="form-control"
					name="txtqtdpedida" id="txtqtdpedida" size="4"
					value="${itempedido.qtdpedida}">
			</div>
			<div class="col l3 m3 s4">
				<label>Qt.Atendida:</label> <input type="text" class="form-control"
					id="txtqtdatendida" readonly size="4"
					value="${itempedido.qtdchegou}">
			</div>
			<div class="col l3 m3 s4">
				<label>Qt.Pendente:</label> <input type="text" class="form-control"
					id="txtqtdpendente" readonly size="4"
					value="${itempedido.qtdpendente}">
			</div>
	
			<div class="col s12">
				<label>Cancelado?</label>
				<c:choose>
					<c:when test="${itempedido.cancelado eq 1}">
						<p>
							<input type="radio"	class="with-gap" name="chkcancelar" id="rd1" value="1" checked>
							<label for="rd1">Sim</label>
						</p>
						<p>
							<input type="radio"	class="with-gap" name="chkcancelar" id="rd2" value="0">
							<label for="rd2">Não</label>
						</p>					
					</c:when>
					<c:otherwise>
						<p>
							<input type="radio"	class="with-gap" name="chkcancelar" id="rd1" value="1">
							<label for="rd1">Sim</label>
						</p>
						<p>
							<input type="radio"	class="with-gap" name="chkcancelar" id="rd2" value="0" checked>
							<label for="rd2">Não</label>
						</p>					
					</c:otherwise>
	
	
				</c:choose>
				<label style="font-size: 8pt; color: red;">Obs.: Caso sim,
					será retirado do relatório de itens pendentes</label> <br />
					<label>Observação:</label>
					<textarea rows="5" cols="40" class="form-control"
					name="txtobservacao">${itempedido.observacao}</textarea>
			</div>
	
			<input type="hidden" name="acao" value="editaritempedido">
	
		</form>
		
	</div>
</div>
