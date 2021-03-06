/*
 * @function ajaxAutocomplete
 *      Creates an autocomplete dropdown
 *      from content fetched via AJAX calls.
 *
 * @param {Object} options
 *      Used to pass options to the function
 *          options.ajaxURL     =   URL for the AJAX call
 *          options.inputId     =   Input field to use for autcompletion
 *          options.minLength   =   Minimum char length to start making AJAX calls.
 * 
 *      eg: var options = { ajaxURL: "/users/checkUserName",
 *                          inputId: 'autocompleteInput',
 *                          minLength: 3 };
 *
 * NOTE - This snippet uses jQuery.
*/


function ajaxAutoComplete(options)
{
    var $input = $("#" + options.inputId);

    var $autocomplete = $('<ul id="ac" class="autocomplete-content dropdown-content"'
                          + 'style="position:absolute"></ul>'),
        $inputDiv = $input.closest('.input-field'),
        request,
        runningRequest = false,
        timeout,
        liSelected;

    if ($inputDiv.length) {
        $inputDiv.append($autocomplete); // Set ul in body
    } else {
        $input.after($autocomplete);
    }

    // function to highlight search query
    var highlight = function (string, match) {
        var matchStart = string.toLowerCase().indexOf("" + match.toLowerCase() + ""),
            matchEnd = matchStart + match.length - 1,
            beforeMatch = string.slice(0, matchStart),
            matchText = string.slice(matchStart, matchEnd + 1),
            afterMatch = string.slice(matchEnd + 1);
        string = "<span>" + beforeMatch + "<span class='highlight'>" + 
                    matchText + "</span>" + afterMatch + "</span>";
        return string;

    };

    // setting selected item to the input upon clicking
    $autocomplete.on('click', 'li', function () {
        $input.val('');
        var value = $(this).data('id');
        $autocomplete.empty();
        preloadActive();
        $.get("srl",{acao:"pesquisarprd", txtdescricao:value})			
    	.done(function(dados){
    		preloadDeActive();          
    		location.href = "tabela.jsp";
    		$('#autocomplete-input').val(''); $('#autocomplete-input').focus();				
    	});			        
    });

    $input.on('keyup', function (e) {

//        if (timeout) { // comment to remove timeout
//            clearTimeout(timeout);
//        }

        if (runningRequest) {
            request.abort();  // stop requests that are already sent
        }

        if (e.which === 13) { // select element with Enter
            liSelected[0].click();
            return;
        }
        
        // scroll ul with arrow keys
        if (e.which === 40) {   // down arrow
            if (liSelected) {
                liSelected.removeClass('selected');
                next = liSelected.next();
                if (next.length > 0) {
                    liSelected = next.addClass('selected');
                } else {
                    liSelected = $autocomplete.find('li').eq(0).addClass('selected');
                }
            } else {
                liSelected = $autocomplete.find('li').eq(0).addClass('selected');
            }
            return; // stop new AJAX call
        } else if (e.which === 38) { // up arrow
            if (liSelected) {
                liSelected.removeClass('selected');
                next = liSelected.prev();
                if (next.length > 0) {
                    liSelected = next.addClass('selected');
                } else {
                    liSelected = $autocomplete.find('li').last().addClass('selected');
                }
            } else {
                liSelected = $autocomplete.find('li').last().addClass('selected');
            }
            return;
        } 

        // escape these keys
        if (e.which === 9 ||        // tab
            e.which === 16 ||       // shift
            e.which === 17 ||       // ctrl
            e.which === 18 ||       // alt
            e.which === 20 ||       // caps lock
            e.which === 35 ||       // end
            e.which === 36 ||       // home
            e.which === 37 ||       // left arrow
            e.which === 39) {       // right arrow
            return;
        } else if (e.which === 27) { // Esc. Close ul
            $autocomplete.empty();
            return;
        }

        var val = $input.val().toLowerCase();
        $autocomplete.empty();

        if (val.length >= options.minLength) {                  // run only if 3 or more characters are entered
  //          timeout = setTimeout(function () { // comment this line to remove timeout
                runningRequest = true;
                
                $input.addClass('ajaxWorking');
                
                request = $.ajax({
                    type: 'GET',
                    url: options.ajaxURL + val,
                    success: function (data) {
                        if (!$.isEmptyObject(data)) { // (or other) check for empty result
                            /*
                                We concatenate the fetched results as strings which
                                will be finally parsed and appended at once.
                                This is more efficient than appending each
                                result directly as <li> individually.
                            */
                        	$input.removeClass('ajaxWorking');
                        	
                            var appendList = '';  // the full results list that we finally append
                            for (var key in data) {
                                if (data.hasOwnProperty(key)) {
                                    var li = '';           // individual result line string
                                    if (!!data[key]) {     // if image exists as in official docs
                                    	li += '<li class="ac-item" data-id="'+data[key].id+
                                    			'" data-text=\"'+data[key].text+
                                    			'"><a href="javascript:void(0)"><span style="font-size: 10pt; font-weight: bold">'+
                                    			data[key].text+'</span><br><span style="font-size: 7pt">'+
                                    			data[key].autor+'</span></a></li>';
                                    }
                                    appendList += li;
                                }
                            }
                            $autocomplete.append(appendList);   // finally appending everything
                        }else{
                            $autocomplete.append($('<li>Sem resultados!</li>'));
                        }
                    },
                    complete: function () {
                        runningRequest = false;
                    }
                });
    //        }, 1000);        // comment this line to remove timeout
        }
    });

    $(document).click(function () { // close ul if clicked outside
        if (!$(event.target).closest($autocomplete).length) {
            $autocomplete.empty();
        }
    });
}