    function getFileContent() {

        var text = $("#cssFilesList option:selected").attr("title");

        $.ajax({
                type: 'GET',
                url: "/openmrs/module/custombranding/CssContent.form?path=" + text,
                dataType: 'text',
                async: true,
                success: function(response) {
                      $('#contentBox').text(response);
                },
                error: function(jqXHR, textStatus, errorThrown) {

                }
        });
    }

    function toogleRecursiveSearchingAndList(elementId) {

        $.ajax({
                type: 'GET',
                url: "/openmrs/module/custombranding/SearchCssFiles.form",
                dataType: 'text',
                async: true,
                success: function(response) {
                     document.getElementById(elementId).options.length = 0;

                        jQuery.each( $.parseJSON(response), function(i, val) {
                            var x = document.getElementById(elementId);
                            var option = document.createElement("option");
                            option.text = val;
                            option.value = val;
                            option.title = i;
                            x.add(option);

                        });
                },
                error: function(jqXHR, textStatus, errorThrown) {

                }
        });
    }

    function dbRequest(action) {

        function func() {
                        location.reload(true);
                      }
        if(action === "replaceCssFile" ){
            if( $("#cssFilesList option:selected").text() !== '' && $("#uploadCssFile").val() !== "" ) {


                var content;
                 readSingleFile('uploadCssFile',content);

                alert(content);

                var data = {
                    'action': action,
                    'content': content
                }

                ajaxRequest('POST', "/openmrs/module/custombranding/dbRequest.form", 'text', true, data, func, func);

            } else {
                $("#errors").text("You need to choose css file to replace with pointed by you");
            }

        } else if( $("#cssFilesList option:selected").text() !== '') {
                var data = {
                    'action': action,
                    'content':   document.getElementById('contentBox').value
                    }

                 ajaxRequest('POST', "/openmrs/module/custombranding/dbRequest.form", 'text', true, data, func, func);

        }
    }
        function ajaxRequest(_type, _url, _dataType, _async, _data, _succes, _error) {
             $.ajax({
                type: _type,
                url: _url,
                dataType: _dataType,
                async: _async,
                data: _data,
                success: _succes,
                error: _error
            });
        }

            function readSingleFile(fileElementId, content) {
                  var file = document.getElementById(fileElementId).files[0];
                   if (!file) {
                              return;
                            }
                  var ready = false;

                  function check(){
                      if (ready === true) {
                           return content;
                      }
                      setTimeout(check, 800);
                  }



                  var reader = new FileReader();
                  reader.onloadend = function() {
                      content = reader.result;
                      ready = true;
                  };
                  reader.readAsText(file);
                  check();
            }





