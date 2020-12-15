<style>
@media print {
  	@ page {size: 297mm 210mm;
			margin: 0;
	}
	body{
		margin: 0;
	}
	.container{
		margin: 0;
	}  
}

.watermark {
  width: 500px;
  height: 250px;
  display: block;
  position: relative;
  border: 5px double black;
  font-style: normal;
  font-family: Arial Narrow !important;
  text-align: center;
}

.watermark .bonus-text1{
	font-size: 1em;
	font-weight: bold;
}

.bonus-qrcode{
	position: absolute;
	width: 80%;
	height: auto;
	top: 130px;
	left: 380px;
}

.bonus-text2{
	font-size: 2em;
	font-weight: bold;
	width: 480px;
	position: absolute;
	margin: 0 auto;
	top: 75px;	
}

.bonus-text3{
	font-size: 0.8em;
	font-weight: bold;
	width: 480px;
	position: absolute;
	margin: 0 auto;
	top: 100px;
}

.bonus-text4{
	font-size: 0.7em;
}

.bonus-rodape{
	font-size: 12pt !important;
	position: absolute;
	top: 130px;
	margin-left: 10px;
	text-align: left;
}

.bonus-rodape-text{
	margin-top: 10px;
	font-size: 13pt !important;
	font-weight: bold;
	padding-left: 45%;
}

.watermark img {
	width: 25%;
	heght: auto;
	float: left;
}
</style>

<fmt:formatNumber var="roundedDesconto" maxFractionDigits="0" type="number" pattern="#,##0.0#" value="${bonus.desconto}" />

<table id="tabelabonus" class="page-break">
<c:forEach begin="0" end="4" varStatus="loop">
<tr>
<td>
<div class="watermark">
	<img alt="logo" src="resources/images/logofe.png">	
    <p class="bonus-text1">${bonus.escola.nome}<br>
    <span class="bonus-text4">${bonus.escola.endereco} - ${bonus.escola.bairro}, ${bonus.escola.municipio}-${bonus.escola.uf}</span></p>   
    <c:choose>
    	<c:when test="${bonus.tipo eq 'valor'}">
    		<p class="bonus-text2">R$ <fmt:formatNumber 
    		value="${bonus.desconto}" type="number" pattern="#,##0.00#" /></p>    		
    	</c:when>
    	<c:otherwise>
    		<p class="bonus-text2">${roundedDesconto}% à Vista</p>
    	</c:otherwise>
    </c:choose>
    <div class="bonus-qrcode">
    	<img alt="" src="resources/images/${bonus.qrcode}">
    </div>           
    <p class="bonus-text3">[${bonus.descricao}]</p><br>    
    <div class="bonus-rodape"><span>-Desconto válido somente nas lojas da FTD Educação<br>
    							 -Contatos: ${pageFone}<br>
    							 -E-mail: ${pageEmail}<br>
    						 </span>	 
   							 <div class="bonus-rodape-text"> *${bonus.escola.vendedor.nome}*
   										&nbsp;&nbsp;${bonus.ano}
   							</div>
	</div>
    
</div>
</td>
<td>
<div class="watermark">
	<img alt="logo" src="resources/images/logofe.png">	
    <p class="bonus-text1">${bonus.escola.nome}<br>
    <span class="bonus-text4">${bonus.escola.endereco} - ${bonus.escola.bairro}, ${bonus.escola.municipio}-${bonus.escola.uf}</span></p>   
    <c:choose>
    	<c:when test="${bonus.tipo eq 'valor'}">
    		<p class="bonus-text2">R$ <fmt:formatNumber 
    		value="${bonus.desconto}" type="number" pattern="#,##0.00#" /></p>    		
    	</c:when>
    	<c:otherwise>
    		<p class="bonus-text2">${roundedDesconto}% à Vista</p>
    	</c:otherwise>
    </c:choose>       
    <div class="bonus-qrcode">
    	<img alt="" src="resources/images/${bonus.qrcode}">
    </div>           
    <p class="bonus-text3">[${bonus.descricao}]</p><br>    
    <div class="bonus-rodape"><span>-Desconto válido somente nas lojas da FTD Educação<br>
    							 -Contatos: ${pageFone}<br>
    							 -E-mail: ${pageEmail}<br>
    						 </span>	 
   							 <div class="bonus-rodape-text"> *${bonus.escola.vendedor.nome}*
   										&nbsp;&nbsp;${bonus.ano}
   							</div>
	</div>
    
</div>
</td>
</tr>
</c:forEach>
</table>